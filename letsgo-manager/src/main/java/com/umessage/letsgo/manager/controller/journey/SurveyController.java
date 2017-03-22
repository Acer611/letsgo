package com.umessage.letsgo.manager.controller.journey;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.umessage.letsgo.domain.po.journey.QuestionEntity;
import com.umessage.letsgo.domain.po.journey.SurveyDetailEntity;
import com.umessage.letsgo.domain.po.journey.SurveyEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.journey.request.QuestionRequest;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.respone.QuestionInfo;
import com.umessage.letsgo.service.api.journey.IQuestionService;
import com.umessage.letsgo.service.api.journey.ISurveyDetailService;
import com.umessage.letsgo.service.api.journey.ISurveyService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengguoqing on 2016/9/1.
 */
@Controller
@RequestMapping(value = "/survey")
public class SurveyController {
    /**
     * 初始数据获取
     */
    @Resource
    private ISurveyService surveyService;
    @Resource
    private IQuestionService questionService;
    @Resource
    private ISurveyDetailService surveyDetailService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;
    @Resource
    private ITravelAgencyService travelAgencyService;
    /*
    查询问卷或者创建问卷
     */
    @RequestMapping(value = "/surveyQueryOrSave", method = RequestMethod.GET)
    public String getSurvey(@RequestParam Long teamId,@RequestParam String title, Model model) {
       SurveyEntity sur= surveyService.selectByTeamId(teamId);
        if(sur!=null&&sur.getSurveyStatus()==1){
            List<QuestionEntity> list=questionService.selectModel(sur.getId());
            List<QuestionInfo> radio=questionService.selectQuestion(sur.getId(),1);
            List<QuestionInfo> check=questionService.selectQuestion(sur.getId(),2);
            List<QuestionEntity> answer=questionService.selectAnswer(sur.getId());
            model.addAttribute("list",list);
            model.addAttribute("radio",radio);
            model.addAttribute("check",check);
            model.addAttribute("answer",answer);
            model.addAttribute("surveyEntity",sur);
            model.addAttribute("teamId",teamId);
            model.addAttribute("title",title);
            model.addAttribute("surveyId",sur.getId());
            return "journey/survey";
        }
        if(sur==null){
            UserResponse userResponse =oauth2LoginHelper.getLoginUser();
//            TravelAgencyEntity travel =travelAgencyService.getByUserId(userResponse.getUserEntity().getId());
            TravelAgencyEntity travel =travelAgencyService.getByTravelerId(userResponse.getUserEntity().getTravelerId());
            List<SurveyEntity> list=surveyService.getAllSurvey(travel.getId());
            model.addAttribute("list",list);
            SurveyEntity surveyEntity=new SurveyEntity();
            surveyEntity.setTeamId(teamId);
            surveyEntity.setTitle(title);
            surveyEntity.setTravelId(travel.getId());
            surveyService.insert(surveyEntity);
            model.addAttribute("surveyEntity",surveyEntity);
            model.addAttribute("surveyId",surveyEntity.getId());
            model.addAttribute("teamId",teamId);
            model.addAttribute("title",title);
            model.addAttribute("list",list);
            return "journey/surverInit";
        }
        else{
            model.addAttribute("surveyEntity",sur);
            model.addAttribute("surveyId",sur.getId());
            model.addAttribute("teamId",teamId);
            model.addAttribute("title",title);
            return "journey/diaoyan";
        }
    }

    /*
    保存问题和选项
     */
    @RequestMapping(value = "/saveQuestionAndOpiton", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public JSONObject saveQuestionAndOpiton( @RequestBody JSONArray json) {
        List<QuestionRequest> list=JSONObject.parseArray(json.toString(),QuestionRequest.class);
            questionService.saveQuestionAndOpiton(list);
        JSONObject js=new JSONObject();
        js.put("code",0);
        return js;

    }
    /*
         获取问卷pdf列表
     */
    @RequestMapping(value = "/getQuestionnaireList", method = RequestMethod.GET)
    public String getQuestionnaireList(@RequestParam Long surveyId, Model model) {
        List<SurveyDetailEntity> list=surveyDetailService.getPdfListOne(surveyId);
        model.addAttribute("list",list);
        model.addAttribute("surveyId",surveyId);
        return "journey/surveypdf";
    }
    /*
   获取已完成问卷列表
    */
    @RequestMapping(value = "/getAllSurvey", method = RequestMethod.GET)
    public String getAllSurvey(Model model) {
        UserResponse userResponse =oauth2LoginHelper.getLoginUser();
//        TravelAgencyEntity travel =travelAgencyService.getByUserId(userResponse.getUserEntity().getId());
        TravelAgencyEntity travel =travelAgencyService.getByTravelerId(userResponse.getUserEntity().getTravelerId());
        List<SurveyEntity> list=surveyService.getAllSurvey(travel.getId());
        model.addAttribute("list",list);
        return "journey/moreSurverList";
    }

    /*
    获取单个问卷
     */
    @RequestMapping(value = "/getOneSurvey", method = RequestMethod.GET)
    public String getOneSurvey(@RequestParam Long surveyId,@RequestParam Long oldSurveyId, Model model) {
       SurveyEntity surveyEntity=surveyService.selectById(oldSurveyId);
        SurveyEntity newEntity=surveyService.selectById(surveyId);
        model.addAttribute("surveyEntity",surveyEntity);
        List<QuestionEntity> list=questionService.selectModel(oldSurveyId);
        List<QuestionInfo> radio=questionService.selectQuestion(oldSurveyId,1);
        List<QuestionInfo> check=questionService.selectQuestion(oldSurveyId,2);
        List<QuestionEntity> answer=questionService.selectAnswer(oldSurveyId);
        List<QuestionEntity> listnew=questionService.selectModel(surveyId);
        List<QuestionInfo> radionew=questionService.selectQuestion(surveyId,1);
        List<QuestionInfo> checknew=questionService.selectQuestion(surveyId,2);
        List<QuestionEntity> answernew=questionService.selectAnswer(surveyId);
        model.addAttribute("surveyId",surveyId);
        model.addAttribute("list",list);
        model.addAttribute("newEntity",newEntity);
        model.addAttribute("radio",radio);
        model.addAttribute("check",check);
        model.addAttribute("answer",answer);
        model.addAttribute("listnew",listnew);
        model.addAttribute("radionew",radionew);
        model.addAttribute("checknew",checknew);
        model.addAttribute("answernew",answernew);
        return "journey/diaoyanone";
    }
}
