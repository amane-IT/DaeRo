package com.ssafy.daero.sns.mapper;

import com.ssafy.daero.sns.vo.*;
import com.ssafy.daero.trip.dto.TripPlaceDto;
import com.ssafy.daero.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface SnsMapper {
    int insertBlock(@Param("userSeq") int userSeq, @Param("blocker") int blocker);

    ArticleVo selectArticleAndTripInfoByArticleSeq(int articleSeq);

    ArrayList<StampVo> selectStampAndDayInfoByTripSeq(int tripSeq);

    Map<String, String> selectUserByUserSeq(int userSeq);

    ArrayList<Integer> selectPlaceTagsByArticleSeq(int articleSeq);

    Integer selectUserSeqByArticleSeq(int articleSeq);

    int deleteArticleByArticleSeq(int articleSeq);

    ArrayList<ReplyVo> selectReplyListByArticleSeq(@Param("articleSeq") int articleSeq, @Param("page") int page);

    Integer selectUserSeqByReplySeq(int replySeq);

    int insertReply(@Param("articleSeq") int articleSeq, @Param("userSeq") int userSeq, @Param("content") String content);

    int selectArticleByArticleSeq(int articleSeq);

    int updateReplyByReplySeq(@Param("replySeq") int replySeq, @Param("content") String content);

    int deleteReplyByReplySeq(int replySeq);

    ArrayList<ReplyVo> selectRereplyListByReplySeq(@Param("replySeq") int replySeq, @Param("page") int page);

    int selectReplyByReplySeq(int replySeq);

    int insertRereply(@Param("articleSeq") int articleSeq, @Param("replySeq") int replySeq, @Param("userSeq") int userSeq, @Param("content") String content);

    int selectArticleLikeByUserSeq(@Param("articleSeq") int articleSeq, @Param("userSeq") int userSeq);

    int insertLike(@Param("articleSeq") int articleSeq, @Param("userSeq") int userSeq);

    int deleteLike(@Param("articleSeq") int articleSeq, @Param("userSeq") int userSeq);

    ArrayList<UserVo> selectLikeUserListByArticleSeq(@Param("articleSeq") int articleSeq, @Param("page") int page);

    int insertReport(@Param("articleSeq") int articleSeq, @Param("reporterUserSeq") int reporterUserSeq, @Param("reportedUserSeq") int reportedUserSeq, @Param("reportSeq") int reportSeq, @Param("type") String type);

    int selectReportArticleByUserSeq(@Param("articleSeq") int articleSeq, @Param("userSeq") int userSeq);

    int selectReportReplyByUserSeq(@Param("replySeq") int replySeq, @Param("userSeq") int userSeq);

    int selectFollowByUserSeq(@Param("followerUserSeq") int followerUserSeq, @Param("followedUserSeq") int followedUserSeq);
    int insertFollow(@Param("followerUserSeq") int followerUserSeq, @Param("followedUserSeq") int followedUserSeq);

    int deleteFollow(@Param("followerUserSeq") int followerUserSeq, @Param("followedUserSeq") int followedUserSeq);

    int selectFollowerByUserSeq(int userSeq);

    int selectFollowingByUserSeq(int userSeq);

    ArrayList<UserVo> selectFollowerListByUserSeq(@Param("userSeq") int userSeq, @Param("page") int page);

    ArrayList<UserVo> selectFollowingListByUserSeq(@Param("userSeq") int userSeq, @Param("page") int page);

    int selectLikeCountByArticleSeq(int articleSeq);

    int selectReplyByArticleSeq(int articleSeq);

    int selectRereplyByReplySeq(int replySeq);

    int selectArticleCount(int userSeq);

    int selectArticleCountByFollowCreatedAt(@Param("userSeq") int userSeq,
                                            @Param("recent") String recent);

    ArrayList<ArticleListVo> selectArticleByFollowCreatedAt(@Param("userSeq") int userSeq,
                                                            @Param("limit") int limit,
                                                            @Param("offset") int offSet,
                                                            @Param("recent") String recent);

    ArrayList<ArticleListVo> selectArticleByNotFollow(@Param("userSeq") int userSeq,
                                                      @Param("limit") int limit,
                                                      @Param("offset") int offSet,
                                                      @Param("recent") String recent);

    int selectCollectionCountByUserSeq(int userSeq);
    ArrayList<Map<String, Object>> selectCollectionByUserSeq(@Param("userSeq") int userSeq, @Param("limit") int limit, @Param("offset") int offset);

    int selectUserCountByNickname(String nickname);

    ArrayList<UserDto> selectUserByNickname(@Param("nickname") String nickname,
                                            @Param("limit") int limit,
                                            @Param("offset") int offset,
                                            @Param("userSeq") int userSeq);

    int selectArticleCountByContent(String content);

    ArrayList<ArticleListVo> selectArticleByContent(@Param("content") String content,
                                                    @Param("limit") int limit,
                                                    @Param("offset") int offset,
                                                    @Param("userSeq") int userSeq);

    int selectArticleCountByPlace(String placeName);

    ArrayList<ArticleListVo> selectArticleByPlace(@Param("placeName") String placeName,
                                                  @Param("limit") int limit,
                                                  @Param("offset") int offset,
                                                  @Param("userSeq") int userSeq);

    ArrayList<TripPlaceDto> selectTripPlacesByArticle(int articleSeq);

    ArrayList<UserDto> selectBlockedUserByBlocker(int userSeq);

    int selectArticleCountByArticleSeqUserSeq(@Param("articleSeq") int articleSeq, @Param("userSeq") int userSeq);

    ArrayList<ArticleListVo> selectArticleByLike();

    int deleteBlock(@Param("userSeq") int userSeq, @Param("blocker") int blocker);

    int updateArticle(@Param("articleSeq") int articleSeq,
                      @Param("title") String title,
                      @Param("tripComment") String tripComment,
                      @Param("tripExpenses") String tripExpenses,
                      @Param("rating") int rating,
                      @Param("expose") String expose);

    int selectTripSeqByArticleSeq(int articleSeq);
    int updateDayComment(@Param("tripSeq") int tripSeq,
                         @Param("dayComment") String dayComment,
                         @Param("rowNum") int rowNum);

    int updateArticleOpen(@Param("articleSeq") int articleSeq, @Param("openYn") char openYn);

}
