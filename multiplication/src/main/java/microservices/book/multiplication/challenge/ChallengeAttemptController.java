package microservices.book.multiplication.challenge;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class provides a REST API to POST the attempts from users.
 */
@RestController
@RequestMapping("/attempts")
public class ChallengeAttemptController {
    private static final Logger log = LoggerFactory.getLogger(ChallengeAttemptController.class);

    private final ChallengeService challengeService;

    public ChallengeAttemptController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @PostMapping
    ResponseEntity<ChallengeAttempt> postResult(@RequestBody @Valid ChallengeAttemptDTO challengeAttemptDTO) {
        log.info("challengeAttemptDTO: {}", challengeAttemptDTO);
        return ResponseEntity.ok(challengeService.verifyAttempt(challengeAttemptDTO));
    }

    @GetMapping
    ResponseEntity<List<ChallengeAttempt>> getStatistics(@RequestParam("alias") String alias) {
        log.info("alias: {}", alias);
        return ResponseEntity.ok(challengeService.getStatsForUser(alias));
    }
}
