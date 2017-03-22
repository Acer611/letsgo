package com.umessage.letsgo.service.impl.system;

import com.huawei.constant.HuaweiPushConstant;
import com.huawei.util.HuaweiPushUtil;
import com.huawei.vo.HuaweiPushRequest;
import com.huawei.vo.PushRet;
import com.umessage.letsgo.dao.system.PushDao;
import com.umessage.letsgo.domain.po.security.DeviceEntity;
import com.umessage.letsgo.domain.po.system.PushEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.service.api.security.IDeviceService;
import com.umessage.letsgo.service.api.system.IPushService;
import com.xiaomi.constant.MiPushConstant;
import com.xiaomi.push.sdk.ErrorCode;
import com.xiaomi.util.MiPushUtil;
import com.xiaomi.vo.MiPushRequest;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Result;
import nsp.NSPClient;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2016/5/4.
 */
@Service
public class PushServiceImpl implements IPushService {
    @Resource
    private PushDao pushDao;
    @Resource
    private IDeviceService deviceService;

    Logger logger = Logger.getLogger(PushServiceImpl.class);

    /**
     * 创建推送实体
     * @param pushEntity
     * @return
     */
    @Override
    public int create(PushEntity pushEntity) {
        pushEntity.setCreateTime(new Date());
        pushEntity.setVersion(0l);
        return pushDao.insert(pushEntity);
    }

    public int update(PushEntity pushEntity) {
        return pushDao.update(pushEntity);
    }

