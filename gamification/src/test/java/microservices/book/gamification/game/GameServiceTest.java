package microservices.book.gamification.game;

import microservices.book.gamification.challenge.ChallengeSolvedEvent;
import microservices.book.gamification.game.GameService.GameResult;
import microservices.book.gamification.game.badgeprocessors.BadgeProcessor;
import microservices.book.gamification.game.domain.BadgeCard;
import microservices.book.gamification.game.domain.BadgeType;
import microservices.book.gamification.game.domain.ScoreCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    private GameService gameService;
    @Mock
    private ScoreRepository scoreRepository;
    @Mock
    private BadgeRepository badgeRepository;
    @Mock
    private BadgeProcessor badgeProcessor;

    @BeforeEach
    public void setUp() {
        gameService = new GameServiceImpl(scoreRepository, badgeRepository, List.of(badgeProcessor));
    }

    @Test
    public void processCorrectAttemptTest() {
        // given
        long userId = 1L, attemptId = 10L;
        ChallengeSolvedEvent attempt = new ChallengeSolvedEvent(attemptId, true, 20, 70, userId, "john");
        ScoreCard scoreCard = new ScoreCard(userId, attemptId);
        given(scoreRepository.getTotalScoreForUser(userId)).willReturn(Optional.of(10));
        given(scoreRepository.findByUserIdOrderByScoreTimestampDesc(userId)).willReturn(List.of(scoreCard));
        given(badgeRepository.findByUserIdOrderByBadgeTimestampDesc(userId)).willReturn(List.of(new BadgeCard(userId, BadgeType.FIRST_WON)));
        given(badgeProcessor.badgeType()).willReturn(BadgeType.LUCKY_NUMBER);
        given(badgeProcessor.processForOptionalBadge(10, List.of(scoreCard), attempt)).willReturn(Optional.of(BadgeType.LUCKY_NUMBER));
        // when
        final GameResult gameResult = gameService.newAttemptForUser(attempt);
        // then
        then(gameResult).isEqualTo(new GameResult(10, List.of(BadgeType.LUCKY_NUMBER)));
        verify(scoreRepository).save(scoreCard);
        verify(badgeRepository).saveAll(List.of(new BadgeCard(userId, BadgeType.LUCKY_NUMBER)));
    }

    @Test
    public void processWrongAttemptTest() {
        // given
        ChallengeSolvedEvent attemptEvent = new ChallengeSolvedEvent(1L, false, 50, 60, 1L, "test");
        // when
        GameResult resultAttempt = gameService.newAttemptForUser(attemptEvent);
        // then
        then(resultAttempt).isEqualTo(new GameResult(0, List.of()));
    }
}
