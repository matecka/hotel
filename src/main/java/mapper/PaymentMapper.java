package mapper;

import dto.response.PaymentResponse;
import model.Payment;

public class PaymentMapper {
    public PaymentResponse from(Payment payment){
        return PaymentResponse.builder()
                .date(payment.getDate())
                .status(payment.getStatus())
                .build();
    }
}
