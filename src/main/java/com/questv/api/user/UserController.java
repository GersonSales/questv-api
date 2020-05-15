package com.questv.api.user;


import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.questv.api.uitl.Strings.USER_API_DESCRIPTION;
import static com.questv.api.uitl.Strings.USER_API_NAME;

@Api(
    value = USER_API_NAME,
    description = USER_API_DESCRIPTION,
    tags = {USER_API_NAME})
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(final UserService userService) {
    this.userService = userService;
    assert userService != null;
  }

  @PostMapping("/sign-up")
  @ResponseBody
  public ResponseEntity<UserDTO> post(@Valid @RequestBody final UserDTO userDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.create(userDTO));
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> get() {
    return ResponseEntity.ok().body(this.userService.findAll());
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserDTO> get(@PathVariable final String userId) {
    return ResponseEntity.ok().body(this.userService.findById(userId));
  }

  @PutMapping("/{userId}")
  public void put(@PathVariable final String userId, @RequestBody final UserDTO userDTO) {
    this.userService.update(userDTO);
  }

  @DeleteMapping("/{userId}")
  public void delete(@PathVariable final String userId) {
    this.userService.delete(userId);
  }
}
