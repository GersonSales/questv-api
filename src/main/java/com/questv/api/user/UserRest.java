package com.questv.api.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
public class UserRest {

    @Autowired
    UserService userService;

    @PostMapping({"/user"})
    public void postUser(@Valid @RequestBody UserDTO userDTO) {
        this.userService.create(userDTO.convert());
    }

    @GetMapping({"/user"})
    public List<UserDTO> getUsers() {
        return this.userService
                .findAll()
                .stream()
                .map(UserModel::convert)
                .collect(Collectors.toList());
    }

}
