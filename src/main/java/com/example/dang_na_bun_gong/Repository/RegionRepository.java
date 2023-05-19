package com.example.dang_na_bun_gong.Repository;

import com.example.dang_na_bun_gong.Entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<RegionEntity, Integer>, JpaSpecificationExecutor<RegionEntity> {

}