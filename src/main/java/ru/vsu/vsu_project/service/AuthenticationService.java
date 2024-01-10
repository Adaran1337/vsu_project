package ru.vsu.vsu_project.service;

import ru.vsu.vsu_project.dto.JwtAuthenticationResponse;
import ru.vsu.vsu_project.dto.SignUpRequest;
import ru.vsu.vsu_project.dto.SigninRequest;

public interface AuthenticationService {

    JwtAuthenticationResponse login(SigninRequest request);

    JwtAuthenticationResponse register(SignUpRequest request);
}
