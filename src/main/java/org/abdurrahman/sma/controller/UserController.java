package org.abdurrahman.sma.controller;

import org.abdurrahman.sma.entities.User;
import org.abdurrahman.sma.repository.UserRepository;
import org.abdurrahman.sma.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User findUser(@PathVariable("id") Integer id) throws Exception {
        return userService.findUserById(id);
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) throws Exception {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new Exception("User not exists");
        }
        return "User deleted";
    }

    @PutMapping("/{userId}")
    public User updateUser(@RequestBody User user, @PathVariable Integer userId) throws Exception {
        return userService.updateUser(user, userId);
    }

    @GetMapping("/email/{email}")
    public User findByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    @GetMapping("/search")
    public List<User> searchUser(@RequestParam("query") String query) {
        return userService.searchUser(query);
    }

    @PutMapping("/follow/{userId1}/{userId2}")
    public User followUserHandler(@PathVariable Integer userId1, @PathVariable Integer userId2) throws Exception {
        return userService.followUser(userId1, userId2);
    }
}
