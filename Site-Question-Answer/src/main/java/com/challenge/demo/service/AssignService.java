package com.challenge.demo.service;

import com.challenge.demo.dto.QuestionDTO;
import com.challenge.demo.dto.ResultDTO;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.UUID;

public interface AssignService {

    @Transactional
    public ResponseEntity<QuestionDTO> AssignOneQuestion(UUID siteUUID, UUID userUUID);

    public ResponseEntity<ResultDTO>  saveResult(UUID siteUUID, UUID userUUID, ResultDTO resultDTO);
}
