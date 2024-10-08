package com.ssafy.daero.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.daero.admin.mapper.AdminMapper;
import com.ssafy.daero.admin.vo.*;
import com.ssafy.daero.sns.mapper.SnsMapper;
import com.ssafy.daero.sns.vo.*;
import com.ssafy.daero.trip.dto.TripPlaceDto;
import com.ssafy.daero.trip.mapper.TripMapper;
import com.ssafy.daero.user.dto.UserDto;
import com.ssafy.daero.user.mapper.UserMapper;
import com.ssafy.daero.user.vo.UserVo;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("FieldCanBeLocal")
@Service
public class AdminService {
    private final int ARTICLE_PAGE_SIZE = 10;
    private final int REPORT_PAGE_SIZE = 10;
    private final int NOTICE_PAGE_SIZE = 10;
    private final AdminMapper adminMapper;
    private final SnsMapper snsMapper;
    private final TripMapper tripMapper;
    private final UserMapper userMapper;
    public AdminService(AdminMapper adminMapper, SnsMapper snsMapper, TripMapper tripMapper, UserMapper userMapper) {
        this.adminMapper=adminMapper;
        this.snsMapper=snsMapper;
        this.tripMapper=tripMapper;
        this.userMapper=userMapper;
    }

    public Integer login(String code) {
        return adminMapper.selectAdminByCode(code);
    }

