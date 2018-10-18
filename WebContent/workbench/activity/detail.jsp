<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="js/workbench/activity/detail.js"></script>

<script type="text/javascript">

	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;
	
	$(function(){
		
		//编辑
		$("#editActBtn").click(function(){
			//获取活动所有者
			$("#edit-marketActivityOwner").val("${user.id}");
			$("#editActivityModal").modal("show");
		});
		//删除活动
		$("#delActBtn").click(function(){
			if(confirm("确定删除？")){	
				$.get("workbench/activity/del.do",{"id":"${activity.id}","_":new Date().getTime()},function(data){
							if(data.success){
								window.location.href = "workbench/activity/index.jsp";
							}else{
								alert("删除失败");
							}
						}
					)
			}
		});
		
		//获取备注
		getNote();
		
		//修改备注
		//后台json NoteAct更新备注信息--287行使用前台
		$("#editNoteBtn").click(function(){
			var time = formatDate(new Date());
			var id = $("#editNoteId").val();
			var description = $.trim($("#editNote-describe").val());
			  $.ajax({
				type : "post",
				url : "workbench/activity/updateNote.do",
				data : 
				{
					"id" : $("#editNoteId").val(),	
					"description" : description,
					"editTime" : time
				},
				success : function(json){
					if(json.success){
						//{"success":true} 更新div
						$("#_"+id).text($.trim($("#editNote-describe").val()));//修改描述
						$("#s_"+id).text(time+" 由 ${user.account}");//修改时间和更改者
						$("#editNoteModal").modal("hide");
					}else{
						//{"success":false}
						alert("更新失败");
					}
				}
			});
		});
	});
	//获取活动备注
	function getNote(){
		$.get(
			"workbench/activity/getNotes.do",
			{
				"id":"${param.id}",
				"_":new Date().getTime()
			},
			function(json){
				var html = "";
				var time = "";
				var account = "";
				$(json).each(function(){
					this.editFlag == "0" ? time = this.createTime : time = this.editTime;
					this.editFlag == "0" ? account = this.createBy : account = this.editBy;
					html += "<tr id="+this.id+"><td>";
					html += "<div class='remarkDiv' style='height: 60px;'>";
					html += "<img title='zhangsan' src='image/user-thumbnail.png' style='width: 30px; height:30px;'>";
					html += "<div style='position: relative; top: -40px; left: 40px;' >";
					html += "<h5 id=_"+this.id+">"+this.description+"</h5>";
					html += "<font color='gray'>市场活动</font> <font color='gray'>-</font> <b>${activity.name}</b> <small id='s_"+this.id+"' style='color: gray;'> "+time+" &nbsp;由&nbsp;"+account+"</small>";
					html += "<div style='position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;'> ";
					html += "<a class='myHref' onclick='editNote("+this.id+")'><span class='glyphicon glyphicon-edit' style='font-size: 20px; color: red;'></span></a>";
					html += "&nbsp;&nbsp;&nbsp;&nbsp;";
					html += "<a class='myHref' onclick='delNote("+this.id+")'><span class='glyphicon glyphicon-remove' style='font-size: 20px; color: red;'></span></a>";
					html += "</div>";
					html += "</div>"; 
					html += "</div>"; 
					html += "</tr></td>"; 
				});   
				$("#tbody").html(html);
			}
		);
	};

	//添加备注
	//使用前台更新备注，json只返回true/false  --190行$("#editNoteBtn")
	/*
		可以只传des，pid_act,后台json.success返回Map id,des,time,creater后获得时间
	*/
	function addNote(){
		var time = new Date();
		var id = time.getTime();
		var date = formatDate(time);
		var remark = $("#remark").val();
		$.ajax({
			type : "post",
			url : "workbench/activity/addNote.do",
			cache : false,
			data : 
			{
				"id":id,
				"description":remark,
				"createTime":date,
				"p_actId":"${param.id}"
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
				if(json.success){
					var html = "<tr id="+id+"><td>";
					html += "<div class='remarkDiv' style='height: 60px;'>";
					html += "<img title='zhangsan' src='image/user-thumbnail.png' style='width: 30px; height:30px;'>";
					html += "<div style='position: relative; top: -40px; left: 40px;' >";
					html += "<h5 id=_"+id+">"+remark+"</h5>";
					html += "<font color='gray'>市场活动</font> <font color='gray'>-</font> <b>${activity.name}</b> <small id='s_"+id+"' style='color: gray;'> "+date+" &nbsp;由&nbsp;${user.account}</small>";
					html += "<div style='position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;'> ";
					html += "<a class='myHref' onclick='editNote("+id+")'><span class='glyphicon glyphicon-edit' style='font-size: 20px; color: red;'></span></a>";
					html += "&nbsp;&nbsp;&nbsp;&nbsp;";
					html += "<a class='myHref' onclick='delNote("+id+")'><span class='glyphicon glyphicon-remove' style='font-size: 20px; color: red;'></span></a>";
					html += "</div>";
					html += "</div>"; 
					html += "</div>"; 
					html += "</td>"; 
					html += "</tr>"; 
					$("#tbody").append(html);
					$("#remark").val("");
					//刷新备注
				}else{
					alert("添加失败");
				}
			}
		});
	}
