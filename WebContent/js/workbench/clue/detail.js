//默认情况下取消和保存按钮是隐藏的
var cancelAndSaveBtnDefault = true;
$(function() {
	getOwner();
	// 获取所有者
	function getOwner() {
		$.get("settings/qx/user/getOwner.do", function(data) {
			var html = "";
			$(data).each(
					function() {
						html += "<option value='" + this.id + "'>"
								+ this.username + "</option>";
					})
			$("#edit-clueOwner").html(html);
		})
	}
	$("#remark").focus(function() {
		if (cancelAndSaveBtnDefault) {
			// 设置remarkDiv的高度为130px
			$("#remarkDiv").css("height", "130px");
			// 显示
			$("#cancelAndSaveBtn").show("2000");
			cancelAndSaveBtnDefault = false;
		}
	});

	$("#cancelBtn").click(function() {
		// 显示
		$("#cancelAndSaveBtn").hide();
		// 设置remarkDiv的高度为130px
		$("#remarkDiv").css("height", "90px");
		cancelAndSaveBtnDefault = true;
	});

	// 更新备注
	$("#updateRemarkBtn").click(
			function() {
				var noteContent = $.trim($("#editRemark-description").val());
				var id = $("#editRemarkId").val();
				if (noteContent == "") {
					alert("不能为空！");
				} else {
					// 发送ajax post，更新备注
					$.post("workbench/clue/updateRemark.do", {
						"id" : id,
						"description" : noteContent
					}, function(json) {
						// {"success":true , "activityRemark" :
						// {"editTime":"","editBy":"","description":""}}
						// 线索 - 李四先生-动力节点 2017-01-22 10:20:10 由zhangsan
						// {"success":false}
						if (json.success) {
							// 隐藏modal
							$("#editRemarkModal").modal("hide");
							// 更新前端div
							$("#_" + id).text(json.clueRemark.description);
							$("#s_" + id).text(
									json.clueRemark.editTime + " 由"
											+ json.clueRemark.editBy);
						} else {
							alert("更新备注失败！");
						}
					});
				}
			});

	// 更新
	$("#updateBtn").click(function() {
		/*
		 * if(表单验证){
		 *  }
		 */
		var clueId = $("#h-clueId").val();
		$.ajax({
			type : "post",
			url : "workbench/clue/update.do",
			data : {
				"id" : id,
				"pid_user" : $("#edit-clueOwner").val(),
				"company" : $.trim($("#edit-company").val()),
				"calls" : $.trim($("#edit-calls").val()),
				"surname" : $.trim($("#edit-surname").val()),
				"job" : $.trim($("#edit-job").val()),
				"email" : $.trim($("#edit-email").val()),
				"phone" : $("#edit-phone").val(),
				"tel" : $("#edit-tel").val(),
				"website" : $.trim($("#edit-website").val()),
				"state" : $.trim($("#edit-state").val()),
				"source" : $.trim($("#edit-source").val()),
				"description" : $.trim($("#edit-description").val()),
				"contactSummary" : $.trim($("#edit-contactSummary").val()),
				"nextContactTime" : $("#edit-nextContactTime").val(),
				"address" : $.trim($("#edit-address").val())
			},
			cache : false,
			success : function(json) {
				if (json.success) {
					$("#editClueModal").modal("hide");
					window.location.href = "workbench/clue/detail.do?id=" + id;
					$("#editForm")[0].reset();
				} else {
					alert("更新失败");
				}
			}
		});
	});
	// 删除线索
	$("#delBtn").click(function() {
		if (confirm("删除这条线索？")) {
			$.ajax({
				type : "post",
				url : "workbench/clue/delete.do",
				cache : false,
				data : {
					"id" : $("#h-clueId").val()
				},
				success : function(json) {
					if (json.success) {
						window.location.href = "workbench/clue/index.jsp";
					} else {
						alert("删除失败");
					}
				}
			});
		}
	});

	// 关联市场活动按钮
	$("#assoiciateFlag").click(function() {
		$("#queryTbody").empty();
		$("#page").empty();
		$("#bundModal").modal("show");
	});

	// 获取关联市场活动
	getAssociateActs();

	// 全选未关联活动
	$("#qx").click(function() {
		$("input[name='fx']").prop("checked", this.checked);
	});
	$("#activityTable").on(
			"click",
			"input[name='fx']",
			function() {
				$("#qx").prop(
						"checked",
						$("input[name='fx']:checked").size() == $(
								"input[name='fx']").size());
			});

	// 查询未关联的市场活动
	$("#queryAct").keydown(function(event) {
		if (event.keyCode == 13) {
			queryActByName(1, 10);
			return false;// 事件冒泡/事件传递--关闭它
		}
	});

	// 解除关联
	$("#disRelationActBtn").click(function() {
		$.post("workbench/clue/delAssociateAct.do", {
			"id" : $("#relationId").val()
		}, function(json) {
			if (json.success) {
				$("#unbundModal").modal("hide");
				getAssociateActs();
			} else {
				alert("解除关联失败");
			}
		})
	});

});

