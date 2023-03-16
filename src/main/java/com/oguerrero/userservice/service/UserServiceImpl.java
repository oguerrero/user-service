package com.oguerrero.userservice.service;

import com.oguerrero.userservice.entity.User;
import com.oguerrero.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(Long id, User user) {
        return userRepository.findById(id)
                .map(u -> {
                    u.setFirstName(user.getFirstName());
                    u.setLastName(user.getLastName());
                    u.setEmail(user.getEmail());
                    u.setPassword(user.getPassword());
                    u.setPhoneNumber(user.getPhoneNumber());
                    u.setUpdatedAt(user.getUpdatedAt());
                    return userRepository.save(u);
                })
                .orElseGet(() -> {
                    user.setId(id);
                    return userRepository.save(user);
                });
    }
}
