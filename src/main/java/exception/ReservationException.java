package exception;

import java.time.LocalDate;

public class ReservationException extends RuntimeException {
    private String message;
    private LocalDate date;

    public ReservationException(String message, LocalDate date) {
        this.message = message;
        this.date = date;
    }
}
