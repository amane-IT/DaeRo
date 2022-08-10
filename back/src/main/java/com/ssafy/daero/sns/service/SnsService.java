package com.ssafy.daero.sns.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.daero.sns.mapper.SnsMapper;
import com.ssafy.daero.sns.vo.*;
import com.ssafy.daero.trip.dto.TripPlaceDto;
import com.ssafy.daero.user.dto.UserDto;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("FieldCanBeLocal")
@Service
public class SnsService {
    private final SnsMapper snsMapper;
    private final int ARTICLE_PAGE_SIZE = 10;
    private final int REPLY_PAGE_SIZE = 10;
    private final int RECENT_DAY = -3;
    private final int SEARCH_USER_PAGE_SIZE = 10;
    private final int SEARCH_ARTICLE_PAGE_SIZE = 4;
    private final int USER_PAGE_SIZE = 10;

    public SnsService(SnsMapper snsMapper) {
        this.snsMapper = snsMapper;
    }

    public Map<String, Object> articleDetail(int articleSeq, int userSeq) throws JsonProcessingException {
        ArticleVo articleVo = snsMapper.selectArticleAndTripInfoByArticleSeq(articleSeq);
        Map<String, Object> articleDetail = new HashMap<>();
        if (articleVo == null) {
            return articleDetail;
        }
        int liked = snsMapper.selectArticleLikeByUserSeq(articleSeq, userSeq);
        char like;
        if (liked == 0) {
            like = 'n';
        } else {
            like = 'y';
        }
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
        articleDetail.put("like_yn", like);
        articleDetail.put("expose", articleVo.getOpenYn());
        return articleDetail;
    }

    public int deleteArticle(int articleSeq, int userSeq) {
        // 본인 게시글인지 확인
        Integer articleUser = snsMapper.selectUserSeqByArticleSeq(articleSeq);
        if (articleUser == null) {
            return 0;
        }
        if (articleUser == userSeq) {
            int deletedArticle = snsMapper.deleteArticleByArticleSeq(articleSeq);
            if (deletedArticle == 0) {
                return 1;
            }
        }
        return 2;
    }

    private LinkedList<Map<String, Object>> createReplyMapList(List<ReplyVo> replyVos) {
        LinkedList<Map<String, Object>> replyList = new LinkedList<>();
        for (ReplyVo rVo : replyVos) {
            Map<String, Object> reply = new HashMap<>();
            reply.put("reply_seq", rVo.getReplySeq());
            reply.put("nickname", rVo.getNickname());
            reply.put("user_seq", rVo.getUserSeq());
            reply.put("profile_url", rVo.getProfileUrl());
            reply.put("created_at", rVo.getCreatedAt());
            reply.put("content", rVo.getContent());
            reply.put("rereply_count", rVo.getRereplyCount());
            if (Objects.equals(rVo.getCreatedAt(), rVo.getUpdatedAt())) {
                reply.put("modified", 'n');
            } else {
                reply.put("modified", 'y');
            }
            replyList.add(reply);
        }
        return replyList;
    }

    public Map<String, Object> replyList(int articleSeq, int page, int userSeq) {
        Integer articleUser = snsMapper.selectUserSeqByArticleSeq(articleSeq);
        if (articleUser == null) {
            return null;
        }
        int totalCount = snsMapper.selectReplyByArticleSeq(articleSeq);
        int totalPage = (totalCount - 1) / REPLY_PAGE_SIZE + 1;
        if (totalPage == 0) {
            totalPage = 1;
        }
        if (page > totalPage) {
            return null;
        }
        LinkedList<Map<String, Object>> replyList = createReplyMapList(snsMapper.selectReplyListByArticleSeq(articleSeq, REPLY_PAGE_SIZE, REPLY_PAGE_SIZE * (page - 1), userSeq));
        Map<String, Object> results = new HashMap<>();
        results.put("total_page", totalPage);
        results.put("page", page);
        results.put("results", replyList);
        return results;
    }

