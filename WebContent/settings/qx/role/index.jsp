<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<!--分页插件-->
	<link rel="stylesheet" href="jquery/bs_pagination/css/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/locale/bs_pagination.zh-CN.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/js/jquery.bs_pagination.min_zh-CN.js"></script>
<script type="text/javascript">
	$(function(){
		//**********页码
		/* var pageNo = 1;
		var pageSize = 5;
		var totalRows = 53;
		var totalPages = totalRows % pageSize == 0 ? totalRows / pageSize : parseInt(totalRows / pageSize) + 1; */
		
		//*********************
		//创建
		$("#createBtn").click(function(){
			$("#addForm")[0].reset();
			$("#nameTip").text("");
			$("#noTip").text("");
			$("#create-roleCode").focus();
			$("#createRoleModal").modal("show");
		});
		//保存
		// data-dismiss="modal"
		$("#saveBtn").click(function(){
			$("#create-roleCode").blur();
			$("#create-roleName").blur();
			if($("#noTip").text() == "" && $("#nameTip").text() == ""){
				save();				
			}
		});
		
		//验证
		$("#create-roleCode").blur(function(){
			var no = $.trim(this.value);
			if( no == ""){
				$("#noTip").text("请输入角色编号代码")
			}else{
				$.ajax({
					type : "post",
					url : "settings/qx/role/check.do",
					data : {"no":no},
					cache : false,
					success : function(json){
						if(json.success){
							$("#noTip").text("");
						}else{
							$("#noTip").text("重复的角色代码");
						}
					}
				})
			};
			
		});
		$("#create-roleCode").focus(function(){
			var no = $.trim(this.value);
			if(no == ""){
				$(this).val("");
				$("#noTip").text("请输入角色编号代码");
			}
		});
		//名称
		$("#create-roleName").blur(function(){
			var no = $.trim($("#create-roleCode").val());
			var name = $.trim(this.value);
			if( no == ""){
				$("#noTip").text("请输入角色编号代码");
			}
			if(name == ""){
				$("#nameTip").text("请输入角色名称");
			}else{
				$("#nameTip").text("");
			}
		});
		
		$("#create-roleName").focus(function(){
			var name = $.trim(this.value);
			if(name == ""){
				$(this).val("");
				$("#nameTip").text("请输入角色名称");
			};
		});
		
		//删除部门
		$("#delBtn").click(function(){
			$fxs = $("input[name='fx']:checked");
			if($fxs.length == 0){
				alert("请选择一条数据删除");
			}else{
				//{"id":10,"id":20}
				//"id=10&id=20",
				var roles = "";
				for(var i = 0;i < $fxs.length;i++){
					roles += "id="+$($fxs[i]).val();
					if(i < $fxs.length - 1){
						roles += "&";
					}
				};
				//{}
				$.post(
					"settings/qx/role/del.do",
					roles,
					function(json){
						if(json.success){
							getRole(1,10);
						}else{
							alert("删除失败");
						}
					}
				);
			}
		});
		
		//全选
		$("#qx").click(function(){
			$("input[name='fx']").prop("checked",this.checked);
		});
		//
		$("#tbody").on("click","input[name='fx']",function(){
			$("#qx").prop("checked",$("input[name='fx']").size() == $("input[name='fx']:checked").size());
		});
		//***************
		//创建
		function save(){
			$.ajax({
				type : "post",
				url : "settings/qx/role/add.do",
				data : {
					"no" : $.trim($("#create-roleCode").val()),
					"name" : $.trim($("#create-roleName").val()),
					"description" : $.trim($("#create-describe").val())
				},
				cache : false,
				success : function(json){
					if(json.success){
						$("#createRoleModal").modal("hide");
						getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
					}else{
						alert("创建失败");
					}
				}
			});
		}
		//获取列表
		//getRole(1,$("#page").bs_pagination('getOption','rowsPerPage'));
		getPage(1,10);
	});
	
	function getPage(pageNo,pageSize){
		$("#tbody").html("");
		$.get(
			"settings/qx/role/list.do",
			{
				"pageNo":pageNo,
				"pageSize":pageSize,
				
				
				
			},
			function(data){
				//{"total":4,"roleList":[{"id":"2","no":"002","name":"销售总监","description":null},{}]}
				var i = 1;
				$(data.roleList).each(function(){
					$("#tbody").append("<tr><td><input type='checkbox' name='fx' value='" + this.id + "'/></td><td>" + (i++)
							+ "</td><td><a href='settings/qx/role/detail.do?id=" + this.id + "' style='text-decoration: none;''>" + this.no
							+ "</a></td><td>" + this.name + "</td><td>" + this.name + "</td></tr>");
					$("#tbody tr:even").addClass("active");
				});
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
						getRole(data.currentPage , data.rowsPerPage);
					}
				});
			},
			"json"
		);
		
	}
	
</script>
</head>
<body>

	<!-- 创建角色的模态窗口 -->
	<div class="modal fade" id="createRoleModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">新增角色</h4>
				</div>
				<div class="modal-body">
				
					<form id="addForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label id="no" for="create-roleCode" class="col-sm-2 control-label">代码<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-roleCode" style="width: 200%;">
								<span id="noTip" style="color: red;font-size: 12px"></span>
							</div>
						</div>
						
						<div class="form-group">
							<label id="name" for="create-roleName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-roleName" style="width: 200%;">
								<span id="nameTip" style="color: red;font-size: 12px"></span>
							</div>
						</div>
						
						<div class="form-group">
							<label id="description" for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 65%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<div>
		<div style="position: relative; left: 30px; top: -10px;">
			<div class="page-header">
				<h3>角色列表</h3>
			</div>
		</div>
	</div>
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
		  <button id="createBtn" type="button" class="btn btn-primary" data-toggle="modal"><span class="glyphicon glyphicon-plus"></span> 创建</button>
		  <button id="delBtn" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	<div style="position: relative; left: 30px; top: 20px;">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" id="qx" /></td>
					<td>序号</td>
					<td>代码</td>
					<td>名称</td>
					<td>描述</td>
				</tr>
			</thead>
			<tbody id="tbody">
				<!-- <tr class="active">
					<td><input type="checkbox" /></td>
					<td>1</td>
					<td><a href="detail.jsp" style="text-decoration: none;">001</a></td>
					<td>管理员</td>
					<td>管理员为最高角色，拥有所有许可</td>
				</tr>
				<tr>
					<td><input type="checkbox" /></td>
					<td>2</td>
					<td><a href="settings/qx/role/detail.jsp" style="text-decoration: none;">002</a></td>
					<td>销售总监</td>
					<td>销售总监销售总监销售总监销售总监销售总监</td>
				</tr>
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>3</td>
					<td><a href="settings/qx/role/detail.html" style="text-decoration: none;">003</a></td>
					<td>市场总监</td>
					<td>市场总监市场总监市场总监市场总监</td>
				</tr> -->
			</tbody>
		</table>
	</div>
	
	<div style="height: 50px; position: relative;top: 30px; left: 30px;">
		<div id="page"></div>
	</div>
			
</body>
</html>