package io.borland.myblog.controller;

import io.borland.myblog.controller.exception.UnauthorizedAccessException;
import io.borland.myblog.controller.exception.UserAlreadyExist;
import io.borland.myblog.controller.exception.UserConfirmPasswordMismatched;
import io.borland.myblog.entity.User;
import io.borland.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping(value = "/api/users/")
@RestController
public class UserRestController {
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAuthenticationUser(Authentication authentication) {
        if (authentication == null) {
            throw new UnauthorizedAccessException("You have to authorise this operation first. Please login into the application");
        }
        User user = userService.getUserByUsername(authentication.getName());
        return new ResponseEntity<>(user.getUserShort(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody User user) {
        User savedUser = userService.getUser(user.getEmail());

        if (savedUser != null) {
            throw new UserAlreadyExist(user.getEmail());
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new UserConfirmPasswordMismatched();
        }

        userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{userId}")
    public void updateUser(@Valid @RequestBody User user, @PathVariable(name = "userId") long id) {
        User savedUser = userService.getUser(id);
        if (savedUser != null) {
            userService.updateUser(user);
        }
    }
}
