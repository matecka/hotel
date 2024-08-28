package service;

import dao.PaymentDao;
import dto.request.PaymentRequest;
import dto.request.UpdatePaymentRequest;
import dto.response.PaymentResponse;
import mapper.PaymentMapper;
import model.Payment;


public class PaymentService {

    private PaymentDao paymentDao;
    private PaymentMapper paymentMapper;

    public PaymentService(PaymentDao paymentDao, PaymentMapper paymentMapper) {
        this.paymentDao = paymentDao;
        this.paymentMapper = paymentMapper;
    }

    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentDao.getPaymentById(id);
        return paymentMapper.from(payment);
    }

    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        Payment payment = Payment.builder()
                .date(paymentRequest.getDate())
                .status(paymentRequest.getStatus())
                .build();

        paymentDao.createPayment(payment);
        return paymentMapper.from(payment);
    }

    public PaymentResponse updatePayment(UpdatePaymentRequest updatePaymentRequest) {
        paymentDao.getPaymentById(updatePaymentRequest.getId());
        Payment payment = Payment.builder()
                .id(updatePaymentRequest.getId())
                .date(updatePaymentRequest.getDate())
                .status(updatePaymentRequest.getStatus())
                .build();

        paymentDao.updatePayment(payment);
        return paymentMapper.from(payment);
    }

//    //5. Napisać metodę która liczy ile kosztował pobyt w hotelu dla danej osoby w danych dniach
//    public BigDecimal getAmountPrice(Reservation reservation){
//
//    }

    public void deletePayment(Long id) {
        paymentDao.deletePayment(id);
    }
}
