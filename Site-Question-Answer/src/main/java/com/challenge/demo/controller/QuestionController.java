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

@RestController
@RequestMapping("/v1/questions")
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
                .map(question -> ResponseEntity.ok(QuestionAnswerDTO.build(qaRepository.findAllByQuestion(question))))
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

    @PutMapping("/{id}/options/{option_id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Option> updateQuestionOptions(@PathVariable(value = "id") Long questionId,
                                                        @PathVariable(value = "option_id") Long optionId,
                                                        @RequestBody Option incomingOption) {
        return optionRepository
                .findById(optionId)
                .map(option -> {
                    option.setOptionText(incomingOption.getOptionText());
                    return new ResponseEntity<>(optionRepository.save(option), HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/options/{option_id}")
    public ResponseEntity<Option> deleteQuestionOptions(@PathVariable(value = "id") Long questionId,
                                                        @PathVariable(value = "option_id") Long optionId) {
        return optionRepository
                .findById(optionId)
                .map(option -> {
                    optionRepository.delete(option);
                    return ResponseEntity.ok(option);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/options/{option_id}")
    public ResponseEntity<Option> getOptionById(@PathVariable(value = "id") Long questionId,
                                                        @PathVariable(value = "option_id") Long optionId) {
        return optionRepository
                .findById(optionId)
                .map(option -> {
                    return ResponseEntity.ok(option);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    /*
     *  CRUD Choice for this question
     */
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

    @PutMapping("/{id}/choices/{choice_id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Choice> updateQuestionChoices(@PathVariable(value = "id") Long questionId,
                                                        @PathVariable(value = "choice_id") Long choiceId,
                                                        @RequestBody Choice incomingChoice) {
        return choiceRepository
                .findById(choiceId)
                .map(choice -> {
                    choice.setChoiceText(incomingChoice.getChoiceText());
                    return new ResponseEntity<>(choiceRepository.save(choice), HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/choices/{choice_id}")
    public ResponseEntity<Choice> deleteQuestionChoices(@PathVariable(value = "id") Long questionId,
                                                        @PathVariable(value = "choice_id") Long choiceId) {
        return choiceRepository
                .findById(choiceId)
                .map(choice -> {
                    choiceRepository.delete(choice);
                    return ResponseEntity.ok(choice);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/choices/{choice_id}")
    public ResponseEntity<Choice> getChoiceById(@PathVariable(value = "id") Long questionId,
                                                @PathVariable(value = "choice_id") Long choiceId) {
        return choiceRepository
                .findById(choiceId)
                .map(choice -> {
                    return ResponseEntity.ok(choice);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}