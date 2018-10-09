package io.borland.myblog.service;

import io.borland.myblog.entity.User;

public interface UserService {
    User getUser(long id);

    User getUser(String email);

    void addUser(User user);

    void updateUser(User user);

    void removeUser(User user);
}
