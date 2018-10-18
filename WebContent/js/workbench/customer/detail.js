$(function(){
	$("#remark").focus(function(){
		if(cancelAndSaveBtnDefault){
			//设置remarkDiv的高度为130px
			$("#remarkDiv").css("height","130px");
			//显示
			$("#cancelAndSaveBtn").show("2000");
			cancelAndSaveBtnDefault = false;
		}
	});
	$("#edit-nextContactTime").datetimepicker({
		minView: "month",
		language:  'zh-CN',
		format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "top-right"
	});
	$("#create-nextContactTime2").datetimepicker({
		minView: "month",
		language:  'zh-CN',
		format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "top-right"
	});
	$("#create-birth").datetimepicker({
		minView: "month",
		language:  'zh-CN',
		format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-right"
	});
	$("#cancelBtn").click(function(){
		//显示
		$("#cancelAndSaveBtn").hide();
		//设置remarkDiv的高度为130px
		$("#remarkDiv").css("height","90px");
		cancelAndSaveBtnDefault = true;
	});
	//自动补全
	$("#create-customerName").typeahead({
		source : function(query,process){
			$.post("workbench/customer/searchCustome",{"name":query},function(json){
				process(json);
			})
		},
		delay : 500
	});
	
	$("body").on("mouseover",".remarkDiv",function(){
		$(this).children("div").children("div").show();
	});

	$("body").on("mouseout" , ".remarkDiv" , function(){
		$(this).children("div").children("div").hide();
	});
	//更新备注
	$("#updateRemarkBtn").click(function(){
		var noteContent = $.trim($("#editRemark-description").val());
		var id = $("#editRemarkId").val();
		if(noteContent == ""){
			alert("不能为空！");
		}else{
			// 发送ajax post，更新备注
			$.post(
				"workbench/customer/updateRemark",
				{
					"id" : id,
					"noteContent" : noteContent
				},
				function(json){
					// {"success":true , "activityRemark" : {"editTime":"","editBy":"","description":""}}
					//线索 - 李四先生-某某某公司 2017-01-22 10:20:10 由zhangsan
					// {"success":false}
					if(json.success){
						// 隐藏modal
						$("#editRemarkModal").modal("hide");
						// 更新前端div
						$("#_" + id).text(json.remark.noteContent);
						$("#s_" + id).text(json.remark.editTime + " 由" + json.remark.editBy);
					}else{
						alert("更新备注失败！");
					}
				}
			);
		}
	});
	//添加联系人
	$("#saveContact").click(function(){
		/*if(表单验证){
			
		}else{
			
		}*/
		 $.post(
			"workbench/customer/addContact",
			{
				//asdasd
				"owner" : $.trim($("#create-contactsOwner").val()),
				"source" : $.trim($("#create-clueSource").val()),
				"fullname" : $.trim($("#create-surname").val()),
				"appellation" : $.trim($("#create-call").val()),
				"job" : $.trim($("#create-job").val()),
				"mphone" : $.trim($("#create-mphone").val()),
				"email" : $.trim($("#create-email").val()),
				"birth" : $.trim($("#create-birth").val()),
				"customerId" : $.trim($("#create-customerName").val()),
				"description" : $.trim($("#create-describe").val()),
				"contactSummary" : $.trim($("#edit-contactSummary").val()),
				"nextContactTime" : $.trim($("#edit-nextContactTime").val()),
				"address" : $.trim($("#edit-address1").val()),
			},
			function(json){
				if(json.success){
					$("#createContactsModal").modal("hide");
					//刷新获取联系人
				}else{
			        alert("添加失败了");
			    }
			}
		)
	});
	//删除关联联系人
	$("#delContactBtn").click(function(){
		var id = $("#contactId").val();
		 $.ajax({
			type : "get",
			url : "workbench/customer/delContact",
			data : {"id" : id},
			success : function(json){
				if(json.success){
					$("#"+id).remove();
					$("#removeContactsModal").modal("hide");
				}else{
					alert("删除失败了");
				}
			}
			
		});
	});
	//删除交易
	$("#delTransBtn").click(function(){
		var transId = $("#transId").val();
		if(confirm("确定删除这条交易,该交易包括的备注同时将被删除")){
			$.post(
					"workbench/customer/delTrans",
					{"transId" : transId},
					function(json){
						if(json.success){
							$("#" + transId).remove();
							$("#removeTransactionModal").modal("hide");
						}else{
							alert("删除交易失败");
						}
					}
				)
			
		}
	});
})
//编辑备注
function editRemark(id){
	$("#editRemarkId").val(id);
	$("#editRemark-description").val($("#_"+id).text());
	$("#editRemarkModal").modal("show");
	
}
//删除备注
function delRemark(id){
	if(confirm("确认删除备注？")){
		$.get(
				"workbench/customer/delRemark",
				{"id":id},
				function(json){
					if(json.success){
						$("#"+id).remove();
					}else{
						alert("删除失败");
					}
				}
			);
	}
};
