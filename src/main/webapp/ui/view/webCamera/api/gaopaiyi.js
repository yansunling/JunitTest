// 切换主头视频模式
function change_view1_mode(){
    data = {
        "camidx": "0",
        "mode": String($("#view1_mode").val())
    }
    $.post("http://127.0.0.1:38088/device=getresolution", JSON.stringify(data), function(res){
        $("#view1_resolution_power option").remove();
        resolution_list = res.data.split("|");
        for(var i=0; i<resolution_list.length; i++){
            $("#view1_resolution_power").append("<option value='" + resolution_list[i] + "'>" + resolution_list[i] + "</option>")
        }
    })
}

// 切换副头视频模式
function change_view2_mode(){
    data = {
        "camidx": "1",
        "mode": String($("#view2_mode").val())
    }
    $.post("http://127.0.0.1:38088/device=getresolution", JSON.stringify(data), function(res){
        $("#view2_resolution_power option").remove();
        resolution_list = res.data.split("|");
        for(var i=0; i<resolution_list.length; i++){
            $("#view2_resolution_power").append("<option value='" + resolution_list[i] + "'>" + resolution_list[i] + "</option>")
        }
    })
}

// 设置主头分辨率
function set_view1_resolution_power(){
    // 1.关闭摄像头
    data1 = { "camidx": "0" };
    $.post("http://127.0.0.1:38088/video=close", JSON.stringify(data1), function(res){
        $("#view1").attr("src", "");

        // 2.设置分辨率
        data2 = {
            "camidx": "0",
            "mode": $("#view1_mode").val(),
            "width": $("#view1_resolution_power").val().split("*")[0],
            "height": $("#view1_resolution_power").val().split("*")[1]
        }
        $.post("http://127.0.0.1:38088/device=setresolution", JSON.stringify(data2), function(res){
            my_log("主头分辨率设置成功");

            // 3.打开摄像头
            $("#view1").attr("src", "http://127.0.0.1:38088/video=stream&camidx=0");
        })
    })
}

// 设置副头分辨率
function set_view2_resolution_power(){
    // 1.关闭摄像头
    data1 = { "camidx": "1" };
    $.post("http://127.0.0.1:38088/video=close", JSON.stringify(data1), function(res){
        $("#view2").attr("src", "");

        // 2.设置分辨率
        data2 = {
            "camidx": "1",
            "mode": $("#view2_mode").val(),
            "width": $("#view2_resolution_power").val().split("*")[0],
            "height": $("#view2_resolution_power").val().split("*")[1]
        }
        $.post("http://127.0.0.1:38088/device=setresolution", JSON.stringify(data2), function(res){
            my_log("副头分辨率设置成功");

            // 3.打开摄像头
            $("#view2").attr("src", "http://127.0.0.1:38088/video=stream&camidx=1");
        })
    })
}

// 打开主头视频
function open_view1(){
    $('#view1').attr("src", "http://127.0.0.1:38088/video=stream&camidx=0");
    my_log('打开主头成功');
}

// 关闭主头视频
function close_view1(){
    data = {
        "camidx": "0"
    }
    $.post("http://127.0.0.1:38088/video=close", JSON.stringify(data), function(res){
        $("#view1").attr("src", "");
        my_log('关闭主头成功');
    })
}

// 打开副头视频
function open_view2(){
    $('#view2').attr("src", "http://127.0.0.1:38088/video=stream&camidx=1");
    my_log('打开副头成功')
}

// 关闭副头视频
function close_view2(){
    data = {
        "camidx": "1"
    }
    $.post("http://127.0.0.1:38088/video=close", JSON.stringify(data), function(res){
        $("#view2").attr("src", "");
        my_log('关闭副头成功');
    })
}

// 左转
function rotate90(){
    data = {
        "camidx": "0",
        "rotate": "90"
    }
    $.post("http://127.0.0.1:38088/video=rotate", JSON.stringify(data), function(res){
        if(res.code == '0'){
            my_log("旋转成功");
        }
    })
}

// 右转
function rotate270(){
    data = {
        "camidx": "0",
        "rotate": "270"
    }
    $.post("http://127.0.0.1:38088/video=rotate", JSON.stringify(data), function(res){
        if(res.code == '0'){
            my_log("旋转成功")
        }
    })
}

