package com.umessage.letsgo.travel.controller.journey;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.AlbumScheduleEntity;
import com.umessage.letsgo.domain.vo.journey.response.AlbumScheduleResponse;
import com.umessage.letsgo.service.api.journey.IAlbumScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by zengguoqing on 2016/9/1.
 */
@Controller
@RequestMapping(value = "/albumschedule")
public class AlbumScheduleController {
    /**
     * 初始数据获取
     */
    @Resource
    private IAlbumScheduleService albumScheduleService;


    /*
    保存酒店
     */
    @RequestMapping(value = "/deleteAlbumSchedule")
    @ResponseBody
    public AlbumScheduleResponse deleteAlbumSchedule(@RequestBody AlbumScheduleEntity albumScheduleEntity) {
        AlbumScheduleResponse albumScheduleResponse = new AlbumScheduleResponse();
        if (albumScheduleEntity.getScheduleDetailId() == null){
            AlbumScheduleResponse.createEmptyParameterResponse("每日行程id不能为空");
        }
        if (albumScheduleEntity.getObjectId() == null){
            AlbumScheduleResponse.createEmptyParameterResponse("每日行程关联表id不能为空");
        }
        if (albumScheduleEntity.getType() == null){
            AlbumScheduleResponse.createEmptyParameterResponse("状态不能为空");
        }
        try {
            albumScheduleService.deleteByContents(albumScheduleEntity);
        }catch (Exception e){
            albumScheduleResponse.setRetCode(ErrorConstant.INTERNAL_SERVER_ERROR);
            albumScheduleResponse.setRetMsg("删除失败");
        }
        return albumScheduleResponse;
    }

}
