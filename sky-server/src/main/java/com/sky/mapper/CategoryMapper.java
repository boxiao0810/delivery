package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @AutoFill(value = OperationType.UPDATE)
    void changeCategory(Category category);

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    @AutoFill(value = OperationType.UPDATE)
    void changeCategoryStatus(Category category);

    @AutoFill(value = OperationType.INSERT)
    void addCategory(Category category);

    void deleteCategory(Long id);

    List<Category> queryCategory(Integer type);
}
