package com.qq.tim.init;

import com.qq.tim.constant.TimConstant;
import com.qq.tim.util.KeyManager;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by zhajl on 2016/4/29.
 */
public class TlsSignInitialize implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        TimConstant.TLS_PRIVATE_KEY = KeyManager.getKey(TimConstant.TLS_PRIVATE_KEY_FILE);
        TimConstant.TLS_PUBLIC_KEY = KeyManager.getKey(TimConstant.TLS_PUBLIC_KEY_FILE);
    }
}
