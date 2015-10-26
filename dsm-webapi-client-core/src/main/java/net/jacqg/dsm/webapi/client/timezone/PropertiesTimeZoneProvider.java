package net.jacqg.dsm.webapi.client.timezone;

import org.springframework.beans.factory.annotation.Value;

import java.util.TimeZone;

public class PropertiesTimeZoneProvider implements TimeZoneProvider {

    @Value("${dsm.webapi.timeZone}")
    private String timeZoneId;

    @Override
    public TimeZone getTimeZone() {
        return TimeZone.getTimeZone(timeZoneId);
    }
}