    /**
     * 推送消息，使用通知栏方式进行推送
     * @param desc
     * @param param
     * @param memberEntityList
     * @throws Exception
     */
    public void pushMessage(String desc, Map<String, String> param, List<MemberEntity> memberEntityList) throws Exception {
        //将传过来的参数转为Json格式
        JSONObject payload=new JSONObject();
        if(param!=null) {
            String type = param.get("type");
            String message = param.get("message");
            payload.put("type",type);
            payload.put("message",message);
        }
        for (MemberEntity memberEntity : memberEntityList) {
            if (memberEntity.getUserId() == -1) {   // 如果是未加入的成员，则不进行推送
                continue;
            }
            // 1、获取用户设备信息及小米注册ID、小米别名
            DeviceEntity deviceEntity = deviceService.getDeviceByUserId(memberEntity.getUserId());
            if (deviceEntity.getDeviceType() == null) { // 没有设备信息的不推送
                continue;
            }
            //如果是华为手机则用华为推送
            //是否为华为手机：1是；0否
            if(deviceEntity.getIsHuaWei()!=null&&"1".equals(deviceEntity.getIsHuaWei())){
                if(StringUtils.isEmpty(deviceEntity.getRegid())){
                    continue;
                }
                HuaweiPushRequest request = new HuaweiPushRequest();
                //内容
                JSONObject payload1=new JSONObject();
                if(param!=null){
                    String type =param.get("type");
                    String message =param.get("message");
                    payload1.put("notification_title",desc);
                    payload1.put("notification_content",message);
                    //payload1.put("doings",type);
                    //payload1.put("url","http://h5.igenshang.com/");
                    payload1.put("doings","1");
                }
                request.setPayload(payload1.toString());
                request.setPassThrough(MiPushConstant.NOTIFICATION_MESSAGE);
                //包名
                request.setPackageName(HuaweiPushConstant.HW_ANDROID_PACKAGE_NAME);
                //用户token
                request.setToken(deviceEntity.getRegid());
                // 华为appId
                request.setAppId(HuaweiPushConstant.HW_SDK_APP_ID);
                // 华为appSecret
                request.setAppSecret(HuaweiPushConstant.HW_ANDROID_APP_SECRET);
                //推送范围，必选 1：指定用户，必须指定tokens字段//2：所有人，无需指定tokens，tags，exclude_tags//3：一群人，必须指定tags或者exclude_tags字段
                request.setPushType(1);
                String result = this.huaweiPushAndroidMessage(request);

                // 保存推送
                this.saveHuaweiPushEntity(result, request, memberEntity.getUserId(), request.getAppSecret());
                continue;
            }

//            //如果是华为手机则用华为推送
//            //是否为华为手机：1是；0否
//            if(deviceEntity.getIsHuaWei()!=null&&"1".equals(deviceEntity.getIsHuaWei())){
//                if(StringUtils.isEmpty(deviceEntity.getRegid())){
//                    continue;
//                }
//                HuaweiPushRequest request = new HuaweiPushRequest();
//                //内容
//                request.setPayload(payload.toString());
//                //包名
//                request.setPackageName(HuaweiPushConstant.HW_ANDROID_PACKAGE_NAME);
//                //用户token
//                request.setToken(deviceEntity.getRegid());
//                // 华为appId
//                request.setAppId(HuaweiPushConstant.HW_SDK_APP_ID);
//                // 华为appSecret
//                request.setAppSecret(HuaweiPushConstant.HW_ANDROID_APP_SECRET);
//                PushRet result = this.huaweiSingleSendPushAndroidMessage(request);
//                // 保存推送
//                this.saveHuaweiPushEntity2(result, request, deviceEntity.getUserId(), request.getAppSecret());
//                continue;
//            }


            // 2、调用小米推送，更新推送实体的状态
            Result result = null;
            MiPushRequest request = new MiPushRequest();
            String androidAppSecret = "";
            String packaageName = "";
            String IOSAppSecret = "";
            String title = "";
            String appSecret = "";

//            if (memberEntity.getRole() == 3) {
//                packaageName = MiPushConstant.T_ANDROID_PACKAGE_NAME;
//                androidAppSecret = MiPushConstant.T_ANDROID_APP_SECRET;
//                IOSAppSecret = MiPushConstant.T_IOS_APP_SECRET;
//                title = "跟上-游客";
//            } else {
            //将用户的推送都发到领队端
                packaageName = MiPushConstant.ANDROID_PACKAGE_NAME;
                androidAppSecret = MiPushConstant.ANDROID_APP_SECRET;
                IOSAppSecret = MiPushConstant.IOS_APP_SECRET;
                title = "跟上-领队";
            //}

            if ("Android".equals(deviceEntity.getDeviceType())) {   // 向安卓设备的用户推送
                request.setTitle(title);
                request.setContent(desc);
                request.setPayload(payload.toString());
                request.setPassThrough(MiPushConstant.NOTIFICATION_MESSAGE);
                request.setPackageName(packaageName);
                request.setType(MiPushConstant.DEFAULT_SOUND);

                result = this.pushAndroidMessage(request, deviceEntity, androidAppSecret);

                appSecret = androidAppSecret;
            } else {    // 向IOS设备的用户推送
                request.setContent(desc);
                request.setExtra(param);
                request.setUrl("default");

                result = this.pushIOSMessage(request, deviceEntity, IOSAppSecret);

                appSecret = IOSAppSecret;
            }

            // 保存推送
            this.savePushEntity(result, request, memberEntity.getUserId(), appSecret);
        }
    }

