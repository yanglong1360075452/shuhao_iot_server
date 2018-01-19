package com.wizinno.shuhao.data.serviceImpl;
import com.wizinno.shuhao.condition.DataCondition;
import com.wizinno.shuhao.data.DataDto;
import com.wizinno.shuhao.data.domain.model.Data;
import com.wizinno.shuhao.data.domain.DataMapper;
import com.wizinno.shuhao.data.util.DataDtosUtils;
import com.wizinno.shuhao.exception.CustomException;
import com.wizinno.shuhao.exception.CustomExceptionCode;
import com.wizinno.shuhao.service.DataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {
   @Autowired
   private DataMapper dataMapper;
    @Override
    public List<DataDto> getDatas(DataCondition dataCondition) {
        List<Data> datas=dataMapper.finByDeviceIdAndTime(dataCondition );
        return DataDtosUtils.getDataDtos(datas);
    }

    @Override
    public List<DataDto> getData(Long deviceId,int records) {

        List<Data> datas=dataMapper.finByDeviceId(deviceId,records);
        return DataDtosUtils.getDataDtos(datas);
    }

    @Override
    public void save(DataDto dataDto) {
        if(dataDto!=null){
            Data data=new Data();
            BeanUtils.copyProperties(dataDto,data);
          dataMapper.insert(data);
        }

    }

    @Override
    public DataDto findByDeviceId(Long deviceId) {
        //判断查询出来最新的一条数据的模块地址是不是最新的
       Data data= dataMapper.isFinByDeviceId(deviceId);
       if(data.getId()==null){
           throw new CustomException(CustomExceptionCode.DeviceNoExit);
       }
       DataDto dataDto=new DataDto();
        BeanUtils.copyProperties(data,dataDto);

        return dataDto;
    }

    @Override
    public List<DataDto> getAllDataByDeviceId(Long deviceId) {
      List<Data> datas= dataMapper.findAllByDeviceId(deviceId);
          return DataDtosUtils.getDataDtos(datas);
    }

    @Override
    public List<DataDto> getDatasManage(DataCondition con) {
        List<Data> datas=dataMapper.getDatasManage(con);
        return DataDtosUtils.getDataDtos(datas);
    }

    @Override
    public Long getDatasManageTotal(DataCondition con) {
        return dataMapper.getDatasManageTotal(con);
    }
}
