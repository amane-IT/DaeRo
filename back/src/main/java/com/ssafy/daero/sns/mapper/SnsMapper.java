package com.ssafy.daero.sns.mapper;

import com.ssafy.daero.sns.dto.ReplyDto;
import com.ssafy.daero.sns.vo.ArticleVo;
import com.ssafy.daero.sns.vo.ReplyVo;
import com.ssafy.daero.sns.vo.StampVo;
import com.ssafy.daero.sns.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface SnsMapper {

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
}
