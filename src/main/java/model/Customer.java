package model;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String address;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Reservation> reservations;
}
