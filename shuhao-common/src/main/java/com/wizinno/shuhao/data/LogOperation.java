package com.wizinno.shuhao.data;

/**
 * Created by LiuMei on 2017-05-09.
 */
public enum LogOperation {
    login(1,"登录"),
    register(2,"注册"),
    getCode(3,"获取验证码"),
    resetPassword(4,"重置密码"),
    userInfo(5,"获取用户信息/用户设备列表"),
    setPassword(6," 修改密码"),
    getDeviceInfo(7,"获取设备详情"),
    bindDevice(8,"绑定设备/设置场景"),
    unbindDevice(9,"解绑设备"),
    getDeviceData(10,"获取传感器数据"),
    getFriendList(11,"好友列表"),
    addFriend(12,"添加好友"),
    deleteFriend(13,"删除好友"),
    myFriendsSharedDevices(14,"获取好友分享给我的设备"),
    mySharedDevices(15,"获取我分享给好友的设备"),
    shareDevice(16,"分享设备给好友"),
    cancelShare(17,"撤销我给好友的分享"),
    getLatestAPP(18,"APP版本检测"),
    sendCmd(19,"控制命令");
    // 定义私有变量
    private Integer nCode;

    private String name;

    // 构造函数，枚举类型只能为私有
    LogOperation(Integer _nCode, String _name) {
        this.nCode = _nCode;
        this.name = _name;
    }

    public Integer toCode(){
        return this.nCode;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static LogOperation valueOf(Integer code) {
        for (LogOperation logOperation : LogOperation.values()){
            if (logOperation.toCode().equals(code)){
                return logOperation;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code) {
        for (LogOperation logOperation : LogOperation.values()){
            if (logOperation.toCode().equals(code)){
                return logOperation.toString();
            }
        }
        return null;
    }
}
