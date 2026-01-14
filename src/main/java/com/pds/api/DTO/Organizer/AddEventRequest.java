package com.pds.api.DTO.Organizer;

import java.time.LocalDate;
import java.time.LocalTime;

public record AddEventRequest(
    String Name,
    String Description,
    String Category,
    LocalDate Date,
    LocalTime Time,
    String City,
    String State,
    String Street,
    int Number,
    float Price
) {
}