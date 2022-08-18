package com.ssafy.daero.admin.mapper;


import com.ssafy.daero.admin.vo.*;
import com.ssafy.daero.sns.vo.ArticleListVo;
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
    Integer selectAdminByCode(String code);
    int selectUserCount();
    ArrayList<UserDto> selectUserList(int page);

    int selectReportCount();

    ArrayList<ReportVo> selectReportList(@Param("limit") int limit, @Param("offset") int offset);

    int updateReportHandled(int reportSeq);

    int selectReplyCount(int articleSeq);

    ArrayList<ReplyVo> selectReplyListByArticleSeq(@Param("articleSeq") int articleSeq, @Param("page") int page);

    ArrayList<ReplyVo> selectRereplyListByByReplySeq(@Param("replySeq") int replySeq);

    ReplyVo selectReplyByReplySeq(int replySeq);

    int selectArticleCount();

    ArrayList<ArticleListVo> selectArticleList(@Param("limit") int limit, @Param("offset") int offset);

    int selectArticleCountByContent(String content);

    ArrayList<ArticleListVo> selectArticleByContent(@Param("content") String content,
                                                    @Param("limit") int limit,
                                                    @Param("offset") int offset);

    ArrayList<UserDto> selectUserListBySearch(@Param("search") String search, @Param("page") int page);

    ArrayList<TripPlaceDto> selectPlaceList(int page);

    int selectPlaceCount();

    ArrayList<Integer> selectTagsByPlaceSeq(int placeSeq);

    int insertPlace(TripPlaceVo tripPlaceVo);

    int insertPlaceTag(@Param("placeSeq") int placeSeq,@Param("tag") int tag);

    Integer selectPlaceSeqByPlaceAddress(String address);

    int updatePlace(TripPlaceVo tripPlaceVo);

    int deletePlaceTag(int placeSeq);

    int deletePlace(int placeSeq);

    int selectInquiryCount();
    ArrayList<Map<String, Object>> selectInquiryList(int page);

    Map<String, Object> selectInquiryDetail(int inquirySeq);

    int updateInquiryByInquirySeq(AnswerVo answerVo);

    int selectFaqCount();
    ArrayList<Map<String, Object>> selectFaqList(int page);

    int insertFaq(FaqVo faqVo);

    int updateFaq(FaqVo faqVo);

    int deleteFaq(int faqSeq);

    int selectNoticeCount();

    ArrayList<Map<String, Object>> selectNoticeList(int limit, int offset);

    int insertNotice(NoticeVo noticeVo);

    int updateNotice(@Param("noticeSeq") int noticeSeq, @Param("title") String title, @Param("content") String content, @Param("adminSeq") int adminSeq);

    int deleteNotice(int noticeSeq);

    int updateUserSuspension(@Param("userSeq") int userSeq, @Param("date") String date);

    ArrayList<ArticleVo> selectArticleListByUserSeq(int userSeq);

    ArrayList<ReplyVo> selectReplyListByUserSeq(int userSeq);

}
