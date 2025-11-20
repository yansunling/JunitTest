jcdfObject = function() {
	/**
	 * 以下m和s json对象变量提前定义，服务于方法stringify
	 */
	var m = {'\b': '\\b','\t': '\\t','\n': '\\n','\f': '\\f','\r': '\\r','"' : '\\"','\\': '\\\\'};
    var s = {'boolean': function (x) {
                return String(x);
            },
            number: function (x) {
                return isFinite(x) ? String(x) : 'null';
            },
            string: function (x) {
                if (/["\\\x00-\x1f"]/.test(x)) {
                    x = x.replace(/(["\x00-\x1f\\"])/g, function(a, b) {
                        var c = m[b];
                        if (c) {
                            return c;
                        }
                        c = b.charCodeAt();
                        return '\\u00' + Math.floor(c / 16).toString(16) + (c % 16).toString(16);
                    });
                }
                return '"' + x + '"';
            },
            object: function (x) {
                if (x) {
                    var a = [], b, f, i, l, v;
                    if (x instanceof Array) {
                        a[0] = '[';
                        l = x.length;
                        for (i = 0; i < l; i += 1) {
                            v = x[i];
                            f = s[typeof v];
                            if (f) {
                                v = f(v);
                                if (typeof v == 'string') {
                                    if (b) {
                                        a[a.length] = ',';
                                    }
                                    a[a.length] = v;
                                    b = true;
                                }
                            }
                        }
                        a[a.length] = ']';
                    } else if (x instanceof Object) {
                        a[0] = '{';
                        for (i in x) {
                            v = x[i];
                            f = s[typeof v];
                            if (f) {
                                v = f(v);
                                if (typeof v == 'string') {
                                    if (b) {
                                        a[a.length] = ',';
                                    }
                                    a.push(s.string(i), ':', v);
                                    b = true;
                                }
                            }
                        }
                        a[a.length] = '}';
                    } else {
                        return;
                    }
                    return a.join('');
                }
                return 'null';
            }
        };
    return {
    	/**
		 * 将指定的json对象解析成字符串
		 * 
		 * @param v
		 *            将要被解析的json对象
		 * 
		 * @return json解析后的字符串
		 */
    	stringify:function (v) {
	         var f = s[typeof v];
	         if (f) {
	             v = f(v);
	             if (typeof v == 'string') {
	                 return v;
	             }
	         }
	         return null;
	    },
	    /**
		 * 获取字符串的真实长度(一个中文占两个长度，英文或者数字占一个长度)
		 * 
		 * @param str
		 *            获取实际长度的目标字符串
		 * 
		 * @return 目标字符串的实际长度
		 */
	    getTrueLenth:function(str) {
	    	return str.replace(/[^\x00-\xff]/g,"xx").length;
	    },
	    /**
		 * 重置指定表单中的内容
		 * 
		 * @param formId
		 *            将要清空内容的表单的id
		 * @param isClearHiddenInput
		 *            是否清空hidden隐藏域中的值，true：清空，false：不清空，不指定默认不清空
		 * 
		 * @return undefined
		 */
	    resetContent:function(formId, isClearHiddenInput) {
	    	var clearForm = document.getElementById(formId);
			if (clearForm)clearForm.reset();
			if (isClearHiddenInput)$("#"+formId+" :input[type='hidden']").val("");
	    },
	    /**
		 * 取消选择(用于Easyui中的dataGrid)，囊括了easyui中的clearSelections和uncheckAll
		 * 为减少实际开发的代码，和后期维护的统一管理，建议采用该方法，而不直接操作easyui的对应方法
		 * 
		 * @param dataTableId
		 *            将要取消所选数据记录的目标datagrid id
		 * 
		 * @return undefined
		 */
	    clearSelect:function(dataTableId) {
	    	$("#"+dataTableId).datagrid('clearSelections');
			$("#"+dataTableId).datagrid('uncheckAll');
	    },
	    /**
		 * 自适应表格的宽度处理(适用于Jquery Easy Ui中的dataGrid), 可以实现列表的各列宽度跟着浏览宽度的变化而变化。
		 * 
		 * @param percent
		 *            当前列的列宽所占整个窗口宽度的百分比(以小数形式出现，如0.3代表30%)
		 * @param bodyWidth
		 *            总宽度，不提供，则默认采用当前页面宽度
		 * 
		 * @return 通过当前窗口和对应的百分比计算出来的具体宽度值
		 */
	    fillsize:function(percent,bodyWidth) {
	    	if(!bodyWidth) {
				bodyWidth = $(document).width();
			} else {
				bodyWidth = bodyWidth;
			}
			return parseInt(bodyWidth*percent);
	    },
	    /**
		 * 获取数据列表的总宽度
		 * 
		 * @param minWidth
		 *            指定最小宽度，确保最后返回的结果一定大于等于该值，不指定的不考虑
		 * 
		 * @return 如果计算出的当前窗口总宽度小于minWidth，则返回minWidth，否则返回当前窗口总宽度
		 */
	    getDatagridWidth:function(minWidth){
	    	if(!minWidth)minWidth = 0;
			var width = ($(document).width()-1);
			return (minWidth > width) ? minWidth:width;
	    },
	    /**
		 * 获取数据列表的高度
		 * 
		 * @return 当前窗口的高度
		 */
	    getDatagridHeight:function(){
	    	return ($(document).height());
	    },
	    // 这里你可以自己做调整
	    fixWidth:function(percent){  
	        return document.body.clientWidth * percent ;
	    },
	    // 这里你可以自己做调整
	    fixHeight:function(percent){  
	        return document.body.clientHeight * percent ;
	    },
	    /**
		 * 获取所选记录的id
		 * 
		 * @param dataTableId
		 *            目标记录所在的列表table的id
		 * @param errorMessage
		 *            错误的提示信息
		 * @param type
		 *            getChecked或者getSelections，默认getChecked
		 * 
		 * @return 所选记录js对象
		 */
	    getSingleSelectRow:function(dataTableId, errorMessage, type){
	    	if (!type)type = "getChecked";
			var rows = $('#'+dataTableId).datagrid(type);
			if(rows && 1 == rows.length){
				return rows[0];
			}else{
				this.showJcdfMessager('提示消息',errorMessage,'warning');
				return null;
			}
	    },
		/**
		 * 获取多条选中记录
		 *
		 * @param dataTableId
		 *            目标记录所在的列表table的id
		 * @param errorMessage
		 *            错误的提示信息
		 * @param type
		 *            getChecked或者getSelections，默认getChecked
		 *
		 * @return 所选记录js对象
		 */
		getSelectRows:function(dataTableId, errorMessage, type){
			if (!type)type = "getChecked";
			var rows = $('#'+dataTableId).datagrid(type);
			if(rows && rows.length > 0){
				return rows;
			}else{
				this.showJcdfMessager('提示消息',errorMessage,'warning');
				return null;
			}
		},
	    /**
		 * 获取所选记录的id,多个id用逗号分隔
		 * 
		 * @param dataTableId
		 *            目标记录所在的列表table的id
		 * @param idField
		 *            主键字段名
		 * @param noOneSelectMessage
		 *            如果没有选择记录，或者指定必需选择一条而选择了多条时的提示信息
		 * @param type
		 *            getChecked或者getSelections，默认getChecked
		 * 
		 * @return 所选记录的id字符串(多个id用逗号隔开)和所选记录数的json对象,如果没有选择记录则提示消息，并返回null
		 */
	    getSelectIds:function(dataTableId, noOneSelectMessage, idField, type) {
	    	if (!type)type = "getChecked"; 
	    	var rows = $('#'+dataTableId).datagrid('getChecked');
			var num = rows.length;
			var ids = null;
			if(num  < 1){
				if (noOneSelectMessage)this.showJcdfMessager('提示消息',noOneSelectMessage,'warning');
				return null;
			}else{
				for(var i = 0; i < num; i=i+1){
					if(null == ids || i == 0){
						ids = rows[i][idField];
					} else {
						ids = ids + "," + rows[i][idField];
					}
				}
				return {"ids":ids,"num":num};
			}
	    },
	    /**
		 * 打开系统操作进度条窗口
		 * 
		 * @param processingMessage
		 *            显示在操作进度条窗口上的提示信息,不提供则使用默认值
		 * @param closable
		 *            true：窗口可以关闭，false：窗口不可以关闭
		 * @param modal
		 *            true:有模式窗口，false：无模式窗口，不指定默认为false
		 * 
		 * @return undefined
		 */
	    openProcessingDialog:function(processingMessage, closable, modal) {
	    	if (!closable)closable=false;
			if (!modal)modal=true;
			// 如果提供了进度条信息，则采用提供的，否则采用默认的！
			if (!processingMessage)processingMessage="正在处理，请稍候......";
			var processingDialogObject = document.getElementById("processingDialog");
			// 如果不存在处理进度条窗口则创建
			if(!$("#processingDialog") || $("#processingDialog").length <= 0) {
				var html = '<div id="processingDialog" modal="'+modal+'" closable="'+closable+'" icon="icon-save" style="overflow-y:hidden;padding:5px;width:350px;height:100px;text-align:center">'
								+'<div id="processingDiv"><table><tr><td><img src="../../img/loading.gif"/></td><td><font color="green" id="processingMessage"></font></td></tr></table></div>'
						+'</div>';
				$("body").append(html);
			}
			$("#processingMessage").html(processingMessage);
			$("#processingDialog").dialog({
				title:"操作中...",
				onOpen:function() {
					$('#processingDialog').css('display','');
				}		
			});
	    },
	    /**
		 * 关闭系统操作进度条窗口
		 * 
		 * @return undefined
		 */
	    closeProcessingDialog:function() {
	    	$('#processingDialog').dialog('close');
	    },
	    /**
		 * 自动调整弹出窗口的长和宽,以适应不同分辨率浏览器下的显示
		 * 
		 * @param dialogId
		 *            弹出窗口的id
		 * @param widthRate
		 *            弹出窗口的宽度与浏览器所能提供的宽度(通常为弹出窗口所子页面对应的Iframe的宽度)的比例,在不同分辨率下使用该比率自动调整
		 * @param maxHeight
		 *            弹出窗口的最大高度
		 * @param maxWidth
		 *            弹出窗口的最大宽度
		 * 
		 * @param 自动调节后的窗口宽度
		 */
	    fillDialogWidthAndHeight:function(dialogId, widthRate, maxHeight, maxWidth) {
	    	var currentBodyHeight = $(document).height();
			var currentBodyWidth = $(document).width();
			// 当前iframe窗口的高宽比
			var heightToWidthRate =  currentBodyHeight/currentBodyWidth;
			var fillWidth = currentBodyWidth*widthRate;
			var fillHeight = fillWidth * heightToWidthRate;
			// 如果当前iframe窗口按百分比计算出的宽度大于实际设置的最大值，则以最大值为准
			if (fillWidth >= maxWidth) {
				fillWidth = maxWidth;
			// 如果计算出的宽度小于最大值则进一步调整
			} else {
				// 如果当前窗口的宽度的95%大于设置的最大值，则自动调整到最大值，否则就取当前窗口的95%作为宽度
				if ((currentBodyWidth * (95/100)) > maxWidth) {
					fillWidth = maxWidth;
				} else {
					fillWidth = currentBodyWidth * (95/100);
				}
			}
			// 如果当前iframe窗口按百分比计算出的高度值大于实际设置的最大值，则以最大值为准，否则进一步调整的方法与以上的宽度调整相同
			if (fillHeight >= maxHeight) {
				fillHeight = maxHeight;
			}
			// 计算窗口左上角的坐标，使窗口居中
			var leftPos = (currentBodyWidth - fillWidth)/2;
			var topPos = (currentBodyHeight - fillHeight)/2;
			$('#'+dialogId).dialog("options").width=fillWidth;
			$('#'+dialogId).dialog("options").height=fillHeight;
			$('#'+dialogId).dialog("resize",{width:fillWidth,height:fillHeight,left:leftPos,top:topPos});
			return fillWidth;
	    },
	    /**
		 * 日期格式化函数，支持模式：YYYY/yyyy年MM月dd日hh小时mm分ss秒SSS毫秒
		 * 
		 * @param date
		 *            日期，不指定，则取当前时间
		 * @param format
		 *            格式模式字符串
		 * 
		 * @reurn 格式化后的日期字符串
		 */
	    dateFormat:function(dateObject, format) {
	    	if (!dateObject)dateObject = new Date();
	    	var o = { 
				"M+" : dateObject.getMonth()+1, // month
				"d+" : dateObject.getDate(), // day
				"h+" : dateObject.getHours(), // hour
				"m+" : dateObject.getMinutes(), // minute
				"s+" : dateObject.getSeconds(), // second
				"q+" : Math.floor((dateObject.getMonth()+3)/3), // quarter
				"S" : dateObject.getMilliseconds() // millisecond
			} 
			if(/(y+)/.test(format)) { 
				format = format.replace(RegExp.$1, (dateObject.getFullYear()+"").substr(4 - RegExp.$1.length)); 
			} 
			for(var k in o) { 
				if(new RegExp("("+ k +")").test(format)) { 
					format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
				} 
			} 
			return format; 
	    },
	    /**
		 * 格式化表单内容为json字符串
		 * 
		 * @param selector
		 *            容器筛选器
		 * @param notEmptyField
		 *            true：过滤掉内容为空的字段，否则空字段保留
		 * 
		 * @return 表单内容格式化后的json对象
		 */
	    serializeToJson:function(selector, notEmptyField) {
	    	var obj = {};
		    $.each( $(selector).serializeArray(), function(i,o){
		         var n = o.name, v = isNaN(o.value) === true ? o.value:$.trim(o.value);
		        if (!(notEmptyField && "" == v)) {
		        	obj[n] = (obj[n] === undefined) ? v : $.isArray(obj[n]) ? obj[n].concat(v) : [obj[n], v];
		        }
		    });
		    return obj;
	    },
	    /**
		 * 将json中的值填充到页面中，按field域进行填充
		 * 
		 * @param selector
		 *            容器筛选器
		 * @param jsonObject
		 *            用于填充的json数据对象
		 * @param type
		 *            填充数据的方式，text：将数据填充到text域中，否则填充到value域中
		 * 
		 * @return undefined
		 */
	    serializeToForm:function(selector, jsonObject, type) {
	    	if(!jsonObject)return false;
		     for(var key in jsonObject) {
		     	var inputObj = $(selector).find("[field='"+key+"']");
		     	if (inputObj && inputObj.size() > 0 && null != jsonObject[key] && "null" != jsonObject[key]) {
		     		if (type && 'text'== type) {
		     			inputObj.text(jsonObject[key]);
		     		} else {
		     			inputObj.val(jsonObject[key]);
		     		}
		     	}
		     }
	    },
	   
	    /**
		 * 弹出操作窗口，框架中所有弹窗中的内容都必需是一个单独的页面，通过url指定
		 * 
		 * @param href
		 *            弹窗中将要显示的页面
		 * @param title
		 *            弹窗标题
		 * @param maxHeight
		 *            弹窗最大高度，如果超过浏览器窗口高度，则按照浏览器窗口的高宽比自动调节缩小
		 * @param maxWidht
		 *            弹窗最大宽度 如果超过浏览器窗口的宽度，则按照参数widthRate调整
		 * @param widthRate
		 *            窗口宽度与浏览器宽度百分比，框架会按照该比调整弹窗宽度到最接近maxWidht
		 * 
		 */
		openJcdfDialog:function(href, title, maxHeight, maxWidth, widthRate) {
			if (!widthRate) {
				widthRate = 0.98;
			}
			if(window.top.jcdfDialog){
				window.top.jcdfDialog(window.self.name, href, title, maxHeight, maxWidth, widthRate);
				window.top.$("#jcdfDiglogDivIframe").height(window.top.$("#jcdfDiglogDivIframe").parent().height()-10);

			}else{
				jcdfDialog(href, title, maxHeight, maxWidth, widthRate);
			}
		},
		/**
		 * 刷新数据列表，如果采用了JCDF框架的弹窗，就必需采用JCDF的该方法进行列表刷新，因为JCDF的弹窗页面
		 * 与列表页面在两个iframe页面中，无法进行js直接调用。
		 * 
		 * @param datagridId
		 *            将要属性的datagrid的id
		 */
		refreshJcdfDatagrid:function(pageId, datagridId, type) {
			//去掉#号
			datagridId=datagridId.replace("#","");
			// 如果列表页面就在当前页面，则直接刷新，否则需要跨iframe进行列表刷新
			if ($("#"+datagridId).length > 0) {
				if ("queryAll" == type) {
					$('#'+datagridId).datagrid('options').queryParams="{}";
				}
				$('#'+datagridId).datagrid('uncheckAll');
				$('#'+datagridId).datagrid('unselectAll');
				$('#'+datagridId).datagrid('reload');
			} else {
				try{
					window.top.frames[pageId].$$.refreshJcdfDatagrid(null,datagridId,type);
				}catch(e){}
				/*try{//这个方法不会被调用到
					window.frames[pageId].$$.flashTable(datagridId);
				}catch(e){}*/
			}
		},
		/**
		 * 窗口关闭，如果采用了JCDF框架的弹窗，就必须采用JCDF的该关闭方法
		 */
		closeJcdfDialog:function() {
			window.top.closeJcdfDialog();
		},
		/**
		 * 消息提示框，相关参数与Easyui的“$.messager.alert”保持一致，具体含义参考 Easyui的官方文档。
		 * 需要注意的是：这里的fn只能穿一个方法的名称和实参(如果有)，不允许包含方法的定义部分，
		 * 所有实际使用时，先定义好回调方法，然后这里直接传入方法名与实参的字符串表示，例如：
		 * 'deleteNoteById("'+deleteNotes.ids+'")'
		 */
		showJcdfMessager:function(title, msg, icon, fn) {
			window.top.showJcdfMessager(window.self.name, title, msg, icon, fn);
		},
		/**
		 * 操作确认框，相关参数与Easyui的“$.messager.confirm”保持一致，具体含义参考 Easyui的官方文档。
		 * fn的注意事项与showJcdfMessager一样。
		 */
		showJcdfConfirm:function(title, msg, fn, fnCanel){
			window.top.showJcdfConfirm(window.self.name, title, msg, fn, fnCanel);
		},

		// 构建url参数
		buildParameter : function(jsonObject){
			var param = "";
			if(jsonObject){
				for(var key in jsonObject){
					param += ",";
					param += key + "=" + jsonObject[key];
				}
			}
			
			return param.substr(1);
		},
		// 构建页面调用url
		buildPageUrl : function( url, technical, business ){
			if(url.indexOf('?')>=0){
				url = technical.appId + '/' + url.substr(0,url.indexOf('?')+1);
			}
			else
				url = technical.appId + '/' +  url + '?';
			
			if(technical&&technical.actionId){
				url += "actionId=" + technical.actionId + "&";
				delete technical.actionId;
			}
			else {
				throw "必须提供actionId";
			}
			
			var techParam = '(' + this.buildParameter(technical) + ')';
			if(business){
				var busiParam = '&[' + this.buildParameter(business) + ']';
				return url + techParam + busiParam;	
			}
			else {
				return url + techParam;	
			}
		},

		getParameters : function(){
			var uri = window.location.search;
			var pos = uri.indexOf('?');
			if(pos>=0){
				uri = uri.substr(pos+1);
			}

			uri = decodeURI(uri);
			var parameters = uri.split('&');
			var obj = {business:{},
					   technical:{}};
			var param;
			if(parameters){
				for(var i=0; i< parameters.length ; i++){
					if(parameters[i].charAt(0)=='[' && parameters[i].charAt(parameters[i].length-1)==']'){
						param = parameters[i].substring(1,parameters[i].length-1);
						if(param){
							var kvs = param.split(',');
							var kv;
							for(var j=0; j< kvs.length ; j++){
								kv = kvs[j].split('=');
								if(kv&&kv.length==2){
									obj.business[kv[0]]=kv[1];
								}
							}
						}
					}
					else if(parameters[i].charAt(0)=='(' && parameters[i].charAt(parameters[i].length-1)==')'){
						param = parameters[i].substring(1,parameters[i].length-1);
						if(param){
							var kvs = param.split(',');
							var kv;
							for(var j=0; j< kvs.length ; j++){
								kv = kvs[j].split('=');
								if(kv&&kv.length==2){
									obj.technical[kv[0]]=kv[1];
								}
							}
						}
					}
				}
			}
			
			return obj;
		},
		
		// 获得参数的方法
		QueryString : function(val){
			var uri = window.location.search;
			var re = new RegExp("[?&]" +val+ "=([^&?]*)", "ig");
			return ((uri.match(re))?(uri.match(re)[0].substr(val.length+2)):null);
		},
		buildQueryObject : function(jsonObject){
			var param = "";
			if(jsonObject){
				for(var key in jsonObject){
					param += "&";
					param += key + "=" + jsonObject[key];
				}
			}
			
			return param.substr(1);
		},
		openMyJcdfDialog:function (title,href , maxHeight, maxWidth, widthRate, closable) {
			if(closable==undefined){
				closable = true;
			}
			var dialogDiv = window.parent.$("#myJcdfDiglogDiv");
			if(!dialogDiv || dialogDiv.length <= 0) {
				var html = '<div id="myJcdfDiglogDiv" style="display: none;">'+
					'<iframe id="myJcdfDiglogDivIframe" name="myJcdfDiglogDivIframe" width="100%" height="98%" src="" frameborder="0" scrolling="auto" marginheight="0" marginwidth="0"></iframe>'+
					'</div>';
				window.parent.$("body").append(html);
			}
			window.parent.$("#myJcdfDiglogDivIframe").attr('src', href);
			window.parent.$('#myJcdfDiglogDiv').css('display','');
			window.parent.$('#myJcdfDiglogDiv').dialog({
				title:title,
				modal:true,
				maximizable:true,
				resizable:true,
				closable: closable,
				closed: false,
				width:maxWidth,
				height:maxHeight,
				// left:350,
				// top:200,
				onOpen:function() {
					$$.fillDialog(maxHeight,maxWidth);
				},
				onResize:function() {
					// $("#myJcdfDiglogDivIframe").height($("#myJcdfDiglogDivIframe").parent().height()-4);
				},
				onClose:function() {
					window.parent.$("#myJcdfDiglogDivIframe").attr('src', 'about:blank');
				}
			});
		},
		fillDialog:function (maxHeight,maxWidth){
			var currentBodyHeight = window.screen.availHeight-100;
			var currentBodyWidth =  window.screen.availWidth;
			var heightToWidthRate =  currentBodyHeight/currentBodyWidth;
			var fillWidth = currentBodyWidth*0.98;
			var fillHeight = fillWidth * heightToWidthRate;
			// 如果当前iframe窗口按百分比计算出的宽度大于实际设置的最大值，则以最大值为准
			if (fillWidth >= maxWidth) {
				fillWidth = maxWidth;
				// 如果计算出的宽度小于最大值则进一步调整
			} else {
				// 如果当前窗口的宽度的95%大于设置的最大值，则自动调整到最大值，否则就取当前窗口的95%作为宽度
				if ((currentBodyWidth * (95/100)) > maxWidth) {
					fillWidth = maxWidth;
				} else {
					fillWidth = currentBodyWidth * (95/100);
				}
			}
			// 如果当前iframe窗口按百分比计算出的高度值大于实际设置的最大值，则以最大值为准，否则进一步调整的方法与以上的宽度调整相同
			if (fillHeight >= maxHeight) {
				fillHeight = maxHeight;
			}
			// 计算窗口左上角的坐标，使窗口居中
			var leftPos = (currentBodyWidth - fillWidth)/2;
			var topPos = (currentBodyHeight - fillHeight)/2;
			window.parent.$("#myJcdfDiglogDiv").dialog("resize",{left:leftPos,top:topPos});
		},







	};
};

