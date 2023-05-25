package com.module.etcd.register.test;

import com.module.etcd.register.RegisterClusterServer;
import com.module.etcd.register.util.IpUtil;

public class RegisterTest {
    public static void main(String[] args) throws Exception{
        String ip = IpUtil.getIp();
        RegisterClusterServer server=new RegisterClusterServer();
        server.setServerName("query-job");
        server.registerNode(ip,"192.168.20.98:2379",10);


//        RegisterClusterServer server2=new RegisterClusterServer();
//        server2.setServerName("tms-job");
//        server2.registerNode(ip,"192.168.20.98:2379",10);
//
//
//
        Thread.sleep(1000000);




    }
}
