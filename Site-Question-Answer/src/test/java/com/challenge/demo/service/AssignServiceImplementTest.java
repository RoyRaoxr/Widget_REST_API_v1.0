package com.challenge.demo.service;

import com.challenge.demo.bean.*;

import static org.junit.jupiter.api.Assertions.*;

import com.challenge.demo.dto.QuestionDTO;
import com.challenge.demo.dto.ResultDTO;
import com.challenge.demo.repo.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AssignServiceImplementTest {

    @InjectMocks
    AssignServiceImplement assignService;

    @Mock
    UserRepository userRepository;

    @Mock
    SiteRepository siteRepository;

    @Mock
    QuestionRepository questionRepository;

    @Mock
    QuestionAnswerRepository qaRepository;

    @Mock
    ResultRepository resultRepository;

    User user = new User();
    Site site = new Site();
    Question question = new Question();
    Option option = new Option();
    Choice choice = new Choice();
    QuestionAnswer questionAnswer = new QuestionAnswer();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        user.setUserUUID(UUID.fromString("5849910b-b95e-44e2-920c-7b24a10539b1"));
        user.setUserId(1L);
        site.setUrl("www.bob.com");
        site.setSiteId(1L);
        site.setSiteUUID(UUID.fromString("07a85957-1a6c-457f-bb4c-3626dc04b7bc"));

        question.setSite(site);
        question.setType("Trivia");
        question.setQuestion("How many toes does a pig have?");
        question.setQuestionId(1L);

        option.setQuestion(question);
        option.setOptionText("Options");
        choice.setChoiceTitle("Choices");
        choice.setChoiceText("4");

        questionAnswer.setOption(option);
        questionAnswer.setChoice(choice);
        questionAnswer.setIsCorrectAnswer(true);
        questionAnswer.setId(1L);
        questionAnswer.setQuestion(question);
    }

    @Test
    public void AssignOneQuestionTest() {
        when(questionRepository.findAssignOneQuestion(anyLong(), anyLong())).thenReturn(question);
        when(siteRepository.findBySiteUUID(any(UUID.class))).thenReturn(site);
        when(userRepository.findByUserUUID(any(UUID.class))).thenReturn(user);
        ResponseEntity<QuestionDTO> questionDTO = assignService.AssignOneQuestion(site.getSiteUUID(), user.getUserUUID());

        verify(questionRepository).findAssignOneQuestion(1L, 1L);
        assertNotNull(questionDTO);
        assertEquals("How many toes does a pig have?", questionDTO.getBody().getQuestion());
        assertEquals("Trivia", questionDTO.getBody().getType());
        assertEquals(Long.valueOf(1L), questionDTO.getBody().getQuestionId());
    }

    @Test
    public void SaveResultTest() {
        ResultDTO resultDto = new ResultDTO();
        resultDto.setVisited(false);
        resultDto.setQuestionId(1L);
        resultDto.setQuestionAnswerId(1L);
        Result result = ResultDTO.transform(resultDto, question, questionAnswer, user);

        when(questionRepository.findById(anyLong())).thenReturn(Optional.of(question));
        when(qaRepository.findById(anyLong())).thenReturn(Optional.of(questionAnswer));
        when(userRepository.findByUserUUID(any(UUID.class))).thenReturn(user);
        when(resultRepository.save(any(Result.class))).thenReturn(result);

        ResultDTO test = assignService.saveResult(user.getUserUUID(), resultDto).getBody();

        verify(resultRepository, times(1)).save(any(Result.class));
        assertNotNull(test);
        assertEquals(resultDto.getQuestionAnswerId(), test.getQuestionAnswerId());
        assertEquals(resultDto.getQuestionId(), test.getQuestionId());
    }
}
