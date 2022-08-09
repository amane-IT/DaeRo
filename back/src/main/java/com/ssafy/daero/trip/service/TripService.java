package com.ssafy.daero.trip.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.daero.trip.dto.*;
import com.ssafy.daero.trip.mapper.TripMapper;
import com.ssafy.daero.trip.vo.*;
import com.ssafy.daero.user.dto.UserDto;
import com.ssafy.daero.user.dto.UserFavorDto;
import com.ssafy.daero.user.mapper.UserMapper;
import com.ssafy.daero.common.util.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("FieldCanBeLocal")
@Service
public class TripService {
    private final TripMapper tripMapper;
    private final UserMapper userMapper;
    private final int CAR_SPEED = 50;
    private final int WALK_SPEED = 2;

    @Autowired
    public TripService(TripMapper tripMapper, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.tripMapper = tripMapper;
    }

    public Map<String, Object> tripStampDetail(int tripStampSeq) {
        Map<String, Object> stampDetail = new HashMap<>();
        TripStampDto stamp = tripMapper.selectByStampSeq(tripStampSeq);
        if (stamp == null) { return null; }
        String place = tripMapper.selectPlaceByPlaceSeq(stamp.getTripPlaceSeq()).getPlaceName();
        String datetime = tripMapper.selectDateByDaySeq(stamp.getTripDaySeq());
        stampDetail.put("place", place);
        stampDetail.put("datetime", datetime);
        stampDetail.put("image_url", stamp.getImageUrl());
        return stampDetail;
    }


    public ArrayList<ArrayList> journeyList(int userSeq, char who, String startDate, String endDate) {
        UserDto userDto = userMapper.selectByUserSeq(userSeq);
        JourneyVo journeyVo = new JourneyVo();
        ArrayList<JourneyVo> jList = new ArrayList<>();
        ArrayList<ArrayList> journeyList = new ArrayList<>();  // 결과 배열
        if (userDto == null) {
            journeyVo.setResult(JourneyVo.ProfileResult.NO_SUCH_USER);
            jList.add(journeyVo);
            journeyList.add(jList);
            return journeyList;
        }
        if (userDto.getDelYn() == 'y') {
            journeyVo.setResult(JourneyVo.ProfileResult.DELETED);
            journeyList.add(jList);
            return journeyList;
        }

        if (Objects.equals(startDate, "null")) { startDate = "1500-01-01"; }
        if (Objects.equals(endDate, "null")) { endDate = "2500-12-31"; }
        if (who == 'n') {
            jList = tripMapper.selectOtherJourneyListByUserSeq(userSeq, startDate, endDate);
        }
        else {            jList = tripMapper.selectMyJourneyListByUserSeq(userSeq, startDate, endDate);
        }
        if (jList.size() == 0) {
            journeyVo.setResult(JourneyVo.ProfileResult.NO_CONTENT);
            journeyList.add(jList);
            return journeyList;
        }
        int currentTripSeq = jList.get(0).getTripSeq();
        Map<String, Object> stamps = new HashMap<>(); // 각 스탬프
        ArrayList<Map<String, Object>> trip = new ArrayList<>();      // 여행별로

        // 여행별로 잘라 배열에 넣기
        for (JourneyVo jVo :
                jList) {
            if (jVo.getTripSeq() != currentTripSeq) {
                journeyList.add(trip);
                trip = new ArrayList<>();
                currentTripSeq = jVo.getTripSeq();
            }
            stamps.put("trip_stamp_seq", jVo.getTripStampSeq());
            stamps.put("latitude", jVo.getLatitude());
            stamps.put("longitude", jVo.getLongitude());
            trip.add(stamps);
            stamps = new HashMap<>();
        }
        journeyList.add(trip);
        return journeyList;
    }

