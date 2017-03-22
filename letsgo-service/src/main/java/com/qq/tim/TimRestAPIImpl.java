package com.qq.tim;

import com.qq.tim.helper.TimRestURLHelper;
import com.qq.tim.vo.request.*;
import com.qq.tim.vo.response.*;
import com.umessage.letsgo.service.common.constant.Constant;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/27.
 */
@Service
public class TimRestAPIImpl implements ITimRestAPI {
    @Resource
    private  RestTemplate restTemplate;

    /**
     * 独立模式帐号同步接口
     * @param request
     * @return
     */
    @Override
    public CommonResponse registerAccount(AccountImportRequest request) {
        String url = TimRestURLHelper.getRestURL("im_open_login_svc", "account_import");

        // 向腾讯云通讯后台发送请求
        CommonResponse result = restTemplate.postForObject(url, request, CommonResponse.class);

        System.out.println(result.toString());
        return result;
    }

    /**
     * 创建群
     * @param request
     * @return
     */
    @Override
    public CreateGroupResponse createGroup(CreateGroupRequest request) {
        String url = TimRestURLHelper.getRestURL("group_open_http_svc","create_group");

        // 向腾讯云通讯后台发送请求
        CreateGroupResponse result = restTemplate.postForObject(url, request, CreateGroupResponse.class);
        return result;
    }

    /**
     * 增加群组成员
     * @param request
     * @return
     */
    @Override
    public AddGroupMemberResponse addGroupMember(AddGroupMemberRequest request) {
        String url = TimRestURLHelper.getRestURL("group_open_http_svc", "add_group_member");

        // 向腾讯云通讯后台发送请求
        AddGroupMemberResponse result = restTemplate.postForObject(url, request, AddGroupMemberResponse.class);
        return result;
    }

    @Override
    public CommonResponse deleteGroupMember(DeleteGroupMemberRequest request) {
        String url = TimRestURLHelper.getRestURL("group_open_http_svc","delete_group_member");
        return restTemplate.postForObject(url,request,CommonResponse.class);
    }

    @Override
    public CommonResponse destroyGroup(DestroyGroupRequest request) {
        String url = TimRestURLHelper.getRestURL("group_open_http_svc","destroy_group");
        return restTemplate.postForObject(url,request,CommonResponse.class);
    }

    /**
     * 在群组中发送普通消息(自定义消息)
     * @param request
     * @return
     */
    @Override
    public CommonResponse sendGroupMsg(SendGroupMsgRequest request) {
        String url = TimRestURLHelper.getRestURL("group_open_http_svc", "send_group_msg");

        CommonResponse result = restTemplate.postForObject(url, request, CommonResponse.class);
        return result;
    }

    /**
     * 获取用户所加入的群组
     * @param request
     * @return
     */
    @Override
    public GetJoinedGroupListResponse getJoinedGroupList(GetJoinedGroupListRequest request) {
        String url = TimRestURLHelper.getRestURL("group_open_http_svc", "get_joined_group_list");

        GetJoinedGroupListResponse result = restTemplate.postForObject(url, request, GetJoinedGroupListResponse.class);
        return result;
    }

    /**
     * 修改群组基础资料
     * @param request
     * @return
     */
    @Override
    public CommonResponse modifyGroupBaseInfo(ModifyGroupBaseInfoRequest request) {
        String url = TimRestURLHelper.getRestURL("group_open_http_svc", "modify_group_base_info");
        CommonResponse response = restTemplate.postForObject(url, request, CommonResponse.class);
        return response;
    }

    /**
     * 修改群成员资料
     * @param request
     * @return
     */
    @Override
    public CommonResponse modifyGroupMemberInfo(ModifyGroupMemberInfoRequest request) {
        String url = TimRestURLHelper.getRestURL("group_open_http_svc", "modify_group_member_info");
        CommonResponse response = restTemplate.postForObject(url, request, CommonResponse.class);
        return response;
    }

    /**
     * 单发单聊消息
     * @param request
     * @return
     */
    @Override
    public CommonResponse sendmsg(SendMsgRequest request) {
        String url = TimRestURLHelper.getRestURL("openim", "sendmsg");
        CommonResponse response = restTemplate.postForObject(url, request, CommonResponse.class);
        return response;
    }

    /**
     * 添加好友
     * @param request
     * @return
     */
    @Override
    public AddFriendResponse addFriend(AddFriendRequest request) {
        String url = TimRestURLHelper.getRestURL("sns", "friend_import");
        AddFriendResponse response = restTemplate.postForObject(url, request, AddFriendResponse.class);
        return response;
    }



    /**
     * 腾讯云通信设置资料_设置头像URL
     * @param userName,photoUrl
     * @return
     */
    @Override
    public PortraitSetResponse portraitSetImage(String userName, String photoUrl) {
        PortraitSetRequest request = new PortraitSetRequest();
        request.setFromAccount(userName);

        List<ProfileItem> plist = new ArrayList<ProfileItem>();
        ProfileItem p = new ProfileItem();
        //设置腾讯云通信支持的标配资料字段
        p.setTag(Constant.Tag_Profile_IM_Image);
        //设置图片地址
        p.setValue(photoUrl);
        plist.add(p);
        request.setProfileItem(plist);

        String url = TimRestURLHelper.getRestURL("profile","portrait_set");

        // 向腾讯云通讯后台发送请求
        PortraitSetResponse result = restTemplate.postForObject(url, request, PortraitSetResponse.class);
        return result;
    }


    /**
     * 腾讯云通信设置资料_设置加好友验证方式为AllowType_Type_NeedConfirm
     * @param userName,allowType
     * @return
     */
    @Override
    public PortraitSetResponse portraitSetAllowType(String userName) {
        PortraitSetRequest request = new PortraitSetRequest();
        request.setFromAccount(userName);

        List<ProfileItem> plist = new ArrayList<ProfileItem>();
        ProfileItem p = new ProfileItem();
        //设置腾讯云通信支持的标配资料字段
        p.setTag(Constant.Tag_Profile_IM_AllowType);
        //设置图片地址
        p.setValue("AllowType_Type_NeedConfirm");
        plist.add(p);
        request.setProfileItem(plist);

        String url = TimRestURLHelper.getRestURL("profile","portrait_set");

        // 向腾讯云通讯后台发送请求
        PortraitSetResponse result = restTemplate.postForObject(url, request, PortraitSetResponse.class);
        return result;
    }


}
