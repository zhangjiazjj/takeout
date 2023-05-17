package org.zj.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.zj.takeout.entity.Setmeal;
import org.zj.takeout.mapper.SetmealMapper;
import org.zj.takeout.service.SetmealService;
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
