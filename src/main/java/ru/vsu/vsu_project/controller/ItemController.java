package ru.vsu.vsu_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.vsu_project.dto.item.ItemDto;
import ru.vsu.vsu_project.dto.item.ItemSlimDto;
import ru.vsu.vsu_project.dto.item.PageDto;
import ru.vsu.vsu_project.service.ItemService;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/energy-drink")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<PageDto<List<ItemSlimDto>>> getItems(@RequestParam Integer page,
                                                               @RequestParam Integer size) {
        var response = itemService.getItems(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/favorite")
    public ResponseEntity<PageDto<List<ItemSlimDto>>> getFavoriteItems(@RequestParam Integer page,
                                                              @RequestParam Integer size) {
        var response = itemService.getFavoriteItems(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItem(@PathVariable Integer itemId) {
        var response = itemService.getItem(itemId);
        return ResponseEntity.ok(response);
    }

    @ResponseStatus(OK)
    @GetMapping("/{itemId}/change-favorite")
    public void changeItemFavorite(@PathVariable Integer itemId, @RequestParam Boolean isFavorite) {
        itemService.changeFavorite(itemId, isFavorite);
    }

    @GetMapping(value = "/{itemId}/image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getItemImage(@PathVariable Integer itemId) {
        var response = itemService.getImage(itemId);
        return ResponseEntity.ok(response);
    }
}
