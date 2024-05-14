package mapper;

import dto.response.MessageResponse;
import model.Message;

public class MessageMapper {

    public MessageResponse from(Message message){
       return MessageResponse.builder()
                .customer(message.getCustomer())
                .dateTime(message.getDateTime())
                .message(message.getText())
                .reservationDetails(message.getReservationDetails())
                .build();
    }
}
