package com.umessage.letsgo.service.impl.team;

import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.dao.team.WaitingDao;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.team.WaitingEntity;
import com.umessage.letsgo.domain.vo.team.requset.WaitingRequest;
import com.umessage.letsgo.domain.vo.team.respone.WaitingResponse;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.IWaitingService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * Created by wendy on 2016/9/2.
 */
@Service
public class WaitingServiceImpl implements IWaitingService {

    @Resource
    private WaitingDao waitingDao;
    @Resource
    private IScheduleService scheduleService;
    @Resource
    private IMemberService memberService;

    @Override
    public int add(WaitingEntity waitingEntity) {
        waitingEntity.setCreateTime(new Date());
        waitingEntity.setVersion(0l);
        return waitingDao.insert(waitingEntity);
    }

    @Override
    public int update(WaitingEntity waitingEntity) {
        waitingEntity.setUpdateTime(new Date());
        return waitingDao.update(waitingEntity);
    }

    @Override
    public WaitingEntity getWaitingList(Long userId) {
        WaitingEntity waitingEntity = waitingDao.selectWithUserId(userId);
        return waitingEntity;
    }

    /**
     * 保存排期
     * @param dates
     * @param waitStatus
     * @param userId
     * @return
     */
    @Override
    public WaitingResponse saveWaiting(List<String> dates, int waitStatus, Long userId) {
        List<String> leisureList = new ArrayList<>();
        List<String> workList = new ArrayList<>();

        // 查询当前用户这六个月的行程
        List<ScheduleEntity> scheduleEntityList = scheduleService.getScheduleInSexMonths(userId, 6);
        // 获取当前用户的排期
        WaitingEntity waitingEntity = this.getWaitingList(userId);

        // 初始化Map
        Map<String, Integer> waitingMap = new LinkedHashMap<>();
        waitingMap = this.InitWaitingMap(scheduleEntityList, waitingEntity);

        // 设置排期
        waitingMap = this.settingMyWaiting(waitingMap, dates, waitStatus);

        // 分别把忙碌时间和空闲时间放到一个List
        for (String dateKey : waitingMap.keySet()) {
            if (waitingMap.get(dateKey) == 1) {
                workList.add(dateKey);
            } else if (waitingMap.get(dateKey) == 2) {
                leisureList.add(dateKey);
            } else {
                continue;
            }
        }

        // 保存用户排期
        if (waitingEntity == null) {
            waitingEntity = new WaitingEntity();
            waitingEntity.setUserId(userId);
            waitingEntity.setWorkDate( StringUtils.join(workList, ",") );
            waitingEntity.setLeisureDate( StringUtils.join(leisureList, ",") );

            // 新增
            this.add(waitingEntity);
        } else {
            waitingEntity.setWorkDate( StringUtils.join(workList, ",") );
            waitingEntity.setLeisureDate( StringUtils.join(leisureList, ",") );
            // 更新
            this.update(waitingEntity);
        }

        WaitingResponse response = new WaitingResponse();
        response.setBusyList(workList);
        response.setFreeList(leisureList);
        response.setTravelList(scheduleEntityList);
        return response;
    }

    /**
     * 初始化排期Map，将原来的排期赋值到一个Map对象中
     * @return
     */
    private Map<String, Integer> InitWaitingMap(List<ScheduleEntity> scheduleEntityList, WaitingEntity waitingEntity) {
        // 定义一个Map排期，并初始化
        Map<String, Integer> waitingMap = new LinkedHashMap<>();
        // 获取当前月的第一天
        Date firstDate = DateUtils.getFirstDateOfMonth(new Date());
        // 获取六个月之后月份的最后一天
        Date lastDate = DateUtils.getLastDateOfMonth(DateUtils.addMonths(new Date(), 6));
        List<String> allDateList = this.calculateDateList(firstDate, lastDate);

        for (String date : allDateList) {
            waitingMap.put(date, 2);
        }

        if (!CollectionUtils.isEmpty(scheduleEntityList)) {
            waitingMap = this.settingScheduleDateToMap(waitingMap, scheduleEntityList);
        }

        if (waitingEntity != null) {
            List<String> leisureList = string2list(waitingEntity.getLeisureDate());
            List<String> workList = string2list(waitingEntity.getWorkDate());

            waitingMap = settingBusyDateToMap(waitingMap, workList);
            waitingMap = settingFreeDateToMap(waitingMap, leisureList);
        }

        return waitingMap;
    }

    /**
     * 设置六月内的行程时间
     * @param waitingMap
     * @param scheduleEntityList
     * @return
     */
    private Map<String, Integer> settingScheduleDateToMap(Map<String, Integer> waitingMap, List<ScheduleEntity> scheduleEntityList) {
        List<String> scheduleDates = new ArrayList<>();

        for (ScheduleEntity scheduleEntity : scheduleEntityList) {
            List<String> dateList = this.calculateDateList(scheduleEntity.getStartDate(), scheduleEntity.getEndDate());
            scheduleDates.addAll(dateList);
        }

        for (String schedleDate : scheduleDates) {
            for (String dateKey : waitingMap.keySet()) {
                if (schedleDate.equals(dateKey)) {
                    waitingMap.put(dateKey, 3);
                    break;
                }
            }
        }

        return waitingMap;
    }

