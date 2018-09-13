package com.example.booking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookingRepositoryTest {

    public static final LocalDate DATE_B = LocalDate.of(2018, 12, 2);
    public static final int SLOT_B = 7;
    public static final LocalDate DATE_A = LocalDate.of(2018, 12, 2);
    public static final LaundryRoom ROOM = LaundryRoom.FIRST_ROOM;
    public static final int SLOT_A = 8;
    public static final Booker APARTMENT_1 = Booker.APARTMENT_1;

    @Autowired
    BookingRepository bookingRepository;

    @Test
    public void findByDateAndSlot_ShouldReturn() {

        bookingRepository.save(new Booking(DATE_A, SLOT_A, APARTMENT_1, ROOM));
        bookingRepository.save(new Booking(DATE_B, SLOT_B, APARTMENT_1, ROOM));

        assertTrue(bookingRepository.findByDateAndHourAndRoom(DATE_A, SLOT_A, ROOM).isPresent());
    }

    @Test
    public void findByDateAndSlot_ShouldNotReturn() {

        bookingRepository.save(new Booking(DATE_B, SLOT_B, APARTMENT_1, ROOM));

        assertFalse(bookingRepository.findByDateAndHourAndRoom(DATE_A, SLOT_A, ROOM).isPresent());
    }

}