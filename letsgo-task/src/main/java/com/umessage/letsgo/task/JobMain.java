package com.umessage.letsgo.task;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhajl on 2016/6/23.
 */
public class JobMain {

    // CHECKSTYLE:OFF
    @SuppressWarnings("resource")
    public static void main(final String[] args) {
        // CHECKSTYLE:ON
        new ClassPathXmlApplicationContext("classpath:META-INF/application-context.xml");
    }
}
