package com.wizinno.shuhao.data.util;

import com.wizinno.shuhao.data.DataDto;
import com.wizinno.shuhao.data.domain.model.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class DataDtosUtils {
    public static List<DataDto> getDataDtos(List<Data>datas){
        DataDto dataDto;
        List<DataDto>dataDtos=new ArrayList<>();
        if(datas!=null){
            for(Data data:datas){
                dataDto=new DataDto();
                BeanUtils.copyProperties(data,dataDto);
                dataDtos.add(dataDto);
            }
        }
        return dataDtos;
    }
}
