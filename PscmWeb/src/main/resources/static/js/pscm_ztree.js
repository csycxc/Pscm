//------------------------------------------------------------------------------------//		
//                                PSCM v2 zTree JS                                    //
//------------------------------------------------------------------------------------//

// 全局变量区域

//划分模板
var setting1 = {
	edit: {
		enable: true,
		showRemoveBtn: false,
		showRenameBtn: false,
		drag: {  
			isCopy: true,
			isMove: false
			},
		},
	async: {
			enable: true,
			type: "get",
			dataType:"json",
			url:"/engDivision/getcommondivtree",
			autoParam:["id=pId"],
		},					
	data: {
		keep: {
			parent: true,
			leaf: true
		},
		simpleData: {
			enable: true
		}
	},
	callback: {
		// beforeDrag: zTreeSourceBeforeDrag,
		beforeDrop: zTreeSourceBeforeDrop,
		// onDrag: onDrag,
		onDrop: rePopulateNodes  
	},
	view: {
		selectedMulti: false,
		fontCss: { 'color': 'blue', 'font-family': '微软雅黑' },
		// showLine: false,
		// showIcon: false
		
	}
};

// 当前项目划分
var setting2 = {
	edit: {
		enable: true,
		showRemoveBtn: true,
		showRenameBtn: true,
		removeTitle: "删除",
		renameTitle: "改名",
		drag: {  
			prev: false,  
            next: false,		
			isCopy: true,
			isMove: false,
			inner: true
			},
		},  

	 async: {
			enable: true,
			type: "get",
			dataType:"json",			
			url: "/engDivision/getdivtree",
			autoParam:["id=pId"],					
		},				
	data: {
		keep: {
			parent: false,
			leaf: false
		},
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeDrag: beforeDrag,
		beforeEditName: beforeEditName,
		beforeRemove: beforeRemove,
		beforeRename: beforeRename,
		onRemove: onRemove,
		onRename: onRename,
		onDblClick: showSubdivWorkBill,
		onAsyncSuccess: onAsyncSuccess,
		onAsyncError: onAsyncError		
	},
	view: {
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
		selectedMulti: false,
		dblClickExpand: false
		// showLine: false,
		// showIcon: false,
		// fontCss: {'color':'black','font-size':'20px'}
	},
};		

var newCount = 1;

// 保存划分树所有节点
var allnodes = []

var initDivTreeFlag = true

/**
 * 划分树加载成功后，判断是否为空，如果为空，就是首次配置，允许随意删除任何级别节点
 * 否则只能允许删除叶节点，因为删除带有子节点的父节点会级联删除下面所有子节点的每隔节点的所有关联数据，这个风险比较大，所以不支持这种用例 *
 * 
 * 实现方式： 记录一个标记位来区分是否是首次初始化划分树编辑还是已经保存过的
 * 
 * @returns
 */
function onAsyncSuccess(){
	console.log("onAsyncSuccess")
	var treeObj = $.fn.zTree.getZTreeObj("treeD");
	treeObj.refresh()
	var nodes = treeObj.getNodes();
	console.log(nodes.length)	
	if(nodes.length>0)
		initDivTreeFlag = false	
	console.log("initDivTreeFlag="+initDivTreeFlag)	
}

function onAsyncError(){
	console.log("onAsyncError, ztree load data error...")	
}

// 动态显示 增加
// 创建新节点时候，计算新属性
function addHoverDom(treeId, treeNode) {
	// console.log(treeNode.tId )
	var sObj = $("#" + treeNode.tId + "_span");

	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0)
		return;
	
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='增加节点' onfocus='this.blur();'></span>";
	
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		// console.log("add new node ...")
		var zTree = $.fn.zTree.getZTreeObj("treeD");
		var newId = calculateNewNodeID(treeNode)
		var newDivLevel = calculateNewNodeDivLevel(treeNode)
		if(newDivLevel>11)
		{
			alert("划分层次（5）太深，不允许建立新级别")
			return
		}
		zTree.addNodes(treeNode, {id:(newId), divLevel:newDivLevel, pId:treeNode.id, name:"新节点" + (newId)});
		var newNode = zTree.getNodeByParam("id",newId );
		showNodeProp(newNode)
		return false;
	});
};

// 根据上下文计算出新的节点的divLevel
function calculateNewNodeDivLevel(parentTreeNode)
{
	console.log("calculateNewNodeDivLevel ... ")
	console.log(parentTreeNode)
	// 存在同级节点，则取同级第一个节点的值
	if(parentTreeNode.children)
	{
		return parentTreeNode.children[0].divLevel
	}
	// 新的子级别，默认比父级别号大2
	else
	{	
		return parseInt(parentTreeNode.divLevel)+2
	}
	
}

