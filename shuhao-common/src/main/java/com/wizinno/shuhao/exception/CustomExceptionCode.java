package com.wizinno.shuhao.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 2016/6/24.
 */
public class CustomExceptionCode {
    public static final Integer PhoneIsRegister= -1;
    public static final Integer ErrorCode = -2;
    public static final Integer UserNoExit= -3;
    public static final Integer PasswordError=-4;
    public static final Integer AuthFailed = -5;
    public static final Integer DeviceNoExit = -6;
    public static final Integer OldPasswordError = -7;
    public static final Integer SendError=-8;
    public static final Integer ErrorParam = -10;
    public static final Integer NoAppVer = -11;
    public static final Integer NoATCMD = -12;
    public static final Integer UsernameOrPasswordError=-4;
    public static final Integer AccessDenied = 1;


    private final static Map<Integer, String> errorMap = new HashMap<>();
    static {
        errorMap.put(-12,"ATCmd命令为空");
        errorMap.put(-11,"没有任何app版本");
        errorMap.put(-10,"参数错误");
        errorMap.put(-8, "发送命令失败");
        errorMap.put(-7, "原密码验证失败");
        errorMap.put(-5, "身份验证失败");
        errorMap.put(-6, "设备不存在");
        errorMap.put(-4, "密码错误");
        errorMap.put(-3,"用户不存在");
        errorMap.put(-2,"验证码错误");
        errorMap.put(-1,"手机号已注册");
        errorMap.put(1, "权限不足");


    }

    public static String getReasonByCode(Integer code, String defaultReason){
        if(errorMap.containsKey(code)){
            return errorMap.get(code);
        }else{
            return defaultReason;
        }
    }
}