// 读取cookies
function getCookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
 
    if(arr=document.cookie.match(reg))
 
        return unescape(arr[2]);
    else
        return null;
};

/**
 * 全局监控ajax请求，处理session无效、无权限操作时，自动跳转到登录页面
 */
$(function(){
	$.ajaxSetup({
       cache : false,
       global : true,
       type: "POST",
       beforeSend: function(XMLHttpRequest) {
    	   if(this.url.indexOf("?")==-1){
        	   this.url += "?ssid=" + getCookie("cip_sessionid");
    	   }else{
        	   this.url += "&ssid=" + getCookie("cip_sessionid");
    	   }
       },
       complete: function(req, status) {
    	   if(req.readyState==4 && req.status==200){
    		   try{
    	           var reqObj = eval('('+req.responseText+")");
    	           // 如果数据请求验证时，当前登录状态已经失效或者对应的请求资源(路径)没有权限
    	           if(reqObj && (reqObj.sessionStatus == "timeout" || reqObj.sessionStatus=="err")){
    	        	   window.top.$.messager.alert('提示消息', reqObj.msg, 'warning', function(){
    	        		   forward2Login();
    	        	   });	
    	           }
    	       }catch(e){
    	    	   
    	       }
    	   }else{
			   if(req.readyState!=0){
				   $$.closeProcessingDialog();
				   if(!top.document.getElementById('newTipsStyle')){
					   var result=req.responseJSON;
					   if(!result){
						   try {
							   result=JSON.parse(req.responseText);
						   } catch (e) {
							   $$.showJcdfMessager('提示消息','异常返回信息提示出错!','warning');
							   return;
						   }
					   }
					   console.log(result);
					   $$_tips.abnormalTipsCus(result);
				   }
				   // $$.showJcdfMessager('提示消息',req.responseText,'warning');
			   }
    	   }
       },
		error: function(error){
			var result=error.responseJSON;
			if(!result){
				try {
					result=JSON.parse(error.responseText);
				} catch (e) {
					$$.showJcdfMessager('提示消息','异常返回信息提示出错!','warning');
					return;
				}
			}
			console.log(result);
			$$_tips.abnormalTipsCus(result);
		}
	});
});

