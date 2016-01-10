package com.github.gauthierj.dsm.webapi.client.timezone;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class TimeZoneUtil implements ApplicationContextAware {

    private static TimeZoneUtil instance;

    @Autowired
    private TimeZoneProvider timeZoneProvider;

    public ZoneOffset getZoneOffset() {
        return LocalDateTime.now().atZone(timeZoneProvider.getTimeZone().toZoneId()).getOffset();
    }

    public static ZoneOffset getOffset() {
        return instance.getZoneOffset();
    }

    @Override
    public synchronized void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        instance = applicationContext.getBean(TimeZoneUtil.class);
    }
}
