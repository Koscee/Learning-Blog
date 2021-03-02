package com.llb.fllbwebsite.services;

import com.llb.fllbwebsite.domain.User;
import com.llb.fllbwebsite.exceptions.UserIdException;
import com.llb.fllbwebsite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveOrUpdateUser(User user) {
       try {
           return userRepository.save(user);
       }catch (Exception e){
           throw new UserIdException("User already exist");
       }

    }

    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
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
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UserIdException("Cannot delete: User with id '" + userId+ "' does not exist");
        }
        userRepository.deleteById(userId);
    }
}
