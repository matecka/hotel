package service;

import dao.CustomerDao;
import dao.PaymentDao;
import dao.ReservationDao;
import dao.RoomDao;
import dto.request.ReservationRequest;
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

    public ReservationService(ReservationDao reservationDao,
                              RoomDao roomDao,
                              ReservationMapper reservationMapper,
                              CustomerDao customerDao,
                              PaymentDao paymentDao) {
        this.reservationDao = reservationDao;
        this.reservationMapper = reservationMapper;
        this.roomDao = roomDao;
        this.customerDao = customerDao;
        this.paymentDao = paymentDao;
    }

    public Reservation updateReservation(Long reservation_id, ReservationRequest reservationRequest, Long customerId, Long paymentId) {
        Reservation reservationById = reservationDao.getReservationById(reservation_id);
        reservationById.setStartDate(reservationRequest.getStartDate());
        reservationById.setEndDate(reservationRequest.getEndDate());
        reservationById.setCustomer(customerDao.getCustomerById(customerId));
        reservationById.setPayment(paymentDao.getPaymentById(paymentId));

        Set<ReservationDetails> existingDetails = reservationById.getReservationDetails();

        existingDetails = existingDetails != null ? existingDetails : new HashSet<>();

        List<Long> newRoomIds = reservationRequest.getRoomId();

        existingDetails.removeIf(detail -> !newRoomIds.contains(detail.getRoom().getId()));

        Set<ReservationDetails> newDetails = new HashSet<>();
        for (Long roomId : newRoomIds) {
            boolean containsRoom = existingDetails.stream()
                    .anyMatch(detail -> detail.getRoom().getId().equals(roomId));
            if (!containsRoom) {
                Room room = roomDao.getRoomById(roomId);
                newDetails.add(ReservationDetails.builder()
                        .reservation(reservationById)
                        .room(room)
                        .build());
            }
        }

        existingDetails.addAll(newDetails);

        Reservation reservation1 = reservationDao.updateReservation(reservationById);
        return reservation1;
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
        reservation = upsertReservation(reservation, reservationRequest, customer, payment);

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


    public Reservation upsertReservation(Reservation reservation, ReservationRequest reservationRequest, Customer customer, Payment payment) {
        return reservation.builder()
                .startDate(reservationRequest.getStartDate())
                .endDate(reservationRequest.getEndDate())
                .customer(customer)
                .payment(payment)
                .build();
    }

    public void isExistsRoom(List<Long> roomIds) {

    }

    public Set<Room> mapByIds(List<Long> newRoomIds) {

        return newRoomIds.stream()
                .map(roomDao::getRoomById)
                .collect(Collectors.toSet());
    }

}
