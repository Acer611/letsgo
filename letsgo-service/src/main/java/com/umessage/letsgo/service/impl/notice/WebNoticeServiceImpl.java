package com.umessage.letsgo.service.impl.notice;

import com.umessage.letsgo.dao.notice.NoticeDao;
import com.umessage.letsgo.domain.po.notice.NoticeEntity;
import com.umessage.letsgo.domain.vo.notice.respone.NoticeRespone;
import com.umessage.letsgo.service.api.notice.IWebNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by ZhaoYidong on 2016/6/16.
 */
@Service
public class WebNoticeServiceImpl implements IWebNoticeService{

    @Resource
    private NoticeDao noticeDao;
    @Override
    public NoticeRespone getNotice(Long id) {
        NoticeEntity entity =noticeDao.select(id);
        if(entity == null) return NoticeRespone.createNotFoundResponse();

        NoticeRespone respone = new NoticeRespone();
        respone.setNoticeEntity(entity);
        return respone;
    }
}
