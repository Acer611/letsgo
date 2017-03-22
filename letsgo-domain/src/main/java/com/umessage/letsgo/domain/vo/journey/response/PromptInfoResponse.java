package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.PromptInfoEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by ZhaoYidong on 2016/7/4.
 */
public class PromptInfoResponse extends CommonResponse{

    @ApiModelProperty(value="提示信息实体")
    private PromptInfoEntity promptInfoEntity;

    public PromptInfoResponse() {}
    public PromptInfoResponse(PromptInfoEntity promptInfoEntity) {
        this.promptInfoEntity = promptInfoEntity;
    }

    public PromptInfoEntity getPromptInfoEntity() {
        return promptInfoEntity;
    }

    public void setPromptInfoEntity(PromptInfoEntity promptInfoEntity) {
        this.promptInfoEntity = promptInfoEntity;
    }

    public static PromptInfoResponse createInvalidParameterResponse(String retMsg){
        class EmptyParameterResponse extends PromptInfoResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.INVALID_PARAMETER;
            }
        }

        PromptInfoResponse response = new EmptyParameterResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    @Override
    public String toString() {
        return "PromptInfoResponse{" +
                "promptInfoEntity=" + promptInfoEntity +
                '}';
    }
}
