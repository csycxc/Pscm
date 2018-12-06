//------------------------------------------------------------------------------------//		
//                                PSCM v2 DivEdit JS                                  //
//------------------------------------------------------------------------------------//

// 全局变量区域
var current_div_sn_code = ""
var current_tab = 0
var lastBillRollID = 0

// 简称定义

// bill -> sub_div_work_bill_table 划分清单
// quota -> sub_div_work_quota_table 划分定额
// ht -> ht_edit_table 隐患
// ha -> ha_edit_table 危险源

// 函数区域

function pageInit() {
	// ----------------------------------------------------
	// 表1分项工程清单信息

	$("#bill").jqGrid({

		height : 200,
		datatype : "local",

		// 不显示翻页按键
		pgbuttons : false,
		viewrecords : false,
		pgtext : "",
		pginput : false,

		jsonReader : {
			id : "divisionSnCode"
		},

		colModel : [
				// {label: '', name : 'PK',index :
				// 'division_sn_code',width : 65, editable :
				// true},				
				{
					label : '划分序号',
					name : 'divisionSnCode.divisionSnCode',
					index : 'division_sn_code',
					width : 65,
					editable : false
				},
				{
					label : '项目名称',
					name : 'name',
					index : 'name',
					width : 90,
					editable : false,
					width : 55
				},
				{
					label : '项目特征描述',
					name : 'charactDes',
					index : 'charact_des',
					width : 100,
					editable : true
				},
				{
					label : '计量单位',
					name : 'unit',
					index : 'unit',
					width : 50,
					align : "right",
					editable : true
				},
				{
					label : '招标图量',
					name : 'bidMapQuan',
					index : 'bid_map_quan',
					width : 50,
					align : "right",
					editable : true
				},
				{
					label : '原始施工图量',
					name : 'rawConMapQuan',
					index : 'raw_con_map_quan',
					width : 62,
					align : "right",
					editable : true
				},
				{
					label : '施工图变更累计量',
					name : 'consMapSumVaryQuan',
					index : 'cons_map_sum_vary_quan',
					width : 90,
					sortable : false,
					editable : true
				},
				{
					label : '施工图量',
					name : 'consMapSumVaryQuan',
					index : 'consMapSumVaryQuan',
					width : 42,
					align : "right",
					editable : true
				},
				{
					label : '综合单价',
					name : 'calculateUnitPrice',
					index : 'calculateUnitPrice',
					width : 42,
					align : "right",
					editable : true
				},
				{
					label : '合价',
					name : 'compUnitPrice',
					index : 'compUnitPrice',
					width : 40,
					align : "right",
					editable : true
				},
				{
					label : '暂估价',
					name : 'temporaryMeasurePrice',
					index : 'temporaryMeasurePrice',
					width : 40,
					align : "right",
					editable : true
				},
				{
					label : '定额人工费',
					name : 'quotaManualFee',
					index : 'quotaManualFee',
					width : 54,
					align : "right",
					editable : true
				},
				{
					label : '状态',
					name : 'status',
					index : 'status',
					width : 50,
					align : "right",
					editable : false,
					hidden : true
				},
				{
					label : '操作',
					name : 'op',
					width : 110,
					fixed : true,
					sortable : false,
					resize : false,
					formatter : function(cellvalue, options,
							rowObject) {
						// 0 未保存, 1 保存 2 提交
						if (rowObject.status != 2) {
							console.log("rowObject:")
							console.log(rowObject.status)
							return '<a href=# onclick=\"editBillRow('
									+ options.rowId
									+ ')\" title="修改后点击保存，如果不想保存，请点击取消">修改</a> <a href=# onclick=\"saveBillRow('
									+ options.rowId
									+ ','
									+ options.rowId
									+ ')\" title="同时保存本行和所有下表中的相关定额数据">保存</a> <a href=# onclick=\"cancelBillRow('
									+ options.rowId
									+ ')\" title="取消当前行修改">取消</a>  <a href=\"#\" style=\"color:#0000ff\" onclick=\"submit_sub_div_work_bill(\''
									+ options.rowId
									+ '\')\" title="提交本行，提交后不可修改本行和下表定额数据">提交</a>'
						} else
							return "(已提交锁定)"
					}
				} ],

		pager : '#bill_nav',
		sortname : 'id',
		viewrecords : true,
		sortorder : "desc",
		editurl : "/workBill/save",
		caption : "(双击左侧树中的分项刷新本表数据，双击表中的行刷新定额表、隐患表和危险源表)",
		autowidth : true,

		// 响应鼠标双击选中行事件，触发更新 资源详单 列表
		ondblClickRow : function(id) {
			// 设置当前选中的current_div_sn_code
			onDoubleCheckBill(id)
		},

		// 添加只读属性到post form
		onclickSubmit : function(options, postdata) {
			console.log("onclickSubmit..." + postdata)
			var rowid = postdata[this.id + "_id"] // like
			// "list_id"
			return {
				myParam : $(this).jqGrid("getCell", rowid,
						"colName")
			}
		}
	})

	$("#bill").jqGrid('navGrid', '#bill_nav', {
		edit : false,
		add : false,
		del : false,
		search : false,
		refresh : false
	})

	// ----------------------------------------------------------------------------------------------------------------------------------------
	// 表2分项工程定额数据表
	$("#quota").jqGrid({
		height : 270,
		datatype : "local",

		// 不显示翻页按键
		pgbuttons : false,
		viewrecords : false,
		pgtext : "",
		pginput : false,

		jsonReader : {
			id : "resCode"
		},
		colModel : [ {
			label : 'divisionSnCode',
			name : 'divisionSnCode',
			index : 'divisionSnCode',
			width : 50,
			align : "right",
			editable : true,
			hidden : true
		}, {
			label : '资源代码',
			name : 'resCode',
			index : 'resCode',
			width : 50,
			align : "right",
			editable : true,
			hidden : true,
			key : true
		}, {
			label : '资源类型',
			name : 'resourcesType',
			index : 'resourcesType',
			width : 75,
			editable : true
		}, {
			label : '资源名称',
			name : 'resName',
			index : 'resName',
			width : 60,
			editable : true
		}, {
			label : '资源型号级别',
			name : 'resTypeLevel',
			index : 'resTypeLevel',
			width : 90,
			editable : true
		}, {
			label : '单位',
			name : 'unit',
			index : 'unit',
			width : 50,
			align : "right",
			editable : true
		}, {
			label : '招标图量',
			name : 'bidMapQuan',
			index : 'bidMapQuan',
			width : 60,
			align : "right",
			editable : true
		}, {
			label : '原始施工图量',
			name : 'rawConsMapQuan',
			index : 'rawConsMapQuan',
			width : 86,
			align : "right",
			editable : true
		}, {
			label : '施工图变更累计量',
			name : 'consMapSumVaryQuan',
			index : 'consMapSumVaryQuan',
			width : 150,
			sortable : false,
			editable : true
		}, {
			label : '损耗率',
			name : 'lossRate',
			index : 'lossRate',
			loss_rate : 40,
			editable : true
		}, {
			label : '单价',
			name : 'compUnitPrice',
			index : 'compUnitPrice',
			width : 60,
			editable : true
		}, {
			label : '节超预警率',
			name : 'saveExcessRate',
			index : 'saveExcessRate',
			width : 80,
			editable : true
		},
		],

		pager : '#quota_nav',
		sortable: true,
		sortname : 'resourcesType',
		viewrecords : true,
		sortorder : "asc",
		editurl : "/subdivworkquota/handlerowopera",
		caption : "&nbsp;",
		autowidth : true,
		multiselect : true,

	})


	$("#quota").jqGrid("navGrid","#quota_nav",
		{
			edit : false,
			add : false,
			search : false,
			refresh : false
		},
		{},
		{},
		{
			url : "/subdivworkquota/handlerowopera",
			// 定制化删除事件
			onclickSubmit : function(options, ids) {
				console.log("on submit")
				console.log(ids)
				var $self = $(this), selectedrows = ids.split(","), selectedData = [], i, l = selectedrows.length;
				var resCodes = "";
	
				for (i = 0; i < l; i++) {
					// fill array selectedData with the data from
					// selected rows
					if (i == 0)
						resCodes = ($self.jqGrid("getRowData",
								selectedrows[i]).resCode);
					else
						resCodes = resCodes
								+ ","
								+ ($self.jqGrid("getRowData",
										selectedrows[i]).resCode);
				}
				return {
					resCodes : JSON.stringify(resCodes)
				}
			}
		});

	$("#quota").jqGrid('inlineNav', "#quota_nav")

	// ----------------------------------------------------------------------------------------------------------------------------------------
	var troubleCateJson = "";
	var troubleLevelJson = "";
	
	// 表3隐患
	$("#ht").jqGrid({
		// url : 'div_edit_item_table.json',

		height : 270,
		width : 500,
		datatype : "local",
		
		// 不显示翻页按键
		pgbuttons : false,
		viewrecords : false,
		pgtext : "",
		pginput : false,

		pager : '#ht_nav',

		colModel : [{
			label : '划分编码',
			name : 'divItemCode',
			index : 'divItemCode',
			editable : false,
			hidden : false,
			key : false,
			width : 160,
		}, {
			label : '隐患编号',
			name : 'troubleCode',
			index : 'troubleCode',
			editable : true, //如果为false，troubleCode传到后台为行号，不是主键值。
			hidden : true, 
			key : true
		}, {
			label : '隐患类别',
			name : 'troubleCate.enumValueName',
			index : 'troubleCate',
			width : 160,
			editable : true,
			edittype:'select',
			editoptions: {value:getEnumVarJson('findEnumVarOfHiddenTroubleCate')}
		}, {
			label : '隐患级别',
			name : 'troubleLevel.enumValueName',
			index : 'troubleLevel',
			width : 160,
			editable : true,
			edittype:'select',
			editoptions: {value: getEnumVarJson('findEnumVarOfHiddenTroubleLevel')}
		}, {
			label : '排查项目',
			name : 'investItem',
			index : 'investItem',
			width : 160,
			editable : true
		}, {
			label : '排查内容',
			name : 'investContent',
			index : 'investContent',
			width : 140,
			align : "right",
			editable : true
		}, {
			label : '隐患描述',
			name : 'description',
			index : 'description',
			width : 160,
			align : "right",
			editable : true
		},		
		],
		

		sortname : 'id',
		viewrecords : true,
		sortorder : "desc",
		editurl : "/hiddenTroubleBill/handlerowopera",
		caption : "&nbsp;",
		autowidth : true,
		multiselect : true,
	})

	// 自定义 导航条
	$("#ht").jqGrid("navGrid","#ht_nav",
		{
			edit : false,
			add : false,
			search : false,
			refresh : false
		},
		{},
		{},
		{
			
			url : "/hiddenTroubleBill/handlerowopera",
			// 定制化删除事件
			onclickSubmit : function(options, ids) {
				console.log("ht on submit")
				console.log(ids)
				var $self = $(this), selectedrows = ids.split(","), selectedData = [], i, l = selectedrows.length;
				var htCodes = "";
				for (i = 0; i < l; i++) {
					// fill array selectedData with the data from
					// selected rows
					if (i == 0)
						htCodes = ($self.jqGrid("getRowData",selectedrows[i]).troubleCode);
					else
						htCodes = htCodes + "," + ($self.jqGrid("getRowData", selectedrows[i]).troubleCode);
				}
				return {
					htCodes :htCodes
				}
			}
		});
	
	$("#ht").jqGrid('inlineNav', "#ht_nav")

	// ----------------------------------------------------------------------------------------------------------------------------------------
	// 表4危险源
	$("#ha").jqGrid({		
		height : 270,
		width : 500,
		datatype : "local",

		// 不显示翻页按键
		pgbuttons : false,
		viewrecords : false,
		pgtext : "",
		pginput : false,

		pager : '#ha_nav',
		colModel : [ {
			label : '划分编码',
			name : 'divItemCode',
			index : 'divItemCode',
			editable : false,
			hidden : false,
			key : false,
			width : 160
		},  {
			label : '危险源编码',
			name : 'hazardsCode',
			index : 'hazardsCode',
			width : 160,
			editable: false,
			key: true,
			hidden: true
		},{
			label : '危险源及危害因素',
			name : 'hazardsFactors',
			index : 'hazardsFactors',
			editable : true,
			width : 160
		}, {
			label : '风险级别',
			name : 'hazardsLevel',
			index : 'hazardsLevel',
			width : 160,
			editable : true
		}, {
			label : '可能造成的事故事件',
			name : 'accidents',
			index : 'accidents',
			width : 160,
			editable : true
		}, {
			label : '控制措施',
			name : 'accidents',
			index : 'accidents',
			width : 160,
			align : "right",
			editable : true
		}, {
			label : '备注说明',
			name : 'description',
			index : 'description',
			width : 160,
			align : "right",
			editable : true
		},
		
		],

		pager : '#ha_nav',
		sortname : 'id',
		viewrecords : true,
		sortorder : "desc",
		editurl : "/hazards/handlerowopera",
		caption : "&nbsp;",
		autowidth : true,
		multiselect : true,

	})

	$("#ha").jqGrid("navGrid","#ha_nav",
		{
			edit : false,
			add : false,
			search : false,
			refresh : false
		},
		{},
		{},
		{
			url : "/hazards/handlerowopera",
			// 定制化删除事件
			onclickSubmit : function(options, ids) {
				console.log("ha on submit")
				console.log(ids)
				var $self = $(this), selectedrows = ids.split(","), selectedData = [], i, l = selectedrows.length;
				var haCodes = "";
	
				for (i = 0; i < l; i++) {
					// fill array selectedData with the data from
					// selected rows
					if (i == 0)
						haCodes = ($self.jqGrid("getRowData",
								selectedrows[i]).hazardsCode);
					else
						haCodes = haCodes
								+ ","
								+ ($self.jqGrid("getRowData",
										selectedrows[i]).hazardsCode);
				}
				return {
					haCodes : haCodes
				}
			}
		});
	$("#ha").jqGrid('inlineNav', "#ha_nav")

}

