<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">
<title>ECharts</title>
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/echarts/echarts.min.js"></script>
<script type="text/javascript">
	$(function(){
		$.get(
			"workbench/chart/activity",
			function(json){
				//[{"name":"","value":""}]
		        // 指定图表的配置项和数据
		        var names = [];
		        var values = [];
		        $(json).each(function(){
					names.push(this.name)
					values.push(this.value)
		        });
		        var myChart2 = echarts.init($("#clue-chart2")[0]);
		        var option2 = {
		        		 title : {
		        		        text: '市场活动来源',
		        		        subtext: '统计图表',
		        		        x:'center'
		        		    },
		        		    tooltip : {
		        		        trigger: 'item',
		        		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		        		    },
		        		    legend: {
		        		        orient: 'vertical',
		        		        left: 'left',
		        		        data: names
		        		    },
		        		    series : [
		        		        {
		        		            name: '活动所有者',
		        		            type: 'pie',
		        		            radius : '55%',
		        		            center: ['50%', '60%'],
		        		            data:json,
		        		            itemStyle: {
		        		                emphasis: {
		        		                    shadowBlur: 10,
		        		                    shadowOffsetX: 0,
		        		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		        		                }
		        		            }
		        		        }
		        		    ]
		        	};
		        myChart2.setOption(option2);
			}
		)
	})
</script>
</head>
<body>
    <div id="clue-chart2" style="width: 800px;height:600px;"></div>
</body>
</html>