$(function(){
		//自动补全
	$("#edit-accountName").typeahead({
		source : function(query,process){
			$.post("workbench/customer/searchCustome",{"name":query},function(json){
					process(json);
				})
		},
		delay : 500
		//item : 2
	});
	
	$("#update").click(function(){
		 $.ajax({
			type : "post",
			url : "workbench/transaction/update",
			data : {
				"id" : "${map.transaction.id}",
				"owner" : $("#edit-transactionOwner").val(),
				"money" : $("#edit-amountOfMoney").val(),
				"name" : $("#edit-transactionName").val(),
				"expectedDate" : $("#edit-expectedClosingDate").val(),
				"customerId" : $("#edit-accountName").val(),
				"stage" : $("#edit-transactionStage").val(),
				"type" : $("#edit-transactionType").val(),
				"source" : $("#edit-clueSource").val(),
				"activity" : $("#h-activityId").val(),
				"contactId" : $("#h-contactId").val(),
				"description" : $("#create-describe").val(),
				"contactSummary" : $("#create-contactSummary").val(),
				"nextContactTime" : $("#create-nextContactTime").val()
				},
				success : function(json){
					if(json.success){
						window.location = "workbench/transaction/index.jsp";
					}else{
						alert("更新失败");
					}
				}
			})
	});
	
	//搜索市场活动框
	$("#searchActivity").keydown(function(event){
		if(event.keyCode == 13){
			searchActivitys(1,10);
			return false;
		}
	});
	
	//确定市场活动
	$("#chooseActicity").click(function(){
		var id = $("input[name='activity']:checked").val();
		$("#h-activityId").val(id);
		$("#edit-activitySrc").val($("#n_"+id).text());
		$("#findMarketActivity").modal("hide");
	});
	
	
	//搜索联系人框
	$("#searchContact").keydown(function(event){
		if(event.keyCode == 13){
			searchContacts(1,10);
			return false;
		}
	});
	//确定联系人
	$("#chooseContacter").click(function(){
		var id = $("input[name='contact']:checked").val();
		$("#h-contactId").val(id);
		$("#edit-contactsName").val($("#fn_"+id).text());
		$("#findContacts").modal("hide");
	});
	
	
});//end

function search1(){//联系人
	$("#contactTbody").empty();
	$("#contacterPage").empty();
	$("#findContacts").modal("show");
};
function search2(){//活动
	$("#activityTbody").empty();
	$("#activitysPage").empty();
	$("#findMarketActivity").modal("show");
};

//查找活动----asdasdasd
function searchActivitys(pageNo,pageSize){
	 $.ajax({
		type : "get",
		url : "workbench/transaction/queryAct",
		cache : false,
		data : {
			"name" : $.trim($("#searchActivity").val()),
			"pageNo" : pageNo,
			"pageSize" : pageSize
		},
		success : function(data){
			var html = "";
			$(data.dataList).each(function(){
				html += '<tr>';
				html += '<td><input value="'+this.id+'" type="radio" name="activity"/></td>';
				html += '<td id=n_'+this.id+'>'+this.name+'</td>';
				html += '<td>'+this.start_date+'</td>';
				html += '<td>'+this.end_date+'</td>';
				html += '<td>'+this.owner+'</td>';
				html += '</tr>';
			});
			$("#activityTbody").html(html);
			var totalPages = Math.ceil(data.total / pageSize);
			$("#activitysPage").bs_pagination({
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
					searchContacts(data.currentPage , data.rowsPerPage);
				}
			});
		},
	}); 
};
//查找联系人
function searchContacts(pageNo,pageSize){
	$.ajax({
		type : "get",
		url : "workbench/transaction/queryContact",
		cache : false,
		data : {
			"fullname" : $.trim($("#searchContact").val()),
			"pageNo" : pageNo,
			"pageSize" : pageSize
		},
		success : function(data){
			var html = "";
			$(data.dataList).each(function(){
				html += '<tr>';
				html += '<td><input type="radio" name="contact" value='+this.id+'></td>';
				html += '<td id=fn_'+this.id+'>'+this.fullname+'</td>';
				html += '<td>'+this.email+'</td>';
				html += '<td>'+this.mphone+'</td>';
				html += '</tr>';
			});
			$("#contactTbody").html(html);
			var totalPages = Math.ceil(data.total / pageSize);
			$("#contacterPage").bs_pagination({
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
					searchContacts(data.currentPage , data.rowsPerPage);
				}
			});
		},
	}); 
}