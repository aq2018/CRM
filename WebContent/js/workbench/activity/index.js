$(function(){
		
		//以下日历插件在FF中存在兼容问题，在IE浏览器中可以正常使用。
		
		$("#q-startTime").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
	        autoclose: true,
	        todayBtn: true,
	        pickerPosition: "bottom-left"
		});
		
		$("#q-endTime").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
	        autoclose: true,
	        todayBtn: true,
	        pickerPosition: "bottom-left"
		});
		
		$("#create-startTime").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
	        autoclose: true,
	        todayBtn: true,
	        pickerPosition: "bottom-left"
		});
		
		$("#edit-startTime").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
	        autoclose: true,
	        todayBtn: true,
	        pickerPosition: "bottom-left"
		});
		$("#create-endTime").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
	        autoclose: true,
	        todayBtn: true,
	        pickerPosition: "bottom-left"
		});
		$("#edit-endTime").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
	        autoclose: true,
	        todayBtn: true,
	        pickerPosition: "bottom-left"
		});
		
		//定制字段
		$("#definedColumns > li").click(function(e) {
			//防止下拉菜单消失
	        e.stopPropagation();
	    });
		
		//全选
		$("#qx").click(function(){
			$("input[name='fx']").prop("checked",this.checked);
		});
		/* $("input[name='fx']").click(function(){
			$("#qx").prop("checked",$("input[name='fx']:checked").size() == $("input[name='fx']").size());
		}); */
		$("#tbody").on("click","input[name='fx']",function(){
			$("#qx").prop("checked",$("input[name='fx']:checked").size() == $("input[name='fx']").size());
		});
		//*******************
		//添加
		$("#addActiveBtn").click(function(){
				
					$("#addForm").submit();
				/*if(){
				}*/
				
		});
		
		//编辑，点击编辑
		$("#editActiveBtn").click(function(){
			$act = $("input[name='fx']:checked");
			if($act.length == 0){
				alert("请选择一条数据修改");
				$(this).removeAttr("data-target");
			}else if($act.length > 1){
				alert("一次请修改一条数据");
				$(this).removeAttr("data-target");
			}else{
				$("#editActivityModal").modal("show");
				getActive($act.val());
			}
		});
		
		//获得编辑内容
		function getActive(id){
			var $id = $("#edit-marketActivityid");
			var $name = $("#edit-marketActivityName");
			var $owner = $("#edit-marketActivityOwner");
			var $startTime = $("#edit-startTime");
			var $endTime = $("#edit-endTime");
			var $cost = $("#edit-cost");
			var $describe = $("#edit-describe");
			$.get(
					"workbench/activity/edit.do",
					{"id":id},
					function(data){
						$id.val(data.id);
						$name.val(data.name);
						$startTime.val(data.start_date);
						$endTime.val(data.end_date);
						$cost.val(data.cost);
						$describe.text(data.description);
					},
					"json"
			);
		}
		
		//编辑部门所有者改变内容也改变
		$("#edit-marketActivityOwner").change(function(){
			$("#edit-marketActivityid").val(this.value);
		})
		
		//通过主键保存部门
		$("#updateActiveBtn").click(function(){
			//$("#editForm #create-code").removeProp("disabled");
			//alert($("#editForm #create-id").val());
			
			$("#editForm").submit();
		});
			
		
		//删除活动
		$("#delActiveBtn").click(function(){
			$fxs = $("input[name='fx']:checked");
			if($fxs.length == 0){
				alert("请选择一条数据删除");
			}else{
				var ids = "";
				$fxs.each(function(){
					ids += "&id="+this.value;
				});
				if(confirm("确定删除？")){	
					$.get(
							"workbench/activity/del.do",
							ids.substr(1),
							function(data){
								if(data.success){
									getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
								}else{
									alert("删除失败");
								}
							}
						)
				}
			}
		});
		
		//模糊查询
		$("#queryBtn").click(function(){
			var owner = $.trim($("#q-owner").val());
			var name = $.trim($("#q-name").val());
			var startTime = $.trim($("#q-startTime").val());
			var endTime = $.trim($("#q-endTime").val());
			
			$("#h-owner").val(owner);
			$("#h-name").val(name);
			$("#h-startTime").val(startTime);
			$("#h-endTime").val(endTime);
			getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
		})
		
		//导出选中
		$("#exportChkBtn").click(function(){
			if($(":checkbox[name='fx']:checked").size() == 0){
				alert("请选择一条数据导出");
			}else {
				var sendData = "";
				$(":checkbox[name='fx']:checked").each(function(){
					sendData += "&id="+this.value;
				})
				sendData = sendData.substr(1);
				window.location = "workbench/activity/exportChk.do?" + sendData;
			}
		});
		
		//导出全部
		$("#exportAllBtn").click(function(){
			window.location = "workbench/activity/exportAll.do";
		});
		
		//导入
		$("#impBtn").click(function(){
			
			$.ajax({
				type : "post",
				url : "workbench/activity/import.do",
				data : new FormData($("#impForm")[0]),
				processData : false,
				contentType : false,
				cache : false,
				success : function(json){
					if(json.success){
						getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
						$("#importActivityModal").modal("hide");
					}else{
						alert("导入失败");
					}
				}
			})
		});
		
		//初始化页面
		getPage(1,10);
		
	});
	
	//查询数据
	function getPage(pageNo,pageSize){
		var owner = $.trim($("#h-owner").val());
		var name = $.trim($("#h-name").val());
		var startTime = $.trim($("#h-startTime").val());
		var endTime = $.trim($("#h-endTime").val());
		
		$("#q-owner").val(owner);
		$("#q-name").val(name);
		$("#q-startTime").val(startTime);
		$("#q-endTime").val(endTime);
		
		$("#tbody").html("");
		$.post(
				"workbench/activity/query.do",
				{
					"pageNo":pageNo,
					"pageSize":pageSize,
					"name":name,"owner":owner,
					"start_date":startTime,
					"end_date":endTime
				},
				function(data){
					$(data.dataList).each(function(){
						$("#tbody").append("<tr class='active'><td><input type='checkbox' name='fx' value='"+this.id+"' /></td><td><a style='text-decoration: none; cursor: pointer;' onclick="+"window.location.href='workbench/activity/detail.do?id="+this.id+"';"+">"
						+this.name+"</a></td><td>"+this.owner+"</td><td>"+(this.start_date == "" ? "-" : this.start_date)+"</td><td>"+(this.end_date == "" ? "-" : this.end_date)+"</td></tr>");
					});
					var totalPages = Math.ceil(data.total / pageSize);
					//$("#total").html("共<b>"+data.total+"</b>条记录");
					$("#tbody tr:even").addClass("active");
					$("#page").bs_pagination({
						currentPage: pageNo, // 页码
						rowsPerPage: pageSize, // 每页显示的记录条数
						maxRowsPerPage: 10, // 每页最多显示的记录条数
						totalPages: totalPages, // 总页数//Math.ceil(getTotalCount()/getPageSize());
						totalRows: data.total, // 总记录条数

						visiblePageLinks: 5, // 显示几个卡片

						showGoToPage: true,
						showRowsPerPage: true,
						showRowsInfo: true,
						showRowsDefaultInfo: true,

						onChangePage : function(event, data){
							getPage(data.currentPage , data.rowsPerPage);
						}
					});
				},
				"json"
			);
	};
