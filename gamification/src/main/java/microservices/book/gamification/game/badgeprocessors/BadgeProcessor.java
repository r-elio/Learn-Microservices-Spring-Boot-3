package microservices.book.gamification.game.badgeprocessors;

import microservices.book.gamification.challenge.ChallengeSolvedEvent;
import microservices.book.gamification.game.domain.BadgeType;
import microservices.book.gamification.game.domain.ScoreCard;

import java.util.List;
import java.util.Optional;

public interface BadgeProcessor {
    /**
     * Process some or all of the passed parameters and decides if the user is entitled to a badge.
     *
     * @param currentScore  current score of a user
     * @param scoreCardList list of score from a user
     * @param solvedEvent   a current solved challenge from a user
     * @return a BadgeType if the user is entitled to this badge, otherwise empty
     */
    Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedEvent solvedEvent);

    /**
     * @return the BadgeType object that this processor is handling. You can use it to filter processors according to your needs.
     */
    BadgeType badgeType();
}
