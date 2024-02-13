package mapper;

import dto.request.RoomRequest;
import dto.response.RoomResponse;
import model.Room;

public class RoomMapper {
    public RoomResponse from(Room room) {
        return RoomResponse.builder()
                .name(room.getName())
                .description(room.getDescription())
                .status(room.getStatus())
                .capacity(room.getCapacity())
                .price(room.getPrice())
                .hotelId(room.getHotel().getId())
                .hotelName(room.getHotel().getName()).build();
    }
}
