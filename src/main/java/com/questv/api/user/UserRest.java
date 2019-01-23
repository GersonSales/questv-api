package com.questv.api.user;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping()
public class UserRest {

    private final ObjectService<UserDTO> userService;

    public UserRest(final ObjectService<UserDTO> userService) {
        this.userService = userService;
        assert userService != null;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public UserDTO postUser(@Valid @RequestBody final UserDTO userDTO) {
        return this.userService.create(userDTO);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/user")
    public List<UserDTO> getUsers() {
        return this.userService.findAll();
    }

    @GetMapping("/user/{userId}")
    public UserDTO getUserById(@PathVariable final Long userId) {
        return this.userService.findById(userId);
    }

    @PutMapping("/user/{userId}")
    public void putUser(@PathVariable final Long userId, @RequestBody final UserDTO userDTO) {
        this.userService.updateById(userId, userDTO);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        this.userService.deleteById(userId);
    }


}