function forward2Login(){
	window.location = c_url_login;
}

/**
 * 自动调节页面datagrid的宽度(当页面宽度变化时，自动调整以适应页面的变化)，当前仅支持一个页面仅有一个datagrid的应用
 * 使用方法：页面引入common.js，然后在datagrid标签上加属性：tagType="datagrid"即可
 * 
 */
var oldBodyWidth = 0;
var oldDatagridWidth = 0;
var oldPanelWidth=0;
$(function(){
	oldBodyWidth = document.body.clientWidth;
	$(window).resize(function(){
	  	$.each($("[tagType='datagrid']"), function(i, v){
	  		try {
		  		var datagridId = $(this).attr("id");
		  		if (0 == oldDatagridWidth) {
			  		oldDatagridWidth = $('#'+datagridId).datagrid('options').width;
			  	}
				$("#"+datagridId).datagrid('resize',{width:(oldDatagridWidth+(document.body.clientWidth-oldBodyWidth))});
			} catch (ex) {
				
			}
	  	});
	  	$.each($("[tagType='panel']"), function(i, v){
	  		try {
		  		var panelId = $(this).attr("id");
		  		if (0 == oldPanelWidth) {
			  		oldPanelWidth = $('#'+panelId).panel('options').width;
			  	}
				$("#"+panelId).panel('resize',{width:(oldPanelWidth+(document.body.clientWidth-oldBodyWidth))});
			} catch (ex) {
				
			}
	  	});
	});
});


