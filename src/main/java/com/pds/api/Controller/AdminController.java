package com.pds.api.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pds.api.Application.UseCases.Auth.AuthenticateUser;
import com.pds.api.DTO.Organizer.AddEventActivityRequest;
import com.pds.api.DTO.Organizer.AddEventActivityResponse;
import com.pds.api.DTO.Organizer.AddEventRequest;
import com.pds.api.DTO.Organizer.AddEventResponse;
import com.pds.api.DTO.Organizer.MyEventsResponse;
import com.pds.api.Domain.Entities.Activity;
import com.pds.api.Domain.Entities.Address;
import com.pds.api.Domain.Entities.Date;
import com.pds.api.Domain.Entities.Event;
import com.pds.api.Domain.Entities.Instructor;
import com.pds.api.Domain.Entities.Time;
import com.pds.api.Domain.IRepositories.IEventRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final IEventRepository eventRepository;
    private final AuthenticateUser autheticate;

    public AdminController(IEventRepository eventRepository, AuthenticateUser authenticate){
        this.eventRepository = eventRepository;
        this.autheticate = authenticate;
    }

    @PostMapping("/add-event")
    public ResponseEntity<AddEventResponse> AddEvent(@RequestBody AddEventRequest req, HttpSession session){
        String Email = (String) session.getAttribute("USER");
        String Role = this.autheticate.getRole(Email);

        if(!Role.equals("ADMIN")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AddEventResponse("usuario não autorizado"));


        LocalDate date = req.Date();
        LocalTime time = req.Time();

        Event event = new Event(req.Name(),
                                req.Description(),
                                req.Category(),
                                new Date(date.getDayOfMonth(), date.getMonthValue(), date.getYear()),
                                new Time(time.getHour(), time.getMinute()),
                                new Address(req.State(), req.City(), req.Street(), req.Number()), req.Price());

        this.eventRepository.save(event, Email);

        System.out.println(event.getAddress().toString());

        return ResponseEntity.ok(new AddEventResponse("Evento adicionado com sucesso"));
    }

    @GetMapping("/my-events")
    public ResponseEntity<MyEventsResponse> MyEvents(HttpSession session){
        String Email = (String) session.getAttribute("USER");
        String Role = this.autheticate.getRole(Email);

        if(!Role.equals("ADMIN")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(new MyEventsResponse(false,
                                                            List.of(),
                                                            "usuario não autorizado"));
        }

        List<Event> events = this.eventRepository.myEvents(Email);

        return ResponseEntity.ok()
                             .body(new MyEventsResponse(true, events, "eventos buscados com sucesso"));
    }

    @PostMapping("/add-activity")
    public ResponseEntity<AddEventActivityResponse>  AddActivity (@RequestBody AddEventActivityRequest req, HttpSession session){
        String Email = (String) session.getAttribute("USER");
        String Role = this.autheticate.getRole(Email);

        if(!Role.equals("ADMIN")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AddEventActivityResponse("usuario não autorizado"));

        LocalDate date = req.Date();
        LocalTime time = req.Time();

        Activity activity = new Activity(req.Name(),
                                         req.Description(),
                                         new Instructor(req.InstructorName(), req.InstructorEmail(), req.InstructorPhone()),
                                         new Date(date.getDayOfMonth(), date.getMonthValue(), date.getYear()),
                                         new Time(time.getHour(), time.getMinute()));

        this.eventRepository.addActivity(req.EventCode(), activity);

        return ResponseEntity.ok()
                             .body(new AddEventActivityResponse("atividade adicionada com sucesso"));
    }
}