    public Map<String, Object> userList(int page) {
        int totalPage = (int) Math.ceil(adminMapper.selectUserCount()/10.0);
        if (totalPage == 0) { totalPage = 1; }
        if (page > totalPage) { return null; }
        ArrayList<UserDto> users = adminMapper.selectUserList(page);

        Map<String, Object> result = new HashMap<>();
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
            user.put("id", uVo.getUserEmail());
            userList.add(user);
            user = new HashMap<>();
        }
        result.put("total_page", totalPage);
        result.put("page", page);
        result.put("results", userList);
        return result;
    }

    public Map<String, Object> userDetail(int userSeq) {
        // user 정보
        UserVo userVo = userMapper.selectByUserSeq(userSeq);
        // 작성한 게시글
        ArrayList<ArticleVo> articleVos = adminMapper.selectArticleListByUserSeq(userSeq);
        // 작성한 댓글
        ArrayList<ReplyVo> replyVos = adminMapper.selectReplyListByUserSeq(userSeq);
        Map<String, Object> userDetail = new HashMap<>();
        userDetail.put("nickname", userVo.getNickname());
        userDetail.put("profile_url", userVo.getProfileImageLink());
        userDetail.put("badge_count", userMapper.selectAllBadgeById(userSeq));
        ArrayList<Map<String, Object>> articles = new ArrayList<>();
        Map<String, Object> article = new HashMap<>();
        for(ArticleVo articleVo: articleVos) {
            article.put("title", articleVo.getTitle());
            article.put("createdAt", articleVo.getCreatedAt());
            articles.add(article);
            article = new HashMap<>();
        }
        ArrayList<Map<String, Object>> replies = new ArrayList<>();
        Map<String, Object> reply = new HashMap<>();
        for(ReplyVo replyVo: replyVos) {
            reply.put("article_seq", replyVo.getArticleSeq());
            reply.put("reply_seq", replyVo.getReplySeq());
            reply.put("content", replyVo.getContent());
            replies.add(reply);
            reply = new HashMap<>();
        }
        userDetail.put("articles", articles);
        userDetail.put("replies", replies);
        return userDetail;
    }

    public Map<String, Object> reportList(int page) {
        int totalCount = adminMapper.selectReportCount();
        int totalPage = (totalCount - 1) / REPORT_PAGE_SIZE + 1;
        if (totalPage == 0) { totalPage = 1; }
        if (page > totalPage) { return null; }
        ArrayList<ReportVo> reports = adminMapper.selectReportList(REPORT_PAGE_SIZE, (page - 1) * REPORT_PAGE_SIZE);

        Map<String, Object> result = new HashMap<>();
        ArrayList<Map<String, Object>> reportList = new ArrayList<>();
        Map<String, Object> report = new HashMap<>();

        for (ReportVo reportVo : reports) {
            report.put("report_seq", reportVo.getReportSeq());
            report.put("report_categories_seq", reportVo.getReportCategorySeq());
            report.put("content_type", reportVo.getArticleType());
            report.put("handled_yn", reportVo.getHandledYn());
            report.put("reporter_user_seq", reportVo.getReporterSeq());
            report.put("reported_user_seq", reportVo.getReportedSeq());
            report.put("reporter_user_nickname", reportVo.getReporterNickname());
            report.put("reported_user_nickname", reportVo.getReportedNickname());
            report.put("reported_at", reportVo.getReportedAt());
            report.put("content_seq", reportVo.getContentSeq());
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
        return updated == 1;
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

        ArrayList<Map<String, Object>> expenses = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        if (!articleVo.getTripExpenses().isEmpty()) {
            expenses = mapper.readValue(articleVo.getTripExpenses(), ArrayList.class);
        }
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
        articleDetail.put("created_at", articleVo.getCreatedAt());
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
        ArrayList<ReplyVo> rereply;

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

    public Map<String, Object> replyDetail(int replySeq) {
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

    private LinkedList<Map<String, Object>> createArticleMapList(ArrayList<ArticleListVo> articleListVos) {
        LinkedList<Map<String, Object>> results = new LinkedList<>();
        for (ArticleListVo articleListVo : articleListVos) {
            Map<String, Object> article = new HashMap<>();
            article.put("article_seq", articleListVo.getArticleSeq());
            article.put("nickname", articleListVo.getNickname());
            article.put("user_seq", articleListVo.getUserSeq());
            article.put("created_at", articleListVo.getCreatedAt());
            article.put("title", articleListVo.getTitle());
            article.put("open_yn", articleListVo.getOpenYn());
            results.add(article);
        }
        return results;
    }

    public Map<String, Object> articleList(int page) {
        int totalPage = (adminMapper.selectArticleCount() - 1) / ARTICLE_PAGE_SIZE;
        if (totalPage == 0) { totalPage = 1; }
        if (page > totalPage) { return null; }
        LinkedList<Map<String, Object>> results = createArticleMapList(adminMapper.selectArticleList(REPORT_PAGE_SIZE, (page - 1) * REPORT_PAGE_SIZE));
        Map<String, Object> articleList = new HashMap<>();
        articleList.put("total_page", totalPage);
        articleList.put("page", page);
        articleList.put("results", results);
        return articleList;
    }

    public Map<String, Object> articleSearch(String content, int page) {
        int totalCount = this.adminMapper.selectArticleCountByContent(content);
        int totalPage = (totalCount - 1) / ARTICLE_PAGE_SIZE + 1;
        if (page > totalPage) return null;
        LinkedList<Map<String, Object>> results = createArticleMapList(this.adminMapper.selectArticleByContent(content, ARTICLE_PAGE_SIZE, (page - 1) * ARTICLE_PAGE_SIZE));
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total_page", totalPage);
        resultMap.put("page", page);
        resultMap.put("results", results);
        return resultMap;
    }

    public Map<String, Object> searchUser(String search, int page) {
        int totalPage = (int) Math.ceil(adminMapper.selectUserCount()/10.0);
        if (totalPage == 0) { totalPage = 1; }
        if (page > totalPage) { return null; }

        ArrayList<UserDto> userDtos = adminMapper.selectUserListBySearch(search, page);
        Map<String, Object> searchUserList = new HashMap<>();
        ArrayList<Map<String, Object>> results = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        for(UserDto uDto:userDtos) {
            result.put("profile_url", uDto.getProfileImageLink());
            result.put("nickname", uDto.getNickname());
            result.put("user_seq", uDto.getUserSeq());
            result.put("report_count", uDto.getReportedCount());
            result.put("reg_date", uDto.getCreatedAt());
            result.put("suspended_yn", uDto.getSuspendedYn());
            results.add(result);
            result = new HashMap<>();
        }
        searchUserList.put("total_page", totalPage);
        searchUserList.put("page", page);
        searchUserList.put("results", results);
        return searchUserList;
    }

    public int deleteArticle(int articleSeq) {
        int article = snsMapper.selectArticleByArticleSeq(articleSeq);
        if (article == 0) { return 0; }
        int deletedArticle = snsMapper.deleteArticleByArticleSeq(articleSeq);
        if (deletedArticle == 0) { return 0; }
        return 1;
    }

    public int deleteReply(int replySeq) {
        int reply = snsMapper.selectReplyByReplySeq(replySeq);
        if (reply == 0) { return 0; }
        int deletedReply = snsMapper.deleteReplyByReplySeq(replySeq);
        if (deletedReply == 0) { return 0; }
        return 1;
    }

    public Map<String, Object> placeList(int page) {
        int totalPage = (int) Math.ceil(adminMapper.selectPlaceCount()/10.0);
        if (totalPage == 0) { totalPage = 1; }
        if (page > totalPage) { return null; }

        ArrayList<TripPlaceDto> placeDtos = adminMapper.selectPlaceList(page);
        if (placeDtos == null) { return null; }
        Map<String, Object> result = new HashMap<>();
        ArrayList<Map<String, Object>> results = new ArrayList<>();
        Map<String, Object> placeList = new HashMap<>();
        for (TripPlaceDto pDto: placeDtos) {
            result.put("trip_place_seq", pDto.getTripPlaceSeq());
            result.put("place_name", pDto.getPlaceName());
            result.put("address", pDto.getAddress());
            results.add(result);
            result = new HashMap<>();
        }
        placeList.put("total_page", totalPage);
        placeList.put("page", page);
        placeList.put("results", results);
        return placeList;
    }

    public Map<String, Object> placeDetail(int placeSeq) {
        TripPlaceDto placeDto = tripMapper.selectPlaceByPlaceSeq(placeSeq);
        if (placeDto == null) { return null; }
        ArrayList<Integer> tags = adminMapper.selectTagsByPlaceSeq(placeSeq);
        if (tags == null) { return null; }
        Map<String, Object> placeDetail = new HashMap<>();
        placeDetail.put("place_name", placeDto.getPlaceName());
        placeDetail.put("address", placeDto.getAddress());
        placeDetail.put("latitude", placeDto.getLatitude());
        placeDetail.put("longitude", placeDto.getLongitude());
        placeDetail.put("tags", tags);
        placeDetail.put("image_url", placeDto.getImageUrl());
        placeDetail.put("description", placeDto.getDescription());
        return placeDetail;
    }

    public boolean createPlace(TripPlaceVo tripPlaceVo) {
        int insertPlace = adminMapper.insertPlace(tripPlaceVo);
        if (insertPlace == 0) { return false; }
        Integer placeSeq = adminMapper.selectPlaceSeqByPlaceAddress(tripPlaceVo.getAddress());
        for(int tag: tripPlaceVo.getTags()) {
            int inserted = adminMapper.insertPlaceTag(placeSeq, tag);
            if (inserted == 0) { return false; }
        }
        return true;
    }

    public boolean updatePlace(int placeSeq, TripPlaceVo tripPlaceVo) {
        TripPlaceDto placeDto = tripMapper.selectPlaceByPlaceSeq(placeSeq);
        if (placeDto == null) { return false; }
        tripPlaceVo.setTripPlaceSeq(placeSeq);
        // trip_places table update
        int updated = adminMapper.updatePlace(tripPlaceVo);
        if (updated == 0) { return false; }
        // tag_trip_places table update (delete && insert)
        int deleted = adminMapper.deletePlaceTag(placeSeq);
        if (deleted == 0) { return false; }
        for(int tag: tripPlaceVo.getTags()) {
            int inserted = adminMapper.insertPlaceTag(placeSeq, tag);
            if (inserted == 0) { return false; }
        }
        return true;

    }

    public boolean deletePlace(int placeSeq) {
        TripPlaceDto tripPlaceDto = tripMapper.selectPlaceByPlaceSeq(placeSeq);
        if (tripPlaceDto == null) { return false; }
        return adminMapper.deletePlace(placeSeq) == 1;
    }

    public Map<String, Object> inquiryList(int page) {
        int totalPage = (int) Math.ceil(adminMapper.selectInquiryCount()/10.0);
        if (totalPage == 0) { totalPage = 1; }
        if (page > totalPage) { return null; }
        ArrayList<Map<String, Object>> inquires = adminMapper.selectInquiryList(page);

        Map<String, Object> inquiryList = new HashMap<>();
        inquiryList.put("total_page", totalPage);
        inquiryList.put("page", page);
        inquiryList.put("results", inquires);
        return inquiryList;
    }

    public Map<String, Object> inquiryDetail(int inquirySeq) {
        Map<String, Object> inquiryDetail = adminMapper.selectInquiryDetail(inquirySeq);
        if (inquiryDetail.isEmpty()) { return null; }
        return inquiryDetail;
    }

    public boolean answerInquiry(int inquirySeq, AnswerVo answerVo) {
        answerVo.setInquirySeq(inquirySeq);
        int updateInquiry = adminMapper.updateInquiryByInquirySeq(answerVo);
        return updateInquiry == 1;
    }

    public Map<String, Object> faqList(int page) {
        int totalPage = (int) Math.ceil(adminMapper.selectFaqCount()/10.0);
        if (totalPage == 0) { totalPage = 1; }
        if (page > totalPage) { return null; }

        ArrayList<Map<String, Object>> faqs = adminMapper.selectFaqList(page);
        if (faqs == null) { return null; }
        Map<String, Object> faqList = new HashMap<>();
        faqList.put("total_page", totalPage);
        faqList.put("page", page);
        faqList.put("results", faqs);
        return faqList;
    }

    public boolean createFaq(FaqVo faqVo) {
        int created = adminMapper.insertFaq(faqVo);
        return created == 1;
    }

    public boolean updateFaq(int faqSeq, FaqVo faqVo) {
        faqVo.setFaqSeq(faqSeq);
        int updated = adminMapper.updateFaq(faqVo);
        return updated == 1;
    }

    public boolean deleteFaq(int faqSeq) {
        int deleted = adminMapper.deleteFaq(faqSeq);
        return deleted == 1;
    }

    public Map<String, Object> noticeList(int page) {
        int totalPage = (adminMapper.selectNoticeCount() - 1) / NOTICE_PAGE_SIZE + 1;
        if (totalPage == 0) { totalPage = 1; }
        if (page > totalPage) { return null; }
        ArrayList<Map<String, Object>> results = adminMapper.selectNoticeList(NOTICE_PAGE_SIZE, (page - 1) * NOTICE_PAGE_SIZE);

        Map<String, Object> noticeList = new HashMap<>();
        noticeList.put("total_page", totalPage);
        noticeList.put("page", page);
        noticeList.put("results", results);
        return noticeList;
    }

    public boolean createNotice(NoticeVo noticeVo) {
        int created = adminMapper.insertNotice(noticeVo);
        return created == 1;
    }

    public boolean updateNotice(int noticeSeq, NoticeVo noticeVo) {
        int updated = adminMapper.updateNotice(noticeSeq, noticeVo.getTitle(), noticeVo.getContent(), noticeVo.getAdminSeq());
        return updated == 1;
    }

    public boolean deleteNotice(int noticeSeq) {
        int deleted = adminMapper.deleteNotice(noticeSeq);
        return deleted == 1;
    }

    public boolean suspendUser(int userSeq, int day) {
        UserDto userDto = userMapper.selectUserByUserSeq(userSeq);
        if (userDto == null) { return false; }
        Calendar recent = Calendar.getInstance();
        recent.add(Calendar.DATE, day);
        String recentString = new SimpleDateFormat("yyyy-MM-dd").format(recent.getTime());
        int updated = adminMapper.updateUserSuspension(userSeq, recentString);
        return updated == 1;
    }

}
