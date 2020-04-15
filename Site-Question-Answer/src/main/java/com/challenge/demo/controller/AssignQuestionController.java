package com.challenge.demo.controller;

import com.challenge.demo.dto.QuestionDTO;
import com.challenge.demo.dto.ResultDTO;
import com.challenge.demo.service.AssignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/assign")
public class AssignQuestionController {

    @Autowired
    AssignService assignService;

    @GetMapping("/{siteUUID}/{userUUID}")
    public ResponseEntity<QuestionDTO> assignOneQuestion(@PathVariable(value = "siteUUID") String siteUUID,
                                                         @PathVariable(value = "userUUID") String userUUID) {
        return assignService.AssignOneQuestion(UUID.fromString(siteUUID), UUID.fromString(userUUID));
    }

    @PostMapping("/{userUUID}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResultDTO> saveResult(@RequestBody ResultDTO incomingResult,
                                                @PathVariable(value = "userUUID") String userUUID) {
        return assignService.saveResult(UUID.fromString(userUUID), incomingResult);
    }
}