// 开启主头纠偏显示
function open_deskew(){
    data = {
        "camidx": "0",
        "open": "1"
    }
    $.post("http://127.0.0.1:38088/dvideo=cameradeskew", JSON.stringify(data), function(res){
        if(res.code == '0'){
            my_log("已开启主头纠偏显示")
        }else{
            my_log("开启纠偏显示失败")
        }
    })
}

// 关闭主头纠偏显示
function close_deskew(){
    data = {
        "camidx": "0",
        "open": "0"
    }
    $.post("http://127.0.0.1:38088/dvideo=cameradeskew", JSON.stringify(data), function(res){
        if(res.code == '0'){
            my_log("已关闭主头纠偏显示")
        }else{
            my_log("关闭纠偏显示失败")
        }
    })
}

// 拍照，本示例为拍照获取base64数据
function scan(view){
	jiupian = "0";
	if (view == 0)
	{
		jiupian = "1"
	}
    data = {
        "filepath": "base64",
        "rotate": "0",
        "deskew": jiupian,
        "camidx": String(view),
        "ColorMode": "0",
        "quality": "0"
    }
    $.post("http://127.0.0.1:38088/video=grabimage", JSON.stringify(data), function(res){
        my_log("base64:" + res.photoBase64, 1);
        my_log("拍照成功");
        add_img(res.photoBase64);
    })
}

// 主头拍照并保存本地
function save_image_scan(){
    data = {
        "filepath": "D:\\a.jpg",
        "rotate": "0",
        "cutpage": "0",
        "deskew": "1",
        "deskewval": "-20",
        "camidx": "0",
        "ClolorMode": "0",
        "quality": "0" 
    }
    $.post("http://127.0.0.1:38088/video=grabimage", JSON.stringify(data), function(res){
        if(res.code == '0')
        {
            my_log("拍照成功，图片保存在D:\\a.jpg")

            // 添加到缩略图
            img = document.createElement('img');
            img.id = 'hu' + String(Math.random());
            img.src = "D:\\a.jpg";
            img.oncontextmenu = showOperation;
            img.style.width = '80px';
            img.style.height = '80px';
            document.getElementById('my_thumbnail').appendChild(img)
        }
    })
}

// 展平拍照
function flattening_scan(){
    data = {
        "filepath": "",
        "rotate": "0",
        "camidx": "0",
        "cutpage": "0",
        "autoflat": {
            "flat": "1",
            "leftfilepath": "D://testleft.jpg",
            "rightfilepath": "D://testright.jpg",
            "removefinger": "1",
            "doublepage": "1"
        }
    }
    $.post("http://127.0.0.1:38088/video=autoflat", JSON.stringify(data), function(res){
        if (res.code == "0"){
            add_img(res.leftphotoBase64);
            add_img(res.rightphotoBase64);
            my_log("展平拍照成功，请到D盘查看【testleft.jpg】【testright.jpg】")
        }else{
            my_log("展平失败")
        }
    })
}

// 开启自动拍照
function open_auto_scan(){
    my_log("开启自动拍照成功")
    data = {
        "movedetecflag": "1",                // 开启纠偏
        "listpath": "D://httpcamera",        // 本地图片保存位置
        "filepath": "LT"                     // 图片前缀
    }
    $.post("http://127.0.0.1:38088/video=movedetec", JSON.stringify(data), function(res){
        add_img(res.data);
        my_log("拍摄成功");
        console.log(res);
        open_auto_scan();
    })
}

// 关闭自动拍照
function close_auto_scan(){
    data = {
        "movedetecflag": "0"
    }
    $.post("http://127.0.0.1:38088/video=movedetec", JSON.stringify(data), function(res){
        my_log("关闭自动拍照成功");
    })
}

// 条码识别
function ocr_barcode(){
    data = {
        "time": "20"
    }
    $.post("http://127.0.0.1:38088/barcode=get", JSON.stringify(data), function(res){
        for(var i = 0; i < res.data.length; i++ ){
            my_log(res.data[i].barcodedata);
        }
        my_log("识别成功，条码数量" + res.data.length + "个，分别是：")
    })
}

