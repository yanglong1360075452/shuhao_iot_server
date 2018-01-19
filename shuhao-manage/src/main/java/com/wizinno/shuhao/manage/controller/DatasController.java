package com.wizinno.shuhao.manage.controller;

import com.wizinno.shuhao.condition.DataCondition;
import com.wizinno.shuhao.controller.BaseController;
import com.wizinno.shuhao.data.DataDto;
import com.wizinno.shuhao.data.PageDataVO;
import com.wizinno.shuhao.data.ResponseVO;
import com.wizinno.shuhao.exception.CustomException;
import com.wizinno.shuhao.exception.CustomExceptionCode;
import com.wizinno.shuhao.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by HP on 2017/11/7.
 */

@RestController
@RequestMapping("/api/manage/data")
public class DatasController extends BaseController {

    @Autowired
    private DataService dataService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseVO getDatasManage(@RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "length",defaultValue = "10") Integer length,
                               @RequestParam(value = "startTime",required = false)Long startTime,
                               @RequestParam(value = "endTime",required = false)Long endTime,
                               @RequestParam(value = "deviceId",required = false)Long deviceId)throws CustomException{

        if (page < 1 || length < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }

        DataCondition con = new DataCondition();
        con.setStart((page -1) * length);
        con.setSize(length);

        if (deviceId != null && !deviceId.equals("")){
            con.setDeviceId(deviceId);
        }

        if (startTime != null){
            if (startTime != 0){
                con.setStartTime(new Date(startTime));
            }

        }
        if (endTime != null){
            if (endTime != 0){
                Calendar calendar   =   new GregorianCalendar();
                calendar.setTime(new Date(endTime));
                calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动//这个时间就是日期往后推一天的结果
                con.setEndTime(calendar.getTime());
            }
        }
        List<DataDto> dtos=dataService.getDatasManage(con);
        Long total=dataService.getDatasManageTotal(con);


        return new PageDataVO(page,length,total,dtos);

    }
}
