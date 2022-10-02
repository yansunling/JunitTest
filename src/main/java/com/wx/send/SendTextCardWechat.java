package com.wx.send;

import com.wx.WechatUtils;
import com.wx.data.WechatTextcard;

public class SendTextCardWechat {
    public static void main(String[] args) throws Exception{

        String receiver="T1113";
        String message="你好！针对客户发货符合以下任一条件，①超出近三月发货周期平均数的2倍 ②最长一个自然月未发货，将推送至销售人员企业微信";


        /*String corpID="ww7a1daec5af2f5422";
        String agentId="1000007";
        String secret="LFluGYxGSkkWIravSeoMJE6zjNYWP3CVnnR2DYKLCBc";*/



       String agentId="1000006";
        String corpID="ww7a1daec5af2f5422";
       String secret="254D52A774A2D1201B86FAAA2AD961F678F6A01AF781AC9D8A5814103FD0B9C31310A198997C79C6C7B7E9FAF361BA2F";





        WechatTextcard textcard=new WechatTextcard();

        textcard.setUrl("https://tlwl.uat.tuolong56.com");
        textcard.setTitle("您收到了一条客户评价");
        textcard.setBtntxt("处理反馈");
        textcard.setDescription("<div class='gray'>运单号：600515785</div><br/>" +
                "<div class='highlight'>评价：一般</div><br/>" +
                "问题描述：失效<br/>" +
                "   1、联系方式问题：手机号不正确\n" +
                "   2、地址问题：地址不正确<br/>" +
                "");


        WechatUtils.sendTextCardMessage(receiver,textcard,corpID,agentId,secret);



    }
}
