/*
 * MemberDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-10 Created by Administrator
 */
package com.umessage.letsgo.dao.team;

import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.team.requset.MemberRequest;
import com.umessage.letsgo.domain.vo.team.requset.WebMemberRequest;

import java.util.List;

public interface MemberDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param memberEntity
     * @return int
     */
    int insert(MemberEntity memberEntity);

    int insertMember(MemberEntity memberEntity);
    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.team.MemberEntity
     */
    MemberEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.team.MemberEntity>
     */
    Page<MemberEntity> selectAll(WebMemberRequest request);

    /**
     * Description: 
     * @param memberEntity
     * @return int
     */
    int update(MemberEntity memberEntity);

    int updateMember(MemberEntity memberEntity);

    List<MemberEntity> selectMemberListWithConditons(MemberRequest request);

    List<Long> selectUserIdListWithConditons(MemberRequest request);

    //新的获取获取团队的游客列表（无论角色是领队还是导游发公告，除了自己本身之外，团队的其他人都要接收到公告。）
    List<MemberEntity> selectMemberListWithConditonsOne(MemberRequest request);

    MemberEntity selectMemberWithConditons(MemberRequest request);

    List<MemberEntity> selectLeaderAndGuideWithConditons(MemberRequest request);

    List<String> selectTeamIdsByUserId(Long userId);

    List<String> selectTeamIdsByUserIdAndAdmin(Long userId);

    //通过id集合查询游客(无user实体信息)。
    List<MemberEntity> selectByIds(List<Long> list);

    List<MemberEntity> selectMemberWithValidTeam(String phone);
    List<MemberEntity> getLeaderMembersByLeaderId(Long leaderId);

    List<Long> selectUserIdWithTId(Long tId);

    List<MemberEntity> selectSomeWithTIds(List<Long> tIds);

    // 根据userId获取团队ID集合
    List<Long> getTeamIds(Long userId);

    MemberEntity selectByTeamId(MemberEntity mem);

    int getMemberCountByStatus(MemberRequest request);

    //通过团ID 获取 没有手机号没有加入到团的 人员
    List<MemberEntity> getNoPhoneMembers(Long tId);

    //根据分房的房间ID查询成员
    List<MemberEntity> getByRoomId(Long roomId);

    /**
     * 根据姓名性别和团号查找用户
     * @return
     */
    MemberEntity selectMemberByNameSexAndTeamID(MemberEntity memberEntity);

    //根据行程状态和行程时间查询成员
    List<MemberEntity> selectMemberByScheduleDate(MemberRequest request);

    //根据行程详情Id查询成员
    List<MemberEntity> selectMemberByScheduleDetailId(MemberRequest request);

    //根据团队id查询所有团队成员手机号
    List<String> selectMemberByTidConditons(MemberRequest request);

    //根据行程结束日期查询行程已经结束的成员
    List<MemberEntity> selectMemberByScheduleEnd(MemberRequest request);
}