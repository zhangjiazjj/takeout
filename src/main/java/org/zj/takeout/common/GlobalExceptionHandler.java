package org.zj.takeout.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器
 */

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception){
        log.info(exception.getMessage());

        if (exception.getMessage().contains("Duplicate entry")){
            String[] s = exception.getMessage().split(" ");
            return R.error(s[2]);
        }
        return R.error("未知错误");
    }

    @ExceptionHandler(CategoryDeleteException.class)
    public R<String> exceptionHandler(CategoryDeleteException exception){
        return R.error(exception.getMessage());
    }
}

