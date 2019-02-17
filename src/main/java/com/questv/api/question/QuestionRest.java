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
@RequestMapping("/questionable/{questionableId}/questions")
public class QuestionRest {

  private final QuestionService questionService;

  public QuestionRest(final QuestionService questionService) {
    this.questionService = questionService;
    assert this.questionService != null;
  }

  
  @PostMapping()
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<QuestionDTO> post(@PathVariable("questionableId") final Long questionableId,
                                          @Valid @RequestBody final QuestionDTO questionDTO) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(this.questionService.createAndAttach(questionableId, questionDTO));
    } catch (IllegalAssociationException exception) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping()
  public ResponseEntity<List<QuestionDTO>> getAllByParent(@PathVariable("questionableId") final Long questionableId,
                                                          @RequestParam(required = false) final Boolean recursive) {
    try {
      final List<QuestionDTO> result = new ArrayList<>();

      if (recursive != null && recursive) {
        result.addAll(this.questionService.findAllByParentRecursive(questionableId));
      } else {
        result.addAll(this.questionService.findAllByParent(questionableId));
      }
      return ResponseEntity.ok(result);

    } catch (final IdNotFoundException exception) {
      return ResponseEntity.badRequest().build();
    }
  }

  
  @GetMapping("/questions")
  public ResponseEntity<List<QuestionDTO>> get() {
    return ResponseEntity.ok(this.questionService.findAll());
  }

  
  @GetMapping("/{questionId}")
  public ResponseEntity<QuestionDTO> get(@PathVariable("questionableId") final Long questionableId,
                                         @PathVariable final Long questionId) {
    try {
      return ResponseEntity.ok(this.questionService.findById(questionId));
    } catch (IdNotFoundException exception) {
      return ResponseEntity.badRequest().build();
    }
  }

  
  @DeleteMapping("/{questionId}")
  public void delete(@PathVariable("questionableId") final Long questionableId,
                     @PathVariable final Long questionId) {
    this.questionService.delete(questionableId, questionId);
  }

  
  @PutMapping("/{questionId}")
  public void put(@PathVariable("questionableId") final Long questionableId,
                  @PathVariable("questionId") final Long questionId,
                  @Valid @RequestBody final QuestionDTO questionDTO) {
    questionDTO.setId(questionId);
    this.questionService.update(questionDTO);
  }
}
