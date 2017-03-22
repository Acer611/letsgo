package com.umessage.letsgo.core.exception;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class BusinessExceptionJacksonSerializer extends StdSerializer<BusinessException> {

    public BusinessExceptionJacksonSerializer() {
        super(BusinessException.class);
    }

	@Override
	public void serialize(BusinessException value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
        jgen.writeStartObject();
		jgen.writeNumberField("retCode", value.getCode());
		jgen.writeStringField("retMsg", value.getMessage());
        jgen.writeEndObject();
	}
}
