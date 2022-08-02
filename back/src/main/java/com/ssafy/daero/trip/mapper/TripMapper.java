package com.ssafy.daero.trip.mapper;

import com.ssafy.daero.trip.dto.*;
import com.ssafy.daero.trip.vo.AlbumVo;
import com.ssafy.daero.trip.vo.JourneyVo;
import com.ssafy.daero.trip.vo.RecommendTagVo;
import com.ssafy.daero.user.dto.UserFavorDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.LinkedList;

@SuppressWarnings("UnusedReturnValue")
@Mapper
public interface TripMapper {

    TripStampDto selectByStampSeq(int tripStampSeq);

    TripPlaceDto selectPlaceByPlaceSeq(int tripPlaceSeq);

    String selectDateByDaySeq(int tripDaySeq);

    ArrayList<TripPlaceDto> selectTripByCoordinate(@Param("minLatitude") double minLatitude,
                                                   @Param("maxLatitude") double maxLatitude,
                                                   @Param("minLongitude") double minLongitude,
                                                   @Param("maxLongitude") double maxLongitude);

    ArrayList<TripPlaceDto> selectPlacesByTripPlaceSeqRange();

    ArrayList<JourneyVo> selectOtherJourneyListByUserSeq(@Param("userSeq") int userSeq, @Param("startDate") String startDate, @Param("endDate") String endDate);

    ArrayList<JourneyVo> selectMyJourneyListByUserSeq(@Param("userSeq") int userSeq, @Param("startDate") String startDate, @Param("endDate") String endDate);

    ArrayList<AlbumVo> selectOtherAlbumListByUserSeq(@Param("userSeq") int userSeq, @Param("page") int page);

    ArrayList<AlbumVo> selectMyAlbumListByUserSeq(@Param("userSeq") int userSeq, @Param("page") int page);

    ArrayList<RecommendTagVo> selectPlaceByTag(int placeTagSeq);

    LinkedList<RecommendTagVo> selectPlaceByRegion(int regionSeq);

    ArrayList<UserFavorDto> selectUserFavorByUserSeq(int userSeq);

    int updateUserFavor(UserFavorDto userFavorDto);
}
