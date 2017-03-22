package com.umessage.letsgo.domain.vo.message.request;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
/**
 * Created by gaofei on 2017/1/13.
 */
public class QMessageRequest extends CommonRequest {

    /**
     * 腾迅groupid
     */
    @ApiModelProperty(value = "腾迅groupid", required = true)
    private String tGroupId;

    @ApiModelProperty(value = "问题反馈信息", required = true)
    private String message;

    @ApiModelProperty(value = "照片列表", required = false)
    private List<String> photoUrlList;
   /* @ApiModelProperty(value = "照片1", required = false)
    private String photoUrl1;

    @ApiModelProperty(value = "照片2", required = false)
    private String photoUrl2;

    @ApiModelProperty(value = "照片3", required = false)
    private String photoUrl3;

    @ApiModelProperty(value = "照片4", required = false)
    private String photoUrl4;

    @ApiModelProperty(value = "照片5", required = false)
    private String photoUrl5;

    @ApiModelProperty(value = "照片6", required = false)
    private String photoUrl6;

    @ApiModelProperty(value = "照片7", required = false)
    private String photoUrl7;

    @ApiModelProperty(value = "照片8", required = false)
    private String photoUrl8;

    @ApiModelProperty(value = "照片9", required = false)
    private String photoUrl9;*/

    public String gettGroupId() {
        return tGroupId;
    }

    public void settGroupId(String tGroupId) {
        this.tGroupId = tGroupId;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getPhotoUrlList() {
        return photoUrlList;
    }

    public void setPhotoUrlList(List<String> photoUrlList) {
        this.photoUrlList = photoUrlList;
    }
}
