package com.pds.api.Controller;
import com.pds.api.Utils.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pds.api.DTO.Users.RegisterRequest;
import com.pds.api.DTO.Users.RegisterResponse;
import com.pds.api.DTO.Users.UpdateRequest;
import com.pds.api.DTO.Users.UpdateResponse;
import com.pds.api.Domain.Entities.Admin;
import com.pds.api.Domain.Entities.Participant;
import com.pds.api.Domain.Entities.User;
import com.pds.api.Domain.IRepositories.IUserRepository;
import com.pds.api.Utils.Validator;
import com.pds.api.Utils.IPasswordEncoder;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserRepository userRepository;
    private final IPasswordEncoder passwordEncoder; // <--- Injeção

    public UserController(IUserRepository userRepository,  IPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> Register (@RequestBody RegisterRequest req){
        List<String> Errors = new ArrayList<>();

        Optional<User> EUser = this.userRepository.findByEmail(req.Email());

        //transforma em chain of commands
        if(EUser.isPresent()) Errors.add("- email ja cadastrado");
        if(!Validator.emailValidator(req.Email())) Errors.add("- email invalido");
        if(!Validator.sizeValidator(req.Password(), 4, 32)) Errors.add("- tamanho invalido");
        //if(!Validator.phoneValidator(req.Phone())) Errors.add("- telefone invalido");

        if(!Errors.isEmpty()){
            return ResponseEntity.badRequest()
                                 .body(RegisterResponse.error(Errors));
        }

        User user = req.Role() == "ADMIN" ? new Admin() : new Participant();

        user.setName(req.Name());
        user.setEmail(req.Email());
        user.setPhone(req.Phone());
        user.setPassword(this.passwordEncoder.encode(req.Password()));
        user.setRole(req.Role());
        
        //retorna usuario
        this.userRepository.save(user);

        return ResponseEntity.ok()
                             .body(RegisterResponse.success(user.getName(),
                                                            user.getEmail(),
                                                            user.getPhone(),
                                                            user.getRole()));
    }    

    @PatchMapping("/{email}/update")
    public ResponseEntity<UpdateResponse> Update (@PathVariable String email,
                                                  @RequestBody UpdateRequest req,
                                                  HttpSession session){
        String logedEmail = (String) session.getAttribute("USER");
        User user = this.userRepository.findByEmail(logedEmail).get();
        
        if(req.Name() != null) user.setName(req.Name());
        if(req.Email() != null) user.setEmail(req.Email());
        if(req.Phone() != null) user.setPhone(req.Phone());
        if(req.Password() != null) user.setPassword(req.Password());

        this.userRepository.save(user);

        return ResponseEntity.ok()
                             .body(new UpdateResponse("atualizado com sucesso"));
    }
}
