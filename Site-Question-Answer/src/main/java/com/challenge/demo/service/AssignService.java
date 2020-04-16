package com.challenge.demo.service;

import com.challenge.demo.dto.QuestionDTO;
import com.challenge.demo.dto.ResultDTO;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.UUID;

public interface AssignService {

    @Transactional
    ResponseEntity<QuestionDTO> AssignOneQuestion(UUID siteUUID, UUID userUUID);

    ResponseEntity<ResultDTO>  saveResult(UUID userUUID, ResultDTO resultDTO);
}
