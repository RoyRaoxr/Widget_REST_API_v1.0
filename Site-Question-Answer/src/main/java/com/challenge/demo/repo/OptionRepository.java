package com.challenge.demo.repo;

import com.challenge.demo.bean.Option;
import com.challenge.demo.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface OptionRepository extends JpaRepository<Option, Long> {
    @Query(value = "SELECT o.* FROM Option o WHERE o.option_uuid = ?1", nativeQuery = true)
    User findByUuid(UUID optionUUID);
}
