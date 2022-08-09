package com.ssafy.daero.trip.controller;

import com.ssafy.daero.image.service.ImageService;
import com.ssafy.daero.image.vo.ImageVo;
import com.ssafy.daero.trip.service.TripService;
import com.ssafy.daero.trip.vo.TripVo;
import com.ssafy.daero.user.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping(value = "/trips")
public class TripController {
    private final TripService tripService;
    private final JwtService jwtService;
    private final ImageService imageService;

    public TripController(TripService tripService, JwtService jwtService, ImageService imageService) {
        this.tripService = tripService;
        this.jwtService = jwtService;
        this.imageService = imageService;
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
    public ResponseEntity<Map<String, Object>> albumList(@PathVariable int user_seq, @RequestParam(defaultValue = "1") String page) {
        Map<String, Object> res = tripService.albumList(user_seq, page, 'n');
        if (res == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else { return new ResponseEntity<>(res, HttpStatus.OK); }
    }

    @GetMapping("/my/{user_seq}/album")
    public ResponseEntity<Map<String, Object>> myAlbumList(@PathVariable int user_seq, @RequestParam(defaultValue = "1") String page) {
        Map<String, Object> res = tripService.albumList(user_seq, page, 'y');
        if (res == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else { return new ResponseEntity<>(res, HttpStatus.OK); }
    }

    @PostMapping("/recommend")
    public ResponseEntity<Map<String, Integer>> recommendPost(@RequestHeader("jwt") String jwt, @RequestBody(required=false) Map<String, ArrayList<Integer>> req) {
        Map<String, Integer> resultMap = new HashMap<>();
        int tripPlaceSeq;
        // 태그로 검색한 경우
        if (req != null && (req.get("regions") != null && req.get("tags") != null) &&
                (req.get("regions").size() != 0 || req.get("tags").size() != 0)) {
            ArrayList<Integer> regionCodes = req.get("regions");
            ArrayList<Integer> tags = req.get("tags");
            if (tags.size() == 0) {
                tripPlaceSeq = tripService.recommendByRegions(regionCodes);
            } else {
                tripPlaceSeq = tripService.recommendByTags(regionCodes, tags);
            }
            // 검색된 결과가 없으면 404 Not Found
            if (tripPlaceSeq == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            resultMap.put("place_seq", tripPlaceSeq);
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }
        int userSeq = Integer.parseInt(jwtService.decodeJwt(jwt).get("user_seq"));
        tripPlaceSeq = tripService.recommendByRandom(userSeq);
        // 검색된 결과가 없으면 404 Not Found
        if (tripPlaceSeq == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        resultMap.put("place_seq", tripPlaceSeq);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @GetMapping("/recommend")
    public ResponseEntity<Map<String, Integer>> recommendGet(
            @RequestParam("place-seq") int tripPlaceSeq,
            @RequestParam("time") int time,
            @RequestParam("transportation") String transportation) {
        int placeSeq = this.tripService.recommendNextPlace(tripPlaceSeq, time, transportation);
        Map<String, Integer> resultMap = new HashMap<>();
        if (placeSeq == 0) {
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }
        resultMap.put("place_seq", placeSeq);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<Map<String, Object>>> nearbyGet(@RequestParam("place-seq") int tripPlaceSeq) {
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
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Map<String, Object>>> popularPlace() {
        LinkedList<Map<String, Object>> resultList = this.tripService.popularPlace();
        if (resultList == null || resultList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> finishTrip(@RequestHeader("jwt") String jwt,
                                                          @RequestParam("json") String jsonString,
                                                          @RequestParam ArrayList<MultipartFile> files) {
        System.out.println(jsonString);

        int fileNum = files.size();
        String[] urls = new String[fileNum];

        TripVo tripVo;
        try {
            tripVo = this.tripService.writeJsonParser(jsonString);
        } catch (Exception e) {
            System.out.println("ERROR : Parsing error");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("files 크기" + files.size());
        System.out.println("제목 : " + tripVo.getTitle());

        for (int i = 0; i < fileNum; i++) {
            ImageVo imageVo = this.imageService.uploadFile(files.get(i));
            if (imageVo.getResult() != ImageVo.ImageResult.SUCCESS) {

                System.out.println("ERROR : " + i + "번째 사진 업로드 실패");
                System.out.println("ERROR TYPE : " + imageVo.getResult());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            urls[i] = imageVo.getDownloadUrl();
        }
        tripVo.setUserSeq(this.jwtService.getUserSeq(jwt));
        try {
            this.tripService.writeArticle(tripVo, urls);
        } catch (Exception e) {
            System.out.println("ERROR : DB 에러");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
