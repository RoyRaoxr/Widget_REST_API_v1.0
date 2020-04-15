package com.challenge.demo.dto;

import com.challenge.demo.bean.Question;
import com.challenge.demo.bean.QuestionAnswer;
import com.challenge.demo.bean.Result;
import com.challenge.demo.bean.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultDTO {
    private Long resultId;

    private Long questionId;

    private Long questionAnswerId;

    private Long userId;

    private Date createAt;

    private Date updatedAt;

    private boolean isVisited;

    public static Result transform(final ResultDTO newResultDto, final Question question,
                                   final QuestionAnswer questionAnswer, final User user) {
        final Result newResult = new Result();
        newResult.setQuestion(question);
        newResult.setIsVisited(newResultDto.isVisited());
        newResult.setUser(user);
        newResult.setQuestionAnswer(questionAnswer);
        return newResult;
    }

    public static ResultDTO build(final Result result) {
        final ResultDTO newResultDto = new ResultDTO();
        newResultDto.setResultId(result.getResultId());
        newResultDto.setQuestionAnswerId(result.getQuestionAnswer().getId());
        newResultDto.setQuestionId(result.getQuestion().getQuestionId());
        newResultDto.setUserId(result.getUser().getUserId());
        newResultDto.setCreateAt(result.getCreatedAt());
        newResultDto.setUpdatedAt(result.getUpdatedAt());
        newResultDto.setVisited(result.getIsVisited());

        return newResultDto;
    }

    public static List<ResultDTO> build(final List<Result> results) {
        final List<ResultDTO> resultDTOList = new ArrayList<>();
        for (Result re : results) {
            resultDTOList.add(build(re));
        }
        return resultDTOList;
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getQuestionAnswerId() {
        return questionAnswerId;
    }

    public void setQuestionAnswerId(Long questionAnswerId) {
        this.questionAnswerId = questionAnswerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
