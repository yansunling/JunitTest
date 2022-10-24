// 打开串口
function open_serialport(){
    let data = {
        "port": "0",
        "baud": "115200",
        "parity": "0",
        "databits": "8",
        "stopbits": "0"
    }
    $.post("http://127.0.0.1:38088/serialport=initserialport", JSON.stringify(data), function(res){
        if(res.code == '0'){
            my_log("打开串口成功")
        }else{
            my_log("打开串口失败")
        }
    })
}

// 关闭串口
function close_serialport(){
    let data = {"port": "0"}
    $.post("http://127.0.0.1:38088/serialport=deinitserialport", JSON.stringify(data), function(res){
        if(res.code == '0'){
            my_log("关闭串口成功")
        }else{
            my_log("关闭串口失败")
        }
    })
}

// 打开签字窗口：GW500R & GW1000R
function sign_r(){
    $.post("http://127.0.0.1:38088/serialport=sign", function(res){
        if(res.code == '0'){
            my_log("弹出签字窗口成功")
        }else{
            my_log("弹出签字窗口失败")
        }
    })
}

// 获取签名图片：GW500R & GW1000R
function sign_r_get(){
    $.post("http://127.0.0.1:38088/serialport=getdata", function(res){
        add_img(res.data)
        if(res.code == '0'){
            my_log("获取签名图片成功")
        }else{
            my_log("获取签名图片失败")
        }
    })
}