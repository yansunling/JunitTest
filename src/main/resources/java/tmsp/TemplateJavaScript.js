//-------------------- 代码可变区
//---------- 数据定义区
var authActions = {};
var gridMenuId = '#tmsp_base_table_list_menu';
//当前用户需要特殊处理的按钮，不在menubar上显示的
const queryLog=new queryNameSpace();
const query=new queryNameSpace();
var metaData = {
		objectId:'{js_name}_list',
		objectName:'{table_comment}',
		objectKeys:['serial_no'],
		queryId:"{table_name}_list",
		listTemplate:"tmsp_base_table_list",
		actionForms:{

			},
		expellActions:{
			"{js_name}_searchData":""
			}

};


var bda_data_str_field = {
	{js_name}_exportData : function (buttonId,actionUrl){
		query.downQuery();
	},
	{js_name}_addData: function (buttonId, actionUrl) {
			let formUrl='ui/view/{html_group}/{js_name}_form.html?actionId={js_name}_form';
			let techParam = {
				appId: appId,
				srcPageId: metaData.objectId,
				srcTableId: metaData.listTemplate
			};
			techParam.actionId = buttonId;
			techParam.mode = 'add';
			techParam.refActionUrl = actionUrl;
			techParam.refActionId = buttonId;
			var callUrl = $$.buildPageUrl(formUrl, techParam, null);
			$$.openJcdfDialog(callUrl, '新增' + metaData.objectName, 400, 1040);
	},
	{js_name}_updateData : function (buttonId,actionUrl){
		let selectRows = $("#"+metaData.listTemplate).datagrid('getChecked');
		if(selectRows.length==0){
			$$.showJcdfMessager('提示消息',  "请选择一条记录", 'info');
			return;
		}
		let oaStatus=selectRows[0].oa_status;
		if(oaStatus!='待申请'){
			$$.showJcdfMessager('提示消息',  "当前OA流程状态不为待申请，无法进行修改！", 'info');
			return;
		}
		let formUrl='ui/view/{html_group}/{js_name}_form.html?actionId={js_name}_form';
		let techParam = {
			appId: appId,
			srcPageId: metaData.objectId,
			srcTableId: metaData.listTemplate,
			row:encodeURIComponent(JSON.stringify(selectRows[0]))
		};
		techParam.actionId = buttonId;
		techParam.mode = 'update';
		techParam.refActionUrl = actionUrl;
		techParam.refActionId = buttonId;
		let callUrl = $$.buildPageUrl(formUrl, techParam, null);
		$$.openJcdfDialog(callUrl, '修改' + metaData.objectName, 400, 1040);

	},
	{js_name}_deleteData: function (buttonId,actionUrl){
		let selectRows = $("#"+metaData.listTemplate).datagrid('getChecked');
		if(selectRows.length==0){
			$$.showJcdfMessager('提示消息',  "请选择一条记录", 'info');
			return;
		}
		let oaStatus=selectRows[0].oa_status;
		if(oaStatus!='待申请'){
			$$.showJcdfMessager('提示消息',  "当前OA流程状态不为待申请，无法进行修改！", 'info');
			return;
		}
		let title = "确认";
		let msg = "确定删除所选记录?";

		$.messager.confirm(title, msg, function (r) {
			if (r) {
				$$.openProcessingDialog();
				$.ajax({
					type: "POST",
					url: actionUrl + "?actionId=" + buttonId,
					dataType: "json",
					data: JSON.stringify({"serial_no":selectRows[0].serial_no}),
					contentType: "application/json",
					success: function (data) {
						$$.closeProcessingDialog();
						if (data && data.errorCode == 0) {
							$$.showJcdfMessager('提示消息', '操作成功', 'info');
							$$.refreshJcdfDatagrid(metaData.objectId,metaData.listTemplate);
						} else {
							$$.showJcdfMessager('提示消息', data.msg, 'warning');
						}
					}
				});
			}
		});
	},
	{js_name}_enableData: function (buttonId,actionUrl){
		let selectRows = $("#"+metaData.listTemplate).datagrid('getChecked');
		if(selectRows.length==0){
			$$.showJcdfMessager('提示消息',  "请选择一条记录", 'info');
			return;
		}
		let usageStatusName = selectRows[0].usage_status_name;
		if(usageStatusName!='禁用'){
			$$.showJcdfMessager('提示消息',  "当前使用状态不为禁用，无法进行启用操作！", 'info');
			return;
		}
		let title = "确认";
		let msg = "确定启用所选记录?";

		$.messager.confirm(title, msg, function (r) {
			if (r) {
				$$.openProcessingDialog();
				$.ajax({
					type: "POST",
					url: actionUrl + "?actionId=" + buttonId,
					dataType: "json",
					data: JSON.stringify({"serial_no":selectRows[0].serial_no,"usage_status":"1"}),
					contentType: "application/json",
					success: function (data) {
						$$.closeProcessingDialog();
						if (data && data.errorCode == 0) {
							$$.showJcdfMessager('提示消息', '操作成功', 'info');
							$$.refreshJcdfDatagrid(metaData.objectId,metaData.listTemplate);
							$$.refreshJcdfDatagrid(metaData.objectId,"tmsp_share_data_log_good_list");
						} else {
							$$.showJcdfMessager('提示消息', data.msg, 'warning');
						}
					}
				});
			}
		});
	},
	{js_name}_disableData: function (buttonId,actionUrl){
		let selectRows = $("#"+metaData.listTemplate).datagrid('getChecked');
		if(selectRows.length==0){
			$$.showJcdfMessager('提示消息',  "请选择一条记录", 'info');
			return;
		}
		let usageStatusName = selectRows[0].usage_status_name;
		if(usageStatusName!='启用'){
			$$.showJcdfMessager('提示消息',  "当前使用状态不为启用，无法进行禁用操作！", 'info');
			return;
		}
		let title = "确认";
		let msg = "确定禁用所选记录?";
		$.messager.confirm(title, msg, function (r) {
			if (r) {
				$$.openProcessingDialog();
				$.ajax({
					type: "POST",
					url: actionUrl + "?actionId=" + buttonId,
					dataType: "json",
					data: JSON.stringify({"serial_no":selectRows[0].serial_no,"usage_status":"2"}),
					contentType: "application/json",
					success: function (data) {
						$$.closeProcessingDialog();
						if (data && data.errorCode == 0) {
							$$.showJcdfMessager('提示消息', '操作成功', 'info');
							$$.refreshJcdfDatagrid(metaData.objectId,metaData.listTemplate);
							$$.refreshJcdfDatagrid(metaData.objectId,"tmsp_share_data_log_good_list");
						} else {
							$$.showJcdfMessager('提示消息', data.msg, 'warning');
						}
					}
				});
			}
		});
	},
	{js_name}_applyData : function (buttonId,actionUrl){
		let selectRows = $("#"+metaData.listTemplate).datagrid('getChecked');
		if(selectRows.length==0){
			$$.showJcdfMessager('提示消息',  "请选择一条记录", 'info');
			return;
		}
		let belong_departs=[];
		let expireList=[];
		let applyList=[];
		let serialList=[];
		let total=0;
		selectRows.forEach(row=>{
			//判断是否是同一个归属部门
			if(belong_departs.indexOf(row.belong_depart)<0){
				belong_departs.push(row.belong_depart);
			}
			//保险状态不对
			if(row.insurance_status!='生效中'&&row.insurance_status!='待生效'){
				expireList.push(row.serial_no)
			}
			//oa申请状态
			if(row.oa_insurance_status!='待申请'){
				applyList.push(row.serial_no);
			}
			serialList.push(row.serial_no);
			total+=Number(row.apply_amount);
		});
		if(belong_departs.length>1){
			$$.showJcdfMessager('提示消息',  "存在不同的归属单位，无法进行借支操作！", 'info');
			return;
		}
		if(expireList.length>0){
			$$.showJcdfMessager('提示消息', expireList.join(",")+"保险状态不为待生效/生效中，无法进行借支申请！", 'info');
			return;
		}
		if(applyList.length>0){
			$$.showJcdfMessager('提示消息', applyList.join(",")+"OA流程状态不为待申请，无法进行借支申请！", 'info');
			return;
		}
		//请求参数
		let requestData={"serial_no":serialList.join(","),"apply_amount":total};
		let formUrl='ui/view/ownCar/tmsp_own_vehicle_insurance_borrow.html?actionId=tmsp_own_vehicle_insurance_borrow';
		let techParam = {
			appId: appId,
			srcPageId: metaData.objectId,
			srcTableId: metaData.listTemplate,
			row:encodeURIComponent(JSON.stringify(requestData))
		};
		techParam.actionId = buttonId;
		techParam.mode = 'update';
		techParam.refActionUrl = actionUrl;
		techParam.refActionId = buttonId;
		let callUrl = $$.buildPageUrl(formUrl, techParam, null);
		$$.openJcdfDialog(callUrl, '借支申请', 600, 740);

	},
	{js_name}_repayData : function (buttonId,actionUrl){
		let selectRows = $("#"+metaData.listTemplate).datagrid('getChecked');
		if(selectRows.length==0){
			$$.showJcdfMessager('提示消息',  "请选择一条记录", 'info');
			return;
		}
		let applyList=[];
		let serialList=[];
		selectRows.forEach(row=>{
			//oa申请状态
			if(row.oa_status!='待申请'){
				applyList.push(row.serial_no);
			}
			serialList.push({"serial_no":row.serial_no,"org_id":row.org_id,"apply_amount":row.apply_amount});

		});
		if(applyList.length>0){
			$$.showJcdfMessager('提示消息', "数据流水号"+applyList.join(",")+"OA流程状态不为待申请，无法进行付款申请！", 'info');
			return;
		}
		//请求参数
		let formUrl='ui/view/ownCar/tmsp_own_vehicle_common_repay.html?actionId=tmsp_own_vehicle_common_repay';
		let techParam = {
			appId: appId,
			srcPageId: metaData.objectId,
			srcTableId: metaData.listTemplate,
			row:encodeURIComponent(JSON.stringify(serialList))
		};
		techParam.actionId = buttonId;
		techParam.mode = 'update';
		techParam.refActionUrl = actionUrl;
		techParam.refActionId = buttonId;
		let callUrl = $$.buildPageUrl(formUrl, techParam, null);
		$$.openJcdfDialog(callUrl, '还借支申请', 650, 840);
	},
	{js_name}_payData : function (buttonId,actionUrl){
		let selectRows = $("#"+metaData.listTemplate).datagrid('getChecked');
		if(selectRows.length==0){
			$$.showJcdfMessager('提示消息',  "请选择一条记录", 'info');
			return;
		}
		let expireList=[];
		let applyList=[];
		let serialList=[];
		let total=0;
		selectRows.forEach(row=>{
			//oa申请状态
			if(row.oa_status!='待申请'){
				applyList.push(row.serial_no);
			}
			//oa申请状态
			if(row.is_cash_name!='现金'){
				expireList.push(row.serial_no);
			}

			serialList.push(row.serial_no);
			total+=Number(row.apply_amount);
		});
		if(expireList.length>0){
			$$.showJcdfMessager('提示消息', "数据流水号"+expireList.join(",")+"的支付类型不为现金，无法进行申请付款，请核实后进行操作！", 'info');
			return;
		}
		if(applyList.length>0){
			$$.showJcdfMessager('提示消息', "数据流水号"+applyList.join(",")+"OA流程状态不为待申请，无法进行付款申请！", 'info');
			return;
		}
		//请求参数
		let requestData={"serial_no":serialList.join(","),"apply_amount":total};
		let formUrl='ui/view/ownCar/tmsp_own_vehicle_insurance_payment.html?actionId=tmsp_own_vehicle_insurance_payment';
		let techParam = {
			appId: appId,
			srcPageId: metaData.objectId,
			srcTableId: metaData.listTemplate,
			row:encodeURIComponent(JSON.stringify(requestData))
		};
		techParam.actionId = buttonId;
		techParam.mode = 'update';
		techParam.refActionUrl = actionUrl;
		techParam.refActionId = buttonId;
		let callUrl = $$.buildPageUrl(formUrl, techParam, null);
		$$.openJcdfDialog(callUrl, '付款申请', 600, 740);
	},
	{js_name}_importData: function (buttonId, actionUrl) {
		var form = {};
		form.formId = "cip_import_form";
		form.formUrl = "ui/view/public/cip_import_form.html?actionId=cip_import_form";

		var techParam = {
			appId: appId,
			srcPageId: metaData.objectId,
			srcTableId: metaData.listTemplate
		};
		techParam.actionId = form.formId;
		techParam.templateName = metaData.objectId + '.xlsx';//需要优化
		techParam.objectName = metaData.objectName;
		techParam.refActionUrl = actionUrl;
		techParam.refActionId = buttonId;
		var callUrl = $$.buildPageUrl(form.formUrl, techParam, null);
		$$.openJcdfDialog(callUrl, '导入' + metaData.objectName, 250, 600);
	},




};




