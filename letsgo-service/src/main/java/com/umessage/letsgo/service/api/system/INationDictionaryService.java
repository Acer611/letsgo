package com.umessage.letsgo.service.api.system;

import com.umessage.letsgo.domain.po.system.NationDictionaryEntity;
import com.umessage.letsgo.domain.vo.system.respone.NationDictionaryResponse;

import java.util.List;

/**
 * Created by wendy on 2016/6/6.
 */
public interface INationDictionaryService {
    List<NationDictionaryEntity> getAllNationDictionaryEntity();

    List<NationDictionaryEntity> getHotNationDictionaryEntity();

    NationDictionaryEntity getNationDictionaryEntity(String code);

    NationDictionaryResponse getNationDictionaryList();
}
