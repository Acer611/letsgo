package com.umessage.letsgo.service.impl.system;

import com.umessage.letsgo.core.utils.IDUtil;
import com.umessage.letsgo.core.utils.JsonUtils;
import com.umessage.letsgo.service.api.system.ICloudFileOperateService;
import com.umessage.letsgo.service.common.constant.Constant;
import com.umessage.letsgo.service.common.helper.QCloudHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoYidong on 2016/7/7.
 */
@Service
public class CloudFileOperateServiceImpl implements ICloudFileOperateService{
    private static final Logger logger = Logger.getLogger(CloudFileOperateServiceImpl.class);

    @Override
    public List<String> fileUpload(MultipartFile[] files) {
        List<String> list = new ArrayList<String>();
        for (MultipartFile file:files){
            String path = Constant.PHOTO_URL+ IDUtil.uuid()+"-"+file.getOriginalFilename();
            try {
                String url =fileUploadCloud(path,file.getInputStream());
                list.add(url);
            } catch (Exception e) {
                logger.error(file.getName()+"图像上传失败\n"+e.getMessage(),e);
            }
        }
        return list;
    }

    private String fileUploadCloud(String path,InputStream inputStream) throws Exception {
        String url = QCloudHelper.fileUpload(path,inputStream);
        url = QCloudHelper.changDomain(url);
        return url;
    }

    @Override
    public String deleteFile(String url,String directory) {
        Map<String,String> map = new HashMap<String,String>();
        if(StringUtils.isEmpty(url) || !url.contains(directory)) {
            map.put("code","-1");
            map.put("message","文件路径不匹配！");
            return JsonUtils.obj2json(map);
        }
        url = url.substring(url.indexOf(directory));
        try {
            return QCloudHelper.deleteFile(url);
        } catch (Exception e) {
            logger.error("文件删除失败\n"+e.getMessage(),e);
            map.put("code","-1");
            map.put("message","文件删除失败！");
            return JsonUtils.obj2json(map);
        }
    }
}
