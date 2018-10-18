$(function(){
	//表单验证
	$("#edit-roleCode").focus(function(){
		var no = $.trim(this.value);
		if(no == ""){
			$(this).val("");
			$("#noTip").text("请输入角色编号代码");
		};
	});
	$("#edit-roleCode").blur(function(){
		var no = $.trim(this.value);
		if( no == ""){
			$("#noTip").text("请输入角色编号代码");
		}else{
			$("#noTip").text("");
			$.ajaxSetup({
				async : false
			});
			$.ajax({
				type : "post",
				url : "settings/qx/role/check.do",
				data : {"no":no},
				cache : false,
				success : function(json){
					if(json.success){
						$("#noTip").text("");
					}else{
						if(confirm("已有该角色代码,是否确认修改？")){
							$("#noTip").text("");
						}else{
							$("#noTip").text("已有该角色代码");
						}
					}
					$.ajaxSetup({
						async : true
					});
				}
			})
		}
	});
	
	$("#edit-roleName").focus(function(){
		var name = $.trim(this.value);
		if(name == ""){
			$(this).val("");
			$("#nameTip").text("请输入角色名称");
		}
	});
	//名称
	$("#edit-roleName").blur(function(){
		var no = $.trim($("#edit-roleCode").val());
		var name = $.trim(this.value);
		if( no == ""){
			$("#noTip").text("请输入修改角色的编号代码");
		}
		if(name == ""){
			$("#nameTip").text("请输入角色名称");
		}else{
			$("#nameTip").text("");
		}
	});
	
	
	//更新
	$("#updateBtn").click(function(){
		$("#edit-roleCode").blur();
		$("#edit-roleName").blur();
		if($("#nameTip").text() == "" && $("#noTip").text() == ""){
			 $.post(
				"settings/qx/role/update.do",
				{
					"id" : $("#h-roleId").val(),
					"no" : $.trim($("#edit-roleCode").val()),
					"name" : $("#edit-roleName").val(),
					"description" : $("#edit-describe").val()
				},
				function(json){
					if(json.success){
						$("#editRoleModal").modal("hide");
						$("#r-no").text(json.data.no);
						$("#r-name").text(json.data.name);
						$("#r-desc").text(json.data.description);
					}else{
						alert("更新失败");
					}
				}
			);
		};
	});
})