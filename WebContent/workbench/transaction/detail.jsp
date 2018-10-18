<%@page contentType="text/html;charset=UTF-8"
import="com.test.crm.domain.DictionaryValue,com.test.crm.domain.Transaction,java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />

<style type="text/css">
.mystage{
	font-size: 20px;
	vertical-align: middle;
	cursor: pointer;
}
.closingDate{
	font-size : 15px;
	cursor: pointer;
	vertical-align: middle;
}
</style>
	
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<!--分页插件-->
	<link rel="stylesheet" href="jquery/bs_pagination/css/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/locale/bs_pagination.zh-CN.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/js/jquery.bs_pagination.min_zh-CN.js"></script>

<script type="text/javascript">

		var json = {
				<%
					Map<String,String> pMap = (Map<String,String>)application.getAttribute("possibilityMap");
					Set<String> keys = pMap.keySet();
					for(String key: keys){
						String value = pMap.get(key);
				%>
					"<%=key%>" : "<%=value%>",
				<%
					}
				%>
		};
	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;
	
	$(function(){
		
		//删除
		$("#del").click(function(){
			if(confirm("确定删除这条交易?")){
				$.post(
					"workbench/transaction/del?id=${transaction.id}",
					function(json){
						if(json.success){
							window.location = "workbench/transaction/index.jsp";			
						}else{
							alert("删除失败");
						}
					}
				)
				
			}
		});
		
		
		//可能性
		
	});//end
	
	//获取客户备注
	function getRemark(){
		$.get(
			"workbench/transaction/getRemarks",
			{
				"transId":"${transaction.id}",
				"_":new Date().getTime()
			},
			function(json){
				var html = "";
				$(json).each(function(){
					html += "<div id='"+this.id+"' class='remarkDiv' style='height: 60px;'>";
					html += "<img title='${user.account}' src='image/user-thumbnail.png' style='width: 30px; height:30px;'>";
					html += "<div style='position: relative; top: -40px; left: 40px;' >";
					html += "<h5 id=_"+this.id+">"+this.description+"</h5>";
					html += "<font color='gray'>交易</font> <font color='gray'>-</font> <b>"+this.customerId+"-${transaction.name}</b> <small id='s_"+this.id+"' style='color: gray;'> "+(this.editFlag == "0" ? this.createTime : time = this.editTime)+" &nbsp;由&nbsp;"+(this.editFlag == "0" ? this.createBy : account = this.editBy)+"</small>";
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
			url : "workbench/transaction/addRemark",
			cache : false,
			data : 
			{
				"description":remark,
				"transId":"${transaction.id}"
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
					html += "<h5 id=_"+json.remark.id+">"+json.remark.description+"</h5>";
					html += "<font color='gray'>交易</font> <font color='gray'>-</font> <b>${transaction.customerId}-${transaction.name}</b> <small id='s_"+json.remark.id+"' style='color: gray;'> "+(json.remark.editFlag == "0" ? json.remark.createTime : time = json.remark.editTime)+" &nbsp;由&nbsp;"+(json.remark.editFlag == "0" ? json.remark.createBy : account = json.remark.editBy)+"</small>";
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
	//编辑备注
	function editRemark(id){
		$("#editRemarkId").val(id);
		$("#editRemark-description").val($("#_"+id).text());
		$("#editRemarkModal").modal("show");
		
	}
	//删除备注
	function delRemark(id){
		if(confirm("确认删除备注？")){
			$.get(
					"workbench/transaction/delRemark",
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
	//通过name查询市场关联sadasdsaasd
	function history(pageNo,pageSize){
		$.ajax({
			type : "get",
			url : "workbench/transaction/history",
			cache : false,
			data : {
				"transId" : "${transaction.id}",
				"pageNo" : pageNo,
				"pageSize" : pageSize
			},
			success : function(data){
				var html = "";
				$(data.dataList).each(function(){
					html += '<tr>';
					html += '<td>'+this.stage+'</td>';
					html += '<td>'+this.money+'</td>';
					html += '<td>'+json[this.stage]+'</td>';
					html += '<td>'+this.expectedDate+'</td>';
					html += '<td>'+this.createTime+'</td>';
					html += '<td>'+this.createBy+'</td>';
					html += '</tr>';
					
				});
				$("#transHisTbody").html(html);
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
						history(data.currentPage , data.rowsPerPage);
					}
				});
			},
		});
	};
	function tip(){
		alert("哎~这个按钮不能返回..");
	}
</script>

</head>
<body>
	<!-- 返回按钮 -->
	<div style="position: relative; top: 35px; left: 10px;">
		<a href="javascript:void(0);" onclick="tip()"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
	</div>
	
	<!-- 大标题 -->
	<div style="position: relative; left: 40px; top: -30px;">
		<div class="page-header">
			<h3>${transaction.customerId}-${transaction.name} <small>￥${transaction.money}</small></h3>
		</div>
		<div style="position: relative; height: 50px; width: 250px;  top: -72px; left: 700px;">
			<button type="button" class="btn btn-default" onclick="window.location.href='workbench/transaction/edit?id=${transaction.id}'"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
			<button id="del" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>

	<!-- 阶段状态 -->
	<div style="position: relative; left: 40px; top: -50px;">
		阶段&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<%
			Transaction trans = (Transaction)request.getAttribute("transaction");
			String stage = trans.getStage();
			String pastStage = (String)request.getAttribute("pastStage");//当前阶段可能性
			
			List<DictionaryValue> stageList= (List<DictionaryValue>)application.getAttribute("transactionStageList");
			
			int firstFailIndex = 0;//第一个失败阶段的下标
			int currentIndex = 0;//当前阶段下标
			int pastIndex = 0;//上一次阶段下标
			for(int i = 0;i < stageList.size();i++){
				DictionaryValue dv  = stageList.get(i);
				String dvStage = dv.getContent();
				String possibility = pMap.get(dvStage);
				if("0".equals(possibility)){
					firstFailIndex = i;
				}
				if(stage.equals(dvStage)){
					currentIndex = i;
				}
				if(pastStage.equals(dvStage)){
					pastIndex = i;
				}
			}
			//动态点灯
				for(int i = 0;i < stageList.size();i++){
					DictionaryValue dv  = stageList.get(i);
					String dvStage = dv.getContent();
					String dvVal = dv.getValue();
					if(currentIndex < firstFailIndex){//正常交易
						if(i < currentIndex){//正常阶段
							%>
								<span id="<%=i%>" onclick="changeIcon('<%=dvVal%>','<%=i%>')" class="glyphicon glyphicon-ok-circle mystage" data-toggle="popover" data-placement="bottom" data-content="<%=dvStage%>" style="color: #90F790;"></span>
								-----------
							<%
						}else if(i == currentIndex){//正在进行
							%>
								<span id="<%=i%>" onclick="changeIcon('<%=dvVal%>','<%=i%>')" class="glyphicon glyphicon-map-marker mystage" data-toggle="popover" data-placement="bottom" data-content="<%=dvStage%>" style="color: #90F790;"></span>
								-----------
							<%	
						}else{//未进行
							//失败的标记
							if(i >= firstFailIndex -1){
								%>
								<span id="<%=i%>" onclick="changeIcon('<%=dvVal%>','<%=i%>')" class="glyphicon glyphicon-remove mystage" data-toggle="popover" data-placement="bottom" data-content="<%=dvStage%>"></span>
								-----------
								<%
							}else{
								%>
								<span id="<%=i%>" onclick="changeIcon('<%=dvVal%>','<%=i%>')" class="glyphicon glyphicon-record mystage" data-toggle="popover" data-placement="bottom" data-content="<%=dvStage%>"></span>
								-----------
								<%
							}
						}
						
					}else{//交易失败
						//上一次的阶段下标之前绿色，当前失败阶段为红x，其他为黑x
						if(i <= pastIndex){
							%>
							<span id="<%=i%>" onclick="changeIcon('<%=dvVal%>','<%=i%>')" class="glyphicon glyphicon-ok-circle mystage" data-toggle="popover" data-placement="bottom" data-content="<%=dvStage%>" style="color: #90F790;"></span>
							-----------
							<%
						}else{//失败其他为黑x
							if(i == currentIndex){//失败所在阶段
								%>
								<span id="<%=i%>" onclick="changeIcon('<%=dvVal%>','<%=i%>')" class="glyphicon glyphicon-remove mystage" data-toggle="popover" data-placement="bottom" data-content="<%=dvStage%>" style="color: red;"></span>
								-----------
								<%	
							}else{
								%>
								<span id="<%=i%>" onclick="changeIcon('<%=dvVal%>','<%=i%>')" class="glyphicon glyphicon-remove mystage" data-toggle="popover" data-placement="bottom" data-content="<%=dvStage%>"></span>
								-----------
								<%
							}
						}
					}
				}
		%>
		<%-- 
		<span class="glyphicon glyphicon-ok-circle mystage" data-toggle="popover" data-placement="bottom" data-content="资质审查" style="color: #90F790;"></span>
		-----------
		<span class="glyphicon glyphicon-ok-circle mystage" data-toggle="popover" data-placement="bottom" data-content="需求分析" style="color: #90F790;"></span>
		-----------
		<span class="glyphicon glyphicon-ok-circle mystage" data-toggle="popover" data-placement="bottom" data-content="价值建议" style="color: #90F790;"></span>
		-----------
		<span class="glyphicon glyphicon-ok-circle mystage" data-toggle="popover" data-placement="bottom" data-content="确定决策者" style="color: #90F790;"></span>
		-----------
		<span class="glyphicon glyphicon-map-marker mystage" data-toggle="popover" data-placement="bottom" data-content="提案/报价" style="color: #90F790;"></span>
		-----------
		<span class="glyphicon glyphicon-record mystage" data-toggle="popover" data-placement="bottom" data-content="谈判/复审"></span>
		-----------
		<span class="glyphicon glyphicon-record mystage" data-toggle="popover" data-placement="bottom" data-content="成交"></span>
		-----------
		<span class="glyphicon glyphicon-record mystage" data-toggle="popover" data-placement="bottom" data-content="丢失的线索"></span>
		-----------
		<span class="glyphicon glyphicon-record mystage" data-toggle="popover" data-placement="bottom" data-content="因竞争丢失关闭"></span>
		-----------
		--%>
		<span class="closingDate">${transaction.expectedDate}</span>
	</div>
	
	<!-- 详细信息 -->
	<div style="position: relative; top: 0px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">所有者</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${transaction.owner}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">金额</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${transaction.money}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">名称</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${transaction.customerId}-${transaction.name}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">预计成交日期</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${transaction.expectedDate}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">客户名称</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${transaction.customerId}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">阶段</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b id="c-stage">${transaction.stage}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">类型</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b id="type">${transaction.type}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">可能性</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b id="passbile">${transaction.possibility}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 40px;">
			<div style="width: 300px; color: gray;">来源</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${transaction.source}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">市场活动源</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${transaction.activityId}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 50px;">
			<div style="width: 300px; color: gray;">联系人名称</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${transaction.contactId}</b></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 60px;">
			<div style="width: 300px; color: gray;">创建者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${transaction.createBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${transaction.createTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 70px;">
			<div style="width: 300px; color: gray;">修改者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b id="editBy">${transaction.editBy}&nbsp;&nbsp;</b><small id="editTime" style="font-size: 10px; color: gray;">${transaction.editTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 80px;">
			<div style="width: 300px; color: gray;">描述</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${transaction.description}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 90px;">
			<div style="width: 300px; color: gray;">联系纪要</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${transaction.contactSummary}&nbsp;
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 100px;">
			<div style="width: 300px; color: gray;">下次联系时间</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>&nbsp;${transaction.nextContactTime}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
	</div>
	
	<!-- 备注 -->
	<div style="position: relative; top: 100px; left: 40px;">
		<div class="page-header">
			<h4>备注</h4>
		</div>
		
		<!-- 备注1 -->
		<%-- <div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>哎呦！</h5>
				<font color="gray">交易</font> <font color="gray">-</font> <b>某某某公司-交易01</b> <small style="color: gray;"> 2017-01-22 10:10:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div>
		
		<!-- 备注2 -->
		<div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>呵呵！</h5>
				<font color="gray">交易</font> <font color="gray">-</font> <b>某某某公司-交易01</b> <small style="color: gray;"> 2017-01-22 10:20:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div>
		--%>
		
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
					<button onclick="addNote()" type="button" class="btn btn-primary">保存</button>
				</p>
			</form>
		</div>
	</div>
	
	<!-- 阶段历史 -->
	<div>
		<div style="position: relative; top: 100px; left: 40px;">
			<div class="page-header">
				<h4>阶段历史</h4>
			</div>
			<div style="position: relative;top: 0px;">
				<table id="activityTable" class="table table-hover" style="width: 900px;">
					<thead>
						<tr style="color: #B3B3B3;">
							<td>阶段</td>
							<td>金额</td>
							<td>可能性</td>
							<td>预计成交日期</td>
							<td>修改时间</td>
							<td>修改者</td>
						</tr>
					</thead>
					<tbody id="transHisTbody">
						<!-- <tr>
							<td>资质审查</td>
							<td>5,000</td>
							<td>10</td>
							<td>2017-02-07</td>
							<td>2016-10-10 10:10:10</td>
							<td>zhangsan</td>
						</tr>
						<tr>
							<td>需求分析</td>
							<td>5,000</td>
							<td>20</td>
							<td>2017-02-07</td>
							<td>2016-10-20 10:10:10</td>
							<td>zhangsan</td>
						</tr>
						<tr>
							<td>谈判/复审</td>
							<td>5,000</td>
							<td>90</td>
							<td>2017-02-07</td>
							<td>2017-02-09 10:10:10</td>
							<td>zhangsan</td>
						</tr> -->
					</tbody>
				</table>
				<table class="table table-hover" style="width: 900px;">
					<tr><td><div id="page"></div></td></tr>
				</table>
			</div>
		</div>
	</div>
	
	<div style="height: 200px;"></div>
	
</body>

<script type="text/javascript">
//点灯改变图标
function changeIcon(stage,id){
	if(confirm("确定要改变交易阶段")){
		$.ajax({
			type : "post",
			url : "workbench/transaction/updateByStage",
			data : {
				"id" : "${transaction.id}",
				"stageVal" : stage,
				"money" : "${transaction.money}",
				"expectedDate" : "${transaction.expectedDate}"
			},
			success : function(json){
				//{"success":"","stage","","editBy":"","editTime","possibility"}/passbile
				$("#passbile").text(json.trans.possibility);
				$("#s-stage").text(json.trans.stage);
				$("#editBy").html(json.trans.editBy + "&nbsp;&nbsp;");
				$("#editTime").text(json.trans.editTime);
				for(var i = 0; i < <%=pMap.size()%>;i++){
					 var successIndex = <%=firstFailIndex-1%>;
					if( id < successIndex){
						if(i < id){
							$("#" + i).removeClass();
							$("#" + i).addClass("glyphicon glyphicon-ok-circle mystage");
							$("#" + i).css("color","#90F790");
						}else if( i == id){
							$("#" + i).removeClass();
							$("#" + i).addClass("glyphicon glyphicon-map-marker mystage");
							$("#" + i).css("color","#90F790");
						}else{
							if(i >= successIndex){
								$("#" + i).addClass("glyphicon glyphicon-remove mystage");
								$("#" + i).css("color","black");
							}else{
								$("#" + i).removeClass();
								$("#" + i).addClass("glyphicon glyphicon-record mystage");
								$("#" + i).css("color","black");
							}
						}
					}else{
						if(i == id){
							$("#" + i).removeClass();
							$("#" + i).addClass("glyphicon glyphicon-remove mystage");
							$("#" + i).css("color","red");
						}else{
							$("#" + i).removeClass();
							$("#" + i).addClass("glyphicon glyphicon-remove mystage");
							$("#" + i).css("color","black");
						}
					} 
				}
				history(1,10);
			},
		})
	}
};

</script>
</html>