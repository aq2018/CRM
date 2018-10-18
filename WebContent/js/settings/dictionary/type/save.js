$(function(){
		$("#saveBtn").click(function(){
			$("#code").blur();
			if($("#tips").text() == ""){
				$("#saveForm").submit();
			}
		});
		//检查编码重复
		$("#code").blur(function(){
			var code = $.trim(this.value);
			var regExp = /^[0-9a-zA-Z]+$/;
			if(code == ""){
				$("#tips").text("请输入编码");
			}else if(!regExp.test(code)){
				$("#tips").text("请输入数字或字母");
 			}else{
				//设置全局ajax为同步方式，即锁住浏览器
				$.ajaxSetup({
					async : false
				});
				$.ajax({
					url : "settings/dictionary/type/checkByCode.do",
					data : {
						"type" : code,
						"_" : new Date().getTime()
					},
					success : function(data){
						if(data.success){
							$("#tips").text("");
						}else{
							$("#tips").text("已有该编码");
						}
					//解锁浏览器
					$.ajaxSetup({
						async : true
					});
					}
					
				});
			}
		});
		
		$("#code").focus(function(){
			if($.trim($(this).val()) == ""){
				$(this).val("");
			}
		})
		
	})