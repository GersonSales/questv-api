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
  public ResponseEntity<QuestionDTO> post(@PathVariable("questionableId") final String questionableId,
                                          @Valid @RequestBody final QuestionDTO questionDTO) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(this.questionService.createAndAttach(questionDTO));
    } catch (IllegalAssociationException exception) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping()
  public ResponseEntity<List<QuestionDTO>> getAllByParent(@PathVariable("questionableId") final String questionableId,
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
  public ResponseEntity<QuestionDTO> get(@PathVariable("questionableId") final String questionableId,
                                         @PathVariable final String questionId) {
    try {
      return ResponseEntity.ok(this.questionService.findById(questionId));
    } catch (IdNotFoundException exception) {
      return ResponseEntity.badRequest().build();
    }
  }

  
  @DeleteMapping("/{questionId}")
  public void delete(@PathVariable("questionableId") final String questionableId,
                     @PathVariable final String questionId) {
    this.questionService.delete(questionId);
  }

  
  @PutMapping("/{questionId}")
  public void put(@PathVariable("questionableId") final String questionableId,
                  @PathVariable("questionId") final String questionId,
                  @Valid @RequestBody final QuestionDTO questionDTO) {
    questionDTO.setId(questionId);
    this.questionService.update(questionDTO);
  }
}
