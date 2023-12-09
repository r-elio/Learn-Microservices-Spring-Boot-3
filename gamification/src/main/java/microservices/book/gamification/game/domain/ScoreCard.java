package microservices.book.gamification.game.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class ScoreCard {
    // The default score assigned to this card, if not specified.
    public static final int DEFAULT_SCORE = 10;
    @Id
    @GeneratedValue
    private Long cardId;
    private Long userId;
    private Long attemptId;
    private long scoreTimestamp;
    private int score;

    public ScoreCard() {
    }

    public ScoreCard(Long cardId, Long userId, Long attemptId, long scoreTimestamp, int score) {
        this.cardId = cardId;
        this.userId = userId;
        this.attemptId = attemptId;
        this.scoreTimestamp = scoreTimestamp;
        this.score = score;
    }

    public ScoreCard(final Long userId, final long attemptId) {
        this(null, userId, attemptId, System.currentTimeMillis(), DEFAULT_SCORE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreCard scoreCard = (ScoreCard) o;
        return score == scoreCard.score &&
                Objects.equals(cardId, scoreCard.cardId) &&
                Objects.equals(userId, scoreCard.userId) &&
                Objects.equals(attemptId, scoreCard.attemptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardId, userId, attemptId, score);
    }

    @Override
    public String toString() {
        return "ScoreCard{" +
                "cardId=" + cardId +
                ", userId=" + userId +
                ", attemptId=" + attemptId +
                ", scoreTimestamp=" + scoreTimestamp +
                ", score=" + score +
                '}';
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }

    public long getScoreTimestamp() {
        return scoreTimestamp;
    }

    public void setScoreTimestamp(long scoreTimestamp) {
        this.scoreTimestamp = scoreTimestamp;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
