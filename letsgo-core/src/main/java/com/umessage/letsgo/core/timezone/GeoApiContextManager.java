package com.umessage.letsgo.core.timezone;

import com.google.maps.GeoApiContext;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhajl on 16/11/14.
 */
public class GeoApiContextManager {

    private static Map<String, GeoApiContext> apiContextMap = new HashMap<>();

    private static GeoApiContextManager ourInstance = new GeoApiContextManager();

    public static GeoApiContextManager getInstance() {
        return ourInstance;
    }

    private GeoApiContextManager() {
    }

    public GeoApiContext getGeoApiContext() {
        ApiKeyManager apiKeyManager = ApiKeyManager.getInstance();
        ApiKeyItem item = apiKeyManager.getRandomApiKeyItem();
        return getGeoApiContext(item.getApikey());
        //return getGeoApiContext("AIzaSyBok03gYzNXxJPWYkfnlaZi7_XVdkc1MVo");
    }

    private GeoApiContext getGeoApiContext(String apiKey) {
        if (apiContextMap.containsKey(apiKey)){
            return apiContextMap.get(apiKey);
        }

        GeoApiContext context = new GeoApiContext().setApiKey(apiKey);
        Method method = ReflectionUtils.findMethod(context.getClass(), "setBaseUrlForTesting", String.class);
        method.setAccessible(true);
        ReflectionUtils.invokeMethod(method, context, "https://www.google.cn");
        apiContextMap.put(apiKey, context);
        return context;
    }

    public void invalidGeoApiContext(GeoApiContext context){
        String apiKey = getGeoApiKey(context);

        ApiKeyManager apiKeyManager = ApiKeyManager.getInstance();
        apiKeyManager.invalidApiKeyItem(apiKey);
    }

    public String getGeoApiKey(GeoApiContext context) {
        Field field = ReflectionUtils.findField(context.getClass(), "apiKey", String.class);
        field.setAccessible(true);
        return ReflectionUtils.getField(field, context).toString();
    }

    public static void main(String[] args) {
        GeoApiContextManager apiContextManager = GeoApiContextManager.getInstance();
        GeoApiContext context = apiContextManager.getGeoApiContext();
        apiContextManager.invalidGeoApiContext(context);
    }

}
