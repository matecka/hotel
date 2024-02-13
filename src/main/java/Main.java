import dao.HotelDao;
import dao.ReservationDao;
import dao.RoomDao;
import dto.request.HotelRequest;
import dto.request.ReservationRequest;
import dto.request.RoomRequest;
import dto.request.UpdateReservationRequest;
import dto.response.ReservationResponse;
import dto.response.RoomResponse;
import mapper.ReservationMapper;
import mapper.RoomMapper;
import model.Room;
import service.HotelService;
import service.ReservationService;
import service.RoomService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HotelDao hotelDao = new HotelDao();
        HotelService hotelService = new HotelService(hotelDao);
        HotelRequest hotelRequest = HotelRequest.builder()
                .name("hotel2")
                .address("Ludna 7")
                .phone("555 333 345")
                .email("hotel2@mail.com").build();

        hotelService.createHotel(hotelRequest);
//        UpdateHotelRequest updatedHotel = UpdateHotelRequest.builder().id(1).name("updatedHotel").color("blue").build();
//        hotelService.updateHotel(updatedHotel);
//        hotelService.deleteHotel(3);


        RoomMapper roomMapper = new RoomMapper();
        RoomDao roomDao = new RoomDao();
        RoomService roomService = new RoomService(roomDao, hotelDao, roomMapper);

        RoomRequest roomRequest = RoomRequest.builder().hotelId(1L).name("A23").capacity(1).description("small, balcony, wi-fi").price(new BigDecimal(153.00)).status("free").build();
        RoomResponse room = roomService.createRoom(roomRequest);
        System.out.println(room);
//        Room room = roomService.getRoomById(12L);
//        System.out.println(room);

//        Hotel hotel = hotelService.getHotelById(4);
//        System.out.println(hotel);

//        UpdateRoomRequest updateRoomRequest = UpdateRoomRequest.builder().name("updatedRoomOther").color("yellow").length(2).width(2).id(9).build();
//        roomService.updateRoom(updateRoomRequest);
//        roomService.deleteRoom(8L);

        ReservationRequest request = ReservationRequest.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.of(2024, 2, 4))
                .roomId(List.of(2L))
                .build();
        ReservationDao reservationDao = new ReservationDao();
//
//
        ReservationMapper reservationMapper = new ReservationMapper();
        ReservationService reservationService = new ReservationService(reservationDao, roomService,reservationMapper, roomDao);


        UpdateReservationRequest updateReservationRequest = UpdateReservationRequest.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.of(2024, 5, 12)).build();

        ReservationResponse reservationResponse = reservationService.updateReservation(updateReservationRequest, 5L);
        System.out.println(reservationResponse);
    }
}
