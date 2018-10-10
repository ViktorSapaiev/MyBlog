package io.borland.myblog.service.impl;

import io.borland.myblog.entity.Role;
import io.borland.myblog.entity.User;
import io.borland.myblog.repository.RoleRepository;
import io.borland.myblog.repository.UserRepository;
import io.borland.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
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
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public void addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        Optional<Role> opt = roleRepository.findById(1);
        opt.ifPresent(roles::add);
        user.setRole(roles);
        userRepository.save(user);
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