var jcdf = new jcdfObject();
var $$ = jcdf;
$$.pageSize = 15;
$$.pageList = [ 15, 20, 30, 50, 100 ];

var getToday = function(){
	var newDate = new Date();
   	var today = newDate.getFullYear()+"-";
 	today += newDate.getMonth()+1+"-";
 	today += newDate.getDate();
 	return today;
};


function getString2Date(dateStr){
	var d = new Date(Date.parse(dateStr.replace(/-/g, "/"))); 
	return d.getTime();
};

function getTimestamp2String(value){
	var d = new Date(value)
	var y = d.getFullYear();
	var m = d.getMonth()+1;
	var day = d.getDate();
	var str = y + "-";
	if(m<10){
		str += "0";
	}
	str += m + "-";
	
	if( day < 10 ){
		str += "0";
	}
	str += day;
	str += " ";
	var hour = d.getHours();
	if(hour<10)
		str += "0";
	str += hour + ":";
	var minute = d.getMinutes();
	if(minute<10)
		str += "0";
	str += minute + ":";
	var second = d.getSeconds();
	if(second<10)
		str += "0";
	str += second;

	return str;
};

function getDate(){
	var d = new Date();
	var y = d.getFullYear();
	var m = d.getMonth()+1;
	var day = d.getDate();
	var str = y + "-";
	if(m<10){
		str += "0";
	}
	str += m + "-";
	
	if( day < 10 ){
		str += "0";
	}
	str += day;
	
	return str;
};

