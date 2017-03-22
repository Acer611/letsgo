package com.umessage.letsgo.service.api.security;

import com.umessage.letsgo.domain.po.security.ValidationCodeEntity;

/**
 * Created by Administrator on 2016/5/4.
 */
public interface IValidationCodeService {
    int create(ValidationCodeEntity validationCodeEntity);

    ValidationCodeEntity getValidCode(Long id);

    int createValidCode(String code, String phone, Integer scope);

    ValidationCodeEntity getValidCodeByPhone(String phone);

    ValidationCodeEntity getValidCodeByPhone(String phone, Integer scope);

    boolean checkValidCode(String phone, String code, Integer scope);
}
