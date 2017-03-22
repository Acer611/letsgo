package com.umessage.letsgo.service.api.notice;

import com.umessage.letsgo.domain.vo.notice.respone.NoticeRespone;

/**
 * Created by ZhaoYidong on 2016/6/16.
 */
public interface IWebNoticeService {

    public NoticeRespone getNotice(Long id);
}
