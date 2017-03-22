package com.qq.tim;

/**
 * Created by zhajl on 2016/4/29.
 */
/*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
        "classpath:spring/spring-mybatis.xml",
        "classpath:spring/spring-beans.xml" })
public class TimRestAPIImplTest {
    @Resource
    private ITimRestAPI timRestAPIImpl;

    @Test
    public void testRegisterAccount() throws Exception {
        AccountImportRequest request = new AccountImportRequest("xiaohao", "小号", "http://www.baidu.com/pic/0001.png");;
        CommonResponse response = timRestAPIImpl.registerAccount(request);
        Assert.assertEquals(new Integer(0), response.getErrorCode());
    }

    @Test
    public void testCreateGroup() throws Exception {
//        CreateGroupRequest request = new CreateGroupRequest("lilei", "Private", "TeamTest", "http://www.baidu.com/team/pic/0001.png");

        List<AppDefinedData> appDefinedDataList = new ArrayList<AppDefinedData>();
        AppDefinedData appDefinedData = new AppDefinedData();
        appDefinedData.setKey("TripStage");//出行状态
        appDefinedData.setValue("1");
        appDefinedDataList.add(appDefinedData);

        AppDefinedData appDefinedData2 = new AppDefinedData();
        appDefinedData2.setKey("TripData");//团名称
        appDefinedData2.setValue("reserve");
        appDefinedDataList.add(appDefinedData2);

        List<MemberList> memberLists = new ManagedList<MemberList>();
        memberLists.add(new MemberList("lilei"));

        CreateGroupRequest request = new CreateGroupRequest("", "Private", "TeamAppDataTest", "http://www.baidu.com/team/pic/0001.png");
        request.setAppDefinedData(appDefinedDataList);
        request.setMemberList(memberLists);
        CreateGroupResponse response = timRestAPIImpl.createGroup(request);
        System.out.println(response.getErrorCode());

        Assert.assertEquals(new Integer(0), response.getErrorCode());
//        Assert.assertEquals("@TGS#1XWEFUAEU", response.getGroupId());
    }

    @Test
    public void createGroupTest(){
        List<AppDefinedData> appDefinedDataList = new ArrayList<AppDefinedData>();
        AppDefinedData appDefinedData = new AppDefinedData();
        appDefinedData.setKey("TripStage");//出行状态
        appDefinedData.setValue("2");
        appDefinedDataList.add(appDefinedData);

        AppDefinedData appDefinedData2 = new AppDefinedData();
        appDefinedData2.setKey("TripData");//团名称
        appDefinedData2.setValue("teamName");
        appDefinedDataList.add(appDefinedData2);
        CreateGroupRequest request = new CreateGroupRequest("", "Private", "1", "https://www.baidu.com/img/baidu_jgylogo3.gif");
        request.setAppDefinedData(appDefinedDataList);
        CreateGroupResponse response = timRestAPIImpl.createGroup(request);
        System.out.println("actionStatus:"+response.getActionStatus());
        System.out.println("errorCode:"+response.getErrorCode());
        System.out.println("errorInfo"+response.getErrorInfo());
        System.out.println("groupId:"+response.getGroupId());
        GetJoinedGroupListRequest groupRequest = new GetJoinedGroupListRequest("xiaohao");
        timRestAPIImpl.getJoinedGroupList(groupRequest);
    }
    @Test
    public void testAddGroupMember() throws Exception {
        List<MemberList> memberList = new ArrayList<MemberList>();
        MemberList member = new MemberList("xiaohao");
        memberList.add(member);
        AddGroupMemberRequest request = new AddGroupMemberRequest("@TGS#1XWEFUAEU", 1, memberList);
        AddGroupMemberResponse response = timRestAPIImpl.addGroupMember(request);

        Assert.assertEquals(new Integer(0), response.getErrorCode());
        Assert.assertEquals("xiaohao", response.getMemberList().get(0).getMemberAccount());
        Assert.assertEquals(new Integer(2), response.getMemberList().get(0).getResult());
    }

    @Test
    public void testSendGroupMsg() throws Exception {
        List<MsgBody> msgBody = new ArrayList<MsgBody>();
        MsgContent content = new MsgContent("message", "notification", "url", "dingdong.aiff");
        MsgBody body = new MsgBody("TIMCustomElem", content);
        msgBody.add(body);

        SendGroupMsgRequest request = new SendGroupMsgRequest("@TGS#17SOGUAEZ", 8912345, msgBody);
        CommonResponse response = timRestAPIImpl.sendGroupMsg(request);

        Assert.assertEquals(new Integer(0), response.getErrorCode());
    }

    @Test
    public void testGetJoinedGroupList() throws Exception {
        GetJoinedGroupListRequest request = new GetJoinedGroupListRequest("xiaohao");
        GetJoinedGroupListResponse response = timRestAPIImpl.getJoinedGroupList(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(new Integer(0) ,response.getErrorCode());
//        Assert.assertEquals(new Integer(1),response.getTotalCount());
        Assert.assertEquals("@TGS#1XWEFUAEU", response.getGroupIdList().get(0).getGroupId());
    }
}*/
