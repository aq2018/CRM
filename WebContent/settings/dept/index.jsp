<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort}${pageContext.request.contextPath }/">

	<meta charset="UTF-8">
	<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />

	<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="jquery/bs_pagination/css/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/locale/bs_pagination.zh-CN.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/js/jquery.bs_pagination.min_zh-CN.js"></script>
	<script type="text/javascript" src="js/settings/dept/index.js"></script>
</head>
<body>

<!-- 我的资料 -->
<div class="modal fade" id="myInformation" role="dialog">
	<div class="modal-dialog" role="document" style="width: 30%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title">我的资料</h4>
			</div>
			<div class="modal-body">
				<div style="position: relative; left: 40px;">
					姓名：<b>${user.username}</b><br><br>
					登录帐号：<b>${user.account}</b><br><br>
					组织机构：<b>1005，市场部，二级部门</b><br><br>
					邮箱：<b>${user.email}</b><br><br>
					失效时间：<b>${user.invalid_time}</b><br><br>
					允许访问IP：<b>${user.permit_ip}</b>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>

<!-- 修改密码的模态窗口 -->
<div class="modal fade" id="editPwdModal" role="dialog">
	<div class="modal-dialog" role="document" style="width: 70%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title">修改密码</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label for="oldPwd" class="col-sm-2 control-label">原密码</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="oldPwd" style="width: 200%;">
						</div>
					</div>

					<div class="form-group">
						<label for="newPwd" class="col-sm-2 control-label">新密码</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="newPwd" style="width: 200%;">
						</div>
					</div>

					<div class="form-group">
						<label for="confirmPwd" class="col-sm-2 control-label">确认密码</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="confirmPwd" style="width: 200%;">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="window.location.href='login.html';">更新</button>
			</div>
		</div>
	</div>
</div>

<!-- 退出系统的模态窗口 -->
<div class="modal fade" id="exitModal" role="dialog">
	<div class="modal-dialog" role="document" style="width: 30%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title">离开</h4>
			</div>
			<div class="modal-body">
				<p>您确定要退出系统吗？</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="window.location.href='login.html';">确定</button>
			</div>
		</div>
	</div>
</div>

<!-- 顶部 -->
<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
	<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
	<div style="position: absolute; top: 15px; right: 15px;">
		<ul>
			<li class="dropdown user-dropdown">
				<a href="javascript:void(0)" style="text-decoration: none; color: white;" class="dropdown-toggle" data-toggle="dropdown">
					<span class="glyphicon glyphicon-user"></span><b id="u-account">${user.account}</b><span class="caret"></span>
				</a>
				<ul class="dropdown-menu">
					<li><a href="workbench/index.jsp"><span class="glyphicon glyphicon-home"></span> 工作台</a></li>
					<li><a href="settings/index.jsp"><span class="glyphicon glyphicon-wrench"></span> 系统设置</a></li>
					<li><a href="javascript:void(0)" data-toggle="modal" data-target="#myInformation"><span class="glyphicon glyphicon-file"></span> 我的资料</a></li>
					<li><a href="javascript:void(0)" data-toggle="modal" data-target="#editPwdModal"><span class="glyphicon glyphicon-edit"></span> 修改密码</a></li>
					<li><a href="javascript:void(0);" data-toggle="modal" data-target="#exitModal"><span class="glyphicon glyphicon-off"></span> 退出</a></li>
				</ul>
			</li>
		</ul>
	</div>
</div>

