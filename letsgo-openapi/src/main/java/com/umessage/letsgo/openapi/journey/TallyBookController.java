package com.umessage.letsgo.openapi.journey;

import javax.annotation.Resource;

import com.umessage.letsgo.domain.vo.journey.response.StatisticsConsumptionResponse;
import com.umessage.letsgo.domain.vo.journey.response.TallyBookResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.po.journey.TallyBookEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.api.journey.ITallyBookService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;

@Api(value = "消费记录接口", description = "关于消费记录的相关操作")
@Controller
@RequestMapping(value = "/api/tallyBook")
public class TallyBookController {

	@Resource
	private ITallyBookService iTallyBookService;
	@Resource
    private UserLoginHelper userLoginHelper;
	
	@RequestMapping(value = "/addConsumptionRecord", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存消费记录信息【游客端】", notes = "保存消费记录信息，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse addConsumptionRecord(@RequestBody TallyBookEntity request) {

		if(request == null || request.getType()==null || request.getMoney()==null || request.getCurrency()==null 
				|| request.getPaymentType()==null || request.getDate()==null || request.getTxGroupId()==null){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：消费记录请求或消费记录类型或消费金额或消费币种或消费支付方式或者消费时间或者腾讯群组id不能为空！");
        }
		CommonResponse response = null;
		// 1、获取当前登录的用户信息
        UserResponse user = userLoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            response = UserResponse.createUserNotLoginResponse();
        }

        request.setUserId(user.getUserEntity().getId());
        response = iTallyBookService.addConsumptionRecord(request);
        return response;
    }
	
	@RequestMapping(value = "/getConsumptionList", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询消费记录列表【游客端】", notes = "查询消费记录信息，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public TallyBookResponse getConsumptionList( @ApiParam(value = "腾讯群组ID", required = true) @RequestParam(required = true) String txGroupId,
            @ApiParam(value = "消费类型,非必填项,0为餐饮1为交通2为购物3为娱乐4为门票5为住宿6为医疗7为美容8为保险9为其他", required = false) @RequestParam(required = false) Integer typeId) {

		if(StringUtils.isEmpty(txGroupId)){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：行程id不能为空！");
        }
        TallyBookResponse response = null;

		// 1、获取当前登录的用户信息
        UserResponse user = userLoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            response = TallyBookResponse.createUserNotLoginResponse();
            return response;
        }

        response = iTallyBookService.getConsumptionList(txGroupId,typeId,user.getUserEntity().getId());
        return response;
    }
	
	@RequestMapping(value = "/statisticsConsumption", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "消费统计【游客端】", notes = "统计消费信息，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public StatisticsConsumptionResponse statisticsConsumption( @ApiParam(value = "腾讯群组ID", required = true) @RequestParam(required = true) String txGroupId) {

		if(StringUtils.isEmpty(txGroupId)){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：行程id不能为空！");
        }
        StatisticsConsumptionResponse response = null;

        // 1、获取当前登录的用户信息
        UserResponse user = userLoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            response = StatisticsConsumptionResponse.createUserNotLoginResponse();
            return response;
        }

        response = iTallyBookService.statisticsConsumptionList(txGroupId,user.getUserEntity().getId());
        return response;
    }
}
