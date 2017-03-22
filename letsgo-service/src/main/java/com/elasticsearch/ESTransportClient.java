package com.elasticsearch;

import com.umessage.letsgo.service.common.constant.Constant;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * Created by gaofei on 2017/2/27.
 */
public class ESTransportClient {


   public static TransportClient getClient(){
      try{
          Settings esSetting = Settings.builder()
              .put(Constant.ELASTIC_SEARCH_CLUSTER_NAME, Constant.ELASTIC_SEARCH_CLUSTER_VALUE)
              .build();
         TransportClient client = new PreBuiltTransportClient(esSetting)
                 .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(Constant.ELASTIC_SEARCH_ADDRESS_VALUE), Integer.parseInt(Constant.ELASTIC_SEARCH_PORT)));

         return client;
      }
      catch (Exception e){
         e.getMessage();
         e.printStackTrace();
      }
      return null;
   }

   public static void main(String args[]) {

      try{
         Settings esSetting = Settings.builder()
                 .put("cluster.name", "elasticsearch_production")
                 .build();
         TransportClient client = new PreBuiltTransportClient(esSetting)
                 .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.9.162"), 9300));

      }
      catch (Exception e){
         e.getMessage();
         e.printStackTrace();
      }
   }

}
