package org.abdurrahman.sma.services.impl;

import org.abdurrahman.sma.entities.User;
import org.abdurrahman.sma.repository.UserRepository;
import org.abdurrahman.sma.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPassword(user.getPassword());

        return userRepository.save(newUser);
    }

    @Override
    public User findUserById(Integer userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            return user.get();
        }

        throw new Exception("User not exist");
    }

    @Override
    public User findUserByEmail(String  email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User followUser(Integer userId1, Integer userId2) throws Exception {

        User user1 = findUserById(userId1);
        User user2 = findUserById(userId2);
        user2.getFollowers().add(user1.getId());
        user1.getFollowings().add(user2.getId());
        userRepository.save(user1);
        userRepository.save(user2);

        return user1;
    }

    @Override
    public User updateUser(User user,Integer userId) throws Exception {
        Optional<User> user1 = userRepository.findById(userId);
        User oldUser = user1.get();
        if (userRepository.existsById(userId)) {
            if (user.getFirstName() != null) {
                oldUser.setFirstName(user.getFirstName());
            }
            if (user.getLastName() != null) {
                oldUser.setLastName(user.getLastName());
            }
            if (user.getEmail() != null) {
                oldUser.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                oldUser.setPassword(user.getPassword());
            }

            userRepository.save(oldUser);
        } else {
            throw new Exception("User not exist");
        }

        return oldUser;
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }


}
