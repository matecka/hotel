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

    public Reservation updateReservation(Long reservationId,
                                         ReservationRequest reservationRequest,
                                         Long customerId,
                                         Long paymentId) {

        Reservation reservationById = reservationDao.getReservationById(reservationId);
        upsertReservation(reservationById, reservationRequest, customerId, paymentId);

        updateOrCreateReservationDetails(reservationById, reservationRequest);

        Reservation reservation = reservationDao.updateReservation(reservationById);

        return reservation;
    }

    public void updateOrCreateReservationDetails(Reservation reservation, ReservationRequest reservationRequest) {

        Set<ReservationDetails> existingDetails = reservation.getReservationDetails();
        existingDetails = existingDetails != null ? existingDetails : new HashSet<>();

        List<Long> newRoomIds = reservationRequest.getRoomId();
        existingDetails.removeIf(detail -> !newRoomIds.contains(detail.getRoom().getId()));
        addNewDetails(existingDetails, newRoomIds, reservation);
    }

public void addNewDetails(Set<ReservationDetails> existingDetails,
                          List<Long> newRoomIds,
                          Reservation reservationById){

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

        reservation = upsertReservation(reservation, reservationRequest, reservationRequest.getCustomerId(), reservationRequest.getPaymentId());

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

//todo customer and payment - to check, now returns old reservation
    public Reservation upsertReservation(Reservation reservation,
                                         ReservationRequest reservationRequest,
                                         Long customerId,
                                         Long paymentId) {
        return reservation.builder()
                .startDate(reservationRequest.getStartDate())
                .endDate(reservationRequest.getEndDate())
                .customer(customerDao.getCustomerById(customerId))
                .payment(paymentDao.getPaymentById(paymentId))
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
