package com.ssafy.daero.service.mapper;

import com.ssafy.daero.service.dto.FaqDto;
import com.ssafy.daero.service.dto.InquiryDto;
import com.ssafy.daero.service.dto.NoticeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface ServiceMapper {

    ArrayList<NoticeDto> selectNoticeList();

    ArrayList<FaqDto> selectFaqList();

    int selectInquiryCountByUserSeq(int userSeq);

    ArrayList<InquiryDto> selectInquiryList(@Param("userSeq") int userSeq, @Param("page") int page);

    int insertInquiry(@Param("userSeq") int userSeq, @Param("title") String title, @Param("content") String content);
}
