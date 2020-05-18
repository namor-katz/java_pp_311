package com.katzendorn.demo.controller;

import com.katzendorn.demo.entity.User;
import com.katzendorn.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/fuck")
public class ListAllUserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String returnListUsers(ModelMap model) {
        List<User> list = userService.allUsers();
        model.addAttribute("list", list);
        return "listAllUsers";
    }
}
