package com.example.dang_na_bun_gong.Entity;

import com.example.dang_na_bun_gong.DTO.QuestPrivateWriteDto;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "quest_private")
@Data
public class QuestPrivateEntity {
    @Id
    @Column(name = "quest_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questId;

    @Column(name = "questioner_id", nullable = false)
    private  String questionerId;

    @Column(name = "quest_title", nullable = false)
    private String questTitle;

    @Column(name = "quest_content_fp")
    private String questContentFp;

    @Column(name = "quest_category_id")
    private Integer questCategoryId;
}
