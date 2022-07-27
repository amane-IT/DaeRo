package com.ssafy.daero.trip.mapper;

import com.ssafy.daero.trip.dto.JourneyDto;
import com.ssafy.daero.trip.dto.TripDayDto;
import com.ssafy.daero.trip.dto.TripPlaceDto;
import com.ssafy.daero.trip.dto.TripStampDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface TripMapper {

    TripStampDto selectByStampSeq(int tripStampSeq);

    TripPlaceDto selectPlaceByPlaceSeq(int tripPlaceSeq);

    String selectDateByDaySeq(int tripDaySeq);

//    ArrayList<Integer> selectTripSeqListByUserSeq(int userSeq);

//    ArrayList<TripDayDto> selectDayListByTripSeq(int tripSeq);
//
//    ArrayList<TripStampDto> selectStampListByDaySeq(int tripDaySeq);

    ArrayList<JourneyDto> selectOtherJourneyListByUserSeq(@Param("userSeq") int userSeq, @Param("startDate") String startDate, @Param("endDate") String endDate);

    ArrayList<JourneyDto> selectMyJourneyListByUserSeq(@Param("userSeq") int userSeq, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
