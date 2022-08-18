package com.ssafy.daero.image.service;

import com.ssafy.daero.image.vo.ImageVo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@SuppressWarnings("FieldCanBeLocal")
@Service
public class ImageService {
    private final String IMAGE_PATH = "/var/lib/tomcat9/webapps/upload/";
    private final String IMAGE_URL_PREFIX = "https://i7d110.p.ssafy.io/api/image/download/";
    private final String[] ALLOWED_IMAGE_MIMES = new String[]{"image/jpeg", "image/png"};

    public ImageVo uploadFile(MultipartFile multipartFile) {
        ImageVo imageVo = new ImageVo();
        if (multipartFile == null || multipartFile.getContentType() == null || Arrays.stream(ALLOWED_IMAGE_MIMES).noneMatch(multipartFile.getContentType()::equals)) {
            imageVo.setResult(ImageVo.ImageResult.BAD_REQUEST);
            return imageVo;
        }
        String file = multipartFile.getOriginalFilename();
        if (file == null) {
            imageVo.setResult(ImageVo.ImageResult.BAD_REQUEST);
            return imageVo;
        }
        if (file.contains("/")) {
            file = file.substring(file.lastIndexOf("/") + 1);
        }
        String fileName = file.substring(0, file.lastIndexOf("."));
        String fileExt = file.substring(file.lastIndexOf(".") + 1);

        File newFile = new File(IMAGE_PATH + file);
        int fileCount = 1;
        while (newFile.exists()) {
            newFile = new File(IMAGE_PATH + fileName + "-" + fileCount + "." +  fileExt);
            fileCount++;
        }

        try {
            multipartFile.transferTo(newFile);
        } catch (IOException e) {
            imageVo.setResult(ImageVo.ImageResult.SERVER_ERROR);
            return imageVo;
        }
        System.out.println("씨발 개새끼");
        imageVo.setDownloadUrl(IMAGE_URL_PREFIX + newFile.getName());
        imageVo.setResult(ImageVo.ImageResult.SUCCESS);
        return imageVo;
    }

    public ImageVo downloadFile(String fileName) {
        ImageVo imageVo = new ImageVo();
        if (fileName == null) {
            imageVo.setResult(ImageVo.ImageResult.BAD_REQUEST);
            return imageVo;
        }
        Path path = Paths.get(IMAGE_PATH + fileName);
        File file = new File(IMAGE_PATH + fileName);
        if (!file.exists()) {
            imageVo.setResult(ImageVo.ImageResult.FILE_NOT_FOUND);
            return imageVo;
        }
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
        String contentType = "image/" + (fileExt.equals("jpg") ? "jpeg" : fileExt);
        try {
            byte[] content = Files.readAllBytes(path);
            imageVo.setFile(new MockMultipartFile(fileName, fileName, contentType, content));
        } catch (IOException e) {
            imageVo.setResult(ImageVo.ImageResult.SERVER_ERROR);
            return imageVo;
        }
        imageVo.setResult(ImageVo.ImageResult.SUCCESS);
        return imageVo;
    }
}
