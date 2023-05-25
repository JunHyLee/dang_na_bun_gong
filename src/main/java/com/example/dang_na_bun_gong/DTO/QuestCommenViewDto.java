package com.example.dang_na_bun_gong.DTO;

import lombok.Data;

@Data
public class QuestCommenViewDto {

    String quest_title, quest_content_fp;

    public QuestCommenViewDto(QuestCommenDto questCommenDto){
        this.quest_title  = questCommenDto.getQuest_title();
        this.quest_content_fp = questCommenDto.getQuest_content_fp();
    }
}