function getTimeStamp(){
	var d = new Date();
	var str = getDate();
	
	var hh = d.getHours();
	var mm = d.getMinutes();
	var ss = d.getSeconds();
	
	str += " ";
	if( hh < 10 ){
		str += "0";
	}
	str += hh + ":";
	
	if( mm < 10 ){
		str += "0";
	}
	str += mm + ":";
	
	if( ss < 10 ){
		str += "0";
	}
	str += ss;
	
	return str;
};

//获取授权controller行为信息
function getAuthButtons(pageId,toolbarId,authActions, expellActions){
	$.ajax({
		type : "POST",
		url : urlResource+pageId,
		cache : false,
		dataType : "json",
		success : function(data) {
			if (null != data && data.errorCode==0) {
				var buttons = generateButtons(data.data,pageId,authActions, expellActions);
			    var obj = $(toolbarId).append(buttons);
			    $.parser.parse(obj);
			}  else {
				$$.showJcdfMessager('提示消息', data.msg, 'warning'); 
			}
		}
	});
};

//生成前台按钮信息
function generateButtons(buttons,pageId,authActions, expellActions){
	var lis="";
	for(var i=0;i<buttons.length;i++){
		if(buttons[i].type=="B"){
			authActions[buttons[i].id] = buttons[i].url;
			if(expellActions.hasOwnProperty(buttons[i].id)){
				continue;
			}
			
			if(!buttons[i].icon){
				buttons[i].icon = 'icon-edit';
			}
			lis+= 
				"<a id='"+buttons[i].id+"' href='#' onclick=\"doAction('"+buttons[i].id+"')\" class='easyui-linkbutton' iconCls='"+buttons[i].icon+"' plain='true'>"+buttons[i].name+"</a>\n";
		}
	}
	
	return lis;
};

//获取下拉框字典信息(获取cip_admin_codes的信息）
function loadCombobox(tableId,domainId,fieldId,multipleFlag){
	if(multipleFlag==undefined){
		multipleFlag=false;
	}
	$('#'+fieldId).combobox({
		url:ddicUrl+domainId+"&tableId="+tableId,
		valueField : 'code_type',
		textField : 'code_name',
		editable: true,
		multiple:multipleFlag,
		filter : function(q, row) {
			var opts = $(this).combobox('options');
			return row[opts.textField].indexOf(q) > -1;
		},
		loadFilter: function(data){
			if (data!=null && data.errorCode>0){
				if(data.msg!=null && data.msg!=""){
					$$.showJcdfMessager('提示消息', data.msg, 'warning');
				}
			}
			return data.data;
		},
		onHidePanel : function() {
			var _options = $(this).combobox('options');
			var _data = $(this).combobox('getData');/* 下拉框所有选项 */
			var _value = $(this).combobox('getValue');/* 用户输入的值 */
			var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
			for (var i = 0; i < _data.length; i++) {
				if (_data[i][_options.valueField] == _value) {
					_b=true;
					break;
				}
			}
			if(!_b){
				$(this).combobox('clear');
			}
		}


	});
};
/**
 * 版本1:有些属性会相互冲突，在后续使用的过程中在做调整，先上这一个版本，曹启龙
 * 版本2：主要起冲突的属性是只读和必填属性,这两个属性用的也比较多，在html标签中支持的也很好，所以这两个属性用html标签控制,分别是readonly="true",data-options="required:false",曹启龙
 * @param fields
 */
