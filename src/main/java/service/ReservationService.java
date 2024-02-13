package service;

import dao.ReservationDao;
import dao.RoomDao;
import dto.request.ReservationRequest;
import dto.request.UpdateReservationRequest;
import dto.response.ReservationResponse;
import exception.ReservationException;

import mapper.ReservationMapper;
import model.Guest;
import model.Reservation;
import model.ReservationDetails;
import model.Room;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReservationService {

    private ReservationDao reservationDao;
    private ReservationMapper reservationMapper;

    private RoomService roomService;
    private RoomDao roomDao;

    public ReservationService(ReservationDao reservationDao, RoomService roomService, ReservationMapper reservationMapper, RoomDao roomDao) {
        this.reservationDao = reservationDao;
        this.roomService = roomService;
        this.reservationMapper = reservationMapper;
        this.roomDao= roomDao;
    }

    //todo w request dodac goscia i payment, po sprawdzeniu crud dla guest i payment
    public ReservationResponse updateReservation(UpdateReservationRequest updateReservationRequest,Long id) {
        Reservation reservation = reservationDao.getReservationById(id).orElseThrow(() -> new ReservationException("reservation not found", LocalDate.now()));
        reservation.setStartDate(updateReservationRequest.getStartDate());
        reservation.setEndDate(updateReservationRequest.getEndDate());
        Reservation updateReservation = reservationDao.updateReservation(reservation);
        return reservationMapper.from(updateReservation);
    }

//    public void createReservation(ReservationRequest reservationRequest) {
//        Reservation reservation = Reservation.builder()
//                .startDate(reservationRequest.getStartDate())
//                .endDate(reservationRequest.getEndDate())
//                .build();
//
//        reservationDao.createReservation(reservation);
//    }

    public void deleteReservation(Long id) {
        reservationDao.deleteReservation(id);
    }

    public Reservation getReservationById(Long id) {
        return reservationDao.getReservationById(id)
                .orElseThrow(() -> new ReservationException("reservation not found", LocalDate.now()));
    }

    public void createReservation(ReservationRequest reservationRequest) {

        Set<Room> rooms = reservationRequest.getRoomId().stream()
                .map(roomId -> roomService.getRoomById(roomId))
                .collect(Collectors.toSet());

        Reservation reservation = Reservation.builder()
                .startDate(reservationRequest.getStartDate())
                .endDate(reservationRequest.getEndDate())
                .build();


        Set<ReservationDetails> reservationDetailsSet = new HashSet<>();
        for (Room room : rooms) {
            ReservationDetails reservationDetails = ReservationDetails.builder()
                    .reservation(reservation)
                    .room(room)
                    .build();
            reservationDetailsSet.add(reservationDetails);
        }
        reservation.setReservationDetails(reservationDetailsSet);

        reservationDao.createReservation(reservation);
    }

    public void isExistsRoom(List<Long> roomIds){

    }

}
