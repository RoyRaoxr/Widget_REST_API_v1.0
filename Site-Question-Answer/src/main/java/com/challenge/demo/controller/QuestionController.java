package com.challenge.demo.controller;

import com.challenge.demo.bean.Choice;
import com.challenge.demo.bean.Option;
import com.challenge.demo.dto.QuestionAnswerDTO;
import com.challenge.demo.dto.QuestionDTO;
import com.challenge.demo.repo.*;
import com.challenge.demo.bean.Question;
import com.challenge.demo.bean.QuestionAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    ChoiceRepository choiceRepository;

    @Autowired
    OptionRepository optionRepository;

    @Autowired
    QuestionAnswerRepository qaRepository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO incomingQuestion) {
        return siteRepository
                .findById(incomingQuestion.getSiteId())
                .map(site -> {
                    final Question newQ = QuestionDTO.createQuestion(incomingQuestion, site);
                    return new ResponseEntity<>(QuestionDTO.build(questionRepository.save(newQ)), HttpStatus.CREATED);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<List<QuestionDTO>> getSites() {
        return Optional
                .ofNullable(questionRepository.findAll())
                .map(questions -> ResponseEntity.ok(QuestionDTO.build(questions)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<QuestionDTO> updateQuestion(@RequestBody Question incomingQuestion, @PathVariable(value = "id") Long questionId) {

        return questionRepository
                .findById(questionId)
                .map(question -> {
                    question.setQuestion(incomingQuestion.getQuestion());
                    question.setSite(incomingQuestion.getSite());
                    return new ResponseEntity<>(QuestionDTO.build(questionRepository.save(question)), HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<QuestionDTO> deleteQuestion(@PathVariable(value = "id") Long questionId) {
        return questionRepository
                .findById(questionId)
                .map(question -> {
                    questionRepository.delete(question);
                    return ResponseEntity.ok(QuestionDTO.build(question));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable(value = "id") Long questionId) {
        return questionRepository
                .findById(questionId)
                .map(question -> ResponseEntity.ok(QuestionDTO.build(question)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/options/{option_id}/choices/{choice_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<QuestionAnswerDTO> createQuestionAnswers(@PathVariable(value = "id") Long questionId,
                                                                   @PathVariable(value = "option_id") Long optionId,
                                                                   @PathVariable(value = "choice_id") Long choiceId,
                                                                   @RequestBody QuestionAnswerDTO newQADto) {
        Optional<Question> question = questionRepository.findById(questionId);
        Optional<Option> option = optionRepository.findById(optionId);
        Optional<Choice> choice = choiceRepository.findById(choiceId);
        if (question.isPresent() && option.isPresent() && choice.isPresent()) {
            final QuestionAnswer newQa = QuestionAnswerDTO.transform(newQADto, question.get(), option.get(), choice.get());
            return new ResponseEntity<>(QuestionAnswerDTO.build(qaRepository.save(newQa)), HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/answers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<QuestionAnswerDTO>> getQuestionAnswers(@PathVariable(value = "id") Long questionId) {
        return questionRepository
                .findById(questionId)
                .map(question -> ResponseEntity.ok(QuestionAnswerDTO.build(question.getAnswers())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
     *  CRUD Option for this question
     */
    @GetMapping("/{id}/options")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Option>> getQuestionOptions(@PathVariable(value = "id") Long questionId) {
        return Optional
                .ofNullable(optionRepository.findAllByQuestion_QuestionId(questionId))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/options")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Option> createQuestionOptions(@PathVariable(value = "id") Long questionId,
                                                        @RequestBody Option option) {
        option.setQuestion(questionRepository.findById(questionId).orElse(null));
        optionRepository.save(option);
        return ResponseEntity.ok(option);
    }

//    @PostMapping("/{id}/options")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<Option>

    @GetMapping("/{id}/choices")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Choice>> getQuestionChoices(@PathVariable(value = "id") Long questionId) {
        return Optional
                .ofNullable(choiceRepository.findAllByQuestion_QuestionId(questionId))
                .map(choices -> ResponseEntity.ok(choices))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/choices")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Choice> createQuestionOptions(@PathVariable(value = "id") Long questionId,
                                                        @RequestBody Choice choice) {
        choice.setQuestion(questionRepository.findById(questionId).orElse(null));
        choiceRepository.save(choice);
        return ResponseEntity.ok(choice);
    }


}