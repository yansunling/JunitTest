let formTemplate = '#form_tmsp_data';
//--------------------- 代码不变区
let callUrl;
let tableId;
let pageId;
let mode='add';
function init(){


	let urlParam = $$.getParameters();
	let techParam = urlParam.technical;
	pageId=techParam.srcPageId;
	tableId=techParam.srcTableId;
	callUrl = techParam.refActionUrl + '?actionId=' + techParam.refActionId;
	$("#vehicle_law_id").queryCombobox({
		onHidePanel:function () {
			let data = $("#vehicle_law_id").queryCombobox("getData");
			let value = $("#vehicle_law_id").queryCombobox("getValue");
			for(let i=0;i<data.length;i++){
				if(data[i].keyname==value){
					$(formTemplate).form("load",data[i]);
					break;
				}
			}
		}
	});

	let row = techParam.row;
	if(row){
		row=JSON.parse(decodeURIComponent(row));
		$(formTemplate).form("load",row);
		mode='edit';
	}else{
		$('#create_date').datebox('setValue', new Date().toLocaleDateString());
	}
	//保存按钮单击事件
	$('#btn_save').bind('click', function() {
		submitData();	
	});
	//取消按钮单击事件
	$('#btn_cancel').bind('click', function(){
		$$.closeJcdfDialog();
	});
};

//提交数据
function submitData(){
	if (!$(formTemplate).form('validate')) {
		return false;
	}
	var jsonData = $$.serializeToJson(formTemplate);
	if(!jsonData) return false;
	//附件上传
	let fileId="file_attachment";
	let file = document.getElementById(fileId).files[0];
	if(!file&&mode=='add'){
		$$.showJcdfMessager('提示消息', '请上传附件', 'info');
		return;
	}
	//如果数据验证通过(即数据合法)
	$$.openProcessingDialog();
	if(file){
		let up = new uploader(document.getElementById(fileId), {appId: "tmsp", multiple: false});
		up.singleSend(file);
		let load=up.data.data[0].file_serial_no;
		jsonData[fileId]=up.data.data[0].file_serial_no;
	}
	//ajax提交数据
	$.ajax({
		type : 'POST',
		url : callUrl,
		dataType : 'json',
		data : JSON.stringify(jsonData),
		contentType: 'application/json',
		success : function(data) {
			$$.closeProcessingDialog();
			if (data && data.errorCode==0) {
				$$.showJcdfMessager('提示消息', '操作成功', 'info');
				$$.closeJcdfDialog();
				window.top.frames['frame_'+pageId].$$.refreshJcdfDatagrid(pageId,tableId,null);
			} else {
				$$.showJcdfMessager('提示消息', data.msg+'('+data.errorCode+')', 'warning');
			}
		}
	});
};