// 获取指纹
function get_biokey(){
    my_log("请按压指纹");
    data = {
        "time": "20"
    }
    $.post("http://127.0.0.1:38088/biokey=get", JSON.stringify(data), function(res){
        if(res.code == '0'){
            add_img(res.data);
            my_log("获取指纹成功");
        }else{
            my_log("获取指纹失败");
        }
    })
}

// 读取身份证
function read_IDCard(){
    $.post("http://127.0.0.1:38088/card=idcard", function(res){
        if(res.code == '0'){
            add_img(res.IDCardInfo.photoBase64);
            my_log("身份证头像：" + res.IDCardInfo.photoBase64);
            my_log("身份证民族代码：" + res.IDCardInfo.nationCode);
            my_log("身份证性别代码：" + res.IDCardInfo.sexCode);
            my_log("身份证有效终止日期：" + res.IDCardInfo.validEnd);
            my_log("身份证有效起始日期：" + res.IDCardInfo.validStart);
            my_log("身份证发卡机构：" + res.IDCardInfo.issueOrgan);
            my_log("身份证号码：" + res.IDCardInfo.cardID);
            my_log("身份证地址：" + res.IDCardInfo.address);
            my_log("身份证生日：" + res.IDCardInfo.birthday);
            my_log("身份证性别：" + res.IDCardInfo.sex);
            my_log("身份证姓名：" + res.IDCardInfo.name);
            my_log("身份证读取成功，信息如下：");
        }else{
            my_log("读取失败");
        }
    })
}

// 弹出签字窗口: GW500A &  GW1000A
function open_sign(){
    data = {
        "pos": {
            "top": "250",
            "left": "280",
            "width": "600",
            "height": "250"
            },
        "remark": "开始签名"
    }
    $.post("http://127.0.0.1:38088/serialport=sign", JSON.stringify(data), function(res){
        my_log("弹出签字窗口成功");
    })
}

// 关闭签字窗口：GW500A & GW1000A
function close_sign(){
    $.post("http://127.0.0.1:38088/serialport=close", function(){
        my_log("关闭签字窗口成功");
    })
}

// 获取签名图片
function get_sign(){
    $.post("http://127.0.0.1:38088/pendisplay=getsigndata", function(res){
        add_img(res.data);
        my_log("获取签名图片成功");
    })
}

// 开始录制视频
function start_video(){
    data = {
        "action": "start",
        "parameter": {
            "camidx": "0",
            "width": "640",
            "hieght": "480",
            "audio": "",
            "framerate": "10",
            "filepath": "D://a.mp4",
            "bit_rate": "400000"
        }
    }
    $.post("http://127.0.0.1:38088/video=record", JSON.stringify(data), function(res){
        if(res.code == "0"){
            my_log("开始录制视频。。。");
        }
    })
}

// 结束录制视频
function end_video(){
    data = {
        "action": "stop"
    }
    $.post("http://127.0.0.1:38088/video=record", JSON.stringify(data), function(res){
        if(res.code == "0"){
            my_log("结束录制，文件保存地址：D:a.mp4，时长：" + res.time);
        }
    })
}

// 获取音频设备列表
function get_audio_list(){
    data =  {
        "action": "audio"
    }
    $.post("http://127.0.0.1:38088/video=record", JSON.stringify(data), function(res){
        my_log("音频设备列表：" + res.audio);
    })
}

// 获取录制视频状态
function get_video_status(){
    data = {
        "action": "status"
    }
    $.post("http://127.0.0.1:38088/video=record", JSON.stringify(data), function(res){
        res_status = {
            "100": "空闲中",
            "101": "录像中",
            "102": "设备错误"
        }
        my_log("当前状态：" + res_status[res.status]);
    })
}

// 开启A3A4幅面自动切换
function open_a3a4(){
    data = {
        "switchflag": "1",
        "a3size": "0.5",
        "a4size": "0.9"
    }
    $.post("http://127.0.0.1:38088/device=a3a4switch", JSON.stringify(data), function(res){
        my_log("开启A3A4幅面自动切换成功");
    })
}

// 关闭A3A4幅面自动切换
function close_a3a4(){
    data = {
        "switchflag": "0",
        "a3size": "0.5",
        "a4size": "0.9"
    }
    $.post("http://127.0.0.1:38088/device=a3a4switch", JSON.stringify(data), function(res){
        my_log("关闭A3A4幅面自动切换成功");
    })
}