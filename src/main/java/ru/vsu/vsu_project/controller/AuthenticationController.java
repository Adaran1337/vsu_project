package ru.vsu.vsu_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.vsu_project.dto.JwtAuthenticationResponse;
import ru.vsu.vsu_project.dto.SignUpRequest;
import ru.vsu.vsu_project.dto.SigninRequest;
import ru.vsu.vsu_project.service.AuthenticationService;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody SigninRequest request) {
        var response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> register(@RequestBody SignUpRequest request) {
        var response = authenticationService.register(request);
        return ResponseEntity.ok(response);
    }
}
