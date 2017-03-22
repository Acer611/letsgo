package com.umessage.letsgo.travel.controller.journey;
import com.elasticsearch.EsClientHelper;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.po.journey.*;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.system.RegionEntityPo;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleDetailRequest;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleRequest;
import com.umessage.letsgo.domain.vo.journey.response.PromptInfoResponse;
import com.umessage.letsgo.domain.vo.journey.response.SchedulePageResponse;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleResponse;
import com.umessage.letsgo.domain.vo.journey.response.TeamScheduleResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.requset.TeamRequest;
import com.umessage.letsgo.domain.vo.team.respone.TeamPageRespone;
import com.umessage.letsgo.service.api.journey.*;
import com.umessage.letsgo.service.api.message.IQMessageService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import com.umessage.letsgo.service.common.constant.Constant;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by ZhaoYidong on 2016/5/31.
 *
 */
@Api(value = "旅行社端行程管理接口", description = "关于旅行社端行程的相关操作")
@Controller
@RequestMapping(value = "/schedule")
public class ScheduleController {

    private Logger logger = LogManager.getLogger(ScheduleDetailScenicController.class.getName());

    @Resource
    private IScheduleService scheduleService;
    @Resource
    private IScheduleDetailsService scheduleDetailsService;
    @Resource
    private IPromptInfoService promptService;
    @Resource
    private ITravelAgencyService travelService;
    @Resource
    private ITeamService teamService;
    @Resource
    private UserLoginHelper userLoginHelper;
    @Resource
    private IMemberService memberService;

    @Resource
    private IUserService userService;
    @Resource
    private IQMessageService qMessageService;
    @Resource
    private IShoppingScheduleService shoppingScheduleService;
    @Resource
    private IOwnExpenseScheduleService ownExpenseScheduleService;

    /**
     * 主界面接口：
     * @return
     */
    @RequestMapping("mainPage")
    public String mainPage(Model model){
        ScheduleRequest request = new ScheduleRequest(Constant.pageNum,Constant.pageSize);
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        UserEntity currentUserEntity = userService.getCurrentUserEntity();
        if(currentUserEntity.getRole() != null && currentUserEntity.getRole() == 7){
            return  "redirect:/schedule/getTeamList?pageNum=1";
        }
        if(travel == null || travel.getId() == null) return "redirect:/login";
        request.setTravelId(travel.getId());

        long a1 = System.currentTimeMillis();
        //SchedulePageResponse response = scheduleService.getSchedules(request);
        long a2 = System.currentTimeMillis();
        logger.info("获取行程耗时："+ (a2-a1) );
        long a3 = System.currentTimeMillis();
        List<ScheduleEntity> travelingList = scheduleService.getTravelListByStatus(request,Constant.TRAVELING);
        List<ScheduleEntity> traveledList = scheduleService.getTravelListByStatus(request,Constant.TRAVELED);
        List<ScheduleEntity> prepareTravelList = scheduleService.getTravelListByStatus(request,Constant.PREPARE_TRAVEL);
        long a4 = System.currentTimeMillis();
        logger.info("获取旅行社端信息耗时："+ (a4-a3) );
        //获取问题反馈的未读数
        long noReadNum = qMessageService.getNoReadSubjectNum(String.valueOf(travel.getId()));

        model.addAttribute("noReadNum",noReadNum);
        model.addAttribute("traveledList",traveledList);
        model.addAttribute("travelingList",travelingList);
        model.addAttribute("prepareTravelList",prepareTravelList);
        //model.addAttribute("response",response);
        return "journey/scheduleList";
    }

    //参数不能为空，否则抛出异常
    private void paramNotNull(ScheduleRequest scheduleRequest){
        if(scheduleRequest == null){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：行程请求对象不能为空！");
        }
    }
    /**
     * 行程列表
     * @return
     */
    @RequestMapping("search")
    public String search(ScheduleRequest request, Model model) {
        paramNotNull(request);
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        if(travel == null || travel.getId() == null) return "redirect:/login";
        request.setTravelId(travel.getId());

        SchedulePageResponse response = scheduleService.getSchedules(request);
        model.addAttribute("response",response);
        if(Constant.MAIN_PAGE_MORE.equals(request.getClickMore()) ||
                Constant.UPDATE_TEMPLATE_More.equals(request.getClickMore())){
            model.addAttribute("travelTemplate",request.getTravelTemplate());
            return "journey/moreScheduleList";
        }
        return "journey/scheduleList";
    }

