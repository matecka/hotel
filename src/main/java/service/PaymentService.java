package service;

import dao.PaymentDao;
import dto.request.PaymentRequest;
import dto.request.UpdatePaymentRequest;
import exception.PaymentException;
import model.Payment;

import java.time.LocalDate;

public class PaymentService {

    private PaymentDao paymentDao;

    public PaymentService(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public void updatePayment(UpdatePaymentRequest updatePaymentRequest) {
        paymentDao.getPaymentById(updatePaymentRequest.getId()).orElseThrow(() -> new PaymentException("payment not found", LocalDate.now()));
        Payment payment = Payment.builder()
                .id(updatePaymentRequest.getId())
                .date(updatePaymentRequest.getDate())
                .status(updatePaymentRequest.getStatus())
                .build();

        paymentDao.updatePayment(payment);
    }

    public void createPayment(PaymentRequest paymentRequest) {
        Payment payment = Payment.builder()
                .date(paymentRequest.getDate())
                .status(paymentRequest.getStatus())
                .build();

        paymentDao.createPayment(payment);
    }

    public void deletePayment(Long id) {
        paymentDao.deletePayment(id);
    }

    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id).orElseThrow(() -> new PaymentException("payment not found", LocalDate.now()));
    }
}
