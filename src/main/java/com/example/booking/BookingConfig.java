package com.example.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Slf4j
@Configuration
public class BookingConfig {

    @Bean
    CommandLineRunner loadDatabase(BookingRepository repository) {
        return args -> log.info("*****Saved booking: {}", repository.save(
                new Booking(LocalDate.of(2018, 10, 11),
                        7,
                        Booker.APARTMENT_1,
                        LaundryRoom.FIRST_ROOM)
        ));
    }

}
