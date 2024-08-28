package mapper;

import dto.response.ReservationDetailsResponse;
import model.ReservationDetails;

public class ReservationDetailsMapper {
    public ReservationDetailsResponse from(ReservationDetails reservationDetails){
        return ReservationDetailsResponse.builder()
                .reservationId(reservationDetails.getReservation().getId())
                .roomId(reservationDetails.getRoom().getId())
                .build();
    }
}
