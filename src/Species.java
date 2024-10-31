import java.util.List;

public abstract class Species {
    protected String name;
    protected int population;
    protected int reproductionRate;
    protected int age; // Возраст
    protected int hunger; // Уровень голода
    protected int thirst; // Уровень жажды
    protected int maxAge; // Максимальный возраст

    public Species(String name, int population, int reproductionRate, int maxAge) {
        this.name = name;
        this.population = population;
        this.reproductionRate = reproductionRate;
        this.age = 0;
        this.hunger = 0;
        this.thirst = 0;
        this.maxAge = maxAge;
    }

    public abstract void interact(Environment environment, List<Species> speciesList);

    public void reproduce() { this.population += reproductionRate; }

    public void increaseAge() { this.age++; }

    public void increaseHunger() { this.hunger++; }

    public void increaseThirst() { this.thirst++; }

    public void decreasePopulation(int amount) { this.population = Math.max(0, this.population - amount); }

    public boolean isAlive() {
        return this.age < maxAge && this.hunger < 10 && this.thirst < 10 && this.population > 0;
    }

    public abstract String toDataString();
}
