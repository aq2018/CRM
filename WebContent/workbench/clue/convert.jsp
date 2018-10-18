<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<!--分页插件-->
	<link rel="stylesheet" href="jquery/bs_pagination/css/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/locale/bs_pagination.zh-CN.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/js/jquery.bs_pagination.min_zh-CN.js"></script>
<script type="text/javascript">
	$(function(){
		$("#isCreateTransaction").click(function(){
			if(this.checked){
				$("#create-transaction2").show(200);
			}else{
				$("#create-transaction2").hide(200);
			}
		});
		
		$("#expectedClosingDate").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
	        autoclose: true,
	        todayBtn: true,
	        pickerPosition: "bottom-left"
		});
		
		//查询未关联的市场活动
		$("#queryAct").keydown(function(event){
			if(event.keyCode == 13){
				getActsOriginByName(1,10);
				return false;//事件冒泡/事件传递--关闭它
			}
		});
		
		
		//转换
		$("#convert").click(function(){
			if($("#isCreateTransaction").is(":checked")){
				$("#flag").val("1");
				$("#createTransForm").submit();
			}else{
				$("#flag").val("");
				window.location = "workbench/clue/convert.do?clueId=${param.clueId}";
			}
		});
		
	});//end
	//通过name查询市场关联sadasdsaasd
	function getActsOriginByName(pageNo,pageSize){
		$.ajax({
			type : "get",
			url : "workbench/clue/getActsOrigin.do",
			cache : false,
			data : {
				"clueId" : "${param.clueId}",
				"name" : $.trim($("#queryAct").val()),
				"pageNo" : pageNo,
				"pageSize" : pageSize
			},
			success : function(data){
				var html = "";
				$(data.dataList).each(function(){
					html += '<tr>';
					html += '<td><input type="radio" name="act" value='+this.id+' onclick="setActName(\''+this.name+'\')"></td>';
					html += '<td>'+this.name+'</td>';
					html += '<td>'+this.start_date+'</td>';
					html += '<td>'+this.end_date+'</td>';
					html += '<td>'+this.owner+'</td>';
					html += '</tr>';
				});
				$("#tbody").html(html);
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
						queryActByName(data.currentPage , data.rowsPerPage);
					}
				});
			},
		});
	};
	//搜索图标
	function reset(){
		$("#tbody").empty();
		$("#page").empty();
		$("#searchActivityModal").modal("show");
	}
	function setActName(name){
		$("#actName").val(name);
	}
	//选择活动源
	function choiceAct(){
		var actid = $("input[name='act']").val();
		$("#actId").val(actid);
		$("#activity").val($("#actName").val());
		$("#searchActivityModal").modal("hide");
	};
</script>

</head>
<body>
	
	<!-- 搜索市场活动的模态窗口 -->
	<div class="modal fade" id="searchActivityModal" role="dialog" >
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">搜索市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						  	 <input type="hidden" class="form-control" id="actName">
						    <input id="queryAct" type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="tbody">
							<!-- <tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>
							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr> -->
						</tbody>
					</table>
				</div>
				<div id="page" align="left"></div>
				<div class="modal-footer">
					<button onclick="choiceAct()" type="button" class="btn btn-primary">确定</button>
				</div>
			</div>
		</div>
	</div>

	<div id="title" class="page-header" style="position: relative; left: 20px;">
		<h4>转换线索 <small>${param.fullname}- ${param.company}</small></h4>
	</div>
	<div id="create-customer" style="position: relative; left: 40px; height: 35px;">
		新建客户：${param.company}
	</div>
	<div id="create-contact" style="position: relative; left: 40px; height: 35px;">
		新建联系人：${param.fullname}
	</div>
	<div id="create-transaction1" style="position: relative; left: 40px; height: 35px; top: 25px;">
		<input type="checkbox" id="isCreateTransaction"/>
		为客户创建交易
	</div>
	<div id="create-transaction2" style="position: relative; left: 40px; top: 20px; width: 80%; background-color: #F7F7F7; display: none;" >
	
		<form id="createTransForm" method="post" action="workbench/clue/convert.do">
		  <div class="form-group" style="width: 400px; position: relative; left: 20px;">
		    <label for="amountOfMoney">金额</label>
		    <input type="hidden" class="form-control" name="clueId" value="${param.clueId}">
		    <input type="hidden" class="form-control" id="flag" name="flag">
		    <input type="text" class="form-control" name="amountOfMoney">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="tradeName">交易名称</label>
		    <input type="text" class="form-control" name="tradeName" value="${param.company}-">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="expectedClosingDate">预计成交日期</label>
		    <input type="text" class="form-control" id="expectedClosingDate" name="expectedClosingDate">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="stage">阶段</label>
		    <select name="stage"  class="form-control">
		    	<!-- <option></option>
		    	<option>资质审查</option>
		    	<option>需求分析</option>
		    	<option>价值建议</option>
		    	<option>确定决策者</option>
		    	<option>提案/报价</option>
		    	<option>谈判/复审</option>
		    	<option>成交</option>
		    	<option>丢失的线索</option>
		    	<option>因竞争丢失关闭</option> -->
		    	<option></option>
		    	<c:forEach items="${stageList}" var="c">
		    		<option>${c.content}</option>
		    	</c:forEach>
		    </select>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="activity">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" onclick="reset()" style="text-decoration: none;"><span class="glyphicon glyphicon-search"></span></a></label>
		    <input type="hidden" class="form-control" id="actId">
		    <input type="text" class="form-control" id="activity" placeholder="点击上面搜索" readonly>
		  </div>
		</form>
		
	</div>
	
	<div id="owner" style="position: relative; left: 40px; height: 35px; top: 50px;">
		<input type="hidden" id="ownerId" value="${param.pid_user}">
		记录的所有者：<br>
		<b>${param.owner}</b>
	</div>
	<div id="operation" style="position: relative; left: 40px; height: 35px; top: 100px;">
		<input id="convert" class="btn btn-primary" type="button" value="转换">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-default" type="button" value="取消" onclick="window.history.back()">
	</div>
</body>
</html>