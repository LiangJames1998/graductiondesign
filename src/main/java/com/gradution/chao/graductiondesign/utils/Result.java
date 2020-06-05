package com.gradution.chao.graductiondesign.utils;

import java.io.Serializable;

public class Result<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    //初始化
    public Result(){

    }

    public Result(int code,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }



    public Result(T data){
        this.code=400;
        this.msg="success";
        this.data=data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T date) {
        this.data = date;
    }
}