</script>

</head>
<body>

    <!-- 修改市场活动的模态窗口 -->
    <div class="modal fade" id="editActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">修改市场活动</h4>
                </div>
                <div class="modal-body">

                    <form class="form-horizontal" role="form">

                        <div class="form-group">
                            <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-marketActivityOwner">
                                </select>
                            </div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="${activity.name}">
                                <span id="nameTip" style="color: red;font-size: 14px"></span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-startTime" value="${activity.start_date}">
                                <span id="startTip" style="color: red;font-size: 14px"></span>
                            </div>
                            <label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-endTime" value="${activity.end_date}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-cost" value="${activity.cost}">
                                <span id="costTip" style="color: red;font-size: 14px"></span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="edit-describe">${activity.description}</textarea>
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
    <!-- 编辑备注模态 窗口 -->
	<div class="modal fade" id="editNoteModal" role="dialog">
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
							 <input type="hidden" class="form-control" id="editNoteId">
								<textarea class="form-control" rows="3" id="editNote-describe"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="editNoteBtn" type="button" class="btn btn-primary">更新</button>
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
			<h3>市场活动-${activity.name} <small>${activity.start_date} ~ ${activity.end_date}</small></h3>
		</div>
		<div style="position: relative; height: 50px; width: 250px;  top: -72px; left: 700px;">
			<button id="editActBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
			<button id="delActBtn" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	
	<!-- 详细信息 -->
	<div style="position: relative; top: -70px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">所有者</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${activity.owner}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">名称</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>&nbsp;${activity.name}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>

		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">开始日期</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${activity.start_date}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">结束日期</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>&nbsp;${activity.end_date}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">成本</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>&nbsp;${activity.cost}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">创建者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>&nbsp;${activity.creater}&nbsp;</b><small style="font-size: 10px; color: gray;">${activity.createTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 40px;">
			<div style="width: 300px; color: gray;">修改者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>&nbsp;${activity.editer}&nbsp;</b><small style="font-size: 10px; color: gray;">${activity.editTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 50px;">
			<div style="width: 300px; color: gray;">描述</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${activity.description}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
	</div>
	
	
	<!-- 备注 -->
	<div style="position: relative; top: 30px; left: 40px;">
		
		<table>
		<thead>
			<tr>
				<td>
					<div class="page-header">
					<h4>备注</h4>
					</div>
				</td>
			</tr>
		</thead>	
		<tbody id="tbody">
			<tr>
				<td>
					<!-- 备注1 -->
					<!-- <div class="remarkDiv" style="height: 60px;">
						<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
						<div style="position: relative; top: -40px; left: 40px;" >
							<h5>哎呦！</h5>
							<font color="gray">市场活动张三</font> <font color="gray">-</font> <b>发传单</b> <small style="color: gray;"> 2017-01-22 10:10:10 由zhangsan</small>
							<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
								<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
							</div>
						</div>
					</div> -->
					
					<!-- 备注2 -->
						<!-- <div class="remarkDiv" style="height: 60px;">
							<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
							<div style="position: relative; top: -40px; left: 40px;" >
								<h5>呵呵！</h5>
								<font color="gray">市场活动</font> <font color="gray">-</font> <b>发传单</b> <small style="color: gray;"> 2017-01-22 10:20:10 由zhangsan</small>
								<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
									<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
								</div>
							</div>
						</div> -->
				</td>
			</tr>
		</tbody>
		<tfoot>
			<tr>
				<td>
					<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
						<form role="form" style="position: relative;top: 10px; left: 10px;">
							<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="添加备注..."></textarea>
							 <span id="remarkTip" style="color: red;font-size: 14px"></span>
							
							<p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
								<button id="cancelBtn" type="button" class="btn btn-default">取消</button>
								<button onclick="addNote()" type="button" class="btn btn-primary">保存</button>
							</p>
						</form>
					</div>
				</td>
			</tr>
		</tfoot>
	</table>
		
	</div>
	<div style="height: 200px;"></div>
</body>
</html>