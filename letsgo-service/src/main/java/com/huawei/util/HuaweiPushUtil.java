package com.huawei.util;

import com.huawei.constant.HuaweiPushConstant;
import com.huawei.vo.HuaweiPushRequest;
import com.huawei.vo.PushRet;
import nsp.NSPClient;
import nsp.OAuth2Client;
import nsp.support.common.AccessToken;
import nsp.support.common.NSPException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by pw on 2016/11/1.
 */
public class HuaweiPushUtil {
    public static final String TIMESTAMP_NORMAL = "yyyy-MM-dd HH:mm:ss";

    /**
     * 华为推送
     * 单发透传消息
     * @param client
     * @throws NSPException
     */
    public static PushRet single_send(NSPClient client ,HuaweiPushRequest request)
            throws NSPException
    {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat dataFormat = new SimpleDateFormat(TIMESTAMP_NORMAL);
        //目标用户，必选。
        //由客户端获取， 32 字节长度。手机上安装了push应用后，会到push服务器申请token，申请到的token会上报给应用服务器
        String token = "0861022008078125300000040700CN01";
        if(!StringUtils.isEmpty(request.getToken())){
            token=request.getToken();
        }
        //发送到设备上的消息，必选
        //最长为4096 字节（开发者自定义，自解析）
        String message = "透传测试信息";
        if(!StringUtils.isEmpty(request.getPayload())){
            message=request.getPayload();
        }
        //必选
        //0：高优先级
        //1：普通优先级
        //缺省值为1
        int priority = 0;
        //消息是否需要缓存，必选
        //0：不缓存
        //1：缓存
        //  缺省值为0
        int cacheMode = 1;
        //标识消息类型（缓存机制），必选
        //由调用端赋值，取值范围（1~100）。当TMID+msgType的值一样时，仅缓存最新的一条消息
        int msgType = 1;
        //可选
        //如果请求消息中，没有带，则MC根据ProviderID+timestamp生成，各个字段之间用下划线连接
        String requestID = "";
        //unix时间戳，可选
        //格式：2013-08-29 19:55
        // 消息过期删除时间
        //如果不填写，默认超时时间为当前时间后48小时
        String expire_time = dataFormat.format(currentTime + 3 * 60 * 60 * 1000);
        //构造请求
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("deviceToken", token);
        hashMap.put("message", message);
        hashMap.put("priority", priority);
        hashMap.put("cacheMode", cacheMode);
        hashMap.put("msgType", msgType);
        hashMap.put("requestID", "");
        hashMap.put("expire_time", expire_time);
        //设置http超时时间
        client.setTimeout(10000, 15000);
        //接口调用
        PushRet resp = client.call("openpush.message.single_send", hashMap, PushRet.class);
        //打印响应
        System.err.println("单发接口消息响应:" + resp.getResultcode() + ",message:" + resp.getMessage());
        return resp;
    }

