package com.challenge.demo.repo;

import com.challenge.demo.bean.Result;
import com.challenge.demo.bean.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.UUID;

public interface ResultRepository extends JpaRepository<Result, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE result SET is_visited = 1 WHERE user_id = ?1", nativeQuery = true)
    void resetQuestionList(Long userId);

}
