package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String position;
    private BigDecimal salary;
    private String email;
    private LocalDate hireDate;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
