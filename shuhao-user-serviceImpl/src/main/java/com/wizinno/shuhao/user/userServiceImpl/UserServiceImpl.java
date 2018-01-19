package com.wizinno.shuhao.user.userServiceImpl;
import com.wizinno.shuhao.condition.LogCondition;
import com.wizinno.shuhao.condition.UserCondition;
import com.wizinno.shuhao.device.data.DeviceDto;
import com.wizinno.shuhao.exception.CustomException;
import com.wizinno.shuhao.exception.CustomExceptionCode;
import com.wizinno.shuhao.user.data.*;
import com.wizinno.shuhao.user.domain.*;
import com.wizinno.shuhao.user.domain.model.*;
import com.wizinno.shuhao.user.util.DtoTranUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import com.wizinno.shuhao.userService.UserService;

import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserFriendsMapper userFriendsMapper;
    @Autowired
    private AppVerMapper appVerMapper;
    @Autowired
    private AuthoCodeMapper authoCodeMapper;
    @Autowired
    private LogMapper logMapper;
    @Override
    public int updateUser(UserDto userDto) {
        User user=new User();
        BeanUtils.copyProperties(userDto,user);
        int a=  userMapper.updateByPrimaryKey(user);
        return a;
    }

    @Override
    public void insert(UserDto userDto) {
        User user1= userMapper.selectByPhone(userDto.getPhone());
       if(user1!=null){
           throw new CustomException(CustomExceptionCode.PhoneIsRegister);
       }
        User user=new User();
        BeanUtils.copyProperties(userDto,user);
        userMapper.insert(user);
    }

    @Override
    public UserDto findByPhone(String phone) {
        User user=userMapper.selectByPhone(phone);
        UserDto userDto=new UserDto();
        if(user==null){
            throw new CustomException(CustomExceptionCode.UserNoExit);
        }
        BeanUtils.copyProperties(user,userDto);
        return userDto;
    }

    @Override
    public UserDto findById(Long userId) {
       User user= userMapper.selectByPrimaryKey(userId);
        UserDto userDto=new UserDto();
       if(user==null){
           throw new CustomException(CustomExceptionCode.UserNoExit);
       }
        BeanUtils.copyProperties(user,userDto);
        return userDto;
    }

    @Override
    public void insertFriend(UserFriendsDto userFriendsDto) {
        UserFriends userFriends=new UserFriends();
        BeanUtils.copyProperties(userFriendsDto,userFriends);
        userFriendsMapper.insert(userFriends);
    }

    @Override
    public List<UserDto> getFriends(Long userId) {
        UserDto userDto;
        List<UserDto>list=new ArrayList<>();
        List<User>users=userMapper.getFriends(userId);
        list= DtoTranUser.dtoTranUser(users);
        return list;
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        userFriendsMapper.deleteByPrimaryKey(userId,friendId);
    }

    @Override
    public AppVerDto newAppVer(Integer platform) {
        AppVer appVer=new AppVer();
        AppVerDto appVerDto=new AppVerDto();
       List<AppVer> appVers= appVerMapper.getNewApp(platform);
       if(appVers!=null&&appVers.size()>0){
           appVer=appVers.get(0);
           BeanUtils.copyProperties(appVer,appVerDto);
       }else{
           throw new CustomException(CustomExceptionCode.NoAppVer);
       }
        return appVerDto;
    }

    @Override
    public void saveApp(AppVerDto appVerDto) {
        if(appVerDto==null){
            throw new CustomException(CustomExceptionCode.NoAppVer);
        }
        AppVer appVer=new AppVer();
        BeanUtils.copyProperties(appVerDto,appVer);
        appVerMapper.insert(appVer);
    }

    @Override
    public AuthoCodeDto selectByPhone(String phone) {
        AuthoCodeDto authoCodeDto=new AuthoCodeDto();
       AuthoCode authoCode= authoCodeMapper.selectByPhone(phone);
       if(authoCode!=null){
           BeanUtils.copyProperties(authoCode,authoCodeDto);
       }
        return authoCodeDto;
    }

    @Override
    public void saveAuthoCode(AuthoCodeDto authoCodeDto) {
        AuthoCode authoCode=new AuthoCode();
        BeanUtils.copyProperties(authoCodeDto,authoCode);
        authoCodeMapper.insert(authoCode);

    }

    @Override
    public void updateByPhone(AuthoCodeDto authoCodeDto) {
        AuthoCode authoCode=new AuthoCode();
        BeanUtils.copyProperties(authoCodeDto,authoCode);
        authoCodeMapper.updateByPhone(authoCode);
    }


    @Override
    public List<UserDto> getUsers(UserCondition con) {
        List<User> users = userMapper.getUsers(con);
        List<UserDto> lists = new ArrayList();
        UserDto userDto;
        for (User user:users){
             userDto = new UserDto();
             BeanUtils.copyProperties(user,userDto);
             lists.add(userDto);
        }
        return lists;
    }

    @Override
    public Long getUsersTotal(UserCondition con) {

        return  userMapper.getUsersTotal(con);
    }

    @Override
    public List<DeviceDto> getDevicesByUserId(UserCondition con) {
        List<DeviceDto> dtos = userMapper.getDevicesByUserId(con);
        for (DeviceDto dto:dtos){
            if (dto.getIsOnline() == 0){
                dto.setIsOnlineDesc("离线");
            }else {
                dto.setIsOnlineDesc("在线");
            }
        }

        return dtos;
    }

    @Override
    public Long getDevicesByUserIdTotal(UserCondition con) {

        return userMapper.getDevicesByUserIdTotal(con);
    }

    @Override
    public List<UserDto> getFriendsByUserId(UserCondition con) {

        List<User> users=userMapper.getFriendsByUserId(con);
        List<UserDto> userDtos = DtoTranUser.dtoTranUser(users);
        for (UserDto dto:userDtos){
            List<Long> userShares=userMapper.getUserShares(con.getUserId(),dto.getUserId());
            if (userShares.size() > 0){
                dto.setUserShares(userShares);
            }

            List<Long> friendShares=userMapper.getFriendShares(dto.getUserId(),con.getUserId());
            if (friendShares.size() > 0){
                dto.setFriendShares(friendShares);
            }

        }
        return userDtos;

    }

    @Override
    public Long getFriendsByUserIdTotal(UserCondition con) {
        return userMapper.getFriendsByUserIdTotal(con);
    }

    @Override
    public List<DeviceDto> getFriendDevicesShares(UserCondition con) {
        List<DeviceDto> dtos= userMapper.getFriendDevicesShares(con);
        for(DeviceDto dto:dtos){
            if (dto.getIsOnline() != null){
                if (dto.getIsOnline() == 0){
                    dto.setIsOnlineDesc("离线");
                }else {
                    dto.setIsOnlineDesc("在线");
                }
            }

        }
        return dtos;
    }

    @Override
    public Long getFriendDevicesSharesTotal(UserCondition con) {
        return userMapper.getFriendDevicesSharesTotal(con);
    }

    @Override
    public List<AppVerDto> getAppVers() {
        List<AppVer> appVers = appVerMapper.getAppVers();
        List<AppVerDto> lists = new ArrayList();
        AppVerDto appVerDto =null;
        for (AppVer appVer:appVers){
             appVerDto = new AppVerDto();
             BeanUtils.copyProperties(appVer,appVerDto);
            lists.add(appVerDto);
        }
        return lists;
    }

    @Override
    public void saveLog(LogDto logDto) {
        Log log=new Log();
        BeanUtils.copyProperties(logDto,log);
        logMapper.insert(log);
    }

    @Override
    public List<LogDto> queryByCondition(LogCondition logCondition) {
        List<Log> logs=logMapper.queryByCondition(logCondition);
        List<LogDto>logDtos=new ArrayList<>();
        LogDto logDto;
        if(logs.size()>0){
            for(Log log:logs){
                logDto=new LogDto();
                BeanUtils.copyProperties(log,logDto);
                logDtos.add(logDto);

            }
        }
        return logDtos;
    }

    @Override
    public List<LogDto> queryAllLogs(LogCondition logCondition) {
       List<Log>logs= logMapper.selectAll(logCondition);
        List<LogDto>logDtos=new ArrayList<>();
        LogDto logDto;
        if(logs.size()>0){
            for(Log log:logs){
                logDto=new LogDto();
                BeanUtils.copyProperties(log,logDto);
                logDtos.add(logDto);

            }
        }
        return logDtos;
    }

    @Override
    public AppVerDto updateAppVers(AppVerDto dto) {
        AppVer appVer = new AppVer();
        BeanUtils.copyProperties(dto,appVer);
        appVerMapper.updateByPrimaryKey(appVer);
        AppVer appVer1 = appVerMapper.selectByPrimaryKey(dto.getId());
        AppVerDto appVerDto = new AppVerDto();
        BeanUtils.copyProperties(appVer1,appVerDto);
        return appVerDto;
    }

    @Override
    public void saveAppVers(AppVerDto dto) {
        AppVer appVer = new AppVer();
        BeanUtils.copyProperties(dto,appVer);
        appVerMapper.insert(appVer);

    }


}