// ----------------------------------------------------------------------------------------------------------------------------------------
// 公共函数
//getTroubleCateJson()  getTroubleLevelJson()

function getEnumVarJson(method){
	//动态生成select内容
	var str="";
	$.ajax({
		type:"post",
		async:false,
		url:"/hiddenTroubleBill/"+method,
		success:function(data){
			if (data != null) {
		        var jsonobj=eval(data);
		        var length=jsonobj.length;
		        for(var i=0;i<length;i++){
		            if(i!=length-1){
		            	str+=jsonobj[i].enumValueName+":"+jsonobj[i].enumValueName+";";
		            }else{
		            	str+=jsonobj[i].enumValueName+":"+jsonobj[i].enumValueName;// 这里是option里面的 value:label
		            }
		        }
			}
			console.log(str);
		}
	});
	return str;
}
// 单击 资源数量 中的一行， 会根据选中的分项工程名称来刷新 资源详单 表格中的数据，显示当前选中 分项工程的资源详单

function refresh_div_work_quota_table(rowId) {

	// 先清空当前数据
	$("#quota").jqGrid('clearGridData')

	var divisionSnCode = $("#bill").getCell(rowId, 'divisionSnCode.divisionSnCode')

	// 将divisionSnCode参数传递到quota jqgrid，提交数据时候带上 divisionSnCode这个值
	$("#quota").setGridParam({
		editurl : '/subdivworkquota/handlerowopera?divsncode=' + divisionSnCode
	})
	
	$("#ht").setGridParam({
		editurl : '/hiddenTroubleBill/handlerowopera?divsncode=' + divisionSnCode
	})
	
	$("#ha").setGridParam({
		editurl : '/hazards/handlerowopera?divsncode=' + divisionSnCode
	})

	current_div_sn_code = divisionSnCode

	// alert(div_id)
	console.log('divisionSnCode=' + divisionSnCode)

	// test load local test data
	// url = "workBill.json"
	url = "/subdivworkquota/findbydivsncode?divisionsncode=" + divisionSnCode
	console.log(url)
	$.getJSON(url, function(json) {
		console.log(json)
		$.each(json, function(i, item) {
			$("#quota").jqGrid('addRowData', i + 1, item)
		})
	})

}

