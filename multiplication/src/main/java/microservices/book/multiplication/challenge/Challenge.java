package microservices.book.multiplication.challenge;

import java.util.Objects;

/**
 * This class represents a Challenge to solve a Multiplication (a * b)
 */
public class Challenge {
    private final int factorA;
    private final int factorB;

    public Challenge(int factorA, int factorB) {
        this.factorA = factorA;
        this.factorB = factorB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Challenge challenge = (Challenge) o;
        return factorA == challenge.factorA &&
                factorB == challenge.factorB;
    }

    @Override
    public int hashCode() {
        return Objects.hash(factorA, factorB);
    }

    @Override
    public String toString() {
        return String.format("Challenge(factorA=%s, factorB=%s)", factorA, factorB);
    }

    public int getFactorA() {
        return factorA;
    }

    public int getFactorB() {
        return factorB;
    }
}
