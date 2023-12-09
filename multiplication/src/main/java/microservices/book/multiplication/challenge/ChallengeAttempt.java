package microservices.book.multiplication.challenge;

import jakarta.persistence.*;
import microservices.book.multiplication.user.User;

import java.util.Objects;

/**
 * Identifies the attempt from a {@link User} to solve a challenge.
 */
@Entity
public class ChallengeAttempt {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    private int factorA;
    private int factorB;
    private int guess;
    private boolean correct;

    public ChallengeAttempt() {
    }

    public ChallengeAttempt(Long id, User user, int factorA, int factorB, int guess, boolean correct) {
        this.id = id;
        this.user = user;
        this.factorA = factorA;
        this.factorB = factorB;
        this.guess = guess;
        this.correct = correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChallengeAttempt attempt = (ChallengeAttempt) o;
        return factorA == attempt.factorA &&
                factorB == attempt.factorB &&
                guess == attempt.guess &&
                correct == attempt.correct &&
                Objects.equals(id, attempt.id) &&
                Objects.equals(user, attempt.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, factorA, factorB, guess, correct);
    }

    @Override
    public String toString() {
        return "ChallengeAttempt{" +
                "id=" + id +
                ", user=" + user +
                ", factorA=" + factorA +
                ", factorB=" + factorB +
                ", guess=" + guess +
                ", correct=" + correct +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getFactorA() {
        return factorA;
    }

    public void setFactorA(int factorA) {
        this.factorA = factorA;
    }

    public int getFactorB() {
        return factorB;
    }

    public void setFactorB(int factorB) {
        this.factorB = factorB;
    }

    public int getGuess() {
        return guess;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
