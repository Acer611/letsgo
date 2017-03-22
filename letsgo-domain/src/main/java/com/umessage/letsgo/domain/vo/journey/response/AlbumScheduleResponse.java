package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.AlbumScheduleEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/29.
 */
public class AlbumScheduleResponse extends CommonResponse{
    private List<AlbumScheduleEntity> albumScheduleEntityList;


    public static AlbumScheduleResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends AlbumScheduleResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        AlbumScheduleResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public static AlbumScheduleResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends AlbumScheduleResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.USER_NOT_LOGIN;
            }

            @Override
            public String getRetMsg() {
                return "用户未登录或登录信息过期";
            }
        }

        return new UserNotLoginResponse();
    }

    public static AlbumScheduleResponse createEmptyParameterResponse(String retMsg){
        class EmptyParameterResponse extends AlbumScheduleResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }
        }

        AlbumScheduleResponse response = new EmptyParameterResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public List<AlbumScheduleEntity> getAlbumScheduleEntityList() {
        return albumScheduleEntityList;
    }

    public void setAlbumScheduleEntityList(List<AlbumScheduleEntity> albumScheduleEntityList) {
        this.albumScheduleEntityList = albumScheduleEntityList;
    }
}
