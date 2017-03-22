package com.umessage.letsgo.service.impl.journey.Helper;

import com.umessage.letsgo.core.utils.CreatePdfUtil;
import com.umessage.letsgo.core.utils.IDUtil;
import com.umessage.letsgo.core.utils.PdfTranferUtil;
import com.umessage.letsgo.domain.po.journey.SurveyDetailEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.service.api.journey.ISurveyDetailService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.common.helper.PdfHelper;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

/**
 * Created by myz on 2016/10/18.
 */
@Service
@EnableAsync
public class HtmlPdfHelper {

    private Logger logger = Logger.getLogger(HtmlPdfHelper.class);

    @Resource
    private ISurveyDetailService surveyDetailService;

    @Resource
    private VelocityHtmlHelper velocityHtmlHelper;

    @Resource
    private IUserService userService;

    @Resource
    private ITeamService teamService;


    @Async("htmlPdfExecutor")
    public void htmlZpfd(String surveyDetailid, String basePath){
        try {
            //html转pdf
//          String HTML = Constant.H5_BASE_URL+"survey/getSurveyWithSign?surveyUserId="+surveyDetailid;
//          String HTML = velocityHtmlHelper.velocityCreateHtml(surveyDetailid, basePath);
            SurveyDetailEntity surveyDetail = surveyDetailService.getById(Long.parseLong(surveyDetailid));
            TeamEntity teamEntity = teamService.getTeamByTXGroupId(surveyDetail.getTxgroupId());
            String teamNum = teamEntity.getTeamNum();
            UserEntity userEntity = userService.getUserById(surveyDetail.getUserId());
            Long id = userEntity.getId();
            String realName = userEntity.getRealName();

            logger.info("htmlZpfd html地址："+basePath);
            String folder=System.getProperty("java.io.tmpdir");
//            String DEST  = folder+"//"+ IDUtil.uuid()+".pdf";
            String DEST = folder+"//"+teamNum+"-"+id+"-"+realName+".pdf";
            File file = new File(DEST);
            file.getParentFile().mkdirs();

            CreatePdfUtil.htmlToPdfOne(basePath,DEST);
            //pdf上传到腾讯云，改变j_survey_detail的pdfurl
            String DESTN = teamNum+"-"+id+"-"+realName;
            String pdfUrl = PdfHelper.savePdfCloudFile(DEST,DESTN);
            SurveyDetailEntity surveyDetailEntity = new SurveyDetailEntity();
            surveyDetailEntity.setId(Long.parseLong(surveyDetailid));
            surveyDetailEntity.setPdfUrl(pdfUrl);
            surveyDetailService.updatePdfURL(surveyDetailEntity);
            //生成pdf缩略图
            String thumbnailUrl = folder+"//"+IDUtil.uuid()+".png";
            PdfTranferUtil.tranfer(DEST,thumbnailUrl);
            //pdf缩略图上传到腾讯云，改变j_survey_detail的thumbnailUrl
            String realThumbnailUrl = PdfHelper.saveThumbnailCloudFile(thumbnailUrl);
            surveyDetailEntity.setThumbnailUrl(realThumbnailUrl);
            SurveyDetailEntity pngSurveyDetailEntity = new SurveyDetailEntity();
            pngSurveyDetailEntity.setId(Long.parseLong(surveyDetailid));
            pngSurveyDetailEntity.setThumbnailUrl(realThumbnailUrl);
            surveyDetailService.updatePngURL(pngSurveyDetailEntity);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     *
     * htmlcode 转化为PDF页面
     * @param htmlCode
     * @param surveyDetailid
     */
    public void htmlToPDF(String htmlCode,String surveyDetailid,String signUrl) {

        try {
            //获取问卷明细的内容
            SurveyDetailEntity surveyDetail = surveyDetailService.getById(Long.parseLong(surveyDetailid));

            //根据腾讯组ID 获取团队信息
            TeamEntity teamEntity = teamService.getTeamByTXGroupId(surveyDetail.getTxgroupId());
            String teamNum = teamEntity.getTeamNum();

            //获取用户信息
            UserEntity userEntity = userService.getUserById(surveyDetail.getUserId());
            Long id = userEntity.getId();
            String realName = userEntity.getRealName();

            //临时存放路径
            String folder = System.getProperty("java.io.tmpdir");
            String DEST = folder+ System.getProperty("file.separator")+teamNum+"-"+id+"-"+realName+".pdf";
            logger.info("临时文件存放路径为：" + DEST);
            String tempFilePath =folder + System.getProperty("file.separator") + teamNum + "-" + id + "-" + realName + ".pdf";

            File file = new File(DEST);
            file.getParentFile().mkdirs();
            //htmlcode转化为PDF
            CreatePdfUtil.htmlToPDFByHTML(htmlCode,DEST,tempFilePath);

            //pdf上传到腾讯云，改变j_survey_detail的pdfurl
            String DESTN = teamNum+"-"+id+"-"+realName;
            String pdfUrl = PdfHelper.savePdfCloudFile(DEST,DESTN);
            SurveyDetailEntity surveyDetailEntity = new SurveyDetailEntity();
            surveyDetailEntity.setId(Long.parseLong(surveyDetailid));
            surveyDetailEntity.setPdfUrl(pdfUrl);
            surveyDetailService.updatePdfURL(surveyDetailEntity);

            //生成pdf缩略图
            String thumbnailUrl = folder+"//"+IDUtil.uuid()+".png";
            PdfTranferUtil.tranfer(DEST,thumbnailUrl);
            logger.info("缩略图图片路径为：" + thumbnailUrl);

            //pdf缩略图上传到腾讯云，改变j_survey_detail的thumbnailUrl
            String realThumbnailUrl = PdfHelper.saveThumbnailCloudFile(thumbnailUrl);
            surveyDetailEntity.setThumbnailUrl(realThumbnailUrl);
            SurveyDetailEntity pngSurveyDetailEntity = new SurveyDetailEntity();
            pngSurveyDetailEntity.setId(Long.parseLong(surveyDetailid));
            pngSurveyDetailEntity.setThumbnailUrl(realThumbnailUrl);
            surveyDetailService.updatePngURL(pngSurveyDetailEntity);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
