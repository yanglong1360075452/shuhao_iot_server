package com.wizinno.shuhao.manage.controller;

import com.wizinno.shuhao.controller.BaseController;
import com.wizinno.shuhao.data.ResponseVO;
import com.wizinno.shuhao.user.data.AppVerDto;
import com.wizinno.shuhao.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by HP on 2017/11/7.
 */

@RestController
@RequestMapping("/api/manage/appver")
public class AppVersController extends BaseController{

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseVO getAppVers(){
       List<AppVerDto> dtos= userService.getAppVers();
        return new ResponseVO(dtos);
    }


    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO saveAppVers(@RequestBody AppVerDto dto){
        if (dto.getId() != null){
            dto.setId(null);
        }
       userService.saveAppVers(dto);
        return new ResponseVO();
    }
}
