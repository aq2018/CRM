<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<!--分页插件-->
	<link rel="stylesheet" href="jquery/bs_pagination/css/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/locale/bs_pagination.zh-CN.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/js/jquery.bs_pagination.min_zh-CN.js"></script>

<script type="text/javascript">
	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;
	
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
		$("#edit-birth").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
	        autoclose: true,
	        todayBtn: true,
	        pickerPosition: "bottom-left"
		});
		$("#create-nextContactTime").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
	        autoclose: true,
	        todayBtn: true,
	        pickerPosition: "bottom-left"
		});
		$("#cancelBtn").click(function(){
			//显示
			$("#cancelAndSaveBtn").hide();
			//设置remarkDiv的高度为130px
			$("#remarkDiv").css("height","90px");
			cancelAndSaveBtnDefault = true;
		});
		
		$("body").on("mouseover",".remarkDiv",function(){
			$(this).children("div").children("div").show();
		});
		$("body").on("mouseout",".remarkDiv",function(){
			$(this).children("div").children("div").hide();
		});
		getOwner();
		//更新
		$("#update").click(function(){
			$.post(
					"workbench/contacts/update",
					{
						"id" : "${contact.id}",
						"owner" : $.trim($("#edit-contactsOwner").val()),
						"source" : $.trim($("#edit-clueSource").val()),
						"fullname" : $.trim($("#edit-surname").val()),
						"appellation" : $.trim($("#edit-call").val()),
						"job" : $.trim($("#edit-job").val()),
						"mphone" : $.trim($("#edit-mphone").val()),
						"email" : $.trim($("#edit-email").val()),
						"birth" : $.trim($("#edit-birth").val()),
						"customerId" : "${contact.customerId}",
						"customerName" : $.trim($("#edit-customerName").val()),
						"description" : $.trim($("#edit-describe").val()),
						"contactSummary" : $.trim($("#create-contactSummary").val()),
						"nextContactTime" : $.trim($("#create-nextContactTime").val()),
						"address" : $.trim($("#edit-address1").val()),
						"_" : new Date().getTime()
					},
					function(json){
						if(json.success){
							$("#editContactsModal").modal("hide");
							window.location = "workbench/contacts/detail?id=${contact.id}"
					    }else{
					        alert("添加失败了");
					    }
					}
				);
		});
		
		//获取活动备注
		getRemark();
		//更新备注
		$("#updateRemarkBtn").click(function(){
			var remark = $.trim($("#editRemark-description").val());
			var id = $("#editRemarkId").val();
			if(remark == ""){
				alert("不能为空！");
			}else{
				$.post(
					"workbench/contacts/updateRemark",
					{
						"id" : id,
						"description" : remark
					},
					function(json){
						// {"success":true , "activityRemark" : {"editTime":"","editBy":"","description":""}}
						//线索 - 李四先生-某某某公司 2017-01-22 10:20:10 由zhangsan
						// {"success":false}
						if(json.success){
							// 隐藏modal
							$("#editRemarkModal").modal("hide");
							// 更新前端div
							$("#_" + id).text(json.contactRemark.description);
							$("#s_" + id).text(json.contactRemark.editTime + " 由" + json.contactRemark.editBy);
						}else{
							alert("更新备注失败！");
						}
					}
				);
			}
		});
		
		//关联市场活动按钮
		$("#assoiciateFlag").click(function(){
			$("#queryTbody").empty();
			$("#actPage").empty();
			$("#bundActivityModal").modal("show");
		});
		
		//获取关联市场活动
		getAssociateActs();
		//全选未关联活动
		$("#qx").click(function(){
			$("input[name='fx']").prop("checked",this.checked);
		});
		$("#activityTable").on("click","input[name='fx']",function(){
			$("#qx").prop("checked",$("input[name='fx']:checked").size() == $("input[name='fx']").size());
		});
		
		//查询未关联的市场活动
		$("#queryAct").keydown(function(event){
			if(event.keyCode == 13){
				queryActByName(1,10);
				return false;//事件冒泡/事件传递--关闭它
			}
		});
		
		//点击关联
		$("#relationActBtn").click(function(){
			//{"id":"?"}
			var sendData = "contactId=${contact.id}";
			$("input[name='fx']:checked").each(function(){
				sendData += "&id=" + this.value;
			});
			 $.post(
				"workbench/contacts/addAssociateAct",
				sendData,
				function(json){
					if(json.success){
						$("#bundActivityModal").modal("hide");
						getAssociateActs();
					}else{
						alert("关联失败了");
					}
				}
			) 
		});
		
		//解除关联
		$("#disRelationActBtn").click(function(){
			$.post(
				"workbench/contacts/delAssociateAct",
				{"id" : $("#relationId").val()},
				function(json){
					if(json.success){
						$("#unbundActivityModal").modal("hide");
						getAssociateActs();
					}else{
						alert("解除关联失败");
					}
				}
			)
		});
		
		//创建交易
		$("#creatTrans").click(function(){
			sendData = "owner=${contact.ownerId}";
			sendData += "&source=${contact.source}";
			sendData += "&customerName=${contact.customerName}";
			sendData += "&contactName=${contact.fullname}";
			sendData += "&contactId=${contact.id}";
			sendData += "&email=${contact.email}";
			sendData += "&mphone=${contact.mphone}";
			sendData += "&job=${contact.job}";
			sendData += "&birth=${contact.birth}";
			sendData += "&description=${contact.description}";
			sendData += "&contactSummary=${contact.contactSummary}";
			sendData += "&nextContactTime=${contact.nextContactTime}";
			sendData += "&address=${contact.address}";
			window.location = "workbench/transaction/create?"+sendData;
		});
		
		//获取交易信息
		getTransInfo();
		//删除交易
		$("#delTransBtn").click(function(){
			$.post(
				"workbench/contacts/delTrans",
				{"id" : $("#transId").val()},
				function(json){
					if(json.success){
						$("#unbundTransModal").modal("hide");
						getTransInfo();
					}else{
						alert("删除交易失败");
					}
				}
			)
		});
		
	});//end--end-------------------------------------end-----------------------------end---------------------------end
	//获取所有者
	function getOwner(dom){
		$.get(
				"workbench/contacts/getOwner",
				function(data){
					var html = "";
					 $(data).each(function(){
						 html += "<option value='"+this.id+"'>"+this.username+"</option>";
					 })
					 $("#edit-contactsOwner").html(html);
					 $("#edit-contactsOwner").val("${contact.ownerId}");
				}
			);
	};
	//获取备注
	function getRemark(){
		$.get(
			"workbench/contacts/getRemark",
			{
				"id":"${contact.id}",
				"_":new Date().getTime()
			},
			function(json){
				var html = "";
				$(json).each(function(){
					html += "<div id='"+this.id+"' class='remarkDiv' style='height: 60px;'>";
					html += "<img title='zhangsan' src='image/user-thumbnail.png' style='width: 30px; height:30px;'>";
					html += "<div style='position: relative; top: -40px; left: 40px;' >";
					html += "<h5 id=_"+this.id+">"+this.description+"</h5>";
					html += "<font color='gray'>联系人</font> <font color='gray'>-</font> <b>${contact.fullname}${contact.appellation}-${contact.customerName}</b> <small id='s_"+this.id+"' style='color: gray;'> "+(this.editFlag == "0" ? this.createTime : time = this.editTime)+" &nbsp;由&nbsp;"+(this.editFlag == "0" ? this.createBy : account = this.editBy)+"</small>";
					html += "<div style='position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;'> ";
					html += "<a class='myHref' onclick='editRemark(\""+this.id+"\")'><span class='glyphicon glyphicon-edit' style='font-size: 20px; color: red;'></span></a>";
					html += "&nbsp;&nbsp;&nbsp;&nbsp;";
					html += "<a class='myHref' onclick='delRemark(\""+this.id+"\")'><span class='glyphicon glyphicon-remove' style='font-size: 20px; color: red;'></span></a>";
					html += "</div>";
					html += "</div>"; 
					html += "</div>"; 

				});   
				$("#remarkDiv").before(html);
			}
		);
	};
	//添加备注
	function addRemark(){
		var remark = $("#remark").val();
		$.ajax({
			type : "post",
			url : "workbench/contacts/addRemark",
			cache : false,
			data : 
			{
				"description":remark,
				"contactId":"${contact.id}"
			},
			beforeSend : function(){
				if (remark == "" || remark == "添加备注..."){
					$("#remarkTip").text("请输入备注后保存");
					return false;
				}
				$("#remarkTip").text("");
				return true;
			},
			success : function(json){
				//{"id",createTime,creater,}
				var html = "";
				if(json.success){
					html += "<div id="+json.contactRemark.id+" class='remarkDiv' style='height: 60px;'>";
					html += "<img title='zhangsan' src='image/user-thumbnail.png' style='width: 30px; height:30px;'>";
					html += "<div style='position: relative; top: -40px; left: 40px;' >";
					html += "<h5 id=_"+json.contactRemark.id+">"+json.contactRemark.description+"</h5>";
					html += "<font color='gray'>联系人</font> <font color='gray'>-</font> <b>${contact.fullname}${contact.appellation}-${contact.customerName}</b> <small id='s_"+json.contactRemark.id+"' style='color: gray;'> "+json.contactRemark.createTime+" &nbsp;由&nbsp;${user.account}</small>";
					html += "<div style='position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;'> ";
					html += "<a class='myHref' onclick='editRemark(\""+json.contactRemark.id+"\")'><span class='glyphicon glyphicon-edit' style='font-size: 20px; color: red;'></span></a>";
					html += "&nbsp;&nbsp;&nbsp;&nbsp;";
					html += "<a class='myHref' onclick='delRemark(\""+json.contactRemark.id+"\")'><span class='glyphicon glyphicon-remove' style='font-size: 20px; color: red;'></span></a>";
					html += "</div>";
					html += "</div>"; 
					html += "</div>"; 
					$("#remarkDiv").before(html);
					$("#remark").val("");
					//刷新备注
				}else{
					alert("添加失败");
				}
			}
		});
	};
	//编辑备注
	function editRemark(id){
		$("#editRemarkId").val(id);
		$("#editRemark-description").val($("#_"+id).text());
		$("#editRemarkModal").modal("show");
	};
	//删除备注
	function delRemark(id){
		if(confirm("确认删除备注？")){
			$.get(
				"workbench/contacts/delRemark",
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
	
	//获取交易
	function getTransInfo(){//unbundModal
		$.ajax({
			type : "get",
			url : "workbench/contacts/transInfo",
			data : {"contactId": "${contact.id}"},
			success : function(data){
				var html = "";
				$(data).each(function(){
                      html += '<tr>';
                      html += '<td><a href="workbench/transaction/detail?id=' + this.id + '" style="text-decoration: none;">'+this.customerId+'-'+this.name+'</a></td>';
					  html += '<td>'+this.money+'</td>';
					  html += '<td>'+this.stage+'</td>';
					  html += '<td>'+this.possibility+'</td>';
					  html += '<td>'+this.expectedDate+'</td>';
					  html += '<td>'+this.type+'</td>';
					  html += '<td><a href="javascript:void(0);" onclick="popTrans(\''+this.id+'\')" style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>删除</a></td>';
					  html += '</tr>';
					  $("#transTbody").html(html);
				})
			}
		});
	}
	//删除交易模态框
	function popTrans(id){
		$("#transId").val(id);
		$("#unbundTransModal").modal("show");
	}
	//关联市场活动
	function getAssociateActs(){
		$.ajax({
			type : "get",
			url : "workbench/contacts/getAssociateActs",
			data : {
				"id" : "${contact.id}",
				"_" : new Date().getTime()
			},
			cache : false,
			success : function(json){
				var html = "";
				$(json).each(function(){
					html += '<tr id='+this.relationId+'>';
					html += '<td>'+this.name+'</td>';
					html += '<td>'+this.start_date+'</td>';
					html += '<td>'+this.end_date+'</td>';
					html += '<td>'+this.owner+'</td>';
					html += '<td><a href="javascript:void(0);" onclick="disAssociateAct(\''+this.relationId+'\')" style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>解除关联</a></td>';
					html += '</tr>';
				});
				$("#actTbody").html(html);
			}
		});
	};
	//通过name查询市场关联sadasdsaasd
	function queryActByName(pageNo,pageSize){
		$.ajax({
			type : "get",
			url : "workbench/contacts/queryAssociateAct",
			cache : false,
			data : {
				"contactId" : "${contact.id}",
				"name" : $.trim($("#queryAct").val()),
				"pageNo" : pageNo,
				"pageSize" : pageSize
			},
			success : function(data){
				var html = "";
				$(data.dataList).each(function(){
					html += '<tr>';
					html += '<td><input type="checkbox" name="fx" value='+this.id+'></td>';
					html += '<td>'+this.name+'</td>';
					html += '<td>'+this.start_date+'</td>';
					html += '<td>'+this.end_date+'</td>';
					html += '<td>'+this.owner+'</td>';
					html += '</tr>';
				});
				$("#queryTbody").html(html);
				var totalPages = Math.ceil(data.total / pageSize);
				$("#actPage").bs_pagination({
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
						queryActByName(data.currentPage , data.rowsPerPage);
					}
				});
			},
		});
	};
	//解除关联链接
	function disAssociateAct(id){
		$("#relationId").val(id);
		$("#unbundActivityModal").modal("show");
	}
</script>

</head>
<body>

	<!-- 解除联系人和市场活动关联的模态窗口 -->
	<div class="modal fade" id="unbundActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 30%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">解除关联</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="relationId">
					<p>您确定要解除该关联关系吗？</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="disRelationActBtn" type="button" class="btn btn-danger">解除</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 解除联系人和交易关联的模态窗口 -->
	<div class="modal fade" id="unbundTransModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 30%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">删除交易</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="transId">
					<p>该交易内关联的备注信息将被删除，您确定要删除这条交易吗？</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="delTransBtn" type="button" class="btn btn-danger">解除</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 联系人和市场活动关联的模态窗口 -->
	<div class="modal fade" id="bundActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">关联市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input id="queryAct" type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td><input id="qx" type="checkbox"/></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="queryTbody">
							<!-- <tr>
								<td><input type="checkbox"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>
							<tr>
								<td><input type="checkbox"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr> -->
						</tbody>
					</table>
				</div>
				<div id="actPage" align="left"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="relationActBtn" type="button" class="btn btn-primary">关联</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改联系人的模态窗口 -->
	<div class="modal fade" id="editContactsModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">修改联系人</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-contactsOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-contactsOwner">
								  <!-- <option selected>zhangsan</option>
								  <option>lisi</option>
								  <option>wangwu</option> -->
								</select>
							</div>
							<label for="edit-clueSource" class="col-sm-2 control-label">来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-clueSource">
								 <%--  <option></option>
								  <option selected>广告</option>
								  <option>推销电话</option>
								  <option>员工介绍</option>
								  <option>外部介绍</option>
								  <option>在线商场</option>
								  <option>合作伙伴</option>
								  <option>公开媒介</option>
								  <option>销售邮件</option>
								  <option>合作伙伴研讨会</option>
								  <option>内部研讨会</option>
								  <option>交易会</option>
								  <option>web下载</option>
								  <option>web调研</option>
								  <option>聊天</option> --%>
								   <c:forEach items="${clueSourceList}" var="cs">
				  					<option>${cs.content}</option>
					  	  		 </c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-surname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-surname" value="${contact.fullname}">
							</div>
							<label for="edit-call" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-call">
								 <!--  <option></option>
								  <option selected>先生</option>
								  <option>夫人</option>
								  <option>女士</option>
								  <option>博士</option>
								  <option>教授</option> -->
								  <c:forEach items="${callsList}" var="c">
						  				<option>${c.content}</option>
							  	  </c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-job" value="${contact.job}">
							</div>
							<label for="edit-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-mphone" value="${contact.mphone}">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-email" value="${contact.email}">
							</div>
							<label for="edit-birth" class="col-sm-2 control-label">生日</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-birth" value="${contact.birth}">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-customerName" class="col-sm-2 control-label">客户名称</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-customerName" placeholder="支持自动补全，输入客户不存在则新建" value="${contact.customerName}">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">${contact.description}</textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="create-contactSummary">${contact.contactSummary}</textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control" id="create-nextContactTime" value="${contact.nextContactTime}">
								</div>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address1" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address1">${contact.address}</textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="update" type="button" class="btn btn-primary" data-dismiss="modal">更新</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 返回按钮 -->
	<div style="position: relative; top: 35px; left: 10px;">
		<a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
	</div>
	
	<!-- 大标题 -->
	<div style="position: relative; left: 40px; top: -30px;">
		<div class="page-header">
			<h3>${contact.fullname}${contact.appellation}<small> - ${contact.customerName}</small></h3>
		</div>
		<div style="position: relative; height: 50px; width: 500px;  top: -72px; left: 700px;">
			<button type="button" class="btn btn-default" data-toggle="modal" data-target="#editContactsModal"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
			<button id="delBtn" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	
	<!-- 详细信息 -->
	<div style="position: relative; top: -70px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">所有者</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${contact.owner}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">来源</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${contact.source}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">客户名称</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${contact.customerName}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">姓名</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${contact.fullname}${contact.appellation}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">邮箱</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${contact.email}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">手机</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${contact.mphone}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">职位</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${contact.job}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">生日</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>&nbsp;</b>${contact.birth}</div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 40px;">
			<div style="width: 300px; color: gray;">创建者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${contact.createBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${contact.createTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 50px;">
			<div style="width: 300px; color: gray;">修改者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${contact.editBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${contact.editTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 60px;">
			<div style="width: 300px; color: gray;">描述</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${contact.description}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 70px;">
			<div style="width: 300px; color: gray;">联系纪要</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					&nbsp;${contact.contactSummary}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 80px;">
			<div style="width: 300px; color: gray;">下次联系时间</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>&nbsp;${contact.nextContactTime}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
        <div style="position: relative; left: 40px; height: 30px; top: 90px;">
            <div style="width: 300px; color: gray;">详细地址</div>
            <div style="width: 630px;position: relative; left: 200px; top: -20px;">
                <b>
                  ${contact.address}
                </b>
            </div>
            <div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
        </div>
	</div>
	<!-- 备注 -->
	<div style="position: relative; top: 20px; left: 40px;">
		<div class="page-header">
			<h4>备注</h4>
		</div>
		
		<!-- 备注1 -->
		<%-- <div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>哎呦！</h5>
				<font color="gray">联系人</font> <font color="gray">-</font> <b>李四先生-北京某某某公司</b> <small style="color: gray;"> 2017-01-22 10:10:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div>
		
		备注2
		<div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>呵呵！</h5>
				<font color="gray">联系人</font> <font color="gray">-</font> <b>李四先生-北京某某某公司</b> <small style="color: gray;"> 2017-01-22 10:20:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div> --%>
		<!-- 编辑备注模态 窗口 -->
	<div class="modal fade" id="editRemarkModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">编辑备注</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label id="description" for="create-describe" class="col-sm-2 control-label">备注内容</label>
							<div class="col-sm-10" style="width: 65%;">
							 <input type="hidden" class="form-control" id="editRemarkId">
								<textarea class="form-control" rows="3" id="editRemark-description"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="updateRemarkBtn" type="button" class="btn btn-primary">更新</button>
				</div>
			</div>
		</div>
	</div>
		<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
			<form role="form" style="position: relative;top: 10px; left: 10px;">
				<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="添加备注..."></textarea>
				<span id="remarkTip" style="color: red;font-size: 14px"></span>
				<p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
					<button id="cancelBtn" type="button" class="btn btn-default">取消</button>
					<button type="button" class="btn btn-primary" onclick="addRemark()">保存</button>
				</p>
			</form>
		</div>
	</div>
	
	<!-- 交易 -->
	<div>
		<div style="position: relative; top: 20px; left: 40px;">
			<div class="page-header">
				<h4>交易</h4>
			</div>
			<div style="position: relative;top: 0px;">
				<table id="activityTable3" class="table table-hover" style="width: 900px;">
					<thead>
						<tr style="color: #B3B3B3;">
							<td>名称</td>
							<td>金额</td>
							<td>阶段</td>
							<td>可能性</td>
							<td>预计成交日期</td>
							<td>类型</td>
							<td></td>
						</tr>
					</thead>
					<tbody id="transTbody">
						<%-- <tr>
							<td><a href="transaction/detail.html" style="text-decoration: none;">某某某公司-交易01</a></td>
							<td>5,000</td>
							<td>谈判/复审</td>
							<td>90</td>
							<td>2017-02-07</td>
							<td>新业务</td>
							<td><a href="javascript:void(0);" data-toggle="modal" data-target="#unbundModal" style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>删除</a></td>
						</tr> --%>
					</tbody>
				</table>
			</div>
			
			<div>
				<a href="javascript:void(0)" id="creatTrans" style="text-decoration: none;"><span class="glyphicon glyphicon-plus"></span>新建交易</a>
			</div>
		</div>
	</div>
	
	<!-- 市场活动 -->
	<div>
		<div style="position: relative; top: 60px; left: 40px;">
			<div class="page-header">
				<h4>市场活动</h4>
			</div>
			<div style="position: relative;top: 0px;">
				<table id="activityTable" class="table table-hover" style="width: 900px;">
					<thead>
						<tr style="color: #B3B3B3;">
							<td>名称</td>
							<td>开始日期</td>
							<td>结束日期</td>
							<td>所有者</td>
							<td></td>
						</tr>
					</thead>
					<tbody id="actTbody">
						<!-- <tr>
							<td><a href="activity/detail.jsp" style="text-decoration: none;">发传单</a></td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
							<td>zhangsan</td>
							<td><a href="javascript:void(0);" data-toggle="modal" data-target="#unbundActivityModal" style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>解除关联</a></td>
						</tr> -->
					</tbody>
				</table>
			</div>
			
			<div>
				<a href="javascript:void(0);" id="assoiciateFlag" style="text-decoration: none;"><span class="glyphicon glyphicon-plus"></span>关联市场活动</a>
			</div>
		</div>
	</div>
	
	
	<div style="height: 200px;"></div>
</body>
</html>