    /**
     * 推送自定义铃声消息，使用通知栏方式进行推送
     * @param desc
     * @param param
     * @param memberEntityList
     * @throws Exception
     */
    public void pushMessageByMyRingtone(String desc, Map<String, String> param, List<MemberEntity> memberEntityList) throws Exception {
        //将传过来的参数转为Json格式
        JSONObject payload=new JSONObject();
        if(param!=null) {
            String type = param.get("type");
            String message = param.get("message");
            payload.put("type",type);
            payload.put("message",message);
        }
        for (MemberEntity memberEntity : memberEntityList) {
            if (memberEntity.getUserId() == -1) {   // 如果是未加入的成员，则不进行推送
                continue;
            }
            // 1、获取用户设备信息及小米注册ID、小米别名
            DeviceEntity deviceEntity = deviceService.getDeviceByUserId(memberEntity.getUserId());
            if (deviceEntity.getDeviceType() == null) { // 没有设备信息的不推送
                continue;
            }
            //如果是华为手机则用华为推送
            //是否为华为手机：1是；0否
            if(deviceEntity.getIsHuaWei()!=null&&"1".equals(deviceEntity.getIsHuaWei())){
                if(StringUtils.isEmpty(deviceEntity.getRegid())){
                    continue;
                }
                HuaweiPushRequest request = new HuaweiPushRequest();
                //内容
                JSONObject payload1=new JSONObject();
                if(param!=null){
                    String type =param.get("type");
                    String message =param.get("message");
                    payload1.put("notification_title",desc);
                    payload1.put("notification_content",message);
                    //payload1.put("doings",type);
                    //payload1.put("url","http://h5.igenshang.com/");
                    payload1.put("doings", 1);
                }
                request.setPayload(payload1.toString());
                request.setPassThrough(MiPushConstant.NOTIFICATION_MESSAGE);
                //包名
                request.setPackageName(HuaweiPushConstant.HW_ANDROID_PACKAGE_NAME);
                //用户token
                request.setToken(deviceEntity.getRegid());
                // 华为appId
                request.setAppId(HuaweiPushConstant.HW_SDK_APP_ID);
                // 华为appSecret
                request.setAppSecret(HuaweiPushConstant.HW_ANDROID_APP_SECRET);
                //推送范围，必选 1：指定用户，必须指定tokens字段//2：所有人，无需指定tokens，tags，exclude_tags//3：一群人，必须指定tags或者exclude_tags字段
                request.setPushType(1);
                String result = this.huaweiPushAndroidMessage(request);

                // 保存推送
                this.saveHuaweiPushEntity(result, request, memberEntity.getUserId(), request.getAppSecret());
                continue;
            }

//            //如果是华为手机则用华为推送
//            //是否为华为手机：1是；0否
//            if(deviceEntity.getIsHuaWei()!=null&&"1".equals(deviceEntity.getIsHuaWei())){
//                if(StringUtils.isEmpty(deviceEntity.getRegid())){
//                    continue;
//                }
//                HuaweiPushRequest request = new HuaweiPushRequest();
//                //内容
//                request.setPayload(payload.toString());
//                //包名
//                request.setPackageName(HuaweiPushConstant.HW_ANDROID_PACKAGE_NAME);
//                //用户token
//                request.setToken(deviceEntity.getRegid());
//                // 华为appId
//                request.setAppId(HuaweiPushConstant.HW_SDK_APP_ID);
//                // 华为appSecret
//                request.setAppSecret(HuaweiPushConstant.HW_ANDROID_APP_SECRET);
//                PushRet result = this.huaweiSingleSendPushAndroidMessage(request);
//                // 保存推送
//                this.saveHuaweiPushEntity2(result, request, deviceEntity.getUserId(), request.getAppSecret());
//                continue;
//            }

            // 2、调用小米推送，更新推送实体的状态
            Result result = null;
            MiPushRequest request = new MiPushRequest();
            String androidAppSecret = "";
            String packaageName = "";
            String IOSAppSecret = "";
            String title = "";
            String appSecret = "";

//            if (memberEntity.getRole() == 3) {
//                packaageName = MiPushConstant.T_ANDROID_PACKAGE_NAME;
//                androidAppSecret = MiPushConstant.T_ANDROID_APP_SECRET;
//                IOSAppSecret = MiPushConstant.T_IOS_APP_SECRET;
//                title = "跟上-游客";
//            } else {
                packaageName = MiPushConstant.ANDROID_PACKAGE_NAME;
                androidAppSecret = MiPushConstant.ANDROID_APP_SECRET;
                IOSAppSecret = MiPushConstant.IOS_APP_SECRET;
                title = "跟上-领队";
//            }

            if ("Android".equals(deviceEntity.getDeviceType())) {   // 向安卓设备的用户推送
                request.setTitle(title);
                request.setContent(desc);
                request.setPayload(payload.toString());
                request.setPassThrough(MiPushConstant.NOTIFICATION_MESSAGE);
                request.setPackageName(packaageName);
                request.setType(MiPushConstant.DEFAULT_SOUND);

                Map<String, String> extra = new HashMap<>();
                extra.put(Constants.EXTRA_PARAM_SOUND_URI, "android.resource://" + packaageName + "/raw/call");
                request.setExtra(extra);

                result = this.pushAndroidMessage(request, deviceEntity, androidAppSecret);

                appSecret = androidAppSecret;

            } else {    // 向IOS设备的用户推送
                request.setContent(desc);
                request.setExtra(param);

                String url = "call.caf";    // 自定义铃声
                request.setUrl(url);

                result = this.pushIOSMessage(request, deviceEntity, IOSAppSecret);

                appSecret = IOSAppSecret;
            }

            // 保存推送
            this.savePushEntity(result, request, memberEntity.getUserId(), appSecret);
        }
    }

