package ru.vsu.vsu_project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.vsu_project.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    Page<Item> findByUsers_Id(Integer id, Pageable pageable);
}
