package com.umessage.letsgo.service.api.team;

import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.team.requset.WebMemberRequest;
import com.umessage.letsgo.domain.vo.team.respone.WebMemberResponse;

import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/12.
 */
public interface IWebMemberService {

    // 根据查询条件，获取成员列表
    WebMemberResponse getMemberList(WebMemberRequest request);

    // 添加游客
    int memberAdd(MemberEntity memberEntity);

    // 查看游客
    MemberEntity memberPreview(Long id);

    // 修改游客
    int memberUpdate(MemberEntity memberEntity);
    // 删除游客
    CommonResponse memberDelete(Long id);

    // 导出游客excel信息
    byte[] getMemberExcel(List<MemberEntity> memberList);

    // 文件模板下载
    void fileDownload();

    // 导入游客excel信息
    WebMemberResponse importMemberExcel(String filePath, Long tId);

    //通过id集合查询游客(无user实体信息)。
    List<MemberEntity> selectByIds(List<Long> ids);

    //获得唯一名称
    String getUniqueName();
    WebMemberResponse memberAddAjax(MemberEntity member);

    //新增 领队导游 添加日志
    int addMemberExportExceLog(TravelAgencyEntity travel);
}
