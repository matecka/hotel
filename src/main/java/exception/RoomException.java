package exception;

import java.time.LocalDate;

public class RoomException extends RuntimeException{
    private String message;
    private LocalDate date;

    public RoomException(String message, LocalDate date) {
        this.message = message;
        this.date = date;
    }
}
