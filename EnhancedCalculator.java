import java.util.Scanner;

public class EnhancedCalculator {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("Welcome to Enhanced Calculator!");
        
        while (running) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1: // Basic arithmetic
                    performBasicArithmetic();
                    break;
                case 2: // Scientific calculations
                    performScientificCalculations();
                    break;
                case 3: // Unit conversions
                    performUnitConversions();
                    break;
                case 4: // Exit
                    running = false;
                    System.out.println("Thank you for using Enhanced Calculator!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            System.out.println(); // Add a blank line for better readability
        }
        
        scanner.close();
    }
    
    private static void printMenu() {
        System.out.println("\n==== CALCULATOR MENU ====");
        System.out.println("1. Basic Arithmetic");
        System.out.println("2. Scientific Calculations");
        System.out.println("3. Unit Conversions");
        System.out.println("4. Exit");
    }
    
    private static void printBasicArithmeticMenu() {
        System.out.println("\n==== BASIC ARITHMETIC ====");
        System.out.println("1. Addition");
        System.out.println("2. Subtraction");
        System.out.println("3. Multiplication");
        System.out.println("4. Division");
        System.out.println("5. Return to main menu");
    }
    
    private static void printScientificCalculationsMenu() {
        System.out.println("\n==== SCIENTIFIC CALCULATIONS ====");
        System.out.println("1. Square Root");
        System.out.println("2. Power (Exponentiation)");
        System.out.println("3. Sine");
        System.out.println("4. Cosine");
        System.out.println("5. Logarithm (base 10)");
        System.out.println("6. Return to main menu");
    }
    
    private static void printUnitConversionsMenu() {
        System.out.println("\n==== UNIT CONVERSIONS ====");
        System.out.println("1. Temperature (Celsius to Fahrenheit)");
        System.out.println("2. Temperature (Fahrenheit to Celsius)");
        System.out.println("3. Length (Meters to Feet)");
        System.out.println("4. Length (Feet to Meters)");
        System.out.println("5. Currency (USD to EUR)");
        System.out.println("6. Currency (EUR to USD)");
        System.out.println("7. Return to main menu");
    }
    
    private static void performBasicArithmetic() {
        boolean returnToMainMenu = false;
        
        while (!returnToMainMenu) {
            printBasicArithmeticMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1: // Addition
                    double num1 = getDoubleInput("Enter first number: ");
                    double num2 = getDoubleInput("Enter second number: ");
                    System.out.println(num1 + " + " + num2 + " = " + (num1 + num2));
                    break;
                case 2: // Subtraction
                    num1 = getDoubleInput("Enter first number: ");
                    num2 = getDoubleInput("Enter second number: ");
                    System.out.println(num1 + " - " + num2 + " = " + (num1 - num2));
                    break;
                case 3: // Multiplication
                    num1 = getDoubleInput("Enter first number: ");
                    num2 = getDoubleInput("Enter second number: ");
                    System.out.println(num1 + " * " + num2 + " = " + (num1 * num2));
                    break;
                case 4: // Division
                    num1 = getDoubleInput("Enter first number: ");
                    num2 = getDoubleInput("Enter second number: ");
                    if (num2 == 0) {
                        System.out.println("Error: Division by zero!");
                    } else {
                        System.out.println(num1 + " / " + num2 + " = " + (num1 / num2));
                    }
                    break;
                case 5: // Return to main menu
                    returnToMainMenu = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            System.out.println(); // Add a blank line for better readability
        }
    }
    
    private static void performScientificCalculations() {
        boolean returnToMainMenu = false;
        
        while (!returnToMainMenu) {
            printScientificCalculationsMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1: // Square Root
                    double num = getDoubleInput("Enter a number: ");
                    if (num < 0) {
                        System.out.println("Error: Cannot calculate square root of a negative number!");
                    } else {
                        System.out.println("Square root of " + num + " = " + Math.sqrt(num));
                    }
                    break;
                case 2: // Power
                    double base = getDoubleInput("Enter base: ");
                    double exponent = getDoubleInput("Enter exponent: ");
                    System.out.println(base + " raised to the power of " + exponent + " = " + Math.pow(base, exponent));
                    break;
                case 3: // Sine
                    double angle = getDoubleInput("Enter angle in degrees: ");
                    double radians = Math.toRadians(angle);
                    System.out.println("Sine of " + angle + " degrees = " + Math.sin(radians));
                    break;
                case 4: // Cosine
                    angle = getDoubleInput("Enter angle in degrees: ");
                    radians = Math.toRadians(angle);
                    System.out.println("Cosine of " + angle + " degrees = " + Math.cos(radians));
                    break;
                case 5: // Logarithm
                    num = getDoubleInput("Enter a number: ");
                    if (num <= 0) {
                        System.out.println("Error: Cannot calculate logarithm of a non-positive number!");
                    } else {
                        System.out.println("Logarithm (base 10) of " + num + " = " + Math.log10(num));
                    }
                    break;
                case 6: // Return to main menu
                    returnToMainMenu = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            System.out.println(); // Add a blank line for better readability
        }
    }
    
    private static void performUnitConversions() {
        boolean returnToMainMenu = false;
        
        while (!returnToMainMenu) {
            printUnitConversionsMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1: // Celsius to Fahrenheit
                    double celsius = getDoubleInput("Enter temperature in Celsius: ");
                    double fahrenheit = (celsius * 9/5) + 32;
                    System.out.println(celsius + " °C = " + fahrenheit + " °F");
                    break;
                case 2: // Fahrenheit to Celsius
                    fahrenheit = getDoubleInput("Enter temperature in Fahrenheit: ");
                    celsius = (fahrenheit - 32) * 5/9;
                    System.out.println(fahrenheit + " °F = " + celsius + " °C");
                    break;
                case 3: // Meters to Feet
                    double meters = getDoubleInput("Enter length in meters: ");
                    double feet = meters * 3.28084;
                    System.out.println(meters + " meters = " + feet + " feet");
                    break;
                case 4: // Feet to Meters
                    feet = getDoubleInput("Enter length in feet: ");
                    meters = feet / 3.28084;
                    System.out.println(feet + " feet = " + meters + " meters");
                    break;
                case 5: // USD to EUR
                    double usd = getDoubleInput("Enter amount in USD: ");
                    double eur = usd * 0.85; // Using a fixed conversion rate
                    System.out.println(usd + " USD = " + eur + " EUR");
                    break;
                case 6: // EUR to USD
                    eur = getDoubleInput("Enter amount in EUR: ");
                    usd = eur / 0.85; // Using a fixed conversion rate
                    System.out.println(eur + " EUR = " + usd + " USD");
                    break;
                case 7: // Return to main menu
                    returnToMainMenu = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            System.out.println(); // Add a blank line for better readability
        }
    }
    
    private static int getIntInput(String prompt) {
        int input = 0;
        boolean validInput = false;
        
        while (!validInput) {
            try {
                System.out.print(prompt);
                input = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer.");
            }
        }
        
        return input;
    }
    
    private static double getDoubleInput(String prompt) {
        double input = 0.0;
        boolean validInput = false;
        
        while (!validInput) {
            try {
                System.out.print(prompt);
                input = Double.parseDouble(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
        
        return input;
    }
}