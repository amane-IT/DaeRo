package com.ssafy.daero.trip.mapper;

import com.ssafy.daero.trip.dto.JourneyDto;
import com.ssafy.daero.trip.dto.TripPlaceDto;
import com.ssafy.daero.trip.dto.TripStampDto;
import com.ssafy.daero.trip.dto.AlbumDto;
import com.ssafy.daero.trip.vo.AlbumVo;
import com.ssafy.daero.trip.vo.JourneyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface TripMapper {

    TripStampDto selectByStampSeq(int tripStampSeq);

    TripPlaceDto selectPlaceByPlaceSeq(int tripPlaceSeq);

    String selectDateByDaySeq(int tripDaySeq);


    ArrayList<JourneyVo> selectOtherJourneyListByUserSeq(@Param("userSeq") int userSeq, @Param("startDate") String startDate, @Param("endDate") String endDate);

    ArrayList<JourneyVo> selectMyJourneyListByUserSeq(@Param("userSeq") int userSeq, @Param("startDate") String startDate, @Param("endDate") String endDate);

    ArrayList<AlbumVo> selectOtherAlbumListByUserSeq(@Param("userSeq") int userSeq, @Param("page") int page);

    ArrayList<AlbumVo> selectMyAlbumListByUserSeq(@Param("userSeq") int userSeq, @Param("page") int page);
}
