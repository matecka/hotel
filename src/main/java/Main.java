import dao.*;
import dto.request.*;
import dto.response.*;
import mapper.*;
import model.Customer;
import model.Payment;
import model.Reservation;
import model.enums.Status;
import service.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        HotelDao hotelDao = new HotelDao();
        HotelMapper hotelMapper = new HotelMapper();
        HotelService hotelService = new HotelService(hotelDao, hotelMapper);
        HotelRequest hotelRequest = HotelRequest.builder()
                .name("hotel2")
                .address("Ludna 7")
                .phone("555 333 345")
                .email("hotel2@mail.com").build();

        HotelResponse hotel = hotelService.createHotel(hotelRequest);
        System.out.println(hotel);
        hotelService.deleteHotel(3L);

        RoomDao roomDao = new RoomDao();
        RoomMapper roomMapper = new RoomMapper();
        RoomService roomService = new RoomService(roomDao, hotelDao, roomMapper);
        RoomRequest roomRequest = RoomRequest.builder()
                .name("B1X")
                .capacity(3)
                .description("Family Room: A room specifically designed to accommodate families, typically offering multiple beds or a sofa bed to accommodate more guests")
                .hotelId(8L)
                .status("Occupied")
                .price(new BigDecimal(550))
                .build();
        RoomResponse room = roomService.createRoom(roomRequest);
        System.out.println(room);

        CustomerDao customerDao = new CustomerDao();
        CustomerMapper customerMapper = new CustomerMapper();
        CustomerService customerService = new CustomerService(customerDao, customerMapper);
        CustomerRequest customerRequest = CustomerRequest.builder()
                .name("byleco2")
                .address("byleadres22")
                .email("bylebyle@email.com")
                .phone("333 444 222")
                .surname("aaa").build();

        CustomerResponse customer = customerService.createCustomer(customerRequest);
        System.out.println(customer);

        PaymentDao paymentDao = new PaymentDao();
        PaymentMapper paymentMapper = new PaymentMapper();
        PaymentService paymentService = new PaymentService(paymentDao, paymentMapper);
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .date(LocalDate.now())
                .status(Status.EXPIRED)
                .build();
        PaymentResponse payment = paymentService.createPayment(paymentRequest);
        System.out.println(payment);

        ReservationDao reservationDao = new ReservationDao();
        ReservationMapper reservationMapper = new ReservationMapper();
        ReservationService reservationService = new ReservationService(reservationDao, roomDao, reservationMapper, customerDao, paymentDao);
        ReservationRequest request = ReservationRequest.builder()
                .roomId(Collections.singletonList(9L))
                .startDate(LocalDate.now())
                .endDate(LocalDate.of(2024, 4, 2))
                .customerId(1L)
                .paymentId(3L)
                .build();
        ReservationResponse reservationResponse = reservationService.createReservation(request);
        System.out.println(reservationResponse);

        Reservation reservation1 = Reservation.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.of(2024, 3, 23))
                .build();

        Customer customer1 = Customer.builder()
                .address("addresssao")
                .name("Updatek")
                .email("updf")
                .surname("surwisko")
                .phone("777 777 000")
                .build();

        Payment payment1 = Payment.builder()
                .status(Status.FAILED)
                .date(LocalDate.now())
                .build();
        Reservation reservation = reservationService.updateReservation(33L, request, 6L, 15L);
        System.out.println(reservation);

    }

}
