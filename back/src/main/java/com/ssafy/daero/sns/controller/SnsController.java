package com.ssafy.daero.sns.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.daero.sns.service.SnsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/sns")
public class SnsController {
    private final SnsService snsService;

    public SnsController(SnsService snsService) { this.snsService = snsService; }

    @GetMapping("/article/{article_seq}")
    public ResponseEntity<Map<String, Object>> articleDetail(@PathVariable int article_seq) throws JsonProcessingException {
        Map<String, Object> res = snsService.articleDetail(article_seq);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

}
