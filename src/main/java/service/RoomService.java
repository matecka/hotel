package service;

import dao.HotelDao;
import dao.RoomDao;
import dto.request.RoomRequest;
import dto.request.UpdateRoomRequest;
import dto.response.RoomResponse;
import exception.RoomException;
import mapper.RoomMapper;
import model.Hotel;
import model.Room;

import java.time.LocalDate;

public class RoomService {

    private RoomMapper roomMapper;

    private RoomDao roomDao;
    private HotelDao hotelDao;

    public RoomService(RoomDao roomDao, HotelDao hotelDao, RoomMapper roomMapper) {
        this.roomDao = roomDao;
        this.hotelDao = hotelDao;
        this.roomMapper = roomMapper;
    }

    public Room getRoomById(Long id) {

        return roomDao.getRoomById(id).orElseThrow(() -> new RoomException("room not found", LocalDate.now()));//todo Exception

    }

    public RoomResponse createRoom(RoomRequest roomRequest) {

        Hotel hotel = hotelDao.getHotelById(roomRequest.getHotelId()).orElseThrow(() -> new RoomException("room not found", LocalDate.now()));

        Room room = Room.builder().hotel(hotel).name(roomRequest.getName()).description(roomRequest.getDescription()).capacity(roomRequest.getCapacity()).status(roomRequest.getStatus()).price(roomRequest.getPrice()).build();
        roomDao.createRoom(room);
        return roomMapper.from(room);

    }

    public void updateRoom(UpdateRoomRequest updateRoomRequest) {
        Room room = roomDao.getRoomById(updateRoomRequest.getId()).orElseThrow(() -> new RoomException("room not found", LocalDate.now()));

        room.setName(updateRoomRequest.getName());
        room.setDescription(updateRoomRequest.getDescription());
        room.setCapacity(updateRoomRequest.getCapacity());
        room.setPrice(updateRoomRequest.getPrice());
        room.setStatus(updateRoomRequest.getStatus());

        roomDao.updateRoom(room);

    }

    public void deleteRoom(Long id) {
        roomDao.deleteRoom(id);
    }

}
