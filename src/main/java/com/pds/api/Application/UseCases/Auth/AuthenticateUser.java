package com.pds.api.Application.UseCases.Auth;
import com.pds.api.Utils.IPasswordEncoder;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pds.api.Domain.Entities.User;
import com.pds.api.Domain.IRepositories.IUserRepository;

@Service
public class AuthenticateUser {
    private IUserRepository userRepository;
    private IPasswordEncoder passwordEncoder;

    public AuthenticateUser(IUserRepository userRepository, IPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean checkCredentials(String email, String password){
        if (email == null || password == null) return false;

        Optional<User> EUser = this.userRepository.findByEmail(email);
        
        User user = EUser.get();

        if(this.passwordEncoder.matches(user.getPassword(), password)) return true;

        return false;
    }

    public String getRole(String email) {
        User user = this.userRepository.findByEmail(email).get();
        return user.getRole();
    }    
}