// 根据上下文计算出新的节点的ID,符合1.1.1.1这种层级结构
function calculateNewNodeID(parentTreeNode)
{
	console.log("calculateNewNodeID ... ")
	console.log(parentTreeNode)
	if(parentTreeNode.children)
	{
		console.log("parentTreeNode.children ... ")		
		console.log(parentTreeNode.id)
		childrenSize = parentTreeNode.children.length
		return parentTreeNode.id + "." + (childrenSize + 1)	
	}
	else
	{	
		console.log("parentTreeNode.getPreNode ...")
		if(parentTreeNode.getParentNode())
			return calculateIDByParentNodeID(parentTreeNode.id)
	}
}


function calculateIDByPreNodeID(preNodeID)
{	
	console.log("calculateIDByPreNodeID ...")
	var newid = ""
	var prefixIdx = preNodeID.lastIndexOf('\.')
	var prefix = preNodeID.substr(preNodeID.length - prefixIdx)
	var previousID = preNodeID.substr(prefixIdx+1 ,preNodeID.length)
	return prefix + "." +  (parseInt(previousID)+1) 
}


function calculateIDByParentNodeID(parentNodeID)
{
	console.log("calculateIDByParentNodeID ...")
	return parentNodeID + ".1"	
}


function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};

$(document).ready(function(){
	$.fn.zTree.init($("#treeS"), setting1);
	$.fn.zTree.init($("#treeD"), setting2);		
});

// 禁用双击展开功能
function dblClickExpand(treeId, treeNode) {
	return false;
}

var log, className = "dark";
function beforeDrag(treeId, treeNodes) {
	return false;
}

