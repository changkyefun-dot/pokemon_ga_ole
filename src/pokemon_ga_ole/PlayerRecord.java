package pokemon_ga_ole;

public class PlayerRecord {
    private String username;
    private String password;
    private int score;

    public PlayerRecord() {}

    public PlayerRecord(String username, String password, int score) {
        this.username = username;
        this.password = password;
        this.score = score;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // Display method
    public void displayInfo() {
        System.out.printf("Username: %s | Score: %d\n", username, score);
    }

    // Convert to file format (space-separated)
    public String toFileString() {
        return username + " " + password + " " + score;
    }

    // Parse from file line (expects 3 parts)
    public static PlayerRecord fromFileString(String line) {
        String[] parts = line.trim().split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("❌ Invalid line format in player.txt: " + line);
        }
        return new PlayerRecord(parts[0], parts[1], Integer.parseInt(parts[2]));
    }
}


