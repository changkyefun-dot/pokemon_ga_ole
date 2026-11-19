package pokemon_ga_ole;

import java.util.Random;
public class PokeBallType {
   public static PokeBall getRandomPokeBall() {
       Random rand = new Random();
       int chance = rand.nextInt(100) + 1; // 1 to 100
       if (chance <= 70) {
           return new PokeBall("Standard Ball", 0.30);
       } else if (chance <= 90) {
           return new PokeBall("Ultra Ball", 0.80);
       } else {
           return new PokeBall("Master Ball", 1.00);
       }
   }
   public static void main(String[] args) {
       PokeBall selected = getRandomPokeBall();
       System.out.println("You got: " + selected);
   }
}

