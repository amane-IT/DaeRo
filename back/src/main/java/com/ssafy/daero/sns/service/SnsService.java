package com.ssafy.daero.sns.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.daero.sns.mapper.SnsMapper;
import com.ssafy.daero.sns.vo.ArticleVo;
import com.ssafy.daero.sns.vo.StampVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class SnsService {
    private final SnsMapper snsMapper;

    public SnsService(SnsMapper snsMapper) { this.snsMapper = snsMapper; }

    public Map<String, Object> articleDetail(int articleSeq) throws JsonProcessingException {

        ArticleVo articleVo = snsMapper.selectArticleAndTripInfoByArticleSeq(articleSeq);
        ArrayList<StampVo> stampVo = snsMapper.selectStampAndDayInfoByTripSeq(articleVo.getTripSeq());
        Map<String, String> userInfo = snsMapper.selectUserByUserSeq(articleVo.getUserSeq());
        ArrayList<Integer> tags = snsMapper.selectPlaceTagsByArticleSeq(articleSeq);
        Map<String, Object> articleDetail = new HashMap<>();

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
            snsMapper.deleteArticleByArticleSeq(articleSeq);
            return 1;
        }
        else {
            return 99;
        }
    }
}
