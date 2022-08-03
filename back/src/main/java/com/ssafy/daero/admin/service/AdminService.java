package com.ssafy.daero.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.daero.admin.dto.ReportDto;
import com.ssafy.daero.admin.mapper.AdminMapper;
import com.ssafy.daero.sns.mapper.SnsMapper;
import com.ssafy.daero.sns.vo.ArticleVo;
import com.ssafy.daero.sns.vo.ReplyVo;
import com.ssafy.daero.sns.vo.StampVo;
import com.ssafy.daero.user.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
public class AdminService {
    private AdminMapper adminMapper;
    private SnsMapper snsMapper;
    public AdminService(AdminMapper adminMapper, SnsMapper snsMapper) {
        this.adminMapper=adminMapper;
        this.snsMapper=snsMapper;
    }

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

    public Map<String, Object> articleDetail(int articleSeq) throws JsonProcessingException {
        ArticleVo articleVo = snsMapper.selectArticleAndTripInfoByArticleSeq(articleSeq);
        Map<String, Object> articleDetail = new HashMap<>();
        if(articleVo == null) { return null; }
        if(articleVo.getOpenYn() == 'n') { return null; }

        ArrayList<StampVo> stampVo = snsMapper.selectStampAndDayInfoByTripSeq(articleVo.getTripSeq());
        Map<String, String> userInfo = snsMapper.selectUserByUserSeq(articleVo.getUserSeq());
        ArrayList<Integer> tags = snsMapper.selectPlaceTagsByArticleSeq(articleSeq);


        ArrayList<Map<String, Object>> records = new ArrayList<>();
        Map<String, Object> days = new HashMap<>();
        ArrayList<Map<String, Object>> stamps = new ArrayList<>();
        Map<String, Object> stamp = new HashMap<>();

        int currentDaySeq = stampVo.get(0).getTripDaySeq();
        String datetime = stampVo.get(0).getDatetime();
        String dayComment = stampVo.get(0).getDayComment();
        for (StampVo sVo :
                stampVo) {
            if (sVo.getTripDaySeq() != currentDaySeq) {
                days.put("datetime", datetime);
                days.put("day_comment", dayComment);
                days.put("trip_stamps", stamps);
                records.add(days);

                currentDaySeq = sVo.getTripDaySeq();
                datetime = sVo.getDatetime();
                dayComment = sVo.getDayComment();
                stamps = new ArrayList<>();
                days = new HashMap<>();
            }
            stamp.put("image_url", sVo.getImageUrl());
            stamp.put("trip_stamp_seq", sVo.getTripStampSeq());
            stamp.put("latitude", sVo.getLatitude());
            stamp.put("longitude", sVo.getLongitude());
            stamps.add(stamp);
            stamp = new HashMap<>();

        }
        days.put("datetime", datetime);
        days.put("day_comment", dayComment);
        days.put("trip_stamps", stamps);
        records.add(days);

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Map<String, Object>> expenses = mapper.readValue(articleVo.getTripExpenses(), ArrayList.class);

        articleDetail.put("user_seq", articleVo.getUserSeq());
        articleDetail.put("nickname", userInfo.get("nickname"));
        articleDetail.put("profile_url", userInfo.get("profile_image_link"));
        articleDetail.put("title", articleVo.getTitle());
        articleDetail.put("trip_comment", articleVo.getTripComment());
        articleDetail.put("rating", articleVo.getRating());
        articleDetail.put("likes", articleVo.getLikes());
        articleDetail.put("comments", articleVo.getComments());
        articleDetail.put("tags", tags);
        articleDetail.put("trip_expenses", expenses);
        articleDetail.put("records", records);
        return articleDetail;
    }

    public Map<String, Object> replyList(int articleSeq, int page) {
        int totalPage = (int) Math.ceil(adminMapper.selectReplyCount(articleSeq)/10.0);
        if (totalPage == 0) { totalPage = 1; }
        if (page > totalPage) { return null; }

        ArrayList<ReplyVo> replyVos = adminMapper.selectReplyListByArticleSeq(articleSeq, page);
        Map<String, Object> replyList = new HashMap<>(); // 최종 결과
        ArrayList<Map<String, Object>> results = new ArrayList<>();
        ArrayList<Map<String, Object>> rereplies = new ArrayList<>();
        Map<String, Object> reply = new HashMap<>();
        ArrayList<ReplyVo> rereply = new ArrayList<>();

        for (ReplyVo rVo:replyVos) {
            rereply = adminMapper.selectRereplyListByByReplySeq(rVo.getReplySeq());
            for (ReplyVo rrVo : rereply) {
                reply.put("reply_seq", rrVo.getReplySeq());
                reply.put("nickname", rrVo.getNickname());
                reply.put("user_seq", rrVo.getUserSeq());
                reply.put("content", rrVo.getContent());
                reply.put("created_at", rrVo.getCreatedAt());
                rereplies.add(reply);
                reply = new HashMap<>();
            }
            reply.put("reply_seq", rVo.getReplySeq());
            reply.put("nickname", rVo.getNickname());
            reply.put("user_seq", rVo.getUserSeq());
            reply.put("content", rVo.getContent());
            reply.put("created_at", rVo.getCreatedAt());
            reply.put("rereply", rereplies);
            results.add(reply);
            reply = new HashMap<>();
            rereplies = new ArrayList<>();
        }
        replyList.put("total_page", totalPage);
        replyList.put("page", page);
        replyList.put("results", results);
        return replyList;
    }

    public Map<String, Object> replyDetail(int articleSeq, int replySeq) {
        int article = adminMapper.selectArticleByArticleSeq(articleSeq);
        if (article == 0) { return null; }
        ReplyVo replyVo = adminMapper.selectReplyByReplySeq(replySeq);
        if (replyVo == null) { return null; }
        Map<String, Object> replyDetail = new HashMap<>();
        replyDetail.put("reply_seq", replyVo.getReplySeq());
        replyDetail.put("nickname", replyVo.getNickname());
        replyDetail.put("user_seq", replyVo.getUserSeq());
        replyDetail.put("content", replyVo.getContent());
        replyDetail.put("created_at", replyVo.getCreatedAt());
        return replyDetail;

    }


}