<!-- 创建部门的模态窗口 -->
<div class="modal fade" id="createDeptModal" role="dialog">
	<div class="modal-dialog" role="document" style="width: 80%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel"><span class="glyphicon glyphicon-plus"></span> 新增部门</h4>
			</div>
			<div class="modal-body">

				<form id="addForm" class="form-horizontal" role="form" method="get" action="settings/dept/add.do">

					<div class="form-group">
						<label for="create-code" class="col-sm-2 control-label">编号<span style="font-size: 15px; color: red;">*</span></label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="create-code" name="deptno" style="width: 100%;" placeholder="编号为四位数字，不能为空">
							<span id="addNoTip" style="color: red;font-size: 12px"></span>
						</div>
					</div>

					<div class="form-group">
						<label for="create-name" class="col-sm-2 control-label">名称</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="create-name" name="dname" style="width: 100%;">
							<span id="addnameTip" style="color: red;font-size: 12px"></span>
						</div>
					</div>

					<div class="form-group">
						<label for="create-manager" class="col-sm-2 control-label">负责人</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="create-manager" name="master" style="width: 100%;">
							<span id="addmasterTip" style="color: red;font-size: 12px"></span>
						</div>
					</div>

					<div class="form-group">
						<label for="create-phone" class="col-sm-2 control-label">电话</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="create-phone" name="tel" style="width: 100%;">
							<span id="addtelTip" style="color: red;font-size: 12px"></span>
						</div>
					</div>

					<div class="form-group">
						<label for="create-describe" class="col-sm-2 control-label">描述</label>
						<div class="col-sm-10" style="width: 55%;">
							<textarea class="form-control" rows="3" id="create-describe" name="descript"></textarea>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" id="saveDeptBtn" class="btn btn-primary">保存</button>
			</div>
		</div>
	</div>
</div>

<!-- 修改部门的模态窗口 -->
<div class="modal fade" id="editDeptModal" role="dialog">
	<div class="modal-dialog" role="document" style="width: 80%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel"><span class="glyphicon glyphicon-edit"></span> 编辑部门</h4>
			</div>
			<div class="modal-body">

				<form id="editForm" class="form-horizontal" role="form" action="settings/dept/update.do">

					<div class="form-group">
						<label for="create-code" class="col-sm-2 control-label">编号<span style="font-size: 15px; color: red;">*</span></label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="hidden" class="form-control" id="edit-id" name="id" style="width: 200%;">
							<input type="text" class="form-control" id="edit-code" name="deptno" style="width: 200%;" placeholder="编号为四位数字，不能为空">
							<span id="editNoTip" style="color: red;font-size: 12px"></span>
						</div>
					</div>

					<div class="form-group">
						<label for="create-name" class="col-sm-2 control-label">名称</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="edit-name" name="dname" style="width: 200%;">
							<span id="editTip" style="color: red;font-size: 12px"></span>
						</div>
					</div>

					<div class="form-group">
						<label for="create-manager" class="col-sm-2 control-label">负责人</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="edit-manager" name="master" style="width: 200%;">
							<span id="addmasterTip" style="color: red;font-size: 12px"></span>
						</div>
					</div>

					<div class="form-group">
						<label for="create-phone" class="col-sm-2 control-label">电话</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="edit-phone" name="tel" style="width: 200%;">
							<span id="edittelTip" style="color: red;font-size: 12px"></span>
						</div>
					</div>

					<div class="form-group">
						<label for="create-describe" class="col-sm-2 control-label">描述</label>
						<div class="col-sm-10" style="width: 55%;">
							<textarea class="form-control" rows="3" id="edit-describe" name="descript"></textarea>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" id="updateDeptBtn" class="btn btn-primary">更新</button>
			</div>
		</div>
	</div>
</div>

<div style="width: 95%">
	<div>
		<div style="position: relative; left: 30px; top: -10px;">
			<div class="page-header">
				<h3>部门列表</h3>
			</div>
		</div>
	</div>
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px; top:-30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
			<button type="button" id="createBtn" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> 创建</button>
			<button type="button" id="editDeptBtn" class="btn btn-default" data-toggle="modal"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
			<button type="button" id="delDeptBtn" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	<div style="position: relative; left: 30px; top: -10px;">
		<table class="table table-hover">
			<thead>
			<tr style="color: #B3B3B3;">
				<td><input type="checkbox" id='qx'/></td>
				<td>编号</td>
				<td>名称</td>
				<td>负责人</td>
				<td>电话</td>
				<td>描述</td>
			</tr>
			</thead>
			<tbody id="tbody">
			</tbody>
		</table>
	</div>

	<div style="height: 50px; position: relative;top: 0px; left:30px;">
		<div id="page"></div>
	</div>

</div>

</body>
</html>