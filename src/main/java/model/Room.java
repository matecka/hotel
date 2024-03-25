package model;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Room {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String status;
    private Integer capacity;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @EqualsAndHashCode.Exclude
    private Hotel hotel;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "room", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ReservationDetails> reservationDetails;
}
