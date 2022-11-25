package com.helloworld.v1.web.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.helloworld.v1.common.exception.ApiException;
import com.helloworld.v1.common.exception.ExceptionEnum;
import com.helloworld.v1.common.security.util.SecurityUtil;
import com.helloworld.v1.domain.entity.User;
import com.helloworld.v1.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3Upload {

    private final UserRepository userRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public String upload(MultipartFile multipartFile) throws IOException {

        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());

        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
        String URI = amazonS3.getUrl(bucket, s3FileName).toString();

        String ext = URI.substring(URI.lastIndexOf(".") + 1);
        if (!ext.equals("png") && !ext.equals("jpg") && !ext.equals("jpeg")){
            throw new ApiException(ExceptionEnum.NOT_MATCH_EXT);
        }

        User findUser = SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_MEMBER));

        findUser.setProfileImage(URI);
        return URI;
    }

    public String update(MultipartFile multipartFile) throws IOException{
        User findUser = SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_MEMBER));

        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());

        String ext = s3FileName.substring(s3FileName.lastIndexOf(".") + 1);
        if (!ext.equals("png") && !ext.equals("jpg") && !ext.equals("jpeg")){
            throw new ApiException(ExceptionEnum.NOT_MATCH_EXT);
        }

        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
        String URI = amazonS3.getUrl(bucket, s3FileName).toString();

        findUser.setProfileImage(URI);
        return URI;
    }

    public String getProfileImage() {
        User findUser = SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_MEMBER));
        return findUser.getProfileImage();
    }
}