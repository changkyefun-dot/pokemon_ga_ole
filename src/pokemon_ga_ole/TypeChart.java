package pokemon_ga_ole;

public class TypeChart {
	   public static double getEffectiveness(String attackerType, String defenderType) {
	       return switch (attackerType) {
	           case "Water" -> switch (defenderType) {
	               case "Fire", "Ground" -> 2.0;
	               case "Water", "Grass" -> 0.5;
	               default -> 1.0;
	           };
	           case "Electric" -> switch (defenderType) {
	               case "Water" -> 2.0;
	               case "Electric", "Grass" -> 0.5;
	               case "Ground" -> 0.0;
	               default -> 1.0;
	           };
	           case "Fire" -> switch (defenderType) {
	               case "Grass" -> 2.0;
	               case "Water", "Fire" -> 0.5;
	               default -> 1.0;
	           };
	           case "Grass" -> switch (defenderType) {
	               case "Water", "Ground" -> 2.0;
	               case "Fire", "Grass" -> 0.5;
	               default -> 1.0;
	           };
	           case "Ground" -> switch (defenderType) {
	               case "Electric", "Fire" -> 2.0;
	               case "Grass", "Water" -> 0.5;
	               default -> 1.0;
	           };
	           default -> 1.0;
	       };
	   }
	}

