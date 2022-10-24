window.onload = function(){
    // 判断浏览器是否是IE
    if(window.ActiveXObject || "ActiveXObject" in window){    // 是IE
        $('#view_chrome').remove()
    }else{                                                    // 不是IE
        $('#view_ie').remove();

        // 加载视频图像
        $("#view1").attr("src", "http://127.0.0.1:38088/video=stream&camidx=0");
        $("#view2").attr("src", "http://127.0.0.1:38088/video=stream&camidx=1");

        // 加载主头视频模式
        view1_mode_data1 = { "camidx": "0", "mode": "0" };
        view1_mode_data2 = { "camidx": "0", "mode": "1" };
        $.post("http://127.0.0.1:38088/device=getresolution", JSON.stringify(view1_mode_data2), function(res){
            if(res.data.split("|").length > 1){
                $("#view1_mode").append("<option value='1'>MJPG</option>");
            }
        })
        $.post("http://127.0.0.1:38088/device=getresolution", JSON.stringify(view1_mode_data1), function(res){
            if(res.data.split("|").length > 1){
                $("#view1_mode").append("<option value='0'>YUY2</option>");
            }
        })
        $("#view1_mode").val('1');
        

        // 加载主头分辨率
        data = {
            "camidx": "0",
            "mode": "1"
        }
        $.post("http://127.0.0.1:38088/device=getresolution", JSON.stringify(data), function(res){
            resolution_list = res.data.split("|");
            for(var i=0; i<resolution_list.length; i++){
                $("#view1_resolution_power").append("<option value='" + resolution_list[i] + "'>" + resolution_list[i] + "</option>")
            }
        })

        // 加载副头视频模式
        view2_mode_data1 = { "camidx": "1", "mode": "0" };
        view2_mode_data2 = { "camidx": "1", "mode": "1" };
        $.post("http://127.0.0.1:38088/device=getresolution", JSON.stringify(view2_mode_data2), function(res){
            if(res.data.split("|").length > 1){
                $("#view2_mode").append("<option value='1'>MJPG</option>");
            }
        })
        $.post("http://127.0.0.1:38088/device=getresolution", JSON.stringify(view2_mode_data1), function(res){
            if(res.data.split("|").length > 1){
                $("#view2_mode").append("<option value='0'>YUY2</option>");
            }
        })
        $("#view2_mode").val('1');
        

        // 加载副头分辨率
        data2 = {
            "camidx": "1",
            "mode": "1"
        }
        $.post("http://127.0.0.1:38088/device=getresolution", JSON.stringify(data2), function(res){
            resolution_list = res.data.split("|");
            for(var i=0; i<resolution_list.length; i++){
                $("#view2_resolution_power").append("<option value='" + resolution_list[i] + "'>" + resolution_list[i] + "</option>")
            }
        })
    }

    // 选项卡切换效果
    nth = document.getElementById('area_title').getElementsByTagName('li');
    vth = document.getElementById('area_content').getElementsByTagName('div');
    for(var i = 0; i < nth.length; i++){
        nth[i].index = i;
        nth[i].onclick = function(){
            for(var j = 0; j < nth.length; j++)
            {
                nth[j].style.backgroundColor = 'thistle';
            }
            this.style.backgroundColor = 'yellow';
            for(var j=0;j<vth.length; j++){
                vth[j].style.display = 'none';
            }
            vth[this.index].style.display = 'block';
        }
    }

    // 选项卡初始效果
    for(var i = 0; i < nth.length; i++){
        nth[i].style.backgroundColor = 'thistle';
        vth[i].style.display = 'none';
    }
    nth[0].style.backgroundColor = 'yellow';
    vth[0].style.display = 'block';
}