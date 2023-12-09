package microservices.book.gamification.game.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class BadgeCard {
    @Id
    @GeneratedValue
    private Long badgeId;
    private Long userId;
    private long badgeTimestamp;
    private BadgeType badgeType;

    public BadgeCard() {
    }

    public BadgeCard(Long badgeId, Long userId, long badgeTimestamp, BadgeType badgeType) {
        this.badgeId = badgeId;
        this.userId = userId;
        this.badgeTimestamp = badgeTimestamp;
        this.badgeType = badgeType;
    }

    public BadgeCard(final Long userId, final BadgeType badgeType) {
        this(null, userId, System.currentTimeMillis(), badgeType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BadgeCard badgeCard = (BadgeCard) o;
        return Objects.equals(badgeId, badgeCard.badgeId) &&
                Objects.equals(userId, badgeCard.userId) &&
                badgeType == badgeCard.badgeType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(badgeId, userId, badgeType);
    }

    @Override
    public String toString() {
        return "BadgeCard{" +
                "badgeId=" + badgeId +
                ", userId=" + userId +
                ", badgeTimestamp=" + badgeTimestamp +
                ", badgeType=" + badgeType +
                '}';
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public long getBadgeTimestamp() {
        return badgeTimestamp;
    }

    public void setBadgeTimestamp(long badgeTimestamp) {
        this.badgeTimestamp = badgeTimestamp;
    }

    public BadgeType getBadgeType() {
        return badgeType;
    }

    public void setBadgeType(BadgeType badgeType) {
        this.badgeType = badgeType;
    }
}
