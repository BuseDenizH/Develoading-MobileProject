package com.Develoading.backend.controller;

import com.Develoading.backend.model.User;
import com.Develoading.backend.model.PasswordUpdateRequest;
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

    @PutMapping("/updatemail/{mail}")
    @CrossOrigin(origins = "http://localhost:8100")
    public ResponseEntity<User> updateMail(@PathVariable String mail, @RequestParam String newMail) {
        User user = userService.getUserByMail(mail);
        if (user == null) {
            return ResponseEntity.notFound().build(); // Kullanıcı bulunamazsa 404 döndür
        }
        if (userService.getUserByMail(newMail) != null) {
            return ResponseEntity.badRequest().body(null); // Yeni mail zaten kullanılıyorsa hata
        }
        user.setMail(newMail); // Yeni maili setle
        User updatedUser = userService.saveUser(user); // Güncellenmiş kullanıcıyı kaydet
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/updatepassword/{mail}")
    @CrossOrigin(origins = "http://localhost:8100")
    public ResponseEntity<User> updatePassword(
            @PathVariable String mail,
            @RequestBody PasswordUpdateRequest request) {
        try {
            if (request.getOldPassword() == null || request.getNewPassword() == null) {
                return ResponseEntity.badRequest().body(null);
            }

            User updatedUser = userService.updatePassword(mail, request.getOldPassword(), request.getNewPassword());
            return ResponseEntity.ok(updatedUser);
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