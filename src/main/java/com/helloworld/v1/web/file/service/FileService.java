package com.helloworld.v1.web.file.service;

import com.helloworld.v1.web.file.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {
    private final S3Upload s3Upload;

    public FileUploadResponse upload(MultipartFile multipartFile) throws IOException {
        String fileUrl = s3Upload.upload(multipartFile);
        return new FileUploadResponse(true, "파일 업로드 성공", fileUrl);
    }

}
