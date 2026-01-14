package com.pds.api.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pds.api.Application.UseCases.Auth.AuthenticateUser;
import com.pds.api.DTO.Auth.LoginRequest;
import com.pds.api.DTO.Auth.LoginResponse;
import com.pds.api.DTO.Auth.MeResponse;
import com.pds.api.Domain.Entities.User;
import com.pds.api.Domain.IRepositories.IUserRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final IUserRepository userRepository;
    private final AuthenticateUser authentication;

    public AuthController(IUserRepository userRepository, AuthenticateUser authentication){
        this.userRepository = userRepository;
        this.authentication = authentication;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> Login (@RequestBody LoginRequest req, HttpSession session){
        List<String> Errors = new ArrayList<>();
        
        
        if(!this.authentication.checkCredentials(req.Email(), req.Password())){
            Errors.add("- credenciais invalidas");
        }
        
        if(!Errors.isEmpty()){
            return ResponseEntity.badRequest()
                                 .body(LoginResponse.error(Errors));
        }

        User user = this.userRepository.findByEmail(req.Email()).get();
        
        session.setAttribute("USER", user.getEmail());

        return ResponseEntity.ok(LoginResponse.success(user.getName(), user.getEmail(), user.getPhone(), user.getRole()));
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(HttpSession session) {
        String email = (String) session.getAttribute("USER");

        if(email == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Optional<User> EUser = this.userRepository.findByEmail(email);

        if(EUser.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        User user = EUser.get();

        return ResponseEntity.ok(new MeResponse(user.getName(), user.getEmail(), user.getPhone()));                          
    }
}
