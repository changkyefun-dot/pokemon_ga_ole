package pokemon_ga_ole;

import java.util.*;

public class Game {
    private ArrayList<Player> players = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private Player currentPlayer;
    private ArrayList<Pokemon> wildPokemonPool = new ArrayList<>();
    private Database database = new Database();

    public Game() {
        initializeWildPokemonPool();
        database.loadDB();
        loadPlayersFromDatabase();
    }

    private void initializeWildPokemonPool() {
        wildPokemonPool.add(new Pokemon("Pikachu", "Electric", 5, 35, 55, 30));
        wildPokemonPool.add(new Pokemon("Charmander", "Fire", 5, 39, 52, 43));
        wildPokemonPool.add(new Pokemon("Bulbasaur", "Grass", 5, 45, 49, 49));
        wildPokemonPool.add(new Pokemon("Squirtle", "Water", 5, 44, 48, 65));
        wildPokemonPool.add(new Pokemon("Magikarp", "Water", 5, 20, 10, 55));
        wildPokemonPool.add(new Pokemon("Pidgey", "Normal", 5, 40, 45, 40));
        wildPokemonPool.add(new Pokemon("Oddish", "Grass", 5, 45, 50, 55));
    }

    private void loadPlayersFromDatabase() {
        for (PlayerRecord record : database.getPlayerRecords()) {
            Player player = new Player(record.getUsername(), record.getPassword());
            player.setScore(record.getScore());
            players.add(player);
        }
    }

