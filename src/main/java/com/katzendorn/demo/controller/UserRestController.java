package com.katzendorn.demo.controller;


import com.katzendorn.demo.entity.User;
import com.katzendorn.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/v1/users")
    public List<User> getAllUsers() {
        return userService.allUsers().stream().map(User::UserRest)
                .collect(Collectors.toList());
    }
}
