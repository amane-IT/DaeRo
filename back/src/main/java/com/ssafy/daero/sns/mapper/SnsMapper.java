package com.ssafy.daero.sns.mapper;

import com.ssafy.daero.sns.dto.ReplyDto;
import com.ssafy.daero.sns.vo.ArticleVo;
import com.ssafy.daero.sns.vo.ReplyVo;
import com.ssafy.daero.sns.vo.StampVo;
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


    ArrayList<ReplyVo> selectReplyListByArticleSeq(@Param("articleSeq") int articleSeq, @Param("page") int page);

    Integer selectUserSeqByReplySeq(int replySeq);

    Integer insertReply(@Param("articleSeq") int articleSeq, @Param("userSeq") int userSeq, @Param("content") String content);

    int selectArticleByArticleSeq(int articleSeq);

    Integer updateReplyByReplySeq(@Param("replySeq") int replySeq, @Param("content") String content);

    Integer deleteReplyByReplySeq(int replySeq);




}
