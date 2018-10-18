$(function(){
	//定制字段
	$("#definedColumns > li").click(function(e) {
		//防止下拉菜单消失
        e.stopPropagation();
    });
	$("#create-nextContactTime").datetimepicker({
		minView: "month",
		language:  'zh-CN',
		format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
	});
	$("#create-nextContactTime2").datetimepicker({
		minView: "month",
		language:  'zh-CN',
		format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
	});
	//点击创建
	$("#createBtn").click(function(){
		getOwner($("#create-customerOwner"));
	});
	
	//添加
	$("#saveBtn").click(function(){
		/*if(表单验证){
			
		}else{
			
		}*/
		$.post(
			"workbench/customer/add",
			{
				"owner" : $.trim($("#create-customerOwner").val()),
				"name" : $.trim($("#create-customerName").val()),
				"website" : $.trim($("#create-website").val()),
				"phone" : $.trim($("#create-phone").val()),
				"description" : $.trim($("#create-description").val()),
				"contactSummary" : $.trim($("#create-contactSummary").val()),
				"nextContactTime" : $.trim($("#create-nextContactTime").val()),
				"address" : $.trim($("#create-address1").val())
			},
			function(json){
			    if(json.success){
					$("#createCustomerModal").modal("hide");
					getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
			    }else{
			        alert("添加失败了");
			    }
			}
		)
	});
	//点击修改
	$("#editBtn").click(function(){
		getOwner($("#edit-customerOwner"));
		//编辑
		if($("input[name='fx']:checked").size() != 1){
			alert("请选择一条数据修改");
		}else{
			$("#editCustomerModal").modal("show");
			$.post(
					"workbench/customer/edit",
					{
						"id" : $("input[name='fx']:checked").val(),
					},
					function(json){
				    	$(json).each(function(){
				    		$("#editId").val(this.id);
				    		$("#edit-customerOwner").val(this.owner);
					    	$("#edit-customerName").val(this.name);
					    	$("#edit-website").val(this.website);
					    	$("#edit-phone").val(this.phone);
					    	$("#edit-describe").val(this.description);
					    	$("#create-contactSummary1").val(this.contactSummary);
					    	$("#create-nextContactTime2").val(this.nextContactTime);
					    	$("#create-address").val(this.address)
				    	})
					}
				);
		}
	});
	
	//update
	$("#updateBtn").click(function(){
		/* if(表单验证){
			
		} */
		$.ajax({
			type : "post",
			url : "workbench/customer/update",
			data : {
				"id" : $("#editId").val(),
				"owner" : $.trim($("#edit-customerOwner").val()),
				"name" : $.trim($("#edit-customerName").val()),
				"website" : $.trim($("#edit-website").val()),
				"phone" : $.trim($("#edit-phone").val()),
				"description" : $.trim($("#edit-describe").val()),
				"contactSummary" : $.trim($("#create-contactSummary1").val()),
				"nextContactTime" : $.trim($("#create-nextContactTime2").val()),
				"address" : $.trim($("#create-address").val())

			},
			cache : false,
			success : function(json){
				if(json.success){
					$("#editCustomerModal").modal("hide");
					getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
				}else{
					alert("更新失败");
				}
			}
			
		});
	});
	
	//del
	$("#delBtn").click(function(){
		if($("input[name='fx']:checked").size() == 0){
			alert("请选择一条数据删除");
		}else{
			if(confirm("删除"+$("input[name='fx']:checked").size() +"条数据？")){
				var sendData = "";
				$("input[name='fx']:checked").each(function(){
					sendData += "&id=" + this.value;
				});
				$.ajax({
					type : "post",
					url : "workbench/customer/del",
					cache : false,
					data : sendData.substr(1),
					success : function(json){
						if(json.success){
							getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
							$("#qx").prop("checked",false);
						}else{
							alert("删除失败");
						}
					}
				});
			}
		}
	});
	
	//导入
	$("#importBtn").click(function(){
		$.ajax({
			type : "post",
			url : "workbench/customer/imports",
			cache : false,
			data : new FormData($("#importForm")[0]),
			processData : false,
			contentType : false,
			success : function(json){
				if(json.success){
					alert("成功导入"+json.count+"条数据");
					$("#importCustomerModal").modal("hide");
					getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
				}else{
					alert("导入失败");
				}
			}
			
		});
	});
	
	//导出选中
	$("#exportChk").click(function(){
		if($("input[name='fx']:checked").size() == 0){
			alert("请选择至少一条数据导出");
		}else{				
			var sendData = "";
			$("input[name='fx']:checked").each(function(){
				sendData += "&id=" + this.value; 
			});
			window.location.href = "workbench/customer/exportChk?" + sendData.substr(1);
		}
	});
	//导出全部
	$("#exportAll").click(function(){
		if(confirm("确认导出全部？")){
			window.location = "workbench/customer/exportAll";				
		}
	});
	
	//获取客户列表
	getPage(1,10);
	
	//查询
	$("#queryBtn").click(function(){
		var owner = $.trim($("#q-owner").val());
		var name = $.trim($("#q-name").val());
		var phone = $.trim($("#q-phone").val());
		var website = $.trim($("#q-website").val());
		
		$("#h-owner").val(owner);
		$("#h-name").val(name);
		$("#h-phone").val(phone);
		$("#h-website").val(website);
		getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
	})
	
	$("#qx").click(function(){
		$("input[name='fx']").prop("checked",this.checked);
	});
	
	//on 父级元素.on(绑定事件,绑定子元素,回掉函数)
	$("#tbody").on("click","input[name='fx']",function(){
		$("#qx").prop("checked",$("input[name='fx']:checked").size() == $("input[name='fx']").size());
	});
	
	//添加备注
	
	
})//end
//查询数据
function getPage(pageNo,pageSize){
	var owner = $.trim($("#h-owner").val());
	var name = $.trim($("#h-name").val());
	var phone = $.trim($("#h-phone").val());
	var website = $.trim($("#h-website").val());
	
	$("#q-owner").val(owner);
	$("#q-name").val(name);
	$("#q-phone").val(phone);
	$("#q-website").val(website);
	
	$("#tbody").html("");
	$.post(
			"workbench/customer/query",
			{
				"pageNo":pageNo,
				"pageSize":pageSize,
				"owner":owner,
				"phone":phone,
				"website":website
			},
			function(data){
				var html = "";
				$(data.dataList).each(function(){
					html += '<tr>';
                    html += '<td><input type="checkbox" name="fx" value='+this.id+'></td>';
                    html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/customer/detail?id='+this.id+'\';">'+this.name+'</a></td>';
                    html += '<td>'+this.owner+'</td>';
                    html += '<td>'+(this.phone == null ? "-" : this.phone)+'</td>';
                    html += '<td>'+(this.website == null ? "-" : this.website)+'</td>';
                	html += '</tr>';
				});
				$("#tbody").html(html);
				var totalPages = Math.ceil(data.total / pageSize);
				//$("#total").html("共<b>"+data.total+"</b>条记录");
				$("#tbody tr:even").addClass("active");
				$("#page").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 10, // 每页最多显示的记录条数
					totalPages: totalPages, // 总页数//Math.ceil(getTotalCount()/getPageSize());
					totalRows: data.total, // 总记录条数

					visiblePageLinks: 5, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						getPage(data.currentPage , data.rowsPerPage);
					}
				});
			},
			"json"
		);
};