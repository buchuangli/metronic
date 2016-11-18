//$.ajax({
//	url: "data/data.json",
//	type: "get",
//	success: function(res) {
		/*$(function() {
			var data = {
				percent:["49.11","49.03","49.11","49.04","48.96","49.04","49.12","49.04","49.12","49.20","49.28",
		    		"49.21","49.13","49.05","49.13","49.21","49.29","49.37","49.29","49.22","49.30","49.22","49.30",
		    		"49.38","49.46","49.38","49.30","49.38","49.46","49.38","49.31","49.38","49.46","49.39","49.31",
		    		"49.24","49.31","49.24","49.16","49.09","49.01","48.94","48.87","48.79","48.87","48.80","48.87",
		    		"48.95","49.03","48.95","49.03","49.10","49.18","49.11","49.03","49.11","49.19","49.26","49.19",
		    		"49.26","49.34","49.41","49.49","49.41","49.34","49.27","49.20","49.13","49.20","49.27","49.20",
		    		"49.28","49.35","49.28","49.35","49.42","49.50","49.43","49.50","49.43","49.36","49.29","49.36",
		    		"49.43","49.50","49.57","49.65","49.58","49.65","49.58","49.65","49.58","49.65","49.72","49.79",
		    		"49.86","49.93","50.00","49.93","50.00"],
    			order:[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,
	    			34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,
	    			67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,]
			}
			echartsInit1(data);
		})*/
//	}
//})
function echartsInit1 (data,select1){
		var _chart1=echarts.init(select1);
		var option={
			title:{
	    		text:'页面访问总量统计图表',
	    		left:'center',
	    		textStyle:{
	    			color:'#737373'
	    		}
	    	},
			tooltip: {
				trigger: 'axis',
				axisPointer: { // 坐标轴指示器，坐标轴触发有效
					type: 'line' // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			dataZoom: [
	            {
	                type: 'inside',
	                start: 0,
	                end: 100,
		            throttle:0
	            },
		    ],
	    	color:['#5fb7e5','#11ffe5'],
	    	xAxis:{
	    		type:'category',
	    		data:data.order,
	    		axisLine: {
	                lineStyle: {
	                    color: '#737373'
	                }
	            },
	    		solitLine:{
	    			show:false
	    		},
	    		boundaryGap:false
	    	},
	    	yAxis: [{
	    		name:'访问次数',
	    		type:'value',
	            axisLabel: {
	                formatter: "{value}"
	            },
	            axisLine: {
	                lineStyle: {
	                    color: '#737373'
	                }
	            },
	            splitNumber: 3,
	            splitLine: {
	                show: false
	            },
				scale:true
	        }],
	        series:[{
	        	name:"访问次数",
	        	type:'line',
	        	data:data.percent,
	        	lineStyle:{
	        		normal:{
	        			color:'#5fb7e5'
	        		}
	        	},
	        	areaStyle: {normal: {}},
	        	symbolSize:10,
	        	showSymbol:false
	        }]
	    }
		_chart1.setOption(option);
		window.onresize = _chart1.resize;
}
function echartsInit2 (data,select2){
		var _chart2=echarts.init(select2);
		var option={
			title: {
		        text: 'PV地理视图',
		        subtext: 'MAP DEMO',
		        left: 'center',
		        textStyle:{
			        color:"#737373"
		        }
		    },
		    color:['#5fb7e5','#11ffe5','#ffa800'],
		    visualMap: {
		    	show:false,
		        min: 0,
		        max: 2500,
		        left: '5%',
		        top: 'bottom',
		        calculable: true,
		        inRange: {
		            color: ['#fff', '#0075f8']
		        },
		        textStyle:{
			        color:"#737373"
		        }
		    },
		    series: [
		        {
		            name: '访问次数',
		            type: 'map',
		            mapType: 'china',
		            roam: false,
		            data:data.city
		        }
		    ]
		}
		_chart2.setOption(option);
}