    /**
     * 推送消息，使用透传方式进行推送,推送至游客端
     * @param deviceList
     * @param desc
     * @param param
     * @throws Exception
     */
    /*
    @Override
    @Transactional
    public void pushMIMessageToTourist(List<DeviceEntity> deviceList, String desc, Map<String, String> param)throws Exception {
        //将传过来的参数转为Json格式
        JSONObject payload=new JSONObject();
        if(param!=null) {
            String type = param.get("type");
            String message = param.get("message");
            payload.put("type",type);
            payload.put("message",message);
        }
        for (DeviceEntity device : deviceList){
            //如果是华为手机则用华为推送
            //是否为华为手机：1是；0否
            if(device.getIsHuaWei()!=null&&"1".equals(device.getIsHuaWei())){
                if(StringUtils.isEmpty(device.getRegid())){
                    continue;
                }
                HuaweiPushRequest request = new HuaweiPushRequest();
                //内容
                request.setPayload(payload.toString());
                //包名
                request.setPackageName(HuaweiPushConstant.HW_ANDROID_PACKAGE_NAME);
                //用户token
                request.setToken(device.getRegid());
                // 华为appId
                request.setAppId(HuaweiPushConstant.HW_SDK_APP_ID);
                // 华为appSecret
                request.setAppSecret(HuaweiPushConstant.HW_ANDROID_APP_SECRET);
                PushRet result = this.huaweiSingleSendPushAndroidMessage(request);
                // 保存推送
                this.saveHuaweiPushEntity2(result, request, device.getUserId(), request.getAppSecret());
                continue;
            }
            Result result = null;
            String appSecret = "";
            MiPushRequest request = new MiPushRequest();
            if ("Android".equals(device.getDeviceType())) {
                request.setTitle("跟上");
                request.setContent(desc);
                request.setPayload(payload.toString());
                request.setPassThrough(MiPushConstant.PASS_THROUGH_MESSAGE);    // 设置透传方式
                request.setPackageName(MiPushConstant.T_ANDROID_PACKAGE_NAME);
                request.setType(MiPushConstant.DEFAULT_ALL);

                result = this.pushAndroidMessage(request, device, MiPushConstant.T_ANDROID_APP_SECRET);

                appSecret = MiPushConstant.T_ANDROID_APP_SECRET;
            } else {    // 向IOS设备的用户推送
                request.setContent(desc);
                param.put("content-available", "1");    // 设置透传方式
                request.setExtra(param);
                request.setUrl("default");

                result = this.pushIOSMessage(request, device, MiPushConstant.T_IOS_APP_SECRET);

                param.remove("content-available");  // 删除透传方式参数

                appSecret = MiPushConstant.T_IOS_APP_SECRET;
            }

            this.savePushEntity(result, request, device.getUserId(), appSecret);

        }
    }
    */

