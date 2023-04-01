package com.hyh.ruiji.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyh.ruiji.Service.CategoryService;
import com.hyh.ruiji.Service.DishFlavorService;
import com.hyh.ruiji.Service.DishService;
import com.hyh.ruiji.common.R;
import com.hyh.ruiji.dto.DishDto;
import com.hyh.ruiji.pojo.Category;
import com.hyh.ruiji.pojo.Dish;
import com.hyh.ruiji.pojo.DishFlavor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name),Dish::getName,name);
        dishService.page(pageInfo,wrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((hyh)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(hyh,dishDto);
            Long categoryId = hyh.getCategoryId();
            Category category = categoryService.getById(categoryId);
            String name1 = category.getName();
            dishDto.setCategoryName(name1);
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        System.out.println(dishDto);
        dishService.saveWithFlavor(dishDto);
        return R.success("成功");
    }

    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id){
        return R.success(dishService.getByIdWithDishFlavor(id));
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithCategoryWithDishFlavor(dishDto);
        return R.success("成功");
    }

    @GetMapping("/list")
    public R<List<Dish>> getByCategory(Long categoryId){
        return R.success(dishService.getByCategoryId(categoryId));
    }

}
