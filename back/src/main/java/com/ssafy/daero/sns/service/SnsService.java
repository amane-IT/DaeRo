package com.ssafy.daero.sns.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.daero.sns.mapper.SnsMapper;
import com.ssafy.daero.sns.vo.*;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("FieldCanBeLocal")
@Service
public class SnsService {
    private final SnsMapper snsMapper;
    private final int PAGE_SIZE = 10;
    private final int RECENT_DAY = -3;

    public SnsService(SnsMapper snsMapper) {
        this.snsMapper = snsMapper;
    }

    public Map<String, Object> articleDetail(int articleSeq, int userSeq) throws JsonProcessingException {
        ArticleVo articleVo = snsMapper.selectArticleAndTripInfoByArticleSeq(articleSeq);
        Map<String, Object> articleDetail = new HashMap<>();
        if(articleVo == null) { return articleDetail; }
        int liked = snsMapper.selectArticleLikeByUserSeq(articleSeq, userSeq);
        char like;
        if (liked == 0) { like = 'n'; }
        else { like = 'y'; }
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
        articleDetail.put("like_yn", like);
        return articleDetail;
    }

    public int deleteArticle(int articleSeq, int userSeq) {
        // 본인 게시글인지 확인
        Integer articleUser = snsMapper.selectUserSeqByArticleSeq(articleSeq);
        if (articleUser == null) { return 0; }
        if (articleUser == userSeq) {
            int deletedArticle = snsMapper.deleteArticleByArticleSeq(articleSeq);
            if (deletedArticle == 0) {
                return 1;
            }
        }
        return 2;
    }

    public Map<String, Object> replyList(int articleSeq, String page) {
        Integer articleUser = snsMapper.selectUserSeqByArticleSeq(articleSeq);
        if (articleUser == null) { return null; }
        int totalPage = (int) Math.ceil((snsMapper.selectReplyByArticleSeq(articleSeq))/10.0);
        if (totalPage  == 0) { totalPage = 1; }
        if (Integer.parseInt(page) > totalPage) { return null; }
        ArrayList<Map<String, Object>> replyList = new ArrayList<>();
        Map<String, Object> reply = new HashMap<>();
        ArrayList<ReplyVo> replyVos = snsMapper.selectReplyListByArticleSeq(articleSeq, Integer.parseInt(page));
        Map<String, Object> results = new HashMap<>();
        for (ReplyVo rVo :
                replyVos) {
            reply.put("reply_seq", rVo.getReplySeq());
            reply.put("nickname", rVo.getNickname());
            reply.put("user_seq", rVo.getUserSeq());
            reply.put("profile_url", rVo.getProfileUrl());
            reply.put("created_at", rVo.getCreatedAt());
            reply.put("content", rVo.getContent());
            reply.put("rereply_count", rVo.getRereplyCount());
            if (Objects.equals(rVo.getCreatedAt(), rVo.getUpdatedAt())) { reply.put("modified", 'n'); }
            else { reply.put("modified", 'y'); }
            replyList.add(reply);
            reply = new HashMap<>();
        }
        results.put("total_page", totalPage);
        results.put("page", Integer.parseInt(page));
        results.put("results", replyList);
        return results;
    }

    public ReplyVo createReply(int articleSeq, int userSeq, String content) {
        ReplyVo replyVo = new ReplyVo();
        // article이 존재하는지 확인
        int article = snsMapper.selectArticleByArticleSeq(articleSeq);
        if (article == 0) { replyVo.setResult(ReplyVo.ReplyResult.NO_SUCH_ARTICLE); return replyVo; }
        else {
            int inserted = snsMapper.insertReply(articleSeq, userSeq, content);
            if (inserted == 1) { replyVo.setResult(ReplyVo.ReplyResult.SUCCESS); }
            else { replyVo.setResult(ReplyVo.ReplyResult.FAILURE); }
            return replyVo;
        }
    }

    public ReplyVo updateReply(int userSeq, int replySeq, String content) {
        Integer replyUser = snsMapper.selectUserSeqByReplySeq(replySeq);
        ReplyVo replyVo = new ReplyVo();
        // reply가 없는 경우
        if (replyUser == null) { replyVo.setResult(ReplyVo.ReplyResult.NO_SUCH_REPLY); return replyVo;}
        // replyUser와 userSeq가 다른 경우(본인이 아닌 경우) (UNAUTH)
        if (replyUser != userSeq) { replyVo.setResult(ReplyVo.ReplyResult.UNAUTHORIZED); return replyVo;}
        // 본인이 맞는 경우 -> 수정
        int updated = snsMapper.updateReplyByReplySeq(replySeq, content);
        if (updated == 1) { replyVo.setResult(ReplyVo.ReplyResult.SUCCESS); }
        else { replyVo.setResult(ReplyVo.ReplyResult.FAILURE); }
        return replyVo;
    }

