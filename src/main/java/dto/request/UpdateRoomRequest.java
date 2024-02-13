package dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoomRequest {

    private Long id;
    private String name;
    private String description;
    private String status;
    private Integer capacity;
    private BigDecimal price;
}
