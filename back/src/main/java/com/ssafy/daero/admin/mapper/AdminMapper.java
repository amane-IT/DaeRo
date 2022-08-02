package com.ssafy.daero.admin.mapper;


import com.ssafy.daero.admin.dto.ReportDto;
import com.ssafy.daero.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;


@Mapper
public interface AdminMapper {
    int selectUserCount();
    ArrayList<UserDto> selectUserList(int page);

    int selectReportCount();

    ArrayList<ReportDto> selectReportList(int page);

    int updateReportHandled(int reportSeq);
}
