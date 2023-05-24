package com.javaBase.json;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class JsonTest {
	public static void main(String[] args) {
		String msg="{\"errorCode\":100017,\"msg\":\"非正常CIPResponseMsg数据!\",\"data\":\"\r\n" + 
				"<!DOCTYPE HTML>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"\r\n" + 
				"    <meta charset='utf-8'/>\r\n" + 
				"    <title>您访问的页面不存在</title>\r\n" + 
				"\r\n" + 
				"    <link rel='shortcut icon' href='/ui/img/favicon.ico'/>\r\n" + 
				"\r\n" + 
				"<style>\r\n" + 
				"    .state-error{height:320px;font-size:14px;padding-top:282px;color:#b4bacc;}\r\n" + 
				"    .state-error .center{text-align:center;}\r\n" + 
				"    .state-error b{color:#64aeff;}\r\n" + 
				"    .state-error a{display:inline-block;*zoom:1;*display:inline;}\r\n" + 
				"    .state-error a:hover{text-decoration:none;}\r\n" + 
				"    .state-error a.refresh-btn{height:15px;line-height:15px;padding-left:23px;color:#64aeff;background:url('//static2.qianqian.com/web/static/i/PDjWl2sD.png') no-repeat;}\r\n" + 
				"    .state-error a.back-btn{margin-top:7px;width:107px;height:27px;line-height:27px;border:2px solid #b4bacc;text-align:center;border-radius:14px;color:#b4bacc;overflow:hidden;cursor:pointer}\r\n" + 
				"    .state-error a.back-btn:hover{background:#dfefff;}\r\n" + 
				"    .body{background:none;}\r\n" + 
				"    .state-500{background:url('/brs/ui/img/500.png') center 65px no-repeat;}\r\n" + 
				"</style>\r\n" + 
				"\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"        <div class='body clearfix'>\r\n" + 
				"\r\n" + 
				"            <div class='state-error state-500 c9'>\r\n" + 
				"                <div class='center'><a class='back-btn' onclick='refresh()'>刷新</a></div>\r\n" + 
				"            </div>\r\n" + 
				"\r\n" + 
				"        </div>\r\n" + 
				"<script type='text/javascript'>\r\n" + 
				"\r\n" + 
				"    var $centerTab = window.top.document.getElementById('centerTab');\r\n" + 
				"    function refresh(){\r\n" + 
				"        if($centerTab){\r\n" + 
				"            var display = window.top.$('#jcdfDiglogDiv').parent().css('display');\r\n" + 
				"            var refreshFrame;\r\n" + 
				"            if(display && 'block' == display) {//如果是弹框打开\r\n" + 
				"                refreshFrame = window.top.$('#jcdfDiglogDivIframe')[0];\r\n" + 
				"            }else{\r\n" + 
				"                var currentTab = window.top.$('#centerTab').tabs('getSelected');\r\n" + 
				"                if(currentTab && currentTab.find('iframe').length > 0){\r\n" + 
				"                    refreshFrame = currentTab.find('iframe')[0];\r\n" + 
				"                }\r\n" + 
				"            }\r\n" + 
				"            if(refreshFrame){\r\n" + 
				"                var refresh_url = refreshFrame.src;\r\n" + 
				"                refreshFrame.contentWindow.location.href= refresh_url;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"        }else{\r\n" + 
				"            window.location.reload();\r\n" + 
				"        }\r\n" + 
				"    }\r\n" + 
				"</script>\r\n" + 
				"\r\n" + 
				"</body>\r\n" + 
				"</html>\r\n" + 
				"\"}\r\n" + 
				"";
		
//		System.out.println(msg);
		
		Map parseObject = JSONObject.parseObject(msg,Map.class);
		parseObject.get("data");
		
		
	}
}
