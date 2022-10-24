// 拍照并添加到PDF队列
function add_pdf(){
    // 1.先拍照获取图片数据
    data1 = {
        "filepath": "base64",
        "rotate": "0",
        "cutpage": "0",
        "camidx": "0",
        "ColorMode": "0",
        "quality": "0"
    }
    $.post("http://127.0.0.1:38088/video=grabimage", JSON.stringify(data1), function(res1){
        // 2.将图片添加到PDF队列中
        data2 = {
            "ImagePath": "",
            "ImageBase64": res1.photoBase64
        }
        $.post("http://127.0.0.1:38088/pdf=addimage", JSON.stringify(data2), function(res2){
            my_log("拍照成功，并添加PDF队列成功");
        })
        add_img(res1.photoBase64);
    })
}

// 生成PDF文件
function save_pdf(){
    data = {
        "PdfPath": "D://123.pdf"
    }
    $.post("http://127.0.0.1:38088/pdf=save", JSON.stringify(data), function(res){
        if(res.code == "0"){
            my_log("PDF文件生成成功，【D:pdf.pdf】")
        }else{
            my_log(res.data)
        }
    })
}