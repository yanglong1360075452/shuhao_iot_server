package com.wizinno.shuhao.app.controller;
import com.wizinno.shuhao.app.config.Constant;
import com.wizinno.shuhao.app.data.UserSimple;
import com.wizinno.shuhao.app.utils.AuthoCode;
import com.wizinno.shuhao.app.utils.JwtUtil;
import com.wizinno.shuhao.app.utils.Util;
import com.wizinno.shuhao.condition.DataCondition;
import com.wizinno.shuhao.controller.BaseController;
import com.wizinno.shuhao.data.DataDto;
import com.wizinno.shuhao.data.ResponseVO;
import com.wizinno.shuhao.device.data.DeviceDto;
import com.wizinno.shuhao.device.domain.model.Device;
import com.wizinno.shuhao.device.service.DeviceService;
import com.wizinno.shuhao.exception.CustomException;
import com.wizinno.shuhao.exception.CustomExceptionCode;
import com.wizinno.shuhao.service.DataService;
import com.wizinno.shuhao.share.data.ShareDto;
import com.wizinno.shuhao.share.service.ShareService;
import com.wizinno.shuhao.user.data.AppVerDto;
import com.wizinno.shuhao.user.data.AuthoCodeDto;
import com.wizinno.shuhao.user.data.UserDto;
import com.wizinno.shuhao.user.data.UserFriendsDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.wizinno.shuhao.userService.UserService;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/app/user")
public class ControllerApp extends BaseController {
    private Logger log = Logger.getLogger(ControllerApp.class);

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DataService dataService;
    @Autowired
    private ShareService shareService;


    /**
     * 获取短信验证码
     *
     * @param phone
     * @param type
     * @return
     */
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO getAuthCode(@RequestParam(value = "phone", required = true) String phone,
                                  @RequestParam(value = "type", required = true) Integer type) {
        if (Util.stringNull(phone) || type < 0) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        String code=  AuthoCode.getAuthCode(phone, type);

        //将phone 和 code  保存数据库
        //先查询phone这条数据是否存在
       AuthoCodeDto authoCodeDto= userService.selectByPhone(phone);
       if(authoCodeDto.getId()==null){
           //将phone 和 code 保存数据库
           authoCodeDto=new AuthoCodeDto();
           authoCodeDto.setPhone(phone);
           authoCodeDto.setAuthoCode(code);
           userService.saveAuthoCode(authoCodeDto);
       }else{
           //更新数据库
           authoCodeDto.setAuthoCode(code);
           userService.updateByPhone(authoCodeDto);
       }
        return new ResponseVO("验证码发送成功");
    }


