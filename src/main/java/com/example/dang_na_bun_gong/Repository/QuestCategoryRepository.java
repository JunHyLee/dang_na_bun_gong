package com.example.dang_na_bun_gong.Repository;

import com.example.dang_na_bun_gong.Entity.QuestCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestCategoryRepository extends JpaRepository<QuestCategoryEntity, Integer>, JpaSpecificationExecutor<QuestCategoryEntity> {
    
}
