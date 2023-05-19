package com.example.dang_na_bun_gong.Service;

import com.example.dang_na_bun_gong.Entity.NotionEntity;
import com.example.dang_na_bun_gong.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Page<NotionEntity> notionList(Pageable pageable){
        return customerRepository.findAll(pageable);
    }
}
