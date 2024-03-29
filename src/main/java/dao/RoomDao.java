package dao;

import config.HibernateUtil;
import model.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class RoomDao {
    public Optional<Room> getRoomById(Long roomId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Room room = session.get(Room.class, roomId);
            transaction.commit();
            return Optional.ofNullable(room);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
            }
    }

    public void createRoom(Room room) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(room);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateRoom(Room room) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(room);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public boolean deleteRoom(Long roomId) {
        Transaction transaction = null;
        boolean isDeleted = false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Room room = session.get(Room.class, roomId);
            if (room != null) {
                session.delete(room);
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
