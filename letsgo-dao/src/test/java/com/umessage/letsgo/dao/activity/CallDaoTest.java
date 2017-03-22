package com.umessage.letsgo.dao.activity;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.umessage.letsgo.domain.po.activity.CallEntity;
import com.umessage.letsgo.domain.vo.activity.request.CallRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
		"classpath:spring/spring-mybatis.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	TransactionDbUnitTestExecutionListener.class })
@Transactional
@DatabaseSetup("callData.xml")
public class CallDaoTest {
	
	@Resource
	private CallDao callDao;

	@Test
	public void testSelectCallListWithConditons() {
//		fail("Not yet implemented");
		CallRequest request = new CallRequest();
//		request.setTeamId(1l);
		request.setStatus(0);
		List<CallEntity> list = callDao.selectCallListWithConditons(request);
		
//		Assert.assertEquals(1, list.size());
		
//		Assert.assertNull(list);
	}

	@Test
	public void testSelectCallWithId() {
//		fail("Not yet implemented");
		CallRequest request = new CallRequest();
		request.setStatus(0);
		request.setId(1001L);
		CallEntity callEntity = callDao.selectCallWithIdAndStatus(request);
		
		Assert.assertNotNull(callEntity);
//		Assert.assertEquals(1, (int)callEntity.getCallDetailList().size());
	}

	@Test
	public void test(){
		String s = "008615135120909";
		String str =s.substring(s.length()-11);
		System.out.println(str);
	}

	@Test
	public void testPreAndSuf(){
		String field = "ID, jour_id, day_num, schedule_date, start_place, destination1, destination2,"
				+"destination3, destination4, schedule_photos_url1, schedule_photos_url2, schedule_photos_url3,"
				+"desc, hotel_confirm, hotel, hotel_input, same_level, flight1, flight2, traffic_info,"
				+"shopp_info, catering_info, leader_id, new_entry, new_contant, create_time, update_time,version";

		String prefixField = addPrefix(field,"sd.");
		System.out.println(prefixField);

		String suffixField = addSuffix(field," AS sd_");
		System.out.println(suffixField);

		String preAndSuf = addPrefix(suffixField,"sd.");
		System.out.println(preAndSuf);
	}

	public String addPrefix(String fieldString , String Prefix){
		String[] fieldArray = fieldString.split(",");
		StringBuilder sb = new StringBuilder();
		for (String field : fieldArray) {
			sb.append(Prefix);
			sb.append(field.trim());
			sb.append(",");
		}
		return sb.substring(0,sb.length()-1);
	}

	public String addSuffix(String fieldString , String suffix){
		String[] fieldArray = fieldString.split(",");
		StringBuilder sb = new StringBuilder();
		for (String field : fieldArray) {
			String fieldTrim =field.trim();
			sb.append(fieldTrim);
			sb.append(suffix);
			sb.append(fieldTrim);
			sb.append(",");
		}
		return sb.substring(0,sb.length()-1);
	}

}
