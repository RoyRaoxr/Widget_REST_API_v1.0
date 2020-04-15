package com.challenge.demo.repo;

import com.challenge.demo.bean.Choice;
import com.challenge.demo.bean.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    List<Choice> findAllByQuestion_QuestionId(Long questionId);
}
