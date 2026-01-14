package com.pds.api.Infrastructure.Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pds.api.Domain.Entities.Activity;
import com.pds.api.Domain.IRepositories.IActivitiesRepository;

public class ActivitiesRepository implements IActivitiesRepository{
    private final Map<String, List<Activity>> participant_activities = new HashMap<>();

    @Override
    public void participantActivities(String email, Activity activity) {
        participant_activities.computeIfAbsent(email, k -> new ArrayList<>())
                              .add(activity);
    }
}
