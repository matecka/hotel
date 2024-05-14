package dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Reservation;
import model.Room;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateReservationDetailsRequest {

    private Reservation reservation;
    private Room room;
}
