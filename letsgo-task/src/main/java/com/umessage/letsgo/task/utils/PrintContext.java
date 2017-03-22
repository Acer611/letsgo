package com.umessage.letsgo.task.utils;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zhajl on 2016/5/29.
 */
public final class PrintContext {

    private static final String PROCESS_JOB_MESSAGE = "------ %s process: %s at %s ------";

    private static final String FETCH_DATA_MESSAGE = "------ %s load sharding items: %s at %s ------";

    private static final String PROCESS_DATA_MESSAGE = "------ %s process data: %s at %s ------";

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private Logger logger = Logger.getLogger(PrintContext.class);

    private final Class<?> clazz;

    public PrintContext(final Class<?> clazz) {
        this.clazz = clazz;
    }

    public void printProcessJobMessage(final List<Integer> shardingItems) {
        logger.info(String.format(PROCESS_JOB_MESSAGE, clazz.getSimpleName(), shardingItems, new SimpleDateFormat(DATE_FORMAT).format(new Date())));
    }

    public void printFetchDataMessage(final int shardingItem) {
        logger.info(String.format(FETCH_DATA_MESSAGE, clazz.getSimpleName(), shardingItem, new SimpleDateFormat(DATE_FORMAT).format(new Date())));
    }

    public void printFetchDataMessage(final List<Integer> shardingItems) {
        logger.info(String.format(FETCH_DATA_MESSAGE, clazz.getSimpleName(), shardingItems, new SimpleDateFormat(DATE_FORMAT).format(new Date())));
    }

    public void printProcessDataMessage(final Object data) {
        logger.info(String.format(PROCESS_DATA_MESSAGE, clazz.getSimpleName(), data, new SimpleDateFormat(DATE_FORMAT).format(new Date())));
    }
}
