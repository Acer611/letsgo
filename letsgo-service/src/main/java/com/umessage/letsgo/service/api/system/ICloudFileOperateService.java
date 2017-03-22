package com.umessage.letsgo.service.api.system;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by ZhaoYidong on 2016/7/7.
 */
public interface ICloudFileOperateService {

    /**
     * 文件上传
     */
    List<String> fileUpload(MultipartFile[]files);

    /**
     * 文件删除
     */
    String deleteFile(String url, String directory);
}
