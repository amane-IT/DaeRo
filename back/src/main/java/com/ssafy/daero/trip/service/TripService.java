package com.ssafy.daero.trip.service;

import com.ssafy.daero.trip.dto.TripStampDto;
import com.ssafy.daero.trip.mapper.TripMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
        String place = tripMapper.selectPlaceNameByPlaceSeq(stamp.getTripPlaceSeq());
        String datetime = tripMapper.selectDateByDaySeq(stamp.getTripDaySeq());
        stampDetail.put("place", place);
        stampDetail.put("datetime", datetime);
        stampDetail.put("image_url", stamp.getImageUrl());
        return stampDetail;
    }



}
