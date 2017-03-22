package com.umessage.letsgo.domain.vo.system.respone;

import com.umessage.letsgo.domain.po.system.NationDictionaryEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 2016/6/6.
 */
public class NationDictionaryResponse extends CommonResponse {
    @ApiModelProperty(value = "热门货币列表")
    private List<NationDictionaryEntity> hotNationDictionaryList = new ArrayList<>();

    @ApiModelProperty(value = "货币列表")
    private List<NationDictionaryEntity> nationDictionaryList = new ArrayList<>();

    public List<NationDictionaryEntity> getHotNationDictionaryList() {
        return hotNationDictionaryList;
    }

    public void setHotNationDictionaryList(List<NationDictionaryEntity> hotNationDictionaryList) {
        this.hotNationDictionaryList = hotNationDictionaryList;
    }

    public List<NationDictionaryEntity> getNationDictionaryList() {
        return nationDictionaryList;
    }

    public void setNationDictionaryList(List<NationDictionaryEntity> nationDictionaryList) {
        this.nationDictionaryList = nationDictionaryList;
    }

}
