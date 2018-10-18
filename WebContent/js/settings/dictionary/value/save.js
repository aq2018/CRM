$(function() {
	getTypeCode();

	//添加
	$("#saveBtn").click(function() {
		var dicValue = $dicValue.val();
		var sortNo = $orderNo.val();
		var pid_dictType = $dicTypeCode.val();
		var content = $text.val();
		/* "settings/dictionary/value/add.do",
		{"dictValue":dicValue,"sortNo":sortNo,"pid_dictType":pid_dictType,"content":content}, */
		$("#create-dicTypeCode").blur();
		$("#create-dicValue").blur();
		$("#create-text").blur();
		$("#create-orderNo").blur();
		if ($("#typeTip").text() == "" && $("#typeTip").text() == ""
				&& $("#valueTip").text() == ""
				&& $("#orderTip").text() == "") {
			$("#addForm").submit();
		}
	});
	//表单校验
	$("#create-dicTypeCode").change(function() {
		var typeCode = this.value;
		if (typeCode == "") {
			$("#typeTip").text("请选择字典类型编码");
		} else {
			$("#typeTip").text("");
		}
	})

	$("#create-dicTypeCode").blur(function() {
		var typeCode = this.value;
		if (typeCode == "") {
			$("#typeTip").text("请选择字典类型编码");
		} else {
			$("#typeTip").text("");
		}
	});

	$("#create-dicValue").focus(function() {
		var typeCode = $("#create-dicTypeCode").val();
		var value = $.trim(this.value);
		if (typeCode == "") {
			$("#typeTip").text("请输入字典类型编码");
		} else {
			$("#typeTip").text("");
		}
		//如果为空清空
		if (value == "") {
			$(this).val("");
		}
	})

	//字典值
	$("#create-dicValue").blur(function() {
		var value = $.trim(this.value);
		if (value == "") {
			$("#valueTip").text("请输入字典值");
		} else {
			//验证该字典下value和编码是否重复
			$("#valueTip").text("");
			$.ajaxSetup({
				async : false
			});
			$.ajax({
				type : "get",
				url : "settings/dictionary/value/checkUniqueByTypeAndValue.do",
				data : {
					"typeid" : $("#create-dicTypeCode").val(),
					"value" : value
				},
				cache : false,
				success : function(data) {
					if (data.success) {
						$("#valueTip").text("");

					} else {
						$("#valueTip").text("该编码类型下已有该字典值");
					}
					$.ajaxSetup({
						async : true
					});
				}

			});
		}
	});

	//文本
	$("#create-text").focus(function() {
		var typeCode = $("#create-dicTypeCode").val();
		var text = $.trim(this.value);
		var value = $.trim($("#create-dicValue").val());
		if (value == "") {
			$("#valueTip").text("请输入字典值");
		}
		if (typeCode == "") {
			$("#typeTip").text("请输入字典值");
		}
		if (text == "") {
			$(this).val("");
		}
	});
	$("#create-text").blur(function() {
		var text = $.trim(this.value);
		if (text == "") {
			$("#textTip").text("请输入文本");
		} else {
			$("#textTip").text("");
		}
	});

	//排序号
	$("#create-orderNo").focus(function() {
		var typeCode = $("#create-dicTypeCode").val();
		var text = $.trim($("#create-text").val());
		var value = $.trim($("#create-dicValue").val());
		var order = $.trim(this.value);
		if (typeCode == "") {
			$("#typeTip").text("请输入字典编码");
		}
		if (value == "") {
			$("#valueTip").text("请输入字典值");
		}
		if (text == "") {
			$("#textTip").text("请输入文本");
		}
		if (order == "") {
			$(this).val("");
		}
	});

	$("#create-orderNo").blur(function() {
		var order = $.trim(this.value);
		if (order == "") {
			$("#orderTip").text("请输入排序号");
		} else {
			var regExp = /^[1-9][0-9]*$/;
			if (regExp.test(order)) {
				$("#orderTip").text("");
			} else {
				$("#orderTip").text("请输入数字");
			}
		}
	});

})
function getTypeCode() {
	var $dicValue = $("#create-dicValue");
	var $text = $("#create-text");
	var $orderNo = $("#create-orderNo");
	var $dicTypeCode = $("#create-dicTypeCode");
	//调用获取字典类型
	//[{"id":"1","type":"orgType"},{}]
	dicTypeCode.html("<option value=''></option>");
	$.get("settings/dictionary/value/getTypeCode.do", function(data) {
		$(data.dictList).each(function() {
			dicTypeCode.append("<option>" + this.type + "</option>");
		});
	}, "json");
};