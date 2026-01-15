package com.pds.api.Infrastructure.Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.pds.api.Domain.Entities.Activity;
import com.pds.api.Domain.Entities.Event;
import com.pds.api.Domain.IRepositories.IEventRepository;

@Repository
public class EventRepository implements IEventRepository {
    private final Map<String, Event> events = new HashMap<>();
    private final Map<String, List<String>> organizer_events = new HashMap<>();
    private final Map<String, List<Activity>> event_activities = new HashMap<>();
    private final Map<String, List<String>> participant_events = new HashMap<>(); 

    @Override
    public void save(Event event, String adminEmail) {
        events.put(event.getCode(), event);
        organizer_events.computeIfAbsent(adminEmail, k -> new ArrayList<>())
                       .add(event.getCode());
    }

    @Override
    public void deleteByCode(String code) {
        events.remove(code);
    }

    @Override
    public Optional<Event> findByCode(String code) {
        return Optional.ofNullable(events.get(code));
    }

    @Override
    public List<Event> myEvents(String organizerEmail) {
        List<String> codes = organizer_events.get(organizerEmail);

        if(codes == null || codes.isEmpty()) return List.of();

        return codes.stream().map(events::get)
                             .filter(Objects::nonNull)
                             .toList();
    }

    @Override
    public void addActivity(String code, Activity activity) {
        event_activities.computeIfAbsent(code, k -> new ArrayList<>())
                        .add(activity);
    }

    @Override
    public List<Activity> listActivities(String code) {
        List<Activity> activities = event_activities.get(code);

        if(activities.isEmpty()) return List.of();

        return activities;
    }

    @Override
    public void joinEvent(String code, Parti) {
        Event event = events.get(code);
        Participant user = this.user_repos

        if(event == null || email == null) return;
        else {
            event.addParticipant()
        }
    }
}
