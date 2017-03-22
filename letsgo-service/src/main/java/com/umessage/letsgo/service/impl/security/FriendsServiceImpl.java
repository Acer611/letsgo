package com.umessage.letsgo.service.impl.security;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.dao.security.FriendsDao;
import com.umessage.letsgo.domain.po.security.FriendsEntity;
import com.umessage.letsgo.domain.po.team.LeaderEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.request.FriendRequest;
import com.umessage.letsgo.domain.vo.security.respone.FriendListResponse;
import com.umessage.letsgo.service.api.security.IFriendsService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.team.ILeaderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by zengguoqing on 2016/9/6.
 */
@Service
public class FriendsServiceImpl implements IFriendsService {
    @Resource
    private FriendsDao friendsDao;
    @Resource
    private IUserService userServiceImpl;
    @Resource
    private ILeaderService leaderServiceImpl;

    /*
    @Transactional
    @Override
    public CommonResponse saveFriend(FriendsEntity friendsEntity) {
        CommonResponse commonResponse=new CommonResponse();
        try {
            AddFriendRequest addFriendRequest=new AddFriendRequest();
            List<AddFriendItem> li=new ArrayList<>();
            AddFriendItem addFriendItem=new AddFriendItem();
            addFriendItem.setToAccount(friendsEntity.getFriendTxUserId());
            addFriendItem.setAddSource("AddSource_Type_t");
            li.add(addFriendItem);
            addFriendRequest.setFromAccount(friendsEntity.getTxUserId());
            addFriendRequest.setAddFriendItem(li);
            AddFriendResponse addFriendResponse=timRestAPI.addFriend(addFriendRequest);

            if (!addFriendResponse.getActionStatus().equals("OK")){
                commonResponse.setRetCode(1);
                commonResponse.setRetMsg("好友添加失败");
                return commonResponse;
            }

            friendsEntity.setCreateTime(new Date());
            friendsEntity.setVersion(0l);


            MemberEntity mem=new MemberEntity();
            mem.settId(friendsEntity.getTeamId());
            mem.setUserId(friendsEntity.getUserId());

            MemberEntity user=memberDao.selectByTeamId(mem);
            if(user.getRole()==3){
                friendsEntity.setUserRole(0);
            }
            else{
                friendsEntity.setUserRole(1);
            }

            mem.setUserId(friendsEntity.getFriendUserId());
            MemberEntity friend=memberDao.selectByTeamId(mem);
            if(friend.getRole()==3){
                friendsEntity.setFriendRole(0);
            }
            else{
                friendsEntity.setFriendRole(1);
            }
            Long friendUserId=userDao.findUserId(friendsEntity.getFriendTxUserId());
            friendsEntity.setFriendType(friendsEntity.getFriendRole());
            friendsEntity.setFriendUserId(friendUserId);
            friendsDao.saveFriend(friendsEntity);
            friendsEntity.setUserId(friendUserId);
            friendsEntity.setFriendUserId(friendsEntity.getUserId());
            friendsEntity.setFriendType(friendsEntity.getUserRole());
            friendsDao.saveFriend(friendsEntity);
            commonResponse.setRetCode(0);
            commonResponse.setRetMsg("好友添加成功");
        }
        catch (Exception e){
            commonResponse.setRetMsg("好友添加失败");
            commonResponse.setRetCode(1);
        }
        return commonResponse;
    }

    @Transactional
    @Override
    public CommonResponse saveFriendTwo(FriendsEntity friendsEntity) {
        CommonResponse commonResponse=new CommonResponse();
        try {
            AddFriendRequest addFriendRequest=new AddFriendRequest();
            List<AddFriendItem> li=new ArrayList<>();
            AddFriendItem addFriendItem=new AddFriendItem();
            addFriendItem.setToAccount(friendsEntity.getFriendTxUserId());
            addFriendItem.setAddSource("AddSource_Type_o");
            li.add(addFriendItem);
            addFriendRequest.setFromAccount(friendsEntity.getTxUserId());
            addFriendRequest.setAddFriendItem(li);
            AddFriendResponse addFriendResponse=timRestAPI.addFriend(addFriendRequest);
            if(addFriendResponse.getActionStatus().equals("OK")){
                friendsEntity.setCreateTime(new Date());
                friendsEntity.setVersion(0l);
                Long friendUserId=userDao.findUserId(friendsEntity.getFriendTxUserId());
                UserEntity user=userDao.select(friendUserId);
                if(user.getGuideStatus()==1){
                    friendsEntity.setFriendType(1);
                }
                else{
                    friendsEntity.setFriendType(0);
                }
                friendsEntity.setFriendUserId(friendUserId);
                friendsDao.saveFriend(friendsEntity);
                friendsEntity.setUserId(friendUserId);
                friendsEntity.setFriendUserId(friendsEntity.getUserId());
                UserEntity friend=userDao.select(friendsEntity.getUserId());
                if(friend.getGuideStatus()==1){
                    friendsEntity.setFriendType(1);
                }
                else{
                    friendsEntity.setFriendType(0);
                }
                friendsDao.saveFriend(friendsEntity);
                commonResponse.setRetCode(0);
                commonResponse.setRetMsg("好友添加成功");
            }
          else {
                commonResponse.setRetCode(1);
                commonResponse.setRetMsg("好友添加失败");
            }
        }
        catch (Exception e){
            commonResponse.setRetMsg("好友添加失败");
            commonResponse.setRetCode(1);
        }
        return commonResponse;
    }
    */

