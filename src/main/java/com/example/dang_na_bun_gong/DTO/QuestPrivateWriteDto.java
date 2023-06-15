package com.example.dang_na_bun_gong.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestPrivateWriteDto {
    private Integer questId;
    private String questTitle;
    private String questContent;
    private Integer questCateogoryId;
}