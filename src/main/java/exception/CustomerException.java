package exception;

import java.time.LocalDate;

public class CustomerException extends RuntimeException {
    private String message;
    private LocalDate date;

    public CustomerException(String message, LocalDate date) {
        this.message = message;
        this.date = date;
    }
}
