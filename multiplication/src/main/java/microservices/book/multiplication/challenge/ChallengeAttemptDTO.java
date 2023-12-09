package microservices.book.multiplication.challenge;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;

/**
 * Attempt coming from the user
 */
public class ChallengeAttemptDTO {
    @Min(1) @Max(99)
    int factorA, factorB;
    @NotBlank
    String userAlias;
    @PositiveOrZero
    int guess;

    public ChallengeAttemptDTO(int factorA, int factorB, String userAlias, int guess) {
        this.factorA = factorA;
        this.factorB = factorB;
        this.userAlias = userAlias;
        this.guess = guess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ChallengeAttemptDTO dto = (ChallengeAttemptDTO) o;
        return factorA == dto.factorA &&
                factorB == dto.factorB &&
                Objects.equals(userAlias, dto.userAlias) &&
                guess == dto.guess;
    }

    @Override
    public int hashCode() {
        return Objects.hash(factorA, factorB, userAlias, guess);
    }

    @Override
    public String toString() {
        return String.format("ChallengeAttemptDTO(factorA=%s, factorB=%s, userAlias=%s, guess=%s)",
                factorA, factorB, userAlias, guess);
    }

    public int getFactorA() {
        return factorA;
    }

    public int getFactorB() {
        return factorB;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public int getGuess() {
        return guess;
    }
}
