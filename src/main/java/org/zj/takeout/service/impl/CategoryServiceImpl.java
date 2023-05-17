package org.zj.takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zj.takeout.common.CategoryDeleteException;
import org.zj.takeout.entity.Category;
import org.zj.takeout.entity.Dish;
import org.zj.takeout.entity.Setmeal;
import org.zj.takeout.mapper.CategoryMapper;
import org.zj.takeout.service.CategoryService;
import org.zj.takeout.service.DishService;
import org.zj.takeout.service.SetmealService;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    DishService dishService;

    @Autowired
    SetmealService setmealService;

    @Override
    public void delete(Long id) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getCategoryId,id);

        int count1 = dishService.count(wrapper);

        LambdaQueryWrapper<Setmeal> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(wrapper1);

        if (count1>0 || count2>0){
            throw new CategoryDeleteException("当前分类关联了菜品");
        }

        super.removeById(id);

    }
}
