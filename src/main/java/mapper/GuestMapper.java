package mapper;


import dto.response.GuestResponse;
import model.Guest;

public class GuestMapper {
    public GuestResponse from(Guest guest){
        return GuestResponse.builder()
                .name(guest.getName())
                .surname(guest.getSurname())
                .address(guest.getAddress())
                .phone(guest.getPhone())
                .email(guest.getEmail())
                .build();
    }
}
