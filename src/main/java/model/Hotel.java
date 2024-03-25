package model;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Hotel {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Room> rooms;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Employee> employees;
}