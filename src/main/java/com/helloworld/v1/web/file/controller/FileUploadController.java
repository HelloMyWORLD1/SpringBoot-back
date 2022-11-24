package com.helloworld.v1.web.file.controller;

import com.helloworld.v1.web.file.dto.FileUploadResponse;
import com.helloworld.v1.web.file.service.FileService;
import com.helloworld.v1.web.file.service.S3Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FileUploadController {

    private final FileService fileService;
    private final S3Upload s3Upload;

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(
                fileService.upload(multipartFile));
    }
}
