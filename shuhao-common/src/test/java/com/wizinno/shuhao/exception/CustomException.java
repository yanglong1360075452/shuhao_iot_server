package com.wizinno.shuhao.exception;

/**
 * Created by HP on 2016/6/24.
 */
public class CustomException extends RuntimeException{

    private Integer code;
    private String data;

    public CustomException(Integer code){
        super(code.toString(), null);
        this.code = code;
    }

    public CustomException(Integer code, String data) {
        super(code.toString());
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