    /**
     * 行程列表
     * @return
     */
    @RequestMapping("searchAjax")
    @ResponseBody
    public SchedulePageResponse searchAjax(ScheduleRequest request) {
        paramNotNull(request);
        if (request.getOneSelf() != null && request.getOneSelf()==1){
            UserEntity currentUserEntity = userService.getCurrentUserEntity();
            request.setUserId(currentUserEntity.getId());
        }else {
            TravelAgencyEntity travel = travelService.getCurrentTravel();
            if(travel == null || travel.getId() == null){
                return SchedulePageResponse.createNotFoundResponse("没有发现旅行社");
            }
            request.setTravelId(travel.getId());
        }

        return scheduleService.getSchedules(request);
    }

    /**
     * 点击创建新行程操作
     * @return
     */
    @RequestMapping("scheduleInit")
    public String scheduleInit(ScheduleRequest request, Model model){
        paramNotNull(request);
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        if(travel == null || travel.getId() == null) return "redirect:/login";
        request.setTravelId(travel.getId());

        SchedulePageResponse response = scheduleService.getSchedules(request);
        model.addAttribute("list",response.getScheduleList());
        return "journey/scheduleInit";
    }

    /**
     * 选择创建行程方式
     * 已有行程或新行程;已有行程id
     * @return
     */
    @RequestMapping("scheduleInitChoose")
    public String scheduleInitChoose(ScheduleRequest request,Model model){
        paramNotNull(request);
        if(Constant.UPDATE_TEMPLATE.equals(request.getTravelTemplate())){
            if(Constant.UPDATE_TEMPLATE_More.equals(request.getClickMore())){
                return search(request,model);
            }
            scheduleShow(request.getId(),model);
        }
        return "journey/scheduleInitTemplate";
    }

    /**
     * 预览行程
     */
    @RequestMapping("schedulePreview")
    public String schedulePreview(Long id,Model model){
        scheduleShow(id,model);
        return "journey/schedulePreview";
    }


    /**
     * 编辑行程
     */
    @RequestMapping("scheduleEdit")
    public String scheduleEdit(Long id,Model model){
        scheduleShow(id,model);
        return "journey/scheduleEdit";
    }

    private void scheduleShow(long id,Model model){
        ScheduleEntity scheduleEntity = scheduleService.getSchedule(id);
        PromptInfoEntity promptEntity = promptService.getByScheduleId(id);

        List<ShoppingScheduleEntity> shoppingSchedules = shoppingScheduleService.selectShoppingByScheduleId(id);
        List<OwnExpenseScheduleEntity> ownExpenseSchedules = ownExpenseScheduleService.selectOwnExpenseScheduleByScheduleId(id);
        promptEntity.setShoppingScheduleList(shoppingSchedules);
        promptEntity.setOwnExpenseScheduleList(ownExpenseSchedules);
        model.addAttribute("scheduleEntity",scheduleEntity);
        model.addAttribute("promptEntity",promptEntity);
    }


    /**
     * 保存行程
     */
    @RequestMapping(value = "scheduleSave", method = RequestMethod.POST)
    @ResponseBody
    public ScheduleResponse scheduleSave(ScheduleEntity schedule){
        return scheduleService.scheduleSave(schedule);//返回到当前页。
    }
    /**
     * 更新行程
     */
    @RequestMapping(value = "scheduleUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ScheduleResponse scheduleUpdate(ScheduleEntity schedule){
        return scheduleService.scheduleUpdate(schedule);//返回到当前页。
    }


    /**
     * 保存每日行程
     */
/*    @RequestMapping(value = "scheduleDetailSave", method = RequestMethod.POST)
    @ResponseBody
    public ScheduleResponse scheduleDetailSave(ScheduleDetailEntity scheduleDetail){
        return scheduleDetailsService.saveScheduleDeails(scheduleDetail);//返回到当前页。
    }*/

