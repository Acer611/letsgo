package com.jrmf360;

import com.jrmf360.vo.request.OpenWalletRequest;
import com.jrmf360.vo.response.WalletCommonResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by zhajl on 16/9/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
        "classpath:spring/spring-mybatis.xml",
        "classpath:spring/spring-beans.xml" })
public class StandardWalletImplTest {

    @Resource
    private IStandardWallet standardWalletImpl;

    @Test
    public void testOpenWallet() throws Exception {
        OpenWalletRequest request = new OpenWalletRequest();
        request.setCustUid("fm3dmmjygm3tombzgyzdqmi");
        request.setCustMobile("+8615801376280");
        //request.setCustUid("10149");
        //request.setCustMobile("8613126862882");

        WalletCommonResponse response = standardWalletImpl.openWallet(request);
        System.out.println("响应状态:" + response.getRespstat() + ", 响应描述:" + response.getRespmsg());
    }


}
