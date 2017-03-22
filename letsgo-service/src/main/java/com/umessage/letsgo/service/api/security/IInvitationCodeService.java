package com.umessage.letsgo.service.api.security;

import com.umessage.letsgo.domain.po.security.InvitationCodeEntity;

import java.util.List;

/**
 * Created by wendy on 2016/8/24.
 */
public interface IInvitationCodeService {
    int add(InvitationCodeEntity invitationCodeEntity);

    int addList(List<InvitationCodeEntity> invitationCodeEntities);

    int update(InvitationCodeEntity invitationCodeEntity);

    InvitationCodeEntity getLatestInvitationCode();

    void createAndSaveInvitationCode();

    InvitationCodeEntity select(String code);
}
