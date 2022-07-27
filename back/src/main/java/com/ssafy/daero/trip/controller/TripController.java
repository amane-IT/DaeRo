package com.ssafy.daero.trip.controller;


import com.ssafy.daero.trip.service.TripService;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Map<String, Object>> tripStampDetail(@RequestHeader Map<String, String> header, @PathVariable int trip_stamp_seq) {
        String userJwt = header.get("jwt");

        Map<String, Object> res = tripService.tripStampDetail(trip_stamp_seq);
        if (res != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
