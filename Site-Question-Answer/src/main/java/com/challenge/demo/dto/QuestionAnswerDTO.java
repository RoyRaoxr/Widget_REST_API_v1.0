package com.challenge.demo.dto;

import com.challenge.demo.bean.Choice;
import com.challenge.demo.bean.Option;
import com.challenge.demo.bean.Question;
import com.challenge.demo.bean.QuestionAnswer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestionAnswerDTO {

    private Long id;

    private Long questionId;

    private Long optionId;

    private Long choiceId;

    private boolean isCorrectAnswer;

    private Date createdAt;

    private Date updatedAt;

    public static QuestionAnswer transform(final QuestionAnswerDTO newQADto, final Question question,
                                           final Option option, final Choice choice) {
        final QuestionAnswer newQa = new QuestionAnswer();
        newQa.setIsCorrectAnswer(newQADto.getIsCorrectAnswer());
        newQa.setQuestion(question);
        newQa.setChoice(choice);
        newQa.setOption(option);
        return newQa;
    }

    public static QuestionAnswerDTO build(final QuestionAnswer save) {
        final QuestionAnswerDTO newQaDto = new QuestionAnswerDTO();
        newQaDto.setId(save.getId());
        newQaDto.setQuestionId(save.getQuestion().getQuestionId());
        newQaDto.setChoiceId(save.getChoice().getChoiceId());
        newQaDto.setOptionId(save.getOption().getOptionId());
        newQaDto.setIsCorrectAnswer(save.isCorrectAnswer());
        newQaDto.setCreatedAt(save.getCreatedAt());
        newQaDto.setUpdatedAt(save.getUpdatedAt());
        return newQaDto;
    }

    public static List<QuestionAnswerDTO> build(final List<QuestionAnswer> answers) {
        final List<QuestionAnswerDTO> ret = new ArrayList<>();
        for (QuestionAnswer qa : answers) {
            ret.add(build(qa));
        }
        return ret;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(final Long questionId) {
        this.questionId = questionId;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public Long getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Long choiceId) {
        this.choiceId = choiceId;
    }

    public boolean getIsCorrectAnswer() {
        return isCorrectAnswer;
    }

    public void setIsCorrectAnswer(final boolean correctAnswer) {
        isCorrectAnswer = correctAnswer;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
