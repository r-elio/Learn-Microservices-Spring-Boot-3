package microservices.book.gamification.game;

import microservices.book.gamification.challenge.ChallengeSolvedEvent;
import microservices.book.gamification.game.GameService.GameResult;
import microservices.book.gamification.game.badgeprocessors.BadgeProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    private GameService gameService;
    @Mock
    private ScoreRepository scoreRepository;
    @Mock
    private BadgeRepository badgeRepository;
    @Mock
    private List<BadgeProcessor> badgeProcessors;

    @BeforeEach
    public void setUp() {
        gameService = new GameServiceImpl(scoreRepository, badgeRepository, badgeProcessors);
    }

    @Test
    public void checkCorrectAttemptTest() {

    }

    @Test
    public void checkIncorrectAttemptTest() {
        // given
        ChallengeSolvedEvent attemptEvent = new ChallengeSolvedEvent(1L, false, 50, 60, 1L, "test");
        // when
        GameResult resultAttempt = gameService.newAttemptForUser(attemptEvent);
        // then
        then(resultAttempt.score()).isEqualTo(0);
        then(resultAttempt.badges()).isEmpty();
    }
}
