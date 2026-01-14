package com.pds.api.DTO.Organizer;

import java.util.List;

import com.pds.api.Domain.Entities.Event;

public record MyEventsResponse(boolean Success, List<Event> Events, String Message) {
}