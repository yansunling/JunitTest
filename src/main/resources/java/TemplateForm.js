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

	let row = techParam.row;
	if(row){
		row=JSON.parse(decodeURIComponent(row));
		$(formTemplate).form("load",row);
		mode='edit';
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
	//如果数据验证通过(即数据合法)
	$$.openProcessingDialog();
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
