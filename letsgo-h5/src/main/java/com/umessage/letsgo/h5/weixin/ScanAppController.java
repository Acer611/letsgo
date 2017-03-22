package com.umessage.letsgo.h5.weixin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lizhen on 2016/11/24.
 */

@Controller
public class ScanAppController {

    /**
     * 微信扫描APP二维码跳转指定页面
     * @return
    */
    @RequestMapping(value = "/t/{teamId}")
    public String getWeChatInfoByOpenID(@PathVariable String teamId) {
        return "scanerror";
    }

}
