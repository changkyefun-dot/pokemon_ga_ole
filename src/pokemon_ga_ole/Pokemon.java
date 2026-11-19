package pokemon_ga_ole;

import java.util.Objects;

public class Pokemon {
    private String name;
    private String type;
    private int level;
    private int maxHP;
    private int currentHP;
    private int attack;
    private int defense;
    private boolean captured;

    public Pokemon(String name, String type, int level, int maxHP, int attack, int defense) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.captured = false;
    }

    // 🌟 Getters
    public String getName() { return name; }
    public String getType() { return type; }
    public int getLevel() { return level; }
    public int getMaxHP() { return maxHP; }
    public int getCurrentHP() { return currentHP; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public boolean isCaptured() { return captured; }

    // 🛠️ Setters
    public void setCurrentHP(int hp) {
        this.currentHP = Math.max(0, Math.min(hp, maxHP));
    }

    public void setCaptured(boolean status) {
        this.captured = status;
    }

    // 🎯 Damage Handler
    public void takeDamage(int damage) {
        currentHP = Math.max(0, currentHP - damage);
        System.out.println("\n🎯 " + name + " took " + damage + " damage!");
        System.out.println("❤️ Current HP: " + currentHP + "/" + maxHP);
    }

    // ⬆️ Level Up
    public void levelUp() {
        level++;
        maxHP += 5;
        attack += 3;
        defense += 2;
        currentHP = maxHP;
        System.out.println("\n⬆️ " + name + " leveled up to level " + level + "!");
        System.out.println("✨ Stats increased!");
    }

    // 📊 Stat Viewer
    public void showStats() {
        System.out.printf(
            "\n📊 %s (%s) - Lv.%d\n❤️ HP: %d/%d  ⚔️ ATK: %d  🛡️ DEF: %d\n",
            name, type, level, currentHP, maxHP, attack, defense
        );
    }

    // 🧬 Clone Method
    public Pokemon clone() {
        Pokemon clone = new Pokemon(name, type, level, maxHP, attack, defense);
        clone.setCurrentHP(currentHP);
        clone.setCaptured(captured);
        return clone;
    }

    // 🔁 Equality Check
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pokemon other = (Pokemon) obj;
        return this.name.equals(other.name) && this.type.equals(other.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}



