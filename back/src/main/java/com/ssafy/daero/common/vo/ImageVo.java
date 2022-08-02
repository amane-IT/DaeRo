package com.ssafy.daero.common.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageVo {
    public enum ImageResult {
        SUCCESS, BAD_REQUEST, SERVER_ERROR, FILE_NOT_FOUND,
    }
    private String downloadUrl;
    private ImageResult result;
    private MultipartFile file;
    private byte[] bytes;
}
