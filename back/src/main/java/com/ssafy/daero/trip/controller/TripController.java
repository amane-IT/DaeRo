package com.ssafy.daero.trip.controller;


import com.ssafy.daero.trip.service.TripService;
import com.ssafy.daero.user.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/trips")
public class TripController {
    private final TripService tripService;
    private final JwtService jwtService;

    public TripController(TripService tripService, JwtService jwtService) {
        this.tripService = tripService;
        this.jwtService = jwtService;
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

    @PostMapping("/recommend")
    public ResponseEntity<Map<String, Integer>> recommendPost(@RequestHeader("jwt") String jwt, @RequestBody Map<String, ArrayList<Integer>> req) {
        Map<String, Integer> resultMap = new HashMap<>();
        ArrayList<Integer> regionCodes = req.get("regions");
        ArrayList<Integer> tags = req.get("tags");
        int tripPlaceSeq;
        // 아무 태그 없이 검색한 경우
        if (req.isEmpty() || (regionCodes.size() == 0 && tags.size() == 0)) {
            int userSeq = Integer.parseInt(jwtService.decodeJwt(jwt).get("user_seq"));
            tripPlaceSeq = tripService.recommendByRandom(userSeq);
            // 검색된 결과가 없으면 404 Not Found
            if (tripPlaceSeq == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            resultMap.put("place_seq", tripPlaceSeq);
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }
        // 태그로 검색한 경우
        try {
            if (tags.size() == 0) {
                tripPlaceSeq = tripService.recommendByRegions(regionCodes);
            } else {
                tripPlaceSeq = tripService.recommendByTags(regionCodes, tags);
            }
            // 검색된 결과가 없으면 404 Not Found
            if (tripPlaceSeq == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            resultMap.put("place_seq", tripPlaceSeq);
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/recommend?place-name={trip_places_seq}&time={time}&transportation={transportation}")
    public ResponseEntity<Map<String, Integer>> recommendGet(
            @PathVariable("trip_places_seq") int tripPlaceSeq,
            @PathVariable("time") int time,
            @PathVariable("transportation") String transportation) {
        int placeSeq = this.tripService.recommendNextPlace(tripPlaceSeq, time, transportation);
        if (placeSeq == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("place_seq", placeSeq);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @GetMapping("/nearby?place-name={trip_places_seq}")
    public ResponseEntity<List<Map<String, Object>>> nearbyGet(@PathVariable("trip_places_seq") int tripPlaceSeq) {
        LinkedList<Map<String, Object>> responseList;
        try {
            responseList = this.tripService.nearbyPlace(tripPlaceSeq);
        } catch (Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/info/{trip_places_seq}")
    public ResponseEntity<Map<String, Object>> placeInfo(@PathVariable("trip_places_seq") int tripPlaceSeq) {
        Map<String, Object> resultMap = this.tripService.placeInfo(tripPlaceSeq);
        if (resultMap == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Map<String, Object>>> popularPlace() {
        LinkedList<Map<String, Object>> resultList = this.tripService.popularPlace();
        if (resultList == null || resultList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }
}
