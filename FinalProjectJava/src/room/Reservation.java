package room;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Serializable {
    @Serial
    private static final long serialVersionUID = -2999064689991666732L;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Reservation(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

}
