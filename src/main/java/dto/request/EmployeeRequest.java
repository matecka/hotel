package dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequest {

    private String name;
    private String surname;
    private String phone;
    private String position;
    private BigDecimal salary;
    private String email;
    private LocalDate hireDate;
}
