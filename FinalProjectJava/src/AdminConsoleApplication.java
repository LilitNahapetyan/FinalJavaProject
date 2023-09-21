import room.Room;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;

public class AdminConsoleApplication {
    private final SysAdmin admin = new SysAdmin();

    // Main method to manage the hotel room system
    public void manageRooms() {
        Scanner sc = new Scanner(System.in);
        String operator;
        System.out.println("Welcome to the Hotel Room Management System");
        do {
            printMenu();
            operator = sc.next();
            switch (operator) {
                case "1" -> {
                    addRoom(sc);
                }
                case "2" -> {
                    addCustomer(sc);
                }
                case "3" -> {
                    bookRoom(sc);
                }
                case "4" -> {
                    saveData();
                }
                case "5" -> {
                    makeReport(sc);
                }
                case "6" -> {
                    admin.printHotelInfo();
                }
                case "0" -> {
                    System.out.println("Exiting...");
                    System.out.println("Do you want to save data?  Y/N");
                    String command = sc.next().toLowerCase();
                    if(command.equals("y")){
                        saveData();
                    }
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (!operator.equals("0"));
    }

    // Utility method to read an integer input from the user
    private int readIntInput(Scanner sc, String prompt) {
        int input = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print(prompt);
                input = sc.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid numeric choice.");
                sc.nextLine();
            }
        }
        return input;
    }

    // Method to add a room to the system
    void addRoom(Scanner sc){
        String output = """
                1 - Single Room 
                2 - Double Room
                3 - Deluxe Room
                """;
        System.out.println(output);
        boolean find = false;
        while (!find){
            System.out.println("Please, choose one of this:  1 , 2 , 3");
            switch (sc.next()){
                case "1" -> {
                    admin.registerSingleRoom();
                    find = true;
                }
                case "2" -> {
                    admin.registerDoubleRoom();
                    find = true;
                }
                case "3" -> {
                    admin.registerDeluxeRoom();
                    find = true;
                }
            }
        }
    }

    // Method to add a customer to the system
    public void addCustomer(Scanner sc) {
        String name;
        String email;

        while (true) {
            System.out.print("Name: ");
            name = sc.next();
            if (validateName(name)) {
                break;
            } else {
                System.out.println("Invalid name. Please try again.");
            }
        }

        while (true) {
            System.out.print("Email: ");
            email = sc.next();
            if (validateEmail(email)) {
                break;
            } else {
                System.out.println("Invalid email. Please try again.");
            }
        }

        admin.registerCustomer(name, email);
    }

    // Method to book a room for a customer
    void bookRoom(Scanner sc){
        String name;
        String email;

        while (true) {
            System.out.print("Name: ");
            name = sc.next();
            if (validateName(name)) {
                break;
            } else {
                System.out.println("Invalid name. Please try again.");
            }
        }

        while (true) {
            System.out.print("Email: ");
            email = sc.next();
            if (validateEmail(email)) {
                break;
            } else {
                System.out.println("Invalid email. Please try again.");
            }
        }

        LocalDate start;
        LocalDate end;

        while (true){
            try {
                System.out.println("Start Date (yyyy-MM-dd):");
                start = LocalDate.parse(sc.next());
                System.out.println("End Date (yyyy-MM-dd):");
                end = LocalDate.parse(sc.next());

                if (validateDate(start,end)) {
                    break;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");

            }
        }

        admin.availableRoomsForPeriod(start, end);
        int id = readIntInput(sc, "Enter room ID: ");
        Customer customer = new Customer(name, email);

        try {
            admin.bookRoom(customer, id, start, end);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    // Method to generate a report
    public void makeReport(Scanner sc){
        admin.printRoomInfo();
        int roomId = readIntInput(sc,"Input ID");

        Room room = admin.findRoomById(roomId);

        if (room == null) {
            System.out.println("Room with ID " + roomId + " not found.");
        } else {
            System.out.println("Generating report for Room ID " + roomId + "...");
            admin.roomBookingReport(roomId, "reports.txt");
            System.out.println("Report generated successfully.");
        }
    }

    // Method to save data to a file
    public void saveData(){
        try {
            admin.saveAllData();
            System.out.println("All data saved successfully to file");
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
    public boolean validateEmail(String email) {
        String regex = "^[a-z0-9][a-z0-9.+-]*@[a-z][a-z.-]*\\.[a-z]*$";
        return Pattern.matches(regex, email);
    }

    public boolean validateName(String name) {
        String regex = "^[a-zA-Z\\s-]+$";
        return Pattern.matches(regex, name);
    }

    public boolean validateDate(LocalDate first, LocalDate second) {
        if (first.isAfter(second)) {
            System.out.println("input valid dates.");
            return false;
        }

        LocalDate currentDate = LocalDate.now();

        if (first.isBefore(currentDate)) {
            System.out.println("start date must be on or after the current date.");
            return false;
        }

        return true;
    }

    // Utility method to print the main menu
    private void printMenu() {
        String message = """
                1: Add a Room
                2: Add a Customer
                3: Book a Room for a Customer
                4: Save System State to a File
                5: Make report
                6: Print hotel info
                0: Exit
                """;
        System.out.println(message);
    }
}
