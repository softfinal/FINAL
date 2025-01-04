package org.example;

import java.util.Scanner;

public class Main {

	    public static void main(String[] args) throws Exception {
	        Function functions = new Function(); // Instance of Functions class
	        Scanner scanner = new Scanner(System.in);
	        Printing printing = new Printing();

	        boolean running = true;
	        while (running) {
	            printWelcomeMenu();
	            int choice = getValidIntegerInput(scanner, printing);

	            switch (choice) {
	                case 1:
	                    functions.signUpFunction(); // Use Functions class for sign-up
	                    break;
	                case 2:
	                    functions.signInFunction(); // Use Functions class for sign-in
	                    break;
	                case 3:
	                    running = false; // Exit the loop
	                    printing.printSomething("Exiting system. Goodbye!");
	                    break;
	                default:
	                    printing.printSomething("Invalid choice! Please enter a valid choice.");
	                    break;
	            }
	        }
	        scanner.close(); // Close the scanner to avoid resource leaks
	    }

	    private static void printWelcomeMenu() {
	        Printing printing = new Printing();
	        printing.printSomething("""
	        ------ Welcome to Home Page ------
	        |                                |
	        |          1. Sign up            |
	        |          2. Sign in            |
	        |          3. Exit               |
	        |                                |
	        ----------------------------------
	        Enter your choice: 
	        """);
	    }

	    private static int getValidIntegerInput(Scanner scanner, Printing printing) {
	        while (!scanner.hasNextInt()) {
	            printing.printSomething("Invalid input! Please enter a valid integer.");
	            scanner.next();
	        }
	        return scanner.nextInt();
	    }
	}

