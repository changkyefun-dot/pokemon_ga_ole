package pokemon_ga_ole;

import java.util.List;
public class BattleSystemLogic {
   private List<Pokemon> playerPokemons;
  
   public BattleSystemLogic(List<Pokemon> playerPokemons) {
       this.playerPokemons = playerPokemons;
   }
    public void startBattle(Pokemon enemy) {
       System.out.println("Battle VS against " + enemy.getName() + " (" + enemy.getType() + ")");
      
       System.out.println("⚔️Battle in progress⚔️");
      
       System.out.println("Battle has ended! Hooray!");
      
      
       saveProgress();
   }
  
   public void saveProgress() {
       System.out.println("💾 Saving player progress");
       for (Pokemon p : playerPokemons) {
           System.out.println("Saved: " + p.getName() + " (" + p.getType() + ")");
       }
   }
 
   public void exitGame() {
       System.out.println("🚪 Exiting game now!");
       saveProgress();
       System.out.println("👋 Bye Bye!");
       System.exit(0);
   }
}
