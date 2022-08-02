package com.ssafy.daero.admin.service;

import com.ssafy.daero.admin.dto.ReportDto;
import com.ssafy.daero.admin.mapper.AdminMapper;
import com.ssafy.daero.user.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
public class AdminService {
    private AdminMapper adminMapper;
    public AdminService(AdminMapper adminMapper) { this.adminMapper=adminMapper; }

    public Map<String, Object> userList(int page) {
        int totalPage = (int) Math.ceil(adminMapper.selectUserCount()/10.0);
        if (totalPage == 0) { totalPage = 1; }
        if (page > totalPage) { return null; }
        ArrayList<UserDto> users = adminMapper.selectUserList(page);

        Map<String, Object> result = new HashMap();
        ArrayList<Map<String, Object>> userList = new ArrayList<>();
        Map<String, Object> user = new HashMap<>();
        for (UserDto uVo :
                users) {
            user.put("profile_url", uVo.getProfileImageLink());
            user.put("nickname", uVo.getNickname());
            user.put("user_seq", uVo.getUserSeq());
            user.put("report_count", uVo.getReportedCount());
            user.put("reg_date", uVo.getCreatedAt());
            user.put("suspended_yn", uVo.getSuspendedYn());
            userList.add(user);
            user = new HashMap<>();
        }
        result.put("total_page", totalPage);
        result.put("page", page);
        result.put("results", userList);
        return result;
    }

    public Map<String, Object> reportList(int page) {
        int totalPage = (int) Math.ceil(adminMapper.selectReportCount()/10.0);
        if (totalPage == 0) { totalPage = 1; }
        if (page > totalPage) { return null; }
        ArrayList<ReportDto> reports = adminMapper.selectReportList(page);

        Map<String, Object> result = new HashMap<>();
        ArrayList<Map<String, Object>> reportList = new ArrayList<>();
        Map<String, Object> report = new HashMap<>();

        for (ReportDto rDto :
                reports) {
            report.put("report_seq", rDto.getReportSeq());
            report.put("report_categories_seq", rDto.getReportCategorySeq());
            report.put("content_type", rDto.getArticleType());
            report.put("handled_yn", rDto.getHandledYn());
            report.put("reporter_user_seq", rDto.getReporterSeq());
            report.put("reported_user_seq", rDto.getReportedSeq());
            report.put("reported_at", rDto.getReportedAt());
            report.put("report_url", rDto.getReportUrl());
            reportList.add(report);
            report = new HashMap<>();
        }
        result.put("total_page", totalPage);
        result.put("page", page);
        result.put("results", reportList);
        return result;
    }

    public boolean handleReport(int reportSeq) {
        int updated = adminMapper.updateReportHandled(reportSeq);
        if (updated == 1) { return true; }
        else { return false; }
    }
}
