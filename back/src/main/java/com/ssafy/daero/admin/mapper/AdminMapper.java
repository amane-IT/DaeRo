package com.ssafy.daero.admin.mapper;


import com.ssafy.daero.admin.dto.ReportDto;
import com.ssafy.daero.admin.vo.AnswerVo;
import com.ssafy.daero.admin.vo.TripPlaceVo;
import com.ssafy.daero.sns.vo.ArticleVo;
import com.ssafy.daero.sns.vo.ReplyVo;
import com.ssafy.daero.trip.dto.TripPlaceDto;
import com.ssafy.daero.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Map;


@Mapper
public interface AdminMapper {
    int selectUserCount();
    ArrayList<UserDto> selectUserList(int page);

    int selectReportCount();

    ArrayList<ReportDto> selectReportList(int page);

    int updateReportHandled(int reportSeq);

    int selectReplyCount(int articleSeq);

    ArrayList<ReplyVo> selectReplyListByArticleSeq(@Param("articleSeq") int articleSeq, @Param("page") int page);

    ArrayList<ReplyVo> selectRereplyListByByReplySeq(@Param("replySeq") int replySeq);

    int selectArticleByArticleSeq(int articleSeq);

    ReplyVo selectReplyByReplySeq(int replySeq);

    int selectArticleCount();

    ArrayList<ArticleVo> selectArticleList(int page);

    ArrayList<UserDto> selectUserListBySearch(@Param("search") String search, @Param("page") int page);

    ArrayList<TripPlaceDto> selectPlaceList(int page);

    int selectPlaceCount();

    ArrayList<Integer> selectTagsByPlaceSeq(int placeSeq);

    int insertPlace(TripPlaceVo tripPlaceVo);

    int insertPlaceTag(int placeSeq, int tag);

    Integer selectPlaceSeqByPlaceAddress(String address);

    int deletePlace(int placeSeq);

    int selectInquiryCount();
    ArrayList<Map<String, Object>> selectInquiryList(int page);

    Map<String, Object> selectInquiryDetail(int inquirySeq);

    int updateInquiryByInquirySeq(@Param("inquirySeq") int inquirySeq, @Param("content") String content, @Param("author") String author);

    int selectFaqCount();
    ArrayList<Map<String, Object>> selectFaqList(int page);
}
