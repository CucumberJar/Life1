import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добро пожаловать в расширенный симулятор экосистемы!");
        System.out.println("1. Создать новую симуляцию");
        System.out.println("2. Загрузить существующую симуляцию");
        int choice = scanner.nextInt();

        try {
            if (choice == 1) {
                simulation.createNewSimulation();
            } else if (choice == 2) {
                simulation.loadSimulation();
            }

            simulation.run();
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом: " + e.getMessage());
        }
    }
}
