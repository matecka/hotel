package exception;

import java.time.LocalDate;

public class GuestException extends RuntimeException {
    private String message;
    private LocalDate date;

    public GuestException(String message, LocalDate date) {
        this.message = message;
        this.date = date;
    }
}
