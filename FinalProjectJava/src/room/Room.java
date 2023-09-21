package room;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Room implements Serializable {
    @Serial
    private static final long serialVersionUID = 8340103473456058155L;
    private final int roomId;
    private final RoomType roomType;
    private static int generateRoomId = 0;
    List<Reservation> reservationList = new ArrayList<>();

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
            return true;
        }

        Reservation newReservation = new Reservation(startTime, endTime);

        for (Reservation existingReservation : reservationList) {
            LocalDate existingStartDate = existingReservation.getStartDate();
            LocalDate existingEndDate = existingReservation.getEndDate();

            if (!(newReservation.getStartDate().isAfter(existingEndDate)
                    || newReservation.getEndDate().isBefore(existingStartDate))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Room ID: " + roomId +
                ", Room Type: " + roomType +
                ", Price Per Day: $" + this.getPricePerDay();
    }

}

