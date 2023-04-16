package com.roadpricing.user.Controller;

import com.roadpricing.user.Model.User;
import com.roadpricing.user.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getAllUser() {
        try{
            List<User> users = userService.findAll();
            return ResponseEntity.ok().body(users);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        try{
            User user = userService.findById(id);
            return ResponseEntity.ok().body(user);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        User user = userService.getUserByEmail(email);
        if(user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/{firstName}/{lastName}")
    public List<User> getUserByNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
        // Check if first name and last name are not empty or null
        if (firstName != null && !firstName.isEmpty() && lastName != null && !lastName.isEmpty()) {
            // Call service to get user by first name and last name
            return userService.getUserByNameAndLastName(firstName, lastName);
        } else {
            // Handle invalid input
            throw new IllegalArgumentException("First name and last name must not be empty or null");
        }
    }

}
