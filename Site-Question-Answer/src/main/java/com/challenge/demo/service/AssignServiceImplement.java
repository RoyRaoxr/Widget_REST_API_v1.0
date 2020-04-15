package com.challenge.demo.service;

import com.challenge.demo.bean.*;
import com.challenge.demo.dto.QuestionDTO;
import com.challenge.demo.dto.ResultDTO;
import com.challenge.demo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AssignServiceImplement implements AssignService {

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionAnswerRepository qaRepository;

    @Autowired
    ResultRepository resultRepository;

    @Override
    public ResponseEntity<QuestionDTO> AssignOneQuestion(UUID siteUUID, UUID userUUID) {

        Long siteId = siteRepository.findBySiteUUID(siteUUID).getSiteId();
        Long userId = userRepository.findByUserUUID(userUUID).getUserId();
        Question question = questionRepository.findAssignOneQuestion(siteId, userId);

        if (question == null) {
            resultRepository.resetQuestionList(userId);
            question = questionRepository
                    .findAssignOneQuestion(siteId, userId);
        }

        if (question == null) return ResponseEntity.notFound().build();

        QuestionDTO questionDTO = QuestionDTO.build(question);
        return new ResponseEntity<>(questionDTO, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<ResultDTO> saveResult(UUID userUUID, ResultDTO resultDTO) {

        Optional<Question> question = questionRepository.findById(resultDTO.getQuestionId());
        Optional<QuestionAnswer> questionAnswer = qaRepository.findById(resultDTO.getQuestionAnswerId());
        User user = userRepository.findByUserUUID(userUUID);
        Result result = new Result();

        if (user != null && question.isPresent() && questionAnswer.isPresent()) {
            result = ResultDTO.transform(resultDTO, question.get(), questionAnswer.get(), user);
            return new ResponseEntity<>(ResultDTO.build(resultRepository.save(result)), HttpStatus.CREATED);
        }
        return ResponseEntity.notFound().build();
    }

}
