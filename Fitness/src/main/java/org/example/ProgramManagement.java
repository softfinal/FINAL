package fitt;

import java.io.*;
import java.util.*;

// فئة العميل (Client)
class Client {
    int id;
    String name;
    int age;
    String fitnessGoals;
    String dietaryPreferences;
    String accountStatus;

    public Client(int id, String name, int age, String fitnessGoals, String dietaryPreferences) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.fitnessGoals = fitnessGoals;
        this.dietaryPreferences = dietaryPreferences;
        this.accountStatus = "Pending";
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getFitnessGoals() {
        return fitnessGoals;
    }

    public String getDietaryPreferences() {
        return dietaryPreferences;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String status) {
        this.accountStatus = status;
    }

    public void updateProfile(String newName, int newAge, String newGoals, String newPreferences) {
        this.name = newName;
        this.age = newAge;
        this.fitnessGoals = newGoals;
        this.dietaryPreferences = newPreferences;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + age + "," + fitnessGoals + "," + dietaryPreferences + "," + accountStatus;
    }
}

// فئة إدارة العملاء (ClientService)
class ClientService {
    private Map<Integer, Client> clients = new HashMap<>();
    private int clientCounter = 1;

    public int createClient(String name, int age, String fitnessGoals, String dietaryPreferences) {
        if (name == null || name.isEmpty() || age <= 0 || fitnessGoals == null || dietaryPreferences == null) {
            throw new IllegalArgumentException("Invalid client data");
        }
        Client newClient = new Client(clientCounter, name, age, fitnessGoals, dietaryPreferences);
        clients.put(clientCounter, newClient);
        return clientCounter++;
    }

    public Client getClientById(Object id) {
        if (id == null) return null;
        return clients.get(id);
    }

    public void updateClient(int clientId, String name, int age, String fitnessGoals, String dietaryPreferences) {
        Client client = clients.get(clientId);
        if (client != null) {
            client.updateProfile(name, age, fitnessGoals, dietaryPreferences);
        }
    }

    public void deleteClient(Object id) {
        clients.remove(id);
    }

    public void removeClient(Client client) {
        if (client != null) {
            clients.remove(client.getId());
        }
    }

    public Collection<Client> listAllClients() {
        return clients.values();
    }
}

// فئة إدارة الملفات (FileService)
class FileService {

    public void saveToFile(String fileName, Collection<Client> clients) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Client client : clients) {
                writer.write(client.toString());
                writer.newLine();
            }
        }
    }

    public List<Client> loadFromFile(String fileName) throws IOException {
        List<Client> clients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] data = line.split(",");
                    if (data.length == 6) {
                        Client client = new Client(
                                Integer.parseInt(data[0]),
                                data[1],
                                Integer.parseInt(data[2]),
                                data[3],
                                data[4]
                        );
                        client.setAccountStatus(data[5]);
                        clients.add(client);
                    }
                } catch (Exception e) {
                    System.err.println("Error reading line: " + line + " - " + e.getMessage());
                }
            }
        }
        return clients;
    }
}

// فئة الإدارة العامة (ProgramManagement)
public class ProgramManagement {
    private ClientService clientService = new ClientService();
    private FileService fileService = new FileService();

    public void run() throws IOException {
        // إنشاء عملاء تجريبيين
        int clientId1 = clientService.createClient("John Doe", 25, "Lose Weight", "Vegetarian");
        int clientId2 = clientService.createClient("Jane Smith", 30, "Build Muscle", "Vegan");

        // عرض قائمة العملاء
        System.out.println("All Clients:");
        for (Client client : clientService.listAllClients()) {
            System.out.println(client);
        }

        // تحديث بيانات عميل
        clientService.updateClient(clientId1, "John Doe", 26, "Build Muscle", "Gluten-Free");
        System.out.println("\nUpdated Client:");
        System.out.println(clientService.getClientById(clientId1));

        // حذف عميل
        clientService.deleteClient(clientId2);
        System.out.println("\nClient Deleted. Remaining Clients:");
        for (Client client : clientService.listAllClients()) {
            System.out.println(client);
        }

        // حفظ البيانات إلى ملف
        fileService.saveToFile("clients.txt", clientService.listAllClients());
        System.out.println("\nClients saved to file.");

        // تحميل البيانات من الملف
        List<Client> loadedClients = fileService.loadFromFile("clients.txt");
        System.out.println("\nLoaded Clients from File:");
        for (Client client : loadedClients) {
            System.out.println(client);
        }
    }

    public static void main(String[] args) {
        ProgramManagement program = new ProgramManagement();
        try {
            program.run();
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
