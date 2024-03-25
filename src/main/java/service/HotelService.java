package service;

import dao.HotelDao;
import dto.request.HotelRequest;
import dto.request.UpdateHotelRequest;
import dto.response.HotelResponse;
import mapper.HotelMapper;
import model.Hotel;

public class HotelService {

    private HotelDao hotelDao;
    private HotelMapper hotelMapper;

    public HotelService(HotelDao hotelDao, HotelMapper hotelMapper) {
        this.hotelDao = hotelDao;
        this.hotelMapper = hotelMapper;
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

    public HotelResponse createHotel(HotelRequest hotelRequest) {
        Hotel hotel = Hotel.builder()
                .name(hotelRequest.getName())
                .address(hotelRequest.getAddress())
                .phone(hotelRequest.getPhone())
                .email(hotelRequest.getEmail()).build();
        hotelDao.saveHotel(hotel);
        return hotelMapper.from(hotel);

    }

    public void deleteHotel(Long id) {
        hotelDao.deleteHotel(id);
    }

    public HotelResponse getHotelById(Long id) {
        Hotel hotel = hotelDao.getHotelById(id);
        return hotelMapper.from(hotel);
    }

}
