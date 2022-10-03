package com.helloworld.v1.web.test.service;

import com.helloworld.v1.advice.exception.EmailLoginFailedException;
import com.helloworld.v1.advice.exception.EmailSignupFailedException;
import com.helloworld.v1.advice.exception.UserNotFoundException;
import com.helloworld.v1.domain.entity.User;
import com.helloworld.v1.domain.repository.UserRepository;
import com.helloworld.v1.web.test.dto.UserLoginResponseDto;
import com.helloworld.v1.web.test.dto.UserRequestDto;
import com.helloworld.v1.web.test.dto.UserResponseDto;

import com.helloworld.v1.web.test.dto.UserSignupRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Long save(UserRequestDto userDto) {
        User saved = userRepository.save(userDto.toEntity());
        return saved.getUserId();
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUser() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());

    }

    @Transactional
    public Long update(Long id, UserRequestDto userRequestDto) {
        User modifiedUser = userRepository
                .findById(id).orElseThrow(UserNotFoundException::new);
        modifiedUser.updateNickName(userRequestDto.getNickName());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserLoginResponseDto login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(EmailLoginFailedException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new EmailLoginFailedException();
        return new UserLoginResponseDto(user);
    }

    @Transactional
    public Long signup(UserSignupRequestDto userSignupDto) {
        User user = userRepository.findByEmail(userSignupDto.getEmail()).orElse(null);
        if (user == null) return userRepository.save(userSignupDto.toEntity()).getUserId();
        else throw new EmailSignupFailedException();
    }
}
