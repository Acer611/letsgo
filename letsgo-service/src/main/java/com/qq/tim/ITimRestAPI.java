package com.qq.tim;

import com.qq.tim.vo.request.*;
import com.qq.tim.vo.response.*;

/**
 * Created by Administrator on 2016/4/27.
 */
public interface ITimRestAPI {

    CommonResponse registerAccount(AccountImportRequest request);

    CreateGroupResponse createGroup(CreateGroupRequest request);

    /**
     * 增加群组成员
     */
    AddGroupMemberResponse addGroupMember(AddGroupMemberRequest request);
    /**
     * 删除群组成员
     */
    CommonResponse deleteGroupMember(DeleteGroupMemberRequest request);

    /**
     * 解散群组
     */
    CommonResponse destroyGroup(DestroyGroupRequest request);

    CommonResponse sendGroupMsg(SendGroupMsgRequest request);

    GetJoinedGroupListResponse getJoinedGroupList(GetJoinedGroupListRequest request);

    CommonResponse modifyGroupBaseInfo(ModifyGroupBaseInfoRequest request);

    CommonResponse modifyGroupMemberInfo(ModifyGroupMemberInfoRequest request);

    CommonResponse sendmsg(SendMsgRequest request);

    AddFriendResponse addFriend(AddFriendRequest request);

    PortraitSetResponse portraitSetImage(String userName, String photoUrl);

    PortraitSetResponse portraitSetAllowType(String userName);

}
