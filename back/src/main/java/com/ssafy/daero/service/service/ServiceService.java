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

    public ArrayList<InquiryDto> inquiryList(int userSeq) {
        ArrayList<InquiryDto> inquiryList = serviceMapper.selectInquiryList(userSeq);
        return inquiryList;
    }

    public boolean createInquiry(int userSeq, String title, String content) {
        int inserted = serviceMapper.insertInquiry(userSeq, title, content);
        return inserted == 1;
    }
}
