package service;

import dao.ReservationDetailsDao;
import dto.response.ReservationDetailsResponse;
import mapper.ReservationDetailsMapper;
import model.ReservationDetails;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationDetailsService {
    private ReservationDetailsDao reservationDetailsDao;
    private ReservationDetailsMapper reservationDetailsMapper;


    public ReservationDetailsService(ReservationDetailsDao reservationDetailsDao,
                                     ReservationDetailsMapper reservationDetailsMapper) {
        this.reservationDetailsDao = reservationDetailsDao;
        this.reservationDetailsMapper = reservationDetailsMapper;
    }


    public ReservationDetailsResponse getReservationDetailsById(Long id) {
        ReservationDetails reservationDetails =
                reservationDetailsDao.getOptionalReservationDetailsById(id).orElseThrow();
        return reservationDetailsMapper.from(reservationDetails);
    }

    public List<ReservationDetailsResponse> getAllRoomReserved(LocalDate dateFrom, LocalDate dateTo) {
        return reservationDetailsDao.getAllReservationDetails().stream()
                .filter(rd -> {
                    LocalDate startDate = rd.getReservation().getStartDate();
                    LocalDate endDate = rd.getReservation().getEndDate();
                    return startDate.isAfter(dateFrom) && endDate.isBefore(dateTo);
                })
                .map(reservationDetailsMapper::from)
                .collect(Collectors.toList());
    }
}
