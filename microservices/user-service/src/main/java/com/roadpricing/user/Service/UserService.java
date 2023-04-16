package com.roadpricing.user.Service;

import com.roadpricing.user.Model.User;
import com.roadpricing.user.Repo.UserRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public List<User> findAll() {
        List<User> users = userRepo.findAll();
        return users;
    }

    public User findById(Long id) {
        User user = userRepo.findById(id).orElse(null);
        return user;
    }

    public User getUserByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user;
    }

    public List<User> getUserByNameAndLastName(String firstName, String lastName) {
        return userRepo.findByFirstNameAndLastName(firstName, lastName);
    }
}
