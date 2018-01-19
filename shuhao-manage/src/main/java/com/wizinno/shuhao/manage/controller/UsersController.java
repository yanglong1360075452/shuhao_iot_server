package com.wizinno.shuhao.manage.controller;

import com.wizinno.shuhao.condition.UserCondition;
import com.wizinno.shuhao.controller.BaseController;
import com.wizinno.shuhao.data.PageDataVO;
import com.wizinno.shuhao.data.ResponseVO;
import com.wizinno.shuhao.device.data.DeviceDto;
import com.wizinno.shuhao.exception.CustomException;
import com.wizinno.shuhao.exception.CustomExceptionCode;
import com.wizinno.shuhao.user.data.UserDto;
import com.wizinno.shuhao.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * Created by HP on 2017/11/6.
 */

@RestController
@RequestMapping("/api/manage/users")
public class UsersController extends BaseController {

    @Autowired
    private UserService userService;

    /*
    * 获取用户列表
    * */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseVO getUsers(@RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "length",defaultValue = "10") Integer length,
                               @RequestParam(value = "phone",required = false)String phone,
                               @RequestParam(value = "userId",required = false)Long userId)throws CustomException{
        if (page < 1 || length < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        UserCondition con = new UserCondition();
        con.setSize(length);
        con.setStart((page - 1) * length);
        if (phone != null && !phone.equals("")){
            con.setPhone(phone.trim());
        }

        if (userId != null && !userId.equals("")){
            con.setUserId(userId);
        }

       List<UserDto> users= userService.getUsers(con);
      Long total=  userService.getUsersTotal(con);
        return new PageDataVO(page,length,total,users);

    }


    /*
    *通过id获取用户详情
    * */
    @RequestMapping(value ="/{userId}" ,method = RequestMethod.GET)
    public ResponseVO getUserById(@PathVariable("userId")Long userId)throws CustomException{
        if (userId < 1){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        UserDto user = userService.findById(userId);

        return new ResponseVO(user);

    }

    /*
    * 获取用户的设备列表
    * */
    @RequestMapping(value = "/devices",method = RequestMethod.GET)
    public ResponseVO getDevicesByUserId(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                         @RequestParam(value = "length",defaultValue = "10") Integer length,
                                         @RequestParam(value = "userId")Long userId)throws CustomException{
        if (userId < 1){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        if (page < 1 || length < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        UserCondition con = new UserCondition();
        con.setSize(length);
        con.setStart((page - 1) * length);
        con.setUserId(userId);

        List<DeviceDto> dtos =userService.getDevicesByUserId(con);
        Long total =userService.getDevicesByUserIdTotal(con);


        return new PageDataVO(page,length,total,dtos);
    }


    /*
     * 获取用户的好友列表
     * * */
    @RequestMapping(value = "/friends",method = RequestMethod.GET)
    public ResponseVO getFriendsByUserId(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                         @RequestParam(value = "length",defaultValue = "10") Integer length,
                                         @RequestParam(value = "userId")Long userId)throws CustomException{
        if (userId < 1){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        if (page < 1 || length < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        UserCondition con = new UserCondition();
        con.setSize(length);
        con.setStart((page - 1) * length);
        con.setUserId(userId);

        List<UserDto> dtos =userService.getFriendsByUserId(con);
        Long total =userService.getFriendsByUserIdTotal(con);


        return new PageDataVO(page,length,total,dtos);
    }



    /*
     * 好友分享的设备
     * */
    @RequestMapping(value = "/friendDevices",method = RequestMethod.GET)
    public ResponseVO getFriendDevicesShares(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                         @RequestParam(value = "length",defaultValue = "10") Integer length,
                                         @RequestParam(value = "userId")Long userId)throws CustomException{
        if (userId < 1){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        if (page < 1 || length < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        UserCondition con = new UserCondition();
        con.setSize(length);
        con.setStart((page - 1) * length);
        con.setUserId(userId);

        List<DeviceDto> dtos =userService.getFriendDevicesShares(con);
        Long total =userService.getFriendDevicesSharesTotal(con);


        return new PageDataVO(page,length,total,dtos);
    }
}
