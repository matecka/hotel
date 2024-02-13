package service;

import dao.GuestDao;
import dto.request.GuestRequest;
import dto.request.UpdateGuestRequest;
import exception.GuestException;
import model.Guest;

import java.time.LocalDate;

public class GuestService {

    private GuestDao guestDao;

    public GuestService(GuestDao guestDao) {
        this.guestDao = guestDao;
    }

    public void updateGuest(UpdateGuestRequest updateGuestRequest) {
        guestDao.getGuestById(updateGuestRequest.getId()).orElseThrow(() -> new GuestException("guest not found", LocalDate.now()));

        Guest guest = Guest.builder()
                .id(updateGuestRequest.getId())
                .name(updateGuestRequest.getName())
                .surname(updateGuestRequest.getSurname())
                .phone(updateGuestRequest.getPhone())
                .email(updateGuestRequest.getEmail())
                .address(updateGuestRequest.getAddress()).build();

        guestDao.updateGuest(guest);
    }

    public void createGuest(GuestRequest guestRequest) {
        Guest guest = Guest.builder()
                .name(guestRequest.getName())
                .surname(guestRequest.getSurname())
                .phone(guestRequest.getPhone())
                .email(guestRequest.getEmail())
                .address(guestRequest.getAddress()).build();

        guestDao.createGuest(guest);
    }

    public void deleteGuest(Long id) {
        guestDao.deleteGuest(id);
    }

    public Guest getGuestById(Long id) {
        return guestDao.getGuestById(id).orElseThrow(() -> new GuestException("guest not found", LocalDate.now()));
    }
}