    /**
     * 设置原来的忙碌时间
     * @param waitingMap
     * @param workList
     * @return
     */
    private Map<String, Integer> settingBusyDateToMap(Map<String, Integer> waitingMap, List<String> workList) {
        for (String dateKey : waitingMap.keySet()) {
            for (String w : workList) {
                if (w.equals(dateKey)) {
                    waitingMap.put(dateKey, 1);
                }
            }
        }

        return waitingMap;
    }

    /**
     * 设置原来的空闲时间
     * @param waitingMap
     * @param leisureList
     * @return
     */
    private Map<String, Integer> settingFreeDateToMap(Map<String, Integer> waitingMap, List<String> leisureList) {
        for (String dateKey : waitingMap.keySet()) {
            for (String l : leisureList) {
                if (l.equals(dateKey)) {
                    waitingMap.put(dateKey, 2);
                }
            }
        }

        return waitingMap;
    }

    private Map<String, Integer> settingMyWaiting(Map<String, Integer> waitingMap, List<String> dates, Integer waitStatus) {
        String temp = null;
        for (int i=0; i<dates.size(); i++) {
            for (String dateKey : waitingMap.keySet()) {
                try {
                    Date date1 = DateUtils.parseDate(dateKey, "yyyy-MM-dd");
                    Date date2 = DateUtils.parseDate(dates.get(i), "yyyy-MM-dd");

                    if (date1.before(date2)) {
                        continue;
                    }

                    if (temp != null) {
                        Date date3 = DateUtils.parseDate(temp, "yyyy-MM-dd");
                        if (date1.before(date3) || date1.equals(date3)) {
                            continue;
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (waitingMap.get(dateKey) == 3) {
                    continue;
                } else {
                    waitingMap.put(dateKey, waitStatus);
                    temp = dateKey;
                    break;
                }
            }
        }

        return waitingMap;
    }

    /**
     * 获取用户的排期
     * @param userId
     * @return
     */
    @Override
    public WaitingResponse getWaiting(Long userId) {
        WaitingEntity waitingEntity = this.getWaitingList(userId);

        List<String> leisureList = new ArrayList<>();
        List<String> workList = new ArrayList<>();
        if (waitingEntity != null) {
            String leisure = waitingEntity.getLeisureDate();    // 空闲时间
            String work = waitingEntity.getWorkDate();    // 忙碌时间
            leisureList = string2list(leisure);
            workList = string2list(work);
        }

        // 查询当前用户这六个月的行程
        List<ScheduleEntity> scheduleEntityList = scheduleService.getScheduleInSexMonths(userId, 6);
        /*List<String> scheduleDates = new ArrayList<>();

        for (ScheduleEntity scheduleEntity : scheduleEntityList) {
            List<String> dateList = this.calculateDateList(scheduleEntity.getStartDate(), scheduleEntity.getEndDate());
            scheduleDates.addAll(dateList);
        }*/

        WaitingResponse response = new WaitingResponse();
        response.setBusyList(workList);
        response.setFreeList(leisureList);
        response.setTravelList(scheduleEntityList);
        return response;
    }

    private List<String> calculateDateList(Date startDate, Date endDate) {
        List<String> dates = new ArrayList<>();
        int days = DateUtils.dayBetween(startDate, endDate);
        for (int i = 0; i <= days; i++) {
            Date tmpDate = DateUtils.addDay(startDate, i);
            dates.add(DateUtils.date2String("yyyy-MM-dd", tmpDate));
        }
        return dates;
    }

    private List<String> string2list(String str) {
        String[] array = str.split(",");
        List<String> list = new LinkedList<>();
        for (int i=0; i<array.length; i++) {
            list.add(array[i]);
        }

        return list;
    }
    //通过日期判断该用户在该日期是否空闲
    public boolean hasTime(Long userId,String time){
        WaitingRequest request=new WaitingRequest();
        request.setUserId(userId);
        request.setTime(time);
        int i =waitingDao.hasTime(request);
        if(i>0){
            return true;
        }
        return false;
    }

    //通过用户ID 获取领队的 排期 忙碌时间
    public List<String>  getWorkList(Long userId){
        List<String> workList = new ArrayList<String>();
        WaitingEntity waitingEntity=waitingDao.selectWithUserId(userId);
        if(waitingEntity==null || waitingEntity.getWorkDate()==null){
            return workList;
        }
        String [] strs = waitingEntity.getWorkDate().split(",");
        if(strs!=null && strs.length>0){
            for (int i = 0; i <strs.length ; i++) {
                workList.add(strs[i]);
            }
        }
        return workList;
    }

}
