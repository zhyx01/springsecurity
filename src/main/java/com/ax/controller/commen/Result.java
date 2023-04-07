package com.ax.controller.commen;

import lombok.Data;

/**
 * className: Result
 * description: 统一响应结果集
 *
 * @author: axiang
 * date: 2023/4/6 9:16
 */
@Data
public class Result<T> {

    private Integer code;
    private String msg;
    private T data;

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
