package mapper;

import dto.response.HotelResponse;
import model.Hotel;

public class HotelMapper {
    public HotelResponse from(Hotel hotel){
        return HotelResponse.builder()
                .name(hotel.getName())
                .address(hotel.getAddress())
                .phone(hotel.getPhone())
                .email(hotel.getEmail())
                .build();
    }
}
