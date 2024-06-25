package service;

import dao.ReservationDetailsDao;
import dto.response.ReservationDetailsResponse;
import mapper.ReservationDetailsMapper;
import model.ReservationDetails;

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
}
