package com.wizinno.shuhao.app.utils;
import com.wizinno.shuhao.data.LogOperation;
import com.wizinno.shuhao.user.data.LogDto;
import com.wizinno.shuhao.user.data.UserDto;
import com.wizinno.shuhao.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2017/7/6.
 */
@Component
public  class Path {
@Autowired
private UserService userService;

    private static Path path;
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct     //关键二   通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作
    public void init() {
        path = this;
        path.userService = this.userService;   // 初使化时将已静态化的userRepository实例化

    }
    public static void pathUrl(String allurl, String url, UserDto user, HttpServletRequest request,
                               String parameter, int statue , String responseMessage) {
        Map map = new HashMap();
        if (parameter != null) {
            String[] para = parameter.split("&");
            for (int i = 0; i < para.length; i++) {
                String[] pa = para[i].split("=");
                map.put(pa[0], pa[1]);
            }
        }
        if (user == null) {
            if (url.equals("/api/app/user/login")) {
                String phone = (String) map.get("phone");
                UserDto userDto = path.userService.findByPhone(phone);
                if (userDto.getUserId() != null) {
                   saveLog(userDto,request,url,allurl,responseMessage, LogOperation.login.toString());
                }
            } else if (url.equals("/api/app/user/getAuthCode")) {
                String phone = (String) map.get("phone");
                UserDto userDto = path.userService.findByPhone(phone);
                if (userDto.getUserId() != null) {
                    saveLog(userDto, request, url, allurl, responseMessage,LogOperation.getCode.toString());
                }
            }else if (url.equals("/api/app/user/register")) {
                String phone = (String) map.get("phone");
                UserDto userDto = path.userService.findByPhone(phone);
                if (userDto.getUserId() != null) {
                    saveLog(userDto, request, url, allurl, responseMessage,LogOperation.register.toString());
                }
                } else if (url.equals("/api/app/user/resetPassword")) {
                String phone = (String) map.get("phone");
                UserDto userDto = path.userService.findByPhone(phone);
                if (userDto.getUserId() != null) {
                    saveLog(userDto, request, url, allurl, responseMessage,LogOperation.resetPassword.toString());
                }
                }  else {
                LogDto logDto=new LogDto();
                logDto.setOpTime(new Date());
                logDto.setIpAddr(Util.getIpAddr(request));
                logDto.setUrl(url);
                logDto.setRequest(allurl);
                logDto.setResponse(responseMessage);
                path.userService.saveLog(logDto);
            }


        }else{
            if(url.equals("/api/app/user/userInfo")){
                saveLog(user, request, url, allurl, responseMessage,LogOperation.userInfo.toString());

            }else if(url.equals("/api/app/user/setPassword")){
                saveLog(user, request, url, allurl, responseMessage,LogOperation.setPassword.toString());
            }else if(url.equals("/api/app/user/getDeviceInfo")){
                saveLog(user, request, url, allurl, responseMessage,LogOperation.getDeviceInfo.toString());
            }else if(url.equals("/api/app/user/bindDevice")){
                saveLog(user, request, url, allurl, responseMessage,LogOperation.bindDevice.toString());
            }else if(url.equals("/api/app/user/unbindDevice")){
                saveLog(user, request, url, allurl, responseMessage,LogOperation.unbindDevice.toString());
            }
            else if(url.equals("/api/app/user/getDeviceData")){
                if(responseMessage.getBytes().length<1024){
                    saveLog(user, request, url, allurl, responseMessage,LogOperation.getDeviceData.toString());
                }else{
                    responseMessage="数据量过大********";
                    saveLog(user, request, url, allurl, responseMessage,LogOperation.getDeviceData.toString());
                }
            }
            else if(url.equals("/api/app/user/getFriendList")){
                saveLog(user, request, url, allurl, responseMessage,LogOperation.getFriendList.toString());
            }else if(url.equals("/api/app/user/addFriend")){
                saveLog(user, request, url, allurl, responseMessage,LogOperation.addFriend.toString());
            }else if(url.equals("/api/app/user/deleteFriend")){
                saveLog(user, request, url, allurl, responseMessage,LogOperation.deleteFriend.toString());
            }else if(url.equals("/api/app/user/myFriendsSharedDevices")){
                saveLog(user, request, url, allurl, responseMessage,LogOperation.myFriendsSharedDevices.toString());
            }else if(url.equals("/api/app/user/mySharedDevices")){
                saveLog(user, request, url, allurl, responseMessage,LogOperation.mySharedDevices.toString());
            }else if(url.equals("/api/app/user/shareDevice")){
                saveLog(user, request, url, allurl, responseMessage,LogOperation.shareDevice.toString());
            }else if(url.equals("/api/app/user/cancelShare")){
                saveLog(user, request, url, allurl, responseMessage,LogOperation.cancelShare.toString());
            }else if(url.equals("/api/app/user/sendCmd")){
                saveLog(user, request, url, allurl, responseMessage,LogOperation.sendCmd.toString());
            }
        }
    }
    public static void saveLog(UserDto userDto,HttpServletRequest request,String url,String allurl,String responseMessage,String event){
        LogDto logDto=new LogDto();
        logDto.setOpTime(new Date());
        logDto.setUserId(userDto.getUserId());
        logDto.setIpAddr(Util.getIpAddr(request));
        logDto.setUrl(url);
        logDto.setEvent(event);
        logDto.setRequest(allurl);
        logDto.setResponse(responseMessage);
        path.userService.saveLog(logDto);
    }

}
