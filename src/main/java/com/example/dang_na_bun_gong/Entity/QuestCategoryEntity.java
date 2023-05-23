package com.example.dang_na_bun_gong.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "quest_category")
public class QuestCategoryEntity {

    @Id
    @Column(name = "quest_category_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quest_category_id;

    @Column(name = "quest_category_name", nullable = false)
    private String q_category_name;
}
