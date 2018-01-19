package com.wizinno.shuhao.device.serviceImpl;
import com.wizinno.shuhao.condition.DeviceCondition;
import com.wizinno.shuhao.device.data.DeviceDto;
import com.wizinno.shuhao.device.domain.DeviceMapper;
import com.wizinno.shuhao.device.domain.model.Device;
import com.wizinno.shuhao.device.service.DeviceService;
import com.wizinno.shuhao.exception.CustomException;
import com.wizinno.shuhao.exception.CustomExceptionCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;
    @Override
    public DeviceDto deviceInfo(Long deviceId) {
        DeviceDto deviceDto=new DeviceDto();
       Device device= deviceMapper.selectByPrimaryKey(deviceId);
               if(device==null){
           throw new CustomException(CustomExceptionCode.DeviceNoExit);
       }
        BeanUtils.copyProperties(device,deviceDto);
        return deviceDto;
    }

    @Override
    public DeviceDto queryDevice(Long deviceId) {
        DeviceDto deviceDto=new DeviceDto();
        Device device= deviceMapper.selectByPrimaryKey(deviceId);
        if(device!=null){
            BeanUtils.copyProperties(device,deviceDto);
        }
        return deviceDto;
    }

    @Override
    public void saveDevice(DeviceDto deviceDto) {
        Device device=new Device();
        BeanUtils.copyProperties(deviceDto,device);
        deviceMapper.insert(device);
    }

    @Override
    public List<DeviceDto> isBindDevice(Long userId) {
        DeviceDto deviceDto;
        List<DeviceDto>deviceDtos=new ArrayList<>();
        List<Device> devices=deviceMapper.isBindDevice(userId);
        if(devices.size()>0){
            for(Device device:devices){
                deviceDto=new DeviceDto();
                BeanUtils.copyProperties(device,deviceDto);
                deviceDtos.add(deviceDto);
            }

        }
        return deviceDtos;
    }

    @Override
    public void bindDevice(DeviceDto deviceDto) {
        Device device=new Device();
        BeanUtils.copyProperties(deviceDto,device);
        deviceMapper.updateByPrimaryKey(device);
    }

    @Override
    public void updataDevice(DeviceDto deviceDto) {
        Device device=new Device();
        BeanUtils.copyProperties(deviceDto,device);
        deviceMapper.updateByPrimaryKey(device);
    }

    @Override
    public List<DeviceDto> getDevices(DeviceCondition con) {
        List<Device> devices=deviceMapper.getDevices(con);
        List<DeviceDto> lists = new ArrayList();
        DeviceDto deviceDto =null;
        for (Device device:devices){

            deviceDto = new DeviceDto();

            if (device.getIsOnline() != null){
                if (device.getIsOnline() == 0){
                    deviceDto.setIsOnlineDesc("离线");
                }else {
                    deviceDto.setIsOnlineDesc("在线");
                }
            }

             BeanUtils.copyProperties(device,deviceDto);
             lists.add(deviceDto);
        }
        return lists;
    }

    @Override
    public Long getDevicesTotal(DeviceCondition con) {
        return deviceMapper.getDevicesTotal(con);
    }


}
