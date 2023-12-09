package microservices.book.multiplication.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

/**
 * Stores information to identify the user.
 */
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String alias;

    public User() {
    }

    public User(Long id, String alias) {
        this.id = id;
        this.alias = alias;
    }

    public User(final String userAlias) {
        this(null, userAlias);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(alias, user.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, alias);
    }

    @Override
    public String toString() {
        return String.format("User(id=%s, alias=%s)", id, alias);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
