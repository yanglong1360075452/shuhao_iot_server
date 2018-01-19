package com.wizinno.shuhao.service;
import com.wizinno.shuhao.condition.DataCondition;
import com.wizinno.shuhao.data.DataDto;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
public interface DataService {
    public List<DataDto>  getDatas(DataCondition con);

    public List<DataDto> getData(Long deviceId,int records);

    public void save(DataDto dataDto);

    public DataDto findByDeviceId(Long deviceId);

    public List<DataDto> getAllDataByDeviceId(Long deviceId);


    List<DataDto> getDatasManage(DataCondition con);

    Long getDatasManageTotal(DataCondition con);

}
