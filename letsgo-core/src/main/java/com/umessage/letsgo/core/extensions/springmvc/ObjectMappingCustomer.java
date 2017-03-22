package com.umessage.letsgo.core.extensions.springmvc;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.joda.JodaModule;

@Deprecated
public class ObjectMappingCustomer extends ObjectMapper {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3843882765855050480L;

	public ObjectMappingCustomer()  
    {  
        super();  
        // 允许单引号  
        //this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);  
        // 字段和值都加引号  
        //this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);  
        // 数字也加引号  
        //this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);  
        //this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
       // this.setSerializationInclusion(Include.NON_NULL);
        
        //this.registerModule(new JodaModule());
        // 空值处理为空串
        this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>(){

            @Override
            public void serialize(Object value, JsonGenerator jg,
                    SerializerProvider sp) throws IOException,
                    JsonProcessingException {
                 jg.writeString("");  
            }
             
        });
    }
}
