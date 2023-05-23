package com.example.dang_na_bun_gong.DTO;

import lombok.Data;


@Data
public class QuestCommenListDto {
    Integer quest_id, quest_category_id;
    String quest_title, quest_content_fp;

    public QuestCommenListDto(QuestCommenDto questCommenDto){
        this.quest_id = questCommenDto.getQuest_id();
        this.quest_title  = questCommenDto.getQuest_title();
        this.quest_content_fp = questCommenDto.getQuest_content_fp();
        this.quest_category_id = questCommenDto.getQuest_category_id();
    }
}
