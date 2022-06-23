package com.wx.send;

import com.wx.WechatUtils;

public class SendTest {
    public static void main(String[] args) throws Exception{

        String receiver="T1113";
        String message="你好！针对客户发货符合以下任一条件，①超出近三月发货周期平均数的2倍 ②最长一个自然月未发货，将推送至销售人员企业微信";
        String corpID="ww7a1daec5af2f5422";
        String agentId="1000007";
        String secret="LFluGYxGSkkWIravSeoMJE6zjNYWP3CVnnR2DYKLCBc";
        WechatUtils.sendMessage(receiver,message,corpID,agentId,secret);

        String filePath="C:/Users/admin/Desktop/销售客户发货统计_0601_颜孙令.xlsx";
        WechatUtils.sendFileMessage(receiver,filePath,corpID,agentId,secret);

    }
}
