package com.ssafy.daero.service.service;

import com.ssafy.daero.service.dto.NoticeDto;
import com.ssafy.daero.service.mapper.ServiceMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class ServiceService {
    private final ServiceMapper serviceMapper;

    public ServiceService(ServiceMapper serviceMapper) { this.serviceMapper = serviceMapper; }

    public ArrayList<NoticeDto> noticeList() {
        ArrayList<NoticeDto> noticeList = serviceMapper.selectNoticeList();
        return noticeList;
    }
}
