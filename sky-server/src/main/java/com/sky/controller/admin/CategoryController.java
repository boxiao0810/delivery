package com.sky.controller.admin;



import com.fasterxml.jackson.databind.ser.Serializers;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "Category related interface")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PutMapping
    @ApiOperation("change category")
    public Result<T> changeCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.changeCategory(categoryDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("category page query")
    public Result<PageResult> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageResult page = categoryService.pageQuery(categoryPageQueryDTO);

        return Result.success(page);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("enable/disable category")
    public Result<T> enableOrDisable(@PathVariable("status") Integer status, @RequestParam("id") Long id) {
        categoryService.enableOrDisable(status, id);
        return Result.success();
    }

    @PostMapping
    @ApiOperation("Add category")
    public Result<T> addCategory(@RequestBody CategoryDTO categoryDTO) {
        System.out.println(categoryDTO.getName());
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("Delete category")
    public Result<T> deleteCategory(@RequestParam Long id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("Query category")
    public Result<List<Category>> queryCategory(@RequestParam Integer type) {
        System.out.println("typessssss" + type);
        List<Category> result = categoryService.queryCategory(type);
        return Result.success(result);
    }
}