    public void start() {
        System.out.println("\n🌟 Welcome to the Pokémon Ga Ole 🌟");

        while (true) {
            System.out.println("\n==============================");
            System.out.println("         🔐 Login Menu         ");
            System.out.println("==============================");
            System.out.println("1️⃣  Login");
            System.out.println("2️⃣  Register");
            System.out.println("3️⃣  Exit");
            System.out.print("\nChoose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    if (login()) mainMenu();
                }
                case 2 -> register();
                case 3 -> {
                    database.storeDB();
                    System.out.println("👋 Exiting game. See you!");
                    return;
                }
                default -> System.out.println("❌ Invalid option.");
            }
        }
    }

    private void register() {
        System.out.print("\nEnter new username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        for (Player p : players) {
            if (p.getUsername().equals(username)) {
                System.out.println("⚠️ Username already exists!");
                return;
            }
        }

        Player newPlayer = new Player(username, password);
        players.add(newPlayer);
        database.addNewPlayerRecord(username, password, 0);
        database.storeDB();

        System.out.println("✅ Registration successful! Welcome, Trainer " + username + "!");
    }

    private boolean login() {
        System.out.print("\nEnter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        for (Player p : players) {
            if (p.getUsername().equals(username) && p.checkPassword(password)) {
                currentPlayer = p;
                System.out.println("🔓 Login successful! Welcome back, Trainer " + username + ".");
                return true;
            }
        }

        System.out.println("❌ Invalid credentials.");
        return false;
    }

    private void mainMenu() {
        while (true) {
            System.out.println("\n==========================");
            System.out.println("        🧭 MAIN MENU       ");
            System.out.println("==========================");
            System.out.println("\nWelcome, Trainer " + currentPlayer.getUsername() + "!");
            System.out.println("🎯 Score: " + currentPlayer.getScore());

            System.out.println("\nPlease choose an option:");
            System.out.println("1️⃣  Catch & Battle (Full Session)");
            System.out.println("2️⃣  Catch Only");
            System.out.println("3️⃣  View Pokémon Collection");
            System.out.println("4️⃣  View Top 5 Scores");
            System.out.println("5️⃣  🛍️ Visit Shop");
            System.out.println("6️⃣  ❌ Exit Game");
            System.out.print("\nYour choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> startFullSession();
                case 2 -> startCatchOnly();
                case 3 -> currentPlayer.viewPokemonCollection();
                case 4 -> viewTopScores();
                case 5 -> openShop();
                case 6 -> {
                    database.storeDB();
                    System.out.println("✅ Goodbye!");
                    return;
                }
                default -> System.out.println("❌ Invalid option.");
            }
        }
    }

    private void startFullSession() {
        System.out.println("\n[🔔] Full Session Start!");

        if (wildPokemonPool.isEmpty()) initializeWildPokemonPool();

        Battle battle = new Battle(currentPlayer);
        Battle.setPool(wildPokemonPool);
        battle.setCatchOnlyMode(true);
        battle.startBattle(currentPlayer.getCollection());

        if (battle.wasPokemonCaught()) {
            Score.updatePlayerScore(currentPlayer, 10);
            database.addOrUpdateScore(currentPlayer.getUsername(), 10);
            System.out.println("\n⚔️ A random trainer appears and challenges you to a duel!");
            startTrainerChallenge();
        }
    }

    private void startCatchOnly() {
        System.out.println("\n[🌟] Catch Only Mode Activated.");

        if (wildPokemonPool.isEmpty()) initializeWildPokemonPool();

        Battle battle = new Battle(currentPlayer);
        Battle.setPool(wildPokemonPool);
        battle.setCatchOnlyMode(true);
        battle.startBattle(currentPlayer.getCollection());

        if (battle.wasPokemonCaught()) {
            Score.updatePlayerScore(currentPlayer, 10);
            database.addOrUpdateScore(currentPlayer.getUsername(), 10);
        }
    }

    private void startTrainerChallenge() {
        currentPlayer.viewPokemonCollection();

        ArrayList<Pokemon> trainerTeam = new ArrayList<>();
        trainerTeam.add(wildPokemonPool.get(new Random().nextInt(wildPokemonPool.size())).clone());

        Player trainer = new Player("Trainer" + new Random().nextInt(1000), "npc");
        for (Pokemon p : trainerTeam) trainer.addPokemon(p);

        Battle trainerBattle = new Battle(currentPlayer);
        Battle.setPool(wildPokemonPool);
        trainerBattle.setCatchOnlyMode(false);
        trainerBattle.startBattle(currentPlayer.getCollection());

        if (trainerBattle.didPlayerWin()) {
            Score.updatePlayerScore(currentPlayer, 10);
            database.addOrUpdateScore(currentPlayer.getUsername(), 10);
            System.out.println("🏆 You gained 10 points for defeating the trainer!");
        }
    }

    private void viewTopScores() {
        database.displayTop5Scores();
    }

    private void openShop() {
        while (true) {
            System.out.println("\n🛒 Welcome to the Shop!\n");
            System.out.println("1️⃣  Buy Standard PokéBall (30% success) - 5 points");
            System.out.println("2️⃣  Buy Ultra PokéBall (80% success) - 10 points");
            System.out.println("3️⃣  Buy Master PokéBall (100% success) - 20 points");
            System.out.println("4️⃣  Buy Miracle Heal - 10 points");
            System.out.println("5️⃣  Buy Miracle Boost - 10 points");
            System.out.println("6️⃣  Buy Miracle Catch - 15 points");
            System.out.println("0️⃣  Exit Shop");
            System.out.print("\nChoose an item to buy: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> purchasePokeBall("Standard Ball", 0.30, 5);
                case 2 -> purchasePokeBall("Ultra Ball", 0.80, 10);
                case 3 -> purchasePokeBall("Master Ball", 1.00, 20);
                case 4 -> purchaseMiracleItem("Miracle Heal", "Full Restore", 10);
                case 5 -> purchaseMiracleItem("Miracle Boost", "+50% Attack for this battle", 10);
                case 6 -> purchaseMiracleItem("Miracle Catch", "Guaranteed Catch", 15);
                case 0 -> {
                    System.out.println("👋 Leaving the shop.");
                    return;
                }
                default -> System.out.println("❌ Invalid choice.");
            }
        }
    }

    private void purchasePokeBall(String name, double successRate, int cost) {
        if (currentPlayer.getScore() >= cost) {
            currentPlayer.addPokeBall(new PokeBall(name, successRate));
            currentPlayer.decreaseScore(cost);
            database.addOrUpdateScore(currentPlayer.getUsername(), -cost);
            System.out.println("✅ " + name + " purchased!");
        } else {
            System.out.println("❌ Not enough points.");
        }
    }

    private void purchaseMiracleItem(String name, String description, int cost) {
        if (currentPlayer.getScore() >= cost) {
            currentPlayer.getMiracleItems().add(new MiracleItem(name, description));
            currentPlayer.decreaseScore(cost);
            database.addOrUpdateScore(currentPlayer.getUsername(), -cost);
            System.out.println("✅ " + name + " purchased!");
        } else {
            System.out.println("❌ Not enough points.");
        }
    }
}



