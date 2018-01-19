package com.wizinno.shuhao.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 2016/6/24.
 */
public class CustomExceptionCode {
    private final static Map<Integer, String> errorMap = new HashMap<>();



    static {


    }

    public static String getReasonByCode(Integer code, String defaultReason){
        if(errorMap.containsKey(code)){
            return errorMap.get(code);
        }else{
            return defaultReason;
        }
    }
}
