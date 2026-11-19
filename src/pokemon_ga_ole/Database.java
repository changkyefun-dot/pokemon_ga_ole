package pokemon_ga_ole;

import java.io.*;
import java.util.*;

public class Database {
    private Scanner inputScanner;
    private Formatter outputFormatter;
    private ArrayList<PlayerRecord> playerRecords = new ArrayList<>();

    // Load player records from file
    public void loadDB() {
        inputScanner = openInputFile("resources/player.txt");
        readPlayerFile();
        closeInputFile();
    }

    // Save player records to file
    public void storeDB() {
        outputFormatter = openOutputFile("resources/player.txt");
        writePlayerFile();
        closeOutputFile();
    }

    private Scanner openInputFile(String filename) {
        try {
            return new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.err.println("❌ Unable to open file for reading.");
            return null;
        }
    }

    private Formatter openOutputFile(String filename) {
        try {
            return new Formatter(new File(filename));
        } catch (FileNotFoundException e) {
            System.err.println("❌ Unable to open file for writing.");
            return null;
        }
    }

    private void readPlayerFile() {
        if (inputScanner == null) return;

        while (inputScanner.hasNextLine()) {
            String line = inputScanner.nextLine();
            try {
                PlayerRecord record = PlayerRecord.fromFileString(line);
                playerRecords.add(record);
            } catch (IllegalArgumentException e) {
                System.err.println("⚠️ Skipping invalid line: \"" + line + "\"");
            }
        }
    }

    private void writePlayerFile() {
        if (outputFormatter == null) return;

        for (PlayerRecord record : playerRecords) {
            outputFormatter.format("%s\n", record.toFileString());
        }
    }

    private void closeInputFile() {
        if (inputScanner != null) inputScanner.close();
    }

    private void closeOutputFile() {
        if (outputFormatter != null) outputFormatter.close();
    }

    // ✅ Add or update a player's score (add delta points)
    public void addOrUpdateScore(String username, int scoreToAdd) {
        for (PlayerRecord record : playerRecords) {
            if (record.getUsername().equalsIgnoreCase(username)) {
                record.setScore(record.getScore() + scoreToAdd);
                return;
            }
        }
        // If not found, do nothing
    }

    // ✅ Add a new player record
    public void addNewPlayerRecord(String username, String password, int score) {
        playerRecords.add(new PlayerRecord(username, password, score));
    }

    // ✅ Set exact score (overwrite previous score) — BEST for syncing at logout
    public void setExactScore(String username, int finalScore) {
        for (PlayerRecord record : playerRecords) {
            if (record.getUsername().equalsIgnoreCase(username)) {
                record.setScore(finalScore);
                return;
            }
        }
    }

    // ✅ Get score (optional)
    public int getScoreForUser(String username) {
        for (PlayerRecord record : playerRecords) {
            if (record.getUsername().equalsIgnoreCase(username)) {
                return record.getScore();
            }
        }
        return 0;
    }

    // ✅ Validate login
    public boolean validateLogin(String username, String password) {
        for (PlayerRecord record : playerRecords) {
            if (record.getUsername().equals(username) && record.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // ✅ Display top 5 scores
    public void displayTop5Scores() {
        playerRecords.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

        System.out.println("\n🏆 Top 5 Players:");
        for (int i = 0; i < Math.min(5, playerRecords.size()); i++) {
            PlayerRecord r = playerRecords.get(i);
            System.out.printf("%d. %s - %d points\n", i + 1, r.getUsername(), r.getScore());
        }
    }

    public ArrayList<PlayerRecord> getPlayerRecords() {
        return playerRecords;
    }
}


