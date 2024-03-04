package dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    private String name;
    private String surname;
    private String phone;
    private String email;
    private String address;
}
