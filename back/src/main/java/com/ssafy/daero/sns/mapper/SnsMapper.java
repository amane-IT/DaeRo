package com.ssafy.daero.sns.mapper;

import com.ssafy.daero.sns.vo.ArticleVo;
import com.ssafy.daero.sns.vo.StampVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface SnsMapper {

    ArticleVo selectArticleAndTripInfoByArticleSeq(int articleSeq);

    ArrayList<StampVo> selectStampAndDayInfoByTripSeq(int tripSeq);

    Map<String, String> selectUserByUserSeq(int userSeq);

    ArrayList<Integer> selectPlaceTagsByArticleSeq(int articleSeq);

    Integer selectUserSeqByArticleSeq(int articleSeq);

    Integer deleteArticleByArticleSeq(int articleSeq);


}
