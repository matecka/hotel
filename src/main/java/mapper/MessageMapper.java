package mapper;

import dto.response.MessageResponse;
import model.Message;

public class MessageMapper {

    public MessageResponse from(Message message){
       return MessageResponse.builder()
                .customerId(message.getCustomer().getId())
                .dateTime(message.getDateTime())
                .message(message.getText())
                .reservationDetailsId(message.getReservationDetails().getId())
                .build();
    }
}
