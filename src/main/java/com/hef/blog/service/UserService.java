package com.hef.blog.service;

import com.hef.blog.entity.User;

public interface UserService {
    User checkUser(String username, String password);

    User getUserByUsername(String username);
}
