package com.ssafy.daero.trip.service;

import com.ssafy.daero.trip.dto.*;
import com.ssafy.daero.trip.mapper.TripMapper;
import com.ssafy.daero.trip.vo.AlbumVo;
import com.ssafy.daero.trip.vo.JourneyVo;
import com.ssafy.daero.user.dto.UserDto;
import com.ssafy.daero.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class TripService {
    private final TripMapper tripMapper;
    private final UserMapper userMapper;

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
        else {
            jList = tripMapper.selectMyJourneyListByUserSeq(userSeq, startDate, endDate);
        }
        if (jList.size() == 0) {
            journeyVo.setResult(JourneyVo.ProfileResult.NO_CONTENT);
            journeyList.add(jList);
            return journeyList;
        }
        int currentTripSeq = jList.get(0).getTripSeq();
        Map<String, Object> stamps = new HashMap<>(); // 각 스탬프
        ArrayList<Map> trip = new ArrayList<>();      // 여행별로

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

    public ArrayList albumList(int userSeq, String page, char who) {
        UserDto userDto = userMapper.selectByUserSeq(userSeq);
        ArrayList albumList = new ArrayList<>(); // 결과 배열
        AlbumVo albumVo = new AlbumVo();
        if (userDto == null) {
            albumVo.setResult(JourneyVo.ProfileResult.NO_SUCH_USER);
            albumList.add(albumVo);
            return albumList;
        }
        if (userDto.getDelYn() == 'y') {
            albumVo.setResult(JourneyVo.ProfileResult.DELETED);
            albumList.add(albumVo);
            return albumList;
        }

        int pagenum = Integer.parseInt(page);
        ArrayList<AlbumVo> albumVos = new ArrayList<>();
        if (who == 'n') {
            albumVos = tripMapper.selectOtherAlbumListByUserSeq(userSeq, pagenum);
        }
        else {
            albumVos = tripMapper.selectMyAlbumListByUserSeq(userSeq, pagenum);
        }
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
        return albumList;
        
    }



}
