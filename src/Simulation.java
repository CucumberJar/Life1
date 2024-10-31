import java.io.*;
import java.util.*;

public class Simulation {
    private List<Species> speciesList = new ArrayList<>();
    private Environment environment;
    private String simulationName;  // уникальное имя для каждой симуляции

    public void createNewSimulation() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название новой симуляции:");
        simulationName = scanner.nextLine();

        System.out.println("Введите начальные параметры экосистемы:");
        System.out.print("Температура: ");
        int temperature = scanner.nextInt();
        System.out.print("Влажность: ");
        int humidity = scanner.nextInt();
        System.out.print("Количество воды: ");
        int water = scanner.nextInt();
        environment = new Environment(temperature, humidity, water);

        boolean addingSpecies = true;
        while (addingSpecies) {
            System.out.println("Добавьте обитателя:");
            System.out.print("1 - Растение, 2 - Травоядное, 3 - Хищник: ");
            int type = scanner.nextInt();
            scanner.nextLine();  // очистка буфера

            System.out.print("Название: ");
            String name = scanner.nextLine();
            System.out.print("Начальная популяция: ");
            int population = scanner.nextInt();
            System.out.print("Темп восстановления (или размножения): ");
            int rate = scanner.nextInt();

            switch (type) {
                case 1 -> speciesList.add(new Plant(name, population, rate));
                case 2 -> speciesList.add(new Herbivore(name, population, rate));
                case 3 -> speciesList.add(new Carnivore(name, population, rate));
                default -> System.out.println("Неверный тип.");
            }

            System.out.print("Добавить ещё одного обитателя? (да - 1, нет - 0): ");
            addingSpecies = scanner.nextInt() == 1;
        }
        saveSimulation();
    }

    public void loadSimulation() throws IOException {
        File dataDir = new File("data/");
        File[] simulationDirs = dataDir.listFiles(File::isDirectory);

        if (simulationDirs == null || simulationDirs.length == 0) {
            System.out.println("Нет доступных сохранённых симуляций.");
            return;
        }

        System.out.println("Доступные симуляции:");
        for (int i = 0; i < simulationDirs.length; i++) {
            System.out.println((i + 1) + ". " + simulationDirs[i].getName());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Выберите симуляцию для загрузки (введите номер): ");
        int choice = scanner.nextInt();
        if (choice < 1 || choice > simulationDirs.length) {
            System.out.println("Неверный выбор.");
            return;
        }

        simulationName = simulationDirs[choice - 1].getName();
        environment = Environment.loadFromFile("data/" + simulationName + "/environment.txt");
        speciesList = loadSpecies("data/" + simulationName + "/plants.txt", "data/" + simulationName + "/herbivores.txt","data/" + simulationName + "/carnivores.txt");
    }


    public void run() {
        try {
            // Загружаем состояние симуляции
            loadSimulation();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Меню:");
                System.out.println("1. Сделать один ход");
                System.out.println("2. Сохранить симуляцию");
                System.out.println("3. Выход");
                System.out.print("Выберите опцию: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        step();
                        break;
                    case 2:
                        saveSimulation();
                        System.out.println("Симуляция сохранена.");
                        break;
                    case 3:
                        System.out.println("Выход из программы.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void step() {
        System.out.println("Симуляция одного шага:");
        environment.updateResources(); // Обновляем ресурсы

        for (Species species : speciesList) {
            species.interact(environment, speciesList);
            System.out.println(species.name + " - Популяция: " + species.population + ", Возраст: " + species.age + ", Голод: " + species.hunger + ", Жажда: " + species.thirst);
        }

        // Дополнительная информация о ресурсах окружения
        System.out.println("Ресурсы окружения обновлены: Вода - " + environment.getWater() + ", Температура - " + environment.getTemperature());
    }

        private List<Species> loadSpecies(String plantsFile, String herbivoresFile, String carnivoresFile) throws IOException {
            List<Species> species = new ArrayList<>();
            species.addAll(Plant.loadFromFile(plantsFile));            // Загружаем растения
            species.addAll(Herbivore.loadFromFile(herbivoresFile));     // Загружаем травоядных
            species.addAll(Carnivore.loadFromFile(carnivoresFile));     // Загружаем хищников
            return species;
        }

        private void saveSimulation() throws IOException {
            File simulationDir = new File("data/" + simulationName);
            if (!simulationDir.exists()) {
                simulationDir.mkdirs();
            }

            environment.saveToFile("data/" + simulationName + "/environment.txt");
            Plant.saveToFile(speciesList, "data/" + simulationName + "/plants.txt");
            Herbivore.saveToFile(speciesList, "data/" + simulationName + "/herbivores.txt");
            Carnivore.saveToFile(speciesList, "data/" + simulationName + "/carnivores.txt");
        }
    }


