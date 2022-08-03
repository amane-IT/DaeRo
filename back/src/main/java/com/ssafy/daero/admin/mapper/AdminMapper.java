package com.ssafy.daero.admin.mapper;


import com.ssafy.daero.admin.dto.ReportDto;
import com.ssafy.daero.sns.vo.ArticleVo;
import com.ssafy.daero.sns.vo.ReplyVo;
import com.ssafy.daero.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;


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
}
