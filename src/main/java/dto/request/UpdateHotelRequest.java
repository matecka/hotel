package dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateHotelRequest {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
}
