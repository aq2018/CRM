$(function() {
	// 更新
	$("#updateBtn").click(function() {
		var valtip = $("#valtip").text();
		var texttip = $("#texttip").text();
		var ordertip = $("#ordertip").text();
		if (valtip == "" && texttip == "" && ordertip == "") {
			$("#updateForm").submit();
		}

	});

	$("#edit-dicValue").blur(function() {
		var val = $.trim(this.value);
		if (val == "") {
			$("#valtip").text("请输入字典值");
		} else {
			$("#valtip").text("");
		}
	});
	$("#edit-text").blur(function() {
		var text = $.trim(this.value);
		if (text == "") {
			$("#texttip").text("请输入文本");
		} else {
			$("#texttip").text("");
		}
	});
	$("#edit-orderNo").blur(function() {
		var orderno = $.trim(this.value);
		if (orderno == "") {
			$("#ordertip").text("请输入排序号");
		} else {
			$("#ordertip").text("");
		}
	});

});
