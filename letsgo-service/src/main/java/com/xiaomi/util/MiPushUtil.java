package com.xiaomi.util;

import com.xiaomi.constant.MiPushConstant;
import com.xiaomi.vo.MiPushRequest;
import com.xiaomi.xmpush.server.*;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/3.
 */
public class MiPushUtil {

    /**
     * 构建android推送信息
     *
     * @param request
     * @return
     */
    public static Message buildMessage2Android(MiPushRequest request) throws Exception {
        Message.Builder builder = new Message.Builder()
                .title(request.getTitle())
                .description(request.getContent())
                .payload(request.getPayload())
                .restrictedPackageName(request.getPackageName())    // 设置包名
                .passThrough(request.getPassThrough())              // 消息方式
                .notifyType(request.getType())                      // 使用默认提示音提示
                .enableFlowControl(true);                           // 控制消息是否需要进行平缓发送

        // 添加扩展消息字段
        if (request.getExtra() != null && !request.getExtra().isEmpty()) {
            for (Map.Entry<String, String> entry : request.getExtra().entrySet()) {
                builder.extra(entry.getKey(), entry.getValue());
            }
        }

        Message message = builder.build();
        return message;
    }

    /**
     * 构建ios推送信息
     *
     * @param request
     * @return
     */
    public static Message buildMessage2IOS(MiPushRequest request) throws Exception {
        Message.IOSBuilder builder = new Message.IOSBuilder()
                .description(request.getContent())
                .soundURL(request.getUrl());

        // 添加扩展消息字段
        if (request.getExtra() != null && !request.getExtra().isEmpty()) {
            for (Map.Entry<String, String> entry : request.getExtra().entrySet()) {
                builder.extra(entry.getKey(), entry.getValue());
            }
        }

        Message message = builder.build();
        return message;
    }

    /**
     * 根据regId，给安卓用户发送一条消息
     * @param request
     * @param regIds
     * @return
     * @throws Exception
     */
    public static Result sendAndroidMessageToRegIds(MiPushRequest request, String regIds, String appSecret) throws Exception {
        // 正式环境
        Constants.useOfficial();
        Sender sender = new Sender(appSecret);
        Message message = buildMessage2Android(request);

        Result result = sender.send(message, regIds, 0);
        return result;
    }

    /**
     * 根据alias，给安卓用户发送一条短信
     * @param request
     * @param aliases
     * @return
     * @throws Exception
     */
    public static Result sendAndroidMessageToAliases(MiPushRequest request, String aliases, String appSecret) throws Exception {
        // 正式环境
        Constants.useOfficial();

        Sender sender = new Sender(appSecret);
        Message message = buildMessage2Android(request);


        // 根据aliasList，发送消息到指定设备上，不重试。
        Result result = sender.sendToAlias(message, aliases, 0);
        return result;
    }

    /**
     * 根据regId，给IOS用户发送一条短信
     * @param request
     * @param regIds
     * @return
     * @throws Exception
     */
    public static Result sendIOSMessageToRegIds(MiPushRequest request, String regIds, String appSecret) throws Exception {
        int sandbox = MiPushConstant.SANDBOX;
        if (sandbox == 0) {
            // 测试环境
            // 测试环境只提供对IOS支持，不支持Android
            Constants.useSandbox();
        } else {
            // 正式环境
            Constants.useOfficial();
        }
        Sender sender = new Sender(appSecret);
        Message message = buildMessage2IOS(request);

        Result result = sender.send(message, regIds, 0);
        return result;
    }

    /**
     * 根据alias，给IOS用户发送一条短信
     * @param request
     * @param aliases
     * @return
     * @throws Exception
     */
    public static Result sendIOSMessageToAliases(MiPushRequest request, String aliases, String appSecret) throws Exception {
        int sandbox = MiPushConstant.SANDBOX;
        if (sandbox == 0) {
            // 测试环境
            // 测试环境只提供对IOS支持，不支持Android
            Constants.useSandbox();
        } else {
            // 正式环境
            Constants.useOfficial();
        }

        Sender sender = new Sender(appSecret);
        Message message = buildMessage2IOS(request);

        // 根据aliasList，发送消息到指定设备上，不重试。
        Result result = sender.sendToAlias(message, aliases, 0);
        return result;
    }

    /**
     * 追踪消息的推送状态
     * @param appSecretKey
     * @param msgId
     * @return
     * @throws IOException
     */
    public static String getPushMsgLog(String appSecretKey, String msgId) throws IOException {
        Tracer tracer = new Tracer(appSecretKey);

        // 获取单条消息的送达状态
        String messageStatus = tracer.getMessageStatus(msgId, 0);

        return messageStatus;
    }

}
