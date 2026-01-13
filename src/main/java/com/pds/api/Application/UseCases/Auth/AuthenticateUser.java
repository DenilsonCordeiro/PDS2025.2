package com.pds.api.Application.UseCases.Auth;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pds.api.Domain.Entities.User;
import com.pds.api.Domain.IRepositories.IUserRepository;

@Service
public class AuthenticateUser {
    private IUserRepository userRepository;

    public AuthenticateUser(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean checkCredentials(String email, String password){
        if (email == null || password == null) return false;

        Optional<User> EUser = this.userRepository.findByEmail(email);
        
        User user = EUser.get();

        if(user.getPassword().equals(password)) return true;

        return false;
    }
    
}
