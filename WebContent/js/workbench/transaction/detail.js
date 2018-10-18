$(function(){
	$("#remark").focus(function(){
		if(cancelAndSaveBtnDefault){
			//设置remarkDiv的高度为130px
			$("#remarkDiv").css("height","130px");
			//显示
			$("#cancelAndSaveBtn").show("2000");
			cancelAndSaveBtnDefault = false;
		}
	});
	
	$("#cancelBtn").click(function(){
		//显示
		$("#cancelAndSaveBtn").hide();
		//设置remarkDiv的高度为130px
		$("#remarkDiv").css("height","90px");
		cancelAndSaveBtnDefault = true;
	});
	
	$("body").on("mouseover",".remarkDiv",function(){
		$(this).children("div").children("div").show();
	});

	$("body").on("mouseout" , ".remarkDiv" , function(){
		$(this).children("div").children("div").hide();
	});
	
	//阶段提示框
	$(".mystage").popover({
        trigger:'manual',
        placement : 'bottom',
        html: 'true',
        animation: false
    }).on("mouseenter", function () {
                var _this = this;
                $(this).popover("show");
                $(this).siblings(".popover").on("mouseleave", function () {
                    $(_this).popover('hide');
                });
            }).on("mouseleave", function () {
                var _this = this;
                setTimeout(function () {
                    if (!$(".popover:hover").length) {
                        $(_this).popover("hide")
                    }
                }, 100);
            });
	
	getRemark();
	//更新备注
	$("#updateRemarkBtn").click(function(){
		var description = $.trim($("#editRemark-description").val());
		var id = $("#editRemarkId").val();
		if(description == ""){
			alert("不能为空！");
		}else{
			// 发送ajax post，更新备注
			$.post(
				"workbench/transaction/updateRemark",
				{
					"id" : id,
					"description" : description
				},
				function(json){
					// {"success":true , "remark" : {"editTime":"","editBy":"","description":""}}
					//线索 - 李四先生-动力节点 2017-01-22 10:20:10 由zhangsan
					// {"success":false}
					if(json.success){
						// 隐藏modal
						$("#editRemarkModal").modal("hide");
						// 更新前端div
						$("#_" + id).text(json.remark.description);
						$("#s_" + id).text(json.remark.editTime + " 由" + json.remark.editBy);
					}else{
						alert("更新备注失败！");
					}
				}
			);
		}
	});
	//交易历史
	history(1,10);
})