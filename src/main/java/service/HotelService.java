package service;

import dao.HotelDao;
import dto.request.HotelRequest;
import dto.request.UpdateHotelRequest;
import dto.response.HotelResponse;
import dto.response.RoomResponse;
import mapper.HotelMapper;
import mapper.RoomMapper;
import model.Hotel;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class HotelService {

    private HotelDao hotelDao;
    private HotelMapper hotelMapper;
    private RoomMapper roomMapper;

    public HotelService(HotelDao hotelDao, HotelMapper hotelMapper, RoomMapper roomMapper) {
        this.hotelDao = hotelDao;
        this.hotelMapper = hotelMapper;
        this.roomMapper = roomMapper;
    }

    public HotelResponse getHotelById(Long id) {
        Hotel hotel = hotelDao.getHotelById(id);
        return hotelMapper.from(hotel);
    }

    public HotelResponse createHotel(HotelRequest hotelRequest) {
        Hotel hotel = Hotel.builder()
                .name(hotelRequest.getName())
                .address(hotelRequest.getAddress())
                .phone(hotelRequest.getPhone())
                .email(hotelRequest.getEmail()).build();
        hotelDao.saveHotel(hotel);
        return hotelMapper.from(hotel);

    }

    public HotelResponse updateHotel(UpdateHotelRequest updateHotelRequest) {
        hotelDao.getHotelById(updateHotelRequest.getId());

        Hotel hotel = Hotel.builder()
                .id(updateHotelRequest.getId())
                .name(updateHotelRequest.getName())
                .address(updateHotelRequest.getAddress())
                .email(updateHotelRequest.getEmail())
                .phone(updateHotelRequest.getPhone()).build();
        hotelDao.updateHotel(hotel);
        return hotelMapper.from(hotel);
    }

    public void deleteHotel(Long id) {
        hotelDao.deleteHotel(id);
    }

    public List<RoomResponse> getRoomsBetweenPrices(Long id, BigDecimal from, BigDecimal to) {
        return hotelDao.getHotelById(id).getRooms()
                .stream().filter(r -> r.getPrice().compareTo(from) >= 1 && r.getPrice().compareTo(to) <= 1)
                .map(roomMapper::from)
                .collect(Collectors.toList());
    }

    public Long countRoomsByCapacity(Long id, Integer capacity) {
        return hotelDao.getHotelById(id).getRooms()
                .stream()
                .filter(r -> r.getCapacity().equals(capacity)).count();
    }

    public List<String> getAllRoomNamesInHotel(Long hotelId) {
       return hotelDao.getHotelById(hotelId)
                .getRooms()
                .stream().map(room -> room.getName())
                .collect(Collectors.toList());
    }

}
