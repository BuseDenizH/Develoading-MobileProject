package com.Develoading.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Develoading.backend.model.PasswordUpdateRequest;
import com.Develoading.backend.model.User;
import com.Develoading.backend.service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender; // E-posta gönderimi için

    @PostMapping("/reset-password/{email}")
    @CrossOrigin(origins = "")
    public ResponseEntity<?> resetPassword(@PathVariable String email) {

        User user = userService.getUserByMail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // 6 haneli rastgele bir şifre yenileme kodu oluştur
        String resetCode = generateResetCode();

        // E-posta gönderme
        sendResetEmail(email, resetCode);

        // Burada reset kodunu veritabanında saklamak da isteyebilirsiniz

        return ResponseEntity.ok().body(resetCode);
    }

    // E-posta gönderme işlemi
    private void sendResetEmail(String email, String resetCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Şifre Yenileme Bağlantısı");
        message.setText("Şifre yenileme kodunuz: " + resetCode);
        message.setFrom("develoadingg@gmail.com");
        mailSender.send(message);
    }

    // 6 haneli şifre yenileme kodu üretme
    private String generateResetCode() {
        int code = (int)(Math.random() * 999999);
        return String.format("%06d", code); // 6 haneli formatta
    }

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

    @PutMapping("/modifypassword/{mail}")
    @CrossOrigin(origins = "http://localhost:8100")
    public ResponseEntity<User> modifypassword(
            @PathVariable String mail,
            @RequestBody PasswordUpdateRequest request) {
        try {
            // Yeni şifrenin boş olup olmadığını kontrol et
            if (request.getNewPassword() == null || request.getNewPassword().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }

            // Şifre güncelleme işlemini yap
            User updatedUser = userService.updatePassword(mail, request.getNewPassword());
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