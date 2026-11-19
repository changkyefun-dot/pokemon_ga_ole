package pokemon_ga_ole;

public class PokeBall  {
    private String name;
    private double successRate;

    public PokeBall(String name, double successRate) {
        this.name = name;
        this.successRate = successRate;
    }

    public String getName() {
        return name;
    }

    public double getSuccessRate() {
        return successRate;
    }

    @Override
    public String toString() {
        return name + " (Success Rate: " + (int)(successRate * 100) + "%)";
    }
}


