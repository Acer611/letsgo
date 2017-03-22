package com.umessage.letsgo.travel.controller.journey;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.umessage.letsgo.domain.po.journey.QuestionEntity;
import com.umessage.letsgo.domain.po.journey.SurveyDetailEntity;
import com.umessage.letsgo.domain.po.journey.SurveyEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.journey.request.QuestionRequest;
import com.umessage.letsgo.domain.vo.journey.request.SurveyCreateRequest;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.respone.QuestionInfo;
import com.umessage.letsgo.service.api.journey.IQuestionService;
import com.umessage.letsgo.service.api.journey.ISurveyDetailService;
import com.umessage.letsgo.service.api.journey.ISurveyService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    @Resource
    private ITeamService teamService;
    /*
    查询问卷或者创建问卷
     */
    @RequestMapping(value = "/surveyQueryOrSave", method = RequestMethod.GET)
    public String getSurvey(@RequestParam Long teamId, Model model) {
       SurveyEntity sur= surveyService.selectByTeamId(teamId);
        TeamEntity teamEntity = teamService.selectById(teamId);
        String title = "";
        if (teamEntity!=null){
            title = teamEntity.getName();
        }
        if(sur!=null&&sur.getSurveyStatus()==1){
            List<QuestionEntity> list=questionService.selectModel(sur.getId());
            List<QuestionInfo> radio=questionService.selectQuestion(sur.getId(),1);
            List<QuestionInfo> check=questionService.selectQuestion(sur.getId(),2);
            List<QuestionEntity> answer=questionService.selectAnswer(sur.getId());

            if(list.size()>10){
                list =  list.subList(1,11);
            }
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
            TravelAgencyEntity travel =  travelAgencyService.getCurrentTravel();

            List<SurveyEntity> list = surveyService.getAllSurvey(travel.getId());
            List<QuestionEntity> modelList = new ArrayList<>();
            if(list.size()>10){
                list =  list.subList(0,10);
            }
            SurveyEntity surveyEntity = new SurveyEntity();
            surveyEntity.setTeamId(teamId);
            surveyEntity.setTitle(title);
            surveyEntity.setTravelId(travel.getId());
            //surveyService.insert(surveyEntity);
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
    public JSONObject saveQuestionAndOpiton(@RequestBody SurveyCreateRequest SurveyCreateRequest ) {

        JSONArray json = SurveyCreateRequest.getJson();
        SurveyEntity surveyEntity = SurveyCreateRequest.getSurveryEntity();
        surveyService.insert(surveyEntity);
        List<QuestionRequest> list = JSONObject.parseArray(json.toString(),QuestionRequest.class);
        questionService.createQuestionAndOpiton(list,surveyEntity);
        JSONObject js=new JSONObject();
        js.put("code",0);
        js.put("surveryId",surveyEntity.getId());
        return js;

    }
    /*
         获取问卷pdf列表
     */
    @RequestMapping(value = "/getQuestionnaireList", method = RequestMethod.GET)
    public String getQuestionnaireList(@RequestParam Long surveyId, Model model) {
//      List<SurveyDetailEntity> list=surveyDetailService.getPdfList(surveyId);
        List<SurveyDetailEntity> list=surveyDetailService.getPdfListOne(surveyId);
        SurveyEntity surveyEntity = surveyService.selectById(surveyId);

        model.addAttribute("list",list);
        model.addAttribute("surveyId",surveyId);
        model.addAttribute("teamId", surveyEntity.getTeamId());
        model.addAttribute("title", surveyEntity.getTitle());
        return "journey/surveypdf";
    }
    /*
   获取已完成问卷列表
    */
    @RequestMapping(value = "/getAllSurvey", method = RequestMethod.GET)
    public String getAllSurvey(@RequestParam Long surveyId,Model model) {
        UserResponse userResponse =oauth2LoginHelper.getLoginUser();
//        TravelAgencyEntity travel =travelAgencyService.getByUserId(userResponse.getUserEntity().getId());
        TravelAgencyEntity travel =travelAgencyService.getByTravelerId(userResponse.getUserEntity().getTravelerId());
        List<SurveyEntity> list=surveyService.getAllSurvey(travel.getId());
        model.addAttribute("list",list);
        model.addAttribute("surveyId",surveyId);
        return "journey/moreSurverList";
    }

    /*
    获取单个问卷
     */
    @RequestMapping(value = "/getOneSurvey", method = RequestMethod.GET)
    public String getOneSurvey(@RequestParam Long oldSurveyId, Long teamId,String title,Long travelId,
                               Model model) {
        TeamEntity teamEntity = teamService.selectById(teamId);
        if (teamEntity!=null){
            title = teamEntity.getName();
        }
        SurveyEntity newSurveyEntity = new SurveyEntity();
        newSurveyEntity.setTeamId(teamId);
        newSurveyEntity.setTitle(title);
        newSurveyEntity.setTravelId(travelId);
        model.addAttribute("newEntity",newSurveyEntity);

        SurveyEntity surveyEntity = surveyService.selectById(oldSurveyId);
        model.addAttribute("surveyEntity",surveyEntity);
        List<QuestionEntity> list=questionService.selectModel(oldSurveyId);
        List<QuestionInfo> radio=questionService.selectQuestion(oldSurveyId,1);
        List<QuestionInfo> check=questionService.selectQuestion(oldSurveyId,2);
        List<QuestionEntity> answer=questionService.selectAnswer(oldSurveyId);
        model.addAttribute("list",list);
        model.addAttribute("radio",radio);
        model.addAttribute("check",check);
        model.addAttribute("answer",answer);
        model.addAttribute("listnew",list);
        model.addAttribute("radionew",radio);
        model.addAttribute("checknew",check);
        model.addAttribute("answernew",answer);
        return "journey/diaoyanone";
    }


    @RequestMapping(value = "/createSurveyByModel", method = RequestMethod.GET)
    public String createSurveyByModel( Long teamId,String title ,Model model) {
        TravelAgencyEntity travel =  travelAgencyService.getCurrentTravel();
        TeamEntity teamEntity = teamService.selectById(teamId);
        if (teamEntity!=null){
            title = teamEntity.getName();
        }
        SurveyEntity surveyEntity = new SurveyEntity();
        surveyEntity.setTeamId(teamId);
        surveyEntity.setTitle(title);
        surveyEntity.setTravelId(travel.getId());
        model.addAttribute("teamId",teamId);
        model.addAttribute("title",title);
        model.addAttribute("trivateId",travel.getId());
        model.addAttribute("surveyEntity",surveyEntity);
        return "journey/diaoyan";
    }


}
