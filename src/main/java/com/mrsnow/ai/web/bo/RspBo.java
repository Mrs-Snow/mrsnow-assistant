package com.mrsnow.ai.web.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * @Author dongzhen
 * @CreateTime: 2024-12-13  12:48
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RspBo<T> {
    T data;
    int code;
    String msg;

    public RspBo(T data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }
    public RspBo<T> success(){
        this.code = 0;
        this.msg = "success";
        return this;
    }

    public RspBo<T> success(T data,String message,int code){
        this.code = code;
        this.msg = message;
        this.data = data;
        return this;
    }
    public RspBo<T> failed(){
        this.code = -1;
        this.msg = "success";
        return this;
    }


    public RspBo<T> failed(T data,String message,int code){
        this.code = code;
        this.msg = message;
        this.data = data;
        return this;
    }

    public static <T> RspBo<T> success(T data){
        return new RspBo<T>(data,0,"success");
    }
    public static <T> RspBo<T> success(T data,String message){
        return new RspBo<T>(data,0,message);
    }

    public static <T> RspBo<T> failed(T data){
        return new RspBo<T>(data,-1,"failed");
    }
    public static <T> RspBo<T> failed(String message){
        return new RspBo<T>(null,-1,message);
    }
    public static <T> RspBo<T> failed(T data,String message){
        return new RspBo<T>(data,-1,message);
    }

}
