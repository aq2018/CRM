$(function(){
		$("#create-expectedClosingDate").datetimepicker({
		minView: "month",
		language:  'zh-CN',
		format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-right"
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
		//item : 2
	});
	
	
	$("#save").click(function(){
		/* if(){
			表单校验
		} */
		
		$.ajax({
			type : "post",
			url : "workbench/transaction/save",
			data : {
				"owner" : $.trim($("#create-transactionOwner").val()),
				"money" : $.trim($("#create-amountOfMoney").val()),
				"name" : $.trim($("#create-transactionName").val()),
				"expectedDate" : $.trim($("#create-expectedClosingDate").val()),
				"customerId" : $.trim($("#create-customerName").val()),
				"stage" : $.trim($("#create-transactionStage").val()),
				"type" : $.trim($("#create-transactionType").val()),
				"source" : $.trim($("#create-clueSource").val()),
				"activityId" : $.trim($("#create-activityId").val()),
				"contactId" : $.trim($("#create-contactsId").val()),
				"description" : $.trim($("#create-describe").val()),
				"contactSummary" : $.trim($("#create-contactSummary").val()),
				"nextContactTime" : $.trim($("#create-nextContactTime").val())
			},
			cache : false,
			success : function(json){
				if(json.success){
					window.location = "workbench/transaction/index.jsp";
				}else{
					alert("添加失败");
				}
			}
		});
	});
	
	//获取阶段可能性
	$("#create-transactionStage").change(function(){
		var stage = this.value;
		$("#create-possibility").val(json[stage]);
	});
	
	//搜索联系人框
	$("#searchContact").keydown(function(event){
		if(event.keyCode == 13){
			searchContacts(1,10);
			return false;
		}
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
		$("#create-activityId").val(id);
		$("#create-activitySrc").val($("#n_"+id).text());
		$("#findMarketActivity").modal("hide");
	});
	
	
	
	//确定联系人
	$("#chooseContacter").click(function(){
		var id = $("input[name='contact']:checked").val();
		$("#create-contactsId").val(id);
		$("#create-contactsName").val($("#fn_"+id).text());
		$("#findContacts").modal("hide");
	});
	
	
	
});//end_end
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