package com.umessage.letsgo.service.api.security;

import com.umessage.letsgo.domain.po.security.WxTemplateMessageEntity;

import java.util.List;

public interface IWxTemplateMessageService {
    /**
     * 删除
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 插入
     * @param wxTemplateMessageEntity
     * @return
     */
    int insert(WxTemplateMessageEntity wxTemplateMessageEntity);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    WxTemplateMessageEntity select(Long id);

    /**
     * 查询全部
     * @return
     */
    List<WxTemplateMessageEntity> selectAll();

    /**
     * 更新
     * @param wxTemplateMessageEntity
     * @return
     */
    int update(WxTemplateMessageEntity wxTemplateMessageEntity);
}
