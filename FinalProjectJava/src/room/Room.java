package room;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Room implements Serializable {
    @Serial
    private static final long serialVersionUID = 14165563114686L;
    private int roomId;
    private RoomType roomType;
    private static int generateRoomId = 0;
    private double pricePerDay;
    List<Reservation> reservationList = new ArrayList<>();
    private boolean isAvailable = true;

    public Room(RoomType roomType) {
        this.roomId = generateRoomId++;
        this.roomType = roomType;
    }
    public void addReservation(LocalDate startTime, LocalDate endTime){
        Reservation reservation = new Reservation(startTime,endTime);
        reservationList.add(reservation);

    }

    public static void changeID(int id){
        generateRoomId = id;
    }
    public int getRoomId() {
        return roomId;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public double getPricePerDay() {
        return roomType.getPricePerDay();
    }

    public boolean isAvailable(LocalDate startTime, LocalDate endTime) {
        if (reservationList.isEmpty()) {
            return true; // The room is available because there are no reservations
        }

        Reservation newReservation = new Reservation(startTime, endTime);

        for (Reservation existingReservation : reservationList) {
            LocalDate existingStartDate = existingReservation.getStartDate();
            LocalDate existingEndDate = existingReservation.getEndDate();

            // Check for full overlap or partial overlap
            if (!(newReservation.getStartDate().isAfter(existingEndDate)
                    || newReservation.getEndDate().isBefore(existingStartDate))) {
                return false; // The room is not available for the specified time period
            }
        }
        return true; // The room is available for the specified time period
    }

    @Override
    public String toString() {
        return "Room ID: " + roomId +
                ", Room Type: " + roomType +
                ", Price Per Day: $" + this.getPricePerDay();
    }

}

