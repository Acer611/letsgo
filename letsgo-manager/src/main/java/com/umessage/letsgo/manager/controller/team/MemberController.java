package com.umessage.letsgo.manager.controller.team;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.utils.QueryUtils;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.requset.WebMemberRequest;
import com.umessage.letsgo.domain.vo.team.respone.MemberResponse;
import com.umessage.letsgo.domain.vo.team.respone.WebMemberResponse;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.team.ILeaderService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import com.umessage.letsgo.service.api.team.IWebMemberService;
import com.umessage.letsgo.service.common.constant.Constant;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.Api;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoYidong on 2016/6/12.
 */
@Api(value = "旅行社端成员管理接口", description = "关于旅行社端成员的相关操作")
@Controller
@RequestMapping(value = "/member")
public class MemberController {

    @Resource
    private IMemberService memberService;
    @Resource
    private IWebMemberService webMemberService;
    @Resource
    private IScheduleService scheduleService;
    @Resource
    private ITravelAgencyService travelService;
    @Resource
    private ILeaderService leaderService;

    @RequestMapping("search")
    public String search(WebMemberRequest request, Model model) {
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        if(travel == null || travel.getId() == null) return "redirect:/login";
        request.setTravelId(travel.getId());

        WebMemberResponse response = webMemberService.getMemberList(request);
        model.addAttribute("response",response);
        return "team/memberList";
    }
    @RequestMapping(value = "searchAjax")
    @ResponseBody
    public WebMemberResponse search(WebMemberRequest request) {
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        if(travel == null || travel.getId() == null){
            return WebMemberResponse.createNotFoundResponse("没有发现旅行社");
        }
        request.setTravelId(travel.getId());
        //查询列表，分页
        return webMemberService.getMemberList(request);
    }

    @RequestMapping("memberPreview")
    public String memberPreview(Long id,Model model){
        MemberEntity memberEntity = webMemberService.memberPreview(id);
        List<ScheduleEntity> traveledList = scheduleService.getSchedulesByMember(id,Constant.TRAVELED);
        List<ScheduleEntity> prepareTravelList = scheduleService.getSchedulesByMember(id,Constant.PREPARE_TRAVEL);
        model.addAttribute("member",memberEntity);
        model.addAttribute("traveledList",traveledList);
        model.addAttribute("prepareTravelList",prepareTravelList);
        return "team/memberPreview";
    }

    @RequestMapping("memberEdit")
    public String memberEdit(Long id,Model model){
        MemberEntity memberEntity = webMemberService.memberPreview(id);
        model.addAttribute("member",memberEntity);
        return "team/memberEdit";
    }

    @RequestMapping("memberEditAjax")
    @ResponseBody
    public WebMemberResponse memberEdit(Long id){
        MemberEntity memberEntity = webMemberService.memberPreview(id);
        WebMemberResponse response = new WebMemberResponse();
        response.setMember(memberEntity);
        return response;
    }

    @RequestMapping("memberUpdate")
    public String memberUpdate(MemberEntity member,Model model){
        WebMemberResponse verifyResponse = member.dataVerify();
        if(verifyResponse.getRetCode() != 0){
            model.addAttribute("member",member);
            model.addAttribute("retMsg",verifyResponse.getRetMsg());
            return "team/memberEdit";
        }
        webMemberService.memberUpdate(member);
        return "redirect:search";
    }

    @RequestMapping(value = "memberUpdateAjax", method = RequestMethod.POST)
    @ResponseBody
    public WebMemberResponse memberUpdate(MemberEntity member){
        WebMemberResponse verifyResponse = member.dataVerify();
        if(verifyResponse.getRetCode() != 0){
            return verifyResponse;
        }
        webMemberService.memberUpdate(member);
        WebMemberResponse response = new WebMemberResponse();
        response.setMember(member);
        return response;
    }

    @RequestMapping("memberDeleteAjax")
    @ResponseBody
    public CommonResponse memberDelete(Long id){
        return webMemberService.memberDelete(id);
    }

    @RequestMapping("memberAdd")
    @ResponseBody
    public WebMemberResponse memberAdd(MemberEntity member){
        return webMemberService.memberAddAjax(member);
    }

    @RequestMapping("memberCreate")
    public String memberInit(){
        return "team/memberCreate";
    }

