package exception;

import java.time.LocalDate;

public class EmployeeException extends RuntimeException {
    private String message;
    private LocalDate date;

    public EmployeeException(String message, LocalDate date) {
        this.message = message;
        this.date = date;
    }
}
