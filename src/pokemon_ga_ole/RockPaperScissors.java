package pokemon_ga_ole;

import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {

    public static boolean playRockPaperScissors() {
        String[] options = {"rock", "paper", "scissors"};
        Random random = new Random();

        @SuppressWarnings("resource") 
        Scanner userinput = new Scanner(System.in);

        String systemChoice = options[random.nextInt(options.length)];
        String playerChoice;

        while (true) {
            System.out.println("\n🧠 Choose your move:");
            System.out.println("[1] ✊ Rock");
            System.out.println("[2] ✋ Paper");
            System.out.println("[3] ✌️ Scissors");
            System.out.print("👉 Enter your choice (1, 2, or 3): ");

            try {
                int playerNumChoice = Integer.parseInt(userinput.nextLine());
                if (playerNumChoice >= 1 && playerNumChoice <= 3) {
                    playerChoice = options[playerNumChoice - 1];
                    break;
                } else {
                    System.out.println("⚠️ Invalid number. Please enter 1, 2, or 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid input. Please enter a number (1, 2, or 3).");
            }
        }

        System.out.println("\n🧍 You chose: " + formatChoice(playerChoice));
        System.out.println("💻 System chose: " + formatChoice(systemChoice));

        if (playerChoice.equals(systemChoice)) {
            System.out.println("🔁 It's a tie! Let's play again.\n");
            return playRockPaperScissors();
        } else if ((playerChoice.equals("rock") && systemChoice.equals("scissors")) ||
                   (playerChoice.equals("paper") && systemChoice.equals("rock")) ||
                   (playerChoice.equals("scissors") && systemChoice.equals("paper"))) {
            System.out.println("✅ You win! You go first in battle!\n");
            return true;
        } else {
            System.out.println("❌ System wins! The system goes first in battle!\n");
            return false;
        }
    }

    private static String formatChoice(String choice) {
        return switch (choice) {
            case "rock" -> "✊ Rock";
            case "paper" -> "✋ Paper";
            case "scissors" -> "✌️ Scissors";
            default -> choice;
        };
    }

    public static void main(String[] args) {
        playRockPaperScissors();
    }
}
