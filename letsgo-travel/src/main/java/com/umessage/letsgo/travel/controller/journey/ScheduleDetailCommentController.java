package com.umessage.letsgo.travel.controller.journey;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleDetailCommentRequest;
import com.umessage.letsgo.domain.vo.journey.response.DetailCommentResponse;
import com.umessage.letsgo.service.api.journey.IScheduleDetailCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "点评接口", description = "关于行程的相关操作")
@Controller
@RequestMapping(value = "/scheduleDetailComment")
public class ScheduleDetailCommentController {

	@Resource
    private IScheduleDetailCommentService scheduleDetailCommentService;
	
	 @RequestMapping(value = "/getJournarComment", method = RequestMethod.POST)
	    public String getJournarComment(@RequestBody  ScheduleDetailCommentRequest scheduleDetailCommentRequest) {

//		 	if (StringUtils.isEmpty(scheduleDetailCommentRequest.getHotelId())) {
//	            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：酒店id【hotelId】不能为空！");
//	        }
		 	/*if (StringUtils.isEmpty(scheduleDetailCommentRequest.getTravelId())) {
	            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：旅行社id【travelId】不能为空！");
	        }
		 	if (StringUtils.isEmpty(scheduleDetailCommentRequest.getScheduleId())) {
	            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：行程id【scenicId】不能为空！");
	        }*/
		 	if (StringUtils.isEmpty(scheduleDetailCommentRequest.getScheduleDetailId())) {
	            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：行程详细id【scheduleDetailId】不能为空！");
	        }
		 	if (StringUtils.isEmpty(scheduleDetailCommentRequest.getUserId())) {
	            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：用户id【userId】不能为空！");
	        }
		 	if (StringUtils.isEmpty(scheduleDetailCommentRequest.getSatisfiedStatus())) {
	            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：满意度标识【satisfiedStatus】不能为空！");
	        }
			try {
				scheduleDetailCommentService.submitJournarComment(scheduleDetailCommentRequest);
			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "获取点评失败");
			}
		 	
		 	return "redirect:scheduleDetailComment/selectJournarComment?scheduleDetailId="+scheduleDetailCommentRequest.getScheduleDetailId() ;
	    }
	@RequestMapping(value = "/selectJournarComment", method = RequestMethod.POST)
	public String selectJournarComment(@RequestParam Long scheduleDetailId) {

		if (StringUtils.isEmpty(scheduleDetailId)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：行程明细id【scheduleDetailId】不能为空！");
		}
//		DetailCommentResponse scheduleDetailCommentRequest=scheduleDetailCommentService.selectComment(scheduleDetailId);
		return "comment/comment";
	}
}
