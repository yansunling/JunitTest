package com.dy.register.test;

import com.dy.register.EtcdUtil;
import com.dy.register.EtcdClientListener;

public class RegisterMonitor {
    public static void main(String[] args) throws  Exception{
        EtcdUtil.setAddress("192.168.20.98:2379");
        String key="tms-job";
        /*while(true){
            String key="XXXSrv/v1.0/address/1";
            try {
                Thread.sleep(1000);
                String etcdValueByKey = EtcdUtil.getEtcdValueByKey(key);
                System.out.println(etcdValueByKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        /*EtcdUtil.setAddress("192.168.20.98:2379");
        ServerMonitor serverMonitor=ServerMonitor.getInstance();

        serverMonitor.setServerName("XXXSrv/v1.0/address/1");
        serverMonitor.startWatch();*/


        EtcdClientListener listener=new EtcdClientListener();
        listener.watchKey=key;
        listener.init();



        Thread.sleep(1000000);
    }
}
