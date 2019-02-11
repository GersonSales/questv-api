package com.questv.api.user;


import com.questv.api.answered.question.AnsweredQuestionModel;
import com.questv.api.contracts.ObjectService;
import com.questv.api.contracts.Restable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRest implements Restable<UserDTO> {

  private final ObjectService<UserDTO> userService;

  public UserRest(final ObjectService<UserDTO> userService) {
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
  public ResponseEntity<UserDTO> get(@PathVariable final Long userId) {
    return ResponseEntity.ok().body(this.userService.findById(userId));
  }

  @PutMapping("/{userId}")
  public void put(@PathVariable final Long userId, @RequestBody final UserDTO userDTO) {
    this.userService.update(userDTO);
  }

  @DeleteMapping("/{userId}")
  public void delete(@PathVariable final Long userId) {
    this.userService.delete(userId);
  }

  @PostMapping("/{userId}/answeredQuestion")
  public void postAnsweredQuestion(@PathVariable final Long userId,
                                   @RequestBody final AnsweredQuestionModel answeredQuestionModel) {
    ((UserService)this.userService).attachAnsweredModel(userId, answeredQuestionModel);

  }
}
