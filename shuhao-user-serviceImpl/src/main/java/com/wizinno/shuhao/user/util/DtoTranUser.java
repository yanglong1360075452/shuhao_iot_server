package com.wizinno.shuhao.user.util;
import com.wizinno.shuhao.user.data.UserDto;
import com.wizinno.shuhao.user.domain.model.User;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class DtoTranUser {
   public static List<UserDto> dtoTranUser(List<User> list){
       UserDto userDto;
       List<UserDto>lists=new ArrayList<>();
           for(User user:list){
               if(user!=null){
                   userDto=new UserDto();
                   BeanUtils.copyProperties(user,userDto);
                   lists.add(userDto);
               }
           }


       return lists;
   }
}
