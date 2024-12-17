package com.Develoading.backend.controller;

import com.Develoading.backend.model.User;
import com.Develoading.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @CrossOrigin(origins = "")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        if (userService.getUserByMail(user.getMail()) != null) {
            return ResponseEntity.badRequest().body(null); // E-posta zaten kayıtlıysa
        }
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "")
    public ResponseEntity<User> loginUser(@RequestBody User loginRequest) {
        try {
            User user = userService.loginUser(loginRequest.getMail(), loginRequest.getPassword());
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/update/{mail}")
    public ResponseEntity<User> updateUser(@PathVariable String mail, @RequestBody User updatedUser) {
        try {
            updatedUser.setMail(mail); // Path'ten gelen mail'i User objesine setle
            User savedUser = userService.updateUser(updatedUser);
            return ResponseEntity.ok(savedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    // Tüm kullanıcıları listeleme
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/mail/{mail}")
    public ResponseEntity<User> getUserByMail(@PathVariable String mail) {
        User user = userService.getUserByMail(mail);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/mail/{mail}")
    public ResponseEntity<Void> deleteUser(@PathVariable String mail) {
        User user = userService.getUserByMail(mail);
        if (user != null) {
            userService.deleteUserByMail(mail);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}