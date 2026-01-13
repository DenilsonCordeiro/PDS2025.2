package com.pds.api.Domain.IRepositories;

import java.util.List;
import java.util.Optional;

import com.pds.api.Domain.Entities.User;

public interface IUserRepository {
    void save(User user);
    void deleteByEmail(String email);
    Optional<User> findByEmail(String email);
    List<User> findAll();
}
