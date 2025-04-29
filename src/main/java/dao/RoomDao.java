package dao;

import exception.RoomException;
import model.Room;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RoomDao extends BaseDao {

    public Optional<Room> getOptionalRoomById(Long roomId) {
        return executeTransaction(session ->
                Optional.ofNullable(session.get(Room.class, roomId)));
    }

    public Room getRoomById(Long roomId) {
        return getOptionalRoomById(roomId)
                .orElseThrow(() -> new RoomException("room not found", LocalDate.now()));
    }

    public List<Room> getAllRoom() {
        return executeTransaction(session ->
                session.createQuery("from Room", Room.class).list());
    }


    public void createRoom(Room room) {
        executeTransaction(session -> session.save(room));
    }

    public Room updateRoom(Room room) {
        return executeTransaction(session -> {
            session.update(room);
            return room;
        });
    }

    public boolean deleteRoom(Long roomId) {
        return executeTransaction(session -> {
            Room room = session.get(Room.class, roomId);
            if (room != null) {
                session.delete(room);
                return true;
            }
            return false;
        });
    }
}
