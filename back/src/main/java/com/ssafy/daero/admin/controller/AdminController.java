package com.ssafy.daero.admin.controller;

import com.ssafy.daero.admin.service.AdminService;
import com.ssafy.daero.admin.vo.UserVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
}
