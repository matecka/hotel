package dao;

import exception.ReservationException;
import model.Reservation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ReservationDao extends BaseDao {

    public Optional<Reservation> getOptionalReservationById(Long reservationId) {
        return executeTransaction(session ->
                Optional.ofNullable(session.get(Reservation.class, reservationId)));
    }

    public Reservation getReservationById(Long reservationId) {
        return getOptionalReservationById(reservationId)
                .orElseThrow(() -> new ReservationException("Reservation not found", LocalDate.now()));
    }

    public List<Reservation> getAllReservations() {
        return executeTransaction(session ->
                session.createQuery("from Reservation", Reservation.class).list());
    }

    public void createReservation(Reservation reservation) {
        executeTransaction(session -> session.save(reservation));
    }

    public Reservation updateReservation(Reservation reservation) {
        return executeTransaction(session -> {
            session.update(reservation);
            return reservation;
        });
    }

    public boolean deleteReservation(Long reservationId) {
        return executeTransaction(session -> {
            Reservation reservation = session.get(Reservation.class, reservationId);
            if (reservation != null) {
                session.delete(reservation);
                return true;
            }
            return false;
        });
    }
}
