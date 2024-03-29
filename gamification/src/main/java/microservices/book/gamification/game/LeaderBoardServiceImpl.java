package microservices.book.gamification.game;

import microservices.book.gamification.game.domain.LeaderBoardRow;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {
    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;

    public LeaderBoardServiceImpl(final ScoreRepository scoreRepository, final BadgeRepository badgeRepository) {
        this.scoreRepository = scoreRepository;
        this.badgeRepository = badgeRepository;
    }

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {
        // Get score only
        List<LeaderBoardRow> scoreOnly = scoreRepository.findFirst10();
        // Combine with badges
        return scoreOnly.stream().map(row -> {
            List<String> badges = badgeRepository.findByUserIdOrderByBadgeTimestampDesc(row.getUserId()).stream()
                    .map(b -> b.getBadgeType().getDescription()).collect(Collectors.toList());
            return row.withBadges(badges);
        }).collect(Collectors.toList());
    }
}