// 单击 资源数量 中的’编辑'，或者双击 一行，会弹出来jqGrid默认自带的通用编辑数据窗体
function openForm(rowid) {
	console.log('openform rowid=' + rowid)
	$('#div_edit_table').jqGrid('editGridRow', rowid, {
		recreateForm : true,
		closeAfterEdit : true,
		closeOnEscape : true,
		reloadAfterSubmit : false,
		height : 600,
		left : 200,
		width : 600
	})
}

// 启用行编辑

function editBillRow(rowId) {
	console.log('editRow id=' + rowId)
	console.log('oldSelectRowId id=' + $("#selectRowId").val())
	console.log('selectRowId id=' + $("#selectRowId").val())
	// 原选中行ID
	var oldSelectRowId = $("#selectRowId").val()
	if (oldSelectRowId != null && oldSelectRowId != ""
			&& oldSelectRowId.length > 0) {
		$("#fieldGrid").jqGrid('saveRow', oldSelectRowId) // 保存上一行
	}

	// 当前选中行
	$("#selectRowId").val(rowId) // 临时存储当前选中行
	// $("#fieldGrid").jqGrid('editRow', id)
	$(".review-" + rowId).removeClass('not-editable-cell')
	$("#bill").jqGrid('editRow', rowId, {
		keys : true,
		focusField : 1
	})

}

