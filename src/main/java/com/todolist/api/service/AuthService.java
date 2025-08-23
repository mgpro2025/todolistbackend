package com.todolist.api.service;

import com.todolist.api.entity.User;

public interface AuthService {
    User registerUser(String email, String password);
    String loginUser(String email, String password);
}
