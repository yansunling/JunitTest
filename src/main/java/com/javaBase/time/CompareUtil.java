package com.javaBase.time;

import java.sql.Timestamp;

public class CompareUtil {


    //判断当前时间是否在生效时间范围内
    public static boolean availableTime(Timestamp effect,Timestamp expire){

      Long effectTime=null;
      Long expireTime=null;
      Long nowTime=System.currentTimeMillis();
      if(effect==null&&expire==null){//生效，失效时间都为空，有效
          return true;
      }else if(effect!=null&&expire==null){//生效时间不为空，失效为空
          effectTime = effect.getTime();
          if(nowTime>=effectTime){//当前时间大于生效时间，有效
              return true;
          }else{
              return false;
          }
      }else if(effect==null&&expire!=null){//生效时间为空，失效为不为空
          expireTime=expire.getTime();
          if(expireTime>nowTime){//当前时间小于失效时间，有效
              return true;
          }else{
              return false;
          }
      }else{
          effectTime=effect.getTime();
          expireTime=expire.getTime();
          if(nowTime>=effectTime&&nowTime<expireTime){//生效，失效时间都为不为空
              return true;  //在这个时间范围内有效
          }else {
              return false;
          }
      }

    }

}
