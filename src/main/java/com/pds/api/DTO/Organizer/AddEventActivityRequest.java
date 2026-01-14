package com.pds.api.DTO.Organizer;

import java.time.LocalDate;
import java.time.LocalTime;

public record AddEventActivityRequest(
    String EventCode,
    String Name, 
    String Description,
    String InstructorName,
    String InstructorEmail,
    String InstructorPhone,
    LocalTime Time,
    LocalDate Date
) {
    
}
