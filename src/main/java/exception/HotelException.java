package exception;

import java.time.LocalDate;

public class HotelException extends RuntimeException {
    private String message;
    private LocalDate date;

    public HotelException(String message, LocalDate date) {
        this.message = message;
        this.date = date;
    }
}