function beforeEditName(treeId, treeNode) {
	className = (className === "dark" ? "":"dark");
	console.log("[ beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
	var zTree = $.fn.zTree.getZTreeObj("treeD");
	zTree.selectNode(treeNode);
	showNodeProp(treeNode);
	return false;
}

// 提示用户删除数据风险并且判断 initDivTreeFlag ，如果不是true 初始化状态，则不运行删除带有子节点的父节点，只能删除叶子节点

function beforeRemove(treeId, treeNode) {
	className = (className === "dark" ? "":"dark");
	console.log("[  beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
	var zTree = $.fn.zTree.getZTreeObj("treeD");
	zTree.selectNode(treeNode);	
	if(!initDivTreeFlag && treeNode.isParent)
	{
		alert("只能删除叶子节点！")
		return false;
	}
	
	if (initDivTreeFlag)
		msg = "是否删除？"
	else
		msg = "请注意:删除'" + treeNode.name + "'后，与其划分项关联的所有数据都会被删除，请谨慎确认是否删除？"
		
	var result =  confirm(msg);
	console.log("confirm:"+result)
	if(!result)
		return false
	else
	{// 如果已经保存到租户业务库中，先调用后台删除该节点，后删除界面节点
		console.log("ajax call to delete tree node:"+treeNode.id)
		if(!initDivTreeFlag)
			callAjaxToDelDivNode(treeNode.id)
	}
	
}
function onRemove(e, treeId, treeNode) {
	console.log("[ onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
}
function beforeRename(treeId, treeNode, newName, isCancel) {
	className = (className === "dark" ? "":"dark");
	console.log((isCancel ? "<span style='color:red'>":"") + "[  beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
	if (newName.length == 0) {
		setTimeout(function() {
			var zTree = $.fn.zTree.getZTreeObj("treeD");
			zTree.cancelEditName();
			alert("节点名称不能为空.");
		}, 0);
		return false;
	}
	return true;
}
function onRename(e, treeId, treeNode, isCancel) {
	console.log((isCancel ? "<span style='color:red'>":"") + "[  onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
}
function showRemoveBtn(treeId, treeNode) {
	return true;
	// return !treeNode.isFirstNode;
}
function showRenameBtn(treeId, treeNode) {
	return true;
	// return !treeNode.isLastNode;
}


/** 拖拽释放之后结束前执行 moveType值类型请参考api */
function zTreeSourceBeforeDrop(treeId, treeNodes, targetNode, moveType) {  
    return validationDD(treeNodes[0], targetNode)	
}


/** 拖拽释放之后结束前执行 moveType值类型请参考api */
function zTreeSourceAfterDrop(treeId, treeNodes, targetNode, moveType) {  
    // return validationDD(treeNodes[0], targetNode)
	console.log("after drop")
	console.log(treeId)
	console.log(targetNode)
}


// 显示划分节点属性配置窗口
function showNodeProp(treeNode)
{
	console.log('showNodeProp...');
	console.log(treeNode)
	// console.log(treeNode.getPath())
	
	var $vW = $(window).width();
	var $w = $('#node_prop').outerWidth();
	var $vH = $(window).height();
	var $h = $('#node_prop').outerHeight();
	$('#node_prop').css({"left":($vW - $w)/2 + "px","top":($vH - $h)/2 + "px"});	
	
	$('#node_prop').dialog('open')
	$('#node_prop').dialog('move', {top:($vH - $h)/2 -100, left:($vW - $w)/2 })


	$("#div_node_name").textbox("setValue",treeNode.name);
	if(treeNode.divlevel)
		console.log(treeNode.divlevel)
	$("#div_node_divlevel").textbox("setValue",treeNode.divLevel);	
	$("#div_node_id").textbox("setValue",treeNode.id);
	$("#div_node_pId").textbox("setValue",treeNode.pId);
	$("#div_node_divItemCode").textbox("setValue",treeNode.divItemCode);
	$("#div_node_skill").textbox("setValue",treeNode.skill);
	
	// tid是ztree node的物理id,用来找回这个node
	$("#div_node_tId").textbox("setValue",treeNode.tId);
	
}


// 双击节点，根据当前划分节点 来更新右侧 分项工程配置表数据
// 如果是页节点
// 如果是
function showSubdivWorkBill(event, treeId, treeNode)
{
	console.log('showSubdivWorkBill  ...  ');
	console.log(treeNode);
	load_sub_div_work_bill(treeNode.id, treeNode.name)
}


// 保存节点属性编辑
function saveNodeProp(){
    console.log("saveNodeProp")
	tid = $("#div_node_tId").textbox('getValue')
	console.log(tid);
	
    // 检查输入项
    if(!validNodePropInput())
    	return
    	
	// 同步数据
	var treeObj = $.fn.zTree.getZTreeObj('treeD');  
	treeNode = treeObj.getNodeByTId(tid)
	console.log($("#div_node_name").textbox('getValue'))
	treeNode.name =  $("#div_node_name").textbox('getValue')
	treeNode.divItemCode =  $("#div_node_divItemCode").textbox('getValue')
	treeNode.skill =  $("#div_node_skill").textbox('getValue')

	// divItemCode为空时候可以修改
	
	$('#div_node_divItemCode').textbox({disabled:true});
	$("#div_node_divItemCode").attr("disabled","false");
	
	if(treeNode.divItemCode==null || treeNode.divItemCode.length==0 )
	{
		console.log('treeNode.divItemCode is null')	
		// $('#div_node_divItemCode').textbox('textbox').attr('disabled',false);
		$("#div_node_divItemCode").attr("disabled","enable");
	}		
	else
		$("#div_node_divItemCode").attr("disabled","disabled");
		// $('#div_node_divItemCode').textbox('textbox').attr('disabled',true);
	
	// 刷新节点
	treeObj.updateNode(treeNode);  
	console.log(treeNode)
	
	// 关闭属性窗口
	$('#node_prop').dialog('close')
}

function closeNodeProp()
{
	if(validNodePropInput())
		$('#node_prop').dialog('close')
}

function validNodePropInput()
{
	var skill = $("#div_node_skill").textbox('getValue')
	var divItemCode = $("#div_node_divItemCode").textbox('getValue')
	if(!skill)	
	{
		alert("请输入‘工法’信息")
		return false
	}
	if(!divItemCode)	
	{
		alert("请输入‘划分编码’信息")
		return false
	}	
	return true
}



// 保存配置好的项目划分树
function saveDivTree(){
	console.log("saveDivTree...")
	if(initDivTreeFlag)
	{
		var result = confirm("是否已经编辑完？如果保存后会将节点关联到大量划分相关的数据，只能从每个叶子节点开始逐一删除，不能直接删除带有子划分的划分节点");
		if(!result)
			return
	}
	var treeObj = $.fn.zTree.getZTreeObj("treeD");
	treeObj.refresh()
	var nodes = treeObj.getNodes();			
	
	var simpleNodes = treeObj.transformToArray(nodes);
	var json=JSON.stringify(simpleNodes); 
	
	//console.log("div tree:")
	//console.log(json)
	//console.log(json.length)
	
	if(json.length==2)
	{
		alert("当前配置树为空，不需要保存，请继续配置后再保存!")
		return
	}
		
	var url = "/engDivision/savedivtree"
	console.log("saveDivTree call rest api: "+url)
		
	 $.ajax({url, cache: false, type:"POST",data: json, datatype: "json", contentType : 'application/json;charset=utf-8',
      success: function(data) {
    	  console.log("success ")
    	  alert("保存成功")
      },
      error: function(data) {
    	  console.log(data)
		  alert("保存失败:"+data.responseText)
      }
   });		
}


/** 检查拖拽是否合乎业务约束逻辑 */

function validationDD(source, target) {
	console.log("zTreeSourceBeforeDrop sourceNode:")
	console.log(source)
	console.log(source.getPath())
	console.log("zTreeSourceBeforeDrop targetNode:")
	console.log(target)

	console.log("sourceNode level:" + source.level)
	if (target) console.log("targetNode level:" + target.level)
		
	console.log("sourceNode divLevel:" + source.divLevel)
	
	// case 1. 目标树已经被删空，只能拖拽一级节点
	if (target == null) {
		// 不是 单位工程级别节点，不能创建成目标树种的顶级节点
		if (source.divLevel != 3) {
			alert("当前项目划分树为空，只能新建顶级'单位工程级别节点'！")
			return false
		}
		return true
	}

	if (source.level != target.level + 1) {
		alert("源节点'" + source.name + "'的与目标位置级别不一致，不能拷贝！")
		return false
	}
}

// 递归遍历树，深度大于deep时退出，防止栈溢出
// 遍历每隔节点的时候，增加path属性，为该节点在树中的路径定位，比如1.1.1.1
// 1. 重新生成id主键，是一个整形自增的数字
// 2. 重新生成path，格式 1.1.1.1，标识当前节点在树种的具体位置
function rePopulateTree(treeNode,result,deep){ 
    
	// 超出最大支持递归深度
	if(treeNode.level>deep)
	  return result

	// 递归遍历所有子节点，并重新设置id，pId
	if (treeNode.isParent) {
		var childrenNodes = treeNode.children;  
		if (childrenNodes) {  
			for (var i = 0; i < childrenNodes.length; i++) {
				// 只为从模板树种拖拽过来的节点重新生成id pid
				if(childrenNodes[i].parentDivItemCode)
				{
					childrenNodes[i].pId = childrenNodes[i].getParentNode().id
					childrenNodes[i].id = childrenNodes[i].pId + "." + (i+1)				
				}
				// childrenNodes[i].id = childrenNodes[i].tId
				result.push(childrenNodes[i])			
				rePopulateTree(childrenNodes[i], result, deep);  
			}  
		}  	
	}
	
	// 刷新树
	var treeObj = $.fn.zTree.getZTreeObj("treeD");
	treeObj.refresh()	
    return result;  
} 

/**
 * 
 * 重新填充 遍历所有项目划分树的子树，调用rePopulateTree更新子树内节点
 * 
 */
function rePopulateNodes(){
	console.log("rePopulateNodes...")
	var treeObj = $.fn.zTree.getZTreeObj("treeD");	
	var nodes = treeObj.getNodes();	
	
	// 刷划分树
	allnodes = []
	
	if( Object.prototype.toString.call(nodes)=='[object Array]')
	{
		console.log("treeObj.length()="+nodes.length)
		if(nodes.length>0)
		{
			for(var i=0;i<nodes.length;i++)
			{
				// 首先填充顶级节点，而且只为从模板树种拖拽过来的节点重新生成id pid
				if(nodes[i].level==0 && nodes[i].parentDivItemCode)
				{
					// nodes[i].path=(i+1)
					nodes[i].id = (i+1)
					nodes[i].pId = 0
				}
				allnodes.push(nodes[i])		
				// 填充子节点
				rePopulateTree(nodes[i], allnodes, 8)
			}			
		}
	}		
	//console.log(allnodes.length)
	//console.log(allnodes)
	return allnodes;
}

// 调用后台restapi 删除划分树子节点
function callAjaxToDelDivNode(divid)
{
	var url = "/engDivision/delete?divisionsncode="+divid
	console.log("callAjaxToDelDivNode "+url)
		
	 $.ajax({url, cache: false, 
      success: function(data) {
    	  console.log("success ")
    	  alert("删除成功")
      },
      error: function(data) {
    	  console.log(data)
		  alert("删除出错:"+data.responseText)
      }
   });			
}

// 展开树节点
function expandAll(ztree)
{
	var treeObj = $.fn.zTree.getZTreeObj(ztree); 
	treeObj.expandAll(true);  
}

// 折叠树节点
function contractAll(ztree)
{
	var treeObj = $.fn.zTree.getZTreeObj(ztree); 
	treeObj.expandAll(false);  
}
