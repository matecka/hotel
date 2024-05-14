package exception;

import java.time.LocalDate;

public class ReservationDetailsException extends RuntimeException {

    private String message;
    private LocalDate date;

    public ReservationDetailsException(String message, LocalDate date) {
        this.message = message;
        this.date = date;
    }
}