    /**
     * 通知栏消息接口
     * @param client
     * @throws NSPException
     */
    public static String notification_send(NSPClient client,HuaweiPushRequest request)
            throws NSPException
    {
        long currentTime = System.currentTimeMillis();
        //推送范围，必选
        //1：指定用户，必须指定tokens字段
        //2：所有人，无需指定tokens，tags，exclude_tags
        //3：一群人，必须指定tags或者exclude_tags字段
        Integer push_type = 1;
        if(request.getPushType()!=null){
            push_type=request.getPushType();
        }
        //目标用户，可选
        //当push_type=1时，该字段生效
        String tokens = "0862911034416433300000040700CN01";
        if(!StringUtils.isEmpty(request.getToken())){
            tokens=request.getToken();
        }
        //标签，可选
        //当push_type的取值为2时，该字段生效
        String tags = "{\"tags\":[{\"location\":[\"ShangHai\",\"GuangZhou\"]},}\"age\":[\"20\",\"30\"]}]}";
        if(!StringUtils.isEmpty(request.getTags())){
            tags=request.getTags();
        }
        //排除的标签，可选
        //当push_type的取值为2时，该字段生效
        String exclude_tags = "{\"exclude_tags\":[{\"music\":[\"blue\"]},{\"fruit\":[\"apple\"]}]}";
        if(!StringUtils.isEmpty(request.getExcludeTags())){
            exclude_tags=request.getExcludeTags();
        }
        //消息内容，必选
        //该样例是点击通知消息打开url连接。更多的android样例请参考http://developer.huawei.com/ -> 资料中心 -> Push服务 -> API文档 -> 4.2.1 android结构体
        String android =
                "{\"notification_title\":\"the good 1news!\",\"notification_content\":\"Price reduction!\",\"doings\":3,\"url\":\"vmall.com\"}";
        if(!StringUtils.isEmpty(request.getPayload())){
            android=request.getPayload();
        }
        //消息发送时间，可选
        //如果不携带该字段，则表示消息实时生效。实际使用时，该字段精确到分
        //消息发送时间戳，timestamp格式ISO 8601：2013-06-03T17:30:08+08:00
        //String send_time = "2013-09-03T17:30:08+08:00";
        String pattern = "yyyy-MM-dd'T'HH:mm:ssZZ";
        String send_time = DateFormatUtils.format(currentTime,pattern);
        //消息过期时间，可选
        //timestamp格式ISO 8601：2013-06-03T17:30:08+08:00
        //String expire_time = "2013-09-05T17:30:08+08:00";
        String expire_time = DateFormatUtils.format(currentTime,pattern);
        //构造请求
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("push_type", push_type);
        hashMap.put("tokens", tokens);
        hashMap.put("tags", "");
        hashMap.put("exclude_tags", "");
        hashMap.put("android", android);
        hashMap.put("send_time", "");
        hashMap.put("expire_time", "");

        //设置http超时时间
        client.setTimeout(10000, 15000);
        //接口调用
        String rsp = client.call("openpush.openapi.notification_send", hashMap, String.class);

        //打印响应
        //响应样例：{"result_code":0,"request_id":"1380075138"}
        System.err.println("通知栏消息接口响应：" + rsp);
        return rsp;
    }

    /**
     * 群发消息
     * @param client
     * @throws NSPException
     */
    public static void batch_send(NSPClient client)
            throws NSPException
    {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat dataFormat = new SimpleDateFormat(TIMESTAMP_NORMAL);
        //目标用户列表，必选
        //最多填1000个，每个目标用户为32字节长度，由系统分配的合法TMID。手机上安装了push应用后，会到push服务器申请token，申请到的token会上报给应用服务器
        String[] deviceTokenList = {"0861022008078125300000040700CN01"};

        //发送到设备上的消息，必选
        //最长为4096 字节（开发者自定义，自解析）
        String message = "hello~~ you got a push message";

        //消息是否需要缓存，必选
        //0：不缓存
        //1：缓存
        // 缺省值为0
        Integer cacheMode = 1;

        //标识消息类型（缓存机制），必选
        //由调用端赋值，取值范围（1~100）。当TMID+msgType的值一样时，仅缓存最新的一条消息
        Integer msgType = 1;

        //unix时间戳，可选
        //格式：2013-08-29 19:55
        // 消息过期删除时间
        //如果不填写，默认超时时间为当前时间后48小时
        String expire_time =dataFormat.format(currentTime + 3 * 60 * 60 * 1000);

        //构造请求
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("deviceTokenList", deviceTokenList);
        hashMap.put("message", message);
        hashMap.put("cacheMode", cacheMode);
        hashMap.put("msgType", msgType);
        hashMap.put("expire_time", expire_time);

        //设置http超时时间
        client.setTimeout(10000, 15000);
        //接口调用
        PushRet resp = client.call("openpush.message.batch_send", hashMap, PushRet.class);

        //打印响应
        System.err.println("群发接口消息响应: resultcode：" + resp.getResultcode() + ",message:" + resp.getMessage());
    }

