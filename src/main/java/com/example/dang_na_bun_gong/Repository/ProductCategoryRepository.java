package com.example.dang_na_bun_gong.Repository;

import com.example.dang_na_bun_gong.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<CategoryEntity, Integer>, JpaSpecificationExecutor<CategoryEntity> {

}
