package ru.vsu.vsu_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.vsu.vsu_project.dto.item.ItemDto;
import ru.vsu.vsu_project.dto.item.ItemSlimDto;
import ru.vsu.vsu_project.dto.item.PageDto;
import ru.vsu.vsu_project.entity.Item;
import ru.vsu.vsu_project.entity.User;
import ru.vsu.vsu_project.repository.ItemRepository;
import ru.vsu.vsu_project.service.ItemService;
import ru.vsu.vsu_project.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final UserService userService;

    @Override
    public PageDto<List<ItemSlimDto>> getItems(Integer page, Integer size) {
        var slimDtos = itemRepository
                .findAll(PageRequest.of(page, size, Sort.by("id")))
                .map(this::toSlimDto);
        return new PageDto<>(slimDtos.getContent(), slimDtos.getTotalElements());
    }

    @Override
    public ItemDto getItem(Integer itemId) {
        return toItemDto(itemRepository.findById(itemId).get());
    }

    @Override
    public PageDto<List<ItemSlimDto>> getFavoriteItems(Integer page, Integer size) {
        var userId = getUserId();
        var slimDtos = itemRepository
                .findByUsers_Id(userId, PageRequest.of(page, size, Sort.by("id")))
                .map(this::toSlimDto);
        return new PageDto<>(slimDtos.getContent(), slimDtos.getTotalElements());
    }

    @Override
    public void changeFavorite(Integer itemId, Boolean isFavorite) {
        var item = itemRepository.findById(itemId).get();
        var userId = getUserId();
        var isUserHasFavorite = item.getUsers()
                .stream()
                .anyMatch((user) -> Objects.equals(user.getId(), userId));
        if (isUserHasFavorite && !isFavorite) {
            var user = item.getUsers()
                    .stream()
                    .filter(itemUser -> Objects.equals(itemUser.getId(), userId))
                    .findFirst()
                    .get();
            user.getFavoriteItems().remove(item);
//            item.getUsers().remove(user);
            item.setFavoriteCount(item.getFavoriteCount() - 1);
        } else if (!isUserHasFavorite && isFavorite) {
            var user = userService.getUserById(userId);
            user.getFavoriteItems().add(item);
//            item.getUsers().add(getUser());
            item.setFavoriteCount(item.getFavoriteCount() + 1);
        }
        itemRepository.save(item);
    }

    @Override
    public byte[] getImage(Integer itemId) {
        var item = itemRepository.findById(itemId).get();
        try (var inputStream = getClass().getResourceAsStream("/energy-drinks/" + item.getImgPath())) {
            assert inputStream != null;
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    ItemSlimDto toSlimDto(Item item) {
        var userId = getUserId();
        return ItemSlimDto.builder()
                .id(item.getId())
                .title(item.getTitle())
                .subTitle(item.getSubTitle())
                .isFavorite(item.getUsers().stream().anyMatch((user) -> Objects.equals(user.getId(), userId)))
                .build();
    }

    ItemDto toItemDto(Item item) {
        var userId = getUserId();
        return ItemDto.builder()
                .id(item.getId())
                .title(item.getTitle())
                .subTitle(item.getSubTitle())
                .isFavorite(item.getUsers().stream().anyMatch((user) -> Objects.equals(user.getId(), userId)))
                .description(item.getDescription())
                .favoriteCount(item.getUsers().size())
                .build();
    }

    private Integer getUserId() {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
}
