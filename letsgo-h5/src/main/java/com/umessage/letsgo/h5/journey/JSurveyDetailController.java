package com.umessage.letsgo.h5.journey;

import com.umessage.letsgo.domain.po.journey.*;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleShowRequest;
import com.umessage.letsgo.domain.vo.journey.request.SurveyDetailRequest;
import com.umessage.letsgo.domain.vo.journey.response.SurveyQuestionResponse;
import com.umessage.letsgo.service.api.journey.*;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/h5/survey")
public class JSurveyDetailController {

    @Resource
    private ISurveyService surveyService;

    @Resource
    private ISurveyDetailService surveyDetailService;

    @Resource
    private IScheduleDetailScenicService scheduleDetailScenicService;

    @Resource
    private IHotelScheduleService hotelScheduleService;
    @Resource
    private IAlbumScheduleService albumScheduleService;
    @Resource
    private IScheduleDescService scheduleDescService;
    @Resource
    private IShoppingScheduleService shoppingScheduleService;
    @Resource
    private IOwnExpenseScheduleService ownExpenseScheduleService;

    private Logger logger = LoggerFactory.getLogger(JSurveyDetailController.class);

    @RequestMapping(value = "/getSurveyWithSign", method = RequestMethod.GET)
    @ApiOperation(value = "获取游客填写的问卷及签名【领队端】", notes = "获取游客填写的问卷及签名，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    public String getSurveyWithSign(@RequestParam  Long surveyUserId, Model model) {
        SurveyQuestionResponse response = new SurveyQuestionResponse();

        response = surveyService.getSurveyWithSign(surveyUserId);
        model.addAttribute("response", response);
        return "journey/sign";
    }

    @RequestMapping(value = "/getSurveyDetailPdfUrl", method = RequestMethod.GET)
    @ApiOperation(value = "app页面下载调查问卷的pdf", notes = "")
    public String getSurveyDetailPdfUrl(@RequestParam  Long userId, String txGroupid, Model model) {
        logger.info("getSurveyDetailPdfUrl"+txGroupid);
        SurveyDetailRequest request = new SurveyDetailRequest();
        request.setConfirmStatus(1);//确认
//      request.setUserId(userId);
        request.setTxgroupId(txGroupid);
        //team的name ; user的realname ; pdfurl ;
        List<SurveyDetailEntity> surveyDetailList= surveyDetailService.getConfirmSurveyOne(request);

        model.addAttribute("response", surveyDetailList);
        //页面地址？
        return "download";
    }


    /**
     * 根据景点ID 获取景点和照片的信息
     * @return
     */
    @RequestMapping(value ="/getScenicByScenId",method = RequestMethod.GET)
    public String getScenic(ScheduleShowRequest showRequest, Long scenId, Model model){
        ScheduleDetailScenicEntity scenicEntity = scheduleDetailScenicService.getScenicByScenicId(scenId);
        model.addAttribute("scenicEntity",scenicEntity);
        return "journey/scenicdetail";
    }


    /**
     * 根据景点ID 获取景点和照片的信息
     * @return
     */
    @RequestMapping(value ="/getScenicPictureByScenId",method = RequestMethod.GET)
    public String getScenicPictureByScenId(ScheduleShowRequest showRequest, Long scenId, Model model){
        ScheduleDetailScenicEntity scenicEntity = scheduleDetailScenicService.getScenicByScenicId(scenId);
        model.addAttribute("scenicEntity",scenicEntity);
        return "journey/scenicphotolist";
    }


    /**
     * 根据酒店id 查询酒店及照片信息
     * @param showRequest
     * @param model
     * @return
     */
    @RequestMapping(value ="/getHotelByHotelId")
    public String getHotelByHotelId(ScheduleShowRequest showRequest, Long hotelId, Model model){
        if (hotelId != null){
            HotelScheduleEntity hotelScheduleEntity = hotelScheduleService.select(hotelId);
            if (hotelScheduleEntity != null){
                List<AlbumScheduleEntity> albumSchedules = albumScheduleService.getAlbumSchedule(hotelScheduleEntity.getScheduleDetailId(), hotelScheduleEntity.getId(), 2);
                if (albumSchedules != null){
                    List<String> photoUrls = new ArrayList<>();
                    for (AlbumScheduleEntity albumScheduleEntity:albumSchedules) {
                        photoUrls.add(albumScheduleEntity.getPhotoUrl());
                    }
                    hotelScheduleEntity.setPhotoUrls(photoUrls);
                    if(albumSchedules==null){
                        albumSchedules = new ArrayList<>();
                    }
                    hotelScheduleEntity.setAlbumScheduleList(albumSchedules);
                }
            }
            model.addAttribute("hotelScheduleEntity",hotelScheduleEntity);
        }
        return "journey/hoteldetail";
    }


