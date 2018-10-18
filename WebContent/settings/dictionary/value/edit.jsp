<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/settings/dictionary/value/edit.js"></script>
<script type="text/javascript">
$(function(){
	setDictValue();
})
//获得编辑内容
function setDictValue() {// {"sortNo":1,"id":"1","type":"orgType","value":"1","content":"1"}
	$.get("settings/dictionary/value/getValue.do", {
		"id" : "${id}"
	}, function(data) {
		$("#edit-id").val("${id}");
		$("#edit-dicTypeCode").val(data.type);
		$("#edit-dicValue").val(data.val);
		$("#edit-text").val(data.content);
		$("#edit-orderNo").val(data.sortNo);
	}, "json");
};
</script>
</head>
<body>

	<div style="position:  relative; left: 30px;">
		<h3>修改字典值</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button id="updateBtn" type="button" class="btn btn-primary">更新</button>
			<button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form id="updateForm" class="form-horizontal" role="form" action="settings/dictionary/value/update.do" method="post">
					
		<div class="form-group">
			<label for="edit-dicTypeCode" class="col-sm-2 control-label">字典类型编码</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-dicTypeCode" name="type" style="width: 200%;" value="性别" readonly>
			</div>
		</div>
		
		<div class="form-group">
			<label for="edit-dicValue" class="col-sm-2 control-label">字典值<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="hidden" class="form-control" id="edit-id" name="id" style="width: 200%;">
				<input type="text" class="form-control" id="edit-dicValue" name="value" style="width: 200%;" value="m">
				<span id="valtip" style="color: red;font-size: 12px"></span>
			</div>
		</div>
		
		<div class="form-group">
			<label for="edit-text" class="col-sm-2 control-label">文本</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-text" name="content" style="width: 200%;" value="男">
				<span id="texttip" style="color: red;font-size: 12px"></span>
			</div>
		</div>
		
		<div class="form-group">
			<label for="edit-orderNo" class="col-sm-2 control-label">排序号</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-orderNo" name="sortNo" style="width: 200%;" value="1">
				<span id="ordertip" style="color: red;font-size: 12px"></span>
			</div>
		</div>
	</form>
	
	<div style="height: 200px;"></div>
</body>
</html>