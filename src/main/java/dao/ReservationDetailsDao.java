package dao;

import config.HibernateUtil;
import exception.ReservationDetailsException;
import model.ReservationDetails;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.Optional;

public class ReservationDetailsDao {

    public Optional<ReservationDetails> getOptionalReservationDetailsById(Long reservationDetailsId) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            ReservationDetails reservationDetails =
                    session.get(ReservationDetails.class, reservationDetailsId);

            transaction.commit();

            return Optional.ofNullable(reservationDetails);
        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public ReservationDetails getReservationDetailsById(Long reservationDetailsId) {
        return getOptionalReservationDetailsById(reservationDetailsId)
                .orElseThrow(() -> new ReservationDetailsException(
                        "reservationDetails not found",
                        LocalDate.now()));
    }
}
