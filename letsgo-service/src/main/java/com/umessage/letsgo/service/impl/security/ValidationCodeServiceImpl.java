package com.umessage.letsgo.service.impl.security;

import com.umessage.letsgo.dao.security.ValidationCodeDao;
import com.umessage.letsgo.domain.po.security.ValidationCodeEntity;
import com.umessage.letsgo.domain.vo.system.request.ValidationCodeRequest;
import com.umessage.letsgo.service.api.security.IValidationCodeService;
import com.umessage.letsgo.service.common.constant.Constant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/4.
 */
@Service
public class ValidationCodeServiceImpl implements IValidationCodeService {
    @Resource
    private ValidationCodeDao validationCodeDao;

    @Override
    public int create(ValidationCodeEntity validationCodeEntity) {
        validationCodeEntity.setCreateTime(new Date());
        validationCodeEntity.setVersion(0l);
        return validationCodeDao.insert(validationCodeEntity);
    }

    @Override
    public ValidationCodeEntity getValidCode(Long id) {
        ValidationCodeEntity validationCodeEntity = validationCodeDao.select(id);
        if (validationCodeEntity == null)
            return new ValidationCodeEntity();
        return validationCodeEntity;
    }

    @Override
    public int createValidCode(String code, String phone, Integer scope) {
        ValidationCodeEntity validationCode = new ValidationCodeEntity();
        validationCode.setPhone(phone);
        validationCode.setCode(code);
        validationCode.setValidTime(Constant.VALID_CODE_TIME*60*1000l);
        validationCode.setScope(scope);
        return this.create(validationCode);
    }

    /**
     * 获取有效的验证码
     * @param phone
     * @return
     */
    @Override
    public ValidationCodeEntity getValidCodeByPhone(String phone) {
        ValidationCodeEntity codeEntity = validationCodeDao.selectLatestWithPhone(phone);

        return this.calculateValidTime(codeEntity);
    }

    @Override
    public ValidationCodeEntity getValidCodeByPhone(String phone, Integer scope) {
        ValidationCodeRequest request = new ValidationCodeRequest();
        request.setPhone(phone);
        request.setScope(scope);

        ValidationCodeEntity codeEntity = validationCodeDao.selectLatestWithPhoneAndScope(request);

        return this.calculateValidTime(codeEntity);
    }

    private ValidationCodeEntity calculateValidTime(ValidationCodeEntity codeEntity) {
        if (codeEntity != null) {
            // 计算失效时间
            long validTime = codeEntity.getValidTime();
            long failureTime = codeEntity.getCreateTime().getTime() + validTime;

            // 对比当前时间和失效时间
            if (new Date().compareTo(new Date(failureTime)) <= 0) {
                return codeEntity;
            }
        }

        return new ValidationCodeEntity();
    }

    /**
     * 验证验证码
     * @param phone
     * @return
     */
    @Override
    public boolean checkValidCode(String phone, String code, Integer scope) {
        ValidationCodeEntity validationCodeEntity = this.getValidCodeByPhone(phone, scope);
        if (code.equals(validationCodeEntity.getCode())) {
            return true;
        }
        return false;
    }
}
