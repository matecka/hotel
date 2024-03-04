package dao;


import config.HibernateUtil;
import exception.HotelException;
import model.Hotel;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.Optional;

public class HotelDao {
    public void saveHotel(Hotel hotel) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(hotel);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateHotel(Hotel hotel) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(hotel);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Optional<Hotel> getOptionalHotelById(Long hotelId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Hotel hotel = session.get(Hotel.class, hotelId);
            transaction.commit();
            return Optional.ofNullable(hotel);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Hotel getHotelById(Long hotelId) {
       return getOptionalHotelById(hotelId).orElseThrow(() -> new HotelException("hotel not found", LocalDate.now()));
    }

    public boolean deleteHotel(Long hotelId) {
        Transaction transaction = null;
        boolean isDeleted = false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Hotel hotel = session.get(Hotel.class, hotelId);
            if (hotel != null) {
                session.delete(hotel)
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
