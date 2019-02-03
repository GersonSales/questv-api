package com.questv.api.question;

import com.questv.api.contracts.Restable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
  @ResponseBody
  public QuestionDTO post(@Valid @RequestBody final QuestionDTO questionDTO) {
    return this.questionService.createAndAttach(questionDTO);
  }

  @Override
  @GetMapping("/question")
  @ResponseBody
  public List<QuestionDTO> getAll() {
    return this.questionService.findAll();
  }

  @Override
  @GetMapping("/question/{questionId}")
  public QuestionDTO getById(@PathVariable final Long questionId) {
    return this.questionService.findById(questionId);
  }

  @Override
  @DeleteMapping("/question/{questionId}")
  public void deleteById(@PathVariable final Long questionId) {
    this.questionService.deleteById(questionId);
  }

  @Override
  @PutMapping("/question")
  public void put(@Valid @RequestBody final QuestionDTO questionDTO) {
    this.questionService.updateById(questionDTO.getId(), questionDTO);
  }
}