    @Override
    public FriendListResponse getAllFriends(Long userId, Integer friendType) {
        FriendListResponse f = new FriendListResponse();
        try{
            List<FriendsEntity> allFriends =friendsDao.getAllFriends(userId, friendType);
            f.setAllFriends(allFriends);
            if (CollectionUtils.isEmpty(allFriends)){
                f.setRetMsg("当前无好友数据");
            }
        }
        catch (Exception e){
            f.setRetCode(ErrorConstant.INTERNAL_SERVER_ERROR);
            f.setRetMsg("查询好友失败");
        }
        return f;
    }


    /**
     * 通过UserID获取好友
     * @param userId
     * @param friendTXUserId
     * @return
     */
    @Override
    public FriendsEntity getFriendByUserId(Long userId, String friendTXUserId) {
        FriendsEntity friendsEntity = friendsDao.getFriendByUserId(userId, friendTXUserId);
        return friendsEntity;
    }

    @Transactional
    @Override
    public CommonResponse saveFriend(FriendRequest friendRequest) {
        CommonResponse commonResponse=new CommonResponse();

        FriendsEntity selfFriendsEntity  = null;
        FriendsEntity otherFriendsEntity  = null;
        Integer friendType = 0;
        try {
            // 查找朋友用户id
            Long friendUserId = userServiceImpl.findUserId(friendRequest.getFriendTxUserId());
            friendRequest.setFriendUserId(friendUserId);

            // 查找自身好友关系
            selfFriendsEntity = getFriendByUserId(friendRequest.getUserId(), friendRequest.getFriendTxUserId());
            // 查找对方好友关系
            otherFriendsEntity = getFriendByUserId(friendRequest.getUserId(), friendRequest.getFriendTxUserId());
            // 查找是否同行
            friendType = getFriendType(friendRequest);
        }
        catch (Exception e){
            commonResponse.setRetMsg("查找好友关系失败");
            commonResponse.setRetCode(ErrorConstant.INTERNAL_SERVER_ERROR);
            return commonResponse;
        }

        try {
            // 保存自方朋友关系
            if (selfFriendsEntity == null){
                saveSelfFriend(friendRequest, friendType);
            }

            // 保存对方朋友关系
            if (otherFriendsEntity == null){
                saveOtherFriend(friendRequest, friendType);
            }

            commonResponse.setRetCode(0);
            commonResponse.setRetMsg("好友添加成功");
        }
        catch (Exception e){
            commonResponse.setRetMsg("好友添加失败");
            commonResponse.setRetCode(ErrorConstant.INTERNAL_SERVER_ERROR);
        }
        return commonResponse;
    }

    /**
     * 获取好友类型
     * @param friendRequest
     * @return 朋友类型, 0表示游客, 1表示同行
     */
    private Integer getFriendType(FriendRequest friendRequest){
        LeaderEntity otherLeader = leaderServiceImpl.getLeaderByUserId(friendRequest.getFriendUserId());
        if (otherLeader == null){
            return  0;
        }

        LeaderEntity selfLeader = leaderServiceImpl.getLeaderByUserId(friendRequest.getUserId());
        if (selfLeader == null){
            return 0;
        }

        return 1;
    }

    /**
     * 保存自身好友关系
     * @param friendRequest
     * @param friendType
     */
    private void saveSelfFriend(FriendRequest friendRequest, Integer friendType){
        FriendsEntity friendsEntity = new FriendsEntity();
        friendsEntity.setUserId(friendRequest.getUserId());
        friendsEntity.setTxUserId(friendRequest.getTxUserId());
        friendsEntity.setFriendUserId(friendRequest.getFriendUserId());
        friendsEntity.setFriendTxUserId(friendRequest.getFriendTxUserId());
        friendsEntity.setFriendType(friendType);

        friendsEntity.setCreateTime(new Date());
        friendsEntity.setVersion(0l);

        friendsDao.saveFriend(friendsEntity);
    }

    /**
     * 保存对方大好友关系
     * @param friendRequest
     * @param friendType
     */
    private void saveOtherFriend(FriendRequest friendRequest,  Integer friendType){
        FriendsEntity friendsEntity = new FriendsEntity();
        friendsEntity.setUserId(friendRequest.getFriendUserId());
        friendsEntity.setTxUserId(friendRequest.getFriendTxUserId());

        friendsEntity.setFriendUserId(friendRequest.getUserId());
        friendsEntity.setFriendTxUserId(friendRequest.getTxUserId());
        friendsEntity.setFriendType(friendType);

        friendsEntity.setCreateTime(new Date());
        friendsEntity.setVersion(0l);
        friendsDao.saveFriend(friendsEntity);
    }

}
