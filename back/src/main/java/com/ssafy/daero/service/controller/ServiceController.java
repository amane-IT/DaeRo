package com.ssafy.daero.service.controller;

import com.ssafy.daero.service.dto.FaqDto;
import com.ssafy.daero.service.dto.NoticeDto;
import com.ssafy.daero.service.service.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping(value = "/service")
public class ServiceController {

    private final String SUCCESS = "SUCCESS";
    private final String FAILURE = "FAILURE";
    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) { this.serviceService = serviceService; }

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


}
