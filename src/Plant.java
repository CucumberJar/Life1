import java.io.*;
import java.util.*;

public class Plant extends Species {
    private int regenerationRate;

    public Plant(String name, int population, int regenerationRate) {
        super(name, population, 0, 10);
        this.regenerationRate = regenerationRate;
    }

    @Override
    public void interact(Environment environment, List<Species> speciesList) {
        if (environment.getWater() > 30 && environment.getTemperature() >= 15) {
            this.population += regenerationRate;
        }
        increaseAge();
    }

    @Override
    public String toDataString() {
        return "Plant," + name + "," + population + "," + regenerationRate + "," + age + "," + hunger + "," + thirst;
    }

    public static Plant fromDataString(String data) {
        String[] parts = data.split(",");
        return new Plant(parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
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
                if (species instanceof Plant) {
                    writer.write(species.toDataString());
                    writer.newLine();
                }
            }
        }
    }
}
