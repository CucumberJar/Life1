import java.io.*;
import java.util.List;

public class Environment {
    private int temperature;
    private int humidity;
    private int water;

    public Environment(int temperature, int humidity, int water) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.water = water;
    }

    public int getTemperature() { return temperature; }
    public int getHumidity() { return humidity; }
    public int getWater() { return water; }

    public void updateResources() {
        // Ежедневное изменение ресурсов
        this.water += (int) (Math.random() * 10 - 5); // случайное изменение воды
        this.temperature += (int) (Math.random() * 2 - 1); // небольшое изменение температуры
    }

    public void predictPopulationChanges(List<Species> speciesList) {
        System.out.println("Предсказание изменений популяций...");
        for (Species species : speciesList) {
            if (species instanceof Plant && water < 20) {
                System.out.println(species.name + ": высокая вероятность уменьшения из-за недостатка воды.");
            } else if (species instanceof Herbivore && water < 20) {
                System.out.println(species.name + ": вероятное уменьшение популяции из-за нехватки пищи.");
            } else if (species instanceof Carnivore && water < 10) {
                System.out.println(species.name + ": может сократиться из-за нехватки травоядных.");
            }
        }
    }

    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Temperature=" + temperature);
            writer.newLine();
            writer.write("Humidity=" + humidity);
            writer.newLine();
            writer.write("Water=" + water);
        }
    }

    public static Environment loadFromFile(String filename) throws IOException {
        int temperature = 0, humidity = 0, water = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            temperature = Integer.parseInt(reader.readLine().split("=")[1]);
            humidity = Integer.parseInt(reader.readLine().split("=")[1]);
            water = Integer.parseInt(reader.readLine().split("=")[1]);
        }
        return new Environment(temperature, humidity, water);
    }
}
