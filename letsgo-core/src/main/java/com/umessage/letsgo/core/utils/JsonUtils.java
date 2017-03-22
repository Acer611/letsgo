package com.umessage.letsgo.core.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
  
/** 
 * jsonson utils 
 * @see http://jackson.codehaus.org/ 
 * @see https://github.com/FasterXML/jackson 
 * @see http://wiki.fasterxml.com/JacksonHome 
 * @author magic_yy 
 * 
 */  
public class JsonUtils {  
    static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    private static XmlMapper xmlMapper = null; 
    private static ObjectMapper objectMapper =null;
    static{
        xmlMapper = new XmlMapper(); 
        xmlMapper.setDateFormat(fmt);  
        objectMapper=new ObjectMapper();
        objectMapper.setDateFormat(fmt);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.addHandler(new DeserializationProblemHandler(){
            @Override
            public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser jp,
                    JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName) throws IOException,
                    JsonProcessingException {
                return true;
            }
        });
    }
    public JsonUtils(SimpleDateFormat fmt){
        xmlMapper = new XmlMapper(); 
        xmlMapper.setDateFormat(fmt);  
        objectMapper=new ObjectMapper();
        objectMapper.setDateFormat(fmt);
        objectMapper.addHandler(new DeserializationProblemHandler(){
            @Override
            public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser jp,
                    JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName) throws IOException,
                    JsonProcessingException {
                return true;
            }
        });
    }
    /** 
     * javaBean,list,array convert to json string 
     */  
    public static String obj2json(Object obj) {  
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return "{}";
        }
    }  
      
    /** 
     * json string convert to javaBean 
     */  
    public static <T> T json2pojo(String jsonStr,Class<T> clazz){
        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                return clazz.newInstance();
            } catch (Exception e1) {
                return null;
            }
        }  
    }  
      
    /** 
     * json string convert to map 
     */  
    public static <T> Map<String,T> json2map(String jsonStr){  
        try {
            return objectMapper.readValue(jsonStr, Map.class);
        }catch (Exception e) {
            return  new HashMap<String,T>();
        }  
    }  
      
    /** 
     * json string convert to map with javaBean 
     */  
    public static <T> Map<String,T> json2map(String jsonStr,Class<T> clazz){  
        Map<String, Map<String, Object>> map;
        try {
            map = objectMapper.readValue(jsonStr, new TypeReference<Map<String,T>>() {});
            Map<String,T> result = new HashMap<String, T>();  
            for (Entry<String, Map<String,Object>> entry : map.entrySet()) {  
                result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));  
            }  
            return result;  
        } catch (Exception e) {
            return new HashMap<String ,T>();
        }  
    }  
      
    /** 
     * json array string convert to list with javaBean 
     */  
    public static <T> List<T> json2list(String jsonArrayStr,Class<T> clazz){  
        List<Map<String, Object>> list;
        try {
            list = objectMapper.readValue(jsonArrayStr, new TypeReference<List<T>>() {  
            });
            List<T> result = new ArrayList<T>();  
            for (Map<String, Object> map : list) {  
                result.add(map2pojo(map, clazz));  
            }  
            return result;  
        } catch (Exception e) {
            return new ArrayList<T>();
        }  
    }  
      
    /** 
     * map convert to javaBean 
     */  
    public static <T> T map2pojo(Map map,Class<T> clazz){  
        try {
            return objectMapper.convertValue(map, clazz);  
        } catch (Exception e) {
            try {
                return clazz.newInstance();
            } catch (Exception e1) {
                return null;
            }
        }
    }  

    /**
     * pojo convert to map
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> pojo2map(Object o) {
        try {
            return objectMapper.convertValue(o, Map.class);
        } catch (Exception e) {
            return new HashMap<String, T>();
        }
    }
    /** 
     * json string convert to xml string 
     */  
    public static String json2xml(String jsonStr)throws Exception{  
        JsonNode root = objectMapper.readTree(jsonStr);  
        String xml = xmlMapper.writeValueAsString(root);  
        return xml;  
    }  
      
    /** 
     * xml string convert to json string 
     */  
    public static String xml2json(String xml)throws Exception{  
        StringWriter w = new StringWriter();  
        JsonParser jp = xmlMapper.getFactory().createParser(xml);  
        JsonGenerator jg = objectMapper.getFactory().createGenerator(w);  
        while (jp.nextToken() != null) {  
            jg.copyCurrentEvent(jp);  
        }  
        jp.close();  
        jg.close();  
        return w.toString();  
    } 

    /** 
     * json string convert to xml string 
     */  
    public static String pojo2xml(Object obj)throws Exception{
        String xml =xmlMapper.writeValueAsString(obj);
        return xml;  
    }  
      
    /** 
     * xml string convert to json string 
     */  
    public static<T> T xml2pojo(String xml,Class<T> clazz)throws Exception{
        if(StringUtils.isBlank(xml)) return null;
        try {
            String jsonStr=xml2json(xml);
            return json2pojo(jsonStr, clazz);
        } catch (Exception e) {
            return null;
        }
    }
}  