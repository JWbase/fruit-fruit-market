package com.shop.fruitfruit.web.admin.repository;

import com.shop.fruitfruit.domain.admin.Admin;
import com.shop.fruitfruit.web.admin.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AdminRepository implements AdminMapper {

    private final AdminMapper adminMapper;

    @Override
    public Admin findById(Admin admin) {
        return adminMapper.findById(admin);
    }
}
