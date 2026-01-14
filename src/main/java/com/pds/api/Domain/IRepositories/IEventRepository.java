package com.pds.api.Domain.IRepositories;

import java.util.List;
import java.util.Optional;

import com.pds.api.Domain.Entities.Event;

public interface IEventRepository {
    void save(Event event, String adminEmail);
    void deleteByCode(String code);
    Optional<Event> findByCode(String code);
    List<Event> myEvents(String organizerEmail);
}