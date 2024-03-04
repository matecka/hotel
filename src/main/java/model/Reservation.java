package model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @EqualsAndHashCode.Exclude
    private Customer customer;
    private Integer personCount;
    @OneToOne
    private Payment payment;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservation", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ReservationDetails> reservationDetails;
}
