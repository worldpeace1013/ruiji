package com.hyh.ruiji.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyh.ruiji.dto.DishDto;
import com.hyh.ruiji.dto.SetmealDto;
import com.hyh.ruiji.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    public void saveSetmeal(SetmealDto setmealDto);



}
