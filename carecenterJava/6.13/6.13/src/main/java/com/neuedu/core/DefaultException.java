package com.neuedu.core;

import com.neuedu.vo.ResultVo;
import org.apache.ibatis.jdbc.Null;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultException {
    @ExceptionHandler
    ResultVo<Null> defaultExceptionHandler(Exception ex) {
        ex.printStackTrace();
        if (ex instanceof NeueduException) {
            return  ResultVo.failed(ex.getMessage());
        }
        return ResultVo.failed("系统异常,请联系管理员");
    }
}
