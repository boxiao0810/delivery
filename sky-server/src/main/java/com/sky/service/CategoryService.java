package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {
    void changeCategory(CategoryDTO categoryDTO);

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void enableOrDisable(Integer status, Long id);

    void addCategory(CategoryDTO categoryDTO );

    void deleteCategory(Long id);

    List<Category> queryCategory(Integer type);
}
