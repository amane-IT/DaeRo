package com.ssafy.daero.service.mapper;

import com.ssafy.daero.service.dto.NoticeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ServiceMapper {

    ArrayList<NoticeDto> selectNoticeList();
}