    /**
     * 根据酒店id 查询照片信息
     * @param showRequest
     * @param model
     * @return
     */
    @RequestMapping(value ="/getHotelAlbumByHotelId")
    public String getHotelAlbumByHotelId(ScheduleShowRequest showRequest, Long hotelId, Model model){
        if (hotelId != null){
            HotelScheduleEntity hotelScheduleEntity = hotelScheduleService.select(hotelId);
            if (hotelScheduleEntity != null){
                List<AlbumScheduleEntity> albumSchedules = albumScheduleService.getAlbumSchedule(hotelScheduleEntity.getScheduleDetailId(), hotelScheduleEntity.getId(), 2);
                if (albumSchedules != null){
                    List<String> photoUrls = new ArrayList<>();
                    for (AlbumScheduleEntity albumScheduleEntity:albumSchedules) {
                        photoUrls.add(albumScheduleEntity.getPhotoUrl());
                    }
                    hotelScheduleEntity.setPhotoUrls(photoUrls);
                    if(albumSchedules==null){
                        albumSchedules = new ArrayList<>();
                    }
                    hotelScheduleEntity.setAlbumScheduleList(albumSchedules);
                }
            }
            model.addAttribute("hotelScheduleEntity",hotelScheduleEntity);
        }
        return "journey/photolist";
    }

    @RequestMapping(value ="/getDesc",method = RequestMethod.GET)
    public String getDesc(ScheduleShowRequest showRequest, Long id, Model model){
        List<ScheduleDescEntity> scheduleDescEntityList = scheduleDescService.getScheduleDescByScheduleId(id);
        model.addAttribute("scenicDesc",scheduleDescEntityList);
        return "";
    }


    /**
     * 根据购物场所根据id 查询
     * @param model
     * @return
     */
    @RequestMapping(value ="/getShoppingByShoppingId")
    public String getShoppingByShoppingId(Long shoppingId, Model model){
        ShoppingScheduleEntity shoppingSchedule = null;
        if (shoppingId != null){
            shoppingSchedule = shoppingScheduleService.select(shoppingId);
        }
        model.addAttribute("shoppingSchedule",shoppingSchedule);
        return "journey/shoppingposition";
    }

    /**
     * 根据自费项目id 查询自费项目
     * @param model
     * @return
     */
    @RequestMapping(value ="/getOwnExpenseByOwnExpenseId")
    public String getOwnExpenseByOwnExpenseId(Long ownExpenseId, Model model){
        if (ownExpenseId != null){
            OwnExpenseScheduleEntity ownExpenseSchedule = ownExpenseScheduleService.select(ownExpenseId);
            if (ownExpenseSchedule != null){
                Long scheduleId = ownExpenseSchedule.getScheduleId();
                Long scheduleDetailId = ownExpenseSchedule.getScheduleDetailId();
                List<AlbumScheduleEntity> albumScheduleList = null;
                if (scheduleId != null){
                    albumScheduleList = albumScheduleService.getAlbumScheduleByScheduleId(scheduleId, ownExpenseSchedule.getId(), 3);
                }else if (scheduleDetailId != null){
                    albumScheduleList = albumScheduleService.getAlbumSchedule(scheduleDetailId, ownExpenseSchedule.getId(), 3);
                }
                if (albumScheduleList != null){
                    List<String> images = new ArrayList<>();
                    for (AlbumScheduleEntity albumSchedule:albumScheduleList) {
                        images.add("\""+albumSchedule.getPhotoUrl()+"\"");
                    }
                    ownExpenseSchedule.setPhotoUrls(images);
                    ownExpenseSchedule.setAlbumScheduleList(albumScheduleList);
                }
            }
            model.addAttribute("ownExpenseSchedule",ownExpenseSchedule);
        }
        return "journey/owndetail";
    }


    /**
     * 根据自费项目id 查询图片
     * @param model
     * @return
     */
    @RequestMapping(value ="/getOwnExpenseAlbumByOwnExpenseId")
    public String getOwnExpenseAlbumByOwnExpenseId(Long ownExpenseId, Model model){
        if (ownExpenseId != null){
            OwnExpenseScheduleEntity ownExpenseSchedule = ownExpenseScheduleService.select(ownExpenseId);
            if (ownExpenseSchedule != null){
                Long scheduleId = ownExpenseSchedule.getScheduleId();
                Long scheduleDetailId = ownExpenseSchedule.getScheduleDetailId();
                List<AlbumScheduleEntity> albumScheduleList = null;
                if (scheduleId != null){
                    albumScheduleList = albumScheduleService.getAlbumScheduleByScheduleId(scheduleId, ownExpenseSchedule.getId(), 3);
                }else if (scheduleDetailId != null){
                    albumScheduleList = albumScheduleService.getAlbumSchedule(scheduleDetailId, ownExpenseSchedule.getId(), 3);
                }
                if (albumScheduleList != null){
                    List<String> images = new ArrayList<>();
                    for (AlbumScheduleEntity albumSchedule:albumScheduleList) {
                        images.add("\""+albumSchedule.getPhotoUrl()+"\"");
                    }
                    ownExpenseSchedule.setPhotoUrls(images);
                    ownExpenseSchedule.setAlbumScheduleList(albumScheduleList);
                }
            }
            model.addAttribute("ownExpenseSchedule",ownExpenseSchedule);
        }
        return "journey/ownphotolist";
    }

}
