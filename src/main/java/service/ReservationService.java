package service;

import dao.CustomerDao;
import dao.PaymentDao;
import dao.ReservationDao;
import dao.RoomDao;
import dto.request.ReservationRequest;
import dto.request.UpdateReservationRequest;
import dto.response.ReservationResponse;
import mapper.ReservationMapper;
import model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReservationService {

    private ReservationDao reservationDao;
    private ReservationMapper reservationMapper;


    private RoomDao roomDao;

    private CustomerDao customerDao;
    private PaymentDao paymentDao;

    public ReservationService(ReservationDao reservationDao, RoomDao roomDao, ReservationMapper reservationMapper, CustomerDao customerDao, PaymentDao paymentDao) {
        this.reservationDao = reservationDao;
        this.reservationMapper = reservationMapper;
        this.roomDao = roomDao;
        this.customerDao = customerDao;
        this.paymentDao = paymentDao;
    }


    public ReservationResponse updateReservation(UpdateReservationRequest updateReservationRequest, Long id) {
        reservationDao.getReservationById(id);
        Payment payment = paymentDao.getPaymentById(updateReservationRequest.getPayment_id());
        Customer customer = customerDao.getCustomerById(updateReservationRequest.getCustomer_id());

        Reservation reservation = Reservation.builder()
                .startDate(updateReservationRequest.getStartDate())
                .endDate(updateReservationRequest.getEndDate())
                .payment(payment)
                .customer(customer)
                .build();

        Reservation updateReservation = reservationDao.updateReservation(reservation);

        return reservationMapper.from(updateReservation);
    }


    public void deleteReservation(Long id) {
        reservationDao.deleteReservation(id);
    }

    public ReservationResponse getReservationById(Long id) {
        Reservation reservation = reservationDao.getReservationById(id);
        return reservationMapper.from(reservation);
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation();
        Set<Room> rooms = reservationRequest.getRoomId().stream()
                .map(roomId -> roomDao.getRoomById(roomId))
                .collect(Collectors.toSet());

        Customer customer = customerDao.getCustomerById(reservationRequest.getCustomerId());
        Payment payment = paymentDao.getPaymentById(reservationRequest.getPaymentId());
        upsertReservation(reservation, reservationRequest, customer, payment);

        Set<ReservationDetails> details = createReservationDetails(rooms, reservation);
        reservation.setReservationDetails(details);

        reservationDao.createReservation(reservation);
        return reservationMapper.from(reservation);
    }

    public Set<ReservationDetails> createReservationDetails(Set<Room> rooms, Reservation reservation) {
        Set<ReservationDetails> reservationDetailsSet = new HashSet<>();
        for (Room room : rooms) {
            ReservationDetails reservationDetails = ReservationDetails.builder()
                    .reservation(reservation)
                    .room(room)
                    .build();
            reservationDetailsSet.add(reservationDetails);
        }
        return reservationDetailsSet;
    }

    public void upsertReservation(Reservation reservation, ReservationRequest reservationRequest, Customer customer, Payment payment) {
        reservation.builder()
                .startDate(reservationRequest.getStartDate())
                .endDate(reservationRequest.getEndDate())
                .customer(customer)
                .payment(payment)
                .build();
    }

    public void isExistsRoom(List<Long> roomIds) {

    }

}
