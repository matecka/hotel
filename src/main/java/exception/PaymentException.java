package exception;

import java.time.LocalDate;

public class PaymentException extends RuntimeException {
    private String message;
    private LocalDate date;

    public PaymentException(String message, LocalDate date) {
        this.message = message;
        this.date = date;
    }
}
