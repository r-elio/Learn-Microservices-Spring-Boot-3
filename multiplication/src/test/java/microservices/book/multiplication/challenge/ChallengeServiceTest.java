package microservices.book.multiplication.challenge;

import microservices.book.multiplication.user.User;
import microservices.book.multiplication.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ChallengeServiceTest {
    private ChallengeService challengeService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ChallengeAttemptRepository attemptRepository;
    @Mock
    private ChallengeEventPublisher challengeEventPublisher;

    @BeforeEach
    public void setUp() {
        challengeService = new ChallengeServiceImpl(userRepository, attemptRepository, challengeEventPublisher);
    }

    @Test
    public void checkCorrectAttemptTest() {
        // given
        String alias = "john_doe";
        given(attemptRepository.save(any())).will(returnsFirstArg());
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, alias, 3000);
        // when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);
        // then
        then(resultAttempt.isCorrect()).isTrue();
        verify(userRepository).save(new User(alias));
        verify(attemptRepository).save(resultAttempt);
        verify(challengeEventPublisher).challengeSolved(resultAttempt);
    }

    @Test
    public void checkWrongAttemptTest() {
        // given
        String alias = "john_doe";
        given(attemptRepository.save(any())).will(returnsFirstArg());
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, alias, 5000);
        // when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);
        // then
        then(resultAttempt.isCorrect()).isFalse();
        verify(userRepository).save(new User(alias));
        verify(attemptRepository).save(resultAttempt);
        verify(challengeEventPublisher).challengeSolved(resultAttempt);
    }

    @Test
    public void checkExistingUserTest() {
        // given
        String alias = "john_doe";
        given(attemptRepository.save(any())).will(returnsFirstArg());
        User existingUser = new User(1L, alias);
        given(userRepository.findByAlias(alias)).willReturn(Optional.of(existingUser));
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, alias, 5000);
        // when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);
        // then
        then(resultAttempt.isCorrect()).isFalse();
        then(resultAttempt.getUser()).isEqualTo(existingUser);
        verify(userRepository, never()).save(any());
        verify(attemptRepository).save(resultAttempt);
        verify(challengeEventPublisher).challengeSolved(resultAttempt);
    }

    @Test
    public void retrieveStatsTest() {
        // given
        String alias = "john_doe";
        User user = new User(alias);
        ChallengeAttempt attempt1 = new ChallengeAttempt(1L, user, 50, 60, 3010, false);
        ChallengeAttempt attempt2 = new ChallengeAttempt(2L, user, 50, 60, 3051, false);
        List<ChallengeAttempt> lastAttempts = List.of(attempt1, attempt2);
        given(attemptRepository.findTop10ByUserAliasOrderByIdDesc(alias)).willReturn(lastAttempts);
        // when
        List<ChallengeAttempt> latestAttemptsResult = challengeService.getStatsForUser(alias);
        // then
        then(latestAttemptsResult).isEqualTo(lastAttempts);
    }
}
