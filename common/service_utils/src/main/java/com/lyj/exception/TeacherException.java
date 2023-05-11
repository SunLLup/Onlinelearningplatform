package com.lyj.exception;

import com.lyj.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class TeacherException {

    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        return Result.fail(null).message("执行了全局异常");
    }

    @ExceptionHandler(MyException.class)
    public Result myerror(MyException e){
        return Result.fail(null).code(e.getCode()).message(e.getMsg());
    }

}