    public ReplyVo createReply(int articleSeq, int userSeq, String content) {
        ReplyVo replyVo = new ReplyVo();
        // article이 존재하는지 확인
        int article = snsMapper.selectArticleByArticleSeq(articleSeq);
        if (article == 0) {
            replyVo.setResult(ReplyVo.ReplyResult.NO_SUCH_ARTICLE);
            return replyVo;
        } else {
            int inserted = snsMapper.insertReply(articleSeq, userSeq, content);
            if (inserted == 1) {
                replyVo.setResult(ReplyVo.ReplyResult.SUCCESS);
            } else {
                replyVo.setResult(ReplyVo.ReplyResult.FAILURE);
            }
            return replyVo;
        }
    }

    public ReplyVo updateReply(int userSeq, int replySeq, String content) {
        Integer replyUser = snsMapper.selectUserSeqByReplySeq(replySeq);
        ReplyVo replyVo = new ReplyVo();
        // reply가 없는 경우
        if (replyUser == null) {
            replyVo.setResult(ReplyVo.ReplyResult.NO_SUCH_REPLY);
            return replyVo;
        }
        // replyUser와 userSeq가 다른 경우(본인이 아닌 경우) (UNAUTH)
        if (replyUser != userSeq) {
            replyVo.setResult(ReplyVo.ReplyResult.UNAUTHORIZED);
            return replyVo;
        }
        // 본인이 맞는 경우 -> 수정
        int updated = snsMapper.updateReplyByReplySeq(replySeq, content);
        if (updated == 1) {
            replyVo.setResult(ReplyVo.ReplyResult.SUCCESS);
        } else {
            replyVo.setResult(ReplyVo.ReplyResult.FAILURE);
        }
        return replyVo;
    }

    public ReplyVo deleteReply(int userSeq, int replySeq) {
        Integer replyUser = snsMapper.selectUserSeqByReplySeq(replySeq);
        ReplyVo replyVo = new ReplyVo();
        // reply가 없는 경우
        if (replyUser == null) {
            replyVo.setResult(ReplyVo.ReplyResult.NO_SUCH_REPLY);
            return replyVo;
        }
        // replyUser와 userSeq가 다른 경우(본인이 아닌 경우) (UNAUTH)
        if (replyUser != userSeq) {
            replyVo.setResult(ReplyVo.ReplyResult.UNAUTHORIZED);
            return replyVo;
        }
        int deleted = snsMapper.deleteReplyByReplySeq(replySeq);
        if (deleted == 1) {
            replyVo.setResult(ReplyVo.ReplyResult.SUCCESS);
        } else {
            replyVo.setResult(ReplyVo.ReplyResult.FAILURE);
        }
        return replyVo;
    }

    public Map<String, Object> rereplyList(int replySeq, int page, int userSeq) {
        Integer replyUser = snsMapper.selectUserSeqByReplySeq(replySeq);
        if (replyUser == null) {
            return null;
        }
        int totalCount = snsMapper.selectRereplyByReplySeq(replySeq);
        int totalPage = (totalCount - 1) / REPLY_PAGE_SIZE + 1;
        if (totalPage == 0) {
            totalPage = 1;
        }
        if (page > totalPage) {
            return null;
        }
        LinkedList<Map<String, Object>> rereplyList = createReplyMapList(snsMapper.selectRereplyListByReplySeq(replySeq, REPLY_PAGE_SIZE, REPLY_PAGE_SIZE * (page - 1), userSeq));
        Map<String, Object> results = new HashMap<>();
        results.put("total_page", totalPage);
        results.put("page", page);
        results.put("results", rereplyList);
        return results;
    }

    public ReplyVo createRereply(int articleSeq, int replySeq, int userSeq, String content) {
        ReplyVo replyVo = new ReplyVo();
        // reply가 존재하는지 확인
        int reply = snsMapper.selectReplyByReplySeq(replySeq);
        if (reply == 0) {
            replyVo.setResult(ReplyVo.ReplyResult.NO_SUCH_REPLY);
        } else {
            int inserted = snsMapper.insertRereply(articleSeq, replySeq, userSeq, content);
            if (inserted == 1) {
                replyVo.setResult(ReplyVo.ReplyResult.SUCCESS);
            } else {
                replyVo.setResult(ReplyVo.ReplyResult.FAILURE);
            }
        }
        return replyVo;
    }

