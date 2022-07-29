package com.ssafy.daero.trip.controller;


import com.ssafy.daero.trip.service.TripService;
import com.ssafy.daero.trip.vo.JourneyVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping(value = "/trips")
public class TripController {
    private final String SUCCESS = "SUCCESS";
    private final String FAILURE = "FAILURE";

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }


    // tripstamp 상세 조회
    @GetMapping("/trip-stamp/{trip_stamp_seq}")
    public ResponseEntity<Map<String, Object>> tripStampDetail(@PathVariable int trip_stamp_seq) {
        Map<String, Object> res = tripService.tripStampDetail(trip_stamp_seq);
        if (res != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{user_seq}/journey")
    public ResponseEntity<ArrayList<ArrayList>> journeyList(@PathVariable int user_seq, @RequestParam(value = "start-date", required = false, defaultValue = "null") String start_date, @RequestParam(value = "end-date", required = false, defaultValue = "null") String end_date) {
        ArrayList<ArrayList> res = tripService.journeyList(user_seq, 'n', start_date, end_date);
        if (res.get(0).get(0).getClass().getName().equals("com.ssafy.daero.trip.vo.JourneyVo")) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @GetMapping("/my/{user_seq}/journey")
    public ResponseEntity<ArrayList<ArrayList>> myJourneyList(@PathVariable int user_seq, @RequestParam(value = "start-date", required = false, defaultValue = "null") String start_date, @RequestParam(value = "end-date", required = false, defaultValue = "null") String end_date) {
        ArrayList<ArrayList> res = tripService.journeyList(user_seq, 'y', start_date, end_date);
        if (res.get(0).get(0).getClass().getName().equals("com.ssafy.daero.trip.vo.JourneyVo")) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @GetMapping("/user/{user_seq}/album")
    public ResponseEntity<ArrayList<Map<String, Object>>> albumList(@PathVariable int user_seq, @RequestParam(defaultValue = "1") String page) {
        ArrayList res = tripService.albumList(user_seq, page, 'n');
        if (res.get(0).getClass().getName().equals("com.ssafy.daero.trip.vo.AlbumVo") | res.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else { return new ResponseEntity<>(res, HttpStatus.OK); }
    }

    @GetMapping("/my/{user_seq}/album")
    public ResponseEntity<ArrayList<Map<String, Object>>> myAlbumList(@PathVariable int user_seq, @RequestParam(defaultValue = "1") String page) {
        ArrayList res = tripService.albumList(user_seq, page, 'y');
        if (res.get(0).getClass().getName().equals("com.ssafy.daero.trip.vo.AlbumVo") | res.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else { return new ResponseEntity<>(res, HttpStatus.OK); }
    }

}