function initControlSetting(fields) {
	var ctrlType;
	for (var i = 0; i < fields.length; i++) {
		ctrlType = fields[i].ctrlType;
		//(1)是否显示-ok(只是针对form的情况)
		if (fields[i].display == 'N') {
			$('#' + fields[i].field).parent().parent().css('display', 'none');
			continue;//不显示的话后面操作没有意义
		}
		//(4)默认值-ok
		if (fields[i].defaultValue != '') {
			if ('easyui-combobox' == ctrlType) {
				if (fields[i].ddicTable != '') {//查询数据字典为combobox设值
					loadCombobox(fields[i].ddicTable, fields[i].domainId, fields[i].field);
					$('#' + fields[i].field).combobox({editable: true});
				}
				$('#' + fields[i].field).combobox('setValue', fields[i].defaultValue);
			} else if ('easyui-datebox' == ctrlType) {
				$('#' + fields[i].field).datebox("setValue", fields[i].defaultValue);
			} else if ('easyui-datetimebox' == ctrlType) {
				$('#' + fields[i].field).datetimebox("setValue", fields[i].defaultValue);
			} else if ('easyui-numberbox' == ctrlType) {
				$('#' + fields[i].field).numberbox('setValue', fields[i].defaultValue);
			} else if ('easyui-validatebox' == ctrlType) {
				$('#' + fields[i].field).val(fields[i].defaultValue);
			} else {
				$('#' + fields[i].field).val(fields[i].defaultValue);
			}
		} else {
			if ('easyui-datebox' == ctrlType) {
				// $('#' + fields[i].field).datebox("setValue", getToday());
			} else if ('easyui-datetimebox' == ctrlType) {
				// $('#' + fields[i].field).datetimebox("setValue", getToday());
			} else if (fields[i].ddicTable != '') {//查询数据字典为combobox设值
				loadCombobox(fields[i].ddicTable, fields[i].domainId, fields[i].field,fields[i].multiple);
				$('#' + fields[i].field).combobox({editable: true});
			}
		}
		//(2)是否只读
		if (fields[i].readOnly == 'Y') {
			if ('easyui-combobox' == ctrlType) {
				$('#' + fields[i].field).combobox({readonly: true});
			} else if ('easyui-validatebox' == ctrlType) {
				$('#' + fields[i].field).attr('readonly', true);
			} else if ('easyui-numberbox' == ctrlType) {
				//$('#'+fields[i].field).attr('readonly',true);
			} else {
				$('#' + fields[i].field).textbox("readonly", true);
			}
		}
		//(3)是否必填
		if (fields[i].mustFlag == 'Y') {
			$('#' + fields[i].field).textbox({
				required: true
			})
		}
		//(5)精度值-精度只对numberbox有效
		if (fields[i].precise >= 0 && 'easyui-numberbox' == ctrlType) {
			$('#' + fields[i].field).numberbox({
				precision: parseInt(fields[i].precise)
			});
		}
		//(6)范围值－最小最大长度只对validatebox有效
		if ('easyui-validatebox' == ctrlType) {
			if (fields[i].minLen >= 0 && typeof (fields[i].maxLen) == 'undefined') {
				$('#' + fields[i].field).validatebox({
					validType: 'minLength[' + parseInt(fields[i].minLen) + ']'
				});
			}
			if (typeof (fields[i].minLen) == 'undefined' && fields[i].maxLen >= 0) {
				$('#' + fields[i].field).validatebox({
					validType: 'maxLength[' + parseInt(fields[i].maxLen) + ']'
				});
			}
			if (fields[i].minLen >= 0 && fields[i].maxLen >= fields[i].minLen) {
				$('#' + fields[i].field).validatebox({
					validType: 'length[' + parseInt(fields[i].minLen) + ',' + parseInt(fields[i].maxLen) + ']'
				});
			}
		}
	}
}

/**************************************************/
/***************************************************
 * 异常信息提示
 * zyj
 */
