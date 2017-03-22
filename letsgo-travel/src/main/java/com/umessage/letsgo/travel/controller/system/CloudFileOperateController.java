package com.umessage.letsgo.travel.controller.system;

import com.umessage.letsgo.core.utils.JsonUtils;
import com.umessage.letsgo.service.api.system.ICloudFileOperateService;
import com.umessage.letsgo.service.api.team.IWebMemberService;
import com.umessage.letsgo.service.common.constant.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoYidong on 2016/7/7.
 */
@Controller
@RequestMapping("/cloudFile")
public class CloudFileOperateController {

    @Resource
    private ICloudFileOperateService cloudService;

    @RequestMapping(value={"/uploadCloudPhotos"},method= RequestMethod.POST)
    @ResponseBody
    public List<String> uploadCloudPhotos(@RequestParam MultipartFile[] file) {
        return cloudService.fileUpload(file);
    }

    @RequestMapping(value={"/deleteCloudPhotos"},method= RequestMethod.GET)
    @ResponseBody
    public Map<String,String> deleteCloudPhotos(String url){
        String json = cloudService.deleteFile(url, Constant.PHOTO_URL);
        return JsonUtils.json2map(json);
    }

}
