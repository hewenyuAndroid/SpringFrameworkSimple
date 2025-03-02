package com.example.restful_crud.commen;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class R<T> {

    private Integer code;
    private String msg;
    private T data;

    public static <T> R<T> ok() {
        return new R<T>()
                .setCode(200)
                .setMsg("success");
    }

    public static <T> R<T> ok(T data) {
        return new R<T>()
                .setCode(200)
                .setMsg("success")
                .setData(data);
    }

    public static <T> R<T> error() {
        return new R<T>()
                .setCode(100)
                .setMsg("failure");
    }

    public static <T> R<T> error(Integer code, String msg) {
        return new R<T>()
                .setCode(code)
                .setMsg(msg);
    }

    public static <T> R<T> error(Integer code, String msg, T data) {
        return new R<T>()
                .setCode(code)
                .setMsg(msg)
                .setData(data);
    }

}
