package dao;

import config.HibernateUtil;
import exception.RoomException;
import model.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RoomDao {

    public Optional<Room> getOptionalRoomById(Long roomId) {
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

    public Room getRoomById(Long roomId) {
        return getOptionalRoomById(roomId)
                .orElseThrow(() -> new RoomException("room not found", LocalDate.now()));
    }

    public List<Room> getAllRoom() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Room> rooms = session.createQuery("from room", Room.class).list();
            transaction.commit();
            return rooms;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Collections.emptyList();
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
