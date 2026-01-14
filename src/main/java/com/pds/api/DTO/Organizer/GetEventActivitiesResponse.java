package com.pds.api.DTO.Organizer;

import java.util.List;

import com.pds.api.Domain.Entities.Activity;

public record GetEventActivitiesResponse(boolean Success, List<Activity> Activities, String Message) {
} 
