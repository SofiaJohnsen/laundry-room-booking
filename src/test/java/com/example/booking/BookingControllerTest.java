package com.example.booking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingControllerTest {

    private static final String HTTP_LOCALHOST = "http://localhost:";
    private static final String PREFIX_API = "/api/v1";

    @Autowired
    BookingRepository bookingRepository;

    @Value("${local.server.port}")
    int port;

    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void initialGetShouldReturnOk() {
        ResponseEntity<List> booking = restTemplate.getForEntity(
                HTTP_LOCALHOST + port + PREFIX_API + "/bookings",
                List.class);

        assertThat(booking.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void postShouldReturnCreated() {
        Booking body = new Booking(LocalDate.of(2018, 12, 9), 7, Booker.APARTMENT_2, LaundryRoom.SECOND_ROOM);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Booking> entity = new HttpEntity<>(body, headers);

        ResponseEntity booking = restTemplate.postForEntity(
                HTTP_LOCALHOST + port + PREFIX_API + "/bookings",
                entity,
                Booking.class);

        assertThat(booking.getStatusCode(), is(HttpStatus.CREATED));
    }
}