$(function() {
	ajax();
	function ajax() {
		$("#tbody").html("");
		$.get("settings/dictionary/value/getList.do", function(data) {
			var i = 1;
			$(data).each(function() {
						$("#tbody").append("<tr><td><input type='checkbox' name='fx' value='"
										+ this.id + "'/></td><td>" + (i++)
										+ "</td><td>" + this.value
										+ "</td><td>" + this.content
										+ "</td><td>" + this.sortNo
										+ "</td><td>" + this.type
										+ "</td></tr>");
						$("#tbody tr:even").addClass("active");
					});
		}, "json");
	}
	;
	// 全选
	$("#qx").click(function() {
		if ($(this).is(":checked")) {
			$("input[name=fx]").prop("checked", true);
		} else {
			$("input[name=fx]").removeProp("checked");
		}
	});

	// 修改
	$("#editBtn")
			.click(
					function() {
						$fxValue = $("input[name='fx']:checked");
						if ($fxValue.length == 0) {
							alert("请选择一条数据修改");
						} else if ($fxValue.length == 1) {
							window.location.href = "settings/dictionary/value/edit.do?id="
									+ $fxValue.val();
						} else {
							alert("一次请修改一条数据");

						}
					});

	// 删除
	$("#delBtn").click(function() {
		$fxs = $("input[name='fx']:checked");
		if ($fxs.length == 0) {
			alert("请选择一条数据删除");
		} else {
			// "id=10&id=20",
			var ids = "";
			for (var i = 0; i < $fxs.length; i++) {
				ids += "id=" + $($fxs[i]).val();
				if (i < $fxs.length - 1) {
					ids += "&";
				}
			}
			window.location.href = "settings/dictionary/value/del.do?" + ids;
		}

	});
})