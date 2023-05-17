package org.zj.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zj.takeout.entity.Category;

public interface CategoryService extends IService<Category> {
    void delete(Long id);
}
