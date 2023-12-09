package microservices.book.gamification.challenge;

import java.util.Objects;

public class ChallengeSolvedDTO {
    private final long attemptId;
    private final boolean correct;
    private final int factorA;
    private final int factorB;
    private final long userId;
    private final String userAlias;

    public ChallengeSolvedDTO(long attemptId, boolean correct, int factorA, int factorB, long userId, String userAlias) {
        this.attemptId = attemptId;
        this.correct = correct;
        this.factorA = factorA;
        this.factorB = factorB;
        this.userId = userId;
        this.userAlias = userAlias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ChallengeSolvedDTO dto = (ChallengeSolvedDTO) o;
        return attemptId == dto.attemptId &&
                correct == dto.correct &&
                factorA == dto.factorA &&
                factorB == dto.factorB &&
                userId == dto.userId &&
                Objects.equals(userAlias, dto.userAlias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attemptId, correct, factorA, factorB, userId, userAlias);
    }

    @Override
    public String toString() {
        return String.format("ChallengeSolvedDTO(attemptId=%s, correct=%s, factorA=%s, factorB=%s, userId=%s, userAlias=%s)",
                attemptId, correct, factorA, factorB, userId, userAlias);
    }

    public long getAttemptId() {
        return this.attemptId;
    }

    public boolean isCorrect() {
        return this.correct;
    }

    public int getFactorA() {
        return this.factorA;
    }

    public int getFactorB() {
        return this.factorB;
    }

    public long getUserId() {
        return this.userId;
    }

    public String getUserAlias() {
        return this.userAlias;
    }
}
