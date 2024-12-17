package com.Develoading.backend.service;

import com.Develoading.backend.model.User;
import com.Develoading.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        // E-posta eşsiz mi kontrol et
        if (userRepository.findByMail(user.getMail()) != null) {
            throw new IllegalArgumentException("E-posta zaten kayıtlı.");
        }
        return userRepository.save(user);
    }


    // E-posta ile kullanıcı sorgulama
    public User getUserByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    // Tüm kullanıcıları alma
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



    public User updateUser(User user) {
        User existingUser = userRepository.findByMail(user.getMail());
        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setSurname(user.getSurname());
            return userRepository.save(existingUser);
        }
        throw new IllegalArgumentException("Kullanıcı bulunamadı.");
    }

    public void deleteUserByMail(String mail) {
        User user = userRepository.findByMail(mail);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    public User loginUser(String mail, String password) {
        User user = userRepository.findByMailAndPassword(mail, password);
        if (user == null) {
            throw new IllegalArgumentException("Geçersiz e-posta veya şifre.");
        }
        return user;
    }
}