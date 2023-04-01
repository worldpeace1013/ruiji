package com.hyh.ruiji.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyh.ruiji.dto.DishDto;
import com.hyh.ruiji.pojo.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithDishFlavor(long id);

    public void updateWithCategoryWithDishFlavor(DishDto dishDto);

    public List<Dish> getByCategoryId(Long categoryId);
}
