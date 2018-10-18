$(function(){
		$("#qx").click(function(){
			$("input[name='dept']").prop("checked",this.checked);
		});
		$("#tbody").on("click","input[name='dept']",function(){
			$("#qx").prop("checked",$("input[name='dept']:checked").size() == $("input[name='dept']").size());
		});
		// 创建
		// data-toggle="modal" data-target="#createDeptModal"
		$("#createBtn").click(function(){
			$("#addForm")[0].reset();
			$("#createDeptModal").modal("show");
		})
		
		// 保存
		$("#saveDeptBtn").click(function(){
			$("#addForm #create-code").blur();
			var $deptno = $("#addForm #create-code").val();
			var $dname = $("#addForm #create-name").val();
			var $manager = $("#addForm #create-manager").val();
			var $phone = $("#addForm #create-phone").val();
			var $descript = $("#addForm #create-describe").val();
			
			if($("#addNoTip").text() == ""){
				$.post(
					"settings/dept/add.do",
					{"deptno":$deptno,"dname":$dname,"master":$manager,"tel":$phone,"descript":$descript},
					function(json){
						if(json.success){
							// 添加成功,刷新页面
							alert("添加成功");
							$("#createDeptModal").modal("hide");
							getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
						}else{
							//
							alert("啊哦，添加失败了~")
						}
					}
				);
			}
			
		});
		function pgRelaod(){
			window.location = "settings/dept/index.jsp";
		}
		// 验证表单
		$("#addForm #create-code").blur(function(){
			var deptno = $.trim($("#create-code").val());
			var regExp = /^[1-9][0-9]{3}$/;
			if(deptno == "" || deptno == "编号为四位数字，不能为空"){
				$("#addNoTip").text("请输入编号");
			}else if(!regExp.test(deptno)){
				$("#addNoTip").text("请输入四位数字编号");
			}else{
				//
				$("#addNoTip").text("");
				$.ajaxSetup({
					async : false
				});
				$.ajax({
					type : "get",
					url : "settings/dept/checkDeptNo.do",	
					data : {"deptno":deptno},// Uncaught ReferenceError: dada
												// is not defined
					cache : false,
					success : function(json){
						if(json.success){
							$("#addNoTip").text("");
						}else{
							$("#addNoTip").text("已有该编号");
						}
						$.ajaxSetup({
							async : true
						});
					}
					
				});
			}
		});
		$("#addForm #create-code").focus(function(){
			var deptno = $.trim(this.value);
			if(deptno == "" || deptno == "编号为四位数字，不能为空"){
				$(this).val("");
				$("#addNoTip").text("请输入编号");
			}
		});
		$("#addForm #create-name").blur(function(){
			var dname = $.trim(this.value);
			if(dname == ""){
				$("#addnameTip").text("请输入名称");
			}else{
				$("#addnameTip").text("");
			}
		});
		// 获得部门
		$("#editDeptBtn").click(function(){
			$dept = $("input[name='dept']:checked");
			if($dept.length == 0){
				alert("请选择一条数据修改");
			}else if($dept.length > 1){
				alert("一次请修改一条数据");
			}else{
				$("#editDeptModal").modal("show");
				$.post(
						"settings/dept/edit.do",
						{"id":$dept.val()},
						function(data){
							$("#edit-id").val(data.id);
							$("#edit-code").val(data.deptno);
							$("#edit-name").val(data.dname);
							$("#edit-manager").val(data.master);
							$("#edit-phone").val(data.tel);
							$("#edit-describe").text(data.descript);
			
						}
				);
			}
		});
		
		// 更新部门
		$("#updateDeptBtn").click(function(){
			var id = $("#edit-id").val();
			var deptno = $("#edit-code").val();
			var dname = $("#edit-name").val();
			var manager = $("#edit-manager").val();
			var tel = $("#edit-phone").val();
			var descript =	$("#edit-describe").val();
			$.post(// 提交数据
					"settings/dept/update.do",
					{"id":id,"deptno":deptno,"dname":dname,"master":manager,"tel":tel,"descript":descript},
					function(json){
						if(json.success){
							$("#no_" + id).text(deptno);
							$("#n_" + id).text(dname);
							$("#m_" + id).text(manager);
							$("#t_" + id).text(tel);
							$("#d_" + id).text(descript);
							$("#editDeptModal").modal("hide");
						}else{
							alert("更新失败");
						}
					}
			);
		});

		// 删除部门
		$("#delDeptBtn").click(function(){
			$fxs = $("input[name='dept']:checked");
			if($fxs.length == 0){
				alert("请选择一条数据删除");
			}else{
				var delArr = [];
				var sendData = "";
				for(var i = 0;i < $fxs.length;i++){
					sendData += "id="+$($fxs[i]).val();
					delArr.push($($fxs[i]).val());
					if(i < $fxs.length - 1){
						sendData += "&";
					}
				}
				if(confirm("确定删除" + delArr.length + "条数据？")){
					$.post(
						"settings/dept/del.do?" + sendData,
						function(json){
							if(json.success){
								for(var i = 0;i < delArr.length;i++){
									$("#" + delArr[i]).remove();
								}
							}else{
								alert("删除失败")
							}
						}
					) 
				}
			}
		});
		
		getPage(1,10);
	})
	
	function getPage(pageNo,pageSize){
			$.get(
				"settings/dept/getCondition.do",
				{
					"pageNo":pageNo,
					"pageSize":pageSize,
					"_":new Date().getTime()
				},
				function(data){
					var html = "";
					$(data.dataList).each(function(){
						html += '<tr id='+this.id+'>';
						html += '<td><input type="checkbox" name="dept" value='+this.id+'></td>';
						html += '<td id="no_'+this.id+'">'+this.deptno+'</td>';
						html += '<td id="n_'+this.id+'">'+this.dname+'</td>';
						html += '<td id="m_'+this.id+'">'+this.master+'</td>';
						html += '<td id="t_'+this.id+'">'+this.tel+'</td>';
						html += '<td id="d_'+this.id+'">'+this.descript+'</td>';
						html += '</tr>';
					});
					$("#tbody").html(html);					
					$("#tbody tr:even").addClass("active");
					var totalPages = Math.ceil(data.total / pageSize);
					$("#page").bs_pagination({
						currentPage: pageNo,
						rowsPerPage: pageSize,
						maxRowsPerPage: 10,
						totalPages: totalPages,
						totalRows: data.total,
						visiblePageLinks: 5,
						showGoToPage: true,
						showRowsPerPage: true,
						showRowsInfo: true,
						showRowsDefaultInfo: true,
						onChangePage : function(event, data){
							getPage(data.currentPage , data.rowsPerPage);
						}
					});
				}
			);
		};