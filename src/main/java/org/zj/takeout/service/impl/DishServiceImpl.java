package org.zj.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.zj.takeout.entity.Dish;
import org.zj.takeout.mapper.DishMapper;
import org.zj.takeout.service.DishService;
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
