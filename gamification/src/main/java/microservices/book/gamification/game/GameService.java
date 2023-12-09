package microservices.book.gamification.game;

import microservices.book.gamification.challenge.ChallengeSolvedDTO;
import microservices.book.gamification.game.domain.BadgeType;

import java.util.List;
import java.util.Objects;

public interface GameService {
    /**
     * Process a new attempt from a given user.
     *
     * @param challenge the challenge data with user details, factors, etc.
     * @return a {@link GameResult} object containing the new score and badge cards obtained
     */
    GameResult newAttemptForUser(ChallengeSolvedDTO challenge);

    class GameResult {
        private final int score;
        private final List<BadgeType> badges;

        public GameResult(int score, List<BadgeType> badges) {
            this.score = score;
            this.badges = badges;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            GameResult gameResult = (GameResult) o;
            return score == gameResult.score &&
                    Objects.equals(badges, gameResult.badges);
        }

        @Override
        public int hashCode() {
            return Objects.hash(score, badges);
        }

        @Override
        public String toString() {
            return String.format("GameResult(score=%s, badges=%s)", score, badges);
        }

        public int getScore() {
            return score;
        }

        public List<BadgeType> getBadges() {
            return badges;
        }
    }
}
