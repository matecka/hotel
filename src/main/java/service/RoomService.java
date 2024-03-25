package service;

import dao.HotelDao;
import dao.RoomDao;
import dto.request.RoomRequest;
import dto.request.UpdateRoomRequest;
import dto.response.RoomResponse;
import mapper.RoomMapper;
import model.Hotel;
import model.Room;

public class RoomService {

    private RoomDao roomDao;
    private HotelDao hotelDao;

    private RoomMapper roomMapper;

    public RoomService(RoomDao roomDao, HotelDao hotelDao, RoomMapper roomMapper) {
        this.roomDao = roomDao;
        this.hotelDao = hotelDao;
        this.roomMapper = roomMapper;
    }

    public RoomResponse getRoomById(Long id) {
        Room room = roomDao.getRoomById(id);
        return roomMapper.from(room);
    }

    public RoomResponse createRoom(RoomRequest roomRequest) {

        Hotel hotel = hotelDao.getHotelById(roomRequest.getHotelId());

        Room room = Room.builder()
                .hotel(hotel)
                .name(roomRequest.getName())
                .description(roomRequest.getDescription())
                .capacity(roomRequest.getCapacity())
                .status(roomRequest.getStatus())
                .price(roomRequest.getPrice())
                .build();
        roomDao.createRoom(room);
        return roomMapper.from(room);

    }

    public RoomResponse updateRoom(UpdateRoomRequest updateRoomRequest) {
        roomDao.getRoomById(updateRoomRequest.getId());
        Room room = Room.builder()
                .name(updateRoomRequest.getName())
                .description(updateRoomRequest.getDescription())
                .capacity(updateRoomRequest.getCapacity())
                .price(updateRoomRequest.getPrice())
                .status(updateRoomRequest.getStatus())
                .build();

        roomDao.updateRoom(room);
        return roomMapper.from(room);

    }

    public void deleteRoom(Long id) {
        roomDao.deleteRoom(id);
    }

}
