$(function(){
		$("#create-nextContactTime").datetimepicker({
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
		
		//保存
		$("#saveBtn").click(function(){
			$.ajax({
				type : "post",
				url : "workbench/clue/save.do",
				data : {
					"pid_user" : $("#create-clueOwner").val(),     
					"company" : $.trim($("#create-company").val()),        
					"calls" :  $.trim($("#create-calls").val()),          
					"surname" : $.trim($("#create-surname").val()),        
					"job" : $.trim($("#create-job").val()),
					"email" : $.trim($("#create-email").val()),          
					"tel" : $("#create-tel").val(),   
					"phone" : $("#create-phone").val(),   
					"website" : $.trim($("#create-website").val()),        
					"state" : $.trim($("#create-state").val()),          
					"source" : $.trim($("#create-source").val()),         
					"description" : $.trim($("#create-description").val()),    
					"contactSummary" : $.trim($("#create-contactSummary").val()), 
					"nextContactTime" : $("#create-nextContactTime").val(),
					"address" :  $.trim($("#create-address").val())
				},                           
				cache : false,
				success : function(json){
					if(json.success){
						$("#createClueModal").modal("hide");
						getPage(1,$("#myPage").bs_pagination('getOption','rowsPerPage'));
						//getPage(1,$("#page").bs_pagination('getOption','rowsPerPage'));
					}else{
						alert("保存失败");
					}
				}
			})
		});
		
		//全选
		$("#qx").click(function(){
			$("input[name='fx']").prop("checked",this.checked);
		});
		
		//on 父级元素.on(绑定事件,绑定子元素,回掉函数)
		$("#tbody").on("click","input[name='fx']",function(){
			$("#qx").prop("checked",$("input[name='fx']:checked").size() == $("input[name='fx']").size());
		});
		//*************************
		
		//编辑
		$("#editBtn").click(function(){
			$fx = $("input[name='fx']:checked");
			if($fx.size()== 0){
				alert("请选择一条数据修改");
			}else if($fx.size() > 1){
				alert("一次请修改一条数据");
			}else{
				//获取编内容
				$("#editClueModal").modal("show");
				$.ajax({
					type : "get",
					url : "workbench/clue/edit.do",
					cache : false,
					data : {
						"id" : $fx.val()
					},
					success : function(json){
						$(json).each(function(){
							 $("#edit-id").val(this.id);
							 $("#edit-clueOwner").val(this.pid_user);
							 $("#edit-company").val(this.company);        
							 $("#edit-calls").val(this.calls);          
							 $("#edit-surname").val(this.surname);       
							 $("#edit-job").val(this.job);
							 $("#edit-email").val(this.email);          
							 $("#edit-tel").val(this.tel);   
							 $("#edit-phone").val(this.phone);   
							 $("#edit-website").val(this.website);
							 $("#edit-state").val(this.state);          
							 $("#edit-source").val(this.source);         
							 $("#edit-description").val(this.description);    
							 $("#edit-contactSummary").val(this.contactSummary); 
							 $("#edit-nextContactTime").val(this.nextContactTime);
							 $("#edit-address").val(this.address);
						});
					}
				});
			}
		});
		
		//update
		$("#updateBtn").click(function(){
			/* if(表单验证){
				
			} */
			$.ajax({
				type : "post",
				url : "workbench/clue/update.do",
				data : {
					"id" : $("#edit-id").val(),
					"pid_user" : $("#edit-clueOwner").val(),     
					"company" : $.trim($("#edit-company").val()),        
					"calls" :  $.trim($("#edit-calls").val()),          
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
					"address" :  $.trim($("#edit-address").val())
				},
				cache : false,
				success : function(json){
					if(json.success){
						$("#editClueModal").modal("hide");
						getPage(1,$("#myPage").bs_pagination('getOption','rowsPerPage'));
					}else{
						alert("更新失败");
					}
				}
				
			});
		});
		
		//delBtn
		$("#delBtn").click(function(){
			if($("input[name='fx']:checked").size() == 0){
				alert("请选择一条数据删除");
			}else{
				if(confirm("删除"+$("input[name='fx']:checked").size() +"条数据？")){
					var sendData = "";
					$("input[name='fx']:checked").each(function(){
						sendData += "&id=" + this.value;
					});
					$.ajax({
						type : "post",
						url : "workbench/clue/delete.do",
						cache : false,
						data : sendData,
						success : function(json){
							if(json.success){
								getPage(1,$("#myPage").bs_pagination('getOption','rowsPerPage'));
								$("#qx").prop("checked",false);
							}else{
								alert("删除失败");
							}
						}
					});
				}
			}
		});
		
		//导出全部
		$("#exportAllBtn").click(function(){
			if(confirm("导出全部？")){				
				window.location.href = "workbench/clue/exportAll.do";
			}
		});
		//导出选中
		$("#exportChkBtn").click(function(){
			if($("input[name='fx']:checked").size() == 0){
				alert("请选择至少一条数据导出");
			}else{				
				var sendData = "";
				$("input[name='fx']:checked").each(function(){
					sendData += "&id=" + this.value; 
				});
				window.location.href = "workbench/clue/exportChk.do?" + sendData.substr(1);
			}
		});
		//导入
		$("#importBtn").click(function(){
			$.ajax({
				type : "post",
				url : "workbench/clue/import.do",
				cache : false,
				data : new FormData($("#importForm")[0]),
				processData : false,
				contentType : false,
				success : function(json){
					if(json.success){
						$("#importClueModal").modal("hide");
						getPage(1,$("#myPage").bs_pagination('getOption','rowsPerPage'));
					}else{
						alert("导入失败");
					}
				}
				
			});
		});
		
		//查询
		$("#queryBtn").click(function(){
			//$("#queryForm").submit();
			//var owner = $("#owner").val();
			//getPage(pageNo,pageSize,owner);
			$("#h-company").val($("#q-company").val());
			$("#h-calls").val($("#q-calls").val());
			$("#h-surname").val($("#q-surname").val());
			$("#h-tel").val($("#q-tel").val());
			$("#h-phone").val($("#q-phone").val());
			$("#h-state").val($("#q-state").val());
			$("#h-source").val($("#q-source").val());
			$("#h-owner").val($("#q-owner").val());
			getPage(1,$("#myPage").bs_pagination('getOption','rowsPerPage'));
		});
		
		//初始化加载
		getPage(1,10);
	});
	
	function getPage(pageNo,pageSize){
		$("#q-company").val($("#h-company").val());
		$("#q-calls").val($("#h-calls").val());
		$("#q-surname").val($("#h-surname").val());
		$("#q-tel").val($("#h-tel").val());
		$("#q-phone").val($("#h-phone").val());
		$("#q-state").val($("#h-state").val());
		$("#q-source").val($("#h-source").val());
		$("#q-owner").val($("#h-owner").val());
		$.get(
			"workbench/clue/getByCondition.do",
			{
				"pageNo":pageNo,
				"pageSize":pageSize,
				"company" : $.trim($("#h-company").val()),        
				"calls" :  $.trim($("#h-calls").val()),          
				"surname" : $.trim($("#h-surname").val()),     
				"tel" : $.trim($("#h-tel").val()),   
				"phone" : $.trim($("#h-phone").val()),    
				"state" : $("#h-state").val(),          
				"source" : $("#h-source").val(),       
				"owner" : $.trim($("#h-owner").val()), 
				"_":new Date().getTime()
			},
			function(data){
				//pList:{"id":"1","owner":"张三","company":"bjw","calls":"先生","surname":"李四","job":"矿工","email":"balabala@xx.com","phone":"010-84846003","state":"无业","source":"广告","description":"balabala","contactSummary":"balabala","nextContactTime":"balabala","address":"北京","website":null}
				//"total":6
				var html = "";
				$(data.pList).each(function(){
					html += "<tr>";
					html += "<td><input name='fx' type='checkbox' value='"+this.id+"'/></td>";
					html += "<td><a style='text-decoration: none; cursor: pointer;' onclick='window.location.href=\"workbench/clue/detail.do?id="+this.id+"\";'>"+this.surname+""+this.calls+"</a></td>";
					html += "<td>"+(this.company == "" ? "-" :this.company)+"</td>";
					html += "<td>"+(this.tel == "" ? "-" : this.tel)+"</td>";
					html += "<td>"+(this.phone == "" ? "-" : this.phone)+"</td>";
					html += "<td>"+this.source+"</td>";
					html += "<td>"+this.owner+"</td>";
					html += "<td>"+this.state+"</td>";
					html += "</tr>";
				});
				$("#tbody").html(html);					
				$("#tbody tr:even").addClass("active");
				var totalPages = Math.ceil(data.total / pageSize);
				//var totalPages = 100;
				$("#myPage").bs_pagination({
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