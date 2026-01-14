package com.pds.api.Domain.IRepositories;

import com.pds.api.Domain.Entities.Activity;

public interface IActivitiesRepository {
    void participantActivities(String email, Activity activity);
}
