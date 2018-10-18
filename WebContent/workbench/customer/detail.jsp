<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="jquery/typeahead/bootstrap3-typeahead.min.js"></script>
<script type="text/javascript" src="js/workbench/customer/detail.js"></script>
<script type="text/javascript">
	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;
	$(function(){
		//新建联系人按钮
		$("#createBtn").click(function(){
			$("#createContactsModal").modal("show");
			$("#createContactForm")[0].reset();
			$("#create-contactsOwner").val("${customer.ownerId}");
		});
		getOwner();
		//点击编辑
		$("#editBtn").click(function(){
	    	$("#edit-customerName").val("${customer.name}");
	    	$("#edit-website").val("${customer.website}");
	    	$("#edit-phone").val("${customer.phone}");
	    	$("#edit-describe").val("${customer.description}");
	    	$("#create-contactSummary1").val("${customer.contactSummary}");
	    	$("#create-nextContactTime2").val("${customer.nextContactTime}");
	    	$("#create-address").val("${customer.address}");
		})
		//更新
		$("#updateBtn").click(function(){
			/* if(表单验证){
			} */
			$.ajax({
				type : "post",
				url : "workbench/customer/update",
				data : {
					"id" : "${customer.id}",
					"owner" : $.trim($("#edit-customerOwner").val()),
					"name" : $.trim($("#edit-customerName").val()),
					"website" : $.trim($("#edit-website").val()),
					"phone" : $.trim($("#edit-phone").val()),
					"description" : $.trim($("#edit-describe").val()),
					"contactSummary" : $.trim($("#create-contactSummary1").val()),
					"nextContactTime" : $.trim($("#create-nextContactTime2").val()),
					"address" : $.trim($("#edit-address").val())
				},
				cache : false,
				success : function(json){
					if(json.success){
						$("#editCustomerModal").modal("hide");
						window.location.href = "workbench/customer/detail?id=${customer.id}";
					}else{
						alert("更新失败");
					}
				}
			});
		})
		//del
		$("#delBtn").click(function(){
			if(confirm("删除客户信息,该客户相关的备注、交易、联系人也将被删除\n确认删除?")){
				$.ajax({
					type : "post",
					url : "workbench/customer/del",
					cache : false,
					data : {"id" : "${customer.id}"},
					success : function(json){
						if(json.success){
							window.location.href = "workbench/customer/idnex.jsp";
						}else{
							alert("删除失败");
						}
					}
				});
			}
		});
		///获取活动备注
		getRemark();
		//添加备注
		$("#saveRkBtn").click(function(){
			addNote();
		});
		//获取联系人列表
		getContects();
		//创建交易
		$("#createTrans").click(function(){
			sendData = "customerName=${customer.name}";
			sendData += "&&owner=${customer.ownerId}";
			sendData += "&contactSummary=${customer.contactSummary}";
			sendData += "&description=${customer.description}";
			window.location = "workbench/transaction/create?"+sendData;
		});
		//获取交易信息
		getTransInfo();
	});//end--end--end---------------------------end-----------------end----------------end==========end
	//获取所有者
	function getOwner(){
		$.get(
				"workbench/customer/getOwner",
				function(data){
					var html = "";
					 $(data).each(function(){
						 html += "<option value='"+this.id+"'>"+this.username+"</option>";
					 })
					 $("#edit-customerOwner").html(html);
					 $("#create-contactsOwner").html(html);
					 $("#edit-customerOwner").val("${customer.ownerId}");
				}
			)
	};
	//获取客户备注
	function getRemark(){
		$.get(
			"workbench/customer/getRemarks",
			{
				"customerId":"${customer.id}",
				"_":new Date().getTime()
			},
			function(json){
				var html = "";
				$(json).each(function(){
					html += "<div id='"+this.id+"' class='remarkDiv' style='height: 60px;'>";
					html += "<img title='zhangsan' src='image/user-thumbnail.png' style='width: 30px; height:30px;'>";
					html += "<div style='position: relative; top: -40px; left: 40px;' >";
					html += "<h5 id=_"+this.id+">"+this.noteContent+"</h5>";
					html += "<font color='gray'>联系人</font> <font color='gray'>-</font> <b>"+this.fullname+this.appellation+"-${customer.name}</b> <small id='s_"+this.id+"' style='color: gray;'> "+(this.editFlag == "0" ? this.createTime : time = this.editTime)+" &nbsp;由&nbsp;"+(this.editFlag == "0" ? this.createBy : account = this.editBy)+"</small>";
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
	function addNote(){
		var remark = $("#remark").val();
		$.ajax({
			type : "post",
			url : "workbench/customer/addRemark",
			cache : false,
			data : 
			{
				"noteContent":remark,
				"customerId":"${customer.id}"
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
				//"remark" : {"id",createTime,creater,}
				var html ="";
				if(json.success){
					html += "<div id="+json.remark.id+" class='remarkDiv' style='height: 60px;'>";
					html += "<img title='zhangsan' src='image/user-thumbnail.png' style='width: 30px; height:30px;'>";
					html += "<div style='position: relative; top: -40px; left: 40px;' >";
					html += "<h5 id=_"+json.remark.id+">"+json.remark.noteContent+"</h5>";
					html += "<font color='gray'>联系人</font> <font color='gray'>-</font> <b>"+json.remark.fullname+json.remark.appellation+"-${customer.name}</b> <small id='s_"+json.remark.id+"' style='color: gray;'> "+(json.remark.editFlag == "0" ? json.remark.createTime : time = json.remark.editTime)+" &nbsp;由&nbsp;"+(json.remark.editFlag == "0" ? json.remark.createBy : account = json.remark.editBy)+"</small>";
					html += "<div style='position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;'> ";
					html += "<a class='myHref' onclick='editRemark(\""+json.remark.id+"\")'><span class='glyphicon glyphicon-edit' style='font-size: 20px; color: red;'></span></a>";
					html += "&nbsp;&nbsp;&nbsp;&nbsp;";
					html += "<a class='myHref' onclick='delRemark(\""+json.remark.id+"\")'><span class='glyphicon glyphicon-remove' style='font-size: 20px; color: red;'></span></a>";
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
	//获取交易
	function getTransInfo(){//unbundModal
		$.ajax({
			type : "get",
			url : "workbench/customer/transInfo",
			data : {"customerId": "${customer.id}"},
			success : function(data){
				var html = "";
				$(data).each(function(){
                      html += '<tr id="'+this.id+'">';
                      html += '<td><a href="workbench/transaction/detail?id=' + this.id + '" style="text-decoration: none;">' + (this.name == null ? " " : this.name) + '</a></td>';
					  html += '<td>' + (this.money == null ? "" : this.money) + '</td>';
					  html += '<td>' + (this.stage == null ? "" : this.stage) + '</td>';
					  html += '<td>' + (this.possibility == null ? "" : this.possibility) + '</td>';
					  html += '<td>' + (this.expectedDate == null ? "" : this.expectedDate) + '</td>';
					  html += '<td>' + (this.type == null ? "" : this.type) + '</td>';
					  html += '<td><a href="javascript:void(0);" onclick="popTrans(\'' + this.id + '\')" style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>删除</a></td>';
					  html += '</tr>';
					  $("#transTbody").html(html);
				})
			}
		});
	}
	//删除交易模态框
	function popTrans(id){
		$("#transId").val(id);
		$("#removeTransactionModal").modal("show");
	}
	//获取联系人列表
	function getContects(){
		$.ajax({
			type : "get",
			url : "workbench/customer/getContacts",
			data : {"id":"${customer.id}"},
			success : function(json){
				var html ="";
				$(json).each(function(){
					html += '<tr id='+this.id+'>';
					html += '<td><a href="workbench/contacts/detail?id='+this.id+'" style="text-decoration: none;">'+this.fullname+'</a></td>';
					html += '<td>'+this.email+'</td>';
					html += '<td>'+this.mphone+'</td>';
					html += '<td><a href="javascript:void(0);" onclick="del(\''+this.id+'\')" style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>删除</a></td>';
					html += '</tr>';
				});
				$("#contactTbody").html(html);
			}
		})
	};
	//删除客户备注
	function del(id){
		$("#contactId").val(id);
		$("#removeContactsModal").modal("show");
	}
	
</script>

</head>
<body>

	<!-- 删除联系人的模态窗口 -->
	<div class="modal fade" id="removeContactsModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 30%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<input type="hidden" id="contactId">
					<h4 class="modal-title">删除联系人</h4>
				</div>
				<div class="modal-body">
					<p>您确定要删除该联系人吗？</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="delContactBtn" type="button" class="btn btn-danger">删除</button>
				</div>
			</div>
		</div>
	</div>

    <!-- 删除交易的模态窗口 -->
    <div class="modal fade" id="removeTransactionModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 30%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <input type="hidden" id="transId">
                    <h4 class="modal-title">删除交易</h4>
                </div>
                <div class="modal-body">
                    <p>您确定要删除该交易吗？该交易下关联重要的备注内容同时也将删除！</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="delTransBtn" type="button" class="btn btn-danger" data-dismiss="modal">删除</button>
                </div>
            </div>
        </div>
    </div>
	
	<!-- 创建联系人的模态窗口 -->
	<div class="modal fade" id="createContactsModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="$('#createContactsModal').modal('hide');">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建联系人</h4>
				</div>
				<div class="modal-body">
					<form id="createContactForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-contactsOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-contactsOwner">
								  <%-- <option>zhangsan</option>
								  <option>lisi</option>
								  <option>wangwu</option> --%>
								</select>
							</div>
							<label for="create-clueSource" class="col-sm-2 control-label">来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-clueSource">
								  <option></option>
								  <%-- <option>广告</option>
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
								  <c:forEach items="${clueSourceList }" var="c">
								  	<option>${c.content}</option>
								  </c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-surname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-surname">
							</div>
							<label for="create-call" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-call">
								  <option></option>
								  <%-- <option>先生</option>
								  <option>夫人</option>
								  <option>女士</option>
								  <option>博士</option>
								  <option>教授</option> --%>
								  <c:forEach items="${callsList }" var="c">
								  	<option>${c.content}</option>
								  </c:forEach>
								</select>
							</div>
							
						</div>
						
						<div class="form-group">
							<label for="create-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-job">
							</div>
							<label for="create-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-mphone">
							</div>
						</div>
						
						<div class="form-group" style="position: relative;">
							<label for="create-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-email">
							</div>
							<label for="create-birth" class="col-sm-2 control-label">生日</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-birth">
							</div>
						</div>
						
						<div class="form-group" style="position: relative;">
							<label for="create-customerName" class="col-sm-2 control-label">客户名称</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-customerName" autocomplete="off" placeholder="支持自动补全，输入客户不存在则新建" value="${customer.name}">
							</div>
						</div>
						
						<div class="form-group" style="position: relative;">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe">${customer.description}</textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

                        <div style="position: relative;top: 15px;">
                            <div class="form-group">
                                <label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="3" id="edit-contactSummary">${customer.contactSummary}</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
                                <div class="col-sm-10" style="width: 300px;">
                                    <input type="text" class="form-control" id="edit-nextContactTime" value="${customer.nextContactTime}">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address1" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address1">${customer.address}</textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveContact" type="button" class="btn btn-primary" >保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改客户的模态窗口 -->
    <div class="modal fade" id="editCustomerModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">修改客户</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="edit-customerOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-customerOwner">
                                   <!--  <option>zhangsan</option>
                                    <option>lisi</option>
                                    <option>wangwu</option> -->
                                </select>
                            </div>
                            <label for="edit-customerName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-customerName" value="某某某公司">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-website" class="col-sm-2 control-label">公司网站</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-website" value="${customer.website}">
                            </div>
                            <label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-phone" value="010-84846003">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="edit-describe"></textarea>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

                        <div style="position: relative;top: 15px;">
                            <div class="form-group">
                                <label for="create-contactSummary1" class="col-sm-2 control-label">联系纪要</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="3" id="create-contactSummary1"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="create-nextContactTime2" class="col-sm-2 control-label">下次联系时间</label>
                                <div class="col-sm-10" style="width: 300px;">
                                    <input type="text" class="form-control" id="create-nextContactTime2">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address">北京大兴大族企业湾</textarea>
                                </div>
                            </div>
                        </div>
                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="updateBtn" type="button" class="btn btn-primary">更新</button>
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
			<h3>${customer.name}<small><a href="${customer.website}" target="_blank">${customer.website}</a></small></h3>
		</div>
		<div style="position: relative; height: 50px; width: 500px;  top: -72px; left: 700px;">
			<button id="editBtn" type="button" class="btn btn-default" data-toggle="modal" data-target="#editCustomerModal"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
			<button id="delBtn" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	
	<!-- 详细信息 -->
	<div style="position: relative; top: -70px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">所有者</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${customer.ownerName}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">名称</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${customer.name}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">公司网站</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${customer.website}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">公司座机</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${customer.phone}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">创建者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${customer.createBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${customer.createTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">修改者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${customer.editer}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${customer.editTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
        <div style="position: relative; left: 40px; height: 30px; top: 40px;">
            <div style="width: 300px; color: gray;">联系纪要</div>
            <div style="width: 630px;position: relative; left: 200px; top: -20px;">
                <b>
                   ${customer.contactSummary}
                </b>
            </div>
            <div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
        </div>
        <div style="position: relative; left: 40px; height: 30px; top: 50px;">
            <div style="width: 300px; color: gray;">下次联系时间</div>
            <div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${customer.nextContactTime}</b></div>
            <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px; "></div>
        </div>
		<div style="position: relative; left: 40px; height: 30px; top: 60px;">
			<div style="width: 300px; color: gray;">描述</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${customer.description}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
        <div style="position: relative; left: 40px; height: 30px; top: 70px;">
            <div style="width: 300px; color: gray;">详细地址</div>
            <div style="width: 630px;position: relative; left: 200px; top: -20px;">
                <b>
                   ${customer.address}
                </b>
            </div>
            <div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
        </div>
	</div>
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
	<!-- 备注 -->
	<div style="position: relative; top: 10px; left: 40px;">
		<div class="page-header">
			<h4>备注</h4>
		</div>
		
		<!-- 备注1 -->
		<!-- <div class="remarkDiv" style="height: 60px;">
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
		</div> -->
		
		<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
			<form role="form" style="position: relative;top: 10px; left: 10px;">
				<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="添加备注..."></textarea>
				<span id="remarkTip" style="color: red;font-size: 14px"></span>
				<p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
					<button id="cancelBtn" type="button" class="btn btn-default">取消</button>
					<button id="saveRkBtn" type="button" class="btn btn-primary">保存</button>
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
				<table id="transTable2" class="table table-hover" style="width: 900px;">
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
						<%--<tr>
							<td><a href="workbench/transaction/detail.html" style="text-decoration: none;">某某某公司-交易01</a></td>
							<td>5,000</td>
							<td>谈判/复审</td>
							<td>90</td>
							<td>2017-02-07</td>
							<td>新业务</td>
							<td><a href="javascript:void(0);" data-toggle="modal" data-target="#removeTransactionModal" style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>删除</a></td>
						</tr>--%>
					</tbody>
				</table>
			</div>
			
			<div>
				<a href="javascript:void(0)" id="createTrans" style="text-decoration: none;"><span class="glyphicon glyphicon-plus"></span>新建交易</a>
			</div>
		</div>
	</div>
	
	<!-- 联系人 -->
	<div>
		<div style="position: relative; top: 20px; left: 40px;">
			<div class="page-header">
				<h4>联系人</h4>
			</div>
			<div style="position: relative;top: 0px;">
				<table id="contactTable" class="table table-hover" style="width: 900px;">
					<thead>
						<tr style="color: #B3B3B3;">
							<td>名称</td>
							<td>邮箱</td>
							<td>手机</td>
							<td></td>
						</tr>
					</thead>
					<tbody id="contactTbody">
						<!-- <tr>
							<td><a href="workbench/contacts/detail?id=1" style="text-decoration: none;">李四</a></td>
							<td>lisi@bjpowernode.com</td>
							<td>13543645364</td>
							<td><a href="javascript:void(0);" data-toggle="modal" data-target="#removeContactsModal" style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>删除</a></td>
						</tr> -->
					</tbody>
				</table>
			</div>
			
			<div>
				<a href="javascript:void(0);" id="createBtn" style="text-decoration: none;"><span class="glyphicon glyphicon-plus"></span>新建联系人</a>
			</div>
		</div>
	</div>
	
	<div style="height: 200px;"></div>
</body>
</html>