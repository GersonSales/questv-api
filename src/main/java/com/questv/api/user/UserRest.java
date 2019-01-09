package com.questv.api.user;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
public class UserRest {

    private final UserService userService;

    public UserRest(final UserService userService) {
        this.userService = userService;
        assert userService != null;
    }

    @PostMapping({"/user"})
    @ResponseStatus(value = HttpStatus.CREATED)
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
