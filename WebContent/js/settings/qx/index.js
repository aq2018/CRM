$(function(){
	//正常修改密码
	$("#oldPwd").blur(function(){
		var val = this.value;
		if(val == ""){
			$("#tip1").text("请输入原密码");
		}else{
			$("#tip1").text("");
			$.ajaxSetup({
				async : false
			});
			$.post(
				"settings/qx/user/checkOldPwd.do",
				{"account" : $("#u-account").text(),"password" : val},
				function(json){
					if(json.success){
						$("#tip1").text("");
					}else{
						$("#tip1").text("原密码错误");
					}
				}
			)
		}
	});
	
	$("#newPwd").blur(function(){
		var val = this.value;
		if(val == ""){
			$("#tip2").text("请输入新密码");
		}else{
			$("#tip2").text("");
		}
	})
	
	$("#confirmPwd").blur(function(){
		var val = this.value;
		var newPwd = $("#newPwd").val();
		if(val != newPwd){
			$("#tip3").text("两次密码不一致");
		}else{
			$("#tip3").text("");
		}
	})
	$("#oldPwd").focus(function(){
		$("#tip1").text("");
	})
	$("#newPwd").focus(function(){
		$("#tip2").text("");
	})
	$("#confirmPwd").focus(function(){
		$("#tip3").text("");
	})
	
	$("#resetPwd").click(function(){
		if($("#tip1").text() != "原密码错误"){
			$("#oldPwd").blur();
		}
		$("#newPwd").blur();
		$("#confirmPwd").blur();
		if($("#tip1").text() == "" && $("#tip2").text() == "" && $("#tip3").text() == ""){
			alert("请勿修改密码");
			/*$.post(
				"settings/qx/user/resetPwd.do",
				{
					"account" : "${user.account}",
					"password" : $("#newPwd").val()
				},
				function(json){
					if(json.success){
						if(confirm("密码已更改，请重新登录")){
							window.location = "/login.jsp";
						}
						window.location = "/login.jsp";
					}else{
						alert("重置密码失败");
					}
				}
			)*/
		}
	})
})
function logout(){
	window.location = "logout.do";
}
