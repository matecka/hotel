package dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest {

    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> roomId;
    private Long customerId;
    private Long paymentId;
}
