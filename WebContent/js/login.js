$(function() {
	//$("#account").focus();
	$("#lgBtn").click(function() {
		login();
	});
	$(window).keydown(function(event) {
		var code = event.keyCode;
		if (code == "13") {
			login();
		}
	});
	
	$("#fg").click(function() {
		$("#forgot").css("display", "block");
		$("#login").css("display", "none");
		$("#logAct").focus();
	});
	$("#lg").click(function() {
		$("#forgot").css("display", "none");
		$("#login").css("display", "block");
		$("#account").focus();
	});
	$("#logAct").focus(function(){
		$("#tip1").text("");
		var val = $.trim(this.value);
		if(val == ""){
			$("#tip1").val("");
		}
	})
	$("#logAct").blur(function(){
		var val = $.trim(this.value);
		if(val == ""){
			$("#tip1").text("请输入登录用户名");
		}else{
			$("#tip1").text("");
		}
	})
	$("#logEmail").focus(function(){
		$("#tip2").text("");
		var val = $.trim(this.value);
		if(val == ""){
			$("#tip2").val("");
		}
	})
	$("#logEmail").blur(function(){
		var regExp1 = /^[a-zA-Z]/;
		var regExp2 = /^\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}$/;
		var val = $.trim(this.value);
	    if (!regExp2.test(val)) {
			$("#tip2").text("请输入正确的邮箱地址");
		}else if (!regExp1.test(val)) {
			$("#tip2").text("请以字母开头");
		}else{
			$("#tip2").text("");
		}
	})
	$("#btn").click(function(){
		$("#logAct").blur();
		$("#logEmail").blur();
		if($("#tip1").text() == "" && $("#tip2").text() == ""){
			sendMail();
		}
	});

});
function login() {
	$("#errMsg").text("");
	var account = $.trim($("#account").val());
	var passwd = $("#passwd").val();
	var flag = "0";
	if ($("#remember").prop("checked")) {
		flag = "1";
	}
	$.ajax({
		type : "post",
		url : "login",
		data : {
			"account" : account,
			"passwd" : passwd,
			"flag" : flag
		},
		beforeSend : function() {
			if (account == "" || passwd == "") {
				$("#errMsg").text("账号或密码不能为空");
				return false;
			}
			return true;
		},
		success : function(data) {
			//{"success":true}
			//{"success":false,"errMsg":"??"}
			if (data.success) {
				//
				$("#errMsg").text("");
				window.location = "workbench/index.jsp";
			} else {
				$("#errMsg").text(data.errMsg);
			}

		}
	})
};
function sendMail() {
	$.ajax({
		type : "post",
		url : "sendMail",
		data : {
			"account" : $("#logAct").val(),
			"email" : $("#logEmail").val()
		},
		success : function(json) {
			if (json.success) {
				$("#tip2").html(
						"<span style='color:green;font:14px;'>" + json.msg
								+ "<span>");
				settime();
			} else {
				$("#tip2").text(json.msg);
			}
		}
	})
}
function forgot() {
	$("#tip3").text("");
	var code = $.trim($("#code").val());
	var logAct = $.trim($("#logAct").val());
	var email = $.trim($("#logEmail").val());
	$.ajax({
		type : "post",
		url : "forgot",
		data : {
			"code" : code
		},
		beforeSend : function() {
			if (logAct == "") {
				$("#tip1").text("请输入登录用户名");
				return false;
			}
			if (email == "") {
				$("#tip2").text("请输入邮箱地址");
				return false;
			}
			if (code == "") {
				$("#tip3").text("请输入验证码");
				return false;
			}
			return true;
		},
		success : function(json) {
			if (json.success) {
				$("#tip2").text("")
				alert("jump checkpwd page");
			} else {
				$("#tip3").text(json.msg);
			}
		}
	})
}

var countdown = 60;
function settime() {
    if(countdown == 0) {
        $("#btn").attr("disabled", false);
        $("#btn").attr("value", "获取验证码");
        countdown = 60;
        $("#tip2").html("");
        return;
    } else {
        $("#btn").attr("disabled", true);
        $("#btn").attr("value", "重新发送(" + countdown + ")");
        countdown--;
    }
    setTimeout(settime, 1000)
}