package com.hyh.ruiji.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyh.ruiji.Service.CategoryService;
import com.hyh.ruiji.Service.SetmealDishService;
import com.hyh.ruiji.Service.SetmealService;
import com.hyh.ruiji.common.R;
import com.hyh.ruiji.dto.DishDto;
import com.hyh.ruiji.dto.SetmealDto;
import com.hyh.ruiji.pojo.Category;
import com.hyh.ruiji.pojo.Setmeal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveSetmeal(setmealDto);
        return R.success("成功");
    }

    @GetMapping("/page")
    @Transactional
    public R<Page> getPage(Long page,Long pageSize,String name){
        Page<Setmeal> setmealPage = new Page<>();
        Page<SetmealDto> setmealDtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(name!=null,Setmeal::getName,name);
        setmealService.page(setmealPage,setmealLambdaQueryWrapper);
        BeanUtils.copyProperties(setmealPage,setmealDtoPage,"records");
        List<Setmeal> records = setmealPage.getRecords();
        List<SetmealDto> list = records.stream().map((hyh)->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(hyh,setmealDto);
            setmealDto.setCategoryName(categoryService.getById(hyh.getCategoryId()).getName());
            return setmealDto;
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(list);
        return R.success(setmealDtoPage);
    }

    @DeleteMapping
    public R<String> deleteByIds(@RequestParam ArrayList<Long> ids){
        setmealService.removeByIds(ids);
        return R.success("删除成功");
    }

}
