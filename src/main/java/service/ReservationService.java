package service;

import dao.CustomerDao;
import dao.PaymentDao;
import dao.ReservationDao;
import dao.RoomDao;
import dto.request.ReservationRequest;
import dto.response.ReservationResponse;
import mapper.ReservationMapper;
import model.Customer;
import model.Reservation;
import model.ReservationDetails;
import model.Room;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ReservationService {

    private ReservationDao reservationDao;
    private ReservationMapper reservationMapper;
    private RoomDao roomDao;
    private CustomerDao customerDao;
    private PaymentDao paymentDao;

    public ReservationService(ReservationDao reservationDao,
                              RoomDao roomDao,
                              ReservationMapper reservationMapper,
                              CustomerDao customerDao,
                              PaymentDao paymentDao) {
        this.reservationDao = reservationDao;
        this.reservationMapper = reservationMapper;
        this.roomDao = roomDao;
        this.customerDao = customerDao;
        this.paymentDao = paymentDao;
    }

    public ReservationResponse getReservationById(Long id) {
        Reservation reservation = reservationDao.getReservationById(id);
        return reservationMapper.from(reservation);
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation();
        Set<Room> rooms = reservationRequest.getRoomId().stream()
                .map(roomId -> roomDao.getRoomById(roomId))
                .collect(Collectors.toSet());

        reservation = upsertReservation(
                reservation,
                reservationRequest,
                reservationRequest.getCustomerId(),
                reservationRequest.getPaymentId()
        );

        Set<ReservationDetails> details = createReservationDetails(rooms, reservation);
        reservation.setReservationDetails(details);

        reservationDao.createReservation(reservation);
        return reservationMapper.from(reservation);
    }


    public Set<ReservationDetails> createReservationDetails(Set<Room> rooms, Reservation reservation) {
        Set<ReservationDetails> reservationDetailsSet = new HashSet<>();
        for (Room room : rooms) {
            ReservationDetails reservationDetails = ReservationDetails.builder()
                    .reservation(reservation)
                    .room(room)
                    .build();
            reservationDetailsSet.add(reservationDetails);
        }
        return reservationDetailsSet;
    }

    public Reservation updateReservation(Long reservationId,
                                         ReservationRequest reservationRequest,
                                         Long customerId,
                                         Long paymentId) {

        Reservation reservationById = reservationDao.getReservationById(reservationId);
        upsertReservation(reservationById, reservationRequest, customerId, paymentId);

        updateOrCreateReservationDetails(reservationById, reservationRequest);

        Reservation reservation = reservationDao.updateReservation(reservationById);

        return reservation;
    }

    public void updateOrCreateReservationDetails(Reservation reservation,
                                                 ReservationRequest reservationRequest) {

        Set<ReservationDetails> existingDetails = reservation.getReservationDetails();
        existingDetails = existingDetails != null ? existingDetails : new HashSet<>();

        List<Long> newRoomIds = reservationRequest.getRoomId();
        existingDetails.removeIf(detail -> !newRoomIds.contains(detail.getRoom().getId()));
        addNewDetails(existingDetails, newRoomIds, reservation);
    }


    public Reservation upsertReservation(Reservation reservation,
                                         ReservationRequest reservationRequest,
                                         Long customerId,
                                         Long paymentId) {

        reservation.setStartDate(reservationRequest.getStartDate());
        reservation.setEndDate(reservationRequest.getEndDate());
        reservation.setCustomer(customerDao.getCustomerById(customerId));
        reservation.setPayment(paymentDao.getPaymentById(paymentId));

        return reservation;
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteReservation(id);
    }

    public void isExistsRoom(List<Long> roomIds) {
    }

    public Set<Room> mapByIds(List<Long> newRoomIds) {
        return newRoomIds.stream()
                .map(roomDao::getRoomById)
                .collect(Collectors.toSet());
    }

    public void addNewDetails(Set<ReservationDetails> existingDetails,
                              List<Long> newRoomIds,
                              Reservation reservationById) {

        Set<ReservationDetails> newDetails = new HashSet<>();
        for (Long roomId : newRoomIds) {
            boolean containsRoom = existingDetails.stream()
                    .anyMatch(detail -> detail.getRoom().getId().equals(roomId));
            if (!containsRoom) {
                Room room = roomDao.getRoomById(roomId);
                newDetails.add(ReservationDetails.builder()
                        .reservation(reservationById)
                        .room(room)
                        .build());
            }
        }
        existingDetails.addAll(newDetails);
    }


    public List<ReservationResponse> getReservationsInDateRange(LocalDate from, LocalDate to) {
        return reservationDao.getAllReservations()
                .stream()
                .filter(r -> r.getStartDate().isAfter(from) && r.getEndDate().isBefore(to))
                .map(reservationMapper::from)
                .collect(Collectors.toList());
    }

    public List<ReservationResponse> getReservationsByNameAndSurname(String name, String surname) {
        return reservationDao.getAllReservations()
                .stream()
                .filter(r -> r.getCustomer().getName().equals(name) && r.getCustomer().getSurname().equals(surname))
                .map(reservationMapper::from)
                .collect(Collectors.toList());
    }

    public Map<Customer, Long> getCustomerReservationCount() {
        return reservationDao.getAllReservations()
                .stream().collect(Collectors.groupingBy(r -> r.getCustomer(), Collectors.counting()));
    }

    public int getTotalPersonCount() {
        int sum = reservationDao.getAllReservations()
                .stream()
                .mapToInt(r -> r.getPersonCount())
                .sum();
        return sum;
    }

    public int getTotalPersonCountForHotel(Long hotelId) {
        int sum = reservationDao.getAllReservations()
                .stream()
                .filter(r -> isReservationForHotel(r, hotelId))
                .mapToInt(r -> r.getPersonCount())
                .sum();
        return sum;
    }

    private boolean isReservationForHotel(Reservation reservation, Long hotelId) {
        return reservation.getReservationDetails()
                .stream()
                .anyMatch(r -> r.getRoom().getHotel().getId().equals(hotelId));
    }

    //podliczyć pobyty w danym miesiącu br
    public Long countAllReservationsInMonth(String monthName) {
        Month month = Month.valueOf(monthName.toUpperCase());
        LocalDate startOfMonth = LocalDate.of(LocalDate.now().getYear(), month, 1);
        LocalDate endOfMonth = LocalDate.of(LocalDate.now().getYear(), month, startOfMonth.lengthOfMonth());
        return reservationDao.getAllReservations().stream()
                .filter(r -> {
                    LocalDate startDate = r.getStartDate();
                    LocalDate endDate = r.getEndDate();
                    // Usuwamy niepoprawne rezerwacje
                    if (startDate.isAfter(endOfMonth)) return false;
                    if (endDate.isBefore(startOfMonth)) return false;
                    return true;
                }).count();
    }

    //Napisz metodę która liczy ile kosztował pobyt w hotelu dla danej osoby w danych dniach
    public BigDecimal calculateStayCost(LocalDate from, LocalDate to, Long customerId) {
        List<Reservation> reservations = reservationDao.getAllReservations()
                .stream().filter(r -> r.getCustomer().getId().equals(customerId))
                .filter(r -> from.isAfter(r.getStartDate())
                        && to.isBefore(r.getEndDate())).collect(Collectors.toList());
        return reservations.stream().flatMap(r -> r.getReservationDetails()
                .stream().map(rd -> {
                    BigDecimal price = rd.getRoom().getPrice();
                    LocalDate startDate = rd.getReservation().getStartDate();
                    LocalDate endDate = rd.getReservation().getEndDate();
                    int days = startDate.until(endDate).getDays();
                    BigDecimal multiply = price.multiply(new BigDecimal(days));
                    return multiply;
                })).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateReservationCost(Reservation reservation) {
        long nights = reservation.getEndDate().toEpochDay() - reservation.getStartDate().toEpochDay();
        return reservation.getReservationDetails().stream()
                .map(d -> d.getRoom().getPrice().multiply(BigDecimal.valueOf(nights)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    //policz całkowity przychód hotela
    public BigDecimal calculateIncomeByHotelId(Long hotelId) {
        BigDecimal totalIncome = BigDecimal.ZERO;
        List<Reservation> reservations = reservationDao.getAllReservations();
        for (Reservation reservation : reservations) {
            boolean belongsToHotel = reservation.getReservationDetails().stream()
                    .anyMatch(d -> d.getRoom().getHotel().getId().equals(hotelId));
            if (belongsToHotel) {
                BigDecimal reservationCost = calculateReservationCost(reservation);
                totalIncome = totalIncome.add(reservationCost);
            }
        }
        return totalIncome;
    }
}