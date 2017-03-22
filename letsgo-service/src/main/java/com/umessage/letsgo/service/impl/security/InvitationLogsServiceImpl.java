package com.umessage.letsgo.service.impl.security;

import com.umessage.letsgo.dao.security.InvitationLogsDao;
import com.umessage.letsgo.domain.po.security.InvitationLogsEntity;
import com.umessage.letsgo.service.api.security.IInvitationLogsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by wendy on 2016/8/30.
 */
@Service
public class InvitationLogsServiceImpl implements IInvitationLogsService {

    @Resource
    private InvitationLogsDao invitationLogsDao;

    @Override
    public int add(InvitationLogsEntity invitationLogsEntity) {
        invitationLogsEntity.setCreateTime(new Date());
        return invitationLogsDao.insert(invitationLogsEntity);
    }
}
