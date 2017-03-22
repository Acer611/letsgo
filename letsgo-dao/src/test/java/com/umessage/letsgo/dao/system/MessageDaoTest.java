package com.umessage.letsgo.dao.system;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.umessage.letsgo.domain.po.system.MessageEntity;
import com.umessage.letsgo.domain.vo.system.request.MessageRequest;
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
 * Created by Administrator on 2016/5/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
        "classpath:spring/spring-mybatis.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@Transactional
public class MessageDaoTest {

    @Resource
    private MessageDao messageDao;

    @Test
    @DatabaseSetup("messageData.xml")
    public void testUpdateValidWithPhone() throws Exception {
//        MessageEntity before_message = messageDao.select(10001l);
//        Assert.assertEquals(1, (int)before_message.getValid());

        int result = messageDao.updateValidWithPhone("18776887282");

//        Assert.assertEquals(1, result);

        MessageEntity after_message = messageDao.select(10001l);
        Assert.assertEquals("18776887282", after_message.getPhone());
        Assert.assertEquals(0, (int)after_message.getValid());
    }

    @Test
    @DatabaseSetup("messageData.xml")
    public void testSelectMessageListWithCondition() throws Exception {
        MessageRequest req = new MessageRequest();
        req.setPhone("18776887282");
        req.setValid(1);
        req.setMark("CODE");

        List<MessageEntity> messageList = messageDao.selectMessageListWithCondition(req);

        Assert.assertNotNull(messageList);
        Assert.assertEquals(1, messageList.size());
    }
}