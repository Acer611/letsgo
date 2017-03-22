package com.umessage.letsgo.domain.vo.system.request;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by wendy on 2016/7/1.
 */
public class UnreadAndUnconfirmedRequest extends CommonRequest {
    @ApiModelProperty(value = "需要获取未读/未确认数量的分类【1：集合；2：通知：3：公告】", required = true)
    private List<Integer> types;
    @ApiModelProperty(value = "团队ID", required = false)
    private String teamId;

    public List<Integer> getTypes() {
        return types;
    }

    public void setTypes(List<Integer> types) {
        this.types = types;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
