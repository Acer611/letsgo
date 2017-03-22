package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.DataVo;

import java.util.List;

/**
 * Created by wendy on 2016/8/31.
 */
public class TouristResponse extends CommonResponse {
    private List<DataVo> areaDataList;
    private List<DataVo> ageDataList;
    private List<DataVo> sexDataList;
    private int totalCount;

    public List<DataVo> getAreaDataList() {
        return areaDataList;
    }

    public void setAreaDataList(List<DataVo> areaDataList) {
        this.areaDataList = areaDataList;
    }

    public List<DataVo> getAgeDataList() {
        return ageDataList;
    }

    public void setAgeDataList(List<DataVo> ageDataList) {
        this.ageDataList = ageDataList;
    }

    public List<DataVo> getSexDataList() {
        return sexDataList;
    }

    public void setSexDataList(List<DataVo> sexDataList) {
        this.sexDataList = sexDataList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
