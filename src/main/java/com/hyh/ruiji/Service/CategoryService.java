package com.hyh.ruiji.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyh.ruiji.pojo.Category;

public interface CategoryService extends IService<Category> {

    public void remove(Long ids);

}
