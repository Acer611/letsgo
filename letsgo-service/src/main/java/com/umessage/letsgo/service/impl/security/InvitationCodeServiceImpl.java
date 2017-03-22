package com.umessage.letsgo.service.impl.security;

import com.umessage.letsgo.dao.security.InvitationCodeDao;
import com.umessage.letsgo.domain.po.security.InvitationCodeEntity;
import com.umessage.letsgo.service.api.security.IInvitationCodeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wendy on 2016/8/24.
 */
@Service
public class InvitationCodeServiceImpl implements IInvitationCodeService {

    @Resource
    private InvitationCodeDao invitationCodeDao;

    @Override
    public int add(InvitationCodeEntity invitationCodeEntity) {
        invitationCodeEntity.setCreateTime(new Date());
        invitationCodeEntity.setVersion(0l);
        return invitationCodeDao.insert(invitationCodeEntity);
    }

    @Override
    public int addList(List<InvitationCodeEntity> invitationCodeEntities) {
        for (InvitationCodeEntity invitationCodeEntity : invitationCodeEntities) {
            invitationCodeEntity.setCreateTime(new Date());
            invitationCodeEntity.setVersion(0l);
        }
        return invitationCodeDao.insertBatch(invitationCodeEntities);
    }

    @Override
    public int update(InvitationCodeEntity invitationCodeEntity) {
        invitationCodeEntity.setUpdateTime(new Date());
        return invitationCodeDao.update(invitationCodeEntity);
    }

    @Override
    public InvitationCodeEntity getLatestInvitationCode() {
        List<InvitationCodeEntity> codeList = invitationCodeDao.selectLatest();
        if (CollectionUtils.isEmpty(codeList)) {
            return new InvitationCodeEntity();
        }
        return codeList.get(0);
    }


    @Override
    public void createAndSaveInvitationCode() {
        List<InvitationCodeEntity> codes = new ArrayList<>();

        String lastCode = "";
        List<String> codeList = this.creatInvitationCode(lastCode);
        for (String code : codeList) {
            InvitationCodeEntity codeEntity = new InvitationCodeEntity();
            codeEntity.setCode(code);
            codes.add(codeEntity);
        }

        this.addList(codes);
    }

    /**
     * 生成邀请码算法
     * 规则：每次插入10000条数据，插入前可从数据库中获取最新一条邀请码
     */
    private List<String> creatInvitationCode(String lastCode) {
        List<String> codes = new ArrayList<>();
        char[] seeds = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        int index = 0;
        if (!StringUtils.isEmpty(lastCode)) {
            char c = lastCode.charAt(0);
            for (int k = 0; k < seeds.length; k++) {
                if (c == seeds[k]) {
                    index = k + 1;
                    break;
                }
            }
        }

        for (int i=index; i<seeds.length; i++) {
            for (int j=0; j<seeds.length; j++) {
                for (int m=0; m<seeds.length; m++) {
                    for (int n=0; n<seeds.length; n++) {
                        String code = String.valueOf(seeds[i])
                                + String.valueOf(seeds[j])
                                + String.valueOf(seeds[m])
                                + String.valueOf(seeds[n]);
                        codes.add(code);
                    }
                }
            }
            break;
        }

        return codes;
    }

    /**
     * 根据code查询邀请码
     * @param code
     * @return
     */
    public InvitationCodeEntity select(String code){
        InvitationCodeEntity invitationCodeEntity = invitationCodeDao.select(code);
        return invitationCodeEntity;
    }

}
