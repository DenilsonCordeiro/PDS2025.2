package com.pds.api.Infrastructure.Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.pds.api.Domain.Entities.User;
import com.pds.api.Domain.IRepositories.IUserRepository;

@Repository
public class UserRepository implements IUserRepository{
    private final Map<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getEmail(), user);
    }

    @Override
    public void deleteByEmail(String email) {
        users.remove(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(users.get(email));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }    
}
