package com.oa;

import com.yd.utils.common.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractCommentUtil {
    public static void main(String[] args) {
        String address = "<p style=\"font-family:&#39;微软雅黑&#39;,&#39;Microsoft YaHei&#39;;font-size:12px;\"><span style=\"font-family: 微软雅黑; font-size: 18px;\">1、仓储费价格维护：</span><br/></p><p style=\"font-family:&#39;微软雅黑&#39;,&#39;Microsoft YaHei&#39;;font-size:12px;\"><span style=\"font-family: 微软雅黑; font-size: 18px;\"></span></p><p style=\"font-family:&#39;微软雅黑&#39;,&#39;Microsoft YaHei&#39;;font-size:12px;\"><img src=\"https://cdn.nlark.com/yuque/0/2021/png/414674/1632411668994-3ed2aa96-e839-410e-8525-a9af633cc4fa.png\" width=\"960\" id=\"u03386710\" class=\"ne-image\"/></p><p style=\"font-family:&#39;微软雅黑&#39;,&#39;Microsoft YaHei&#39;;font-size:12px;\"><span style=\"font-size: 20px;\">2、到达联打印时仓储费逻辑控制</span></p><p style=\"font-family:&#39;微软雅黑&#39;,&#39;Microsoft YaHei&#39;;font-size:12px;\"><span style=\"font-size: 20px;\"><img src=\"/weaver/weaver.file.FileDownload?fileid=540446\" title=\"\" alt=\"\" width=\"1010\" height=\"368\" style=\"width: 1010px; height: 368px;\"/></span></p><p style=\"font-family:&#39;微软雅黑&#39;,&#39;Microsoft YaHei&#39;;font-size:12px;\"><span style=\"font-size: 20px;\"><br/></span></p><p style=\"font-family:&#39;微软雅黑&#39;,&#39;Microsoft YaHei&#39;;font-size:12px;\"><span style=\"font-size: 20px;\"><img src=\"/weaver/weaver.file.FileDownload?fileid=540449\" title=\"\" alt=\"\"/><img src=\"/weaver/weaver.file.FileDownload?fileid=540448\" title=\"\" alt=\"\"/></span></p><p style=\"font-family:&#39;微软雅黑&#39;,&#39;Microsoft YaHei&#39;;font-size:12px;\"><span style=\"font-family: 微软雅黑; font-size: 18px;\"></span><br/></p>";
        //正则表达式
        String fengli="fileid=(.*?)\"";			//提取风级的正则表达式
        Pattern pafengli=Pattern.compile(fengli);
        Matcher matcher = pafengli.matcher(address);

        int matcher_start = 0;
        while (matcher.find(matcher_start)){
            String fileId = matcher.group(1);

            matcher_start = matcher.end();
        }
        String startStr="src=\"/weaver/weaver.file.FileDownload?fileid=";
        int start = address.indexOf(startStr);
        int end = address.indexOf("\"", start+startStr.length());
        System.out.println(start);
        System.out.println(address.substring(start+startStr.length(),end));
    }
    public static String buildContent(String content){
        if(StringUtils.isNotBlank(content)){
            content=content.replaceAll("<br/><br/><span style=''font-size:11px;color:#666;''>来自android客户端</span>","");
            content=content.replaceAll("<br/><br/><span style=''font-size:11px;color:#666;''>来自iPhone客户端</span>","");
            content=content.replaceAll("<br/><br/><span _target=''wfautoapprove'' style=''font-size:11px;color:#666;''>系统自动批准</span>","");
            content=content.replaceAll("<br/><br/><span style=''font-size:11px;color:#666;''>来自Web手机版</span>","");
        }


        return content;



    }




}
