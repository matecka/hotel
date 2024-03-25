package dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponse {

    private String name;
    private String description;
    private String status;
    private Integer capacity;
    private BigDecimal price;
    private Long hotelId;
    private String hotelName;
}
