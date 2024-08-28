import dao.*;
import dto.request.*;
import dto.response.*;
import mapper.*;
import service.*;
import model.Customer;
import model.Payment;
import model.Reservation;
import model.enums.Status;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HotelDao hotelDao = new HotelDao();
        HotelMapper hotelMapper = new HotelMapper();
        RoomMapper roomMapper = new RoomMapper();
        HotelService hotelService = new HotelService(hotelDao, hotelMapper, roomMapper);
        HotelRequest hotelRequest = HotelRequest.builder()
                .name("hotel2")
                .address("Ludna 7")
                .phone("555 333 345")
                .email("hotel2@mail.com").build();

        HotelResponse hotel = hotelService.createHotel(hotelRequest);
        System.out.println(hotel);
        hotelService.deleteHotel(3L);
        ReservationDetailsDao reservationDetailsDao = new ReservationDetailsDao();
        ReservationDetailsMapper reservationDetailsMapper = new ReservationDetailsMapper();
        ReservationDetailsService reservationDetailsService = new ReservationDetailsService(reservationDetailsDao, reservationDetailsMapper);
        RoomDao roomDao = new RoomDao();
//        RoomMapper roomMapper = new RoomMapper();
        RoomService roomService = new RoomService(roomDao, hotelDao, roomMapper );
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
                .roomId(Arrays.asList(9L, 36L, 38L))
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
                .id(1l)
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
        Reservation reservation = reservationService.updateReservation(272L, request, 1L, 3L);
        System.out.println(reservation);

//        System.out.println(customerService.searchCustomer("byleco2", "aaa"));

        EmployeeDao employeeDao = new EmployeeDao();
        EmployeeMapper employeeMapper = new EmployeeMapper();
        EmployeeService employeeService = new EmployeeService(employeeDao, employeeMapper);
        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .name("Em1")
                .surname("Kowalska")
                .hireDate(LocalDate.of(2020, 4, 12))
                .position("Manager")
                .email("manager@hotel.pl")
                .phone("45634562")
                .salary(new BigDecimal(10000.0))
                .build();
        EmployeeResponse employee = employeeService.createEmployee(employeeRequest);
        System.out.println(employee);


//        List<EmployeeResponse> em1 = employeeService.searchEmployee("Em1");
//        System.out.println(em1);




        MessageDao messageDao = new MessageDao();
        MessageMapper messageMapper = new MessageMapper();
        MessageService messageService = new MessageService(messageDao, messageMapper, customerDao, reservationDetailsDao);
        MessageRequest messageRequest = MessageRequest.builder()
                .customer(1l)
                .dateTime(LocalDateTime.now())
                .message("some text Hello Hotel")
                .reservationDetails(273L)
                .build();

        MessageResponse messageResponse = messageService.createMessage(messageRequest);
        System.out.println(messageResponse);
        System.out.println();
//        Map<Customer, Long> amountReservation = customerService.getAmountReservation();
//        System.out.println(amountReservation);
        System.out.println();
//
        List<Customer> byleco = customerDao.findByCustomerName("byleco");
        System.out.println(byleco);

        LocalDate from = LocalDate.of(2024, 04, 02);
        LocalDate to = LocalDate.of(2024, 05, 13);

        List<ReservationDetailsResponse> allRoomReserved = reservationDetailsService.getAllRoomReserved(from, to);
        System.out.println();
        System.out.println(allRoomReserved);

    }
}
