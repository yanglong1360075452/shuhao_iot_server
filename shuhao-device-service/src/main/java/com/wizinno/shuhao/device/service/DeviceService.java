package com.wizinno.shuhao.device.service;

import com.wizinno.shuhao.condition.DeviceCondition;
import com.wizinno.shuhao.device.data.DeviceDto;

import java.util.List;

public interface DeviceService {
public DeviceDto deviceInfo(Long deviceId);

public DeviceDto queryDevice(Long deviceId);

public void saveDevice(DeviceDto deviceDto);

public List<DeviceDto> isBindDevice(Long userId);

public void bindDevice(DeviceDto deviceDto);

public void updataDevice(DeviceDto deviceDto);

    List<DeviceDto> getDevices(DeviceCondition con);

    Long getDevicesTotal(DeviceCondition con);

}
