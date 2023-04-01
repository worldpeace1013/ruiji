package com.hyh.ruiji.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyh.ruiji.Mapper.CategoryMapper;
import com.hyh.ruiji.Service.CategoryService;
import com.hyh.ruiji.Service.DishService;
import com.hyh.ruiji.Service.SetmealService;
import com.hyh.ruiji.common.CustomException;
import com.hyh.ruiji.pojo.Category;
import com.hyh.ruiji.pojo.Dish;
import com.hyh.ruiji.pojo.Setmeal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryServiceimpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long ids) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getCategoryId,ids);
        int count = dishService.count(wrapper);
        if (count>0){
            throw new CustomException("当前分类下关联的菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Setmeal::getCategoryId,ids);
        int count1 = setmealService.count(wrapper1);
        if (count1>0){
            throw new CustomException("当前分类下关联的套餐，不能删除");
        }

        super.removeById(ids);

    }
}
