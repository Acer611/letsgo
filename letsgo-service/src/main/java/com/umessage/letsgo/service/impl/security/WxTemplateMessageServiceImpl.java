package com.umessage.letsgo.service.impl.security;

import com.umessage.letsgo.dao.security.WxTemplateMessageDao;
import com.umessage.letsgo.domain.po.security.WxTemplateMessageEntity;
import com.umessage.letsgo.service.api.security.IWxTemplateMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class WxTemplateMessageServiceImpl implements IWxTemplateMessageService {
    @Resource 
    private WxTemplateMessageDao wxTemplateMessageDao;

    @Override
    public int delete(Long id) {
        return wxTemplateMessageDao.delete(id);
    }

    @Override
    public int insert(WxTemplateMessageEntity wxTemplateMessageEntity) {
        wxTemplateMessageEntity.setCreateTime(new Date());
        return wxTemplateMessageDao.insert(wxTemplateMessageEntity);
    }

    @Override
    public WxTemplateMessageEntity select(Long id) {
        return wxTemplateMessageDao.select(id);
    }

    @Override
    public List<WxTemplateMessageEntity> selectAll() {
        return wxTemplateMessageDao.selectAll();
    }

    @Override
    public int update(WxTemplateMessageEntity wxTemplateMessageEntity) {
        return wxTemplateMessageDao.update(wxTemplateMessageEntity);
    }
}
