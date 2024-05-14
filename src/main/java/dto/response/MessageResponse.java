package dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Customer;
import model.ReservationDetails;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {

    private LocalDateTime dateTime;
    private String message;
    private ReservationDetails reservationDetails;
    private Customer customer;

}
