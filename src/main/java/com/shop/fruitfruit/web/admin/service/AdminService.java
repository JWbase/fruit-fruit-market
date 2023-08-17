package com.shop.fruitfruit.web.admin.service;

import com.shop.fruitfruit.domain.admin.Admin;
import com.shop.fruitfruit.web.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final AdminRepository adminRepository;

    public Admin findById(Admin admin) {
        return adminRepository.findById(admin);
    }
}
