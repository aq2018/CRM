$(function(){
	
	ajax();
	function ajax(){
		$("#tbody").html("");
		$.get(
			"settings/dictionary/type/getList.do",
			function(data){
				var i = 1;
				$(data.dictList).each(function(){
					$("#tbody").append("<tr><td><input type='checkbox' name='fx' value='" + this.id
								+ "'/></td><td>" +(i++)+ "</td><td>" + this.type
								+ "</td><td>" + this.name + "</td><td>" + this.description
								+ "</td></tr>");					
					$("#tbody tr:even").addClass("active");
				});
			},
			"json"
		);
	};
	
	//全选
	$("#qx").click(function(){
		if($(this).is(":checked")){
			$("input[name=fx]").prop("checked",true);
		}else{
			$("input[name=fx]").removeProp("checked");
		}
	});
	
	//编辑
	$("#editBtn").click(function(){
		$fx = $("input[name='fx']:checked");
		if($fx.length == 0){
			alert("请选择一条数据修改");
		}else if($fx.length > 1){
			alert("一次请修改一条数据");
		}else{
			window.location.href = "settings/dictionary/type/eidtType.do?id=" + $fx.val();
		}
	})
	//删除
	$("#delBtn").click(function(){
		$fxs = $("input[name='fx']:checked");
		if($fxs.length == 0){
			alert("请选择一条数据删除");
		}else{
			//"id=10&id=20",
			var $ids = "";
			for(var i = 0;i < $fxs.length;i++){
				$ids += "id="+$($fxs[i]).val();
				if(i < $fxs.length - 1){
					$ids += "&";
				}
			}
			window.location.href = "settings/dictionary/type/deltType.do?" + $ids;
		}

	});
	
})