    /*
     * 获取token的方法 appId为开发者联盟上面创建应用的APP ID appKey为开发者联盟上面创建应用的 APP SECRET
     * APP ID：appid100       应用包名：com.umessage.genshang   |   APP SECRET：xxxxdtsb4abxxxlz2uyztxxxfaxxxxxx
     */
    public static NSPClient getClient( String appSecret,String appId)throws Exception{
        OAuth2Client oauth2Client = new OAuth2Client();
        oauth2Client.initKeyStoreStream(HuaweiPushUtil.class.getResource("/mykeystorebj.jks").openStream(), "123456");
        AccessToken access_token = oauth2Client.getAccessToken("client_credentials", appId, appSecret);
        System.err.println("access token :" + access_token.getAccess_token() + ",expires time[access token 过期时间]:"
                + access_token.getExpires_in());
        NSPClient client = new NSPClient(access_token.getAccess_token());
        client.initHttpConnections(30, 50);//设置每个路由的连接数和最大连接数
        client.initKeyStoreStream(HuaweiPushUtil.class.getResource("/mykeystorebj.jks").openStream(), "123456");//如果访问https必须导入证书流和密码
       return client;
    }

    /**
     * 方法表述
     *
     * @param args
     *            void
     * @throws NSPException
     */
    public static void main(String[] args)
            throws NSPException
    {
        try
        {
            /*
             * 获取token的方法 appId为开发者联盟上面创建应用的APP ID appKey为开发者联盟上面创建应用的 APP SECRET
             * APP ID：appid100       应用包名：com.umessage.genshang   |   APP SECRET：xxxxdtsb4abxxxlz2uyztxxxfaxxxxxx
             */
            OAuth2Client oauth2Client = new OAuth2Client();
            oauth2Client.initKeyStoreStream(HuaweiPushUtil.class.getResource("/mykeystorebj.jks").openStream(), "123456");
            String appId =  "10596065";
            String appKey = "510391610174c1e82e7f48f8ddcdc393";
            AccessToken access_token = oauth2Client.getAccessToken("client_credentials", appId, appKey);
            System.err.println("access token :" + access_token.getAccess_token() + ",expires time[access token 过期时间]:"
                    + access_token.getExpires_in());
            NSPClient client = new NSPClient(access_token.getAccess_token());
            client.initHttpConnections(30, 50);//设置每个路由的连接数和最大连接数
            client.initKeyStoreStream(HuaweiPushUtil.class.getResource("/mykeystorebj.jks").openStream(), "123456");//如果访问https必须导入证书流和密码
            //调用push单发透传接口
            //single_send(client);
            //调用群发push消息接口
            //batch_send(client);

            //调用通知栏消息接口

            //内容
            JSONObject payload1=new JSONObject();
            payload1.put("notification_title", "华为推送测试");
            payload1.put("notification_content", "华为测试推送内容");
            payload1.put("doings", "1");
            payload1.put("url", "http://h5.igenshang.com/");

            HuaweiPushRequest request = new HuaweiPushRequest();
            request.setPayload(payload1.toString());
            request.setPassThrough(0);
            //包名
            request.setPackageName(HuaweiPushConstant.HW_ANDROID_PACKAGE_NAME);
            //用户token
            //request.setToken("0862455031006177300000040700CN01");
            request.setToken("0862911034416433300000040700CN01");
            // 华为appId
            request.setAppId(HuaweiPushConstant.HW_SDK_APP_ID);
            // 华为appSecret
            request.setAppSecret(HuaweiPushConstant.HW_ANDROID_APP_SECRET);
            //推送范围，必选 1：指定用户，必须指定tokens字段//2：所有人，无需指定tokens，tags，exclude_tags//3：一群人，必须指定tags或者exclude_tags字段
            request.setPushType(1);
            notification_send(client, request);
         }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