    public Map<String, Object> albumList(int userSeq, String page, char who) {
        UserDto userDto = userMapper.selectByUserSeq(userSeq);
        AlbumVo albumVo = new AlbumVo();
        if (userDto == null | userDto.getDelYn() == 'y') {
            return null;
        }
        int totalPage = (int) Math.ceil((tripMapper.selectAlbumCountByUserSeq(userSeq))/10.0);
        if (totalPage == 0) { totalPage = 1; }
        int pagenum = Integer.parseInt(page);
        if (pagenum > totalPage) { return null; }
        ArrayList<AlbumVo> albumVos = new ArrayList<>();
        if (who == 'n') {
            albumVos = tripMapper.selectOtherAlbumListByUserSeq(userSeq, pagenum);
        }
        else {
            albumVos = tripMapper.selectMyAlbumListByUserSeq(userSeq, pagenum);
        }

        Map<String, Object> results = new HashMap<>(); // 결과 배열
        ArrayList<Map<String, Object>> albumList = new ArrayList<>();
        Map<String, Object> album = new HashMap<>();
        for (AlbumVo aVo :
                albumVos) {
            album.put("expose", aVo.getExpose());
            album.put("trip_seq", aVo.getTripSeq());
            album.put("image_url", aVo.getImageUrl());
            album.put("title", aVo.getTitle());
            if (aVo.getLikeYn() == 1) {
                album.put("like_yn", "y");
            }
            else { album.put("like_yn", "n"); }
            albumList.add(album);
            album = new HashMap<>();
        }
        results.put("total_page", totalPage);
        results.put("page", pagenum);
        results.put("results", albumList);
        return results;
        
    }

    public int equalizeAndRandomTag(int userSeq) {
        // 태그별 점수 총합 구하기
        ArrayList<UserFavorDto> userFavorDtoArray = tripMapper.selectUserFavorByUserSeq(userSeq);
        int scoreSum = 0;
        for (UserFavorDto userFavorDto : userFavorDtoArray) {
            scoreSum += userFavorDto.getScore();
        }
        // 총합이 200 이상이면 총합 100 수준으로 평준화
        if (scoreSum > 200) {
            double ratio = scoreSum / 100.0;
            for (UserFavorDto userFavorDto : userFavorDtoArray) {
                int score = (int)(userFavorDto.getScore() / ratio);
                userFavorDto.setScore(score);
            }
            // 평준화한 점수 DB에 갱신하고 총합 반환
            scoreSum = 0;
            for (UserFavorDto userFavorDto : userFavorDtoArray) {
                tripMapper.updateUserFavor(userFavorDto);
                scoreSum += userFavorDto.getScore();
            }
        }
        // 현재 점수 목록 가져오기
        ArrayList<UserFavorDto> favors = tripMapper.selectUserFavorByUserSeq(userSeq);
        // 태그순 정렬해서 큐에 점수만큼 저장.
        Queue<Integer> favorQueue = new LinkedList<>();
        for (UserFavorDto userFavorDto : favors) {
            favorQueue.add(userFavorDto.getScore());
        }
        if (favorQueue.isEmpty()) {
            return 0;
        }
        // 범위내 랜덤 숫자 정해서 걸리는 태그 검색, 선호도 높은 태그에 가중치 걸림.
        int select = (int) (Math.random() * scoreSum);
        int tag = 0;
        do {
            select -= favorQueue.poll();
            tag++;
        } while (select > 0 && !favorQueue.isEmpty());
        return tag;
    }

    public int recommendByRandom(int userSeq) {
        // 점수 평준화
        int tag = equalizeAndRandomTag(userSeq);
        // 선택된 태그에서 여행지 검색
        ArrayList<RecommendTagVo> recommendPool = tripMapper.selectPlaceByTag(tag);
        int len = recommendPool.size();
        if (len == 0) return 0;
        int selected = (int)(len * Math.random());
        return recommendPool.get(selected).getPlaceSeq();
    }

