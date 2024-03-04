import dao.*;
import dto.request.*;
import dto.response.*;
import mapper.*;
import model.enums.Status;
import service.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        HotelDao hotelDao = new HotelDao();
//        HotelMapper hotelMapper = new HotelMapper();
//        HotelService hotelService = new HotelService(hotelDao, hotelMapper);
//        HotelRequest hotelRequest = HotelRequest.builder()
//                .name("hotel2")
//                .address("Ludna 7")
//                .phone("555 333 345")
//                .email("hotel2@mail.com").build();
//
//        HotelResponse hotel = hotelService.createHotel(hotelRequest);
//        System.out.println(hotel);
//        UpdateHotelRequest updatedHotel = UpdateHotelRequest.builder().id(1).name("updatedHotel").color("blue").build();
//        hotelService.updateHotel(updatedHotel);
//        hotelService.deleteHotel(3);


        RoomDao roomDao = new RoomDao();
        RoomMapper roomMapper = new RoomMapper();
        RoomService roomService = new RoomService(roomDao, hotelDao, roomMapper);
//        RoomRequest roomRequest = RoomRequest.builder()
//                .name("B1X")
//                .capacity(3)
//                .description("Family Room: A room specifically designed to accommodate families, typically offering multiple beds or a sofa bed to accommodate more guests")
//                .hotelId(3L)
//                .status("Occupied")
//                .price(new BigDecimal(550))
//                .build();
//        RoomResponse room = roomService.createRoom(roomRequest);
//        System.out.println(room);
//
//        UpdateRoomRequest updateRoomRequest = UpdateRoomRequest.builder()
//                .id(27L)
//                .name("B2X")
//                .capacity(2)
//                .description("Deluxe Room: A room that offers more space and amenities compared to a standard room, such as upgraded furnishings, a larger bed, or better views")
//                .status("Available")
//                .price(new BigDecimal(755))
//                .build();
//        RoomResponse roomResponse = roomService.updateRoom(updateRoomRequest);
//        System.out.println(roomResponse);
        //deleteRoom - errors
//        roomService.deleteRoom(2L);

//        Hotel hotel = hotelService.getHotelById(4);
//        System.out.println(hotel);

//        UpdateRoomRequest updateRoomRequest = UpdateRoomRequest.builder().name("updatedRoomOther").color("yellow").length(2).width(2).id(9).build();
//        roomService.updateRoom(updateRoomRequest);
//        roomService.deleteRoom(8L);


//        ReservationRequest request = ReservationRequest.builder()
//                .startDate(LocalDate.now())
//                .endDate(LocalDate.of(2024, 2, 4))
//                .roomId(List.of(2L))
//                .build();
//        ReservationDao reservationDao = new ReservationDao();
//
////
//        ReservationMapper reservationMapper = new ReservationMapper();
//        ReservationService reservationService = new ReservationService(reservationDao, roomService, reservationMapper, roomDao);
//
//
//        UpdateReservationRequest updateReservationRequest = UpdateReservationRequest.builder()
//                .startDate(LocalDate.now())
//                .endDate(LocalDate.of(2024, 5, 12)).build();
//
//        ReservationResponse reservationResponse = reservationService.updateReservation(updateReservationRequest, 5L);
//        System.out.println(reservationResponse);
//


        CustomerDao customerDao = new CustomerDao();
        CustomerMapper customerMapper  = new CustomerMapper();
        CustomerService customerService = new CustomerService(customerDao, customerMapper);
      CustomerRequest customerRequest = CustomerRequest.builder()
                .name("byleco")
                .address("byleadres")
                .email("byle@email.com")
                .phone("555 444 222")
                .surname("ddd").build();

        CustomerResponse customer = customerService.createCustomer(customerRequest);
        System.out.println(customer);

        PaymentDao paymentDao = new PaymentDao();
        PaymentMapper paymentMapper = new PaymentMapper();
        PaymentService paymentService = new PaymentService(paymentDao, paymentMapper);
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .date(LocalDate.now())
                .status(Status.COMPLETED)
                .build();
        PaymentResponse payment = paymentService.createPayment(paymentRequest);
        System.out.println(payment);


//        ReservationDao reservationDao = new ReservationDao();
//        ReservationMapper reservationMapper = new ReservationMapper();
//        ReservationService reservationService = new ReservationService(reservationDao, roomService, reservationMapper, customerDao);


    }
}
