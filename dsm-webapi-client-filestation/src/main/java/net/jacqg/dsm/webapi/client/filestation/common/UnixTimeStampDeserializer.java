package net.jacqg.dsm.webapi.client.filestation.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class UnixTimeStampDeserializer extends StdScalarDeserializer<LocalDateTime> {

    protected UnixTimeStampDeserializer() {
        super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        JsonToken currentToken = parser.getCurrentToken();
        if(currentToken == JsonToken.VALUE_NUMBER_INT) {
            int valueAsInt = parser.getValueAsInt();
            // TODO handle time zone
            ZoneOffset offset = LocalDateTime.now().atZone(ZoneId.of("Europe/Brussels")).getOffset();
            return LocalDateTime.ofEpochSecond(valueAsInt, 0, offset);
        }
        return null;
    }
}
