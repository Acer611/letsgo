package com.umessage.letsgo.core.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@SuppressWarnings("serial")
public class BusinessExceptionJacksonDeserializer  extends StdDeserializer<BusinessException> {
	public BusinessExceptionJacksonDeserializer() {
		super(BusinessException.class);
	}

	@Override
	public BusinessException deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
			JsonProcessingException {

		JsonToken t = jp.getCurrentToken();
		if (t == JsonToken.START_OBJECT) {
			t = jp.nextToken();
		}
		Map<String, Object> errorParams = new HashMap<String, Object>();
		for (; t == JsonToken.FIELD_NAME; t = jp.nextToken()) {
			// Must point to field name
			String fieldName = jp.getCurrentName();
			// And then the value...
			t = jp.nextToken();
			// Note: must handle null explicitly here; value deserializers won't
			Object value;
			if (t == JsonToken.VALUE_NULL) {
				value = null;
			}
			// Some servers might send back complex content
			else if (t == JsonToken.START_ARRAY) {
				value = jp.readValueAs(List.class);
			}
			else if (t == JsonToken.START_OBJECT) {
				value = jp.readValueAs(Map.class);
			}
			else {
				value = jp.getText();
			}
			errorParams.put(fieldName, value);
		}

		Object errorCode = errorParams.get("retCode");
		String errorMessage = errorParams.containsKey("retMsg") ? errorParams.get("retMsg")
				.toString() : null;
		if (errorMessage == null) {
			errorMessage = errorCode == null ? "OAuth Error" : errorCode.toString();
		}

		BusinessException ex = new BusinessException(Integer.valueOf(errorCode.toString()), errorMessage);
		return ex;

	}

}
