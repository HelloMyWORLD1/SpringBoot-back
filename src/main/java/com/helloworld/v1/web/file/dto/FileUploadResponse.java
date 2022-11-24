package com.helloworld.v1.web.file.dto;

import lombok.Data;

@Data
public class FileUploadResponse {
    private Boolean success;
    private String message;
    private String fileUrl;

    public FileUploadResponse(Boolean success, String message, String fileUrl) {
        this.success = success;
        this.message = message;
        this.fileUrl = fileUrl;
    }
}