// 编辑备注
function editRemark(id) {
	$("#editRemarkId").val(id);
	$("#editRemark-description").val($("#_" + id).text());
	$("#editRemarkModal").modal("show");

}
// 删除备注
function delRemark(id) {
	if (confirm("确认删除备注？")) {
		$.get("workbench/clue/delRemark.do", {
			"id" : id
		}, function(json) {
			if (json.success) {
				$("#" + id).remove();
			} else {
				alert("删除失败");
			}
		});
	}
};
// 解除关联链接
function disAssociateAct(id) {
	$("#relationId").val(id);
	$("#unbundModal").modal("show");
}

// 获取市场活动列表
function getAssociateActs() {
	$
			.ajax({
				type : "get",
				url : "workbench/clue/getAssociateActs.do",
				data : {
					"id" : $("#h-clueId").val()
				},
				cache : false,
				success : function(json) {
					var html = "";
					$(json)
							.each(
									function() {
										html += '<tr id=' + this.relationId
												+ '>';
										html += '<td>' + this.name + '</td>';
										html += '<td>' + this.start_date
												+ '</td>';
										html += '<td>' + this.end_date
												+ '</td>';
										html += '<td>' + this.owner + '</td>';
										html += '<td><a href="javascript:void(0);" onclick="disAssociateAct(\''
												+ this.relationId
												+ '\')" style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>解除关联</a></td>';
										html += '</tr>';
									});
					$("#actTbody").html(html);
				}
			});
};

// 通过name查询市场关联sadasdsaasd
function queryActByName(pageNo, pageSize) {
	$.ajax({
		type : "get",
		url : "workbench/clue/queryAssociateAct.do",
		cache : false,
		data : {
			"clueId" : $("#h-clueId").val(),
			"name" : $.trim($("#queryAct").val()),
			"pageNo" : pageNo,
			"pageSize" : pageSize
		},
		success : function(data) {
			var html = "";
			$(data.dataList).each(
					function() {
						html += '<tr>';
						html += '<td><input type="checkbox" name="fx" value='
								+ this.id + '></td>';
						html += '<td>' + this.name + '</td>';
						html += '<td>' + this.start_date + '</td>';
						html += '<td>' + this.end_date + '</td>';
						html += '<td>' + this.owner + '</td>';
						html += '</tr>';
					});
			$("#queryTbody").html(html);
			var totalPages = Math.ceil(data.total / pageSize);
			$("#page").bs_pagination({
				currentPage : pageNo, // 页码
				rowsPerPage : pageSize, // 每页显示的记录条数
				maxRowsPerPage : 10, // 每页最多显示的记录条数
				totalPages : totalPages, // 总页数//Math.ceil(getTotalCount()/getPageSize());
				totalRows : data.total, // 总记录条数

				visiblePageLinks : 5, // 显示几个卡片

				showGoToPage : true,
				showRowsPerPage : true,
				showRowsInfo : true,
				showRowsDefaultInfo : true,

				onChangePage : function(event, data) {
					queryActByName(data.currentPage, data.rowsPerPage);
				}
			});
		},
	});
};