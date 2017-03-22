package com.umessage.letsgo.task.utils;

import com.google.common.collect.ObjectArrays;
import com.google.common.hash.Hashing;
import com.umessage.letsgo.domain.po.system.YahooRate;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import org.apache.log4j.Logger;
import org.apache.log4j.pattern.IntegerPatternConverter;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhajl on 2016/5/30.
 */
public class JobShardingFilter<T> {

    private List<Integer> shardingItems;

    Logger logger = Logger.getLogger(JobShardingFilter.class);

    public JobShardingFilter(List<Integer> shardingItems) {
        this.shardingItems = shardingItems;
    }

    public List<T> filterJob(List<T> dataList){
        logger.info("过滤前List：" + dataList.toString());
        List<T> result = new ArrayList<T>();
        for (T data: dataList) {
            if (isInSharding(data)){
                result.add(data);
            }
        }

        logger.info("过滤后List：" + result.toString());
        return result;
    }

    private boolean isInSharding(T data) {
        Object value = getObjectValue(data);
        int code = value.hashCode();

        logger.info("过滤数据项 ： code：" + code + " , data" + data.toString());
        int remainder = Math.abs(code % 10);
        for (int i: shardingItems) {
            if (remainder == i){
                return true;
            }
        }
        return false;
    }

    private Object getObjectValue(T data){
        Object value = data;
        Field f = ReflectionUtils.findField(data.getClass(), "id");
        // 如果没有找到id字段, 则使用对象本身作为value
        if (f == null){
            return  value;
        }

        /*
        if (!f.getType().getName().equalsIgnoreCase("int") &&
                !f.getType().getName().equalsIgnoreCase("java.lang.Integer") &&
                !f.getType().getName().equalsIgnoreCase("long") &&
                !f.getType().getName().equalsIgnoreCase("java.lang.Long")){
            return null;
        }
        */
        f.setAccessible(true);
        value = ReflectionUtils.getField(f, data);

        return value;
    }

    public static void main(final String[] args){
        List<Integer> items = new ArrayList<>();
        items.add(5);
        items.add(6);
        items.add(7);
        items.add(9);

        JobShardingFilter filter = new JobShardingFilter(items);

        List<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(8);
        data.add(0);
        data.add(5);
        List<Integer> data2 = filter.filterJob(data);
        System.out.println(data2);

        List<TeamEntity> datalist = new ArrayList<>();
        TeamEntity teamEntity1 = new TeamEntity();
        teamEntity1.setId(2L);
        datalist.add(teamEntity1);
        List<TeamEntity> datalist2 = filter.filterJob(datalist);
        System.out.println(data2);


        List<YahooRate> datalist3 = new ArrayList<>();
        YahooRate yahooRate = new YahooRate();
        yahooRate.setId("USD/CNY");
        datalist3.add(yahooRate);
        List<YahooRate> datalist4 = filter.filterJob(datalist3);
        System.out.println(data2);
    }

}
