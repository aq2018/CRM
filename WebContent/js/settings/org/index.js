var setting = {
			view: {//禁止多选
				selectedMulti: false
			},
			data: {
				simpleData: {//使用简单json数据类型
					enable: true,
				},
			},
			callback: {
				onClick : zTreeOnClick,
				beforeRemove: zTreeBeforeRemove,
            },
            edit: {
    			enable : true,
    			showRenameBtn : false,
    			showRemoveBtn : true
    		},
            async: {//意思是页面加载执行ajax
				enable: true,//开启异步加载
				url:"settings/org/getOrgTree",//异步获取节点json数据的 URL地址
			},
		};
		
	function zTreeOnClick(event, treeId, treeNode) {
		//alert(treeNode.id + "," +treeNode.name);
		//treeNode.id : 选中节点id
		$.ajax({
			type : "get",
			cache : false,
			url : "settings/org/detail",
			data : {"id" : treeNode.id},
			success : function(json){
				$("#o-orgId").val(json.id);
				$("#o_code").text(json.code);
				$("#o_title").html(json.name + "<small>&nbsp;机构明细 </small>");
				$("#o_name").text(json.name);
				$("#o_orgType").text(json.orgType);
				$("#o_manager").text(json.manager == null ? "" : json.manager);
				$("#o_createBy").html(json.createBy + "&nbsp;&nbsp;");
				$("#o_createTime").text(json.createTime);
				$("#o_editBy").html((json.editBy == null ? "" : json.editBy) + "&nbsp;&nbsp;");
				$("#o_editTime").text(json.editTime == null ? "" : json.editTime);
				$("#o_description").text(json.description == null ? "" : json.description);
				//为编辑界面赋值
				$("#edit-orgCode").val(json.code);
				$("#edit-orgName").val(json.name);
				$("#edit-marketActivityOwner").val(json.orgType);
				$("#edit-manager").val(json.manager);
				$("#edit-orgDescribe").val(json.description);
			}
		})
	};
	var zNodes = [];
