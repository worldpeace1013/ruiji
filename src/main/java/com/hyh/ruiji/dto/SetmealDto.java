package com.hyh.ruiji.dto;

import com.hyh.ruiji.pojo.Setmeal;
import com.hyh.ruiji.pojo.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
