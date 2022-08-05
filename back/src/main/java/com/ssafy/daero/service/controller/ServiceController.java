package com.ssafy.daero.service.controller;

import com.ssafy.daero.service.dto.FaqDto;
import com.ssafy.daero.service.dto.NoticeDto;
import com.ssafy.daero.service.service.ServiceService;
import com.ssafy.daero.user.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/service")
public class ServiceController {

    private final String SUCCESS = "SUCCESS";
    private final String FAILURE = "FAILURE";
    private final ServiceService serviceService;
    private final JwtService jwtService;

    public ServiceController(ServiceService serviceService, JwtService jwtService) {
        this.serviceService = serviceService;
        this.jwtService = jwtService;
    }

    @GetMapping("/notice")
    public ResponseEntity<ArrayList<NoticeDto>> noticeList() {
        ArrayList<NoticeDto> res = serviceService.noticeList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/faq")
    public ResponseEntity<ArrayList<FaqDto>> faqList() {
        ArrayList<FaqDto> res = serviceService.faqList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/inquiry")
    public ResponseEntity<Map<String, Object>> inquiryList(@RequestHeader("jwt") String jwt, @RequestParam(defaultValue = "1") String page) {
        Map<String, String> currentUser = jwtService.decodeJwt(jwt);
        Map<String, Object> res = serviceService.inquiryList(Integer.parseInt(currentUser.get("user_seq")), Integer.parseInt(page));
        if (res == null) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/inquiry")
    public ResponseEntity<String> createInquiry(@RequestHeader("jwt") String jwt, @RequestBody Map<String, String> req) {
        Map<String, String> currentUser = jwtService.decodeJwt(jwt);
        boolean res = serviceService.createInquiry(Integer.parseInt(currentUser.get("user_seq")), req.get("title"), req.get("content"));
        if (res) { return new ResponseEntity<>(SUCCESS, HttpStatus.CREATED); }
        return new ResponseEntity<>(FAILURE, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/version")
    public ResponseEntity<Map<String, String>> latestVersion() {
        Map<String, String> res = new HashMap<>();
        res.put("version", "1.0");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


}
