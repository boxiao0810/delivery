package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO){

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //insert one dish to the menu
        dishMapper.insert(dish);
        //insert n flavors to the dish
        List<DishFlavor> flavor = dishDTO.getFlavors();

        Long dishId = dish.getId();

        if (flavor != null && !flavor.isEmpty()) {
            flavor.forEach(d -> {
                d.setDishId(dishId);
            });

            dishFlavorMapper.insert(flavor);
        }


    }
}
