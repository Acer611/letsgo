package com.umessage.letsgo.service.impl.security;

import com.umessage.letsgo.dao.journey.ScheduleDao;
import com.umessage.letsgo.dao.security.ThirdPartyAccountDao;
import com.umessage.letsgo.dao.security.UserDao;
import com.umessage.letsgo.dao.security.WxTeamDao;
import com.umessage.letsgo.dao.team.MemberDao;
import com.umessage.letsgo.dao.team.TeamDao;
import com.umessage.letsgo.dao.security.WxInfoDao;
import com.umessage.letsgo.domain.po.security.ThirdPartyAccountEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.security.WxTeamEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.security.IThirdPartyAccountService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.team.IMemberService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pw on 2016/11/23.
 */

@Service
public class ThirdPartyAccountServiceImpl implements IThirdPartyAccountService {

    private static Logger logger = Logger.getLogger(ThirdPartyAccountServiceImpl.class);
    @Resource
    private ThirdPartyAccountDao thirdPartyAccountDao;
    @Resource
    private UserDao userDao;
    @Resource
    private ScheduleDao scheduleDao;
    @Resource
    private WxInfoDao wxInfoDao;
    @Resource
    private WxTeamDao wxTeamDao;
    @Resource
    private MemberDao memberDao;
    @Resource
    private IMemberService memberService;
    @Resource
    private TeamDao teamDao;
    @Resource
    private IUserService userService;
    @Resource
    private IScheduleService scheduleService;


    /**
     * 添加第三方账号绑定信息
     * @param thirdPartyAccountEntity
     */
    @Override
    public int create(ThirdPartyAccountEntity thirdPartyAccountEntity) {
        thirdPartyAccountEntity.setCreateTime(new Date());
        return thirdPartyAccountDao.insert(thirdPartyAccountEntity);
    }
    /**
     * 删除第三方账号绑定信息
     * @param thirdPartyAccountEntity
     */
    @Override
    public int delete(ThirdPartyAccountEntity thirdPartyAccountEntity) {
        return thirdPartyAccountDao.delete(thirdPartyAccountEntity.getId());
    }
    /**
     * 修改第三方账号绑定信息
     * @param thirdPartyAccountEntity
     */
    public int update(ThirdPartyAccountEntity thirdPartyAccountEntity) {
        thirdPartyAccountEntity.setUpdateTime(new java.util.Date());
        return thirdPartyAccountDao.update(thirdPartyAccountEntity);
    }

    /**
     * 通过unionID查询第三方账号绑定信息
     * @param unionID
     */
    public ThirdPartyAccountEntity selectByUnionID(String unionID){
        return  thirdPartyAccountDao.selectByUnionID(unionID);
    }

    @Override
    public boolean checkIsBindingUser(String unionID) {
        ThirdPartyAccountEntity thirdPartyAccountEntity = thirdPartyAccountDao.selectThirdPartyInfoByUnionID(unionID);
        if(null!=thirdPartyAccountEntity){
            return  true;
        }
        return false;
    }

    /**
     * 绑定用户
     * 分为两步，1.绑定微信用户到跟上用户
     * 2.检查微信用户下是否有团信息和行程信息若存在 将微信名下的团信息绑定到跟上用户名下
     * @param unionID 微信唯一标识
     * @param phoneNumber 手机号
     * @param teamID 团信息
     * @return
     */
    @Override
    public boolean bindingUser(String unionID,String phoneNumber,long teamID,String txGroupid) {
        logger.info("进入bindingUser 方法！");
        //根据手机号获取用户信息
        UserEntity userEntity = userDao.selectByphone(phoneNumber);
        boolean flag = true;
        try {
            logger.info("开始绑定微信用户和跟上用户！");
            /**
             * step 1 Binding 微信用户到跟上用户
             */
            this.bindingWxUserToOurUser(unionID,userEntity);
            logger.info("绑定微信用户和跟上用户完成！");

            logger.info("开始绑定团行程信息！");
            /**
             * step 2 Binding 团信息和行程信息
             */
            this.bindingTeamInfo(userEntity,unionID,teamID,txGroupid);
            logger.info("团行程信息绑定完成！");
        }catch(Exception e) {
            throw e;
        }

        logger.info("执行bindingUser 方法完成！");
        return flag;
    }


    /**
     * 绑定微信用户到跟上用户（单独的用户绑定不含团绑定）
     * @param unionID 微信的唯一标识
     * @param userEntity 用户实体信息
     */
    public void bindingWxUserToOurUser(String unionID , UserEntity userEntity) {
        //根据unionid  查询数据是否存在，若不存在则插入
        logger.info("开始查询账号是否绑定！");
        ThirdPartyAccountEntity thirdParty = thirdPartyAccountDao.selectThirdPartyAccountByUnionid(unionID);
        logger.info("查询绑定完成！"+" thirdParty = "+thirdParty);
        if(null==thirdParty){
            logger.info("向第三方账号表插入数据开始！");
            ThirdPartyAccountEntity thirdPartyAccountEntity = new ThirdPartyAccountEntity();
            thirdPartyAccountEntity.setUnionID(unionID);
            //账号类型 0 微信 1 微博
            thirdPartyAccountEntity.setAccountType(0);
            thirdPartyAccountEntity.setCreateTime(new Date());
            thirdPartyAccountEntity.setUpdateTime(new Date());
            thirdPartyAccountEntity.setUserId(userEntity.getId());
            thirdPartyAccountDao.insert(thirdPartyAccountEntity);
            logger.info("第三方账号表插入数据完成！");
        }
        // 修改微信信息表中 是否绑定字段的状态为 0
        wxInfoDao.updateBindingStatus(unionID);
    }

