package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime dateTime;
    private String text;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_details_id")
    private ReservationDetails reservationDetails;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