    public String likeArticle(int userSeq, int articleSeq, char like) {
        // user 있는지 확인
        Map<String, String> user = snsMapper.selectUserByUserSeq(userSeq);
        if (user == null) {
            return "NO_SUCH_USER";
        }
        // article 있는지 확인
        int article = snsMapper.selectArticleByArticleSeq(articleSeq);
        if (article == 0) {
            return "NO_SUCH_ARTICLE";
        }
        // 좋아요 누른 적 있는지 확인
        int liked = snsMapper.selectArticleLikeByUserSeq(articleSeq, userSeq);
        if (like == 'l') {
            if (liked == 0) {
                int inserted = snsMapper.insertLike(articleSeq, userSeq);
                if (inserted == 1) {
                    return "SUCCESS";
                }
            }
        } else if (like == 'u') {
            if (liked == 1) {
                int deleted = snsMapper.deleteLike(articleSeq, userSeq);
                if (deleted == 1) {
                    return "SUCCESS";
                }
            }
        } else {
            return "BAD_REQUEST";
        }
        return "BAD_REQUEST";
    }

    public Map<String, Object> likeUserList(int articleSeq, String page) {
        // article 있는지 확인
        int article = snsMapper.selectArticleByArticleSeq(articleSeq);
        if (article == 0) {
            return null;
        }
        int totalPage = (int) Math.ceil((snsMapper.selectLikeCountByArticleSeq(articleSeq)) / 10.0);
        if (totalPage == 0) {
            totalPage = 1;
        }
        if (Integer.parseInt(page) > totalPage) {
            return null;
        }
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
        if (article == 0) {
            return "BAD_REQUEST";
        }
        Integer reportedUser = snsMapper.selectUserSeqByArticleSeq(articleSeq);
        // 신고한 적 있는지 확인
        int reported = snsMapper.selectReportArticleByUserSeq(articleSeq, userSeq);
        if (reported == 1) {
            return "ALREADY_REPORTED";
        }
        // 신고하기
        int inserted = snsMapper.insertReport(articleSeq, userSeq, reportedUser, reportSeq, "article");
        if (inserted == 1) {
            return "SUCCESS";
        }
        return "BAD_REQUEST";
    }

    public String reportReply(int replySeq, int userSeq, int reportSeq) {
        // reply 있는지 확인
        int reply = snsMapper.selectReplyByReplySeq(replySeq);
        if (reply == 0) {
            return "BAD_REQUEST";
        }
        Integer reportedUser = snsMapper.selectUserSeqByReplySeq(replySeq);
        // 신고한 적 있는지 확인
        int reported = snsMapper.selectReportReplyByUserSeq(replySeq, userSeq);
        if (reported == 1) {
            return "ALREADY_REPORTED";
        }
        // 신고하기
        int inserted = snsMapper.insertReport(replySeq, userSeq, reportedUser, reportSeq, "reply");
        if (inserted == 1) {
            return "SUCCESS";
        }
        return "BAD_REQUEST";
    }

    public String reportUser(int userSeq, int currentUserSeq, int reportSeq) {
        Map<String, String> user = snsMapper.selectUserByUserSeq(userSeq);
        if (user == null) {
            return "BAD_REQUEST";
        }
        // 신고한 적 있는지 확인
        int reported = snsMapper.selectReportUserByUserSeq(userSeq, currentUserSeq);
        if (reported == 1) {
            return "ALREDY_REPORTED";
        }
        // 신고하기
        int inserted = snsMapper.insertReport(userSeq, currentUserSeq, userSeq, reportSeq, "user");
        if (inserted == 1) {
            return "SUCCESS";
        }
        return "BAC_REQUEST";
    }

    public String followUser(int followerUserSeq, int followedUserSeq) {
        // follow할 유저가 있는지 확인
        Map<String, String> user = snsMapper.selectUserByUserSeq(followedUserSeq);
        if (user == null) {
            return "NO_SUCH_USER";
        }
        // follow한 적 있는지 확인
        int followed = snsMapper.selectFollowByUserSeq(followerUserSeq, followedUserSeq);
        if (followed != 0) {
            return "BAD_REQUEST";
        }
        // follow
        int follow = snsMapper.insertFollow(followerUserSeq, followedUserSeq);
        if (follow == 1) {
            return "SUCCESS";
        }
        return "BAD_REQUEST";
    }

