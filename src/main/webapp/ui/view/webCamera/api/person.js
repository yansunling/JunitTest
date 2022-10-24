// 活体检测
function is_living_person(){
    data = {
        "time": "20"
    }
    $.post("http://127.0.0.1:38088/faceLive=start", JSON.stringify(data), function(res){
        my_log('返回值： ' + res.data);
    })
}

// 人证比对
function person_and_IDCard(){
    // 1.先拍摄人脸图片
    data = {
        "filepath": "base64",
        "rotate": "0",
        "cutpage": "0",
        "camidx": "1",
        "ColorMode": "0",
        "quality": "0"
    }
    $.post("http://127.0.0.1:38088/video=grabimage", JSON.stringify(data), function(res1){
        var img1 = res1.photoBase64;
        add_img(img1);

        // 2.读取身份证，获取头像
        $.post("http://127.0.0.1:38088/card=idcard", function(res2){
            var img2 = res2.IDCardInfo.photoBase64;
            add_img(img2);
            
            // 3.比对
            data3 = {
                "FaceOne": img1,
                "FaceTwo": img2
            }
            $.post("http://127.0.0.1:38088/comparison=imgdata", JSON.stringify(data3), function(res3){
                my_log("比对值：" + res3.data)
            })
        })
    })
}