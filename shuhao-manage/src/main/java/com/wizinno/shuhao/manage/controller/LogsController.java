package com.wizinno.shuhao.manage.controller;

import com.wizinno.shuhao.condition.LogCondition;
import com.wizinno.shuhao.data.PageDataVO;
import com.wizinno.shuhao.data.ResponseVO;
import com.wizinno.shuhao.exception.CustomException;
import com.wizinno.shuhao.exception.CustomExceptionCode;
import com.wizinno.shuhao.user.data.LogDto;
import com.wizinno.shuhao.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by HP on 2017/11/7.
 */

@RestController
@RequestMapping("/api/manage/logs")
public class LogsController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/getLogList", method = RequestMethod.GET)
public ResponseVO getLogList(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "length",defaultValue = "10") Integer length,
                             @RequestParam(value = "fromTime",required = false)Long fromTime,
                             @RequestParam(value = "toTime",required = false)Long toTime,
                             @RequestParam(value="userId",required = false)Long userId){
    if (page < 1 || length < 0){
        throw new CustomException(CustomExceptionCode.ErrorParam);
    }
    LogCondition logCondition=new LogCondition();
    logCondition.setStart((page-1)*length);
    logCondition.setSize(length);
    if(userId!=null){
    logCondition.setUserId(userId);
    }
    if(fromTime!=null){
        logCondition.setFromTime(new Date(fromTime));
    }
    if(toTime!=null){
        logCondition.setToTime(new Date(toTime));
    }

   List<LogDto> logDtoList=userService.queryByCondition(logCondition);
   Long total=(long)userService.queryAllLogs(logCondition).size();

   return new PageDataVO(page,length,total,logDtoList);
}
}