    public String unfollowUser(int followerUserSeq, int followedUserSeq) {
        // unfollow할 유저가 있는지 확인
        Map<String, String> user = snsMapper.selectUserByUserSeq(followedUserSeq);
        if (user == null) {
            return "NO_SUCH_USER";
        }
        // follow한 적 있는지 확인
        int followed = snsMapper.selectFollowByUserSeq(followerUserSeq, followedUserSeq);
        if (followed != 1) {
            return "BAD_REQUEST";
        }
        // unfollow
        int deleted = snsMapper.deleteFollow(followerUserSeq, followedUserSeq);
        if (deleted == 1) {
            return "SUCCESS";
        }
        return "BAD_REQUEST";
    }

    private LinkedList<Map<String, Object>> createUserMapList(ArrayList<UserVo> userVos) {
        LinkedList<Map<String, Object>> userList = new LinkedList<>();
        for (UserVo userVo : userVos) {
            Map<String, Object> map = new HashMap<>();
            map.put("user_seq", userVo.getUserSeq());
            map.put("nickname", userVo.getNickname());
            map.put("profile_url", userVo.getProfileImageLink());
            map.put("follow_yn", userVo.getFollowYn());
            userList.add(map);
        }
        return userList;
    }

    public Map<String, Object> followerList(int userSeq, int followed, int page) {
        // user확인
        Map<String, String> user = snsMapper.selectUserByUserSeq(followed);
        if (user == null) {
            return null;
        }
        // follow 목록 불러오기
        int totalCount = snsMapper.selectFollowerByUserSeq(followed);
        int totalPage = (totalCount - 1) / USER_PAGE_SIZE + 1;
        if (totalPage == 0) {
            totalPage = 1;
        }
        if (page > totalPage) {
            return null;
        }
        LinkedList<Map<String, Object>> followerList = createUserMapList(snsMapper.selectFollowerListByFollowedUserSeq(followed, USER_PAGE_SIZE, USER_PAGE_SIZE * (page - 1), userSeq));
        Map<String, Object> results = new HashMap<>();
        results.put("total_page", totalPage);
        results.put("page", page);
        results.put("results", followerList);
        return results;
    }

    public Map<String, Object> followingList(int userSeq, int follower, int page) {
        // user확인
        Map<String, String> user = snsMapper.selectUserByUserSeq(follower);
        if (user == null) {
            return null;
        }
        // follow 목록 불러오기
        int totalCount = snsMapper.selectFollowingByUserSeq(follower);
        int totalPage = (totalCount - 1) / USER_PAGE_SIZE + 1;
        if (totalPage == 0) {
            totalPage = 1;
        }
        if (page > totalPage) {
            return null;
        }
        LinkedList<Map<String, Object>> followingList = createUserMapList(snsMapper.selectFollowingListByFollowerUserSeq(follower, USER_PAGE_SIZE, USER_PAGE_SIZE * (page - 1), userSeq));
        Map<String, Object> results = new HashMap<>();
        results.put("total_page", totalPage);
        results.put("page", page);
        results.put("results", followingList);
        return results;
    }

    public int getTotalArticlePage(int userSeq) {
        int totalCount = this.snsMapper.selectArticleCount(userSeq);
        return (totalCount - 1) / ARTICLE_PAGE_SIZE + 1;
    }

    private LinkedList<Map<String, Object>> createArticleMap(ArrayList<ArticleListVo> articleListVos) {
        LinkedList<Map<String, Object>> list = new LinkedList<>();
        for (ArticleListVo article : articleListVos) {
            Map<String, Object> map = new HashMap<>();
            map.put("article_seq", article.getArticleSeq());
            map.put("nickname", article.getNickname());
            map.put("user_seq", article.getUserSeq());
            map.put("profile_url", article.getProfileUrl());
            map.put("created_at", article.getCreatedAt());
            map.put("thumbnail_url", article.getThumbnailUrl());
            map.put("description", article.getDescription());
            map.put("title", article.getTitle());
            map.put("start_date", article.getStartDate());
            map.put("end_date", article.getEndDate());
            map.put("likes", article.getLikeCount());
            map.put("replies", article.getReplyCount());
            map.put("like_yn", article.getLikeYn());
            list.add(map);
        }
        return list;
    }

