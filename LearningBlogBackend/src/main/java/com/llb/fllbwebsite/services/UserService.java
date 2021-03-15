package com.llb.fllbwebsite.services;

import com.llb.fllbwebsite.domain.User;
import com.llb.fllbwebsite.exceptions.UserIdException;
import com.llb.fllbwebsite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User saveOrUpdateUser(User user) {
       try {
           user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

           // Username has to be unique (exception)

           //password and confirmPassword must match

           //confirmPassword shouldn't be persisted or shown
           user.setConfirmPassword("");

           //save
           return userRepository.save(user);
       }catch (Exception e){
           throw new UserIdException("User already exist");
       }

    }

    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long userId) {
        User user = userRepository.getById(userId);
        if (user == null) {
            throw new UserIdException("User with Id '" + userId + "' dose not exist");
        }
        return user;
    }

    public User findUserByEmail(String userEmail){
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new UserIdException("User with email '" + userEmail + "' dose not exist");
        }
        return user;
    }

    public void deleteUserById(Long userId) {
        User user = findUserById(userId);
        userRepository.delete(user);
    }
}
