package com.hyh.ruiji.dto;

import com.hyh.ruiji.pojo.Dish;
import com.hyh.ruiji.pojo.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
