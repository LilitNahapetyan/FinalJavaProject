import room.Room;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class AdminConsoleApplication {
    private final SysAdmin admin = new SysAdmin();

    public void manageRooms() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            printMenu();
            choice = readIntInput(sc, "Enter your choice: ");


            switch (choice) {
                case 1 -> {
                    System.out.println("Add a room type:\n1 - Single Room\n2 - Double Room\n3 - Deluxe Room");
                    int roomTypeChoice = readIntInput(sc, "Select room type: ");
                    admin.registerRoom(roomTypeChoice);
                }
                case 2 -> {
                    System.out.println("Name:");
                    String name = sc.next();

                    if (name.isEmpty()) {
                        System.out.println("Name cannot be empty. Please enter a valid name.");
                        continue;
                    }

                    System.out.println("Email:");
                    String email = sc.next();

                    if (email.isEmpty()) {
                        System.out.println("Invalid email format. Please enter a valid email.");
                        continue;
                    }

                    admin.registerCustomer(name, email);
                    System.out.println("Customer added successfully.");
                }
                case 3 -> {
                    System.out.println("Name:");
                    String name = sc.next();
                    System.out.println("Email:");
                    String email = sc.next();
                    LocalDate start;
                    LocalDate end;

                    try {
                        System.out.println("Start Date (yyyy-MM-dd):");
                        start = LocalDate.parse(sc.next());
                        System.out.println("End Date (yyyy-MM-dd):");
                        end = LocalDate.parse(sc.next());

                        if (start.isAfter(end)) {
                            System.out.println("Invalid date range. End date must be after the start date.");
                            continue;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
                        continue;
                    }

                    admin.availableRoomsForPeriod(start, end);
                    int id = readIntInput(sc, "Enter room ID: ");
                    Customer customer = new Customer(name, email);

                    try {
                        admin.bookRoom(customer, id, start, end);
                        System.out.println("Room booked successfully.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 4 -> {
                    try {
                        admin.saveAllData();
                        System.out.println("All data saved successfully to file");
                    } catch (Exception e) {
                        System.out.println("Error saving data: " + e.getMessage());
                    }
                }
                case 5 ->{
                    admin.loadAllData();
                }
                case 6 -> {
                        admin.printRoomInfo();
                        int roomId = readIntInput(sc, "Enter the ID of the room for which you want to generate a report: ");

                        Room room = admin.findRoomById(roomId);

                        if (room == null) {
                            System.out.println("Room with ID " + roomId + " not found.");
                        } else {
                            System.out.println("Generating report for Room ID " + roomId + "...");
                            admin.roomBookingReport(roomId, "reports.txt");
                            System.out.println("Report generated successfully.");
                        }
                }
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

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

    private void printMenu() {
        System.out.println("Welcome to the Hotel Room Management System");
        System.out.println("1: Add a Room");
        System.out.println("2: Add a Customer");
        System.out.println("3: Book a Room for a Customer");
        System.out.println("4: Save System State to a File");
        System.out.println("5: Load the state Room");
        System.out.println("6: Make report");
        System.out.println("0: Exit");
    }

}
