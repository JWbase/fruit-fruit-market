package com.shop.fruitfruit.web.admin.mapper;

import com.shop.fruitfruit.domain.admin.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {

    Admin findById(Admin admin);

}
