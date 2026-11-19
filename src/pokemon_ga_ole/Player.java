package pokemon_ga_ole;

import java.util.ArrayList;

public class Player {
    private static int nextId = 1;

    private int playerId;
    private String username;
    private String password;
    private ArrayList<Pokemon> collection;
    private ArrayList<MiracleItem> miracleItems;
    private ArrayList<PokeBall> pokeBalls;
    private int score;

    public Player(String username, String password) {
        this.playerId = nextId++;
        this.username = username;
        this.password = password;
        this.collection = new ArrayList<>();
        this.miracleItems = new ArrayList<>();
        this.pokeBalls = new ArrayList<>();
        this.score = 0;

        initializeDefaultItems();
        initializeDefaultPokeBalls();
    }

    private void initializeDefaultItems() {
        miracleItems.add(new MiracleItem("Miracle Heal", "Full Restore"));
        miracleItems.add(new MiracleItem("Miracle Catch", "Guaranteed Catch"));
        miracleItems.add(new MiracleItem("Miracle Boost", "+50% Attack for this battle"));
    }

    private void initializeDefaultPokeBalls() {
        for (int i = 0; i < 3; i++) {
            pokeBalls.add(PokeBallType.getRandomPokeBall());
        }
    }

    // 🧑‍💼 Player Info
    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    public String getPassword() {
        return password;  
    }

    public int getPlayerId() {
        return playerId;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int next) {
        nextId = next;
    }

    // 📦 Pokémon Collection
    public ArrayList<Pokemon> getCollection() {
        return collection;
    }

    public void addPokemon(Pokemon pokemon) {
        collection.add(pokemon);
    }

    public void viewPokemonCollection() {
        if (collection.isEmpty()) {
            System.out.println("\n📦 Your Pokémon collection is empty.");
        } else {
            System.out.println("\n📦 Your Pokémon Collection:");
            for (int i = 0; i < collection.size(); i++) {
                Pokemon p = collection.get(i);
                System.out.printf("[%d] %s (%s) HP: %d/%d\n",
                        i + 1, p.getName(), p.getType(), p.getCurrentHP(), p.getMaxHP());
            }
        }
    }

    // 🧪 Miracle Items
    public ArrayList<MiracleItem> getMiracleItems() {
        return miracleItems;
    }

    public void removeMiracleItem(int index) {
        if (index >= 0 && index < miracleItems.size()) {
            miracleItems.remove(index);
        }
    }

    public void viewMiracleItems() {
        if (miracleItems.isEmpty()) {
            System.out.println("\n🧪 You have no miracle items.");
        } else {
            System.out.println("\n🧪 Your Miracle Items:");
            for (int i = 0; i < miracleItems.size(); i++) {
                System.out.printf("[%d] %s\n", i + 1, miracleItems.get(i));
            }
        }
    }

    // 🎯 PokéBalls
    public ArrayList<PokeBall> getPokeBalls() {
        return pokeBalls;
    }

    public void addPokeBall(PokeBall ball) {
        pokeBalls.add(ball);
    }

    public void removePokeBall(int index) {
        if (index >= 0 && index < pokeBalls.size()) {
            pokeBalls.remove(index);
        }
    }

    public void viewPokeBalls() {
        if (pokeBalls.isEmpty()) {
            System.out.println("\n🎯 You have no PokéBalls left.");
        } else {
            System.out.println("\n🎯 Your PokéBalls:\n");
            for (int i = 0; i < pokeBalls.size(); i++) {
                System.out.printf("[%d] %s\n", i + 1, pokeBalls.get(i));
            }
            System.out.println();
        }
    }

    // 🏆 Scoring
    public int getScore() {
        return score;
    }

    public void incrementScore(int amount) {
        score += amount;
    }
    
    public void setScore(int score) {
        this.score = score;
    }

    public void decreaseScore(int cost) {
        if (score >= cost) {
            score -= cost;
        } else {
            System.out.println("⚠️ Not enough score to complete this purchase.");
        }
    }
}



