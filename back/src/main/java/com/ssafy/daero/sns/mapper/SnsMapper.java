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

    Integer deleteArticleByArticleSeq(int articleSeq);

    Integer deleteReplyByArticleSeq(int articleSeq);

    Integer deleteArticleTagByArticleSeq(int articleSeq);


    ArrayList<ReplyVo> selectReplyListByArticleSeq(@Param("articleSeq") int articleSeq, @Param("page") int page);

    Integer selectUserSeqByReplySeq(int replySeq);

    Integer insertReply(@Param("articleSeq") int articleSeq, @Param("userSeq") int userSeq, @Param("content") String content);

    int selectArticleByArticleSeq(int articleSeq);

    Integer updateReplyByReplySeq(@Param("replySeq") int replySeq, @Param("content") String content);

    Integer deleteReplyByReplySeq(int replySeq);

    ArrayList<ReplyVo> selectRereplyListByReplySeq(@Param("replySeq") int replySeq, @Param("page") int page);

    int selectReplyByReplySeq(int replySeq);

    Integer insertRereply(@Param("articleSeq") int articleSeq, @Param("replySeq") int replySeq, @Param("userSeq") int userSeq, @Param("content") String content);

    int selectArticleLikeByUserSeq(@Param("articleSeq") int articleSeq, @Param("userSeq") int userSeq);

    Integer insertLike(@Param("articleSeq") int articleSeq, @Param("userSeq") int userSeq);

    Integer deleteLike(@Param("articleSeq") int articleSeq, @Param("userSeq") int userSeq);

    ArrayList<UserVo> selectLikeUserListByArticleSeq(@Param("articleSeq") int articleSeq, @Param("page") int page);

    Integer insertReport(@Param("articleSeq") int articleSeq, @Param("reporterUserSeq") int reporterUserSeq, @Param("reportedUserSeq") int reportedUserSeq, @Param("reportSeq") int reportSeq, @Param("type") String type);

    int selectReportArticleByUserSeq(@Param("articleSeq") int articleSeq, @Param("userSeq") int userSeq);

    int selectReportReplyByUserSeq(@Param("replySeq") int replySeq, @Param("userSeq") int userSeq);
}