    public LinkedList<Map<String, Object>> articleList(int userSeq, int page) {
        Calendar recent = Calendar.getInstance();
        recent.add(Calendar.DATE, RECENT_DAY);
        String recentString = new SimpleDateFormat("yyyy-MM-dd").format(recent.getTime());
        int followCount = this.snsMapper.selectArticleCountByFollowCreatedAt(userSeq, recentString);
        int followPage = (followCount - 1) / ARTICLE_PAGE_SIZE + 1;
        ArrayList<ArticleListVo> articleList;
        if (page < followPage) {
            articleList = this.snsMapper.selectArticleByFollowCreatedAt(userSeq, ARTICLE_PAGE_SIZE, ARTICLE_PAGE_SIZE * (page - 1), recentString);
        } else {
            int followRemain = followCount % ARTICLE_PAGE_SIZE;
            int other = ARTICLE_PAGE_SIZE - followRemain;
            if (page == followPage) {
                articleList = this.snsMapper.selectArticleByFollowCreatedAt(userSeq, followRemain, ARTICLE_PAGE_SIZE * (page - 1), recentString);
                articleList.addAll(this.snsMapper.selectArticleByNotFollow(userSeq, other, 0, recentString));
            } else {
                articleList = this.snsMapper.selectArticleByNotFollow(userSeq, ARTICLE_PAGE_SIZE, ARTICLE_PAGE_SIZE * (page - followPage - 1) + other, recentString);
            }
        }
        return createArticleMap(articleList);
    }

    public int getSearchUserTotalPage(String nickname) {
        int totalCount = this.snsMapper.selectUserCountByNickname(nickname);
        return (totalCount - 1) / SEARCH_USER_PAGE_SIZE + 1;
    }

    private LinkedList<Map<String, Object>> createBriefUserMap(ArrayList<UserDto> userDtos) {
        LinkedList<Map<String, Object>> list = new LinkedList<>();
        for (UserDto userDto : userDtos) {
            Map<String, Object> map = new HashMap<>();
            map.put("user_seq", userDto.getUserSeq());
            map.put("nickname", userDto.getNickname());
            map.put("profile_url", userDto.getProfileImageLink());
            list.add(map);
        }
        return list;
    }

    public LinkedList<Map<String, Object>> searchUser(String nickname, int page, int userSeq) {
        return createBriefUserMap(this.snsMapper.selectUserByNickname(nickname, SEARCH_USER_PAGE_SIZE, SEARCH_USER_PAGE_SIZE * (page - 1), userSeq));
    }

    private LinkedList<Map<String, Object>> createBriefArticleMap(ArrayList<ArticleListVo> articleListVos) {
        LinkedList<Map<String, Object>> list = new LinkedList<>();
        for (ArticleListVo articleListVo : articleListVos) {
            Map<String, Object> map = new HashMap<>();
            map.put("article_seq", articleListVo.getArticleSeq());
            map.put("image_url", articleListVo.getThumbnailUrl());
            map.put("title", articleListVo.getTitle());
            list.add(map);
        }
        return list;
    }

    public Map<String, LinkedList<Map<String, Object>>> searchArticle(String article, int userSeq) {
        Map<String, LinkedList<Map<String, Object>>> resultMap = new HashMap<>();
        ArrayList<ArticleListVo> searchByContent = this.snsMapper.selectArticleByContent(article, SEARCH_ARTICLE_PAGE_SIZE, 0, userSeq);
        resultMap.put("content", createBriefArticleMap(searchByContent));
        ArrayList<ArticleListVo> searchByPlace = this.snsMapper.selectArticleByPlace(article, SEARCH_ARTICLE_PAGE_SIZE, 0, userSeq);
        resultMap.put("place", createBriefArticleMap(searchByPlace));
        return resultMap;
    }

    public int getSearchContentTotalPage(String content) {
        int totalCount = this.snsMapper.selectArticleCountByContent(content);
        return (totalCount - 1) / ARTICLE_PAGE_SIZE + 1;
    }

    public LinkedList<Map<String, Object>> searchContent(String content, int page, int userSeq) {
        ArrayList<ArticleListVo> searchResult = this.snsMapper.selectArticleByContent(content, ARTICLE_PAGE_SIZE, ARTICLE_PAGE_SIZE * (page - 1), userSeq);
        return createArticleMap(searchResult);
    }

    public int getSearchPlaceTotalPage(String place) {
        int totalCount = this.snsMapper.selectArticleCountByPlace(place);
        return (totalCount - 1) / ARTICLE_PAGE_SIZE + 1;
    }

