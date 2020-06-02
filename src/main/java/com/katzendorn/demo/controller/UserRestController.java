package com.katzendorn.demo.controller;


import com.katzendorn.demo.entity.Role;
import com.katzendorn.demo.entity.User;
import com.katzendorn.demo.service.UserService;
//import groovy.util.logging.Slf4j;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "users")
//    @ApiResponses()
    public List<User> getAllUsers() {
        return userService.allUsers().stream().map(User::UserRest)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "user/info/{id}")
    public User getUserInfo(@PathVariable String id) {
        String id0 = id.replace("id", "");
        Long id1;
        try {
            id1 = Long.parseLong(id0);
        }
        catch (Error e) {
            id1 = 1L;
        }
        return userService.findUserById(id1);
    }

    @ApiOperation(value = "Delete user by id", code = 204)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Delete completed")
    })
    @DeleteMapping(value = "user/delete/{id}")  //можно юзать делете или гет, и соотв. контроллер. лучше делете, видимо.
    //это вообще нужно? все эти коде, ибо отдаёт оно почему-то 200, а не 204
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
//        String id0 = id.replace("id", "");
//        Long id1 = Long.parseLong(id0);
        User user = userService.findUserById(id);
        if(user.getUsername() == null) {
            System.out.println("don't find user");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "create new User", code = 201, response = User.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Succesfully created user")})
    @PostMapping(value = "user/new", consumes = {"application/json"})
    public ResponseEntity<User> createNewUser(@RequestBody User user) {
        System.out.println("но я и есть юзер " + user.getUsername());
        user.setId(null);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("user/update/{id}")
    @ApiOperation(value = "Update exixting user", code = 202, response = User.class)
    @ApiResponses(value = {@ApiResponse(code = 202, message = "Update accepted")})
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        Long id0 = Long.parseLong(id);
        User userOfDb = userService.findUserById(id0);
        Set<Role> roles  = userOfDb.getRoles();
        if(userOfDb.getUsername() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //!! требуется логика проверок, либо забить и юзать просто поля из формы.
        user.setId(id0);
        user.setRoles(roles);

        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
