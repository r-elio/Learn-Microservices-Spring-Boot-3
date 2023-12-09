package microservices.book.gamification.game.domain;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class LeaderBoardRow {
    private final Long userId;
    private final Long totalScore;
    private final List<String> badges;

    public LeaderBoardRow(final Long userId, final Long totalScore) {
        this.userId = userId;
        this.totalScore = totalScore;
        this.badges = List.of();
    }

    public LeaderBoardRow(final Long userId, final Long totalScore, final List<String> badges) {
        this.userId = userId;
        this.totalScore = totalScore;
        this.badges = badges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LeaderBoardRow row = (LeaderBoardRow) o;
        return Objects.equals(userId, row.userId) &&
                Objects.equals(totalScore, row.totalScore) &&
                Objects.equals(badges, row.badges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, totalScore, badges);
    }

    @Override
    public String toString() {
        return String.format("LeaderBoardRow(userId=%s, totalScore=%s, badges=%s)",
                userId, totalScore, badges);
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTotalScore() {
        return totalScore;
    }

    public List<String> getBadges() {
        return Collections.unmodifiableList(badges);
    }

    public LeaderBoardRow withBadges(List<String> badges) {
        return new LeaderBoardRow(this.userId, this.totalScore, badges);
    }
}
