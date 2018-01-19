package com.wizinno.shuhao.userService;

import com.wizinno.shuhao.condition.LogCondition;
import com.wizinno.shuhao.condition.UserCondition;
import com.wizinno.shuhao.device.data.DeviceDto;
import com.wizinno.shuhao.user.data.*;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface UserService {
    public int  updateUser(UserDto userDto);

    public void insert(UserDto userDto);

    public UserDto findByPhone(String phone);

    public UserDto findById(Long userId);

    public void insertFriend(UserFriendsDto userFriendsDto);

    public List<UserDto> getFriends(Long userId);

    public void deleteFriend(Long userId,Long friendId);

    public AppVerDto newAppVer(Integer platform);

    public void saveApp(AppVerDto appVerDto);

    public AuthoCodeDto selectByPhone(String phone);

    public void saveAuthoCode(AuthoCodeDto authoCodeDto);

    public void updateByPhone(AuthoCodeDto authoCodeDto);

    List<UserDto> getUsers(UserCondition con);

    Long getUsersTotal(UserCondition con);

    List<DeviceDto> getDevicesByUserId(UserCondition con);

    Long getDevicesByUserIdTotal(UserCondition con);

    List<UserDto> getFriendsByUserId(UserCondition con);

    Long getFriendsByUserIdTotal(UserCondition con);

    List<DeviceDto> getFriendDevicesShares(UserCondition con);

    Long getFriendDevicesSharesTotal(UserCondition con);

    List<AppVerDto> getAppVers();

    void saveLog(LogDto logDto);

    List<LogDto> queryByCondition(LogCondition logCondition);

    List<LogDto> queryAllLogs(LogCondition logCondition);

    AppVerDto updateAppVers(AppVerDto dto);

    void saveAppVers(AppVerDto dto);
}
