package com.neuedu.vo;

import com.neuedu.core.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class ResultVo<T> {
    @ApiModelProperty("响应编码")
    private final Integer code;
    @ApiModelProperty("响应内容")
    private final T content;
    @ApiModelProperty("文字消息")
    private final String message;

    private ResultVo(Integer code, T content, String message) {
        this.code = code;
        this.content = content;
        this.message = message;
    }
    public static <T> ResultVo<T> getInstance(ResultCode resultCode, T content, String message) {
        return new ResultVo<>(resultCode.getValue(), content, message);
    }
    public static <T> ResultVo<T> success(T content, String message) {
        return getInstance(ResultCode.SUCCESS, content, message);
    }
    public static <T> ResultVo<T> success(T content) {
        return success(content, null);
    }
    public static <T> ResultVo<T> failed(T content, String message) {
        return getInstance(ResultCode.FAILED, content, message);
    }
    public static <T> ResultVo<T> failed(String message) {
        return failed(null, message);
    }
    public static ResultVo unlogin(String message) {
        return ResultVo.getInstance(ResultCode.UNLOGIN, null, message);
    }
    public static ResultVo forbid() {
        return ResultVo.getInstance(ResultCode.FORBID, null, "该用户无此操作权限");
    }
}
