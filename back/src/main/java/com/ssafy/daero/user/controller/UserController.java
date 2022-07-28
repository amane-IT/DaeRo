package com.ssafy.daero.user.controller;

import com.ssafy.daero.user.service.JwtService;
import com.ssafy.daero.user.service.UserService;
import com.ssafy.daero.user.vo.LoginVo;
import com.ssafy.daero.user.vo.SignupVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final char YES = 'y';
    private final char NO = 'n';

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signupPost(@RequestBody SignupVo signupVo) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        if (userService.signup(signupVo)) {
            String jwtToken = jwtService.create(signupVo.getUserSeq(), signupVo.getUserEmail());
            resultMap.put("jwt", jwtToken);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/{userSeq}/verified")
    public ResponseEntity<Map<String, Character>> checkEmailVerifiedGet(@PathVariable int userSeq) {
        Map<String, Character> resultMap = new HashMap<>();
        if (userService.checkEmailVerified(userSeq)) {
            resultMap.put("result", YES);
        } else {
            resultMap.put("result", NO);
        }
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PostMapping("/nickname")
    public ResponseEntity<Map<String, Character>> checkNicknameDuplicatePost(@RequestBody Map<String, String> request) {
        Map<String, Character> resultMap = new HashMap<>();
        if (userService.nicknameAvailable(request.get("nickname"))) {
            resultMap.put("available_YN", YES);
        } else {
            resultMap.put("available_YN", NO);
        }
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PostMapping("{userSeq}/password")
    public ResponseEntity<Map<String, Character>> changePasswordPost(@PathVariable int userSeq, @RequestBody Map<String, String> request) {
        Map<String, Character> resultMap = new HashMap<>();
        HttpStatus status;
        if (userService.checkPasswordByUserSeq(userSeq, request.get("password"))) {
            resultMap.put("result", YES);
            status = HttpStatus.OK;
        } else {
            resultMap.put("result", NO);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @PutMapping("{userSeq}/password")
    public ResponseEntity<Map<String, Character>> changePasswordPut(@PathVariable int userSeq, @RequestBody Map<String, String> request) {
        Map<String, Character> resultMap = new HashMap<>();
        HttpStatus status;
        if (userService.changePasswordByUserSeq(userSeq, request.get("new_password"))) {
            resultMap.put("result", YES);
            status = HttpStatus.OK;
        } else {
            resultMap.put("result", NO);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, Character>> resetPasswordPost(@RequestBody Map<String, String> request) throws MessagingException {
        Map<String, Character> resultMap = new HashMap<>();
        HttpStatus status;
        if (userService.resetPassword(request.get("user_email"))) {
            resultMap.put("result", YES);
            status = HttpStatus.OK;
        } else {
            resultMap.put("result", NO);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @PostMapping("/email")
    public ResponseEntity<Map<String, Integer>> verifyEmailPost(@RequestBody Map<String, String> request) throws MessagingException {
        Map<String, Integer> resultMap = new HashMap<>();
        HttpStatus status;
        String userEmail = request.get("user_email");
        if (userService.verifyEmail(userEmail)) {
            resultMap.put("user_seq", userService.findUserSeq(userEmail));
            status = HttpStatus.OK;
        } else {
            resultMap.put("failure", 0);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginPost(@RequestBody LoginVo loginVO) {
        int userSeq = userService.login(loginVO);
        String jwt = jwtService.create(userSeq, loginVO.getId());
        Map<String, Object> response = new HashMap<>();
        if (userSeq != 0) {
            response.put("user_seq", userSeq);
            response.put("jwt", jwt);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{email_address}")
    public ResponseEntity<Map<String, Object>> findId(@PathVariable String email_address) {
        boolean res = userService.findId(email_address);
        Map<String, Object> response = new HashMap<>();
        response.put("result", res);
        if (res) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{user_seq}/profile")
    public ResponseEntity<Map<String, Object>> userProfile(@RequestHeader Map<String, String> header, @PathVariable int user_seq) {
        String userJwt = header.get("jwt");
        Map<String, String> currentUser = jwtService.decodeJwt(userJwt);
//        System.out.println(currentUser.get("user_seq"));
        if (currentUser.get("user_seq") == null) { return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); }
        Map<String, Object> res = userService.userProfile(user_seq, Integer.parseInt(currentUser.get("user_seq")));
        if (res != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{user_seq}/profile")
    public ResponseEntity<String> userProfileUpdate(@RequestHeader Map<String, String> header, @PathVariable int user_seq, @RequestBody Map<String, String> req) {
        String userJwt = header.get("jwt");
        Map<String, String> currentUser = jwtService.decodeJwt(userJwt);
        if (Integer.parseInt(currentUser.get("user_seq")) != user_seq) { return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); }
        boolean res = userService.updateUserProfile(user_seq, req.get("nickname"));
        if (res) { return new ResponseEntity<>(HttpStatus.OK); }
        else { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
    }

    @PutMapping("/{user_seq}")
    public ResponseEntity<String> leaveUser(@PathVariable int user_seq) {
        boolean res = userService.leaveUser(user_seq);
        if (res) { return new ResponseEntity<>(HttpStatus.OK); }
        else { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
    }

//    @GetMapping("/jwt/jwt")
//    public ResponseEntity<String> jwtTest(@RequestHeader Map<String, String> header) {
////        System.out.println(header);
//        String jwt = header.get("jwt");
////        System.out.println(jwt);
//        String res = jwtService.decodeJwt(jwt);
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }
}
