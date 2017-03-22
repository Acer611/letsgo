package com.umessage.letsgo.h5.notice;

import com.umessage.letsgo.domain.vo.notice.respone.NoticeRespone;
import com.umessage.letsgo.service.api.notice.IWebNoticeService;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * Created by ZhaoYidong on 2016/6/16.
 */
@Api(value = "集合位置页面", description = "关于集合位置的页面")
@Controller
@RequestMapping(value = "/web/gather")
public class WebGatherController {

    @Resource
    private IWebNoticeService webNoticeService;

    @RequestMapping(value = "/getGatherLocation", method = RequestMethod.GET)
    @ApiOperation(value = "通知，集合位置", notes = "通知，集合位置，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public String getGatherLocation(
            @ApiParam(value = "通知，集合id", required = true) @RequestParam(required = true)Long id,
            @ApiParam(value = "经度", required = true) @RequestParam(required = true)double lng,
            @ApiParam(value = "纬度", required = true) @RequestParam(required = true)double lat, Model model) {
        NoticeRespone response =webNoticeService.getNotice(id);

        model.addAttribute("response",response);
        model.addAttribute("lng",lng);
        model.addAttribute("lat",lat);
        return "notice/noticeShow";
    }

}
