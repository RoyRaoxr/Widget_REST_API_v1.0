package com.challenge.demo.repo;

import com.challenge.demo.bean.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "SELECT q.* FROM question q WHERE q.site_id = ?1 and q.question_id not in (" +
            "Select q.question_id From question q Join Result r on q.question_id = r.question_id " +
            "Where r.is_visited = 0)Order by RAND() limit 1", nativeQuery = true)
    Question findAssignOneQuestion(Long siteId, Long userId);

}