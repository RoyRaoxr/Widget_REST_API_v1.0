package com.challenge.demo.repo;

import com.challenge.demo.bean.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