    public ReplyVo deleteReply(int userSeq, int replySeq) {
        Integer replyUser = snsMapper.selectUserSeqByReplySeq(replySeq);
        ReplyVo replyVo = new ReplyVo();
        // reply가 없는 경우
        if (replyUser == null) { replyVo.setResult(ReplyVo.ReplyResult.NO_SUCH_REPLY); return replyVo;}
        // replyUser와 userSeq가 다른 경우(본인이 아닌 경우) (UNAUTH)
        if (replyUser != userSeq) { replyVo.setResult(ReplyVo.ReplyResult.UNAUTHORIZED); return replyVo;}
        int deleted = snsMapper.deleteReplyByReplySeq(replySeq);
        if (deleted == 1) { replyVo.setResult(ReplyVo.ReplyResult.SUCCESS); }
        else { replyVo.setResult(ReplyVo.ReplyResult.FAILURE); }
        return replyVo;
    }

    public Map<String, Object> rereplyList(int replySeq, String page) {
        Integer replyUser = snsMapper.selectUserSeqByReplySeq(replySeq);
        if (replyUser == null) { return null; }
        int totalPage = (int) Math.ceil((snsMapper.selectRereplyByReplySeq(replySeq))/10.0);
        if (totalPage == 0) { totalPage = 1; }
        if (Integer.parseInt(page) > totalPage) { return null; }
        Map<String, Object> results = new HashMap<>();
        ArrayList<ReplyVo> rereplyVos = snsMapper.selectRereplyListByReplySeq(replySeq, Integer.parseInt(page));
        ArrayList<Map<String, Object>> rereplyList = new ArrayList<>();
        Map<String, Object> reply = new HashMap<>();
        for (ReplyVo rVo :
                rereplyVos) {
            reply.put("reply_seq", rVo.getReplySeq());
            reply.put("nickname", rVo.getNickname());
            reply.put("user_seq", rVo.getUserSeq());
            reply.put("profile_url", rVo.getProfileUrl());
            reply.put("created_at", rVo.getCreatedAt());
            reply.put("content", rVo.getContent());
            if (Objects.equals(rVo.getCreatedAt(), rVo.getUpdatedAt())) { reply.put("modified", 'n'); }
            else { reply.put("modified", 'y'); }
            rereplyList.add(reply);
            reply = new HashMap<>();
        }
        results.put("total_page", totalPage);
        results.put("page", Integer.parseInt(page));
        results.put("results", rereplyList);
        return results;
    }

    public ReplyVo createRereply(int articleSeq,int replySeq, int userSeq, String content) {
        ReplyVo replyVo = new ReplyVo();
        // reply가 존재하는지 확인
        int reply = snsMapper.selectReplyByReplySeq(replySeq);
        if (reply == 0) { replyVo.setResult(ReplyVo.ReplyResult.NO_SUCH_REPLY); return replyVo; }
        else {
            int inserted = snsMapper.insertRereply(articleSeq, replySeq, userSeq, content);
            if (inserted == 1) { replyVo.setResult(ReplyVo.ReplyResult.SUCCESS); }
            else { replyVo.setResult(ReplyVo.ReplyResult.FAILURE); }
            return replyVo;
        }
    }

    public String likeArticle(int userSeq, int articleSeq, char like) {
        // user 있는지 확인
        Map<String, String> user = snsMapper.selectUserByUserSeq(userSeq);
        if (user == null) { return "NO_SUCH_USER"; }
        // article 있는지 확인
        int article = snsMapper.selectArticleByArticleSeq(articleSeq);
        if (article == 0) { return "NO_SUCH_ARTICLE"; }
        // 좋아요 누른 적 있는지 확인
        int liked = snsMapper.selectArticleLikeByUserSeq(articleSeq, userSeq);
        if (like == 'l') {
            if (liked == 0) {
                int inserted = snsMapper.insertLike(articleSeq, userSeq);
                if (inserted == 1) { return "SUCCESS"; }
            }
        }
        else if (like == 'u') {
            if (liked == 1) {
                int deleted = snsMapper.deleteLike(articleSeq, userSeq);
                if (deleted == 1) { return "SUCCESS"; }
            }
        }
        else { return "BAD_REQUEST"; }
        return "BAD_REQUEST";
    }

