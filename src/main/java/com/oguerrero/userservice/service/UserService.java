package com.oguerrero.userservice.service;

import com.oguerrero.userservice.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll();
    User getById(Long id);
    User save(User user);
    void delete(Long id);
    User update(Long id, User user);
}
