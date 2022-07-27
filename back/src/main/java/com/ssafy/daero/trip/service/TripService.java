package com.ssafy.daero.trip.service;

import com.ssafy.daero.trip.dto.JourneyDto;
import com.ssafy.daero.trip.dto.TripDayDto;
import com.ssafy.daero.trip.dto.TripPlaceDto;
import com.ssafy.daero.trip.dto.TripStampDto;
import com.ssafy.daero.trip.mapper.TripMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class TripService {
    private final TripMapper tripMapper;

    public TripService(TripMapper tripMapper) {
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

//    public ArrayList<ArrayList> journeyList(int userSeq) {
//        ArrayList<Integer> userTrips = tripMapper.selectTripSeqListByUserSeq(userSeq);
//        ArrayList<ArrayList> journey = new ArrayList<>();
//        // 여행별로
//        for (int tripSeq: userTrips
//             ) {
//            // 각 여행의 일자별로
//            ArrayList<TripDayDto> tripDays = tripMapper.selectDayListByTripSeq(tripSeq);
//            ArrayList<Map<String, Object>> tripInfo = new ArrayList<>();
//            for (TripDayDto day :
//                    tripDays) {
//                ArrayList<TripStampDto> tripStamps = tripMapper.selectStampListByDaySeq(day.getTripDaySeq());
//                String date = day.getDate();
//                // 일자별 트립스탬프들
//                for (TripStampDto stampDto :
//                        tripStamps) {
//                    Map<String, Object> stampInfo = new HashMap<>();
//                    stampInfo.put("date", date);
//                    stampInfo.put("stamp_seq", stampDto.getTripStampSeq());
//                    TripPlaceDto tripPlaceDto = tripMapper.selectPlaceByPlaceSeq(stampDto.getTripPlaceSeq());
//                    stampInfo.put("latitude", tripPlaceDto.getLatitude());
//                    stampInfo.put("longitude", tripPlaceDto.getLongitude());
//                    tripInfo.add(stampInfo);
//                }
//            }
//            journey.add(tripInfo);
//        }
//        return journey;
//    }

    public ArrayList journeyList(int userSeq, char who, String startDate, String endDate) {
        ArrayList<JourneyDto> jList = new ArrayList<>();
        if (Objects.equals(startDate, "null")) { startDate = "1500-01-01"; }
        if (Objects.equals(endDate, "null")) { endDate = "2500-12-31"; }
        if (who == 'n') {
            jList = tripMapper.selectOtherJourneyListByUserSeq(userSeq, startDate, endDate);
        }
        else {
            jList = tripMapper.selectMyJourneyListByUserSeq(userSeq, startDate, endDate);
        }
        if (jList.size() == 0) { return null; }
        int currentTripSeq = jList.get(0).getTripSeq();
        Map<String, Object> stamps = new HashMap<>(); // 각 스탬프
        ArrayList<Map> trip = new ArrayList<>();      // 여행별로
        ArrayList<ArrayList> journeyList = new ArrayList<>();  // 결과 배열
        // 여행별로 잘라 배열에 넣기
        for (JourneyDto journeyDto :
                jList) {
            System.out.println(journeyDto.getTripSeq() != currentTripSeq);
            if (journeyDto.getTripSeq() != currentTripSeq) {
                journeyList.add(trip);
                trip = new ArrayList<>();
                currentTripSeq = journeyDto.getTripSeq();
            }
            stamps.put("trip_stamp_seq", journeyDto.getTripStampSeq());
            stamps.put("latitude", journeyDto.getLatitude());
            stamps.put("longitude", journeyDto.getLongitude());
//            if (who == 'y') {
//                stamps.put("open_yn", journeyDto.getOpenYn());
//            }
            trip.add(stamps);
            stamps = new HashMap<>();
        }
        journeyList.add(trip);
        System.out.println(journeyList);
        return journeyList;
    }



}
