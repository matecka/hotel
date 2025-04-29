package dao;

import exception.HotelException;
import model.Hotel;
import java.time.LocalDate;
import java.util.Optional;

public class HotelDao extends BaseDao {

    public Optional<Hotel> getOptionalHotelById(Long hotelId) {
        return executeTransaction(session ->
                Optional.ofNullable(session.get(Hotel.class, hotelId)));
    }

    public Hotel getHotelById(Long hotelId) {
        return getOptionalHotelById(hotelId)
                .orElseThrow(() -> new HotelException("Hotel not found", LocalDate.now()));
    }

    public void saveHotel(Hotel hotel) {
        executeTransaction(session -> session.save(hotel));
    }

    public Hotel updateHotel(Hotel hotel) {
        return executeTransaction(session -> {
            session.update(hotel);
            return hotel;
        });
    }

    public boolean deleteHotel(Long hotelId) {
        return executeTransaction(session -> {
            Hotel hotel = session.get(Hotel.class, hotelId);
            if (hotel != null) {
                session.delete(hotel);
                return true;
            }
            return false;
        });
    }
}
