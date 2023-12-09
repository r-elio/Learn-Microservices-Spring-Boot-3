package microservices.book.multiplication.challenge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class implements a REST API to get random challenges
 */
@RestController
@RequestMapping("/challenges")
public class ChallengeController {
    private final Logger log = LoggerFactory.getLogger(ChallengeController.class);

    private final ChallengeGeneratorService challengeGeneratorService;

    public ChallengeController(ChallengeGeneratorService challengeGeneratorService) {
        this.challengeGeneratorService = challengeGeneratorService;
    }

    @GetMapping("/random")
    Challenge getRandomChallenge() {
        Challenge challenge = challengeGeneratorService.randomChallenge();
        log.info("Generating a random challenge: {}", challenge);
        return challenge;
    }

}
