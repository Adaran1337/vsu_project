package ru.vsu.vsu_project.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ItemSlimDto {

    private Integer id;

    private String title;

    private String subTitle;

    private Boolean isFavorite;
}
