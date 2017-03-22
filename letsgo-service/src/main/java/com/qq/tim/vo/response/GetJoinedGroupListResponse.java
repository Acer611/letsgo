package com.qq.tim.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Administrator on 2016/4/29.
 */
public class GetJoinedGroupListResponse extends CommonResponse {
    @JsonProperty(value = "TotalCount")
    private Integer totalCount;
    @JsonProperty(value = "GroupIdList")
    private List<GroupIdList> groupIdList;

    public GetJoinedGroupListResponse() {
    }

    public GetJoinedGroupListResponse(Integer totalCount, List<GroupIdList> groupIdList) {
        this.totalCount = totalCount;
        this.groupIdList = groupIdList;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<GroupIdList> getGroupIdList() {
        return groupIdList;
    }

    public void setGroupIdList(List<GroupIdList> groupIdList) {
        this.groupIdList = groupIdList;
    }

    @Override
    public String toString() {
        return "{"
                + "                        \"totalCount\":\"" + totalCount + "\""
                + ",                         \"groupIdList\":" + groupIdList
                + "}";
    }
}
