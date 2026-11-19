package pokemon_ga_ole;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Battle {
    private Pokemon playerPokemon;
    private Pokemon wildPokemon;
    private Random rand = new Random();
    private Scanner input = new Scanner(System.in);
    private boolean catchOnlyMode = false;
    private static ArrayList<Pokemon> pool = new ArrayList<>();
    private Player currentPlayer;
    private boolean boostUsed = false;
    private boolean playerWon = false;

    public Battle(Player player) {
        this.currentPlayer = player;
    }

    public void setCatchOnlyMode(boolean mode) {
        this.catchOnlyMode = mode;
    }

    public static void setPool(ArrayList<Pokemon> pokemonPool) {
        pool = pokemonPool;
    }

    public void startBattle(ArrayList<Pokemon> playerPokemons) {
        System.out.println("\n⭐️⭐️⭐️ BATTLE START ⭐️⭐️⭐️\n");

        if (pool == null || pool.isEmpty()) {
            System.out.println("⚠️ No wild Pokémon available to battle.");
            return;
        }

        if (!catchOnlyMode && (playerPokemons == null || playerPokemons.isEmpty())) {
            System.out.println("⚠️ You have no Pokémon to battle with!");
            return;
        }

        setupBattle(playerPokemons);

        if (!catchOnlyMode) {
            System.out.println("\n🧠 Let's play Rock-Paper-Scissors to decide who attacks first!");
            boolean playerStarts = RockPaperScissors.playRockPaperScissors();
            battleLoop(playerStarts);
            postBattleCatchPhase();
        } else {
            System.out.println("\n🎯 --- CATCH ONLY MODE ---");
            catchPhase();
        }

        System.out.println("\n🌟🌟🌟 BATTLE END 🌟🌟🌟\n");
    }

    private void setupBattle(ArrayList<Pokemon> playerPokemons) {
        for (Pokemon p : playerPokemons) {
            p.setCurrentHP(p.getMaxHP());
        }

        Pokemon selected;
        do {
            selected = pool.get(rand.nextInt(pool.size()));
        } while (playerPokemons.contains(selected));
        wildPokemon = selected.clone();
        wildPokemon.setCurrentHP(wildPokemon.getMaxHP());

        if (!catchOnlyMode) {
            System.out.println("\n📦 Choose your Pokémon:");
            for (int i = 0; i < playerPokemons.size(); i++) {
                System.out.printf("[%d] %s (%s)\n", i + 1,
                        playerPokemons.get(i).getName(),
                        playerPokemons.get(i).getType());
            }

            int choice = -1;
            while (choice < 1 || choice > playerPokemons.size()) {
                System.out.print("🔢 Enter your choice: ");
                if (input.hasNextInt()) {
                    choice = input.nextInt();
                } else {
                    input.next();
                }
            }

            playerPokemon = playerPokemons.get(choice - 1);
        }

        System.out.printf("\n🌿 A wild %s (%s) appeared!\n\n",
                wildPokemon.getName(), wildPokemon.getType());
    }

    private void battleLoop(boolean playerStarts) {
        while (playerPokemon.getCurrentHP() > 0 && wildPokemon.getCurrentHP() > 0) {
            if (playerStarts) {
                playerTurn();
                if (wildPokemon.getCurrentHP() <= 0) break;
                wildAttack();
            } else {
                wildAttack();
                if (playerPokemon.getCurrentHP() <= 0) break;
                playerTurn();
            }
        }

        if (playerPokemon.getCurrentHP() > 0) {
            System.out.println("\n🏆 🎉 You won the battle!");
            playerWon = true;
        } else {
            System.out.println("\n💀 You lost the battle...");
        }
    }

    private void playerTurn() {
        System.out.printf("\n⚔️ Your %s's turn!\n", playerPokemon.getName());
        System.out.println("[1] Attack ⚔️\n[2] Use Miracle Item ✨\n[3] Attempt Capture 🎯\n[4] View Stats 📊");

        int choice = -1;
        while (choice < 1 || choice > 4) {
            System.out.print("👉 Choose an action: ");
            if (input.hasNextInt()) {
                choice = input.nextInt();
            } else {
                input.next();
            }
        }

        switch (choice) {
            case 1 -> playerAttack();
            case 2 -> useMiracleItem();
            case 3 -> catchPhase();
            case 4 -> {
                displayStatus();
                playerTurn();
            }
        }
    }

    private void displayStatus() {
        System.out.printf("📊 Your %s HP: %d/%d\n",
                playerPokemon.getName(), playerPokemon.getCurrentHP(), playerPokemon.getMaxHP());
        System.out.printf("📊 Wild %s HP: %d/%d\n",
                wildPokemon.getName(), wildPokemon.getCurrentHP(), wildPokemon.getMaxHP());
    }

    private void playerAttack() {
        int attack = playerPokemon.getAttack();
        if (boostUsed) attack *= 1.5;

        int damage = calculateDamage(playerPokemon, wildPokemon, attack);
        wildPokemon.takeDamage(damage);

        double effectiveness = TypeChart.getEffectiveness(
                playerPokemon.getType(), wildPokemon.getType());

        System.out.printf("\n💥 %s used an attack! (Effectiveness: %s)\n",
                playerPokemon.getName(), formatEffectiveness(effectiveness));
        System.out.printf("💥 %s dealt %d damage to %s!\n",
                playerPokemon.getName(), damage, wildPokemon.getName());
    }

    private void wildAttack() {
        int damage = calculateDamage(wildPokemon, playerPokemon, wildPokemon.getAttack());
        playerPokemon.takeDamage(damage);

        double effectiveness = TypeChart.getEffectiveness(
                wildPokemon.getType(), playerPokemon.getType());

        System.out.printf("\n⚠️ %s attacked! (Effectiveness: %s)\n",
                wildPokemon.getName(), formatEffectiveness(effectiveness));
        System.out.printf("⚠️ %s dealt %d damage to your %s!\n",
                wildPokemon.getName(), damage, playerPokemon.getName());
    }

    private int calculateDamage(Pokemon attacker, Pokemon defender, int attackStat) {
        double effectiveness = TypeChart.getEffectiveness(
                attacker.getType(), defender.getType());
        int damage = (int) ((attackStat * effectiveness) - (defender.getDefense() / 2));
        return Math.max(1, damage);
    }

    private String formatEffectiveness(double effectiveness) {
        if (effectiveness == 2.0) return "🔥 Super Effective";
        if (effectiveness == 0.5) return "💧 Not Very Effective";
        if (effectiveness == 0.0) return "❌ No Effect";
        return "✅ Normal";
    }

    private void postBattleCatchPhase() {
        if (playerPokemon.getCurrentHP() > 0) {
            System.out.println("\n🎯 --- CATCH PHASE ---");
            catchPhase();
        } else {
            System.out.println("❌ You can't catch Pokémon after losing the battle.");
        }
    }

    private void catchPhase() {
        if (currentPlayer.getPokeBalls().isEmpty()) {
            System.out.println("\n❌ You have no PokéBalls left to catch a Pokémon!");
            return;
        }

        currentPlayer.viewPokeBalls();
        System.out.print("🎯 Choose a PokéBall to use (0 to cancel): ");
        int choice = input.nextInt();
        if (choice == 0) return;

        if (choice < 1 || choice > currentPlayer.getPokeBalls().size()) {
            System.out.println("⚠️ Invalid choice.");
            return;
        }

        PokeBall selectedBall = currentPlayer.getPokeBalls().get(choice - 1);
        currentPlayer.removePokeBall(choice - 1);

        System.out.printf("\n📦 Attempting to catch %s with %s...\n", wildPokemon.getName(), selectedBall.getName());
        boolean miracleCatch = false;

        for (MiracleItem item : currentPlayer.getMiracleItems()) {
            if (item.getName().equals("Miracle Catch")) {
                miracleCatch = true;
                currentPlayer.getMiracleItems().remove(item);
                break;
            }
        }

        boolean caught = miracleCatch || rand.nextDouble() < selectedBall.getSuccessRate();

        if (caught) {
            System.out.printf("🎉 Congratulations! You caught %s!\n", wildPokemon.getName());
            wildPokemon.setCaptured(true);
            currentPlayer.addPokemon(wildPokemon);
            currentPlayer.incrementScore(10);
            System.out.println("📥 " + wildPokemon.getName() + " has been added to your collection!");
        } else {
            System.out.printf("💨 Oh no! %s broke free!\n", wildPokemon.getName());
        }
    }

    private void useMiracleItem() {
        currentPlayer.viewMiracleItems();
        System.out.print("✨ Choose item number to use (0 to cancel): ");
        int choice = input.nextInt();
        if (choice == 0) return;

        if (choice > 0 && choice <= currentPlayer.getMiracleItems().size()) {
            MiracleItem item = currentPlayer.getMiracleItems().get(choice - 1);

            switch (item.getName()) {
                case "Miracle Heal" -> {
                    playerPokemon.setCurrentHP(playerPokemon.getMaxHP());
                    System.out.println("❤️ Your Pokémon has been fully healed!");
                }
                case "Miracle Boost" -> {
                    boostUsed = true;
                    System.out.println("💪 Your Pokémon's attack is boosted this battle!");
                }
                case "Miracle Catch" -> {
                    System.out.println("🎯 This will be used automatically on the next catch attempt.");
                    return;
                }
            }
            currentPlayer.removeMiracleItem(choice - 1);
        } else {
            System.out.println("⚠️ Invalid choice.");
        }
    }

    public boolean wasPokemonCaught() {
        return wildPokemon != null && wildPokemon.isCaptured();
    }

    public boolean didPlayerWin() {
        return playerWon;
    }
}



