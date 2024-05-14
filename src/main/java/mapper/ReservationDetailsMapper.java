package mapper;

import dto.response.ReservationDetailsResponse;
import model.ReservationDetails;

public class ReservationDetailsMapper {
    public ReservationDetailsResponse from(ReservationDetails reservationDetails){
        return ReservationDetailsResponse.builder()
                .reservation(reservationDetails.getReservation())
                .room(reservationDetails.getRoom())
                .build();
    }
}
