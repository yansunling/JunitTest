package com.dy.test.etcdLeaseid;

import com.alibaba.fastjson.JSONObject;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.Set;

public class LeaseTest {
    public static void main(String[] args) throws Exception{
        String address="192.168.20.98:2379";
        EtcdUtil.setAddress(address);

//        Long aLong = EtcdUtil.putEtcdTTL(10);
//        System.out.println(aLong);

//        String key1="test/192.168.1368.14";
//        String key2="test/2";
//        EtcdUtil.putEtcdValueByKeyTTL(key1,"1",10);
//        EtcdUtil.putEtcdValueByKeyTTL(key2,"2",1);
//
//
//       Thread.sleep(3000L);
//
        Map<String, String> test = EtcdUtil.getEtcdKVDir("task-job_etcd_register");
//
//        String etcdValueByKey = EtcdUtil.getEtcdValueByKey(key2);
//
//        System.out.println(etcdValueByKey);
        System.out.println(JSONObject.toJSONString(test));



    }

    public static String getLocalPort() throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        Set<ObjectName> objectNames = mBeanServer.queryNames(new ObjectName("*:type=Connector,*"), null);
        if (objectNames == null || objectNames.size() <= 0) {
            throw new IllegalStateException("Cannot get the names of MBeans controlled by the MBean server.");
        }
        for (ObjectName objectName : objectNames) {
            String protocol = String.valueOf(mBeanServer.getAttribute(objectName, "protocol"));
            String port = String.valueOf(mBeanServer.getAttribute(objectName, "port"));
            // windows下属性名称为HTTP/1.1, linux下为org.apache.coyote.http11.Http11NioProtocol
            if (protocol.equals("HTTP/1.1") || protocol.equals("org.apache.coyote.http11.Http11NioProtocol")) {
                return port;
            }
        }
        throw new IllegalStateException("Failed to get the HTTP port of the current server");
    }


}
