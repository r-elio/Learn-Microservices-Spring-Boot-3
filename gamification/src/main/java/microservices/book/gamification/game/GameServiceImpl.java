package microservices.book.gamification.game;

import microservices.book.gamification.challenge.ChallengeSolvedEvent;
import microservices.book.gamification.game.badgeprocessors.BadgeProcessor;
import microservices.book.gamification.game.domain.BadgeCard;
import microservices.book.gamification.game.domain.BadgeType;
import microservices.book.gamification.game.domain.ScoreCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);

    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;
    // Spring injects all the @Component beans in this list
    private final List<BadgeProcessor> badgeProcessors;

    public GameServiceImpl(final ScoreRepository scoreRepository, final BadgeRepository badgeRepository,
                           final List<BadgeProcessor> badgeProcessors) {
        this.scoreRepository = scoreRepository;
        this.badgeRepository = badgeRepository;
        this.badgeProcessors = badgeProcessors;
    }

    @Override
    public GameResult newAttemptForUser(ChallengeSolvedEvent challenge) {
        // We give points only if it's correct
        if (challenge.correct()) {
            ScoreCard scoreCard = new ScoreCard(challenge.userId(), challenge.attemptId());
            scoreRepository.save(scoreCard);
            log.info("User {} scored {} points for attempt id {}", challenge.userAlias(), scoreCard.getScore(),
                    challenge.attemptId());
            List<BadgeCard> badgeCards = processForBadges(challenge);
            return new GameResult(scoreCard.getScore(),
                    badgeCards.stream().map(BadgeCard::getBadgeType).collect(Collectors.toList()));
        } else {
            log.info("Attempt id {} is not correct. User {} does not get score", challenge.attemptId(),
                    challenge.userAlias());
            return new GameResult(0, List.of());
        }
    }

    /**
     * Checks the total score and the different scorecards
     * obtained to give new badges in case their conditions are met.
     */
    private List<BadgeCard> processForBadges(final ChallengeSolvedEvent solvedChallenge) {
        Optional<Integer> optTotalScore = scoreRepository.getTotalScoreForUser(solvedChallenge.userId());
        if (optTotalScore.isEmpty())
            return Collections.emptyList();
        int totalScore = optTotalScore.get();
        // Get the total score and existing badges for that user
        List<ScoreCard> scoreCardList = scoreRepository
                .findByUserIdOrderByScoreTimestampDesc(solvedChallenge.userId());
        Set<BadgeType> alreadyGotBadges = badgeRepository
                .findByUserIdOrderByBadgeTimestampDesc(solvedChallenge.userId()).stream()
                .map(BadgeCard::getBadgeType).collect(Collectors.toSet());
        // Calls the badge processors for badges that the user doesn't have yet
        List<BadgeCard> newBadgeCards = badgeProcessors.stream()
                .filter(bp -> !alreadyGotBadges.contains(bp.badgeType()))
                .map(bp -> bp.processForOptionalBadge(totalScore, scoreCardList, solvedChallenge))
                .flatMap(Optional::stream).map(badgeType -> new BadgeCard(solvedChallenge.userId(), badgeType))
                .collect(Collectors.toList());
        badgeRepository.saveAll(newBadgeCards);
        return newBadgeCards;
    }
}
