package com.umessage.letsgo.core.timezone;

import com.umessage.letsgo.core.utils.RandomUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhajl on 16/11/11.
 */
public class ApiKeyManager {

    private static ApiKeyManager ourInstance = new ApiKeyManager();

    public static ApiKeyManager getInstance() {
        return ourInstance;
    }

    private ApiKeyManager() {
    }

    /*
    apikey1 AIzaSyBok03gYzNXxJPWYkfnlaZi7_XVdkc1MVo
    apikey2 AIzaSyBJm0v_D7f-Oywc2ZIxRApmC8tAQI1q07k
    apikey3 AIzaSyDdFSmmTibO5uP-wZmfnmwE_bQJKo7KP7k
    apikey4 AIzaSyBNnJTpeQKjtltnfj8o72-hkfgQIhmP-XY
    apikey5 AIzaSyAqWeFN4Kr9mIe82FeLJ5BaNd3dLDZZSyc
    apikey6 AIzaSyCHtuHM6DrBmQEMYyOCOKImg2cxLV49T1Q
    apikey7 AIzaSyBJZLrETWX64KLTnYVa1iWABIjhTr_0aTc
    apikey8 AIzaSyA5dDQUhCBwZnJsYongi57NEHmpMm0Rw7w
    apikey9 AIzaSyClCJCEv5aXqoy6D_ZdbFdp4-ueZlZ48Ck
    */

    private static ApiKeyItem[] API_KEY_ITEMS = {
            new ApiKeyItem("AIzaSyBok03gYzNXxJPWYkfnlaZi7_XVdkc1MVo", 1, "Asia/Shanghai"),
            new ApiKeyItem("AIzaSyBJm0v_D7f-Oywc2ZIxRApmC8tAQI1q07k", 1, "Asia/Shanghai"),
            new ApiKeyItem("AIzaSyDdFSmmTibO5uP-wZmfnmwE_bQJKo7KP7k", 1, "Asia/Shanghai"),
            new ApiKeyItem("AIzaSyBNnJTpeQKjtltnfj8o72-hkfgQIhmP-XY", 1, "Asia/Shanghai"),
            new ApiKeyItem("AIzaSyAqWeFN4Kr9mIe82FeLJ5BaNd3dLDZZSyc", 1, "Asia/Shanghai"),
            new ApiKeyItem("AIzaSyCHtuHM6DrBmQEMYyOCOKImg2cxLV49T1Q", 1, "Asia/Shanghai"),
            new ApiKeyItem("AIzaSyBJZLrETWX64KLTnYVa1iWABIjhTr_0aTc", 1, "Asia/Shanghai"),
            new ApiKeyItem("AIzaSyA5dDQUhCBwZnJsYongi57NEHmpMm0Rw7w", 1, "Asia/Shanghai"),
            new ApiKeyItem("AIzaSyClCJCEv5aXqoy6D_ZdbFdp4-ueZlZ48Ck", 1, "Asia/Shanghai")
    };

    public List<ApiKeyItem> getValidApiKeyItems(){
        List<ApiKeyItem> apiKeyList = new ArrayList<>();
        for (ApiKeyItem item : API_KEY_ITEMS
             ) {
            String timeZone = item.getTimeZone();
            DateTime nowTime = new DateTime(DateTimeZone.forID(timeZone));
            DateTime startOfDay =  nowTime.withTimeAtStartOfDay();
            //DateTime endOfDay =  nowTime.millisOfDay().withMaximumValue();

            if (item.getStatus() == 0 && item.getDateTime().isBefore(startOfDay)){
                item.setStatus(1);
            }
            if (item.getStatus() == 0){
                continue;
            }
            apiKeyList.add(item);
        }
        return apiKeyList;
    }

    public ApiKeyItem getRandomApiKeyItem(){
        List<ApiKeyItem> validApiKeyItems = getValidApiKeyItems();
        // 如果没有合法的ApiKey
        if (CollectionUtils.isEmpty(validApiKeyItems)){
            validApiKeyItems = Arrays.asList(API_KEY_ITEMS);
        }

        ApiKeyItem apiKeyItem = RandomUtils.getRandomElement(validApiKeyItems);
        return  apiKeyItem;
    }

    private static ApiKeyItem findItemByApiKey(String apiKey){
        for (ApiKeyItem item : API_KEY_ITEMS
                ) {
            if (item.getApikey().equals(apiKey)){
                return item;
            }
        }
        return null;
    }

    public void invalidApiKeyItem(String apiKey){
        ApiKeyItem item = findItemByApiKey(apiKey);
        if (item != null){
            item.setStatus(0);
            String timeZone = item.getTimeZone();
            DateTime nowTime = new DateTime(DateTimeZone.forID(timeZone));
            item.setDateTime(nowTime);
        }
    }

    public static void main(String[] args) {
        ApiKeyManager apiKeyManager = ApiKeyManager.getInstance();
        ApiKeyItem item = apiKeyManager.getRandomApiKeyItem();
        String apiKey = item.getApikey();
        apiKeyManager.invalidApiKeyItem(apiKey);
        List<ApiKeyItem> items = apiKeyManager.getValidApiKeyItems();
        System.out.println(items);
    }


}
