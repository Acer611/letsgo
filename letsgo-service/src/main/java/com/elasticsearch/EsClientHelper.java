package com.elasticsearch;

import com.umessage.letsgo.service.common.constant.Constant;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gaofei on 2017/3/9.
 */
public class EsClientHelper {

    private  TransportClient client;

    private EsClientHelper() {
        init();
    }

    public static final EsClientHelper getInstance() {
        return ClientHolder.INSTANCE;
    }

    private static class ClientHolder {
        private static final EsClientHelper INSTANCE = new EsClientHelper();
    }

    /**
     * 初始化默认的client
     */
    private TransportClient init() {
        try {
            Settings esSetting = Settings.builder()
                    .put(Constant.ELASTIC_SEARCH_CLUSTER_NAME, Constant.ELASTIC_SEARCH_CLUSTER_VALUE)
                    .build();
            if(null==client){
                client = new PreBuiltTransportClient(esSetting)
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(Constant.ELASTIC_SEARCH_ADDRESS_VALUE), Integer.parseInt(Constant.ELASTIC_SEARCH_PORT)));
            }
            return client;
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return null;
    }


    public  TransportClient getClient() {
        return client;
    }


}
