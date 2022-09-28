package com.helloworld.v1.security;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/security")
public class SecurityController {
    private final SecurityService securityService;

    @GetMapping("/create/token")
    public Map<String, Object> createToken(@RequestParam(value = "subject") String subject){
        String token = securityService.createToken(subject, 2 * 60 * 1000);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("result", token);
        return map;
    }

    @GetMapping("/get/subject")
    public Map<String, Object> getSubject(@RequestParam(value = "token") String token){
        String subject = securityService.getSubject(token);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("result", subject);
        return map;
    }
}
