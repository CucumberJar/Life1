import java.io.*;
import java.util.*;

public class Carnivore extends Species {

    public Carnivore(String name, int population, int reproductionRate) {
        super(name, population, reproductionRate, 15); // Максимальный возраст для хищников
    }

    @Override
    public void interact(Environment environment, List<Species> speciesList) {
        for (Species species : speciesList) {
            if (species instanceof Herbivore && species.population > 0) {
                int foodAmount = Math.min(species.population, population / 2);
                species.decreasePopulation(foodAmount);
                this.hunger = 0; // Сброс голода после еды
                this.reproduce();
                break;
            }
        }
        increaseHunger(); // Увеличиваем голод
        increaseThirst(); // Увеличиваем жажду
        increaseAge(); // Увеличиваем возраст

        if (!isAlive()) {
            decreasePopulation(population); // Если не жив, уменьшаем популяцию
        }
    }

    @Override
    public String toDataString() {
        return "Carnivore," + name + "," + population + "," + reproductionRate + "," + age + "," + hunger + "," + thirst;
    }

    public static Carnivore fromDataString(String data) {
        String[] parts = data.split(",");
        return new Carnivore(parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
    }


    public static List<Species> loadFromFile(String filename) throws IOException {
        List<Species> speciesList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                speciesList.add(fromDataString(line));
            }
        }
        return speciesList;
    }

    public static void saveToFile(List<Species> speciesList, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Species species : speciesList) {
                if (species instanceof Carnivore) {
                    writer.write(species.toDataString());
                    writer.newLine();
                }
            }
        }
    }
}
