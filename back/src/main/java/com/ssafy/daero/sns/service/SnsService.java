package com.ssafy.daero.sns.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.daero.sns.mapper.SnsMapper;
import com.ssafy.daero.sns.vo.ArticleVo;
import com.ssafy.daero.sns.vo.ReplyVo;
import com.ssafy.daero.sns.vo.StampVo;
import com.ssafy.daero.sns.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class SnsService {
    private final SnsMapper snsMapper;

    public SnsService(SnsMapper snsMapper) { this.snsMapper = snsMapper; }

    public Map<String, Object> articleDetail(int articleSeq) throws JsonProcessingException {
        ArticleVo articleVo = snsMapper.selectArticleAndTripInfoByArticleSeq(articleSeq);
        Map<String, Object> articleDetail = new HashMap<>();
        if(articleVo == null) { return articleDetail; }
        ArrayList<StampVo> stampVo = snsMapper.selectStampAndDayInfoByTripSeq(articleVo.getTripSeq());
        Map<String, String> userInfo = snsMapper.selectUserByUserSeq(articleVo.getUserSeq());
        ArrayList<Integer> tags = snsMapper.selectPlaceTagsByArticleSeq(articleSeq);


        ArrayList<Map> records = new ArrayList<>();
        Map<String, Object> days = new HashMap<>();
        ArrayList<Map> stamps = new ArrayList<>();
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
        ArrayList<Map> expenses = mapper.readValue(articleVo.getTripExpenses(), ArrayList.class);

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

    public Integer deleteArticle(int articleSeq, int userSeq) {
        // 본인 게시글인지 확인
        Integer articleUser = snsMapper.selectUserSeqByArticleSeq(articleSeq);
        if (articleUser == null) { return 0; }
        if (articleUser == userSeq) {
            snsMapper.deleteArticleTagByArticleSeq(articleSeq);
            snsMapper.deleteReplyByArticleSeq(articleSeq);
            snsMapper.deleteArticleByArticleSeq(articleSeq);
            return 1;
        }
        else {
            return 99;
        }
    }

    public ArrayList<Map<String, Object>> replyList(int articleSeq, String page) {
        Integer articleUser = snsMapper.selectUserSeqByArticleSeq(articleSeq);
        if (articleUser == null) { return null; }
        ArrayList<Map<String, Object>> replyList = new ArrayList<>();
        Map<String, Object> reply = new HashMap<>();
        ArrayList<ReplyVo> replyVos = snsMapper.selectReplyListByArticleSeq(articleSeq, Integer.parseInt(page));

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
        return replyList;
    }

    public ReplyVo createReply(int articleSeq, int userSeq, String content) {
        ReplyVo replyVo = new ReplyVo();
        // article이 존재하는지 확인
        int article = snsMapper.selectArticleByArticleSeq(articleSeq);
        if (article == 0) { replyVo.setResult(ReplyVo.ReplyResult.NO_SUCH_ARTICLE); return replyVo; }
        else {
            snsMapper.insertReply(articleSeq, userSeq, content);
            replyVo.setResult(ReplyVo.ReplyResult.SUCCESS);
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
        snsMapper.updateReplyByReplySeq(replySeq, content);
        replyVo.setResult(ReplyVo.ReplyResult.SUCCESS);
        return replyVo;
    }

    public ReplyVo deleteReply(int userSeq, int replySeq) {
        Integer replyUser = snsMapper.selectUserSeqByReplySeq(replySeq);
        ReplyVo replyVo = new ReplyVo();
        // reply가 없는 경우
        if (replyUser == null) { replyVo.setResult(ReplyVo.ReplyResult.NO_SUCH_REPLY); return replyVo;}
        // replyUser와 userSeq가 다른 경우(본인이 아닌 경우) (UNAUTH)
        if (replyUser != userSeq) { replyVo.setResult(ReplyVo.ReplyResult.UNAUTHORIZED); return replyVo;}
        snsMapper.deleteReplyByReplySeq(replySeq);
        replyVo.setResult(ReplyVo.ReplyResult.SUCCESS);
        return replyVo;
    }

    public ArrayList<Map<String, Object>> rereplyList(int replySeq, String page) {
        Integer replyUser = snsMapper.selectUserSeqByReplySeq(replySeq);
        if (replyUser == null) { return null; }
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
        return rereplyList;
    }

    public ReplyVo createRereply(int articleSeq,int replySeq, int userSeq, String content) {
        ReplyVo replyVo = new ReplyVo();
        // reply가 존재하는지 확인
        int reply = snsMapper.selectReplyByReplySeq(replySeq);
        if (reply == 0) { replyVo.setResult(ReplyVo.ReplyResult.NO_SUCH_REPLY); return replyVo; }
        else {
            snsMapper.insertRereply(articleSeq, replySeq, userSeq, content);
            replyVo.setResult(ReplyVo.ReplyResult.SUCCESS);
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
                snsMapper.insertLike(articleSeq, userSeq);
                return "SUCCESS";
            }
            else { return "BAD_REQUEST"; }
        }
        else if (like == 'u') {
            if (liked == 1) {
                snsMapper.deleteLike(articleSeq, userSeq);
                return "SUCCESS";
            }
            else { return "BAD_REQUEST"; }
        }
        else { return "BAD_REQUEST"; }
    }

    public ArrayList<Map<String, Object>> likeUserList(int articleSeq, String page) {
        // article 있는지 확인
        int article = snsMapper.selectArticleByArticleSeq(articleSeq);
        if (article == 0) { return null; }
        ArrayList<UserVo> userList = snsMapper.selectLikeUserListByArticleSeq(articleSeq, Integer.parseInt(page));
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
        return likeUserList;
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
        snsMapper.insertReport(articleSeq, userSeq, reportedUser, reportSeq, "article");
        return "SUCCESS";
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
        snsMapper.insertReport(replySeq, userSeq, reportedUser, reportSeq, "reply");
        return "SUCCESS";
    }

    public String followUser(int followerUserSeq, int followedUserSeq) {
        // follow할 유저가 있는지 확인
        Map<String, String> user = snsMapper.selectUserByUserSeq(followedUserSeq);
        if (user == null) { return "NO_SUCH_USER"; }
        // follow한 적 있는지 확인
        int followed = snsMapper.selectFollowByUserSeq(followerUserSeq, followedUserSeq);
        if (followed != 0) { return "BAD_REQUEST"; }
        // follow
        snsMapper.insertFollow(followerUserSeq, followedUserSeq);
        return "SUCCESS";
    }

    public String unfollowUser(int followerUserSeq, int followedUserSeq) {
        // unfollow할 유저가 있는지 확인
        Map<String, String> user = snsMapper.selectUserByUserSeq(followedUserSeq);
        if (user == null) { return "NO_SUCH_USER"; }
        // follow한 적 있는지 확인
        int followed = snsMapper.selectFollowByUserSeq(followerUserSeq, followedUserSeq);
        if (followed != 1) { return "BAD_REQUEST"; }
        // unfollow
        snsMapper.deleteFollow(followerUserSeq, followedUserSeq);
        return "SUCCESS";
    }

    public Map<String, Object> followerList(int userSeq, String page) {
        // user확인
        Map<String, String> user = snsMapper.selectUserByUserSeq(userSeq);
        if (user == null) { return null; }
        // follow 목록 불러오기
        int totalPage = (int) Math.ceil((snsMapper.selectFollowerByUserSeq(userSeq))*10/10.0);
        if (totalPage == 0) { totalPage = 1; }
        ArrayList<UserVo> users = snsMapper.selectFollowerListByUserSeq(userSeq, Integer.parseInt(page));
        Map<String, Object> results = new HashMap<>();
        ArrayList<Map<String, Object>> followerList = new ArrayList<>();
        Map<String, Object> follower = new HashMap<>();
        for (UserVo uVo :
                users) {
            follower.put("user_seq", uVo.getUserSeq());
            follower.put("nickname", uVo.getNickname());
            follower.put("profile_url", uVo.getProfileImageLink());
            followerList.add(follower);
            follower = new HashMap<>();
        }
        results.put("page", Integer.parseInt(page));
        results.put("total_page", totalPage);
        results.put("results", followerList);

        return results;
    }

    public Map<String, Object> followingList(int userSeq, String page) {
        // user확인
        Map<String, String> user = snsMapper.selectUserByUserSeq(userSeq);
        if (user == null) { return null; }
        // follow 목록 불러오기
        int totalPage = (int) Math.ceil((snsMapper.selectFollowingByUserSeq(userSeq))*10/10.0);
        if (totalPage == 0) { totalPage = 1; }
        ArrayList<UserVo> users = snsMapper.selectFollowingListByUserSeq(userSeq, Integer.parseInt(page));
        ArrayList<Map<String, Object>> followingList = new ArrayList<>();
        Map<String, Object> results = new HashMap<>();
        Map<String, Object> following = new HashMap<>();
        for (UserVo uVo :
                users) {
            following.put("user_seq", uVo.getUserSeq());
            following.put("nickname", uVo.getNickname());
            following.put("profile_url", uVo.getProfileImageLink());
            followingList.add(following);
            following = new HashMap<>();
        }
        results.put("total_page", totalPage);
        results.put("page", page);
        results.put("results", followingList);
        return results;
    }
}
