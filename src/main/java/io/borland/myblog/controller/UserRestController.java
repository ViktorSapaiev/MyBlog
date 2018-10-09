package io.borland.myblog.controller;

import io.borland.myblog.entity.User;
import io.borland.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/users/")
@RestController
public class UserRestController {
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/addUser")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @PutMapping(value = "/updateUser/{userId}")
    public void updateUser(@RequestBody User user, @PathVariable(name = "userId") long id) {
        User savedUser = userService.getUser(id);
        if (savedUser != null) {
            userService.updateUser(user);
        }
    }
}
