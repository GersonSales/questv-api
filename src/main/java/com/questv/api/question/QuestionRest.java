package com.questv.api.question;

import com.questv.api.contracts.Restable;
import com.questv.api.exception.IdNotFoundException;
import com.questv.api.exception.IllegalAssociationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class QuestionRest implements Restable<QuestionDTO> {

  private final QuestionService questionService;

  public QuestionRest(final QuestionService questionService) {
    this.questionService = questionService;
    assert this.questionService != null;
  }

  @Override
  @PostMapping("/question")
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<QuestionDTO> post(@Valid @RequestBody final QuestionDTO questionDTO) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(this.questionService.createAndAttach(questionDTO));
    } catch (IllegalAssociationException exception) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping("/questionable/{parentId}/question")
  public ResponseEntity<List<QuestionDTO>> getAllByParent(@PathVariable final Long parentId,
                                                          @RequestParam(required = false) final Boolean recursive) {
    try {
      final List<QuestionDTO> result = new ArrayList<>();

      if (recursive != null && recursive) {
        result.addAll(this.questionService.findAllByParentRecursive(parentId));
      } else {
        result.addAll(this.questionService.findAllByParent(parentId));
      }
      return ResponseEntity.ok(result);

    } catch (final IdNotFoundException exception) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Override
  @GetMapping("/question")
  public ResponseEntity<List<QuestionDTO>> get() {
    return ResponseEntity.ok(this.questionService.findAll());
  }

  @Override
  @GetMapping("/question/{questionId}")
  public ResponseEntity<QuestionDTO> get(@PathVariable final Long questionId) {
    try {
      return ResponseEntity.ok(this.questionService.findById(questionId));
    } catch (IdNotFoundException exception) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Override
  @DeleteMapping("/question/{questionId}")
  public void delete(@PathVariable final Long questionId) {
    this.questionService.delete(questionId);
  }

  @Override
  @PutMapping("/question/{questionId}")
  public void put(@PathVariable("questionId") final Long questionId, @Valid @RequestBody final QuestionDTO questionDTO) {
    questionDTO.setId(questionId);
    this.questionService.update(questionDTO);
  }
}
