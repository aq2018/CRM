$(function(){
		//日期插件
	function formatDate(now) { 
		var year=new Date(now).getFullYear(); 
		var month=new Date(now).getMonth()+1; 
		var date=new Date(now).getDate(); 
		var hour=new Date(now).getHours(); 
		var minute=new Date(now).getMinutes(); 
		var second=new Date(now).getSeconds(); 
		return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
	}
	$("#startTime").datetimepicker({
		minView: "month",
		language:  'zh-CN',
		format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
	});
	
	$("#endTime").datetimepicker({
		minView: "month",
		language:  'zh-CN',
		format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
	});
	$("#create-expireTime").datetimepicker({
		minView: "month",
		language:  'zh-CN',
		format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
	});
	//全选
	$("#qx").click(function(){
		if($(this).is(":checked")){
			$("input[name='fx']").prop("checked",true);
		}else{
			$("input[name='fx']").removeProp("checked");
		}
	});
	
	$("#tbody").on("click","input[name='fx']",function(){
		$("#qx").prop("checked",$("input[name='fx']:checked").size() == $("input[name='fx']").size());
	});
	
	//删除
	$("#delBtn").click(function(){
		if($("input[name='fx']").size() == 0){
			alert("请选择一条数据删除");
		}else{
			var sendData = "";
			$("input[name='fx']:checked").each(function(){
				sendData += "&id=" + $(this).val();
			});
			if(confirm("确定删除"+$("input[name='fx']:checked").size()+"条数据？")){
				$.post(
					"settings/qx/user/del.do?",
					sendData.substr(1),
					function(json){
						if(json.success){
							getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
						}else{
							alert("删除失败");
						}
					}
				)
			}
		}
	});
	//表单验证
	$("#create-loginActNo").blur(function(){
		var val = $.trim(this.value);
		if(val == ""){
			$("#tip1").text("请输入账号");
		}else{
			$("#tip1").text("");
			$.ajaxSetup({
				async : false
			});
			$.ajax({
				type : "get",
				url : "settings/qx/user/checkAct.do",
				data : {"account" : val},
				success : function(json){
					if(json.success){
						$.ajaxSetup({
							async : true
						});
						$("#tip1").text("");
					}else{
						$("#tip1").text("已有该账号");
					}
				}
			})
		}
	});
	$("#create-loginActNo").focus(function(){
		var val = $.trim(this.value);
		if(val == ""){
			$(this).val("");
		}
	});
	$("#create-loginPwd").blur(function(){
		var val = this.value;
		if(val == ""){
			$("#tip3").text("请输入密码");
		}else{
			$("#tip3").text("");
		}
	});
	$("#create-loginPwd").focus(function(){
		var val = this.value;
		if(val == ""){
			$(this).val("");
		}
	});
	$("#create-confirmPwd").blur(function(){
		var passwd = $("#create-loginPwd").val();
		var confPwd = this.value;
		if(passwd != confPwd){
			$("#tip4").text("两次密码不一致");
		}else{
			$("#tip4").text("");
		}
	});
	$("#create-dept").blur(function(){
		var val = this.value;
		if(val == ""){
			$("#tip6").text("请选择部门");
		}else{
			$("#tip6").text("");
		}
	});
	$("#create-allowIps").blur(function(){
		$("#tip7").text("");
		var regExp = /^(\d+.\d+.\d+.\d+)(,(\d+.\d+.\d+.\d+))*$/;
		var val = $.trim(this.value);
		if(val != "" && !regExp.test(val)){
			$("#tip7").text("请输入正确的ip");
		}
	})
	//新建
	$("#saveBtn").click(function(){
		$("#create-loginActNo").blur();
		$("#create-username").blur();
		$("#create-loginPwd").blur();
		$("#create-confirmPwd").blur();
		$("#create-dept").blur();
		if($("#tip1").text() == "" && $("#tip3").text() == "" && $("#tip4").text() == "" && $("#tip6").text() == "" && $("#tip7").text("")){
			 $.post(
				"settings/qx/user/add.do",
				{
					"account" : $.trim($("#create-loginActNo").val()),
					"username" : $.trim($("#create-username").val()),
					"password" : $.trim($("#create-loginPwd").val()),
					"email" : $.trim($("#create-email").val()),
					"invalid_time" : $.trim($("#create-expireTime").val()),
					"lockState" : $.trim($("#create-lockStatus").val()),
					"deptname" : $.trim($("#create-dept").val()),
					"permit_ip" : $.trim($("#create-allowIps").val()),
				},
				function(json){
					if(json.success){
						$("#createUserModal").modal("hide");
						getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
					}else{
						alert("新增失败");
					}
				}
			)
		}
	});
	
	//查询
	$("#queryBtn").click(function(){
		$("#h-username").val($("#username").val());
		$("#h-deptname").val($("#deptname").val());
		$("#h-chooseState").val($("#chooseState").val());
		$("#h-startTime").val($("#startTime").val());
		$("#h-endTime").val($("#endTime").val());
		getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
	})
	//查询
	
	//创建按钮
	$("#createBtn").click(function(){
		$("#addForm")[0].reset();
		$("#create-dept").html("<option value=''>--选择部门--</option>")//清空原来的下拉数据
		getDept($("#create-dept"));//获取部门列表
	});
	
	//获取部门列表
	//{"dname":"1111","deptno":"1111"}
	function getDept(dom){
		$.post(
				"settings/dept/getDeptNameList.do",
				function(data){
					 $(data).each(function(){
						 dom.append("<option value='"+this.deptno+"'>"+this.dname+"</option>");
					 })
				}
			)
	};
	getPage(1,10);
});//end--end--end-----------------------------------------------------end----------------------------------end
function getPage(pageNo,pageSize){
		$("#username").val($("#h-username").val());
		$("#deptname").val($("#h-deptname").val());
		$("#chooseState").val($("#h-chooseState").val());
		$("#startTime").val($("#h-startTime").val());
		$("#endTime").val($("#h-endTime").val());
		$("#tbody").html("");
		var username = $("#username").val();
		var deptname = $("#deptname").val();
		var state = $("#chooseState").val();
		var start_time = $("#startTime").val();
		var end_time = $("#endTime").val();
		$.post(
			"settings/qx/user/query.do",
			{
				"pageNo":pageNo,
				"pageSize":pageSize,
				"username":username,
				"deptname":deptname,
				"state":state,
				"start_time":start_time,
				"end_time":end_time
			},
			function(data){
				var i = 1;
				var html = "";
				$.each(data.dataList2,function(i,n){
					html += '<tr>';
					html += '<td><input type="checkbox" value=' + n.id + ' name="fx"/></td>';
					html += '<td>' + i + '</td>';
					html += '<td><a href="settings/qx/user/userInfo.do?account=' + n.account + '">' + n.account + '</a></td>';
					html += '<td>' + n.username + '</td>';
					html += '<td>' + n.deptname + '</td>';
					html += '<td>' + n.email + '</td>';
					html += '<td>' + n.invalid_time + '</td>';
					html += '<td>' + n.permit_ip + '</td>';
					html += '<td onclick="changeState(\'' + n.id + '\');"><a id="s_' + n.id + '" href="javascript:void(0);" style="text-decoration: none;">' + n.lockState + '</a></td>';
					html += '<td>' + n.createBy + '</td>';
					html += '<td>' + n.createTime + '</td>';
					html += '<td id="e_' + n.id + '">' + n.editBy + '</td>';
					html += '<td id="et_' + n.id + '">' + n.editTime + '</td>';
					html += '</tr>';
				})
				$("#tbody").html(html);
				$("#total").html("共<b>"+data.total+"</b>条记录");
				$("#tbody tr:even").addClass("active");
				var totalPages = Math.ceil(data.total / pageSize);
				$("#page").bs_pagination({
					currentPage: pageNo,
					rowsPerPage: pageSize,
					maxRowsPerPage: 10,
					totalPages: totalPages,
					totalRows: data.total,

					visiblePageLinks: 5,

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
	}

function changeState(id){
	var state = ($("#s_"+id).text() == "锁定" ? "启用" : "锁定");
	if(confirm(state+"该用户?")){//(this.state == "锁定" ? "启用" : "锁定")
		$.post(
			"settings/qx/user/changeState.do",
			{
				"id" : id,
				"state" : state
			},
			function(json){
				//{"lockState":"","editBy","editTime"}
				if(json.success){
					$("#s_"+id).text(state);
					$("#e_"+id).text(json.user.editBy);
					$("#et_"+id).text(json.user.editTime);
				}else{
					alert(state+"失败");
				}
			}
		)
	}
}