package com.challenge.demo.repo;

import com.challenge.demo.bean.Question;
import com.challenge.demo.bean.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {

    List<QuestionAnswer> findAllByQuestion(Question question);
}
