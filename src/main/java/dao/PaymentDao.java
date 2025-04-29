package dao;

import exception.PaymentException;
import model.Payment;
import java.time.LocalDate;
import java.util.Optional;

public class PaymentDao extends BaseDao {

    public Optional<Payment> getOptionalPaymentById(Long paymentId) {
        return executeTransaction(session ->
                Optional.ofNullable(session.get(Payment.class, paymentId)));
    }

    public Payment getPaymentById(Long paymentId) {
        return getOptionalPaymentById(paymentId)
                .orElseThrow(() -> new PaymentException("Payment not found", LocalDate.now()));

    }

    public void createPayment(Payment payment) {
        executeTransaction(session -> session.save(payment));
    }

    public Payment updatePayment(Payment payment) {
        return executeTransaction(session -> {
            session.update(payment);
            return payment;
        });
    }

    public boolean deletePayment(Long paymentId) {
        return executeTransaction(session -> {
            Payment payment = session.get(Payment.class, paymentId);
            if (payment != null) {
                session.delete(payment);
                return true;
            }
            return false;
        });
    }
}