    public int recommendByTags(ArrayList<Integer> regionCodes, ArrayList<Integer> tags) {
        LinkedList<RecommendTagVo> recommendPool = new LinkedList<>();
        // 태그 하나로 검색
        for (int tag : tags) {
            ArrayList<RecommendTagVo> pool = tripMapper.selectPlaceByTag(tag);
            // 현재 태그로 검색된 지역들에 대하여
            for (RecommendTagVo place : pool) {
                // 검색한 지역범위 내에 포함되면 후보에 넣음, 지역 선택 안하고 검색하면 전체범위 검색
                if (regionCodes.size() == 0 || regionCodes.contains(place.getRegionSeq())) {
                    recommendPool.add(place);
                }
            }
        }
        // 후보군 내에서 랜덤 1개 찾아서 반환
        int len = recommendPool.size();
        if (len == 0) return 0;
        int selected = (int)(Math.random() * len);
        return recommendPool.get(selected).getPlaceSeq();
    }

    public int recommendByRegions(ArrayList<Integer> regionCodes) {
        LinkedList<RecommendTagVo> recommendPool = new LinkedList<>();
        for (int region : regionCodes) {
            recommendPool.addAll(tripMapper.selectPlaceByRegion(region));
        }
        int len = recommendPool.size();
        if (len == 0) return 0;
        int selected = (int)(Math.random() * len);
        return recommendPool.get(selected).getPlaceSeq();
    }

    public int recommendNextPlace(int placeSeq, int time, String transportation) {
        // 현재 위치 가져오기
        TripPlaceDto tripPlaceDto;
        try {
            tripPlaceDto = this.tripMapper.selectPlaceByPlaceSeq(placeSeq);
        } catch (Exception e) {
            return 0;
        }
        // 이동수단에 따른 거리 계산
        double distance;
        if (transportation.equals("car")) {
            distance = time * CAR_SPEED / 60.0;
        } else {
            distance = time * WALK_SPEED / 60.0;
        }
        // 위, 경도 범위 계산
        double[] coordinateRange = DistanceUtil.ReverseHaversine.calculateCoordinate(
                tripPlaceDto.getLatitude(),tripPlaceDto.getLongitude(), distance);

        ArrayList<TripPlaceDto> recommendPool = tripMapper.selectOtherTripByCoordinate(
                coordinateRange[0], coordinateRange[1], coordinateRange[2], coordinateRange[3], placeSeq);
        int len = recommendPool.size();
        System.out.println(len);
        if (len == 0) return 0;
        int selected = (int)(Math.random() * len);
        return recommendPool.get(selected).getTripPlaceSeq();
    }

    public LinkedList<Map<String, Object>> nearbyPlace(int placeSeq) {
        final int NEARBY_DISTANCE = 10;
        // 현재 위치 가져오기
        TripPlaceDto tripPlaceDto = this.tripMapper.selectPlaceByPlaceSeq(placeSeq);
        // 위, 경도 범위 계산
        double[] coordinateRange = DistanceUtil.ReverseHaversine.calculateCoordinate(
                tripPlaceDto.getLatitude(),tripPlaceDto.getLongitude(), NEARBY_DISTANCE);

        ArrayList<TripPlaceDto> nearbyPool = tripMapper.selectOtherTripByCoordinateLimit(
                coordinateRange[0], coordinateRange[1], coordinateRange[2], coordinateRange[3], placeSeq);
        return iteratePlaceInfo(nearbyPool);
    }