///////////--------------------- 代码区；

function init(){
	queryData();
	getAuthButtons(metaData.objectId,gridMenuId,authActions,metaData.expellActions);
};

//////--------------------------- 函数区
function doAction(buttonId){
	var actionUrl = authActions[buttonId];
	if(actionUrl){
		if(bda_data_str_field[buttonId])
			bda_data_str_field[buttonId](buttonId,appId+actionUrl);
		else {
			//调用通用Action方法，如果需要调用特定表单，需要自己增强函数体
			alert('尚未定义按钮的响应方法:'+buttonId);
		}
	}
	else {
		alert('当前用户没有该操作授权:'+buttonId);
	}
};

var downUrl = "/../fsm/api/fsm_api/download.do?file_app_id=tmsp&file_serial_no=";
function queryData() {
	query.init(metaData.queryId, metaData.listTemplate, gridMenuId,true);
	//初始化日志
	queryLog.disSearchQuery();//不生成查询条件
	queryLog.init("tmsp_oil_operate_log","tmsp_share_data_log_good_list","tmsp_share_data_log_list_menu");
	//设置单击事件
	query.setClickRowFunction(function(rowIndex, rowData){
		//设置查询参数
		queryLog.setParamFunction(function(params){
			params.busi_doc_id=rowData.serial_no;
			return params;
		});

		queryLog.queryResult();
	});
	//保险附件
	query.getColumnOption("attachment_url").formatter=function (value, row, index) {
		if(value) {
			return "<a href='" + downUrl + value + "'  style='cursor: pointer;text-decoration:none' width='30px' height='30px' for='im' >下载</a>";
		}
	};
}