    /**
     * 保存每日行程信息
     * @param scheduleDetail
     * @return
     */
    @RequestMapping(value = "scheduleDetailSave", method = RequestMethod.POST)
    @ResponseBody
    public ScheduleResponse scheduleDetailSave(@RequestBody ScheduleDetailRequest scheduleDetail){
        TravelAgencyEntity travelEntity = travelService.getCurrentTravel();
        if(null == travelEntity){
            return ScheduleResponse.createUserNotLoginResponse();
        }
        return scheduleDetailsService.saveScheduleDetail(travelEntity,scheduleDetail);//返回到当前页。
    }




    /**
     *  更新每日行程
     */
/*    @RequestMapping(value = "scheduleDetailUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ScheduleResponse scheduleDetailUpdate(ScheduleDetailEntity scheduleDetail){

        return scheduleDetailsService.updateDetails(scheduleDetail);//返回到当前页。
    }*/

    /**
     * 更新每日行程
     */
    @RequestMapping(value = "scheduleDetailUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ScheduleResponse scheduleDetailUpdate(@RequestBody ScheduleDetailRequest scheduleDetail){

        return scheduleDetailsService.updateScheduleDetailDetail(scheduleDetail);//返回到当前页。
    }

    /**
     * 提示信息保存
     */
    @RequestMapping(value = "promptInfoSave", method = RequestMethod.POST)
    @ResponseBody
    public PromptInfoResponse promptInfoSave(@RequestBody PromptInfoEntity promptInfo){
        if(promptInfo != null){
            //验证字段长度
            String ownExpenceInfo = promptInfo.getOwnExpenceInfo();//自费项目补充信息
            if (!StringUtils.isEmpty(ownExpenceInfo) && ownExpenceInfo.length() > 20000){
                return PromptInfoResponse.createInvalidParameterResponse("自费项目补充信息内容过长，请重新输入");
            }
            String shoppingPlace = promptInfo.getShoppingPlace();//购物场所补充信息
            if (!StringUtils.isEmpty(shoppingPlace) && shoppingPlace.length() > 20000){
                return PromptInfoResponse.createInvalidParameterResponse("购物场所补充信息内容过长，请重新输入");
            }
            String travelAgencyInfo = promptInfo.getTravelAgencyInfo();//组团社信息
            if (!StringUtils.isEmpty(travelAgencyInfo) && travelAgencyInfo.length() > 20000){
                return PromptInfoResponse.createInvalidParameterResponse("组团社信息内容过长，请重新输入");
            }
            String groupClubInfo = promptInfo.getGroupClubInfo();//接团社信息
            if (!StringUtils.isEmpty(groupClubInfo) && groupClubInfo.length() > 20000){
                return PromptInfoResponse.createInvalidParameterResponse("接团社信息内容过长，请重新输入");
            }
            promptService.addPromptInfo(promptInfo);
        }
        return new PromptInfoResponse(promptInfo);//返回当前页
    }

    /**
     * 提示信息更新
     */
    @RequestMapping(value = "promptInfoUpdate", method = RequestMethod.POST)
    @ResponseBody
    public PromptInfoResponse promptInfoUpdate(@RequestBody PromptInfoEntity promptInfo){
        if(promptInfo != null){
            //验证字段长度
            String ownExpenceInfo = promptInfo.getOwnExpenceInfo();//自费项目补充信息
            if (!StringUtils.isEmpty(ownExpenceInfo) && ownExpenceInfo.length() > 20000){
                return PromptInfoResponse.createInvalidParameterResponse("自费项目补充信息内容过长，请重新输入");
            }
            String shoppingPlace = promptInfo.getShoppingPlace();//购物场所补充信息
            if (!StringUtils.isEmpty(shoppingPlace) && shoppingPlace.length() > 20000){
                return PromptInfoResponse.createInvalidParameterResponse("购物场所补充信息内容过长，请重新输入");
            }
            String travelAgencyInfo = promptInfo.getTravelAgencyInfo();//组团社信息
            if (!StringUtils.isEmpty(travelAgencyInfo) && travelAgencyInfo.length() > 20000){
                return PromptInfoResponse.createInvalidParameterResponse("组团社信息内容过长，请重新输入");
            }
            String groupClubInfo = promptInfo.getGroupClubInfo();//接团社信息
            if (!StringUtils.isEmpty(groupClubInfo) && groupClubInfo.length() > 20000){
                return PromptInfoResponse.createInvalidParameterResponse("接团社信息内容过长，请重新输入");
            }
            promptService.updatePromptInfo(promptInfo);
        }
        return new PromptInfoResponse(promptInfo);//返回当前页
    }