    public Map<String, Object> likeUserList(int articleSeq, String page) {
        // article 있는지 확인
        int article = snsMapper.selectArticleByArticleSeq(articleSeq);
        if (article == 0) { return null; }
        int totalPage = (int) Math.ceil((snsMapper.selectLikeCountByArticleSeq(articleSeq))/10.0);
        if (totalPage == 0) { totalPage = 1; }
        if (Integer.parseInt(page) > totalPage) { return null; }
        ArrayList<UserVo> userList = snsMapper.selectLikeUserListByArticleSeq(articleSeq, Integer.parseInt(page));
        Map<String, Object> results = new HashMap<>();
        ArrayList<Map<String, Object>> likeUserList = new ArrayList<>();
        Map<String, Object> user = new HashMap<>();
        for (UserVo uVo :
                userList) {
            user.put("profile_url", uVo.getProfileImageLink());
            user.put("nickname", uVo.getNickname());
            user.put("user_seq", uVo.getUserSeq());
            likeUserList.add(user);
            user = new HashMap<>();
        }
        results.put("total_page", totalPage);
        results.put("page", Integer.parseInt(page));
        results.put("results", likeUserList);
        return results;
    }

    public String reportArticle(int articleSeq, int userSeq, int reportSeq) {
        // article 있는지 확인
        int article = snsMapper.selectArticleByArticleSeq(articleSeq);
        if (article == 0) { return "BAD_REQUEST"; }
        Integer reportedUser = snsMapper.selectUserSeqByArticleSeq(articleSeq);
        // 신고한 적 있는지 확인
        int reported = snsMapper.selectReportArticleByUserSeq(articleSeq, userSeq);
        if (reported == 1) { return "ALREADY_REPORTED"; }
        // 신고하기
        int inserted = snsMapper.insertReport(articleSeq, userSeq, reportedUser, reportSeq, "article");
        if (inserted == 1) { return "SUCCESS"; }
        return "BAD_REQUEST";
    }

    public String reportReply(int replySeq, int userSeq, int reportSeq) {
        // reply 있는지 확인
        int reply = snsMapper.selectReplyByReplySeq(replySeq);
        if (reply == 0) { return "BAD_REQUEST"; }
        Integer reportedUser = snsMapper.selectUserSeqByReplySeq(replySeq);
        // 신고한 적 있는지 확인
        int reported = snsMapper.selectReportReplyByUserSeq(replySeq, userSeq);
        if(reported == 1) { return "ALREADY_REPORTED"; }
        // 신고하기
        int inserted = snsMapper.insertReport(replySeq, userSeq, reportedUser, reportSeq, "reply");
        if (inserted == 1) { return "SUCCESS"; }
        return "BAD_REQUEST";
    }

    public String followUser(int followerUserSeq, int followedUserSeq) {
        // follow할 유저가 있는지 확인
        Map<String, String> user = snsMapper.selectUserByUserSeq(followedUserSeq);
        if (user == null) { return "NO_SUCH_USER"; }
        // follow한 적 있는지 확인
        int followed = snsMapper.selectFollowByUserSeq(followerUserSeq, followedUserSeq);
        if (followed != 0) { return "BAD_REQUEST"; }
        // follow
        int follow = snsMapper.insertFollow(followerUserSeq, followedUserSeq);
        if (follow == 1) { return "SUCCESS"; }
        return "BAD_REQUEST";
    }

    public String unfollowUser(int followerUserSeq, int followedUserSeq) {
        // unfollow할 유저가 있는지 확인
        Map<String, String> user = snsMapper.selectUserByUserSeq(followedUserSeq);
        if (user == null) { return "NO_SUCH_USER"; }
        // follow한 적 있는지 확인
        int followed = snsMapper.selectFollowByUserSeq(followerUserSeq, followedUserSeq);
        if (followed != 1) { return "BAD_REQUEST"; }
        // unfollow
        int deleted = snsMapper.deleteFollow(followerUserSeq, followedUserSeq);
        if (deleted == 1) { return "SUCCESS"; }
        return "BAD_REQUEST";
    }

