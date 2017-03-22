package com.umessage.letsgo.service.api.team;

import com.umessage.letsgo.domain.po.team.OwnExpenseAgencyEntity;

import java.util.List;


/**
 * Created by zengguoqing on 2016/9/7.
 */
public interface IOwnExpenseAgencyService {

    /**
     * Description:
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description:
     * @param theirOwnExpenseAgencyEntity
     * @return int
     */
    int insert(OwnExpenseAgencyEntity theirOwnExpenseAgencyEntity);

    /**
     * Description:
     * @param id
     * @return com.umessage.letsgo.domain.po.team.OwnExpenseAgencyEntity
     */
    OwnExpenseAgencyEntity select(Long id);

    /**
     * Description:
     * @return java.util.List<com.umessage.letsgo.domain.po.team.OwnExpenseAgencyEntity>
     */
    List<OwnExpenseAgencyEntity> selectAll();

    /**
     * Description:
     * @param theirOwnExpenseAgencyEntity
     * @return int
     */
    int update(OwnExpenseAgencyEntity theirOwnExpenseAgencyEntity);

    /**
     *根据名称联想查询自费项目
     * @param ownExpenseAgencyEntity
     * @return
     */
    List<OwnExpenseAgencyEntity> selectOwnExpenseByName(OwnExpenseAgencyEntity ownExpenseAgencyEntity);

    /**
     * 查询名称相同的自费项目
     * @param ownExpenseAgencyEntity
     * @return
     */
    Boolean selectOwnExpenseBySameName(OwnExpenseAgencyEntity ownExpenseAgencyEntity);

    /**
     * 查询自费项目及相册
     * @param id
     * @return
     */
    OwnExpenseAgencyEntity selectOwnExpenseAndAlbum(Long id);

}
