package com.module.etcd.register.test;

import com.module.etcd.register.EtcdUtil;

public class RegisterTest2 {
    public static void main(String[] args) throws Exception{
//        String ip = IpUtil.getIp();
//        RegisterClusterServer server=new RegisterClusterServer();
//        server.setServerName("tms-job");
//        server.registerNode(ip,"192.168.20.98:2379",10);

        EtcdUtil.setAddress("192.168.20.98:2379");
//        String etcdValueByKey = EtcdUtil.getEtcdValueByKey("task-input_etcd_register");
//        System.out.println(etcdValueByKey);
//
//        EtcdUtil.setAddress("192.168.20.98:2379");
//        String etcdValueByKey1 = EtcdUtil.getEtcdValueByKey("task-input_etcd_register");
//        System.out.println(etcdValueByKey1);

        EtcdUtil.putEtcdValueByKeyTTL("ttea","tee",10);


        String etcdValueByKey = EtcdUtil.getEtcdValueByKey("ttea");
        System.out.println(etcdValueByKey);

        EtcdUtil.putEtcdValueByKeyTTL("tte3a","tee",10);

        String dd = EtcdUtil.getEtcdValueByKey("tte3a");
        System.out.println(dd);


//        Thread.sleep(1000000);




    }
}
