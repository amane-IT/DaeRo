package com.ssafy.daero.trip.mapper;

import com.ssafy.daero.trip.dto.TripDayDto;
import com.ssafy.daero.trip.dto.TripStampDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface TripMapper {

    TripStampDto selectByStampSeq(int tripStampSeq);

    String selectPlaceNameByPlaceSeq(int tripPlaceSeq);

    String selectDateByDaySeq(int tripDaySeq);

}