    public Map<String, Object> placeInfo(int placeSeq) {
        TripPlaceDto tripPlaceDto = this.tripMapper.selectPlaceByPlaceSeq(placeSeq);
        if (tripPlaceDto == null) {
            return null;
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("place_seq", tripPlaceDto.getTripPlaceSeq());
        resultMap.put("image_url", tripPlaceDto.getImageUrl());
        resultMap.put("place_name", tripPlaceDto.getPlaceName());
        resultMap.put("address", tripPlaceDto.getAddress());
        resultMap.put("description", tripPlaceDto.getDescription());
        resultMap.put("latitude", tripPlaceDto.getLatitude());
        resultMap.put("longitude", tripPlaceDto.getLongitude());
        return resultMap;
    }

    public LinkedList<Map<String, Object>> popularPlace() {
        return iteratePlaceInfo(this.tripMapper.selectPlacesByTripPlaceSeqRange());
    }

    private LinkedList<Map<String, Object>> iteratePlaceInfo(ArrayList<TripPlaceDto> tripPlaceDtos) {
        LinkedList<Map<String, Object>> resultList = new LinkedList<>();
        for (TripPlaceDto tripPlaceDto : tripPlaceDtos) {
            Map<String, Object> map = new HashMap<>();
            map.put("trip_place_seq", tripPlaceDto.getTripPlaceSeq());
            map.put("image_url", tripPlaceDto.getImageUrl());
            map.put("place_name", tripPlaceDto.getPlaceName());
            resultList.add(map);
        }
        return resultList;
    }

    public void writeArticle(TripVo tripVo, String[] urls) {
        int index = 0;
        int tripSeq = this.tripMapper.insertTrip(tripVo);
        tripVo.setTripSeq(tripSeq);
        tripVo.setThumbnailUrl(urls[tripVo.getThumbnailIndex()]);
        tripVo.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        for (TripDayVo tripDayVo : tripVo.getRecords()) {
            tripDayVo.setTripSeq(tripSeq);
            int tripDaySeq = this.tripMapper.insertTripDay(tripDayVo);
            for (TripStampVo tripStampVo : tripDayVo.getTripStamps()) {
                tripStampVo.setTripSeq(tripSeq);
                tripStampVo.setTripDaySeq(tripDaySeq);
                tripStampVo.setUrl(urls[index]);
                this.tripMapper.insertTripStamp(tripStampVo);
            }
        }
        this.tripMapper.insertArticle(tripVo);
    }

    public TripVo writeJsonParser(String jsonString) {
        JsonObject tripJson = JsonParser.parseString(jsonString).getAsJsonObject();
        TripVo tripVo = new TripVo();
        tripVo.setTitle(tripJson.get("title").getAsString());
        tripVo.setTripComment(tripJson.get("tripComment").getAsString());
        tripVo.setTripExpenses(tripJson.get("tripExpenses").getAsString());
        tripVo.setRating(tripJson.get("rating").getAsInt());
        tripVo.setExpose(tripJson.get("expose").getAsString());
        tripVo.setThumbnailIndex(tripJson.get("thumbnailIndex").getAsInt());

        JsonArray tripDayJsonArray = tripJson.get("records").getAsJsonArray();
        LinkedList<TripDayVo> tripDayVos = new LinkedList<>();
        for (int i = 0; i < tripDayJsonArray.size(); i++) {
            JsonObject tripDay = tripDayJsonArray.get(i).getAsJsonObject();
            TripDayVo tripDayVo = new TripDayVo();
            tripDayVo.setDatetime(tripDay.get("datetime").getAsString());
            tripDayVo.setDayComment(tripDay.get("dayComment").getAsString());

            JsonArray tripStampJsonArray = tripDay.get("tripStamps").getAsJsonArray();
            LinkedList<TripStampVo> tripStampVos = new LinkedList<>();
            for (int j = 0; j < tripStampJsonArray.size(); j++) {
                JsonObject tripStamp = tripStampJsonArray.get(i).getAsJsonObject();
                TripStampVo tripStampVo = new TripStampVo();
                tripStampVo.setSatisfaction(tripStamp.get("satisfaction").getAsString());
                tripStampVo.setTripPlace(tripStamp.get("tripPlaceSeq").getAsInt());
                tripStampVos.add(tripStampVo);
            }
            tripDayVo.setTripStamps(tripStampVos);
            tripDayVos.add(tripDayVo);
        }
        tripVo.setRecords(tripDayVos);
        System.out.println(tripVo.getRecords().size());
        return tripVo;
    }
}