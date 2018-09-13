package com.example.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "api/v1")
public class BookingController {

    @Autowired
    private final BookingRepository bookingRepository;

    public BookingController(BookingRepository repository) {
        this.bookingRepository = repository;
    }

    @GetMapping("/bookings")
    public ResponseEntity<List> allBookings() {
        return ResponseEntity.ok().body(bookingRepository.findAll());
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity getBooking(@PathVariable("id") Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            return ResponseEntity.ok().body(booking.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/bookings")
    public ResponseEntity book(@Valid @RequestBody Booking booking) {
        if (timeAndRoomIsAvailable(booking.getDate(), booking.getHour(), booking.getRoom())) {
            log.info("*****Saved booking: " + booking);
            @Valid Booking savedBooking = bookingRepository.save(booking);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedBooking.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
        log.info("*****Bad request for: " + booking);
        return ResponseEntity.badRequest().build();
    }

    private boolean timeAndRoomIsAvailable(LocalDate date, int slot, LaundryRoom room) {
        return !bookingRepository.findByDateAndHourAndRoom(date, slot, room).isPresent();
    }

    @DeleteMapping("/bookings/{id}")
    public void cancel(@PathVariable("id") Long id) {
        bookingRepository.deleteById(id);
        log.info("*****Deleted booking with id {}", id);
    }
}
