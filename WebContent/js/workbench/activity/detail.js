$(function() {
	$("#remark").focus(function() {
		if (cancelAndSaveBtnDefault) {
			//设置remarkDiv的高度为130px
			$("#remarkDiv").css("height", "130px");
			//显示
			$("#cancelAndSaveBtn").show("2000");
			cancelAndSaveBtnDefault = false;
		}
	});

	$("#cancelBtn").click(function() {
		//显示
		$("#cancelAndSaveBtn").hide();
		//设置remarkDiv的高度为130px
		$("#remarkDiv").css("height", "90px");
		cancelAndSaveBtnDefault = true;
	});

	$("#tbody").on("mouseover", ".remarkDiv", function() {
		$(this).children("div").children("div").show();
	});
	$("#tbody").on("mouseout", ".remarkDiv", function() {
		$(this).children("div").children("div").hide();
	});

	$("#edit-startTime").datetimepicker({
		minView : "month",
		language : 'zh-CN',
		format : 'yyyy-mm-dd',
		autoclose : true,
		todayBtn : true,
		pickerPosition : "bottom-left"
	});
	$("#edit-endTime").datetimepicker({
		minView : "month",
		language : 'zh-CN',
		format : 'yyyy-mm-dd',
		autoclose : true,
		todayBtn : true,
		pickerPosition : "bottom-left"
	});
	//验证
	$("#edit-marketActivityName").blur(function() {
		var name = $.trim(this.value);
		if (name == "") {
			$("#nameTip").text("请输入活动名称");
		} else {
			$("#nameTip").text("");
		}
	});
	$("#edit-marketActivityName").focus(function() {
		var name = $.trim(this.value);
		if (name == "") {
			$(this).val("");
			$("#nameTip").text("请输入活动名称");
		}
	});

	$("#edit-startTime").blur(function() {
		var startTime = $.trim(this.value);
		if (startTime == "") {
			$("#startTip").text("请选择开始日期");
		} else {
			$("#startTip").text("");
		}
	});
	$("#edit-startTime").focus(function() {
		var startTime = $.trim(this.value);
		if (startTime == "") {
			$(this).val("");
			$("#startTip").text("请选择开始日期");
		}
	});
	$("#edit-cost").blur(function() {
		var cost = $.trim(this.value);
		var regExp = /^[1-9]([0-9]*|.[0-9]*)$/;
		if (cost == "") {
			$("#costTip").text("请输入成本");
		} else if (!regExp.test(cost)) {
			$("#costTip").text("请输入大于0的数字");
		} else {
			$("#costTip").text("");
		}
	});
	$("#edit-cost").focus(function() {
		var cost = $.trim(this.value);
		if (cost == "") {
			$(this).val("");
			$("#costTip").text("请输入成本");
		}
	});
	//更新
	$("#updateBtn").click(
			function() {
				$("#edit-marketActivityName").blur();
				$("#edit-startTime").blur();
				$("#edit-startTime").blur();
				$("#edit-cost").blur();
				if ($("#startTip").text() == "" && $("#nameTip").text() == ""
						&& $("#costTip").text() == "") {
					updateAct();
				}
			});
	//更新活动
	function updateAct() {
		var id = $("#edit-marketActivityOwner").val();
		$.post("workbench/activity/updateAct.do", {
			"id" : id,
			"owner" : $("#edit-marketActivityOwner").val(),
			"name" : $.trim($("#edit-marketActivityName").val()),
			"startTime" : $.trim($("#edit-startTime").val()),
			"endTime" : $.trim($("#edit-endTime").val()),
			"cost" : $.trim($("#edit-cost").val()),
			"description" : $.trim($("#edit-describe").val()),
		}, function(json) {
			if (json.success) {
				$("#editActivityModal").modal("hide");
				window.location.href = "workbench/activity/detail.do?id=" + id;
			} else {
				alert("更新失败");
			}
		});
	}
	;

	getOwner();
})

function getOwner() {
	$.ajax({
		type : "get",
		url : "settings/qx/user/getOwner.do",
		cache : false,
		success : function(json) {
			var html = "";
			$.each(json, function(i, n) {
				html += "<option value=" + n.id + ">" + n.username
						+ "</option>";
			});
			$("#edit-marketActivityOwner").html(html);

		}
	});
}
function formatDate(now) {
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var date = now.getDate();
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	if (minute < 10) {
		minute = "0" + minute;
	}
	return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":"
			+ second;
}

//编辑备注
function editNote(id) {
	$("#editNoteId").val(id);
	$("#editNote-describe").val($("#_" + id).text());
	$("#editNoteModal").modal("show");

}
//删除备注
function delNote(id) {
	if (confirm("确认删除备注？")) {
		$.get("workbench/activity/delNoteById.do", {
			"id" : id
		}, function(json) {
			if (json.success) {
				$("#" + id).remove();
			} else {
				alert("删除失败");
			}
		});
	}
}
