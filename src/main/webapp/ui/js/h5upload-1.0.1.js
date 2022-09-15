/**
 * @description 用于文件上传
 * @author fufeijian
 *
 * 2017.11.29 
 */
function uploader( options,input,dragAndDrop){
	var $this = this;
	// var vaurl = "http://kp.zjdywl.com/fsm/api/fsm_api/upload.do";
	var host = "https://tlwl.uat.tuolong56.com"
	var vaurl = "/fsm/api/fsm_api/upload.do";
	var pwurl = "/fsm/api/fsm_api/previewSearch.do";
	this.settings = {
		prefix: 'file',
		multiple: false,	//文件上传模式(false/true 单文件/多文件),默认false 单文件上传
		maxcount:5,			//最大上传文件个数,默认1个,多文件上传时,自定义个数
		autoUpload: false,
		appId: 'crm_app',
		selectBt:'photo',
		// url: vaurl,
		url: host + vaurl,
		requstBusiNo : function(ev){},
		dragAndDrop: '',
		progress: function (ev) { console.log('progress'); console.log(ev); },
		error: function (msg) { console.log('error'); console.log(msg); },
		success: function (data) { console.log('success'); console.log(data); }
	}
	$.extend(this.settings, options);
	this.input = input;
	this.host = host;
	this.dragAndDrop = dragAndDrop;
	this.xhr = new XMLHttpRequest();
	this.send = function () {
		if ($this.input.files.length < 1) {
			if ($this.settings.error) $this.settings.error('请选择上传文件!');
			return false;
		}
		if ($this.settings.multiple === false && $this.input.files.length > 1) {
			if ($this.settings.error) $this.settings.error('更改为多文件上传模式!');
			return false;
		}
		if ($this.settings.multiple === true && $this.input.files.length > $this.settings.maxcount) {
			if ($this.settings.error) $this.settings.error('最多上传'+$this.settings.maxcount+'个文件');
			return false;
		}		
		if ($this.settings.multiple) {
			$this.multiSend($this.input.files);
		}
		else {
			$this.singleSend($this.input.files[0]);
		}
	};

	this.singleSend = function (file) {
		var data = new FormData();
		data.append(String($this.settings.prefix), file);
		data.append('file_app_id', $this.settings.appId)

		$this.upload(data);
	};


	this.multiSend = function (files) {
		var data = new FormData();
		for (var i = 0; i < files.length; i++) data.append(String($this.settings.prefix) + String(i), files[i]);
		data.append('file_app_id', $this.settings.appId)
		$this.upload(data);
	};

	this.upload = function (data) {
		var business_no = $this.settings.businessNo;
		if(!business_no){
			business_no='';
		}
		data.append('business_no', business_no);
		$this.xhr.open('POST', $this.settings.url, false);
		$this.xhr.send(data);
	};
	this.setOpt = function (opt, val) {
		$this.settings[opt] = val;
		return $this;
	};
	this.getOpt = function (opt) {
		return $this.settings[opt];
	};

	this.setInput = function (elem) {
		$this.input = elem;
		return $this;
	};
	this.getInput = function () {
		return $this.input;
	};

	this.getPreview = function(){

      //  if (!$this.settings.multiple) {
			var thost = $this.settings.url.split('/fsm/api/')[0];
            //文件名
            filename = $this.data.data[0].file_serial_no;
            //注册文件系统
            fileappid = $this.data.data[0].file_app_id;
            pw = thost + "/fsm/api/fsm_api/preview.do?file_app_id=" + fileappid + "&file_serial_no=" + filename
            return pw;
       // }
	}

	this.getDownload = function(){

        if (!$this.settings.multiple) {
            //文件名
            filename = $this.data.data[0].file_serial_no;
            //注册文件系统
            fileappid = $this.data.data[0].file_app_id;
            dl = $this.host + "/fsm/api/fsm_api/download.do?file_app_id=" + fileappid + "&file_serial_no=" + filename
            return dl;
        }
	}
	if ($this.settings.progress) this.xhr.upload.addEventListener('progress', $this.settings.progress, false);
	this.xhr.onreadystatechange = function (ev) {
		if ($this.xhr.readyState == 4) {
			console.log('done!');
			if ($this.xhr.status == 200) {
				$this.data = JSON.parse($this.xhr.responseText);
				if ($this.settings.success) $this.settings.success($this.xhr.responseText,$this.getDownload(),$this.getPreview(),$this.data.data[0].file_serial_no, ev);
				$this.input.value = '';
			} else {
				if ($this.settings.error) $this.settings.error(ev);
			}
		}
	};

	//自动生成模式
	if(!$this.input){
        inputId = String(Math.floor(100*Math.random()));
		defaultInput = '<input type="file" id="'+inputId+'" style="display: none;" multiple="multiple"/>'//accept="image/jpg, image/jpeg, image/gif, image/png" 
        $("body").append(defaultInput);
		this.input = $("#"+inputId).get(0);
        if($this.settings.selectBt){
            $("#"+$this.settings.selectBt).click(function () {
                $("#"+inputId).trigger("click");//触发input的click事件
            });
        }
	}

	if ($this.settings.autoUpload) this.input.onchange = this.send;	
	
	//是否生成拖拽模式
	if($this.settings.dragAndDrop){
		if(!$this.dragAndDrop){
	        inputId = String(Math.floor(100*Math.random()));
			defaultInput = '<div id="'+inputId+'" style="width:200px;height:200px;border:1px dashed orange;text-align:center;"><p>拖拽上传</p></div>'
	        $("body").append(defaultInput);
			this.dragAndDrop = $("#"+inputId).get(0);
			$this.dragAndDrop=inputId;
		}
		$("#"+$this.dragAndDrop).on({
			dragenter:function(e){//鼠标拖进文件
			    e.preventDefault();  
			},  
			dragover:function(e){  //鼠标移动文件 
			    e.preventDefault();  
			    this.innerHTML = '请松开';  
			},  
			dragleave:function(e){  //文件离开
			    e.preventDefault();  
			    this.innerHTML = '请拖入要上传的文件';  
			}, 
		    drop:function(e){//拖后放			       
		        e.preventDefault();  
		        var file;
		        var files = e.originalEvent.dataTransfer.files;//获取要上传的文件对象(可以上传多个) jquery的file要去e.originalEvent里面拿
		        $this.multiSend(files);	        
		        $this.xhr.onreadystatechange = function (ev) {
		    		if ($this.xhr.readyState == 4 && $this.xhr.status == 200) {
		    			$this.data = JSON.parse($this.xhr.responseText);
	    				if ($this.settings.success) $this.settings.success($this.xhr.responseText,$this.getDownload(),$this.getPreview(),$this.data.data[0].file_serial_no, ev);
	    				$this.input.value = '';
		    		}else{
		    			if ($this.settings.error) $this.settings.error(ev);
		    		}
		    	};
		    }
		});
	}
	//查看显示方法业务号+appid
	this.fetchByBusiNo = function(businessNo,file_app_id){
		$.ajax({
			   type: "post",
			   url: host+pwurl,
			   async : false,
			   data : {
				   businessNo : businessNo,
				   file_app_id : file_app_id
			   },
			   success: function(data){
				   if (data && data.errorCode == 0) {
					   infoData=data.data;
				   }else {
					   console.log(data.msg + '('+ data.errorCode + ')');
				   }
			   }
		 });	
	};
	//文件预览(适用于查询fsm数据库的文件)
	this.previewData = function(fileData,param){
		var width=400;
		var height=300;
		var column=2;
		 if(param){
			 if(param.width) width=param.width;
			 if(param.height) height=param.height;
			 if(param.column) column=param.column;
		 }
		 var previewinfo='';
		 var imageExt = [".gif",".jpg",".png","jpeg"];//图片文件的后缀名
		 var audioExt = [".mp3"];//音频文件的后缀名
		 var videoExt = [".mp4"];//视频文件的后缀名
		 
		 var i = 0;
		 var column = column;
		 var filelen=fileData.length;
		 var rows=Math.ceil(filelen/column);//行数
		 previewinfo+="<table border='0' bordercolor='#000' cellspacing='10'>";
		 for(var r=0; r<rows; r++) {
			 previewinfo +='<tr>'
			 for(var c=0; c<column; c++) {
			 	//文件名
		        filename = fileData[i].file_serial_no;
		        //注册文件系统
		        fileappid = fileData[i].file_app_id;
		        //原文件名称
		        originfilename = fileData[i].origin_file_name;
		        //预览地址
		        pw = document.location.href.split(document.location.pathname)[0] + "/fsm/api/fsm_api/preview.do?file_app_id=" + fileappid + "&file_serial_no=" + filename;
		        //下载地址
		        dl = document.location.href.split(document.location.pathname)[0] + "/fsm/api/fsm_api/download.do?file_app_id=" + fileappid + "&file_serial_no=" + filename;
		        //获取文件名后缀名
		      	var spot = originfilename.lastIndexOf(".");
		      	if(spot > -1) ext = originfilename.substring(spot); 
		      	
		      	if($.inArray(ext, imageExt)>-1){
		    		previewinfo += '<td><a href="'+pw+'" target="_blank"><img width="'+width+'" height="'+height+'" id="pw" src="'+pw+'"/></a><br>';
		    		previewinfo += '<a href="'+dl+'" id="dl">下载地址：'+originfilename+'</a></td>'
		    	}else if($.inArray(ext, audioExt)>-1){
		    		previewinfo += '<td><audio controls="controls" controlsList="nodownload" src="'+ pw +'" width="'+width+'" height="'+height+'"></audio><br>';
		    		previewinfo += '<a href="'+dl+'" id="dl">下载地址：'+originfilename+'</a></td>'
		    	}else if($.inArray(ext, videoExt)>-1){
		    		previewinfo += '<td><video controls="controls" controlsList="nodownload" preload="metadata" src="'+ pw +'" width="'+width+'" height="'+height+'"></video><br>';
		    		previewinfo += '<a href="'+dl+'" id="dl">下载地址：'+originfilename+'</a></td>'
		    	}else{
		    		previewinfo +='<td><a href="'+ pw +'" target="_blank">【'+originfilename+'】</a><br>';
		    		previewinfo += '<a href="'+dl+'" id="dl">下载地址：'+originfilename+'</a></td>'
		    	} 
		      	i++;
		         if(i >= filelen){
		        	 break;
		         }
			 }
			 previewinfo +='</tr>';
		 }	
		 previewinfo+='</table>';
		return previewinfo;	 
	};
	
	this.searchAttach = function(param){
		var falg=true;
		if(param){
			if(!param.preview_url)	param.preview_url=host+pwurl;
			if(!param.businessNo)	falg=false;
			if(!param.file_app_id)	falg=false;
			if(!param.divId)		falg=false;
		}else{
			falg=false;
		}
		if(!falg){
			console.log('error'); console.log('请确认传入参数是否正确'); 
			return;
		}
		$("#"+param.divId).html("");
		$.ajax({
			   type: "post",
			   url: param.preview_url,
			   data : {
				   businessNo : param.businessNo,
				   file_app_id : param.file_app_id
			   },
			   success: function(data){
				   if (data && data.errorCode == 0) {
					   var info=previewData(data.data);
					   $("#"+param.divId).append(info);
				   }else {
					   console.log(data.msg + '('+ data.errorCode + ')');
				   }
			   }
		 });
	};	
	//文件预览()
	this.previewFile = function(fileData,param){
		var width=400;
		var height=300;
		var column=3;
		 if(param){
			 if(param.width) width=param.width;
			 if(param.height) height=param.height;
			 if(param.column) column=param.column;
		 }
		 var previewinfo='';
		 var imageExt = [".gif",".jpg",".png","jpeg"];//图片文件的后缀名
		 var audioExt = [".mp3"];//音频文件的后缀名
		 var videoExt = [".mp4"];//视频文件的后缀名
		 
		 var i = 0;
		 var column = column;
		 var filelen=fileData.length;
		 var rows=Math.ceil(filelen/column);//行数
		 previewinfo+="<table border='0' bordercolor='#000' cellspacing='10'>";
		 for(var r=0; r<rows; r++) {
			 previewinfo +='<tr>'
			 for(var c=0; c<column; c++) {
		        //文件名称
		        originfilename = fileData[i].file_name;
		        //预览地址
		        pw = fileData[i].file_attach_url;	
		        //下载地址
		        var thost = pw.split('/fsm/api/')[0];
		        dl = thost + "/fsm/api/fsm_api/download.do?file_app_id=" + $this.GetQueryString(pw,'file_app_id') + "&file_serial_no=" + $this.GetQueryString(pw,'file_serial_no');
		        
		        //获取文件名后缀名
		      	var spot = originfilename.lastIndexOf(".");
		      	if(spot > -1) ext = originfilename.substring(spot);       	
		      	if($.inArray(ext, imageExt)>-1){
		    		previewinfo += '<td><a href="'+pw+'" target="_blank"><img width="'+width+'" height="'+height+'" id="pw" src="'+pw+'"/></a><br>';
		    		previewinfo += '<a href="'+dl+'" id="dl">下载地址：'+originfilename+'</a></td>'
		    	}else if($.inArray(ext, audioExt)>-1){
		    		previewinfo += '<td><audio controls="controls" controlsList="nodownload" src="'+ pw +'" width="'+width+'" height="'+height+'"></audio><br>';
		    		previewinfo += '<a href="'+dl+'" id="dl">下载地址：'+originfilename+'</a></td>'
		    	}else if($.inArray(ext, videoExt)>-1){
		    		previewinfo += '<td><video controls="controls" controlsList="nodownload" preload="metadata" src="'+ pw +'" width="'+width+'" height="'+height+'"></video><br>';
		    		previewinfo += '<a href="'+dl+'" id="dl">下载地址：'+originfilename+'</a></td>'
		    	}else{
		    		previewinfo +='<td><a href="'+ pw +'" target="_blank">【'+originfilename+'】</a><br>';
		    		previewinfo += '<a href="'+dl+'" id="dl">下载地址：'+originfilename+'</a></td>'
		    	}  
		      	i++;
		         if(i >= filelen){
		        	 break;
		         }
			 }
			 previewinfo +='</tr>';
		 }	
		 previewinfo+='</table>';
		return previewinfo;	
	}
	
	this.GetQueryString = function(url,name){
	　　　　var arrObj = url.split("?");
　　　　if (arrObj.length > 1) {
　　　　　　var arrPara = arrObj[1].split("&");
　　　　　　var arr;
　　　　　　for (var i = 0; i < arrPara.length; i++) {
　　　　　　　　arr = arrPara[i].split("=");
　　　　　　　　if (arr != null && arr[0] == name) {
			console.log(arr[1]);
　　　　　　　　　　return arr[1];
　　　　　　　　}
　　　　　　}
　　　　　　return "";
　　　　}
　　　　else {
　　　　　　return "";
　　　　}
	}
}