    /**
     *跳转确认行程页面
     */
    @RequestMapping("confirmScheduleInit")
    public String confirmScheduleInit(Long tId,Model model){
        model.addAttribute("tId",tId);
        return "journey/confirmSchedule";
    }
    /**
     *确认行程
     */
    @RequestMapping("confirmSchedule")
    @ResponseBody
    public CommonResponse confirmSchedule(Long tId) throws UnsupportedEncodingException {
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        return scheduleService.confirmSchedule(tId,travel.getId());//返回当前页
    }

//    /**
//     * 进入游客导入页面
//     */
//    @RequestMapping("memberImportInit")
//    public String memberImportInit(Long tId,Model model){
//        model.addAttribute("tId",tId);
//        return "team/importMember";
//    }

    @RequestMapping("teamNumIsRepeat")
    @ResponseBody
    public Map<String,Boolean> teamNumIsRepeat(String teamNum){
        UserResponse userResponse = userLoginHelper.getLoginUser();
        UserEntity user = userResponse.getUserEntity();
        if(user == null || user.getId() == null){
            throw new BusinessException(ErrorConstant.USER_NOT_LOGIN,"用户未登录或登录信息过期");
        }
//        TravelAgencyEntity travel = travelService.getByUserId(user.getId());
        TravelAgencyEntity travel = travelService.getByTravelerId(user.getTravelerId());
        boolean isRepeated= teamService.teamNumIsRepeat(teamNum.trim(),travel.getId());
        Map<String,Boolean> map = new HashMap<String,Boolean>();
        map.put("valid",isRepeated);
        return map;
    }

    /**
     *查询相册行程单列表【旅行社端】
     */
    @RequestMapping("/getTeamList")
    public String getTeamList(@RequestParam(value="pageNum", defaultValue="1") Integer pageNum , Model model) {
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        if(travel == null || travel.getId() == null) return "redirect:/login";
        ScheduleRequest request=new ScheduleRequest();
        request.setTravelId(travel.getId());
        request.setPageNum(pageNum);
        TeamScheduleResponse list=scheduleService.getSchedulesByTravelId(request);
        model.addAttribute("list",list);
        return "teamphoto/lookPhoto";
    }

    /**
     * 行程监控
     * @param model
     * @return
     */
    @RequestMapping("/travelMonitor")
    public String travelMonitor(Model model) {
        // 获取当前登录的旅行社用户
        TravelAgencyEntity travel = travelService.getCurrentTravel();

        // 获取正在进行的行程数量、游客数量
        int travellingCount = scheduleService.getScheduleCountInTravel(travel.getId(), 1);
        int travellingTouristCount = memberService.getMemberCountInTravel(travel.getId(), 1);

        // 获取即将出行的行程数量、游客数量
        int goToTravelCount = scheduleService.getScheduleCountInTravel(travel.getId(), 2);
        int goToTravelTouristCount = memberService.getMemberCountInTravel(travel.getId(), 2);

        // 获取已结束的行程数量、游客数量
        int traveledCount = scheduleService.getScheduleCountInTravel(travel.getId(), 3);
        int traveledTouristCount = memberService.getMemberCountInTravel(travel.getId(), 3);

        // 获取正在进行的行程列表
        List<ScheduleDetailEntityVo> scheduleEntityList = scheduleService.getScheduleListInTravel(travel.getId(), 1);

        model.addAttribute("travellingCount", travellingCount);
        model.addAttribute("travellingTouristCount", travellingTouristCount);
        model.addAttribute("goToTravelCount", goToTravelCount);
        model.addAttribute("goToTravelTouristCount", goToTravelTouristCount);
        model.addAttribute("traveledCount", traveledCount);
        model.addAttribute("traveledTouristCount", traveledTouristCount);
        model.addAttribute("scheduleEntityList", scheduleEntityList);

        return "journey/travelMonitoring";
    }

    //即将开始的行程的列表呈现
    @RequestMapping("/StartTripList")
    public String getStartTripList(ScheduleRequest request,Model model){
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        request.setTravelId(travel.getId());
        request.setStatus(2);
        SchedulePageResponse  response= scheduleService.getJjTripList(request);
        model.addAttribute("response",response);
        return "journey/travelMonitorings";
    }

