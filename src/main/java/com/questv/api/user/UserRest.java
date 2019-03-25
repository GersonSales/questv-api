package com.questv.api.user;


import com.questv.api.answered.question.AnsweredQuestionDTO;
import com.questv.api.contracts.Identifiable;
import com.questv.api.contracts.Rankable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRest {

  private final UserService userService;

  public UserRest(final UserService userService) {
    this.userService = userService;
    assert userService != null;
  }

  @PostMapping("/sign-up")
  @ResponseBody
  public ResponseEntity<UserDTO> post(@Valid @RequestBody final UserDTO userDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.create(userDTO));
  }

  @GetMapping
  public ResponseEntity<List<Identifiable>> get(@RequestParam(required = false) final Boolean ranked) {
    final List<Identifiable> result = new ArrayList<>();
    if (ranked != null && ranked) {
      result.addAll(this.userService.findAllRanked());
    } else {
      result.addAll(this.userService.findAll());
    }
    return ResponseEntity.ok().body(result);
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

  @PostMapping("/{userId}/answeredQuestion")
  public void postAnsweredQuestion(@PathVariable final String userId,
                                   @RequestBody final AnsweredQuestionDTO answeredQuestionModel) {
    this.userService.attachAnsweredQuestion(userId, answeredQuestionModel);

  }

  @DeleteMapping("/{userId}/answeredQuestion")
  public void deleteAnsweredQuestion(@PathVariable final String userId,
                                     @RequestBody final AnsweredQuestionDTO answeredQuestionModel) {
    this.userService.detachAnsweredQuestion(userId, answeredQuestionModel);

  }
}
