package com.helloworld.v1.web.file.controller;

import com.helloworld.v1.web.file.dto.FileGetResponse;
import com.helloworld.v1.web.file.dto.FileUpdateResponse;
import com.helloworld.v1.web.file.dto.FileUploadResponse;
import com.helloworld.v1.web.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Profile_Image")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FileUploadController {

    private final FileService fileService;

    @Operation(summary = "I1", description = "프로필 이미지 생성") // Swagger 표시
    @PostMapping("/profileImage/upload")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(
                fileService.upload(multipartFile));
    }

    @Operation(summary = "I2", description = "프로필 이미지 업데이트") // Swagger 표시
    @PostMapping("/profileImage/update")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<FileUpdateResponse> updateFile(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(
                fileService.update(multipartFile));
    }

    @Operation(summary = "I3", description = "프로필 이미지 조회") // Swagger 표시
    @GetMapping("/profileImage")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<FileGetResponse> getProfileImage() {
        return ResponseEntity.ok(
                fileService.getProfileImage());
    }
}
