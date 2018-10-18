$(function(){
	var deptMap = {};
	$("#create-org").typeahead({
		source : function(query,process){
			var names = [];
			$.post("settings/dept/getDeptByName.do",{"name":query},function(json){
				$(json).each(function(){
					deptMap[this.dname] = this.deptno
					names.push(this.dname);
				})
				process(names);
			})
		},
		delay : 500,
		items : 5,
		afterSelect : function(item){
			$("#deptno").val(deptMap[item]);
		}
	})
	
	$("#updateBtn").click(function(){
		//$("#editForm").submit();
		//data-dismiss="modal"
		//s$ettings/qx/user/update.do
		
		$("#tip").text("");
		var regExp = /^(\d+.\d+.\d+.\d+)(,(\d+.\d+.\d+.\d+))*$/;
		var ip = $.trim($("#edit-allowIps").val());
		$.ajax({
			type : "post",
			url : "settings/qx/user/update.do",
			data : {
				"id" : $("#edit-id").val(),
				"account" : $.trim($("#edit-account").val()),
				"username" : $.trim($("#edit-username").val()),
				"deptno" : $("#deptno").val(),
				"email" : $.trim($("#edit-email").val()),
				"invalid_time" : $.trim($("#edit-expireTime").val()),
				"permit_ip" : ip,
				"lockState" : $.trim($("#edit-lockStatus").val()),
				"password" : $("#edit-password").val()
			},
			cache : false,
			beforeSend : function(){
				if($.trim($("#edit-account").val()) == ""){
					$("#tip").text("账号不能为空");
					return false;
				}
				if($.trim($("#edit-password").val()) == ""){
					$("#tip").text("密码不能为空");
					return false;
				}
				if(ip != ""){
					if(!regExp.test(ip)){
						$("#tip").text("请输入正确的ip地址");
						return false;
					}
				}
				return true;
			},
			success : function(json){
				if(json.success){
					//
					$("#u-account").text(json.data.account);
					$("#u-name").text(json.data.username);
					$("#u-email").text(json.data.email);
					$("#u-invalid_time").text(json.data.invalid_time);
					$("#u-permit_ip").text(json.data.permit_ip);
					$("#u-lockState").text(json.data.lockState);
					$("#u-deptname").text($("#create-org").val());
					$("#editUserModal").modal("hide");
				}else{
					alert("修改失败")
				}
			}
		})	
	});
})