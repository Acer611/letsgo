package com.umessage.letsgo.service.impl.activity

import com.umessage.letsgo.dao.activity.RoomDao
import com.umessage.letsgo.domain.po.activity.RoomDetailEntity
import com.umessage.letsgo.domain.po.activity.RoomEntity
import com.umessage.letsgo.domain.po.team.GroupEntity
import com.umessage.letsgo.domain.po.team.MemberEntity
import com.umessage.letsgo.service.api.activity.IRoomDetailService
import com.umessage.letsgo.service.api.team.ITeamService
import org.dozer.Mapper
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by ZhaoYidong on 2016/5/10.
 */
class RoomServiceImplSpec extends Specification{

    private RoomServiceImpl roomService;
    private RoomDao roomMapper;
    private ITeamService teamService;
    private IRoomDetailService roomDetailService;
    private Mapper dozerBeanMapper;
    private List<RoomEntity> roomList = new ArrayList<RoomEntity>();
    private List<GroupEntity> groupList = new ArrayList<GroupEntity>();
    private List<MemberEntity> memberList = new ArrayList<MemberEntity>();
    @Unroll("getTeamRoomDetailList #teamId")
    def "get team room detail list test"(){
        given:
        //房间详细。
        RoomDetailEntity roomDetailEntity1 = new RoomDetailEntity(id:1L,roomId: 1L,groupId: 1L,groupName: "逗B组", memberId:1L,memberName:"name1",memberSex: 1);
        RoomDetailEntity roomDetailEntity2 = new RoomDetailEntity(id:2L,roomId: 1L,groupId: 1L,groupName: "逗B组", memberId:2L,memberName:"name2",memberSex: 2);
        List<RoomDetailEntity> detailList = new ArrayList<RoomDetailEntity>();
        detailList.add(roomDetailEntity1);
        detailList.add(roomDetailEntity2);

        RoomEntity roomEntity = new RoomEntity(id:1,teamId: 1,roomNum: 1,count: 1,roomType:1,totalCardCount:2,cardCount:1,roomDetailList: detailList);
        roomList.add(roomEntity);

        //已分组，并已分房的成员。
        MemberEntity memberEntity1 = new MemberEntity(id:1L,realName: "name1",teamId:1L,groupId: 1L);

        List<MemberEntity> list = new ArrayList<MemberEntity>();
        list.add(memberEntity1);
        GroupEntity groupEntity = new GroupEntity(id:1L,teamId: 1L, name: "逗B组",memberList: list);
        groupList.add(groupEntity);

        //已分组，未分房的成员。
        MemberEntity memberEntity4 = new MemberEntity(id:4L,realName: "name4",teamId:1L,groupId: 2L);
        List<MemberEntity> list4 = new ArrayList<MemberEntity>();
        list4.add(memberEntity4);
        GroupEntity groupEntity2 = new GroupEntity(id:2L,teamId: 1L, name: "传奇组",memberList: list4);
        groupList.add(groupEntity2);

        //未分组，已分房的成员
        MemberEntity memberEntity2 = new MemberEntity(id:2L,realName: "name2",teamId:1L,groupId: -1L);
        //未分组，没有分房的成员。
        MemberEntity memberEntity3 = new MemberEntity(id:3L,realName: "name3",teamId:1L,groupId: -1L);
        memberList.add(memberEntity2)
        memberList.add(memberEntity3);

        roomMapper=Mock(RoomDao.class)
        roomMapper.selectRoomListByTeamId(1)>> roomList;
        roomMapper.selectRoomListByTeamId(2)>> Collections.emptyList();

        teamService=Mock(ITeamService.class)
        teamService.fetchGroupDetailListByTeamId(1)>> groupList;
        teamService.fetchNotInGroupMemberListByTeamId(1) >> memberList;

        teamService.fetchGroupDetailListByTeamId(2)>> groupList;
        teamService.fetchNotInGroupMemberListByTeamId(2) >> memberList;

        roomService = new RoomServiceImpl(roomMapper: roomMapper,teamService: teamService);

        when:
        def roomResponse = roomService.getTeamRoomDetailList(teamId);

        then:
        roomResponse.getRoomList().size() == rooms
        roomResponse.getGroupList().size() == groups
        roomResponse.getPersonalList().size() == members
        where:
        teamId || rooms | groups |members
        1      || 1     | 1      |1
        2      || 0     | 2      |2
    }

