package com.github.gauthierj.dsm.webapi.client.filestation.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.github.gauthierj.dsm.webapi.client.timezone.TimeZoneUtil;

import java.io.IOException;
import java.time.LocalDateTime;

public class UnixTimeStampDeserializer extends StdScalarDeserializer<LocalDateTime> {

    protected UnixTimeStampDeserializer() {
        super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        JsonToken currentToken = parser.getCurrentToken();
        if(currentToken == JsonToken.VALUE_NUMBER_INT) {
            int valueAsInt = parser.getValueAsInt();
            return LocalDateTime.ofEpochSecond(valueAsInt, 0, TimeZoneUtil.getOffset());
        }
        return null;
    }
}
