package microservices.book.gamification.game.domain;

/**
 * Enumeration with the different types of Badges that a user can win.
 */
public enum BadgeType {
    // Badges depending on score
    BRONZE("Bronze"),
    SILVER("Silver"),
    GOLD("Gold"),
    // Other badges won for different conditions
    FIRST_WON("First time"),
    LUCKY_NUMBER("Lucky Number");
    private final String description;

    BadgeType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
