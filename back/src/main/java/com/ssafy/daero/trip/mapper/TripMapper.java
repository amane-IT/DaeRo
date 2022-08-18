package com.ssafy.daero.trip.mapper;

import com.ssafy.daero.trip.dto.*;
import com.ssafy.daero.trip.vo.*;
import com.ssafy.daero.user.dto.UserFavorDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.LinkedList;

@SuppressWarnings("UnusedReturnValue")
@Mapper
public interface TripMapper {

    int insertArticle(TripVo tripVo);

    int insertTrip(TripVo tripVo);

    int insertTripDay(TripDayVo tripDayVo);

    int insertTripStamp(TripStampVo tripStampVo);

    TripStampDto selectByStampSeq(int tripStampSeq);

    TripPlaceDto selectPlaceByPlaceSeq(int tripPlaceSeq);

    String selectDateByDaySeq(int tripDaySeq);

    ArrayList<TripPlaceDto> selectOtherTripByCoordinate(@Param("minLatitude") double minLatitude,
                                                        @Param("maxLatitude") double maxLatitude,
                                                        @Param("minLongitude") double minLongitude,
                                                        @Param("maxLongitude") double maxLongitude,
                                                        @Param("placeSeq") double placeSeq);

    ArrayList<TripPlaceDto> selectOtherTripByCoordinateLimit(@Param("minLatitude") double minLatitude,
                                                             @Param("maxLatitude") double maxLatitude,
                                                             @Param("minLongitude") double minLongitude,
                                                             @Param("maxLongitude") double maxLongitude,
                                                             @Param("placeSeq") double placeSeq);

    ArrayList<TripPlaceDto> selectPlacesByTripPlaceSeqRange();

    ArrayList<JourneyVo> selectOtherJourneyListByUserSeq(@Param("userSeq") int userSeq, @Param("startDate") String startDate, @Param("endDate") String endDate);

    ArrayList<JourneyVo> selectMyJourneyListByUserSeq(@Param("userSeq") int userSeq, @Param("startDate") String startDate, @Param("endDate") String endDate);

    ArrayList<AlbumVo> selectOtherAlbumListByUserSeq(@Param("userSeq") int userSeq, @Param("page") int page);

    ArrayList<AlbumVo> selectMyAlbumListByUserSeq(@Param("userSeq") int userSeq, @Param("page") int page);

    int selectAlbumCountByUserSeq(int userSeq);

    ArrayList<RecommendTagVo> selectPlaceByTag(int placeTagSeq);

    LinkedList<RecommendTagVo> selectPlaceByRegion(int regionSeq);

    ArrayList<UserFavorDto> selectUserFavorByUserSeq(int userSeq);

    int updateAchievementByPlace(@Param("userSeq") int userSeq, @Param("placeSeq") int placeSeq);

    int updateUserFavor(UserFavorDto userFavorDto);
}