// 保存分项工程清单 行

function saveBillRow(rowId, divId) {
	// 设置当前选中的行

	// console.log("divid:" + divId)
	// 将只读divisionSnCode主键放入extraparam，否则divisionSnCode不会再post中出现
	var divisionSnCode = $("#bill").getCell(rowId, 'divisionSnCode.divisionSnCode')
	console.log("divisionSnCode:" + divisionSnCode)
	var name = $("#bill").getCell(rowId, 'name')
	console.log("name:" + name)
	current_div_sn_code = divisionSnCode
	var status = $("#bill").getCell(rowId, 'status')
	console.log("status:" + status)
	console.log("current_div_sn_code:-------------"+current_div_sn_code)

	$("#bill").saveRow(rowId, {
		"extraparam" : {
			'divisionSnCode.divisionSnCode' : divisionSnCode,
			'name' : name
		}
	})
	// 恢复二次编辑能力
	$("#bill").jqGrid('setCell', rowId, 'divisionSnCode', divisionSnCode, {
		editable : false
	})
}

// 取消行编辑操作

function cancelBillRow(rowId) {

	$("#bill").restoreRow(rowId) // 用修改前的数据填充当前行
}

// 加载 分项工程清单信息
function load_sub_div_work_bill(divSnCode, divName) {

	console.log('load_sub_div_work_bill with divNae ...')
	console.log(divSnCode)
	console.log(divName)

	// 首先清除当前表格数据
	$("#bill").jqGrid('clearGridData')
	$("#quota").jqGrid('clearGridData')

	$("#bill").jqGrid("setCaption", divName);

	url = "/workBill/findbyparentcode?parentcode=" + divSnCode

	$.ajaxSetup({
		async : false
	})
	xhr = $.getJSON(url, function(json) {
		console.log("JSON Data returned:")
		console.log(json)
		if (json) {
			$.each(json, function(i, item) {
				$("#bill").jqGrid('addRowData', i + 1, item)
			})
		}
	})

	$("#sub_div_work_table").setGridParam().trigger("reloadGrid")
}