var abnormalTips = (function() {
    var abnormalTipsCus= function(data){
        //初始化样式
        var html = new Array();
        html.push('<style>\n');
        html.push('.confirm-tips{width:100%; height:100%;  position:fixed; top:0; z-index:99999999;overflow: auto;}\n');
        html.push('.confirm-tips-box{width:450px; height:auto; background:#FFF; position:absolute; border: 1px solid rgba(0,0,0,.2);border-radius: 6px;}\n');
        html.push('.confirm-tips-box h4{display:block; margin-top:20px; text-align:center; font-size:18px; font-weight:normal; color:#3881eb;}\n');
        html.push('.confirm-tips-box p{font-size:14px; color:#757c8a; text-align:left; display:block; margin-top:20px;}\n');
        html.push('.autobut {background:#FFF;text-align: center;}');
        html.push('.table_append_class {background:#FFF;text-align:center;}');
        html.push('.autobut a.submissionqx{font-size:12px;display:inline-block; width:80px; height:40px; -moz-border-radius:5px; -webkit-border-radius:5px; border-radius:5px; border:1px solid #3881eb; text-align:center; line-height:40px; color:#3881eb; margin:0 auto; margin-top:30px; margin-bottom:30px; margin-left:50px; margin-right:20px;}\n');
        html.push('.autobut a.submission{font-size:12px;display:inline-block; width:80px; height:40px; -moz-border-radius:5px; -webkit-border-radius:5px; border-radius:5px; background:#3881eb; text-align:center; line-height:40px; color:#FFF; margin:0 auto; margin-top:30px; margin-bottom:30px; margin-left:30px; margin-right:50px;}\n');
        html.push('.autobut a.submissiontz{font-size:12px;display:inline-block; width:80px; height:40px; -moz-border-radius:5px; -webkit-border-radius:5px; border-radius:5px; background:#3881eb; text-align:center; line-height:40px; color:#FFF; margin:0 auto; margin-top:30px; margin-bottom:30px; margin-left:30px; margin-right:20px;}\n');
        html.push('.download-tips-common{font-size:12px;display:inline-block; width:50px; height:30px; -moz-border-radius:3px; -webkit-border-radius:3px; border-radius:3px; background:#3881eb; text-align:center;line-height:30px; color:#FFF;}\n');
        html.push('.confirm-tips-copybtn{font-size:12px;display:inline-block; width:50px; height:30px; -moz-border-radius:3px; -webkit-border-radius:3px; border-radius:3px; background:#3881eb; text-align:center;line-height:30px; color:#FFF;}\n');
        html.push('.overLoading {display: none;position: absolute;top: 0;left: 0;width: 100%;height: 100%;background-color: #f5f5f5;opacity:0.5;z-index: 99999999;}\n');
        html.push('</style>\n');
        var nod = document.createElement("div");
        nod.innerHTML=html.join("");
        top.document.getElementsByTagName('body')[0].appendChild(nod);
        confirmTips(data);
    }
    //-------------------------
    var closeToolTips=function(){//关闭
        top.document.getElementById('newTipsStyle').remove();
        top.document.getElementById("over").remove();
    }
    var abnormalQueryDetail=function(data){//查看详情
        queryDetail(data);
    }
    var abnormalReport=function(data){//上报
        notice(data);
    }
    var copy=function(){//复制
        const range = top.document.createRange();
        range.selectNode(top.document.getElementById('copy_lsh_serialNo'));
        const selection = top.window.getSelection();
        if(selection.rangeCount > 0) selection.removeAllRanges();
        selection.addRange(range);
        top.document.execCommand('copy');
    }
    var notice=function(obj_json){//通知
        if(notice_wait_time == 0){
            notice_wait_time == 5;
            top.document.getElementsByClassName("submissiontz")[0].style.backgroundColor = "#808080";
            top.document.getElementById('abnormal_report_button').innerText = "正在上报";
            setTimeout(function (){//延时5秒
                ajaxPushLogInfo(obj_json);//推送记录
            },5000);
            setTimeout(function (){
                top.document.getElementById('abnormal_report_button').innerText = "上报负责人";
                top.document.getElementsByClassName("submissiontz")[0].style.backgroundColor = "#3881eb";
                notice_wait_time == 0;
            },6000);
        }
    }
//-----------------------------------------
    var confirmTips= function(obj){
        if(obj && obj.title != ''){
            if(!obj.titleConent){
                obj.titleConent = '异常信息提示';
            }
            var left = parseFloat(($(top.document).width()-500)/2);
            var tips_html = '<div id="over" class="overLoading"></div>\n'+
                '<div class="confirm-tips confirm-tips-common" id="newTipsStyle">' +
                '<div class="confirm-tips-box" style="left:'+left+'px;top:8%;">\n' +
                '        <h4 id="h4_title_id">'+obj.titleConent+'</h4>\n' +
                '        <p style="padding: 0 15px">'+obj.title+'</p>\n' +
                //'        <p style="padding: 0 15px">异常编码:'+obj.errorCode+'</p>\n' +
                //'        <p style="padding: 0 15px">异常流水号:<span id="copy_lsh_serialNo">'+obj.serialNo+'</span>&nbsp;&nbsp;&nbsp;&nbsp;<button class="download-tips-common confirm-tips-copybtn">复制</button></p>\n' +
                '        <div class="autobut">\n' +
                '        <a href="javascript:;" class="submission confirm-tips-confirmbtn" id="abnormal_query_detail_button">查看详情</a>\n' +
                // '        <a href="javascript:;" class="submissiontz confirm-tips-noticebtn" id="abnormal_report_button">上报负责人</a>\n' +
                '        <a href="javascript:;" class="submissionqx confirm-tips-cancelbtn" id="abnormal_colse_button">关闭</a>\n' +
                '        </div>\n' +
                '        <div class="table_append_class" id="custom_append_class">\n' +
                '        </div>\n' +
                '    </div></div>';
            if($('.confirm-tips-common').length <= 0){
                //$(window.top.document.body).append(tips_html);
                //$('body').append(tips_html);
                var nod = document.createElement("div");
                nod.innerHTML=tips_html;
                top.document.getElementsByTagName('body')[0].appendChild(nod);
                top.document.getElementById("over").style.display = "block";//遮罩层
                //拖动
                var dialog = top.document.getElementsByClassName("confirm-tips-box")[0];
                var ismousedown = false;
                var dialogleft,dialogtop;
                var downX,downY;
                dialogleft = parseInt(dialog.style.left);
                dialogtop = parseInt(dialog.style.top);
                var dialogtitlebar = top.document.getElementById ("h4_title_id");
                dialogtitlebar.onmousedown = function(e){
                    ismousedown = true;
                    downX = e.clientX;
                    downY = e.clientY;
                }
                top.document.onmousemove = function(e){
                    if(ismousedown){
                        dialog.style.top = e.clientY - downY + dialogtop + "px";
                        dialog.style.left = e.clientX - downX + dialogleft + "px";
                    }
                }
                /*松开鼠标时要重新计算当前窗口的位置*/
                top.document.onmouseup = function(){
                    dialogleft = parseInt(dialog.style.left);
                    dialogtop = parseInt(dialog.style.top);
                    ismousedown = false;
                }
            }
            //通过“事件监听”的方式来绑定事件（也叫Dom2级事件处理程序）---------------------
            var abnormalColseButton = top.document.getElementById('abnormal_colse_button');//关闭
            abnormalColseButton.addEventListener('click', closeToolTips, false);
            var abnormalQueryDetailButton = top.document.getElementById('abnormal_query_detail_button');//查看详情
            abnormalQueryDetailButton.addEventListener('click', function(){abnormalQueryDetail(obj)}, false);
            /*var abnormalReportButton = top.document.getElementById('abnormal_report_button');//上报异常
            abnormalReportButton.addEventListener('click',function(){abnormalReport(obj)} , false);*/
        }
    }
    var queryDetail= function(ObjData){
        var customAppendClass = top.document.getElementById('custom_append_class');//关闭
        if(query_Resolvent_flag){
            customAppendClass.innerHTML="";
            query_Resolvent_flag=false;
            return;
        }
        query_Resolvent_flag=true;
        customAppendClass.innerHTML="";
        var jsonArrayOne=ObjData.descriptionList;
        //添加详细
        var datailValue="";
        datailValue+='<p style="padding: 0 15px">异常编码:'+ObjData.errorCode+'</p>\n';
        datailValue+='<p style="padding: 0 15px">异常流水号:<span id="copy_lsh_serialNo">'+ObjData.serialNo+'</span>&nbsp;&nbsp;&nbsp;&nbsp;<button id="confirm_tips_copybtn" class="confirm-tips-copybtn">复制</button></p>\n' ;
        if(ObjData.person){
            datailValue+='<p style="padding: 0 15px">负责人:'+ObjData.person+'</p>\n';
        }
        if(jsonArrayOne && jsonArrayOne.length>0){
            datailValue+='<p style="padding: 0 15px"><span>异常详细信息:'+jsonArrayOne[0].description_content+'</span></p>';
        }
        if(ObjData.abnormalType){//添加异常类型显示
            var abnormalT1=ObjData.abnormalType;
            var abnormalT2=ObjData.businessType;
            datailValue+='<p style="padding: 0 15px"><span class="anbormal-p-one">异常问题类:'+abnormalT1+'</span>';
            if(abnormalT2){
                datailValue+='&nbsp;&nbsp;&nbsp;&nbsp;<span class="anbormal-p-one">异常业务类:'+abnormalT2+'</span></p>';
            }
        }
        if(datailValue.length>0){
            /*var detailSearch= '<div><div id="abnormal_1Detail">详细信息</div>';
            detailSearch+=datailValue;
            detailSearch+='</div><br/><br/>';
            $('.table_append_class').append(detailSearch);*/
        }
        var nod1 = document.createElement("div");
        nod1.innerHTML=datailValue;
        customAppendClass.appendChild(nod1);
        var confirmTipsCopybtn = top.document.getElementById('confirm_tips_copybtn');//复制
        confirmTipsCopybtn.addEventListener('click', copy, false);
        //异常处理方法
        var data=ObjData.solutionList;
        if(!data){
            return;
        }
        var jsonArray = data;//JSON.parse(data);
        if(jsonArray && jsonArray.length<1){
            return;
        }
        var table='<fieldset id="fieldset_class" style="margin:10px auto; margin-top:5px; margin-bottom:5px; margin-left:5px; margin-right:5px;">\n '+'<legend>处理方法</legend>\n'
            +'<table id="tb_abnormal_class" cellspacing="0" cellpadding="2" width="100%" style="word-break:break-all; word-wrap:break-all;text-align:center;">'
            +'    <tr>'
            +'        <th style="font-size:12px;">处理方法</th>'
            +'        <th style="font-size:12px;width:60px;">详细文档</th>'
            +'    </tr>';
        var ta='';
        for(var i=0;i<jsonArray.length;i++){
            var obj=jsonArray[i];
            if(!obj.url){
                obj.url="";
            }
            ta=ta+    '    <tr>\n'
                +'        <td style="font-size:12px;">'+obj.handleData+'</td>\n'
                +'        <td style="font-size:12px;"><button class="download-tips-common" style="font-size:12px;width:40px;" value='+obj.url+'>下载</button></td>\n'
                +'    </tr>\n'
        }
        table=table+ta+'</table>\n</fieldset>\n';
        var nod3 = document.createElement("div");
        nod3.innerHTML=table;
        customAppendClass.appendChild(nod3);
        var confirmTipsCopybtns = top.document.getElementsByClassName('download-tips-common');//下载
        for(var i = 0;i < confirmTipsCopybtns.length;i++){
            confirmTipsCopybtns[i].addEventListener('click', function(){HandledownFileUrl(this.value)}, false);
        }
    }
    var HandledownFileUrl= function(url){
        var host_api = top.document.location.href.split(top.document.location.pathname)[0];
        var thost = url.split('/fsm/api/')[1];
        window.location.href = host_api+'/fsm/api/'+thost;
    }
    var ajaxPushLogInfo= function(obj){//上报请求
        if(obj.Dtype){
            return;
        }
        var actionUrl= "/log-api/abnormal/addPushLogRecord.do";
        var jsonData = {
            abnormal_serial_no:obj.serialNo,
            notify_type:'manual',
            op_user_id:'root'
        }
        $.ajax({
            type: "post",
            url: actionUrl,
            data : JSON.stringify(jsonData),
            async : false,
            success: function(data){
                if (data && data.errorCode==0) {
                    alertTipsCus('#008800',100,'上报成功');
                    top.document.getElementById('abnormal_report_button').innerText = "上报成功";
                } else {
                    alertTipsCus('#FF0000',250,data.msg+'('+data.errorCode+')');
                }
            },
            error:function(error){
                alertTipsCus('#FF0000',100,'上报失败');
                top.document.getElementById('abnormal_report_button').innerText = "上报失败";
            }
        });
    }
    var alertTipsCus= function(color,pWidth,content){//上报提示
        if(top.document.getElementById('alertTipsMsg')){
            top.document.getElementById('alertTipsMsg').remove();
        }
        var html ='<div id="alertTipsMsg" style="position:fixed;top:50%;z-index:999999999;width:100%;height:30px;line-height:30px;margin-top:-15px;"><p style="background:'+color+';opacity:0.8;width:'+ pWidth +'px;color:#fff;text-align:center;padding:10px 10px;margin:0 auto;font-size:12px;border-radius:4px;">'+ content +'</p></div>'
        var nod = document.createElement("div");
        nod.innerHTML=html;
        top.document.getElementsByTagName('body')[0].appendChild(nod);
        var t=setTimeout(next,3000);
        function next(){
            top.document.getElementById('alertTipsMsg').remove();
        }
    }
    return {
        abnormalTipsCus: abnormalTipsCus,
        confirmTips: confirmTips,
        queryDetail: queryDetail,
        HandledownFileUrl: HandledownFileUrl,
        ajaxPushLogInfo: ajaxPushLogInfo,
        alertTipsCus: alertTipsCus,
    };
});
$(function(){
    query_Resolvent_flag=false;
    notice_wait_time=0;
    $$_tips=new abnormalTips();
});
function prohibitCopy(){
		document.body.addEventListener("copy",function (event) {
			var clipboardData = event.clipboardData || window.clipboardData;
			if (!clipboardData) {
				return;
			}
			var s = window.getSelection().toString();
			if (s.length>=100){
				event.preventDefault();
				clipboardData.setData("text/plain", "");
				alert("对不起您复制超过100字符,禁止复制");

			}else {
				clipboardData.setData("text/plain", s);
			}
	});

}

