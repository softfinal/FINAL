//package fitnessAcceptanceTest;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.*;
//
//public class ProgramManagement {
//	
//	class Client {
//	    int id;
//	    String name;
//	    int age;
//	    String fitnessGoals;
//	    String dietaryPreferences;
//	    String accountStatus;
//
//	    public Client(int id, String name, int age, String fitnessGoals, String dietaryPreferences) {
//	        this.id = id;
//	        this.name = name;
//	        this.age = age;
//	        this.fitnessGoals = fitnessGoals;
//	        this.dietaryPreferences = dietaryPreferences;
//	        this.accountStatus = "Pending"; 
//	    }
//
//	    public String getName() {
//	        return name;
//	    }
//	    public String getAccountStatus() {
//	        return accountStatus;
//	    }
//	    
//	    public void setAccountStatus(String status) {
//	        this.accountStatus = status;
//	    }
//
//
//	    public void updateProfile(String newName, int newAge, String newGoals, String newPreferences) {
//	        this.name = newName;
//	        this.age = newAge;
//	        this.fitnessGoals = newGoals;
//	        this.dietaryPreferences = newPreferences;
//	    }
//
//	    @Override
//	    public String toString() {
//	        return "ID: " + id + ", Name: " + name + ", Age: " + age + ", Fitness Goals: " + fitnessGoals + ", Dietary Preferences: " + dietaryPreferences;
//	    }
//	}
//
//	class AccountManagementSystem {
//	    private Map<Integer, Client> clients = new HashMap<>();
//	    private int clientCounter = 1;
//
//	    public int createProfile(String name, int age, String fitnessGoals, String dietaryPreferences) {
//	        Client newClient = new Client(clientCounter, name, age, fitnessGoals, dietaryPreferences);
//	        clients.put(clientCounter, newClient);
//	        return clientCounter++;
//	    }
//
//	    public boolean approveAccount(int clientId, AdminInterface admin) {
//	        Client client = clients.get(clientId);
//	        if (client != null && client.getAccountStatus().equals("Pending")) {
//	            // طلب الموافقة من المدير
//	            if (admin.approveAccount(clientId)) {
//	                client.setAccountStatus("Active");
//	                sendNotification(String.valueOf(clientId), "Your account has been approved and activated.");
//	                return true;
//	            }
//	        }
//	        return false;
//	    }
//
//	    public boolean rejectAccount(int clientId, AdminInterface admin) {
//	        Client client = clients.get(clientId);
//	        if (client != null && client.getAccountStatus().equals("Pending")) {
//	            // طلب الرفض من المدير
//	            if (admin.rejectAccount(clientId)) {
//	                client.setAccountStatus("Rejected");
//	                sendNotification(String.valueOf(clientId), "Your account has been rejected.");
//	                return true;
//	            }
//	        }
//	        return false;
//	    }
//	    public Client getClientById(int clientId) {
//	        return clients.get(clientId);  // إرجاع العميل باستخدام معرّف العميل
//	    }
//	    // إرسال إشعار للعميل
//	    private void sendNotification(String clientId, String message) {
//	        System.out.println("Notification to Client " + clientId + ": " + message);
//	    }
//	}
//	public void resetPassword(String email) {
//	    try (BufferedReader reader = new BufferedReader(new FileReader("clients.txt"))) {
//	        String line;
//	        while ((line = reader.readLine()) != null) {
//	            String[] data = line.split(",");
//	            if (data[2].equals(email)) {
//	            	sendNotification(data[1], "Password reset link: [link]");  // إرسال إشعار مع الرابط
//	            }
//	        }
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	}
//	public void deactivateAccount(String clientId)  {
//	    File inputFile = new File("clients.txt");
//	    File tempFile = new File("temp_clients.txt");
//
//	    if (!inputFile.exists()) {
//	        System.out.println("The file clients.txt does not exist.");
//	        return;
//	    }
//
//	    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
//	         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
//	        String line;
//	        boolean clientFound = false;
//
//	        while ((line = reader.readLine()) != null) {
//	            String[] data = line.split(",");
//	            if (data[0].equals(clientId)) {
//	                data[4] = "Inactive"; // تحديث الحالة إلى غير نشط
//	                clientFound = true;
//	                System.out.println("Deactivating account for client: " + data[1]);
//	                sendNotification(data[1], "Your account has been deactivated.");
//	            }
//	            writer.write(String.join(",", data)); // كتابة السطر إلى الملف المؤقت
//	            writer.newLine();
//	        }
//
//	        if (!clientFound) {
//	            System.out.println("Client with ID " + clientId + " not found.");
//	        }
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//
//	    if (!tempFile.renameTo(inputFile)) {
//	        System.out.println("Failed to rename the temporary file.");
//	    } else {
//	        System.out.println("Deactivation successful.");
//	    }
//	}
//
//
//
//	private void sendNotification(String clientName, String message) {
//	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("notifications.txt", true))) {
//	        writer.write(clientName + "," + message);
//	        writer.newLine();
//	    } catch (IOException e) {
//	        System.out.println("Error writing notification: " + e.getMessage());
//	    }
//	}
//}