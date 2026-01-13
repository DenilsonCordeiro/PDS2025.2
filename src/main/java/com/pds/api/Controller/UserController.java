package com.pds.api.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pds.api.DTO.Users.RegisterRequest;
import com.pds.api.DTO.Users.RegisterResponse;
import com.pds.api.Domain.Entities.Admin;
import com.pds.api.Domain.Entities.Participant;
import com.pds.api.Domain.Entities.User;
import com.pds.api.Domain.IRepositories.IUserRepository;
import com.pds.api.Utils.Validator;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserRepository userRepository;

    public UserController(IUserRepository userRepository){
        this.userRepository = userRepository;
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
        
        //retorna usuario
        this.userRepository.save(user);

        return ResponseEntity.ok()
                             .body(RegisterResponse.success(user.getName(),
                                                            user.getEmail(),
                                                            user.getPhone(),
                                                            req.Role()));
    }
}
