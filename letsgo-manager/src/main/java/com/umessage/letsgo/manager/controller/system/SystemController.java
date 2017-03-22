package com.umessage.letsgo.manager.controller.system;

import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.system.IRegionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by ZhaoYidong on 2016/6/17.
 */
@Controller
@RequestMapping(value = "/system")
public class SystemController {

    @Resource
    private IUserService userService;

    @Resource
    private IRegionService regionService;

    /**
     * 批量开通钱包
     * @return
     */
    /*
    @RequestMapping(value = "/batchOpenWalletForUser", method = RequestMethod.POST)
    @ResponseBody
    public WalletCommonResponse batchOpenWalletForUser() {
        return userService.batchOpenWalletForUser();
    }
    */

    /**
     * 批量设置加好友验证
     * @return
     */
    /*
    @RequestMapping(value = "/batchPortraitSetAllowType", method = RequestMethod.POST)
    @ResponseBody
    public com.qq.tim.vo.response.CommonResponse batchPortraitSetAllowType() {
        return userService.batchPortraitSetAllowType();
    }
    */

    /**
     * 批量创建用户邀请码
     * @return
     */
    /*
    @RequestMapping(value = "/batchCreateInvitationCode", method = RequestMethod.POST)
    @ResponseBody
    public WalletCommonResponse batchCreateInvitationCode() {
        return userService.batchCreateInvitationCode();
    }
    */


    @RequestMapping(value = "/batchUpdateLocation", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse batchUpdateLocation(Integer pageNum, Integer pageSize) {
        regionService.batchUpdateCityLocation(pageNum, pageSize);
        //regionService.batchUpdateCountryLocation(pageNum, pageSize);
        return new CommonResponse();
    }

    /*
    跳转批量处理页面
     */
    @RequestMapping(value = "/userForbatch",method = RequestMethod.GET)
    public String userForbatch(){
        return "batch";
    }
}