    /**
     * Binding 团信息和行程信息(当前设计为只绑定出行中和即将出行的团)
     * @param userEntity 用户信息实体
     * @param unionID 微信的唯一标识
     * @param  txGroupid 腾讯组ID
     * @param teamID 团ID
     */
    public void bindingTeamInfo(UserEntity userEntity, String unionID,long teamID,String txGroupid) {

        //检查当前微信账户下是否有团信息
        List<WxTeamEntity> weixinTeamEntityList = wxTeamDao.selectWxTeamInfoListByUnionid(unionID);

        if(!weixinTeamEntityList.isEmpty()){
            //获取微信名下未出行或出行中的团ID
            List<Long> teamIdList = new ArrayList<Long>();
            List<Long> tids = new ArrayList<>();
            for (WxTeamEntity weixinTeamEntity:weixinTeamEntityList) {
                tids.add(weixinTeamEntity.getTeamId());
                //出行状态,1：出行中；2：即将出行；3：已出行"
                TeamEntity teamEntity = teamDao.selectById(weixinTeamEntity.getTeamId());
                if(teamEntity.getStatus()!=3){
                    teamIdList.add(weixinTeamEntity.getTeamId());
                }
            }
            if(teamID<0){
                //团队成员表中将这些团id下插入用户信息
                for (long teamid:teamIdList) {
                    this.binDingToTeamMeber(teamid,userEntity,unionID,txGroupid);
                }
            }else{
                //team 信息表插入新的团信息数据
                if(!tids.contains(teamID)){
                    WxTeamEntity wxTeamEntity = new WxTeamEntity();
                    wxTeamEntity.setTeamId(teamID);
                    wxTeamEntity.setUnionid(unionID);
                    wxTeamDao.insert(wxTeamEntity);
                }
                this.binDingToTeamMeber(teamID,userEntity,unionID,txGroupid);

            }
        }else{
            if(teamID>0){
                this.binDingToTeamMeber(teamID,userEntity,unionID,txGroupid);
            }
        }
    }

    /**
     * 绑定用户到团
     * @param teamID
     * @param userEntity
     */
    private void binDingToTeamMeber(long teamID, UserEntity userEntity,String unionID,String txGroupid) {
        logger.info("开始向t_member表插入或更新数据！");
        //根据用户ID 和团ID 查找当前用户是否在团
        MemberEntity member = memberService.findMemberByNameSexAndTeamID(userEntity.getRealName(),userEntity.getSex(),teamID);
        //根据姓名，性别，和团ID查找是否有匹配上的，若有匹配上的
        //修改joinstatus ,否则作为新加如团员
        if(null!=member){
            member.setGroupId(member.getGroupId());
            member.setIsNewJoin(0);
            member.setJoinStatus(1);
            member.setUserId(userEntity.getId());
            member.settId(teamID);
            member.setPhone(userEntity.getPhone());
            member.setUpdateTime(new Date());
            member.setPhotoUrl(userEntity.getPhotoUrl());
            member.setTeamId(txGroupid);
            member.setRole(3);
            member.setType(userEntity.getType());
            member.setIsAdmin(member.getIsAdmin());
            member.setIsLeader(member.getIsLeader());
            member.setUnionid(unionID);
            memberDao.update(member);
            logger.info("t_member更新数据完成！");
            //加入腾迅云群组，并加好友
            scheduleService.addTXGroup(member, userEntity, txGroupid);
            //发送欢迎加入信息
            userService.sendInvitationMessage(member,txGroupid);
        }else{
            MemberEntity meberEntity = new MemberEntity();
            meberEntity.setGroupId(-1L);
            meberEntity.setIsNewJoin(1);
            meberEntity.setJoinStatus(1);
            meberEntity.setUserId(userEntity.getId());
            meberEntity.settId(teamID);
            meberEntity.setPhone(userEntity.getPhone());
            meberEntity.setCreateTime(new Date());
            meberEntity.setSex(userEntity.getSex());
            meberEntity.setPhotoUrl(userEntity.getPhotoUrl());
            meberEntity.setVersion(0L);
            meberEntity.setTeamId(txGroupid);
            meberEntity.setRealName(userEntity.getRealName());
            meberEntity.setRole(3);
            meberEntity.setType(userEntity.getType());
            meberEntity.setIsAdmin(0);
            meberEntity.setIsLeader(0);
            meberEntity.setUnionid(unionID);
            memberDao.insertMember(meberEntity);
            logger.info("t_member插入数据完成！");
            //加入腾迅云群组，并加好友
            scheduleService.addTXGroup(meberEntity, userEntity, txGroupid);
            //发送欢迎加入信息
            userService.sendInvitationMessage(meberEntity,txGroupid);
        }
    }


    @Override
    public ThirdPartyAccountEntity selectThirdPartyAccountByUnionid(String unionID) {
        ThirdPartyAccountEntity thirdPartyAccountEntity = thirdPartyAccountDao.selectThirdPartyAccountByUnionid(unionID);
        if(null != thirdPartyAccountEntity){
            return thirdPartyAccountEntity;
        }
        return null;
    }

    /**
     * 根据openID获取第三方账号信息
     * @param openId
     * @return
     */
    @Override
    public ThirdPartyAccountEntity findUserInfoByOpenID(String openId) {
        return thirdPartyAccountDao.selectUserInfoByOpenID(openId);
    }

}
