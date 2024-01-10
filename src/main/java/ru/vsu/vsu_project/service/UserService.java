package ru.vsu.vsu_project.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.vsu.vsu_project.entity.User;

public interface UserService {

    UserDetailsService userDetailsService();

    User getUserById(Integer id);
}