    /**
     * 推送消息，使用透传方式进行推送,推送至领队端
     * @param deviceList
     * @param desc
     * @param param
     * @throws Exception
     */
    @Override
    @Transactional
    public void pushMIMessageToGuide(List<DeviceEntity> deviceList, String desc, Map<String, String> param)throws Exception {
       //将传过来的参数转为Json格式
        JSONObject payload=new JSONObject();
        if(param!=null) {
            String type = param.get("type");
            String message = param.get("message");
            payload.put("type",type);
            payload.put("message",message);
        }
        for (DeviceEntity device : deviceList){
            //如果是华为手机则用华为推送
            //是否为华为手机：1是；0否
            if(device.getIsHuaWei()!=null&&"1".equals(device.getIsHuaWei())){
                if(StringUtils.isEmpty(device.getRegid())){
                    continue;
                }
                HuaweiPushRequest request = new HuaweiPushRequest();
                //内容
                request.setPayload(payload.toString());
                //包名
                request.setPackageName(HuaweiPushConstant.HW_ANDROID_PACKAGE_NAME);
                //用户token
                request.setToken(device.getRegid());
                // 华为appId
                request.setAppId(HuaweiPushConstant.HW_SDK_APP_ID);
                // 华为appSecret
                request.setAppSecret(HuaweiPushConstant.HW_ANDROID_APP_SECRET);
                PushRet result = this.huaweiSingleSendPushAndroidMessage(request);
                // 保存推送
                this.saveHuaweiPushEntity2(result, request, device.getUserId(), request.getAppSecret());
                continue;
            }
            Result result = null;
            MiPushRequest request = new MiPushRequest();
            String appSecret = "";

            if ("Android".equals(device.getDeviceType())) {
                request.setTitle("跟上");
                request.setContent(desc);
                request.setPayload(payload.toString());
                request.setPassThrough(MiPushConstant.PASS_THROUGH_MESSAGE);    // 设置透传方式
                request.setPackageName(MiPushConstant.ANDROID_PACKAGE_NAME);
                request.setType(MiPushConstant.DEFAULT_ALL);

                result = this.pushAndroidMessage(request, device, MiPushConstant.ANDROID_APP_SECRET);

                appSecret = MiPushConstant.ANDROID_APP_SECRET;
            } else {    // 向IOS设备的用户推送
                request.setContent(desc);
                param.put("content-available", "1");    // 设置透传方式
                request.setExtra(param);
                request.setUrl("default");

                result = this.pushIOSMessage(request, device, MiPushConstant.IOS_APP_SECRET);

                param.remove("content-available");  // 删除透传方式参数

                appSecret = MiPushConstant.IOS_APP_SECRET;
            }

            this.savePushEntity(result, request, device.getUserId(), appSecret);
        }
    }

    @Override
    public List<PushEntity> getPushList() {
        List<PushEntity> pushEntityList = pushDao.selectPushList();
        if (CollectionUtils.isEmpty(pushEntityList)) {
            return new ArrayList<>();
        }
        return pushEntityList;
    }

