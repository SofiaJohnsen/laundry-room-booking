package com.example.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Entity
public class Booking {

    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @FutureOrPresent
    private LocalDate date;

    @NotNull
    @Min(7)
    @Max(21)
    private int hour;

    @NotNull
    private Booker booker;

    @NotNull
    private LaundryRoom room;

    protected Booking() {
    }

    public Booking(LocalDate date, int slot, Booker booker, LaundryRoom room) {
        this.date = date;
        this.hour = slot;
        this.booker = booker;
        this.room = room;
    }
}