    @RequestMapping("memberSave")
    public String memberSave(MemberEntity member,Model model){
        WebMemberResponse verifyResponse = member.dataVerify();
        if(verifyResponse.getRetCode() != 0){
            model.addAttribute("member",member);
            model.addAttribute("retMsg",verifyResponse.getRetMsg());
            return "team/memberCreate";
        }
        webMemberService.memberAdd(member);
        return "redirect:search";
    }

    @RequestMapping("memberImportInit")
    @ResponseBody
    public WebMemberResponse memberImportInit(Long tId){
        List<MemberEntity> touristList = memberService.getTouristList(tId);
        WebMemberResponse response = new WebMemberResponse();
        response.setMemberList(touristList);
        return response;
    }

    @RequestMapping(value={"/memberImportExcel"},method= RequestMethod.POST)
    @ResponseBody
    public WebMemberResponse memberImportExcel(MultipartFile file,WebMemberRequest memberRequest, HttpServletRequest request){
        String filePath = saveFileUpload(file,request);
        return webMemberService.importMemberExcel(filePath,memberRequest.gettId());//加入修改

    }


    private String saveFileUpload(MultipartFile file, HttpServletRequest request){
        if(file.isEmpty()) return null;
        String dir = request.getServletContext().getRealPath(File.separator)+"resource"+File.separator+"upload";//设定文件保存的目录
        File saveFile = new File(dir,file.getOriginalFilename());
        try {
            FileUtils.writeByteArrayToFile(saveFile, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveFile.getPath();
    }

    @RequestMapping(value={"/memberExportExcel"},method= RequestMethod.GET)
    public void memberExportExcel(WebMemberRequest request, HttpServletResponse response) {
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        if(travel == null || travel.getId() == null){
            try {
                response.sendRedirect("/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        request.setTravelId(travel.getId());
        request.setPageSizes(0);
        WebMemberResponse memberResponse= webMemberService.getMemberList(request);
        byte[] bytes =webMemberService.getMemberExcel(memberResponse.getMemberList());
        try {
            responseHeaderSet(response,String.valueOf(bytes.length),"游客数据.xls");
            write(new ByteArrayInputStream(bytes),response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value={"/templateDownloads"},method= RequestMethod.GET)
    public void templateDownLoads(HttpServletRequest request,HttpServletResponse response) {
        try {
            String dir = request.getServletContext().getRealPath(File.separator)+"resources"+File.separator+"download"+File.separator+"游客导入模板.xls";
            File file = new File(dir);
            responseHeaderSet(response,String.valueOf(file.length()),"游客导入模板.xls");
            write(new FileInputStream(file),response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(InputStream is,OutputStream out){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int readBytes;
            while (-1 != (readBytes = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, readBytes);
            }
            bos.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(bis!=null) bis.close();
                if(bos!=null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void responseHeaderSet(HttpServletResponse response,String size,String fileName) throws UnsupportedEncodingException {
        response.reset();
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "form-data; name=\"attachment\"; filename=\"" + new String(fileName.getBytes("GBK"),"ISO-8859-1")+"\"");
        response.addHeader("Content-Length",size);
    }

    @RequestMapping(value={"/templateDownload"},method= RequestMethod.GET)
    public ResponseEntity<byte[]> templateDownLoad(HttpServletRequest request,HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        try {
            headers.setContentDispositionFormData("attachment", new String("游客导入模板.xls".getBytes("GBK"),"ISO-8859-1"));
            String dir = request.getServletContext().getRealPath(File.separator)+"resources"+File.separator+"download"+File.separator+"游客导入模板.xls";
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(dir)), headers, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("memberPhoneRepeatCheck")
    @ResponseBody
    public Map<String,Boolean> memberPhoneIsRepeated(String phone,Long tId){
        boolean isRepeated= memberService.memberPhoneIsRepeat(QueryUtils.getPhone(phone),tId);
        //游客和领队的手机号是否重复
//        boolean isRepeat = memberService.leaderPhoneIsRepeat(QueryUtils.getPhone(phone), tId);
//        Map<String,Boolean> map = new HashMap<String,Boolean>();
//        map.put("valid",isRepeated || isRepeat);
        Map<String,Boolean> map = new HashMap<String,Boolean>();
        map.put("valid",isRepeated);
        return map;
    }

    @RequestMapping("teamMembersView")
    public String teamMembersView(Long tId, Model model) {
        if (tId == null) return "team/teamMembersView";

        List<MemberEntity> memberEntityList = memberService.getTouristList(tId);
        model.addAttribute("memberList", memberEntityList);
        return "team/teamMembersView";
    }

}
