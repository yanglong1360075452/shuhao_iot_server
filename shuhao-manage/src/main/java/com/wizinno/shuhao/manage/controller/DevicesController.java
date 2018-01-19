package com.wizinno.shuhao.manage.controller;

import com.wizinno.shuhao.condition.DeviceCondition;
import com.wizinno.shuhao.condition.UserCondition;
import com.wizinno.shuhao.controller.BaseController;
import com.wizinno.shuhao.data.PageDataVO;
import com.wizinno.shuhao.data.ResponseVO;
import com.wizinno.shuhao.device.data.DeviceDto;
import com.wizinno.shuhao.device.service.DeviceService;
import com.wizinno.shuhao.exception.CustomException;
import com.wizinno.shuhao.exception.CustomExceptionCode;
import com.wizinno.shuhao.user.data.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by HP on 2017/11/8.
 */

@RestController
@RequestMapping("/api/manage/devices")
public class DevicesController extends BaseController{

    @Autowired
    private  DeviceService deviceService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseVO getDevices(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                 @RequestParam(value = "length",defaultValue = "10") Integer length,
                                 @RequestParam(value = "phone",required = false)String phone,
                                 @RequestParam(value = "deviceId",required = false)Long deviceId)throws CustomException{

        if (page < 1 || length < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        DeviceCondition con = new DeviceCondition();
        con.setSize(length);
        con.setStart((page - 1) * length);
        if (phone != null && !phone.equals("")){
            con.setPhone(phone.trim());
        }

        if (deviceId != null && !deviceId.equals("")){
            con.setDeviceId(deviceId);
        }

        List<DeviceDto> dtos=deviceService.getDevices(con);
       Long total= deviceService.getDevicesTotal(con);

        return new PageDataVO(page,length,total,dtos);
    }

    /*
     *通过设备id获取设备详情
     * */
    @RequestMapping(value ="/{deviceId}" ,method = RequestMethod.GET)
    public ResponseVO getUserById(@PathVariable("deviceId")Long deviceId)throws CustomException{
        if (deviceId < 1){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        DeviceDto deviceDto = deviceService.deviceInfo(deviceId);
        if (deviceDto.getIsOnline() != null){
            if (deviceDto.getIsOnline() == 0){
                deviceDto.setIsOnlineDesc("离线");
            }else {
                deviceDto.setIsOnlineDesc("在线");
            }
        }

        return new ResponseVO(deviceDto);

    }

}