function load_sub_div_work_quota() {
	var rowData = $("#bill").jqGrid("getRowData", id)
	current_div_sn_code = rowData.divisionSnCode.divisionSnCode
	refresh_div_work_quota_table(current_div_sn_code)
}

// 提交 分项工程清单信息

function submit_sub_div_work_bill(rowId) {
	var divisionSnCode = $("#bill").getCell(rowId, 'divisionSnCode.divisionSnCode')
	console.log("divisionSnCode:" + divisionSnCode)
	current_div_sn_code = divisionSnCode

	url = "/workBill/submit?divisionsncode=" + divisionSnCode

	console.log("calling restapi ...")
	console.log(url)

	$.ajax({
		url : url,
		cache : false,
		dataType : 'json',
		success : function(data) {
			console.log('SUCCESS: ', data);
			load_sub_div_work_bill(divisionSnCode, '')
		},
		error : function(data) {
			console.log('ERROR: ', data.responseText);
			alert("提交出错：" + data.responseText)
		}
	});
}

// 加载 隐患信息
function load_hidden_trouble_bill(divSnCode, divName) {

	console.log('load_hidden_trouble_bill with divSnCode:' + divSnCode
			+ ' divName:' + divName)

	// 首先清除当前表格数据
	$("#ht").jqGrid('clearGridData')

	url = "/hiddenTroubleBill/findbydivsncode?divSnCode=" + divSnCode  //hiddenTroubleBill

	if (divSnCode) {
		$.ajaxSetup({
			async : false
		})
		xhr = $.getJSON(url, function(json) {
			console.log("JSON Data returned:")
			console.log(json)
			if (json) {
				$.each(json, function(i, item) {
					$("#ht").jqGrid('addRowData', i + 1, item)
				})
			}
		})

		//$("#ht").setGridParam().trigger("reloadGrid")
	}
}

