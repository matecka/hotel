package service;

import dao.CustomerDao;
import dao.MessageDao;
import dao.ReservationDetailsDao;
import dto.request.MessageRequest;
import dto.request.UpdateMessageRequest;
import dto.response.MessageResponse;
import mapper.MessageMapper;
import model.Message;

import java.time.LocalDateTime;

public class MessageService {


    private MessageDao messageDao;
    private MessageMapper messageMapper;
    private CustomerDao customerDao;
    private ReservationDetailsDao reservationDetailsDao;


    public MessageService(MessageDao messageDao,
                          MessageMapper messageMapper,
                          CustomerDao customerDao,
                          ReservationDetailsDao reservationDetailsDao) {
        this.messageDao = messageDao;
        this.messageMapper = messageMapper;
        this.customerDao = customerDao;
        this.reservationDetailsDao = reservationDetailsDao;

    }

    public MessageResponse updateMessage(UpdateMessageRequest updateMessageRequest) {

        messageDao.getMessageById(updateMessageRequest.getId());

        Message message = Message.builder()
                .id(updateMessageRequest.getId())
                .dateTime(LocalDateTime.now())
                .text(updateMessageRequest.getMessage())
                .reservationDetails(reservationDetailsDao.getReservationDetailsById(updateMessageRequest.getReservationDetails()))
                .customer(customerDao.getCustomerById(updateMessageRequest.getCustomer()))
                .build();

        messageDao.updateMessage(message);
        return messageMapper.from(message);
    }


    public MessageResponse createMessage(MessageRequest messageRequest) {
        Message message = Message.builder()
                .id(messageRequest.getId())
                .customer(customerDao.getCustomerById(messageRequest.getCustomer()))
                .reservationDetails(reservationDetailsDao.getReservationDetailsById(messageRequest.getReservationDetails()))
                .text(messageRequest.getMessage())
                .dateTime(messageRequest.getDateTime())
                .build();

        messageDao.createMessage(message);
        return messageMapper.from(message);
    }

    public void deleteMessage(Long id) {
        messageDao.deleteMessage(id);
    }

    public MessageResponse getMessageById(Long id) {
        Message message = messageDao.getMessageById(id);
        return messageMapper.from(message);
    }
}
