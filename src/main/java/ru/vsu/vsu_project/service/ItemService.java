package ru.vsu.vsu_project.service;

import ru.vsu.vsu_project.dto.item.ItemDto;
import ru.vsu.vsu_project.dto.item.ItemSlimDto;
import ru.vsu.vsu_project.dto.item.PageDto;

import java.util.List;

public interface ItemService {

    PageDto<List<ItemSlimDto>> getItems(Integer page, Integer size);

    ItemDto getItem(Integer itemId);

    PageDto<List<ItemSlimDto>> getFavoriteItems(Integer page, Integer size);

    void changeFavorite(Integer itemId, Boolean isFavorite);

    byte[] getImage(Integer itemId);
}
