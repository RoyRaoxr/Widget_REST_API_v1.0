package com.challenge.demo.repo;

import com.challenge.demo.bean.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {

    List<Option> findAllByQuestion_QuestionId(Long questionId);
}
