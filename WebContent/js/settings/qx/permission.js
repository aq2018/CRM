$(function() {
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	$("#edit").click(function() {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");//获取机构树对象
		var nodes = treeObj.getSelectedNodes();//获取修改的节点对象
		if (nodes.length == 0) {
			alert("请选择许可");
		} else {
			$("#editPermissionModal").modal("show");
		}
	})
});//end-------------------------------------end----------------end
var setting = {
	edit : {
		enable : true,
		showRenameBtn : false,
		showRemoveBtn : true
	},
	view : {//禁止多选
		selectedMulti : false
	},
	data : {
		simpleData : {
			enable : true,
		},
	},
	callback : {
		onClick : zTreeOnClick,
		beforeRemove : zTreeBeforeRemove,
	},
	async : {
		enable : true,
		url : "settings/qx/permission/getTree",
	}
};

var zNodes = [];

function zTreeOnClick(event, treeId, treeNode) {
	$.ajax({
		type : "get",
		cache : false,
		url : "settings/qx/permission/detail",
		data : {
			"id" : treeNode.id
		},
		success : function(json) {
			//为页面赋值
			$("#t-name").html(json.name + "&nbsp;<small>许可明细</small>");
			$("#p-name").text(json.name);
			$("#p-modalURL").html(json.modalURL == null ? "&nbsp;" : json.modalURL);
			$("#p-operateURL").html(json.operateURL == null ? "&nbsp;" : json.operateURL);
			$("#p-sortNo").html(json.sortNo == null ? "&nbsp;" : json.sortNo );
			//编辑框赋值
			$("#h-id").val(json.id);
			$("#edit-permissionName").val(json.name);
			$("#edit-moduleUrl").val(json.modalURL);
			$("#edit-operationUrl").val(json.operateURL);
			$("#edit-orderNo").val(json.sortNo);
		}
	})
};

function add() {
	$("#addForm")[0].reset();
}
function save() {
	$("#tip1").text("");
	var id = $("#h-id").val();
	$.ajax({
		type : "post",
		url : "settings/qx/permission/save",
		data : {
			"name" : $.trim($("#create-permissionName").val()),
			"modalURL" : $.trim($("#create-moduleUrl").val()),
			"operateURL" : $.trim($("#create-operationUrl").val()),
			"sortNo" : $.trim($("#create-orderNo").val()),
			"pid" : id
		},
		cache : false,
		beforeSend : function() {
			if ($("#create-permissionName").val() == "") {
				$("#tip1").text("请输入许可名称");
				return false;
			}
			return true;
		},
		success : function(json) {
			if (json.success) {
				//添加许可树
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				var parent = treeObj.getNodeByParam("id", id);
				var newNode = {
					"id" : json.id,
					"name" : $.trim($("#create-permissionName").val())
				};
				treeObj.addNodes(parent, newNode);
				$("#createPermissionModal").modal("hide");
			} else {
				alert("新增失败");
			}
		}
	})
};
function update() {
	$("#tip2").text("");
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");//获取机构树对象
	var nodes = treeObj.getSelectedNodes();//获取修改的节点对象
	var name = $.trim($("#edit-permissionName").val());
	$.ajax({
		type : "post",
		url : "settings/qx/permission/update",
		data : {
			"id" : nodes[0].id,
			"name" : name,
			"modalURL" : $.trim($("#edit-moduleUrl").val()),
			"operateURL" : $.trim($("#edit-operationUrl").val()),
			"sortNo" : $.trim($("#edit-orderNo").val()),
		},
		cache : false,
		beforeSend : function() {
			if (nodes.length == 0) {
				alert("请选择许可");
				return false;
			}
			if ($("#edit-permissionName").val() == "") {
				$("#tip2").text("请输入许可名称");
				return false;
			}
			return true;
		},
		success : function(json) {
			if (json.success) {
				$("#editPermissionModal").modal("hide");
				//更改页面内容
				$("#t-name").html(name + "<small>许可明细</small>");
				$("#p-name").text(name);
				$("#p-modalURL").html($("#edit-moduleUrl").val() + "&nbsp;");
				$("#p-operateURL").html($("#edit-operationUrl").val() + "&nbsp;");
				$("#p-sortNo").html($("#edit-orderNo").val() + "&nbsp;");
				//修改许可树
				nodes[0].name = name;//改变该节点属性
				treeObj.updateNode(nodes[0]);//更新

			} else {
				alert("修改失败");
			}
		}
	})
};

function zTreeBeforeRemove(treeId, treeNode) {//删除之前回调函数,返回true删除一个节点,返回false不删除
	//alert(treeNode.getParentNode().id);
	if (treeNode.level == 0) {
		alert("根机构不能删除");
		return false;
	} else if (treeNode.isParent) {
		alert("该机构下有子机构不能删除")
		return false;
	} else {
		if (confirm("确定删除选中的机构")) {
			return remove(treeNode.id, treeNode);
		}
		return false
	}
};

//删除机构
function remove(treeId, treeNode) {//参数删除节点treeId,删除节点对象treeNode
	var success = false;//删除机构成功标识
	$.ajax({
		type : "post",
		url : "settings/qx/permission/del",
		data : {
			"id" : treeId
		},
		async : false,//设置同步
		error : function() {
			alert("网络延迟");
		},
		success : function(json) {
			if (json.success) {
				//删除后跳转至父机构明细
				var parent = treeNode.getParentNode();
				zTreeOnClick(null, parent.id, parent);//调用click发送ajax显示父节点明细
				success = true;
			} else {
				alert("删除失败");
			}
		}
	})
	return success;
}
