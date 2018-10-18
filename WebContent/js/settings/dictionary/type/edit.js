$(function(){
	$("#type").blur(function(){
		var val = $.trim(this.value);
		if(val == ""){
			$("#tips").text("编码不能为空");
		}else{
			$("#tips").text("");
		}
	});
	$("#type").focus(function(){
		var val = $.trim(this.value);
		if(val == ""){
			$(this).val("");
		}
	})
	$("#updateBtn").click(function(){
		if($("#tips").text() == ""){
			$("#editForm").submit();
		}
	})
	
})