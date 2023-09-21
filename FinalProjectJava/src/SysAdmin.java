import room.DeluxeRoom;
import room.DoubleRoom;
import room.Room;
import room.SingleRoom;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysAdmin implements Serializable{
    @Serial
    private static final long serialVersionUID = 4301720204968325812L;
    private final String roomFileName = "registeredRooms.ser";
    private final String roomBookingsFilename = "roomBookingsArchive.ser";
    private final String customerBookingsFilename = "customerBookingArchive";

    private final List<Room> hotelRooms = new ArrayList<>();
    private final Map<Room, List<RoomBooking>> roomBookingsMap = new HashMap<>();
    private final Map<Customer, List<RoomBooking>> customerBookingsMap = new HashMap<>();

    public SysAdmin() {
        loadAllData();
        updateGenerateID();
    }

    public void updateGenerateID(){
        Room.changeID(hotelRooms.size() + 1);
    }
    public List<RoomBooking> getRoomBookingHistory(Room room) {
        return roomBookingsMap.getOrDefault(room, new ArrayList<>());
    }

    public List<RoomBooking> getCustomerBookingHistory(Customer customer) {
        return customerBookingsMap.getOrDefault(customer, new ArrayList<>());
    }
    public void printRoomInfo() {
        if (hotelRooms.isEmpty()) {
            System.out.println("No rooms available.");
        } else {
            for (Room r: hotelRooms){
                System.out.println(r);
            }
        }
    }

    // Admin is adding a new hotel room
    // This action involves creating a new room instance and configuring its properties.
    public void registerSingleRoom(){
        hotelRooms.add(new SingleRoom());
        System.out.println("Single Room added.");
    }
    public void registerDoubleRoom(){
        hotelRooms.add(new DoubleRoom());
        System.out.println("Double Room added.");
    }
    public void registerDeluxeRoom(){
        hotelRooms.add(new SingleRoom());
        System.out.println("Deluxe Room added.");
    }

    //Find and display available rooms for a given period.
    public void availableRoomsForPeriod(LocalDate startDate, LocalDate endDate){
        boolean available = false;
        for (Room r: hotelRooms){
            if(r.isAvailable(startDate,endDate)){
                available = true;
                System.out.println(r);
            }
        }
        if(!available){
            System.out.println("there is not available room");
        }
    }

    public void bookRoom(Customer customer, int roomID, LocalDate startDate, LocalDate endDate) {
        Room room = findRoomById(roomID);

        if (room != null && room.isAvailable(startDate, endDate)) {
            RoomBooking booking = new RoomBooking(customer, room, startDate, endDate);

            List<RoomBooking> roomBookings = roomBookingsMap.computeIfAbsent(room, k -> new ArrayList<>());
            roomBookings.add(booking);

            List<RoomBooking> customerBookings = customerBookingsMap.computeIfAbsent(customer, k -> new ArrayList<>());
            customerBookings.add(booking);

            room.addReservation(startDate, endDate);

            generateBill(booking);

            System.out.println("Room booked successfully.");
        } else {
            throw new IllegalArgumentException("Booking failed. Room not available or invalid room ID.");
        }
    }

    public Room findRoomById(int roomID) {
        for (Room room : hotelRooms) {
            if (room.getRoomId() == roomID) {
                return room;
            }
        }
        return null;
    }

    public void roomBookingReport(int roomID, String fileName) {
        Room room = findRoomById(roomID);
        if (room == null) {
            System.out.println("Room with ID " + roomID + " not found.");
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("--------------------------------------------");
            if (roomBookingsMap.isEmpty()) {
                writer.println("No room bookings.\"");
            } else {
                writer.println("Room Booking History for Room ID " + roomID);
                writer.println("Customer              Start Date     End Date");
                writer.println();
                for (Map.Entry<Room, List<RoomBooking>> entry : roomBookingsMap.entrySet()) {
                    List<RoomBooking> bookings = entry.getValue();
                    for (RoomBooking booking : bookings) {
                        writer.println(booking.getCustomer().getName() + "              " +
                                booking.getStartDate() + "     " + booking.getEndDate());
                    }
                }
            }

            System.out.println("Room booking history report for Room ID " + roomID + " written to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing room booking history report to file: " + e.getMessage());
        }
    }

    public void generateBill(RoomBooking booking) {
        double roomCharge = booking.getTotalCost();
        double tax = roomCharge * 0.20; // 20% tax
        double serviceFee = (roomCharge + tax) * 0.10; // 10% service fee
        double totalAmount = roomCharge + tax + serviceFee;

        System.out.println("----- Bill for Room Booking -----");
        System.out.println("Room ID: " + booking.getRoom().getRoomId());
        System.out.println("Room Type: " + booking.getRoom().getRoomType());
        System.out.println("Customer Name: " + booking.getCustomer().getName());
        System.out.println("Customer Email: " + booking.getCustomer().getEmail());
        System.out.println("Booking Period: " + booking.getStartDate() + " to " + booking.getEndDate());
        System.out.println("Room Charge: $" + roomCharge);
        System.out.println("Tax (20%): $" + tax);
        System.out.println("Service Fee (10%): $" + serviceFee);
        System.out.println("Total Amount: $" + totalAmount);
        System.out.println("----------------------------------");
    }



    public void registerCustomer(String name, String email) {
        Customer customerToRegister = new Customer(name, email);
        if (!customerBookingsMap.containsKey(customerToRegister)) {
            customerBookingsMap.put(customerToRegister, new ArrayList<>());
            System.out.println("Customer registered successfully.");
        } else {
            System.err.println("Customer is already registered.");
        }
    }

    private void saveRooms() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(roomFileName))) {
            objectOutputStream.writeObject(hotelRooms);
            objectOutputStream.flush();
        } catch (IOException e) {
            System.err.println("Error saving room data: " + e.getMessage());
        }
    }

    private void saveRoomBookings() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(roomBookingsFilename))) {
            objectOutputStream.writeObject(roomBookingsMap);
            objectOutputStream.flush();
        } catch (IOException e) {
            System.err.println("Error saving room bookings data: " + e.getMessage());
        }
    }

    private void saveCustomerBookings() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(customerBookingsFilename))) {
            objectOutputStream.writeObject(customerBookingsMap);
            objectOutputStream.flush();
        } catch (IOException e) {
            System.err.println("Error saving customer bookings data: " + e.getMessage());
        }
    }
    private void loadRooms() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(roomFileName))) {
            List<Room> savedRooms = (List<Room>) objectInputStream.readObject();
            hotelRooms.addAll(savedRooms);
            System.out.println("Room data loaded successfully from " + roomFileName);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("No saved room data found.");
        }
    }
    private void loadRoomBookings() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(roomBookingsFilename))) {
            Map<Room, List<RoomBooking>> savedRoomBookingsMap = (Map<Room, List<RoomBooking>>) objectInputStream.readObject();
            roomBookingsMap.clear();
            roomBookingsMap.putAll(savedRoomBookingsMap);
            System.out.println("Room bookings map loaded successfully from " + roomBookingsFilename);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("No room bookings data found.");
        }
    }

    private void loadCustomerBookings() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(customerBookingsFilename))) {
            Map<Customer, List<RoomBooking>> savedCustomerBookingsMap = (Map<Customer, List<RoomBooking>>) objectInputStream.readObject();
            customerBookingsMap.clear();
            customerBookingsMap.putAll(savedCustomerBookingsMap);
            System.out.println("Customer bookings map loaded successfully from " + customerBookingsFilename);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("No saved customer bookings map found.");
        }
    }

    public void printHotelInfo() {
        System.out.println("Hotel Information:");
        System.out.println("---------------------------------");

        // Print Hotel Rooms
        System.out.println("Hotel Rooms:");
        if (hotelRooms.isEmpty()) {
            System.out.println("No rooms available.");
        } else {
            for (Room room : hotelRooms) {
                System.out.println(room);
            }
        }

        System.out.println();
        // Print Room Bookings
        System.out.println("Room Bookings:");
        if (roomBookingsMap.isEmpty()) {
            System.out.println("No room bookings.");
        } else {
            for (Map.Entry<Room, List<RoomBooking>> entry : roomBookingsMap.entrySet()) {
                Room room = entry.getKey();
                List<RoomBooking> bookings = entry.getValue();
                System.out.println(room);
                for (RoomBooking booking : bookings) {
                    System.out.println("Customer Name: " + booking.getCustomer().getName());
                    System.out.println("Booking Period: " + booking.getStartDate() + " to " + booking.getEndDate());
                    System.out.println("Total Cost: $" + booking.getTotalCost());
                    System.out.println("---------------------------------");
                }
            }
        }

        System.out.println();
        // Print Customer Bookings
        System.out.println("Customer Bookings:");
        if (customerBookingsMap.isEmpty()) {
            System.out.println("No customer bookings.");
        } else {
            for (Map.Entry<Customer, List<RoomBooking>> entry : customerBookingsMap.entrySet()) {
                Customer customer = entry.getKey();
                List<RoomBooking> bookings = entry.getValue();

                System.out.println("Customer Name: " + customer.getName());
                System.out.println("Customer Email: " + customer.getEmail());
                System.out.println("Total Bookings: " + bookings.size());
                System.out.println("Bookings:");

                for (RoomBooking booking : bookings) {
                    System.out.println("Room Type: " + booking.getRoom().getRoomType());
                    System.out.println("Booking Period: " + booking.getStartDate() + " to " + booking.getEndDate());
                    System.out.println("Total Cost: $" + booking.getTotalCost());
                    System.out.println("---------------------------------");
                }
            }
        }
    }

    public void saveAllData() {
        saveRooms();
        saveRoomBookings();
        saveCustomerBookings();
    }

    public void loadAllData() {
        loadRooms();
        loadRoomBookings();
        loadCustomerBookings();
    }
}
