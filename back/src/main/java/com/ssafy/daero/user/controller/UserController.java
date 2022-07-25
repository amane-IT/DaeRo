package com.ssafy.daero.controller;

import com.ssafy.daero.services.UserService;
import com.ssafy.daero.vo.SignupVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final String SUCCESS = "SUCCESS";
    private final String FAILURE = "FAILURE";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signupPost(@RequestBody SignupVo signupVo) {
        if (userService.signup(signupVo)) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>(FAILURE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/email")
    public ResponseEntity<String> emailAuthPost(@RequestBody Map<String, String> req) {

        return new ResponseEntity<>(FAILURE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("{user_seq}/password")
    public ResponseEntity<Character> passwordChangeGet(@RequestParam int userSeq) {
        
    }
}
