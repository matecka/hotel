package mapper;


import dto.response.ReservationResponse;
import model.Reservation;

public class ReservationMapper {
    public ReservationResponse from(Reservation reservation){
        return ReservationResponse.builder()
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .build();
    }
}
