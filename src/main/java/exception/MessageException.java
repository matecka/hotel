package exception;

import java.time.LocalDate;

public class MessageException extends RuntimeException {
    private String message;
    private LocalDate date;

    public MessageException(String message, LocalDate date) {
        this.message = message;
        this.date = date;
    }
}