    /**
     * 注册用户
     *
     * @param phone
     * @param password
     * @param authCode
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO register(@RequestParam(value = "phone", required = true) String phone,
                               @RequestParam(value = "password", required = true) String password,
                               @RequestParam(value = "authCode", required = true) String authCode) {
        if (Util.stringNull(phone) || Util.stringNull(password) || Util.stringNull(authCode)) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        //手机注册码验证
        AuthoCodeDto authoCodeDto = userService.selectByPhone(phone);
        if(authoCodeDto==null){
            return new ResponseVO("请先获取验证码");
        }else{
            String code=authoCodeDto.getAuthoCode();
            if (!code.equals(authCode)) {
                throw new CustomException(CustomExceptionCode.ErrorCode);
            }
            UserDto userDto = new UserDto();
            userDto.setPassword(bCryptPasswordEncoder.encode(password));
            userDto.setPhone(phone);
            userDto.setRegisterTime(new Date());
            userService.insert(userDto);
            return new ResponseVO("注册成功");
        }

    }

    /**
     * 用户登录
     *
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO login(@RequestParam(value = "phone", required = true) String phone,
                            @RequestParam(value = "password", required = true) String password) {
        if (Util.stringNull(phone) || Util.stringNull(password)) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        Map params = new HashMap();
        String token = null;
        UserDto userDto = userService.findByPhone(phone);
        String subject = JwtUtil.generalSubject(userDto);
        try {
            token = jwtUtil.createJWT(Constant.JWT_ID, subject, Constant.JWT_TTL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean matches = bCryptPasswordEncoder.matches(password, userDto.getPassword());
        if (matches) {
            params.put("id", userDto.getUserId());
            params.put("phone", userDto.getPhone());
            params.put("token", token);
            return new ResponseVO(params);
        } else {
            throw new CustomException(CustomExceptionCode.PasswordError);
        }
    }

    /**
     * 重置密码
     *
     * @param phone
     * @param password
     * @param authCode
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO resetPassword(@RequestParam(value = "phone", required = true) String phone,
                                    @RequestParam(value = "password", required = true) String password,
                                    @RequestParam(value = "authoCode", required = true) String authCode) {
        if (Util.stringNull(phone) || Util.stringNull(password) || Util.stringNull(authCode)) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        AuthoCodeDto authoCodeDto = userService.selectByPhone(phone);
        UserDto userDto = userService.findByPhone(phone);
        String code=authoCodeDto.getAuthoCode();
        if (!code.equals(authCode)) {
            throw new CustomException(CustomExceptionCode.ErrorCode);
        }
        userDto.setPassword(bCryptPasswordEncoder.encode(password));
        userService.updateUser(userDto);
        return new ResponseVO("密码重置成功");

    }

    /**
     * 获取用户信息/用户设备列表
     *
     * @param phone
     * @param userId
     * @return
     */
    @RequestMapping(value = "/userInfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO userInfo(@RequestParam(value = "phone", required = false) String phone,
                               @RequestParam(value = "userId", required = false) Long userId) {
        Long id = (Long) request.getAttribute("userId");
        List<Long>deviceId=new ArrayList<>();
        Map params = new HashMap();
        if (userId != null) {
            //查询某个用户的信息
            UserDto userDto = userService.findById(userId);
            List<DeviceDto> deviceDtos = deviceService.isBindDevice(userId);
            if(deviceDtos!=null&&deviceDtos.size()>0){
                for(DeviceDto deviceDto:deviceDtos){
                    deviceId.add(deviceDto.getDeviceId());
                }
            }
            params.put("deviceId",deviceId);
            params.put("id", userDto.getUserId());
            params.put("phone", userDto.getPhone());
        } else if (phone != null) {
            UserDto userDto = userService.findByPhone(phone);
            Long id1=userDto.getUserId();
            List<DeviceDto> deviceDtos = deviceService.isBindDevice(id);
            if(deviceDtos!=null&&deviceDtos.size()>0){
                for(DeviceDto deviceDto:deviceDtos){
                    deviceId.add(deviceDto.getDeviceId());
                }
            }
            params.put("deviceId",deviceId);
            params.put("id", userDto.getUserId());
            params.put("phone", userDto.getPhone());
        } else {
            UserDto userDto = userService.findById(id);
            params.put("id", userDto.getUserId());
            params.put("phone", userDto.getPhone());
            //查询用户绑定的设备id
            List<DeviceDto> deviceDtos = deviceService.isBindDevice(id);
            if(deviceDtos!=null&&deviceDtos.size()>0){
                for(DeviceDto deviceDto:deviceDtos){
                    deviceId.add(deviceDto.getDeviceId());
            }
            params.put("deviceId",deviceId);

            }


        }

        return new ResponseVO(params);
    }

