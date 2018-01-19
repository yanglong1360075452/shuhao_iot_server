package com.wizinno.shuhao.share.service;

import com.wizinno.shuhao.share.data.ShareDto;

import java.util.List;

public interface ShareService {
    public void save(ShareDto shareDto);

    public ShareDto query(Long userId,Long shareId,Long deviceId);

    public List<ShareDto> findByOrder(Long userId,Long shareId,Long deviceId);

    public void deleteShare(Long userId,Long shareId,Long deviceId);


}
