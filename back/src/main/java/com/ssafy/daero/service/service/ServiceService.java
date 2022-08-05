package com.ssafy.daero.service.service;

import com.ssafy.daero.service.dto.FaqDto;
import com.ssafy.daero.service.dto.InquiryDto;
import com.ssafy.daero.service.dto.NoticeDto;
import com.ssafy.daero.service.mapper.ServiceMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServiceService {
    private final ServiceMapper serviceMapper;

    public ServiceService(ServiceMapper serviceMapper) { this.serviceMapper = serviceMapper; }

    public ArrayList<NoticeDto> noticeList() {
        ArrayList<NoticeDto> noticeList = serviceMapper.selectNoticeList();
        return noticeList;
    }

    public ArrayList<FaqDto> faqList() {
        ArrayList<FaqDto> faqList = serviceMapper.selectFaqList();
        return faqList;
    }

    public Map<String, Object> inquiryList(int userSeq, int page) {
        int totalPage = (int) Math.ceil((serviceMapper.selectInquiryCountByUserSeq(userSeq))/10.0);
        if (totalPage == 0) { totalPage = 1; }
        if (page > totalPage) { return null; }
        ArrayList<InquiryDto> results = serviceMapper.selectInquiryList(userSeq, page);
        Map<String, Object> inquiryList = new HashMap<>();
        inquiryList.put("total_page", totalPage);
        inquiryList.put("page", page);
        inquiryList.put("results", results);
        return inquiryList;
    }

    public boolean createInquiry(int userSeq, String title, String content) {
        int inserted = serviceMapper.insertInquiry(userSeq, title, content);
        return inserted == 1;
    }
}