    /**
     * 修改密码
     *
     * @param password
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "/setPassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO setPassword(@RequestParam(value = "password", required = true) String password,
                                  @RequestParam(value = "newPassword", required = true) String newPassword) {
        if (Util.stringNull(password) || Util.stringNull(newPassword)) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        Long id = (Long) request.getAttribute("userId");
        UserDto userDto = userService.findById(id);
        boolean matches = bCryptPasswordEncoder.matches(password, userDto.getPassword());
        if (matches) {
            userDto.setPassword(bCryptPasswordEncoder.encode(newPassword));
            int a = userService.updateUser(userDto);
            return new ResponseVO("修改密码成功");


        } else {
            throw new CustomException(CustomExceptionCode.OldPasswordError);
        }
    }

    /**
     * 添加好友
     *
     * @param phone
     * @return
     */
    @RequestMapping(value = "/addFriend", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO addFriend(@RequestParam(value = "phone", required = true) String phone) {
        if (Util.stringNull(phone)) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        UserFriendsDto userFriendsDto = new UserFriendsDto();
        Long id = (Long) request.getAttribute("userId");
        UserDto friend = userService.findByPhone(phone);
        userFriendsDto.setFriendId(friend.getUserId());
        userFriendsDto.setUserId(id);
        userService.insertFriend(userFriendsDto);
        return new ResponseVO("添加好友成功");
    }

    /**
     * 好友列表
     *
     * @return
     */
    @RequestMapping(value = "/getFriendList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO getFriendList() {
        UserSimple userSimple;
        List<UserSimple> lists = new ArrayList<>();
        Long id = (Long) request.getAttribute("userId");
        List<UserDto> list = userService.getFriends(id);
        if (list.size() > 0) {
            for (UserDto userDto : list) {
                userSimple = new UserSimple();
                userSimple.setId(userDto.getUserId());
                userSimple.setPhone(userDto.getPhone());
                lists.add(userSimple);
            }
        }
        return new ResponseVO(lists);
    }

    /**
     * 删除好友
     *
     * @param phone
     * @return
     */
    @RequestMapping(value = "/deleteFriend", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO deleteFriend(@RequestParam(value = "phone", required = true) String phone) {
        if (Util.stringNull(phone)) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        Long id = (Long) request.getAttribute("userId");
        UserDto userDto = userService.findByPhone(phone);
        userService.deleteFriend(id, userDto.getUserId());
        return new ResponseVO("删除好友成功");
    }

    /**
     * 获取设备详情
     *
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/getDeviceInfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO getDeviceInfo(@RequestParam(value = "deviceId", required = true) Long deviceId) {
        if (deviceId == null) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        Long id = (Long) request.getAttribute("userId");
        DeviceDto deviceDto = deviceService.deviceInfo(deviceId);
        return new ResponseVO(deviceDto);
    }

    /**
     * 绑定设备
     *
     * @param deviceId
     * @param sinarioCfg
     * @return
     */
    @RequestMapping(value = "/bindDevice", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO bindDevice(@RequestParam(value = "deviceId", required = true) Long deviceId,
                                 @RequestParam(value = "sinarioCfg", required = false) String sinarioCfg) throws UnsupportedEncodingException {
        if (deviceId == null) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        Long id = (Long) request.getAttribute("userId");
        DeviceDto deviceDto = deviceService.queryDevice(deviceId);
        if (deviceDto.getDeviceId()==null) {
            throw new CustomException(CustomExceptionCode.DeviceNoExit);
        }

        //查询改用户是否绑定了设备
        List<DeviceDto> deviceDtos = deviceService.isBindDevice(id);
        List<Long> deviceIds=new ArrayList<>();
        //先查询该设备是否有人绑定
        ;
        if(deviceDto.getUserId()==0){
            if (deviceDtos.size()==0) {
                //1改用户没有绑定设备，执行绑定设备的操作
                deviceDto.setUserPhone(userService.findById(id).getPhone());
                deviceDto.setUserId(id);
                if(sinarioCfg!=null){
                    sinarioCfg=  new String(sinarioCfg.getBytes("ISO-8859-1"),"UTF-8");
                    deviceDto.setSinarioCfg(sinarioCfg);
                    deviceDto.setSinarioCfgTime(new Date());
                }
                deviceService.bindDevice(deviceDto);
                return new ResponseVO("绑定设备成功");
            } else {
                for(DeviceDto deviceDto1:deviceDtos){
                    deviceIds.add(deviceDto1.getDeviceId());
                }
                //改用户绑定了设备，查询改用户是否是绑定新设备
                if (!deviceIds.contains(deviceId)) {
                    //绑定新设备
                    if(sinarioCfg!=null){
                        sinarioCfg=  new String(sinarioCfg.getBytes("ISO-8859-1"),"UTF-8");
                        deviceDto.setSinarioCfg(sinarioCfg);
                        deviceDto.setSinarioCfgTime(new Date());
                    }
                    deviceDto.setUserPhone(userService.findById(id).getPhone());
                    deviceDto.setUserId(id);
                    deviceService.bindDevice(deviceDto);
                    return new ResponseVO("绑定设备成功");
                }else{
                    //场景配置信息是否更新
                    if(sinarioCfg!=null){
                        sinarioCfg=  new String(sinarioCfg.getBytes("ISO-8859-1"),"UTF-8");
                        if (!sinarioCfg.equals(deviceDto.getSinarioCfg())) {
                            //更新场景配置信息
                            deviceDto.setSinarioCfg(sinarioCfg);
                            deviceDto.setSinarioCfgTime(new Date());
                            deviceService.updataDevice(deviceDto);
                            return new ResponseVO(1,"场景配置信息已经更新");
                        }else{
                            return new ResponseVO(2,"配置未更改");
                        }

                    }
                    return new ResponseVO("绑定设备成功");

                }

            }
        }else{
            if(deviceDto.getUserId()==id){
                if(sinarioCfg!=null){
                    if(sinarioCfg.equals(deviceDto.getSinarioCfg())){
                        return new ResponseVO(4,"你已绑定过该设备，无需重复绑定");
                    }else{
                        sinarioCfg=  new String(sinarioCfg.getBytes("ISO-8859-1"),"UTF-8");
                        deviceDto.setSinarioCfg(sinarioCfg);
                        deviceDto.setSinarioCfgTime(new Date());
                        deviceService.updataDevice(deviceDto);
                        return new ResponseVO(1,"场景配置信息已经更新");
                    }
                }else{
                    return new ResponseVO(4,"你已绑定过该设备，无需重复绑定");
                }

            }else{
                return new ResponseVO(3,"绑定失败,已被他人绑定");
            }

        }


    }

    /**
     * 解绑设备
     *
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/unbindDevice", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO unbindDevice(@RequestParam(value = "deviceId", required = true) Long deviceId) {
        if (deviceId == null) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        Long id = (Long) request.getAttribute("userId");
        DeviceDto deviceDto=deviceService.queryDevice(deviceId);
        if(deviceDto.getDeviceId()==null){
            throw new CustomException(CustomExceptionCode.DeviceNoExit);
        }else{
            deviceDto.setUserPhone(null);
            deviceDto.setUserId((long)0);
            deviceService.updataDevice(deviceDto);
            return new ResponseVO("解绑设备成功");
        }


    }
    /**
     * 获取传感器数据
     * @param deviceId
     * @param fromTime
     * @param toTime
     * @return
     */
    @RequestMapping(value = "/getDeviceData", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO getDeviceData(@RequestParam(value = "deviceId", required = true) Long deviceId,
                                    @RequestParam(value = "fromTime", required = false) Long fromTime,
                                    @RequestParam(value = "toTime", required = false) Long toTime){
        if(deviceId==null){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        List <Map> datas=new ArrayList<>();
        List<DataDto> dataDtos=new ArrayList<>();
        Long id = (Long) request.getAttribute("userId");
        DataCondition dataCondition=new DataCondition();
        dataCondition.setDeviceId(deviceId);
        if(fromTime==null&&toTime==null){
            //查询设备表中的传感模块数量
            DeviceDto deviceDto= deviceService.queryDevice(deviceId);
            if(deviceDto.getDeviceId()==null){
                throw new CustomException(CustomExceptionCode.DeviceNoExit);
            }
            int record=deviceDto.getSensorCount()+deviceDto.getDriverCount();
            String ioConfig=deviceDto.getIoCfg();
            //列表包中包含分好，说明模块列表包多余10个
            String modelAd;
            String []totalAdress = new String[1024];
            String lastAdress = null;
            if(ioConfig.indexOf(";")==-1){
                modelAd=ioConfig.split(",")[4];
                totalAdress=modelAd.split("\\|");
                lastAdress=totalAdress[totalAdress.length-1].split("_")[0];
            }else{
                String[] str=ioConfig.split(";");
                modelAd=str[str.length-1];
                totalAdress=modelAd.split("\\|");
                lastAdress=totalAdress[totalAdress.length-1].split("_")[0];
            }
            //判断查询出来最新的一条数据的模块地址是不是最新的
            DataDto dataDto= dataService.findByDeviceId(deviceId);
            String adress= dataDto.getData().split(",")[2];
            if(adress.equals(lastAdress)){
                //查询最新的数据
                dataDtos=dataService.getData(deviceId,record);
                if(dataDtos.size()>0){
                    Map map;
                    for(DataDto dataDt:dataDtos){
                       map= new HashMap();
                       map.put("id",dataDt.getDeviceId());
                       map.put("receiveTime",dataDt.getReceiveTime());
                       map.put("dataPackage",dataDt.getData());
                       datas.add(map) ;
                    }
                }
            }else{
                List<DataDto> dataDtos1=dataService.getAllDataByDeviceId(deviceId);
                List<DataDto>dataDtos2=new ArrayList<>();
                int i;
                for( i=0;i<dataDtos1.size();i++){

                    String adres=dataDtos1.get(i).getData().split(",")[2];
                    if(adres.equals(lastAdress)){
                        break;
                    }
                }
                //集合中向上去 record个数据
                Map map;
                for(int j=i;j<record+i;j++){
                    map=new HashMap();
                    map.put("id",dataDtos1.get(j).getDeviceId());
                    map.put("receiveTime",dataDtos1.get(j).getReceiveTime());
                    map.put("dataPackage",dataDtos1.get(j).getData());
                    datas.add(map);
                }

            }
        }else{
            if(fromTime!=null){
                dataCondition.setStartTime(new Date(fromTime));
            } if(toTime!=null){
                dataCondition.setEndTime(new Date(toTime));
            }
            List<DataDto> dtos=dataService.getDatas(dataCondition);
         if(dtos.size()>0){
             Map map;
                for(DataDto dataDto:dtos){
                    map= new HashMap();
                    map.put("id",dataDto.getDeviceId());
                    map.put("receiveTime",dataDto.getReceiveTime());
                    map.put("dataPackage",dataDto.getData());
                    datas.add(map) ;

                }
            }
        }
       return new ResponseVO(datas);

    }

    /**
     * 分享设备给好友
     * @param deviceId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/shareDevice", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO shareDevice(@RequestParam(value = "deviceId", required =true ) Long[] deviceId,
                                  @RequestParam(value = "userId", required =true ) Long []userId){
        if(deviceId==null||deviceId.length<=0||userId==null||userId.length<=0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        Long id = (Long) request.getAttribute("userId");
        ShareDto shareDto;
        for(Long uid:userId){
            UserDto userDto=userService.findById(uid);
            if(userDto.getUserId()==null){
                throw new CustomException(CustomExceptionCode.UserNoExit);
            }
            for(Long did:deviceId){
                DeviceDto deviceDto=deviceService.queryDevice(did);
                if(deviceDto.getDeviceId()==null){
                    throw new CustomException(CustomExceptionCode.DeviceNoExit);
                }
                //先判断分享表中是否有存过同样的数据
                ShareDto shareDto1=shareService.query(uid,id,did);
                if(shareDto1.getDeviceId()==null){
                    shareService.query(uid,id,did);
                    shareDto=new ShareDto();
                    shareDto.setUserId(uid);
                    shareDto.setShareId(id);
                    shareDto.setDeviceId(did);
                    shareDto.setShareTime(new Date());
                    shareService.save(shareDto);
                }

            }
        }
        return new ResponseVO("分享成功");
    }
    /**
     * 获取好友分享给我的设备
     * @param deviceId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/myFriendsSharedDevices", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO myFriendsSharedDevices(@RequestParam(value = "deviceId", required = false) Long deviceId,
                                             @RequestParam(value = "userId", required = false) Long userId){
        Long id = (Long) request.getAttribute("userId");
        List<ShareDto> list=shareService.findByOrder(id,userId,deviceId);
        return new ResponseVO(list);

    }
    /**
     * 获取我分享给好友的设备
     * @param deviceId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/mySharedDevices", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO mySharedDevices(@RequestParam(value = "deviceId", required = false) Long deviceId,
                                      @RequestParam(value = "userId", required = false) Long userId){

        Long id = (Long) request.getAttribute("userId");
        List<ShareDto> list=shareService.findByOrder(userId,id,deviceId);
        return new ResponseVO(list);
    }
    /**
     * 撤销我给好友的分享
     * @param deviceId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/cancelShare", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO cancelShare(@RequestParam(value = "deviceId", required =true ) Long[] deviceId,
                                  @RequestParam(value = "userId", required =true ) Long []userId){
        if(deviceId==null||deviceId.length<=0||userId==null||userId.length<=0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        Long id = (Long) request.getAttribute("userId");
        for(Long uid:userId){
            if(userService.findById(uid).getUserId()==null){
                throw new CustomException(CustomExceptionCode.UserNoExit);
            }
            for(Long did:deviceId){
                if(deviceService.queryDevice(did).getDeviceId()==null){
                    throw new CustomException(CustomExceptionCode.DeviceNoExit);
                }
                shareService.deleteShare(uid,id,did);

            }
        }
        return new ResponseVO("撤销成功");
    }
    /**
     * app版本检测
     * @param platform
     * @param
     * @return
     */
    @RequestMapping(value = "/getLatestAPP", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO getLatestAPP(@RequestParam(value = "platform", required =true ) Integer platform){
   if(platform<0){
       throw new CustomException(CustomExceptionCode.ErrorParam);
   }
   AppVerDto appVerDto=userService.newAppVer(platform);
        return  new ResponseVO(appVerDto);
    }
    /**
     * app版本详情导入数据库
     * @param appVerDto
     * @param
     * @return
     */
    @RequestMapping(value = "/saveAPP", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO saveAPP(@RequestBody AppVerDto appVerDto ){
        if(appVerDto.getAppVer()<0||Util.stringNull(appVerDto.getDescrib())||Util.stringNull(appVerDto.getUrl())
                || appVerDto.getPlatform()<0){

        throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        appVerDto.setRelTime(new Date());
       userService.saveApp(appVerDto);
       return  new ResponseVO("导入成功");
    }
    /**
     * 控制命令
     * @param ATCmd
     * @param
     * @return
     */
    @RequestMapping(value = "/sendCmd", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO sendCmd(@RequestParam(value = "ATCmd", required =true ) String ATCmd,
                              @RequestParam(value = "deviceId", required =true ) Long deviceId){
        Long id = (Long) request.getAttribute("userId");
        String replyInfo = null;
        InputStream is=null;
        BufferedReader br=null;
        Date time=new Date();
        if(ATCmd==null){
            throw new CustomException(CustomExceptionCode.NoATCMD);
        }
        try {
//1.建立客户端socket连接，指定服务器位置及端口
            String ip="27.54.248.121";
//            String ip="10.68.170.35";
            int port=33333;
            Socket socket =new Socket(ip,port);
//2.得到socket读写流
            is=socket.getInputStream();
            OutputStream os=socket.getOutputStream();
            PrintWriter pw=new PrintWriter(os);

//3.利用流按照一定的操作，对socket进行读写操作
            String sendInfo=deviceId+"#"+ATCmd;
            pw.write(sendInfo);
            pw.flush();
//读取服务器端数据

            //输入流
            while(true){
                    br=new BufferedReader(new InputStreamReader(is));
                    replyInfo = br.readLine();
                    if(replyInfo.indexOf("OK")!=-1||replyInfo.indexOf("ERR")!=-1){
                        break;
                    }
            }


//4.关闭资源
            br.close();
            is.close();
            pw.close();
            os.close();
            socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new CustomException(CustomExceptionCode.SendError);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(CustomExceptionCode.SendError);
        }

        return new ResponseVO(replyInfo);
    }

}