package com.ssafy.daero.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.daero.admin.service.AdminService;
import com.ssafy.daero.sns.service.SnsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private final String SUCCESS = "SUCCESS";
    private final String FAILURE = "FAILURE";

    private final AdminService adminService;
    public AdminController(AdminService adminService) { this.adminService = adminService; }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> userList(@RequestParam(required = false, defaultValue = "1") String page) {
        Map<String, Object> res = adminService.userList(Integer.parseInt(page));
        if (res == null) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<Map<String, Object>> reportList(@RequestParam(required = false, defaultValue = "1") String page) {
        Map<String, Object> res = adminService.reportList(Integer.parseInt(page));
        if (res == null) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/report/{report_seq}")
    public ResponseEntity<String> handleReport(@PathVariable int report_seq) {
        boolean res = adminService.handleReport(report_seq);
        if (res) { return new ResponseEntity<>(SUCCESS, HttpStatus.OK); }
        return new ResponseEntity<>(FAILURE, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/article/{article_seq}")
    public ResponseEntity<Map<String, Object>> articleDetail(@PathVariable int article_seq) throws JsonProcessingException {
        Map<String, Object> res = adminService.articleDetail(article_seq);
        if (res == null) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/article/{article_seq}/reply")
    public ResponseEntity<Map<String, Object>> replyList(@PathVariable int article_seq, @RequestParam(required = false, defaultValue = "1") String page) {
        Map<String, Object> res = adminService.replyList(article_seq, Integer.parseInt(page));
        if (res == null) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/article/{article_seq}/reply/{reply_seq}")
    public ResponseEntity<Map<String, Object>> replyDetail(@PathVariable int article_seq, @PathVariable int reply_seq) {
        Map<String, Object> res = adminService.replyDetail(article_seq, reply_seq);
        if (res == null) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/article")
    public ResponseEntity<Map<String, Object>> articleList(@RequestParam(required = false, defaultValue = "1") String page) {
        Map<String, Object> res = adminService.articleList(Integer.parseInt(page));
        if (res == null) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/users/search")
    public ResponseEntity<Map<String, Object>> searchUser(@RequestParam String search, @RequestParam(required = false, defaultValue = "1") String page) {
        Map<String, Object> res = adminService.searchUser(search, Integer.parseInt(page));
        if (res == null) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
