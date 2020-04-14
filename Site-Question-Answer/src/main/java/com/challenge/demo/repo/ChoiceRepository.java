package com.challenge.demo.repo;

import com.challenge.demo.bean.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {

}
