package microservices.book.multiplication.challenge;

import jakarta.transaction.Transactional;
import microservices.book.multiplication.user.User;
import microservices.book.multiplication.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeServiceImpl implements ChallengeService {
    private static final Logger log = LoggerFactory.getLogger(ChallengeServiceImpl.class);

    private final UserRepository userRepository;
    private final ChallengeAttemptRepository attemptRepository;

    private final ChallengeEventPublisher challengeEventPublisher;

    public ChallengeServiceImpl(UserRepository userRepository, ChallengeAttemptRepository attemptRepository,
                                ChallengeEventPublisher challengeEventPublisher) {
        this.userRepository = userRepository;
        this.attemptRepository = attemptRepository;
        this.challengeEventPublisher = challengeEventPublisher;
    }

    @Transactional
    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attemptDTO) {
        // check if the user already exists for that alias, otherwise create it
        User user = userRepository.findByAlias(attemptDTO.userAlias()).orElseGet(() -> {
            log.info("Creating new user with alias {}", attemptDTO.userAlias());
            return userRepository.save(new User(attemptDTO.userAlias()));
        });
        // check if the attempt is correct
        boolean isCorrect = attemptDTO.guess() == attemptDTO.factorA() * attemptDTO.factorB();
        // Builds the domain object. Null id since it'll be generated by the DB.
        ChallengeAttempt checkedAttempt = new ChallengeAttempt(null, user, attemptDTO.factorA(), attemptDTO.factorB(), attemptDTO.guess(), isCorrect);
        // Stores the attempt
        ChallengeAttempt storedAttempt = attemptRepository.save(checkedAttempt);
        log.info("storedAttempt: {}", storedAttempt);
        // Publishes an event to notify potentially interested subscribers
        challengeEventPublisher.challengeSolved(storedAttempt);
        return storedAttempt;
    }

    @Override
    public List<ChallengeAttempt> getStatsForUser(String userAlias) {
        return attemptRepository.findTop10ByUserAliasOrderByIdDesc(userAlias);
    }

}