package com.helloworld.v1.web.test.service;

import com.helloworld.v1.domain.entity.User;
import com.helloworld.v1.domain.repository.UserRepository;
import com.helloworld.v1.web.test.dto.UserCreateRequest;
import com.helloworld.v1.web.test.dto.UserCreateResponse;
import com.helloworld.v1.web.test.dto.UserDto;
import com.helloworld.v1.web.test.dto.UsersResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    /**
     * 테스트 용 입니다.
     */

    private final UserRepository userRepository;

    @Transactional
    public UserCreateResponse createUser(UserCreateRequest userCreateRequest) {
        User user = userCreateRequest.toEntity();
        Long userId = userRepository.save(user).getId();
        log.info("유저가 생성되었습니다. ID: {}", userId.toString());
        return new UserCreateResponse(userId);
    }

    public UsersResponse getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map(i -> new UserDto(i.getId(), i.getUsername()))
                .collect(Collectors.toList());
        return new UsersResponse(userDtos);
    }
}
