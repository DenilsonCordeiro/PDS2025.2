package com.pds.api.Controller;

<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/participant")
public class ParticipantController {
   
   public ParticipantController(){
      
   } 
}
=======
import org.springframework.http.ResponseEntity;

import com.pds.api.DTO.Participants.JoinRequest;
import com.pds.api.DTO.Participants.JoinResponse;
import com.pds.api.Domain.Entities.Admin;
import com.pds.api.Domain.Entities.Participant;
import com.pds.api.Domain.Entities.User;
import com.pds.api.Domain.IRepositories.IUserRepository;
import com.pds.api.Utils.Validator;

import com.pds.api.Infrainstructure.Repositories.EventRepository;

@RestController
@RequestMapping("/participants")
public class ParticipantController {
    private final IUserRepository userRepository;
    private final IEventRepository eventRepository;

    public ParticipantController(IUserRepository userRepository, IEventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    };

    @PostMapping("{eventCode}/join-event")
    public ResponseEntity<JoinResponse> Join (@PathVariable String eventCode, HttpSession session) {
        List<String> Errors = new ArrayList<>();

        String email = (String) session.getAttribute("USER");
        Optional<User> EUser = this.userRepository.findByEmail(email);
        Optional<Event> event = eventRepository.findByCode(eventCode);

        if(!EUser.isPresent()) Errors.add("- usuario nao encontrado")
        if(!Validator.emailValidator(email)) Errors.add("- email invalido")
        if(!event.isPresent()) Errors.add("- evento não encontrado")

        if(!Errors.isEmpty()) {
            return ResponseEntity.badRequest().body(JoinResponse.error(Errors))
        }

        event.addParticipant(EUser);
        return ResponseEntity.ok();
    }
}
>>>>>>> a63ede2 (rota participante)
