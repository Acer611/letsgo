package com.umessage.letsgo.openapi.team;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.umessage.letsgo.core.utils.IDUtil;
import com.umessage.letsgo.domain.po.team.TeamAblumEntity;
import com.umessage.letsgo.service.api.team.ITeamAblumService;
import com.umessage.letsgo.service.common.helper.QCloudHelper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.umessage.letsgo.domain.vo.team.respone.qq.QQTeamAlbumCallBackResponse;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by zengguoqing on 2016/8/23.
 */
@Controller
@RequestMapping(value = "/callback")
public class CallBackController {
    Logger logger = Logger.getLogger(CallBackController.class);

    @Resource
    private ITeamAblumService teamAblumService;

    @RequestMapping(value = "/uploadTeamAlbum", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public QQTeamAlbumCallBackResponse uploadTeamAlbum(@RequestParam String SdkAppid, @RequestParam String CallbackCommand,
                                                       @RequestParam String ClientIP, @RequestParam String OptPlatform, @RequestBody JSONObject jsonObject) throws Exception {
        QQTeamAlbumCallBackResponse teamAlbumResponse = new QQTeamAlbumCallBackResponse();
        teamAlbumResponse.setActionStatus("OK");
        teamAlbumResponse.setErrorCode(0);
        teamAlbumResponse.setErrorInfo("");

        if (jsonObject == null) {
            return teamAlbumResponse;
        }

        String teamID = jsonObject.getString("GroupId");

        JSONObject msgBody = (jsonObject.getJSONArray("MsgBody")).getJSONObject(0);
        if (msgBody == null || !"TIMImageElem".equals(msgBody.getString("MsgType"))) {
            return teamAlbumResponse;
        }

        JSONObject msgContent = msgBody.getJSONObject("MsgContent");
        JSONArray imageInfoArray = msgContent.getJSONArray("ImageInfoArray");

        String imPhotoUrl = "";
        String imLargeUrl = "";
        String imThumbnailUrl = "";
        int imPhotoWidth = 0;
        int imPhotoHeight = 0;
        for (int i = 0; i < imageInfoArray.size(); i++) {
            JSONObject imgJsonObject = imageInfoArray.getJSONObject(i);
            Integer imgType = imgJsonObject.getInteger("Type");
            String imgUrl = imgJsonObject.getString("URL");

            switch (imgType){
                case 1 :
                    imPhotoUrl = imgUrl;
                    imPhotoWidth = imgJsonObject.getInteger("Width");
                    imPhotoHeight = imgJsonObject.getInteger("Height");
                    break;
                case 2 :
                    imLargeUrl = imgUrl;
                    break;
                case 3 :
                    imThumbnailUrl = imgUrl;
                    break;
                default:
                    break;
            }
        }

        if (imPhotoWidth < 1000 && imPhotoHeight < 1000){
            logger.info("照片宽高都小于1000，不进入团队相册。URL：" + imPhotoUrl);
            return teamAlbumResponse;
        }


        try {
            logger.info("开始保存照片, url：" + imLargeUrl);
            String photoUrl = QCloudHelper.remoteFileUpload("/pic/teamAlbum/" + QCloudHelper.changPath(teamID) + "/" + IDUtil.uuid() + ".jpg", imLargeUrl);
            logger.info("照片保存成功, 原url：" + imLargeUrl + ", 新url：" + photoUrl);
            logger.info("开始保存照片, url：" + imThumbnailUrl);
            String thumbnailUrl = QCloudHelper.remoteFileUpload("/pic/teamAlbum/" + QCloudHelper.changPath(teamID) + "/" + IDUtil.uuid() + ".jpg", imThumbnailUrl);
            logger.info("照片保存成功, 原url：" + imThumbnailUrl+ ", 新url：" + thumbnailUrl);

            TeamAblumEntity teamAblumEntity = new TeamAblumEntity();
            teamAblumEntity.setTeamId(teamID);
            teamAblumEntity.setCreateTime(new Date());
            teamAblumEntity.setPhotographTime(new Date());
            teamAblumEntity.setVersion(0);
            teamAblumEntity.setAuthor("");
            teamAblumEntity.setLat("");
            teamAblumEntity.setLng("");
            teamAblumEntity.setImPhotoUrl(imPhotoUrl);
            teamAblumEntity.setImLargeUrl(imLargeUrl);
            teamAblumEntity.setImThumbnailUrl(imThumbnailUrl);
            teamAblumEntity.setThumbnailUrl(thumbnailUrl);
            teamAblumEntity.setPhotoUrl(photoUrl);
            teamAblumEntity.setType(1); //1 可见 2 隐藏
            teamAblumService.savePhotoUrl(teamAblumEntity);

        } catch (Exception e) {
            logger.error("照片保存失败, request：" + imLargeUrl + "，异常：" + e.toString());
            e.printStackTrace();
        }


        return teamAlbumResponse;
    }
}
