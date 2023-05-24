/**    
 * 文件名：EtcdClientListen.java    
 *    
 * 版本信息：    
 * 日期：2018年8月16日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.dy.etcd;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.Watch;
import io.etcd.jetcd.options.WatchOption;
import io.etcd.jetcd.watch.WatchEvent;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import static com.google.common.base.Charsets.UTF_8;

/**    
 *     
 * 项目名称：DBServer    
 * 类名称：EtcdClientListen    
 * 类描述：  监听事件
 * 创建人：jinyu    
 * 创建时间：2018年8月16日 下午5:58:30    
 * 修改人：jinyu    
 * 修改时间：2018年8月16日 下午5:58:30    
 * 修改备注：    由于我用到的都是字符串，所以直接使用字符串封装，传回的byte[]
 * @version     
 *     
 */
public class ServerListener {

    private String address;//etc地址

    private String  key;//监控key

    private  boolean isStop=false;//关闭监听

    public ServerListener(String address, String watchKey) {
        this.address = address;
        this.key = watchKey;
    }
  /**
   *
  * @Title: init
  * @Description: 初始化监视
  * @param
  * @return void    返回类型
   */
  public void start() {
      //启动监听线程
      new Thread(()->{
          String[] urls = address.split(",");
          Client client = Client.builder().endpoints(urls).build();

          // 最大事件数量
          Integer maxEvents = Integer.MAX_VALUE;
          CountDownLatch latch = new CountDownLatch(maxEvents);
          Watch.Watcher watcher = null;
          try{
              ByteSequence watchKey = ByteSequence.from(key, UTF_8);
              WatchOption watchOpts = WatchOption.newBuilder().build();

              watcher = client.getWatchClient().watch(watchKey, watchOpts, response -> {
                  for (WatchEvent event : response.getEvents())
                  {
                      System.out.println("watch type= \"" + event.getEventType().toString() + "\",  key= \""
                              + Optional.ofNullable(event.getKeyValue().getKey()).map(bs -> bs.toString(UTF_8)).orElse("")
                              + "\",  value= \"" + Optional.ofNullable(event.getKeyValue().getValue())
                              .map(bs -> bs.toString(UTF_8)).orElse("")
                              + "\"");
                  }

                  latch.countDown();
              });
              latch.await();
          }catch (Exception e){
              if (watcher != null){
                  watcher.close();
                  client.close();
              }
          }
        }).start();
      }
}