    @Unroll("fetchGroupsOfNotRoom #groupList,roomList")
    def "fetch group of not room"(){
        given:
        MemberEntity memberEntity1 = new MemberEntity(id:1L,realName: "name1",teamId:1L,groupId: 1L);
        MemberEntity memberEntity2 = new MemberEntity(id:2L,realName: "name2",teamId:1L,groupId: 1L);
        MemberEntity memberEntity3 = new MemberEntity(id:3L,realName: "name3",teamId:1L,groupId: 1L);

        List<MemberEntity> list = new ArrayList<MemberEntity>();
        list.add(memberEntity1);
        list.add(memberEntity2);
        list.add(memberEntity3);
        GroupEntity groupEntity = new GroupEntity(id:1L,teamId: 1L, name: "逗B组",memberList: list);

        MemberEntity memberEntity4 = new MemberEntity(id:4L,realName: "name4",teamId:1L,groupId: 2L);
        MemberEntity memberEntity5 = new MemberEntity(id:5L,realName: "name5",teamId:1L,groupId: 2L);
        MemberEntity memberEntity6 = new MemberEntity(id:6L,realName: "name6",teamId:1L,groupId: 2L);

        List<MemberEntity> list2 = new ArrayList<MemberEntity>();
        list2.add(memberEntity4);
        list2.add(memberEntity5);
        list2.add(memberEntity6);
        GroupEntity groupEntity2 = new GroupEntity(id:2L,teamId: 1L, name: "逗B组",memberList: list2);

        RoomDetailEntity roomDetailEntity1 = new RoomDetailEntity(id:1L,roomId: 1L,groupId: 1L,groupName: "逗B组", memberId:1L,memberName:"name1",memberSex: 1);
        RoomDetailEntity roomDetailEntity2 = new RoomDetailEntity(id:2L,roomId: 1L,groupId: 1L,groupName: "逗B组", memberId:2L,memberName:"name2",memberSex: 2);
        RoomDetailEntity roomDetailEntity3 = new RoomDetailEntity(id:3L,roomId: 1L,groupId: 1L,groupName: "逗B组", memberId:3L,memberName:"name2",memberSex: 2);
        List<RoomDetailEntity> detailList = new ArrayList<RoomDetailEntity>();
        detailList.add(roomDetailEntity1);
        detailList.add(roomDetailEntity2);
        detailList.add(roomDetailEntity3);

        RoomEntity roomEntity = new RoomEntity(id:1,teamId: 1,roomNum: 1,count: 1,roomType:1,totalCardCount:2,cardCount:1,roomDetailList: detailList);
        roomList.add(roomEntity);
        groupList.add(groupEntity);
        groupList.add(groupEntity2);

        roomService = new RoomServiceImpl();

        when:
        def roomResponse = roomService.fetchGroupsOfNotRoom(groupList,roomService.getMemberIdList(roomList));
        then:
        roomResponse.size() == 1;
        roomResponse.get(0).getMemberList().size() == 3;
    }

    @Unroll("fetchMembersOfNotRoom #memberList,roomList")
    def "fetch members of not room test"(){
        given:
        MemberEntity memberEntity1 = new MemberEntity(id:1L,realName: "name1",teamId:1L,groupId: -1L);
        MemberEntity memberEntity2 = new MemberEntity(id:2L,realName: "name2",teamId:1L,groupId: -1L);
        MemberEntity memberEntity3 = new MemberEntity(id:3L,realName: "name3",teamId:1L,groupId: -1L);
        MemberEntity memberEntity4 = new MemberEntity(id:4L,realName: "name4",teamId:1L,groupId: -1L);
        MemberEntity memberEntity5 = new MemberEntity(id:5L,realName: "name5",teamId:1L,groupId: -1L);
        MemberEntity memberEntity6 = new MemberEntity(id:6L,realName: "name6",teamId:1L,groupId: -1L);

        List<MemberEntity> list = new ArrayList<MemberEntity>();
        list.add(memberEntity1);
        list.add(memberEntity2);
        list.add(memberEntity3);
        list.add(memberEntity4);
        list.add(memberEntity5);
        list.add(memberEntity6);

        RoomDetailEntity roomDetailEntity1 = new RoomDetailEntity(id:1L,roomId: 1L, memberId:1L,memberName:"name1",memberSex: 1);
        RoomDetailEntity roomDetailEntity2 = new RoomDetailEntity(id:2L,roomId: 1L, memberId:2L,memberName:"name2",memberSex: 2);
        RoomDetailEntity roomDetailEntity3 = new RoomDetailEntity(id:3L,roomId: 1L, memberId:3L,memberName:"name3",memberSex: 2);
        List<RoomDetailEntity> detailList = new ArrayList<RoomDetailEntity>();
        detailList.add(roomDetailEntity1);
        detailList.add(roomDetailEntity2);
        detailList.add(roomDetailEntity3);

        RoomEntity roomEntity = new RoomEntity(id:1,teamId: 1,roomNum: 1,count: 1,roomType:1,totalCardCount:2,cardCount:1,roomDetailList: detailList);
        roomList.add(roomEntity);
        roomService = new RoomServiceImpl();

        when:
        def roomResponse = roomService.fetchMembersOfNotRoom(list,roomService.getMemberIdList(roomList));
        then:
        roomResponse.size() == 3;
    }


}
