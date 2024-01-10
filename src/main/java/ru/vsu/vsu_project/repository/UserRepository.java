package ru.vsu.vsu_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.vsu_project.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
