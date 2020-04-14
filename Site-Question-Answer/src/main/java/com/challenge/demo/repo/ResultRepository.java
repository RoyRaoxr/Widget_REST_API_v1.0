package com.challenge.demo.repo;

import com.challenge.demo.bean.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {
}
