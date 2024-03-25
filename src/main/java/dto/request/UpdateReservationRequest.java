package dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateReservationRequest {

    private LocalDate startDate;
    private LocalDate endDate;
    private Long customer_id;
    private Long payment_id;
}
