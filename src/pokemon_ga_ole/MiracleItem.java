package pokemon_ga_ole;


public class MiracleItem  {
    private String name;
    private String description;

    public MiracleItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name + " - " + description;
    }
}