    public LinkedList<Map<String, Object>> searchPlace(String place, int page, int userSeq) {
        ArrayList<ArticleListVo> searchResult = this.snsMapper.selectArticleByPlace(place, ARTICLE_PAGE_SIZE, ARTICLE_PAGE_SIZE * (page - 1), userSeq);
        return createArticleMap(searchResult);
    }

    public LinkedList<Map<String, Object>> traceArticle(int articleSeq) {
        ArrayList<TripPlaceDto> tripPlaceDtos = this.snsMapper.selectTripPlacesByArticle(articleSeq);
        LinkedList<Map<String, Object>> list = new LinkedList<>();
        for (TripPlaceDto tripPlaceDto : tripPlaceDtos) {
            Map<String, Object> map = new HashMap<>();
            map.put("image_url", tripPlaceDto.getImageUrl());
            map.put("trip_place_seq", tripPlaceDto.getTripPlaceSeq());
            map.put("place_name", tripPlaceDto.getPlaceName());
            list.add(map);
        }
        return list;
    }

    public Map<String, Object> collection(int userSeq, int page) {
        int totalPage = (int) Math.ceil((snsMapper.selectCollectionCountByUserSeq(userSeq)) / 10.0);
        if (totalPage == 0) {
            totalPage = 1;
        }
        if (page > totalPage) {
            return null;
        }
        ArrayList<Map<String, Object>> results = snsMapper.selectCollectionByUserSeq(userSeq, ARTICLE_PAGE_SIZE, (page - 1) * ARTICLE_PAGE_SIZE);
        Map<String, Object> collection = new HashMap<>();
        collection.put("total_page", totalPage);
        collection.put("page", page);
        collection.put("results", results);
        return collection;
    }

    public int blockUser(int userSeq, int blocker) {
        try {
            this.snsMapper.deleteFollow(blocker, userSeq);
            this.snsMapper.deleteLikeByAuthor(userSeq, blocker);
            return this.snsMapper.insertBlock(userSeq, blocker);
        } catch (Exception e) {
            return 0;
        }
    }

    public int unblockUser(int userSeq, int blocker) {
        try {
            return this.snsMapper.deleteBlock(userSeq, blocker);
        } catch (Exception e) {
            return 0;
        }
    }

    public LinkedList<Map<String, Object>> blockList(int userSeq) {
        return createBriefUserMap(this.snsMapper.selectBlockedUserByBlocker(userSeq));
    }

    public boolean updateArticle(int userSeq, int articleSeq, Map<String, Object> req) {
        // user가 article의 user가 맞는지 확인
        Integer articleUser = this.snsMapper.selectUserSeqByArticleSeq(articleSeq);
        if (articleUser != userSeq) {
            return false;
        }
        // article + trip update
        this.snsMapper.updateArticle(articleSeq, (String) req.get("title"), (String) req.get("tripComment"), (String) req.get("tripExpenses"), (int) req.get("rating"), (String) req.get("expose"));
        int tripSeq = this.snsMapper.selectTripSeqByArticleSeq(articleSeq);
        ArrayList<String> days = (ArrayList<String>) req.get("records");
        // days update
        int rowNum = 1;
        for (String dayComment : days) {
            this.snsMapper.updateDayComment(tripSeq, dayComment, rowNum);
            rowNum++;
        }
        return true;
    }

    public int closeArticle(int articleSeq, int userSeq) {
        if (this.snsMapper.selectArticleCountByArticleSeqUserSeq(articleSeq, userSeq) == 0) {
            return 0;
        }
        try {
            return this.snsMapper.updateArticleOpen(articleSeq, 'n');
        } catch (Exception e) {
            return 0;
        }
    }

    public int openArticle(int articleSeq, int userSeq) {
        if (this.snsMapper.selectArticleCountByArticleSeqUserSeq(articleSeq, userSeq) == 0) {
            return 0;
        }
        try {
            return this.snsMapper.updateArticleOpen(articleSeq, 'y');
        } catch (Exception e) {
            return 0;
        }
    }

    public LinkedList<Map<String, Object>> popularList() {
        return createBriefArticleMap(this.snsMapper.selectArticleByLike());
    }

    public boolean hide(int userSeq, int articleSeq) {
        this.snsMapper.deleteLike(articleSeq, userSeq);
        return this.snsMapper.insertHide(articleSeq, userSeq) != 0;
    }
}
