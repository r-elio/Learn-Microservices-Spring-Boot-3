package microservices.book.gamification.game.badgeprocessors;

import microservices.book.gamification.challenge.ChallengeSolvedDTO;
import microservices.book.gamification.game.domain.BadgeType;
import microservices.book.gamification.game.domain.ScoreCard;

import java.util.List;
import java.util.Optional;

public interface BadgeProcessor {
    /**
     * Process some or all of the passed parameters and decides if the user is entitled to a badge.
     *
     * @param currentScore
     * @param scoreCardList
     * @param solvedDTO
     * @return a BadgeType if the user is entitled to this badge, otherwise empty
     */
    Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedDTO solvedDTO);

    /**
     * @return the BadgeType object that this processor is handling. You can use it to filter processors according to your needs.
     */
    BadgeType badgeType();
}