    @Override
    public boolean updatePushWithTracker(PushEntity pushEntity) {
        try {
            String ret = MiPushUtil.getPushMsgLog(pushEntity.getSecretkey(), pushEntity.getMsgid());

            pushEntity.setMsgresult(ret);
            pushDao.update(pushEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Transactional
    private void savePushEntity(Result result, MiPushRequest request, Long userId, String appSecret) {
        PushEntity pushEntity = new PushEntity();
        if (StringUtils.isEmpty(request.getTitle())) {
            pushEntity.setTitle("跟上");
        } else {
            pushEntity.setTitle(request.getTitle());
        }
        pushEntity.setDescription(request.getContent());
        if (!StringUtils.isEmpty(request.getPayload())) {
            pushEntity.setParam(request.getPayload());
        } else {
            pushEntity.setParam(request.getExtra().toString());
        }
        pushEntity.setUserId(userId);
        pushEntity.setSecretkey(appSecret); //保存密钥
        pushEntity.setPushStatus(0);
        pushEntity.setMsgid(result.getMessageId());
        pushEntity.setErrorCode(result.getErrorCode().getValue());
        pushEntity.setErrorMessage(result.getErrorCode().getDescription());
        pushEntity.setResult(result.toString());

        if (result.getErrorCode() == ErrorCode.Success) {
            pushEntity.setPushStatus(1);
        }

        this.create(pushEntity);
    }

    /**
     * 向IOS设备的用户推送
     * @param request
     * @param device
     * @return
     * @throws Exception
     */
    private Result pushIOSMessage(MiPushRequest request, DeviceEntity device, String appSecret) throws Exception {
        Result result = null;

        logger.info("IOS推送请求参数：" + request.toString());    // 打印日志

        if (!StringUtils.isEmpty(device.getRegid())) {
            // 使用注册ID发送
            logger.info("RegIds：" + device.getRegid());

            result = MiPushUtil.sendIOSMessageToRegIds(request, device.getRegid(), appSecret);
        } else {
            // 使用别名发送
            logger.info("别名：" + device.getAlias());

            result = MiPushUtil.sendIOSMessageToAliases(request, device.getAlias(), appSecret);
        }

        logger.info("IOS推送返回结果：" + result.toString());

        return result;
    }

    /**
     * 向安卓设备的用户推送
     * @param request
     * @param device
     * @return
     * @throws Exception
     */
    private Result pushAndroidMessage(MiPushRequest request, DeviceEntity device, String appSecret) throws Exception {
        Result result = null;

        logger.info("Android推送请求参数：" + request.toString());    // 打印日志

        if (device.getRegid() != null) {
            // 使用注册ID发送
            logger.info("RegIds：" + device.getRegid());

            result = MiPushUtil.sendAndroidMessageToRegIds(request, device.getRegid(), appSecret);
        } else {
            // 使用别名发送
            logger.info("别名：" + device.getAlias());

            result = MiPushUtil.sendAndroidMessageToAliases(request, device.getAlias(), appSecret);
        }

        logger.info("Android推送返回结果：" + result.toString());

        return result;
    }

    private void saveHuaweiPushEntity(String result, HuaweiPushRequest request, Long userId, String appSecret) {
        PushEntity pushEntity = new PushEntity();
        if (StringUtils.isEmpty(request.getTitle())) {
            pushEntity.setTitle("跟上");
        } else {
            pushEntity.setTitle(request.getTitle());
        }
        pushEntity.setDescription(request.getContent());
        if (!StringUtils.isEmpty(request.getPayload())) {
            pushEntity.setParam(request.getPayload());
        } else {
            pushEntity.setParam(request.getExtra().toString());
        }
        pushEntity.setUserId(userId);
        pushEntity.setSecretkey(appSecret); //保存密钥
        pushEntity.setPushStatus(1);
        pushEntity.setResult(result);
        this.create(pushEntity);
    }

    private void saveHuaweiPushEntity2(PushRet result, HuaweiPushRequest request, Long userId, String appSecret) {
        PushEntity pushEntity = new PushEntity();
        if (StringUtils.isEmpty(request.getTitle())) {
            pushEntity.setTitle("跟上");
        } else {
            pushEntity.setTitle(request.getTitle());
        }
        if (!StringUtils.isEmpty(request.getPayload())) {
            pushEntity.setDescription(request.getPayload());
        } else {
            pushEntity.setDescription(request.getExtra().toString());
        }
        pushEntity.setUserId(userId);
        pushEntity.setSecretkey(appSecret); //保存密钥
        if(result.getResultcode()==0){
            pushEntity.setPushStatus(1);
        }else{
            pushEntity.setPushStatus(0);
        }
        pushEntity.setResult(result.getMessage());
        pushEntity.setErrorCode(result.getResultcode());
        this.create(pushEntity);
    }


    /**
     * 使用通知栏方式进行推送
     * 向华为手机设备的用户推送
     * @param request
     * @throws Exception
     */
    private String huaweiPushAndroidMessage(HuaweiPushRequest request) throws Exception {
        String result=null;
        logger.info("Android推送请求参数：" + request.toString());    // 打印日志

        if (request.getToken() != null) {
            NSPClient client = HuaweiPushUtil.getClient(request.getAppSecret(), request.getAppId());
             result = HuaweiPushUtil.notification_send(client, request);
        }
        logger.info("Android推送返回结果：" + result.toString());

        return result;
     }

    /**
     * 使用透传方式进行推送
     * 向华为手机设备的用户推送
     * @param request
     * @throws Exception
     */
    private PushRet huaweiSingleSendPushAndroidMessage(HuaweiPushRequest request) throws Exception {
        PushRet result=null;
        logger.info("Android推送请求参数：" + request.toString());    // 打印日志

        if (request.getToken() != null) {
            NSPClient client = HuaweiPushUtil.getClient(request.getAppSecret(), request.getAppId());
            result = HuaweiPushUtil.single_send(client, request);
        }
        logger.info("Android推送返回结果：" + result.toString());

        return result;
    }


}