    //已经结束的行程的列表呈现
    @RequestMapping("/EndTripList")
    public String getEndTripList(ScheduleRequest request,Model model){
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        request.setTravelId(travel.getId());
        request.setStatus(3);
        SchedulePageResponse response  = scheduleService.getEndTripList(request);
        model.addAttribute("response",response);
        return "journey/travelMonitorings";
    }

    //模糊匹配城市与国家
    @RequestMapping("/getCityByContent")
    @ResponseBody
    public List<RegionEntityPo> getCityByContent(@RequestParam String countryName){

      //以下为使用搜索引擎Elasticsearch 获取城市的代码逻辑
        List<RegionEntityPo> cityList = new ArrayList<>();

        try {
            //获取elasticsearch的client
             TransportClient esTransportClient =  EsClientHelper.getInstance().getClient();

            //获取搜索的结果,调用搜索引擎的Search API
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("alias", countryName))
                    .should(QueryBuilders.prefixQuery("areaengname",countryName)).should(QueryBuilders.prefixQuery("areapinyinname", countryName));

            SearchResponse searchResponse = esTransportClient.prepareSearch("letsgo").setTypes("city").setQuery(queryBuilder).get();

            //处理搜索的结果集
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            for (SearchHit searchHint:searchHits) {
                RegionEntityPo regionEntity = new RegionEntityPo();
                Map resultMap =   searchHint.getSource();
                String parentId = null;
                //获取父对象，以便组装country信息
                Object pid = resultMap.get("parentid");
                if(null!=pid){
                    parentId = pid.toString();
                }
                //调用搜索引擎的GET API
                GetResponse response = esTransportClient.prepareGet("letsgo","city",parentId).get();
                Map pidMap =  response.getSource();
                //组装返回结果，和原来保持一致
                regionEntity = this.getRegionEntity(resultMap,pidMap,regionEntity);
                cityList.add(regionEntity);
            }

            //esTransportClient.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return cityList;
    }

    private RegionEntityPo getRegionEntity(Map resultMap, Map pidMap, RegionEntityPo regionEntity) {
        if(null!= pidMap &&!pidMap.isEmpty() && null!= pidMap.get("areaid")){
            regionEntity.setCountry(null != pidMap.get("alias")?pidMap.get("alias").toString():null);
            regionEntity.setCountryId(null!= pidMap.get("areaid")?pidMap.get("areaid").toString():null);
        }
        if(!resultMap.isEmpty()){
            regionEntity.setAlias(null!= resultMap.get("alias") ? resultMap.get("alias").toString() : null);
            regionEntity.setAreaengname(null!= resultMap.get("areaengname") ? resultMap.get("areaengname").toString() : null);
            regionEntity.setAreaid(null!= resultMap.get("areaid") ? resultMap.get("areaid").toString() : null);
            regionEntity.setAreapinyinname(null!= resultMap.get("areapinyinname") ? resultMap.get("areapinyinname").toString() : null);
            regionEntity.setDelta(null!= resultMap.get("delta") ? resultMap.get("delta").toString() : null);
            regionEntity.setLat(null!= resultMap.get("lat") ? Double.parseDouble(resultMap.get("lat").toString()) : null);
            regionEntity.setLng(null!= resultMap.get("lng") ? Double.parseDouble(resultMap.get("lng").toString()) : null);
            regionEntity.setLv(null!= resultMap.get("lv") ? Integer.parseInt(resultMap.get("lv").toString()) : null);
            regionEntity.setParentid(null!= resultMap.get("parentid") ? resultMap.get("parentid").toString() : null);
            regionEntity.setTimezone(null!= resultMap.get("timezone") ? resultMap.get("timezone").toString() : null);
        }
        return regionEntity;
    }

    //根据国家名称查团队信息
    @RequestMapping("/getTeamByCountryName")
    public String getTeamByCountryName(TeamRequest request, Model model){
        logger.info("country1"+request.getCountry1());
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        request.setTravelId(travel.getId());
        request.setStatus(1);
        request.setTodayDay(new Date());
        TeamPageRespone response = teamService.selectTeamListByCountryN(request);
        model.addAttribute("response",response);

        return "journey/travelMonitoringdetail";
    }

}
