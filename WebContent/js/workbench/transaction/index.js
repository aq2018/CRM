$(function(){
	//定制字段
	$("#definedColumns > li").click(function(e) {
		//防止下拉菜单消失
        e.stopPropagation();
    });
	//
	getPage(1,10);
	//查询
	$("#query").click(function(){
		$("#h-owner").val($("#q-owner").val());
		$("#h-transactionName").val($("#q-transactionName").val());
		$("#h-customerName").val($("#q-customerName").val());
		$("#h-stage").val($("#q-stage").val());
		$("#h-type").val($("#q-type").val());
		$("#h-clueSource").val($("#q-clueSource").val());
		$("#h-contactName").val($("#q-contactName").val());
		getPage(1,10);
	});
	
	$("#qx").click(function(){
		$("input[name='trans']").prop("checked",this.checked);
	});
	$("#tbody").on("click","input[name='trans']",function(){
		$("#qx").prop("checked",$("input[name='trans']").size() == $("input[name='trans']:checked").size());
	});
	
	//del
	$("#delBtn").click(function(){
		if($("input[name='trans']:checked").size() == 0){
			alert("请选择一条数据删除");
		}else{
			if(confirm("删除"+$("input[name='trans']:checked").size() +"条交易数据？,与之相关的备注及历史也将删除，确定删除？")){
				var sendData = "";
				$("input[name='trans']:checked").each(function(){
					sendData += "&id=" + this.value;
				});
				$.ajax({
					type : "post",
					url : "workbench/transaction/del",
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
			url : "workbench/transaction/imports",
			cache : false,
			data : new FormData($("#importForm")[0]),
			processData : false,
			contentType : false,
			success : function(json){
				if(json.success){
					alert("成功导入"+json.count+"条数据");
					$("#importTransModal").modal("hide");
					getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
				}else{
					alert("导入失败");
				}
			}
			
		});
	});
	
	//导出选中
	$("#exportChk").click(function(){
		if($("input[name='trans']:checked").size() == 0){
			alert("请选择至少一条数据导出");
		}else{				
			var sendData = "";
			$("input[name='trans']:checked").each(function(){
				sendData += "&id=" + this.value; 
			});
			window.location.href = "workbench/transaction/exportChk?" + sendData.substr(1);
		}
	});
	//导出全部
	$("#exportAll").click(function(){
		if(confirm("确认导出全部？")){
			window.location = "workbench/transaction/exportAll";				
		}
	});
});//end--end
//编辑
function edit(){
	var trans = $("input[name='trans']:checked");
	if(trans.size() == 0){
		alert("请选择一条交易修改");
	}else if(trans.size() != 1){
		alert("一次修改一条交易");
	}else{
		window.location = "workbench/transaction/edit?id=" + trans.val();
	}
};

//显示联系人
function getPage(pageNo,pageSize){
	$("#q-owner").val($("#h-owner").val());
	$("#q-transactionName").val($("#h-transactionName").val());
	$("#q-customerName").val($("#h-customerName").val());
	$("#q-stage").val($("#h-stage").val());
	$("#q-type").val($("#h-type").val());
	$("#q-clueSource").val($("#h-clueSource").val());
	$("#q-contactName").val($("#h-contactName").val());
	$.post(
		"workbench/transaction/query",
		{
			"pageNo" : pageNo,
			"pageSize" : pageSize,
			"owner" : $.trim($("#h-owner").val()),
			"name" : $.trim($("#h-transactionName").val()),
			"customerId" : $.trim($("#h-customerName").val()),
			"stage" : $.trim($("#h-stage").val()),
			"type" : $.trim($("#h-type").val()),
			"source" : $.trim($("#h-clueSource").val()),
			"contactId" : $.trim($("#h-contactName").val()),
			"_":new Date().getTime()
		},
		function(data){
			var html = "";
			$(data.dataList2).each(function(){
				html +=  '<tr id='+this.id+'>';
				html +=  '<td><input type="checkbox" name="trans" value="'+this.id+'"/></td>';
				html +=  '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/transaction/detail?id='+this.id+'\'">'+this.customerName+'-'+this.name+'</a></td>';
				html +=  '<td>'+this.customerName+'</td>';
				html +=  '<td>'+this.stage+'</td>';
				html +=  '<td>'+this.type+'</td>';
				html +=  '<td>'+this.owner+'</td>';
				html +=  '<td>'+this.source+'</td>';
				html +=  '<td>'+(this.contactName == "" ? "-" : this.contactName)+'</td>';
				html +=  '</tr>';
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