    public Map<String, Object> followerList(int currentUserSeq, int userSeq, String page) {
        // user확인
        Map<String, String> user = snsMapper.selectUserByUserSeq(userSeq);
        if (user == null) { return null; }
        // follow 목록 불러오기
        int totalPage = (int) Math.ceil((snsMapper.selectFollowerByUserSeq(userSeq))/10.0);
        if (totalPage == 0) { totalPage = 1; }
        if (Integer.parseInt(page) > totalPage) { return null; }
        ArrayList<UserVo> users = snsMapper.selectFollowerListByUserSeq(userSeq, Integer.parseInt(page));
        Map<String, Object> results = new HashMap<>();
        ArrayList<Map<String, Object>> followerList = new ArrayList<>();
        Map<String, Object> follower = new HashMap<>();
        for (UserVo uVo :
                users) {
            follower.put("user_seq", uVo.getUserSeq());
            follower.put("nickname", uVo.getNickname());
            follower.put("profile_url", uVo.getProfileImageLink());
            int followed = snsMapper.selectFollowByUserSeq(currentUserSeq, uVo.getUserSeq());
            if (followed == 1) { follower.put("follow_yn", 'y'); }
            else { follower.put("follow_yn", 'n'); }
            followerList.add(follower);
            follower = new HashMap<>();
        }
        results.put("page", Integer.parseInt(page));
        results.put("total_page", totalPage);
        results.put("results", followerList);

        return results;
    }

    public Map<String, Object> followingList(int currentUserSeq, int userSeq, String page) {
        // user확인
        Map<String, String> user = snsMapper.selectUserByUserSeq(userSeq);
        if (user == null) { return null; }
        // follow 목록 불러오기
        int totalPage = (int) Math.ceil((snsMapper.selectFollowingByUserSeq(userSeq))/10.0);
        if (totalPage == 0) { totalPage = 1; }
        if (Integer.parseInt(page) > totalPage) { return null; }
        ArrayList<UserVo> users = snsMapper.selectFollowingListByUserSeq(userSeq, Integer.parseInt(page));
        ArrayList<Map<String, Object>> followingList = new ArrayList<>();
        Map<String, Object> results = new HashMap<>();
        Map<String, Object> following = new HashMap<>();
        for (UserVo uVo :
                users) {
            following.put("user_seq", uVo.getUserSeq());
            following.put("nickname", uVo.getNickname());
            following.put("profile_url", uVo.getProfileImageLink());
            int followed = snsMapper.selectFollowByUserSeq(currentUserSeq, uVo.getUserSeq());
            if (followed == 1) { following.put("follow_yn", 'y'); }
            else { following.put("follow_yn", 'n'); }
            followingList.add(following);
            following = new HashMap<>();
        }
        results.put("total_page", totalPage);
        results.put("page", Integer.parseInt(page));
        results.put("results", followingList);
        return results;
    }

    public int getTotalArticlePage(int userSeq) {
        int totalCount = this.snsMapper.selectArticleCount(userSeq);
        return (totalCount - 1) / PAGE_SIZE + 1;
    }

    public ArrayList<ArticleListVo> articleList(int userSeq, int page) {
        Calendar recent = Calendar.getInstance();
        recent.add(Calendar.DATE, RECENT_DAY);
        String recentString = new SimpleDateFormat("yyyy-MM-dd").format(recent.getTime());
        int followCount = this.snsMapper.selectArticleCountByFollowCreatedAt(userSeq, recentString);
        int followPage = (followCount - 1) / PAGE_SIZE + 1;
        ArrayList<ArticleListVo> articleList;
        if (page < followPage) {
            articleList = this.snsMapper.selectArticleByFollowCreatedAt(userSeq, PAGE_SIZE, PAGE_SIZE * (page - 1), recentString);
        } else {
            int followRemain = followCount % PAGE_SIZE;
            int other = PAGE_SIZE - followRemain;
            if (page == followPage) {
                articleList = this.snsMapper.selectArticleByFollowCreatedAt(userSeq, followRemain, PAGE_SIZE * (page - 1), recentString);
                articleList.addAll(this.snsMapper.selectArticleByNotFollow(userSeq, other, 0, recentString));
            } else {
                articleList = this.snsMapper.selectArticleByNotFollow(userSeq, PAGE_SIZE, PAGE_SIZE * (page - followPage - 1) + other, recentString);
            }
        }
        return articleList;
    }

    public Map<String, Object> collection(int userSeq, int page) {
        int totalPage = (int) Math.ceil((snsMapper.selectCollectionCountByUserSeq(userSeq))/10.0);
        if (totalPage == 0) { totalPage = 1; }
        if (page > totalPage) { return null; }
        ArrayList<Map<String, Object>> results = snsMapper.selectCollectionByUserSeq(userSeq, PAGE_SIZE, (page-1)*PAGE_SIZE);
        Map<String, Object> collection = new HashMap<>();
        collection.put("total_page", totalPage);
        collection.put("page", page);
        collection.put("results", results);
        return collection;
    }
}
