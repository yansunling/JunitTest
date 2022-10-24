// 获取设备型号
function getequipmenttype(){
    $.post("http://127.0.0.1:38088/device=getequipmenttype", function(res){
        if(res.code == '0'){
            my_log("设备型号：" + res.data)
        }else{
            my_log("获取设备型号失败")
        }
    })
}

// 获取设备序列号
function getsonixserialnumber(){
    $.post("http://127.0.0.1:38088/device=getsonixserialnumber", function(res){
        if(res.code == '0'){
            my_log("设备序列号：" + res.data)
        }else{
            my_log("获取设备序列号失败")
        }
    })
}

// 获取设备状态
function get_status(){
    $.post("http://127.0.0.1:38088/video=status", function(res){
        my_log("副摄像头状态：" + res.video1)
        my_log("主摄像头状态：" + res.video0)
    })
}

// 判断设备是否连接
function is_connect(){
    $.post("http://127.0.0.1:38088/device=isconnect", function(res){
        if(res.data == "0"){
            my_log("设备未连接")
        }else{
            my_log("设备连接数：" + res.data)
        }
    })
}