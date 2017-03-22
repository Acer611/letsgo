package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.po.journey.PromptInfoEntity;

import java.util.List;


/**
 * Created by ZhaoYidong on 2016/6/2.
 */
public interface IPromptInfoService {
    // 新增
    public int addPromptInfo(PromptInfoEntity PromptInfoEntity);
    // 删除
    public int deletePromptInfo(long id);
    // 更新
    public int updatePromptInfo(PromptInfoEntity PromptInfoEntity);
    // 获取列表
    public List<PromptInfoEntity> getPromptInfos();
    // 获取
    public PromptInfoEntity getPromptInfo(long id);
    // 通过行程id获取提示信息
    public PromptInfoEntity getByScheduleId(long scheduleId);

}
