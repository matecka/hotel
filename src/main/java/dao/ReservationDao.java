package dao;

import config.HibernateUtil;
import exception.ReservationException;
import model.Reservation;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.Optional;

public class ReservationDao {
    public Optional<Reservation> getOptionalReservationById(Long reservationId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Reservation reservation = session.get(Reservation.class, reservationId);
            transaction.commit();
            return Optional.ofNullable(reservation);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Reservation getReservationById(Long reservationId) {
        return getOptionalReservationById(reservationId).orElseThrow(() -> new ReservationException("reservation not found", LocalDate.now()));
    }

    public void createReservation(Reservation reservation) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(reservation);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Reservation updateReservation(Reservation reservation) {
        Transaction transaction = null;
        Reservation updatedReservation = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(reservation);
            updatedReservation = session.get(Reservation.class, reservation.getId());
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return updatedReservation;
    }


    public boolean deleteReservation(Long reservationId) {
        Transaction transaction = null;
        boolean isDeleted = false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Reservation reservation = session.get(Reservation.class, reservationId);
            if (reservation != null) {
                session.delete(reservation)
                ;
                isDeleted = true;
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return isDeleted;
    }
}
