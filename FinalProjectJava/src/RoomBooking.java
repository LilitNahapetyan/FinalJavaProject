import room.Room;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class RoomBooking implements Serializable {
    @Serial
    private static final long serialVersionUID = 1996450030499171140L;
    private final Customer customer;
    private final Room room;
    private final LocalDate startDate;
    private final LocalDate endDate;


    public RoomBooking(Customer customer, Room room, LocalDate startDate, LocalDate endDate) {
        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        room.addReservation(startDate,endDate);
    }

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getTotalCost() {
        long daysBetween = endDate.toEpochDay() - startDate.toEpochDay();
        return daysBetween * room.getPricePerDay();
    }
}
