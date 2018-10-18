$(function(){
		
		//定制字段
	$("#definedColumns > li").click(function(e) {
		//防止下拉菜单消失
        e.stopPropagation();
    });
	$("#createBtn").click(function(){
		getOwner($("#create-contactsOwner"));
	});
	$("#create-birth").datetimepicker({
		minView: "month",
		language:  'zh-CN',
		format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-right"
	});
	$("#edit-birth").datetimepicker({
		minView: "month",
		language:  'zh-CN',
		format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-right"
	});
	$("#create-nextContactTime1").datetimepicker({
		minView: "month",
		language:  'zh-CN',
		format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "top-right"
	});
	$("#create-nextContactTime").datetimepicker({
		minView: "month",
		language:  'zh-CN',
		format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "top-right"
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
	
	$("#edit-customerName").typeahead({
		source : function(query,process){
			$.post("workbench/customer/searchCustome",{"name":query},function(json){
				process(json);
			})
		},
		delay : 500
	});
	
	//全选
	$("#qx").click(function(){
		$("input[name='contact']").prop("checked",this.checked);
	});
	
	//on 父级元素.on(绑定事件,绑定子元素,回掉函数)
	$("#tbody").on("click","input[name='contact']",function(){
		$("#qx").prop("checked",$("input[name='contact']:checked").size() == $("input[name='contact']").size());
	});
	//导出全部
	$("#exportAllBtn").click(function(){
		if(confirm("导出全部？")){				
			window.location.href = "workbench/contacts/exportAll";
		}
	});
	//导出选中
	$("#exportChkBtn").click(function(){
		if($("input[name='contact']:checked").size() == 0){
			alert("请选择至少一条数据导出");
		}else{				
			var sendData = "";
			$("input[name='contact']:checked").each(function(){
				sendData += "&id=" + this.value; 
			});
			window.location.href = "workbench/contacts/exportChk?" + sendData.substr(1);
		}
	});
	//导入
	$("#importBtn").click(function(){
		$.ajax({
			type : "post",
			url : "workbench/contacts/imports",
			cache : false,
			data : new FormData($("#importForm")[0]),
			processData : false,
			contentType : false,
			success : function(json){
				if(json.success){
					$("##importContactsModal").modal("hide");
					getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
				}else{
					alert("导入失败");
				}
			}
			
		});
	});
	//添加
	$("#saveBtn").click(function(){
		/*if(表单验证){
			
		}else{
			
		}*/
		$.post(
			"workbench/contacts/add",
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
				"contactSummary" : $.trim($("#create-contactSummary1").val()),
				"nextContactTime" : $.trim($("#create-nextContactTime1").val()),
				"address" : $.trim($("#edit-address1").val()),
			},
			function(json){
				if(json.success){
					$("#createContactsModal").modal("hide");
					getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
			    }else{
			        alert("添加失败了");
			    }
			}
		)
	});
	//点击修改
	$("#editBtn").click(function(){
		getOwner($("#edit-contactsOwner"));
		//编辑
		if($("input[name='contact']:checked").size() != 1){
			alert("请选择一条数据修改");
		}else{
			$("#editContactsModal").modal("show");
			$.post(
					"workbench/contacts/edit",
					{"id" : $("input[name='contact']:checked").val()},
					function(json){
						$(json).each(function(){
							$("#edit-contactsOwner").val(this.owner),
							$("#edit-clueSource1").val(this.source),
							$("#edit-surname").val(this.fullname),
							$("#edit-call").val(this.appellation),
							$("#edit-job").val(this.job),
							$("#edit-mphone").val(this.mphone),
							$("#edit-email").val(this.email),
							$("#edit-birth").val(this.birth),
							$("#edit-customerId").val(this.customerId),
							$("#edit-customerName").val(this.customerName),
							$("#edit-describe").val(this.description),
							$("#create-contactSummary1").val(this.contactSummary),
							$("#create-nextContactTime1").val(this.nextContactTime),
							$("#edit-address2").val(this.address)
						})
					}
			)
		}
	});
	$("#qx").click(function(){
		$("input[name='contact']").prop("checked",this.checked);
	});
	
	//on 父级元素.on(绑定事件,绑定子元素,回掉函数)
	$("#tbody").on("click","input[name='contact']",function(){
		$("#qx").prop("checked",$("input[name='contact']:checked").size() == $("input[name='contact']").size());
	});
	//更新
	$("#updateBtn").click(function(){
		/* if(){
			表单验证
		} */
		//更新
		$.post(
				"workbench/contacts/update",
				{
					"id" : $("input[name='contact']:checked").val(),
					"owner" : $.trim($("#edit-contactsOwner").val()),
					"source" : $.trim($("#edit-clueSource1").val()),
					"fullname" : $.trim($("#edit-surname").val()),
					"appellation" : $.trim($("#edit-call").val()),
					"job" : $.trim($("#edit-job").val()),
					"mphone" : $.trim($("#edit-mphone").val()),
					"email" : $.trim($("#edit-email").val()),
					"birth" : $.trim($("#edit-birth").val()),
					"customerId" : $.trim($("#edit-customerId").val()),
					"customerName" : $.trim($("#edit-customerName").val()),
					"description" : $.trim($("#edit-describe").val()),
					"contactSummary" : $.trim($("#create-contactSummary").val()),
					"nextContactTime" : $.trim($("#create-nextContactTime").val()),
					"address" : $.trim($("#edit-address2").val())
				},
				function(json){
					if(json.success){
						$("#editContactsModal").modal("hide");
						getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
				    }else{
				        alert("添加失败了");
				    }
				}
			);
	});
	
	//del
	$("#delBtn").click(function(){
		if($("input[name='contact']:checked").size() == 0){
			alert("请选择一条数据删除");
		}else{
			if(confirm("删除"+$("input[name='contact']:checked").size() +"条数据？")){
				var sendData = "";
				$("input[name='contact']:checked").each(function(){
					sendData += "&id=" + this.value;
				});
				$.ajax({
					type : "post",
					url : "workbench/contacts/del",
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
	
	getPage(1,10);
	
	//查询
	$("#queryBtn").click(function(){
		//$("#queryForm").submit();
		//var owner = $("#owner").val();
		//getPage(pageNo,pageSize,owner);
		$("#h-owner").val($("#q-owner").val());
		$("#h-fullname").val($("#q-fullname").val());
		$("#h-customerName").val($("#q-customerName").val());
		$("#h-clueSource").val($("#edit-clueSource").val());
		$("#h-birth").val($("#q-birth").val());
		getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
	});
});//end--end
//显示联系人
function getPage(pageNo,pageSize){
	$("#q-owner").val($("#h-owner").val());
	$("#q-fullname").val($("#h-fullname").val());
	$("#q-customerName").val($("#h-customerName").val());
	$("#h-clueSource").val($("#edit-clueSource").val());
	$("#q-birth").val($("#h-birth").val());
	$.get(
		"workbench/contacts/query",
		{
			"pageNo":pageNo,
			"pageSize":pageSize,
			"owner" : $.trim($("#h-owner").val()), 
			"fullname" : $.trim($("#h-fullname").val()),    
			"source" : $("#h-clueSource").val(),//edit-clueSource
			"customerName" :  $.trim($("#h-customerName").val()),       
			"birth" : $.trim($("#h-birth").val()),
			"_":new Date().getTime()
		},
		function(data){
			//pList:{"id":"1","owner":"张三","company":"bjw","calls":"先生","surname":"李四","job":"矿工","email":"balabala@xx.com","phone":"010-84846003","state":"无业","source":"广告","description":"balabala","contactSummary":"balabala","nextContactTime":"balabala","address":"北京","website":null}
			//"total":6
			var html = "";
			$(data.dataList).each(function(){
				html += "<tr>";
				html += "<td><input name='contact' type='checkbox' value='"+this.id+"'/></td>";
				html += "<td><a style='text-decoration: none; cursor: pointer;' onclick='window.location.href=\"workbench/contacts/detail?id="+this.id+"\";'>"+this.fullname+"</a></td>";
				html += "<td>"+this.customerName+"</td>";
				html += "<td>"+this.owner+"</td>";
				html += "<td>"+this.source+"</td>";
				html += "<td>"+(this.birth == "" ? "-" : this.birth)+"</td>";
				html += "</tr>";
			});
			$("#tbody").html(html);					
			$("#tbody tr:even").addClass("active");
			var totalPages = Math.ceil(data.total / pageSize);
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
		}
	);
};