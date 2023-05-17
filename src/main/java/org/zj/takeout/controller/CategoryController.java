package org.zj.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.zj.takeout.common.CategoryDeleteException;
import org.zj.takeout.common.R;
import org.zj.takeout.entity.Category;
import org.zj.takeout.service.CategoryService;

import java.util.function.LongFunction;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping
    public R<String> addCategory(@RequestBody Category category){
        boolean save = categoryService.save(category);
        if (save){
            return R.success("新增菜品成功");
        }
        return R.error("未知错误");
    }

    @GetMapping("/page")
    public R<Page> selectPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize){
//        分页构造器
        Page<Category> page1 = new Page<>(page,pageSize);
//        条件构造器
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
//        查询
        categoryService.page(page1,wrapper);
        return R.success(page1);

    }

    @DeleteMapping
    public R<String> delete(@RequestParam("ids") Long id){
//        boolean b = categoryService.removeById(id);
        categoryService.delete(id);
        return R.success("删除成功");

    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        boolean b = categoryService.updateById(category);
        if (b){
            return R.success("修改成功");
        }
        return R.error("修改失败");
    }

}
