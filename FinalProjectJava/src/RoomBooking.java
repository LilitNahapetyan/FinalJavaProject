import room.Room;
import java.time.LocalDate;

public class RoomBooking {
    private Customer customer;
    private Room room;
    private LocalDate startDate;
    private LocalDate endDate;


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
