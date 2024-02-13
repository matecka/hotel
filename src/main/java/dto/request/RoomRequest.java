package dto.request;

import lombok.*;

import java.math.BigDecimal;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {

    private String name;
    private String description;
    private String status;
    private Integer capacity;
    private BigDecimal price;

    private Long hotelId;

}
