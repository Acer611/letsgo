package com.umessage.letsgo.dao.security;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.umessage.letsgo.domain.po.security.ThirdPartyAccountEntity;
import com.umessage.letsgo.domain.po.security.WxInfoEntity;
import com.umessage.letsgo.domain.po.security.WxTeamEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengguoqing on 2016/11/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml", "classpath:spring/spring-mybatis.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@Transactional
public class WxDaoTest {
    @Resource
    private WxInfoDao wxInfoDao;
    @Resource
    private WxTeamDao wxTeamDao;
    @Resource
    private ThirdPartyAccountDao thirdPartyAccountDao;

    @Test
    @DatabaseSetup("wxInfoData.xml")
    public void selectUserInfoByUnionIDTest() {
        WxInfoEntity wxInfoEntity = wxInfoDao.selectUserInfoByUnionID("oFtvpwGOUXke-GwJFJHDPQgF4OHk");
        Assert.assertNotNull(wxInfoEntity);
        Assert.assertEquals("oFtvpwGOUXke-GwJFJHDPQgF4OHk",wxInfoEntity.getUnionID());
    }

    @Test
    @DatabaseSetup("wxTeamData.xml")
    public void selectWxTeamInfoListByUnionidTest() {
        List<WxTeamEntity> wxTeamList = wxTeamDao.selectWxTeamInfoListByUnionid("oFtvpwGOUXke-GwJFJHDPQgF4OHk");
        for (WxTeamEntity wxTeam:wxTeamList) {
            Assert.assertNotNull(wxTeam);
            Assert.assertEquals("oFtvpwGOUXke-GwJFJHDPQgF4OHk",wxTeam.getUnionid());
        }
    }

    @Test
    @DatabaseSetup("thirdPartyData.xml")
    public void selectThirdPartyInfoByUnionIDTest() {
        ThirdPartyAccountEntity thirdPartyAccountEntity = thirdPartyAccountDao.selectThirdPartyInfoByUnionID("oFtvpwGOUXke-GwJFJHDPQgF4OHk");
        Assert.assertNotNull(thirdPartyAccountEntity);
        Assert.assertEquals("oFtvpwGOUXke-GwJFJHDPQgF4OHk",thirdPartyAccountEntity.getUnionID());
    }



}
