package com.oguerrero.userservice.controller;

import com.oguerrero.userservice.Utils.Utils;
import com.oguerrero.userservice.entity.User;
import com.oguerrero.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable Long id) {
        User user = userService.getById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("")
    public ResponseEntity<User> saveUser(@RequestBody User user) {

        String firstName = Utils.format(user.getFirstName());
        String lastName = Utils.format(user.getLastName());
        String encryptedPassword = passwordEncoder.encode(user.getPassword());

        if (Utils.invalidEmail(user.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        if (Utils.invalidPhoneNumber(user.getPhoneNumber())) {
            return ResponseEntity.badRequest().build();
        }

        User filter = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(user.getEmail())
                .password(encryptedPassword)
                .phoneNumber(user.getPhoneNumber())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(null)
                .build();

        User savedUser = userService.save(filter);

        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        userService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {

        User userDB = userService.getById(id);
        String firstName;
        String lastName;
        String encryptedPassword;
        String email;
        String phoneNumber;

        if (userDB == null) {
            return ResponseEntity.notFound().build();
        }

        if (user.getFirstName() == null) {
            firstName = userDB.getFirstName();
        } else
            firstName = Utils.format(user.getFirstName());


        if (user.getLastName() == null) {
            lastName = userDB.getLastName();
        } else
            lastName = Utils.format(user.getLastName());


        if (user.getPassword() == null) {
            encryptedPassword = userDB.getPassword();
        } else
            encryptedPassword = passwordEncoder.encode(user.getPassword());


        if (user.getEmail() == null) {
            email = userDB.getEmail();
        } else {
            if (Utils.invalidEmail(user.getEmail())) {
                return ResponseEntity.badRequest().build();
            }
            email = user.getEmail();
        }

        if (user.getPhoneNumber() == null) {
            phoneNumber = userDB.getPhoneNumber();
        } else {
            if (Utils.invalidPhoneNumber(user.getPhoneNumber())) {
                return ResponseEntity.badRequest().build();
            }
            phoneNumber = user.getPhoneNumber();
        }

        User filter = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(encryptedPassword)
                .phoneNumber(phoneNumber)
                .createdAt(userDB.getCreatedAt())
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .build();

        User updatedUser = userService.update(id, filter);

        return ResponseEntity.ok(updatedUser);
    }
}
