package pokemon_ga_ole;

public class Score {

    // 🧮 Basic score calculation with optional bonus multiplier
    public static int calculateScore(int baseScore, int bonusMultiplier) {
        return baseScore + (baseScore * bonusMultiplier / 100);
    }

    // 🏆 Update a player's score with calculated value (with bonus)
    public static void updatePlayerScore(Player player, int baseScore, int bonusMultiplier) {
        int earnedScore = calculateScore(baseScore, bonusMultiplier);
        player.incrementScore(earnedScore);

        System.out.printf("✅ %s earned %d points (🧮 Base: %d + Bonus: %d%%). 🏆 Total Score: %d\n",
                player.getUsername(), earnedScore, baseScore, bonusMultiplier, player.getScore());
    }

    // 🏆 Simple score update without bonus
    public static void updatePlayerScore(Player player, int baseScore) {
        player.incrementScore(baseScore);

        System.out.printf("✅ %s earned %d points. 🏆 Total Score: %d\n",
                player.getUsername(), baseScore, player.getScore());
    }
}

