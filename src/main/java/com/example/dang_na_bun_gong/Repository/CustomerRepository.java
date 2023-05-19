package com.example.dang_na_bun_gong.Repository;

import com.example.dang_na_bun_gong.Entity.NotionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<NotionEntity, Integer>, JpaSpecificationExecutor<NotionEntity> {

    //공지사항 목록
    Page<NotionEntity> findAll(Pageable pageable);
}
