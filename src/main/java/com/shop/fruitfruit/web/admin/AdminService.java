package com.shop.fruitfruit.web.admin;

import com.shop.fruitfruit.domain.admin.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService implements AdminMapper {

    private final AdminMapper adminMapper;

    @Override
    public Admin findById(Admin admin) {
        return adminMapper.findById(admin);
    }
}
