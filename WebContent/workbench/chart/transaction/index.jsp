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
			"workbench/chart/trans",
			function(json){
				//[{"name":"","value":""}]
		        // 指定图表的配置项和数据
		         var names = [];
		        var values = [];
		        $(json.dataList2).each(function(){
					names.push(this.name)
					values.push(this.value)
		        });
		        
		        var myChart = echarts.init($("#clue-chart")[0]);
		        var option1 = {
		        		title: {
		        	        text: '销售漏斗图',
		        	        subtext: '交易'
		        	    },
		        	    tooltip: {
		        	        trigger: 'item',
		        	        formatter: "{a} <br/>{b} : {c}%"
		        	    },
		        	    toolbox: {
		        	        feature: {
		        	            dataView: {readOnly: false},
		        	            restore: {},
		        	            saveAsImage: {}
		        	        }
		        	    },
		        	    legend: {
		        	        data: names
		        	    },
		        	    calculable: true,
		        	    series: [
		        	        {
		        	            name:'阶段',
		        	            type:'funnel',
		        	            left: '10%',
		        	            top: 60,
		        	            //x2: 80,
		        	            bottom: 60,
		        	            width: '80%',
		        	            // height: {totalHeight} - y - y2,
		        	            min: 0,
		        	            max: json.total,
		        	            minSize: '0%',
		        	            maxSize: '100%',
		        	            sort: 'descending',
		        	            gap: 2,
		        	            label: {
		        	                normal: {
		        	                    show: true,
		        	                    position: 'inside'
		        	                },
		        	                emphasis: {
		        	                    textStyle: {
		        	                        fontSize: 20
		        	                    }
		        	                }
		        	            },
		        	            labelLine: {
		        	                normal: {
		        	                    length: 10,
		        	                    lineStyle: {
		        	                        width: 1,
		        	                        type: 'solid'
		        	                    }
		        	                }
		        	            },
		        	            itemStyle: {
		        	                normal: {
		        	                    borderColor: '#fff',
		        	                    borderWidth: 1
		        	                }
		        	            },
		        	            data:json.dataList2
		        	        }
		        	    ]
		        };
		        myChart.setOption(option1);
		        
		        var myChart2 = echarts.init($("#clue-chart2")[0]);
		        var option2 = {
		        	    xAxis: {
		        	        type: 'category',
		        	        data: names
		        	    },
		        	    tooltip: {},
		        	    yAxis: {
		        	        type: 'value'
		        	    },
		        	    series: [{
		        	        data: values,
		        	        type: 'bar'
		        	    }]
		        	};
		        myChart2.setOption(option2);
			}
		)
	})
</script>
</head>
<body>
    <div id="clue-chart" style="width: 800px;height:400px;"></div>
    <div id="clue-chart2" style="width: 600px;height:400px;"></div>
    <div id="clue-chart3" style="width: 600px;height:400px;"></div>
    
</body>
</html>