// 加载 hazards 信息
function load_hazards(divSnCode, divName) {

	console.log('load_hazards with divSnCode:' + divSnCode + ' divName:'
			+ divName)

	// 首先清除当前表格数据
	$("#ha").jqGrid('clearGridData')

	url = "/hazards/findbydivsncode?divisionsncode=" + divSnCode

	if (divSnCode) {
		$.ajaxSetup({
			async : false
		})
		xhr = $.getJSON(url, function(json) {
			console.log("JSON Data returned:")
			console.log(json)
			if (json) {
				$.each(json, function(i, item) {
					$("#ha").jqGrid('addRowData', i + 1, item)
				})
			}
		})

		$("#ha").setGridParam().trigger("reloadGrid")
	}
}

// 刷新 分项工程定额表 ，隐患，危险源 表
function onDoubleCheckBill(rowId) {
	// 设置当前行选中背景色
	if (lastBillRollID != rowId) {
		$("#bill").jqGrid('setRowData', lastBillRollID, false, {
			background : ''
		});
		$("#bill").jqGrid('setRowData', rowId, false, {
			background : '#e0e0e0'
		});
	}

	lastBillRollID = rowId

	// 先清空当前数据
	var divSnCode = $("#bill").getCell(rowId, 'divisionSnCode.divisionSnCode')
	refresh_div_work_quota_table(rowId)
	load_hazards(divSnCode, "")
	load_hidden_trouble_bill(divSnCode, "")

}

// ---------- main area ------------------
// 以下为一些初始化和事件回调挂载

// 初始化jqGrid
$(function() {
	pageInit()
})

// 容器宽度感知
$(window).bind("resize", function() {
	console.log("resize ...")
	container_width = $("#gridContainer").width()
	console.log("container_width:" + container_width)
	$("#bill").setGridWidth(container_width)
	$("#quota").setGridWidth(container_width)
	$("#ht").setGridWidth(container_width)
	$("#ha").setGridWidth(container_width)
	// $("#tabs").setWidth($("#gridContainer").width())

	$('#tabs').tabs({
		width : $("#tabs").parent().width(),
		height : "auto"
	}).tabs('resize')

})

// 节点属性框关闭事件
$('#node_prop').live('pagehide', function(event) {
	console.log('pagehide');
});

// 处理 tab 选中事件
// index=0 分项工程定额表
// index=1 隐患
// index=2 危险源
$(document).ready(function() {
	$('#tabs').tabs({
		onSelect : function(title, index) {
			console.log(index + ' is selected');
			console.log(current_div_sn_code)
		}
	});
});
