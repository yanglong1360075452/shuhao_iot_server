package com.wizinno.shuhao.share.serviceImpl;

import com.mchange.v2.beans.BeansUtils;
import com.wizinno.shuhao.share.data.ShareDto;
import com.wizinno.shuhao.share.domain.ShareMapper;
import com.wizinno.shuhao.share.domain.model.Share;
import com.wizinno.shuhao.share.service.ShareService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShareServiceImpl implements ShareService {
    @Autowired
    private ShareMapper shareMapper;
    @Override
    public void save(ShareDto shareDto) {
        Share share=new Share();
        BeanUtils.copyProperties(shareDto,share);
        shareMapper.insert(share);

    }

    @Override
    public ShareDto query(Long userId, Long shareId, Long deviceId) {
        ShareDto shareDto=new ShareDto();
      Share share= shareMapper.selectByPrimaryKey(userId,shareId,deviceId);
      if(share!=null){
          BeanUtils.copyProperties(share,shareDto);
      }
        return shareDto;
    }

    @Override
    public List<ShareDto> findByOrder(Long userId, Long shareId, Long deviceId) {
        List<ShareDto> shareDtos=new ArrayList<>();
        ShareDto shareDto;
       List<Share> shares= shareMapper.findByOrder(userId,shareId,deviceId);
       if(shares!=null&&shares.size()>0){
           for(Share share:shares){
               shareDto=new ShareDto();
               BeanUtils.copyProperties(share,shareDto);
               shareDtos.add(shareDto);
           }
       }
        return shareDtos;
    }

    @Override
    public void deleteShare(Long userId, Long shareId, Long deviceId) {
        shareMapper.deleteByPrimaryKey(userId,shareId,deviceId);
    }


}
