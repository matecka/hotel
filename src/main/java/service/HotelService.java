package service;

import dao.HotelDao;
import dto.request.HotelRequest;
import dto.request.UpdateHotelRequest;

import exception.HotelException;
import model.Hotel;

import java.time.LocalDate;

public class HotelService {

    private HotelDao hotelDao;

    public HotelService(HotelDao hotelDao) {
        this.hotelDao = hotelDao;
    }

    public void updateHotel(UpdateHotelRequest updateHotelRequest) {
        hotelDao.getHotelById(updateHotelRequest.getId()).orElseThrow(() -> new HotelException("hotel not found", LocalDate.now()));
        Hotel hotel = Hotel.builder().id(updateHotelRequest.getId()).name(updateHotelRequest.getName()).address(updateHotelRequest.getAddress()).email(updateHotelRequest.getEmail()).phone(updateHotelRequest.getPhone()).build();
        hotelDao.updateHotel(hotel);
    }

    public void createHotel(HotelRequest hotelRequest) {
        Hotel hotel = Hotel.builder().name(hotelRequest.getName()).address(hotelRequest.getAddress()).phone(hotelRequest.getPhone()).email(hotelRequest.getEmail()).build();
        hotelDao.saveHotel(hotel);
    }

    public void deleteHotel(Long id) {
        hotelDao.deleteHotel(id);
    }

    public Hotel getHotelById(Long id) {
        return hotelDao.getHotelById(id).orElseThrow(() -> new HotelException("hotel not found", LocalDate.now()));
    }


}