$(function(){
	$("#create-orgCode").blur(function(){
		var val = $.trim(this.value);
		if(val == ""){
			$("#tip1").text("请输入机构代码");
		}else{
			$("#tip1").text("");
		}
	});
	$("#create-orgCode").focus(function(){
		$("#tip1").text("");
		var val = $.trim(this.value);
		if(val == ""){
			$(this).val("");
		}
	});
	$("#create-orgName").blur(function(){
		var val = $.trim(this.value);
		if(val == ""){
			$("#tip2").text("请输入机构名称");
		}else{
			$("#tip2").text("");
		}
	});
	$("#create-orgName").focus(function(){
		$("#tip2").text("");
		var val = $.trim(this.value);
		if(val == ""){
			$(this).val("");
		}
	});
	$("#edit-marketActivityOwner").blur(function(){
		var val = $.trim(this.value);
		if(val == ""){
			$("#tip3").text("请选择机构类型");
		}else{
			$("#tip3").text("");
		}
	});
	$("#add").click(function(){
		$("#addForm")[0].reset();
		$("#createOrgModal").modal("show");
	})
	//保存
	$("#save").click(function(){
		$("#tip3").text("");
		$("#create-orgCode").blur();
		$("#create-orgName").blur();
		$("#edit-marketActivityOwner").blur();
		
		if($("#tip1").text() == "" && $("#tip2").text() == "" && $("#tip3").text() == ""){
			save();
		}
	});
	
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	
	//点击编辑按钮
	$("#edit").click(function(){
		if($("#o-orgId").val() == ""){
			alert("您未选择机构");
		}else{
			$("#editOrgModal").modal("show");
		}
	});
	//删除
	$("#del").click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getSelectedNodes();
		
		if(nodes.length == 0){
			alert("请选择删除的机构");
		}else if(nodes[0].level == 0){
			alert("根机构不能删除")
		}else{
			var treeId = nodes[0].id;
			if(nodes[0].isParent){
				alert("该机构下有子机构不能删除")
			}else{
				if(remove(treeId)){
					for (var i=0, l=nodes.length; i < l; i++) {
						treeObj.removeNode(nodes[i]);
					}
				}
			}
		}
	})
})//*****end----end****end----end****end----end****end----end****end----end
//新增
	function save(){
		var id = $("#o-orgId").val();
		$.ajax({
			type : "post",
			url : "settings/org/save",
			data : {
				"code" : $.trim($("#create-orgCode").val()),
				"name" : $.trim($("#create-orgName").val()),
				"orgType" : $("#edit-marketActivityOwner").val(),
				"manager" : $.trim($("#create-manager").val()),
				"description" : $.trim($("#create-orgDescribe").val()),
				"pId" : id
			},
			success : function(json){
				if(json.success){
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					//获取父节点--必须要有这个
					var parent = treeObj.getNodeByParam("id" , id);
					//拼接新加的子节点
					var newNode = {"id" : json.id, "name" : $.trim($("#create-orgName").val())};
					treeObj.addNodes(parent, newNode);
					$("#createOrgModal").modal("hide");
				}else{
					alert("保存失败");
				}
			}
		})
	};
	function zTreeBeforeRemove(treeId,treeNode) {
		//alert(treeNode.getParentNode().id);
		if(treeNode.level == 0){
			alert("根机构不能删除");
			return false;
		}else if(treeNode.isParent){
			alert("该机构下有子机构不能删除")
			return false;
		}else{
			return remove(treeNode.id,treeNode);
		}
	};
	//删除机构
	function remove(treeId,treeNode){
		var success = false;//删除机构成功标识
		if(confirm("确定删除选中的机构")){
			$.ajax({
				type : "post",
				url : "settings/org/del",
				data : {"id" : treeId},
				async : false,
				error : function(){
					alert("网络延迟");
				},
				success : function(json){
					if(json.success){
						//删除后跳转值父机构明细
						var parent = treeNode.getParentNode();
						zTreeOnClick(null, parent.id, parent);
						success = true;
					}else{
						alert("删除失败");
					}
				}
			})
		}
		return success;
	}
	//修改机构
	function update(){
		$("#editTip").text("");
		$.ajax({
			type : "post",
			url : "settings/org/update",
			data : {
				"id" : $("#o-orgId").val(),
				"code" : $.trim($("#edit-orgCode").val()),
				"name" : $.trim($("#edit-orgName").val()),
				"orgType" : $("#edit-marketActivityOwner").val(),
				"manager" : $.trim($("#edit-manager").val()),
				"description" : $.trim($("#edit-orgDescribe").val())
			},
			beforeSend : function(){
				if($.trim($("#edit-orgCode").val()) == ""){
					$("#editTip").text("请输入机构代码");
					return false;
				}
				if($.trim($("#edit-orgName").val()) == ""){
					$("#editTip").text("请输入机构名称");
					return false;
				}
				if($.trim($("#edit-marketActivityOwner").val()) == ""){
					$("#editTip").text("请选择机构类型");
					return false;
				}
				return true;
			},
			success : function(json){
				if(json.success){
					$("#o_code").text(json.org.code);
					$("#o_name").text(json.org.name);
					$("#o_orgType").text(json.org.orgType);
					$("#o_manager").text(json.org.manager);
					$("#o_editBy").html(json.org.editBy + "&nbsp;&nbsp;");
					$("#o_editTime").text(json.org.editTime);
					$("#o_description").text(json.org.description);
					$("#editOrgModal").modal("hide");
					//修改机构树
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					var nodes = treeObj.getSelectedNodes();
					nodes[0].name = $("#edit-orgName").val();
					treeObj.updateNode(nodes[0]);
				}else{
					alert("修改失败");
				}
			}
		});
	}