$.fn.datetimebox.defaults.onShowPanel=function(){
	let value = $(this).datetimebox("getValue");
	if(!value){
		$(this).datetimebox('spinner').timespinner('setValue','00:00:00');
	}
}


/***************end******************************/



function setRandomData(){
	var setData = function () {
		try {
			var maxNum = 1;
			var Rand = Math.random();
			$(".easyui-numberbox").each(function () {
				let value = $(this).numberbox("getValue");
				if (!value) {
					$(this).numberbox("setValue", 1);
				}
			});
			let idList=["contact_area","extension_contact"];
			$(".easyui-validatebox").each(function () {
				// 判断元素是否隐藏
				if ($(this).is(":hidden")||idList.includes($(this).attr("id"))) {
					return;
				}
				let clazz = $(this).attr("class");
				if (clazz.indexOf("numberbox") > 0) {
					let value = $(this).numberbox("getValue");
					if (!value) {
						$(this).numberbox("setValue", 1);
					}
				} else {
					let value = $(this).val();
					if (!value) {
						let id = $(this).attr("id");
						if (id.indexOf("mobile") >= 0) {
							let mineId = Math.round(Rand * 1000000000);
							$(this).val("13" + mineId);
						} else if (id.indexOf("other_contact") >= 0) {
							let mineId = Math.round(Rand * 100000000);
							$(this).val(mineId);
						} else {
							var result = Mock.Random.csentence(3);
							if(id.indexOf("name")>=0){
								result = Mock.Random.cname();
							}else if(id.indexOf("code")>=0){
								result = Mock.Random.word(3).toUpperCase();
							}
							$(this).val(result);
						}
					}
				}
			});
			$(".easyui-queryCombobox,.easyui-mulQueryCombobox ").each(function (index) {
				let that = $(this);
				if (maxNum < index) {
					maxNum = index;
				}
				setTimeout(function () {
					let value = that.queryCombobox("getValue");
					if (!value) {
						let combobox = that.combobox("getData");
						let valueField = that.combobox("options").valueField;
						if (combobox.length > 0) {
							that.combobox("select", combobox[0][valueField]);
						}
					}

				}, index * 300);
			});
			$(".easyui-combobox").each(function (index) {
				let that = $(this);
				if (maxNum < index) {
					maxNum = index;
				}
				setTimeout(function () {
					let value = that.combobox("getValue");
					if (!value) {
						let combobox = that.combobox("getData");
						let valueField = that.combobox("options").valueField;
						if (combobox.length > 0) {
							that.combobox("setValue", combobox[0][valueField]);
						}
					}
					setTimeout(function () {

						let value = that.queryCombobox("getValue");
						if (!value) {
							let combobox = that.combobox("getData");
							let valueField = that.combobox("options").valueField;
							if (combobox.length > 0) {
								that.combobox("select", combobox[0][valueField]);
							}
						}
					},1000);

				}, index * 200);
			});
			$(".easyui-datebox").each(function (index) {
				try {
					$(this).datebox("setValue", $$.dateFormat(new Date(), "yyyy-MM-dd"));
				} catch (e) {
				}
			});

			$(".easyui-datetimebox").each(function (index) {
				try {
					$(this).datebox("setValue", $$.dateFormat(new Date(), "yyyy-MM-dd")+" 00:00:00");
				} catch (e) {
				}
			});

			let checkList = [];
			$("input[type=\"checkbox\"]").each(function (index) {
				let name = $(this).attr("name");
				if (name && checkList.indexOf(name) < 0) {
					$(this).attr("checked", "true");
					checkList.push(name);
				}
			});
			$("input[type='radio']").each(function (index) {
				let name = $(this).attr("name");
				if (name && checkList.indexOf(name) < 0) {
					$(this).attr("checked", "true");
					checkList.push(name);
				}
			});
		} catch (e) {
			console.log(e);
		}
		return maxNum;
	};
	var nextTime = setData();
	setTimeout(function () {
		setData();
	}, nextTime * 50);

	setTimeout(function () {
		setData();
	}, nextTime * 150);
}
























