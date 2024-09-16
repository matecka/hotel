package service;

import dao.HotelDao;
import dao.RoomDao;
import dto.request.RoomRequest;
import dto.request.UpdateRoomRequest;
import dto.response.RoomResponse;
import mapper.RoomMapper;
import model.Hotel;
import model.Room;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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


    //Znaleźć pokoje które mają powierzchnie od-do
    public List<RoomResponse> getRoomInRangeCapacity(Integer from, Integer to) {
        return roomDao.getAllRoom().stream()
                .filter(r -> r.getCapacity() >= from && r.getCapacity() <= to)
                .map(roomMapper::from)
                .collect(Collectors.toList());
    }

    //znaleźć pokoje które są w przedziale cenowy od-do
    public List<RoomResponse> getRoomsInPriceRange(BigDecimal min, BigDecimal max) {
        return roomDao.getAllRoom().stream()
                .filter(r -> r.getPrice().compareTo(min) >= 1 && r.getPrice().compareTo(max) <= 1)
                .map(roomMapper::from)
                .collect(Collectors.toList());
    }
}



