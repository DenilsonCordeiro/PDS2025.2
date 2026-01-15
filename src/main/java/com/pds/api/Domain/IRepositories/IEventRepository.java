package com.pds.api.Domain.IRepositories;

import java.util.List;
import java.util.Optional;

import com.pds.api.Domain.Entities.Activity;
import com.pds.api.Domain.Entities.Event;

public interface IEventRepository {
    void save(Event event, String adminEmail);
    void deleteByCode(String code);
    Optional<Event> findByCode(String code);
    List<Event> myEvents(String organizerEmail);
    void addActivity(String code, Activity activity);
    List<Activity> listActivities(String code);
    void joinEvent(String code, String email);
    void leaveEvent(String code, String email);
}