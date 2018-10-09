package io.borland.myblog.service.impl;

import io.borland.myblog.entity.User;
import io.borland.myblog.repository.UserRepository;
import io.borland.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User getUser(long id) {
        Optional<User> optUser = userRepository.findById(id);
        return optUser.orElse(null);
    }

    @Override
    public User getUser(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void addUser(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public void removeUser(User user) {
        userRepository.delete(user);
    }
}
