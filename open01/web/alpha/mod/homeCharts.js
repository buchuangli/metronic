function titleTime(num){
	var timeType=storageArr[num].split("|")[2];
	titleProID=storageArr[num].split("|")[3];
	var time=new Date();
	titleEndTime=new Date();
	if(timeType=="day"){
		time.add("d",-1);
	}else if(timeType=="week"){
		time.add("d",-7);
	}else if(timeType=="oneMonth"){
		time.add("m",-1);
	}else if(timeType=="threeMonth"){
		time.add("m",-3);
	}else if(timeType=="sixMonth"){
		time.add("m",-6);
	}else if(timeType=="year"){
		time.add("y",-1);
	}else if(timeType=="twoYear"){
		time.add("y",-2);
	}
	titleEndTime=titleEndTime.Format("yyyy-MM-dd hh:mm")
	titleStartTime=time.Format("yyyy-MM-dd hh:mm");
}
function pvChartViewCheck(e,data,num){
	if(e.indexOf("line")!=(-1)){
		var data=data.timeSlot;
		var lineView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='页面访问总量统计图表';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var option={
    		color:['rgba(86,144,242,0.7)','rgba(99,116,156,0.5)'],
	    	xAxis:{
	    		type:'category',
	    		data:data.map(function(item){
	    			return item.time;
	    		}),
	    		axisLable:{
	    			formatter:function(value,idx){
	    				var date=new Date(value);
	            		return idx === 0 ? value : [date.getMonth() + 1, date.getDate()].join('-');
	    			}
	    		},
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
	    		scale:{power:1,precision:1},
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
	            }
	        }],
	        series:[{
	        	name:"访问次数",
	        	type:'line',
	        	data:data.map(function(item){
	        		return item.pvNum;
	        	}),
	        	lineStyle:{
        		normal:{
        			color:'rgba(86,144,242,0.7)'
        		}
	        	},
	        	areaStyle: {normal: {}},
	        	symbolSize:10,
	        	showSymbol:false
	        }
	        ]
	    };
		lineView.setOption(option);
		addLoadEvent(lineView.resize);
	}
	if(e.indexOf("map")!=(-1)){
		var geoArr=[];
		for(var i=0;i<data.length;i++){
			var geoObj={};
			geoObj.name=data[i].province;
			geoObj.value=data[i].pvNum;
			geoArr.push(geoObj);
		}
		var mapView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='页面访问总量地理视图';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var option={
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
		    silent:true,
		    series: [
		        {
		            name: '访问次数',
		            type: 'map',
		            mapType: 'china',
		            roam: false,
		            data:geoArr
		        }
		    ]
		};
		option.visualMap.max=(function(){
			var maxNum=0;
			for(var i=0;i<geoArr.length;i++){
				if(maxNum<geoArr[i].value){
					maxNum=geoArr[i].value;
				}
			}
			return maxNum;
		})();
		mapView.setOption(option);
		addLoadEvent(mapView.resize);
	};
	if(e.indexOf("vBar")!=(-1)){
		var vBarView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='页面访问排行';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var dataUrlNum=50;
		var urlI=10;
		var option = {
		color:['rgb(86,144,242)'],
		dataZoom: [
	        {
	            type: 'slider',
	            show: false,
	            yAxisIndex: [0],
	            top:'9.7%',
	            left: '93%',
	            start: 80,
	            end: 100,
		        textStyle:{
			        color:"#737373"
		        }
	        }
	    ],
	    silent:true,
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'value',
	        boundaryGap: [0, 0.01],
			axisLine: {
	                lineStyle: {
	                    color: '#737373'
	                }
	            }
	    },
	    yAxis: {
	        type: 'category',
	        name:'页面访问量排名',
	        data: data.map(function(item){
	        		return (dataUrlNum--).toString();
	        	}),
			axisLine: {
	                lineStyle: {
	                    color: '#737373'
	                }
	            }
	},
	    series: [
	        {
	            name: '访问次数',
	            type: 'bar',
	            barMinHeight:20,
	            data: data.map(function(item){
	        		return item.urlNum;
	        	})
	        }
	    ]
	};
		vBarView.setOption(option);
		addLoadEvent(vBarView.resize);
	};
	if(e.indexOf("area")!=(-1)){
		var areaView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='页面访问总量面积图';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var dataArr=[];
		for(var i=0;i<data.length;i++){
			dataArr.push({});
			dataArr[i].name=data[i].url;
			dataArr[i].value=data[i].urlNum;
		}
		var option = {
        	color:["#203d6e","#6d8c8c","#346d9f","#202e3f","#6b83ba","#f9d992","#324e58","#628f9e","#335592","#d1bca8"],
	        silent:true,
	        series : [
	            {
	                name:'页面访问量统计',
	                type:'treemap',
			        roam:false,
	                itemStyle: {
	                    normal: {
	                        label: {
	                            show: true,
	                            formatter: "{b}"
	                        },
	                        borderWidth: 1
	                    }
	                },
	                data:dataArr
	            }
	        ]
	    };
		areaView.setOption(option);
		addLoadEvent(areaView.resize);
	}
}

//首页IP图例
function ipChartViewCheck(e,data,num){
	if(e.indexOf("line")!=(-1)){
		var lineView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='IP总量统计图表';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var option={
    		color:['rgba(86,144,242,0.7)','rgba(99,116,156,0.5)'],
	    	xAxis:{
	    		type:'category',
	    		data:data.map(function(item){
	    			return item.time;
	    		}),
	    		axisLable:{
	    			formatter:function(value,idx){
	    				var date=new Date(value);
	            		return idx === 0 ? value : [date.getMonth() + 1, date.getDate()].join('-');
	    			}
	    		},
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
	    		name:'IP数量',
	    		type:'value',
	    		scale:{power:1,precision:1},
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
	            }
	        }],
	        series:[{
	        	name:"IP数量",
	        	type:'line',
	        	data:data.map(function(item){
	        		return item["ipNum"];
	        	}),
	        	lineStyle:{
        		normal:{
        			color:'rgba(86,144,242,0.7)'
        		}
	        	},
	        	areaStyle: {normal: {}},
            silent:true,
	        	symbolSize:10,
	        	showSymbol:false
	        }
	        ]
	    };
		lineView.setOption(option);
		addLoadEvent(lineView.resize);
	}
	if(e.indexOf("map")!=(-1)){
		var geoArr=[];
		for(var i=0;i<data.length;i++){
			var geoObj={};
			geoObj.name=data[i].province;
			geoObj.value=data[i].pvNum;
			geoArr.push(geoObj);
		}
		var mapView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='IP总量地理视图';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var option={
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
		        show:false,
		        textStyle:{
			        color:"#737373"
		        }
		    },
		    silent:true,
		    series: [
		        {
		            name: '访问次数',
		            type: 'map',
		            mapType: 'china',
		            roam: false,
		            data:geoArr
		        }
		    ]
		};
		option.visualMap.max=(function(){
			var maxNum=0;
			for(var i=0;i<geoArr.length;i++){
				if(maxNum<geoArr[i].value){
					maxNum=geoArr[i].value;
				}
			}
			return maxNum;
		})();
		mapView.setOption(option);
		addLoadEvent(mapView.resize);
	};
	if(e.indexOf("vBar")!=(-1)){
		var vBarView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='IP总量排行';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var dataUrlNum=50;
		var urlI=10;
		var option = {
	    color:['rgb(86,144,242)'],
		dataZoom: [
	        {
	            type: 'slider',
	            show: false,
	            yAxisIndex: [0],
	            top:'9.7%',
	            left: '93%',
	            start: 80,
	            end: 100,
		        textStyle:{
			        color:"#737373"
		        }
	        }
	    ],
	    silent:true,
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'value',
	        boundaryGap: [0, 0.01],
			axisLine: {
	                lineStyle: {
	                    color: '#737373'
	                }
	            }
	    },
	    yAxis: {
	        type: 'category',
	        name:'访问总量IP排名',
	        data: data["urlCount"].map(function(item){
	        		return (dataUrlNum--).toString();
	        	}),
			axisLine: {
	                lineStyle: {
	                    color: '#737373'
	                }
	            }
	},
	    series: [
	        {
	            name: '访问次数',
	            type: 'bar',
	            barMinHeight:20,
	            data: data["urlCount"].map(function(item){
	        		return item.urlNum;
	        	})
	        }
	    ]
	};
		vBarView.setOption(option);
		addLoadEvent(vBarView.resize);
	};
	if(e.indexOf("area")!=(-1)){
		var areaView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='IP总量面积图';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var dataArr=[];
		for(var i=0;i<data.length;i++){
			dataArr.push({});
			dataArr[i].name=data[i].url;
			dataArr[i].value=data[i].urlNum;
		}
		var option = {
	        color:["#203d6e","#6d8c8c","#346d9f","#202e3f","#6b83ba","#f9d992","#324e58","#628f9e","#335592","#d1bca8"],
	        silent:true,
	        series : [
	            {
	                name:'页面访问量统计',
	                type:'treemap',
			        roam:false,
	                itemStyle: {
	                    normal: {
	                        label: {
	                            show: true,
	                            formatter: "{b}"
	                        },
	                        borderWidth: 1
	                    }
	                },
	                data:dataArr
	            }
	        ]
	    };
		areaView.setOption(option);
		addLoadEvent(areaView.resize);
	};
}

//首页data图例
function dataChartViewCheck(e,data,num){
	if(e.indexOf("line")!=(-1)){
		var lineView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='数据传输总量统计图表';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var option={
	    	color:['rgba(86,144,242,0.7)','rgba(99,116,156,0.5)'],
	    	xAxis:{
	    		type:'category',
	    		data:data.map(function(item){
	    			return item.time;
	    		}),
	    		axisLable:{
	    			formatter:function(value,idx){
	    				var date=new Date(value);
	            		return idx === 0 ? value : [date.getMonth() + 1, date.getDate()].join('-');
	    			}
	    		},
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
	    		name:'数据总量',
	    		type:'value',
	    		scale:{power:1,precision:1},
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
	            }
	        }],
	        series:[{
	        	name:"数据总量",
	        	type:'line',
	        	data:data.map(function(item){
	        		return item["bytes"];
	        	}),
	        	lineStyle:{
        		normal:{
        			color:'rgba(86,144,242,0.7)'
        		}
	        	},
	        	areaStyle: {normal: {}},
            silent:true,
	        	symbolSize:10,
	        	showSymbol:false
	        }
	        ]
	    };
		lineView.setOption(option);
		addLoadEvent(lineView.resize);
	};
	if(e.indexOf("map")!=(-1)){
		var geoArr=[];
		for(var i=0;i<data.length;i++){
			var geoObj={};
			geoObj.name=data[i].province;
			geoObj.value=data[i].pvNum;
			geoArr.push(geoObj);
		}
		var mapView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='数据传输总量地理视图';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var option={
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
		        show:false,
		        textStyle:{
			        color:"#737373"
		        }
		    },
		    silent:true,
		    series: [
		        {
		            name: '数据总量',
		            type: 'map',
		            mapType: 'china',
		            roam: false,
		            data:geoArr
		        }
		    ]
		};
		option.visualMap.max=(function(){
			var maxNum=0;
			for(var i=0;i<geoArr.length;i++){
				if(maxNum<geoArr[i].value){
					maxNum=geoArr[i].value;
				};
			}
			return maxNum;
		})();
		mapView.setOption(option);
		addLoadEvent(mapView.resize);
	};
	if(e.indexOf("vBar")!=(-1)){
		var vBarView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='数据传输量排行';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var dataUrlNum=data.lengtg;
		var urlI=10;
		var option = {
	    color:['rgb(86,144,242)'],
		dataZoom: [
	        {
	            type: 'slider',
	            show: false,
	            yAxisIndex: [0],
	            top:'9.7%',
	            left: '93%',
	            start: 80,
	            end: 100,
		        textStyle:{
			        color:"#737373"
		        }
	        }
	    ],
	    silent:true,
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'value',
	        boundaryGap: [0, 0.01],
			axisLine: {
	                lineStyle: {
	                    color: '#737373'
	                }
	            }
	    },
	    yAxis: {
	        type: 'category',
	        name:'数据传输量排名',
	        data: data.map(function(item){
	        		return (dataUrlNum--).toString();
	        	}),
			axisLine: {
	                lineStyle: {
	                    color: '#737373'
	                }
	            }
	},
	    series: [
	        {
	            name: '数据总量',
	            type: 'bar',
	            barMinHeight:20,
	            data: data.map(function(item){
	        		return item.bytes;
	        	})
	        }
	    ]
	};
		vBarView.setOption(option);
		addLoadEvent(vBarView.resize);
	};
	if(e.indexOf("area")!=(-1)){
		var areaView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='数据传输量面积图';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var dataArr=[];
		for(var i=0;i<data.length;i++){
			dataArr.push({});
			dataArr[i].name=data[i].ip;
			dataArr[i].value=data[i].bytes;
		}
		var option = {
	        color:["#203d6e","#6d8c8c","#346d9f","#202e3f","#6b83ba","#f9d992","#324e58","#628f9e","#335592","#d1bca8"],
	        silent:true,
	        series : [
	            {
	                name:'数据传输量统计',
	                type:'treemap',
			        roam:false,
	                itemStyle: {
	                    normal: {
	                        label: {
	                            show: true,
	                            formatter: "{b}"
	                        },
	                        borderWidth: 1
	                    }
	                },
	                data:dataArr
	            }
	        ]
	    };
		areaView.setOption(option);
		addLoadEvent(areaView.resize);
	};
}

//首页status图例
function statusChartViewCheck(e,data,num){
	if(e.indexOf("bar1")!=(-1)){
		var barView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='页面状态统计图表';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var option = {
	        color:['rgb(86,144,242)'],
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    silent:true,
		    xAxis : [
		        {
		            type : 'category',
		            data : data.map(function(item){
		            	return item['status'].toString();
		            }),
		            axisTick: {
		                alignWithLabel: true
		            },
		            axisLine: {
	                lineStyle: {
	                    color: '#737373'
	                }
	            }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            name:'状态码次数',
		            axisLine: {
	                lineStyle: {
	                    color: '#737373'
	                }
	            }
		        },
		    ],
		    series : [
		        {
		            name:'出现次数',
		            type:'bar',
		            barWidth: '60%',
		            data:data.map(function(item){
		            	return item['statusNum'];
		            }),
		        }
		    ]
		};
		barView.setOption(option);
		addLoadEvent(barView.resize);
	};
	if(e.indexOf("bar2")!=(-1)){
		var barView2=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='页面状态时间分布表';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var data=data.allStatusType["200"];
		var option = {
			color:['rgb(86,144,242)'],
		    silent:true,
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : data.map(function(item){
		            	return item['time'];
		            }),
		            axisTick: {
		                alignWithLabel: true
		            },
		            axisLine: {
	                lineStyle: {
	                    color: '#737373'
	                }
	            }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            scale:{power:1,precision:1},
		            axisLine: {
	                lineStyle: {
	                    color: '#737373'
	                }
	            }
		        },
		    ],
		    series : [
		        {
		        	name : '200',
					type : 'line',
					data : data.map(function(item) {
						return item['pvNum'];
					})
		        }
		    ]
		};
		barView2.setOption(option);
		addLoadEvent(barView2.resize);
	};
	if(e.indexOf("vBar1")!=(-1)){
		var data=data["200"];
		var statusListNum=data.length;
		var vBar1View=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='页面状态URL排行';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		option = {
		color:['rgb(86,144,242)'],
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    silent:true,
	    xAxis: {
	        type: 'value',
	        boundaryGap: [0, 0.01],
			axisLine: {
	                lineStyle: {
	                    color: '#737373'
	                }
	            }
	    },
	    yAxis: {
	        type: 'category',
	        name:'URL排名',
	        data: data.map(function(item){
	        		return "URL"+statusListNum--;
	        	}),
			axisLine: {
	                lineStyle: {
	                    color: '#737373'
	                }
	            }
		},
		series : [{
					name : "200",
					type : 'bar',
					data : data.map(function(item) {
						return item.statusNum;
					})
				}]
	};
		vBar1View.setOption(option);
		addLoadEvent(vBar1View.resize);
	};
	if(e.indexOf("vBar2")!=(-1)){
		var testNum=1;
		var data=data["200"];
		var statusListNum=data.length;
		var vBar1View=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='页面状态IP排行';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		option = {
		color:['rgb(86,144,242)'],
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    silent:true,
	    xAxis: {
	        type: 'value',
	        boundaryGap: [0, 0.01],
			axisLine: {
	                lineStyle: {
	                    color: '#737373'
	                }
	            }
	    },
	    yAxis: {
	        type: 'category',
	        name:'IP排名',
	        data: data.map(function(item){
	        		return "IP"+statusListNum--;
	        	}),
			axisLine: {
	                lineStyle: {
	                    color: '#737373'
	                }
	            }
		},
		series : [{
			name : "200",
			type : 'bar',
			data : data.map(function(item) {
				return item.statusNum;
			})
		}]
	};
		vBar1View.setOption(option);
		addLoadEvent(vBar1View.resize);
	};
}

//browser首页图例
function brChartViewCheck(e,data,num){
	if(e.indexOf("vBar")!=(-1)){
		var data=data.timeSlot;
		var vBarView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='访问端浏览器排行';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var dataUrlNum=50;
		var urlI=10;
		var option = {
		    silent:true,
		    color:['rgb(86,144,242)'],
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis: {
		        type: 'value',
		        boundaryGap: [0, 0.01],
				axisLine: {
		                lineStyle: {
		                    color: '#737373'
		                }
		            }
		    },
		    yAxis: {
		        type: 'category',
		        data: data.map(function(item){
		        		return item.browserType;
		        	}),
				axisLine: {
		                lineStyle: {
		                    color: '#737373'
		                }
		            }
		},
		    series: [
		        {
		            name: '访问次数',
		            type: 'bar',
		            data: data.map(function(item){
		        		return item['browserNum'];
		        	})
		        }
		    ]
		};
		vBarView.setOption(option);
		addLoadEvent(vBarView.resize);
	}else if(e.indexOf("area")!=(-1)){
		var areaView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='访问端浏览器面积图';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var dataArr=[];
		for(var i=0;i<data.length;i++){
			dataArr.push({});
			dataArr[i].name=data[i].browserType;
			dataArr[i].value=data[i].browserNum;
		}
		areaView.setOption(option = {
	        color:["#203d6e","#6d8c8c","#346d9f","#202e3f","#6b83ba","#f9d992","#324e58","#628f9e","#335592","#d1bca8"],
		    silent:true,
	        calculable : false,
	        series : [
	            {
	                name:'访问流量统计',
	                type:'treemap',
	                itemStyle: {
	                    normal: {
	                        label: {
	                            show: true,
	                            formatter: "IP"
	                        },
	                        borderWidth: 1
	                    },
	                    emphasis: {
	                        label: {
	                            show: true
	                        }
	                    }
	                },
	                data:dataArr
	            }
	        ]
	   });
		addLoadEvent(areaView.resize);

	}
}

//os首页图例
function osChartViewCheck(e,data,num){
	if(e.indexOf("vBar")!=(-1)){
		//var data=data.timeSlot;
		var vBarView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='访问端操作系统排行';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var dataUrlNum=50;
		var urlI=10;
		var option = {
		    silent:true,
		    color:['rgb(86,144,242)'],
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis: {
		        type: 'value',
		        boundaryGap: [0, 0.01],
				axisLine: {
		                lineStyle: {
		                    color: '#737373'
		                }
		            }
		    },
		    yAxis: {
		        type: 'category',
		        data: data.map(function(item){
		        		return item.osType;
		        	}),
				axisLine: {
		                lineStyle: {
		                    color: '#737373'
		                }
		            }
		},
		    series: [
	        {
	            name: '访问次数',
	            type: 'bar',
	            data: data.map(function(item){
	        		return item['osNum'];
	        	})
	        }
	    ]
		};
		vBarView.setOption(option);
		addLoadEvent(vBarView.resize);
	};
	if(e.indexOf("area")!=(-1)){
		var areaView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='访问端操作系统面积图';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var dataArr=[];
		var data=data["osType"];
		for(var i=0;i<data.length;i++){
			dataArr.push({});
			dataArr[i].name=data[i].osType;
			dataArr[i].value=data[i].osNum;
		}
		areaView.setOption(option = {
	        color:["#203d6e","#6d8c8c","#346d9f","#202e3f","#6b83ba","#f9d992","#324e58","#628f9e","#335592","#d1bca8"],
		    silent:true,
	        calculable : false,
	        series : [
	            {
	                name:'请求类型统计',
	                type:'treemap',
	                itemStyle: {
	                    normal: {
	                        label: {
	                            show: true,
	                            formatter: "IP"
	                        },
	                        borderWidth: 1
	                    },
	                    emphasis: {
	                        label: {
	                            show: true
	                        }
	                    }
	                },
	                data:dataArr
	            }
	        ]
	    });
		addLoadEvent(areaView.resize);

	};
}

//request首页图例
function requestChartViewCheck(e,data,num){
	if(e.indexOf("vBar")!=(-1)){
		var data=data.timeSlot;
		var vBarView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='请求类型排行';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var dataUrlNum=50;
		var urlI=10;
		var option = {
		    silent:true,
		    color:['rgb(86,144,242)'],
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis: {
		        type: 'value',
		        boundaryGap: [0, 0.01],
				axisLine: {
		                lineStyle: {
		                    color: '#737373'
		                }
		            }
		    },
		    yAxis: {
		        type: 'category',
		        data: data.map(function(item){
		        		return item.osType;
		        	}),
				axisLine: {
		                lineStyle: {
		                    color: '#737373'
		                }
		            }
		},
		    series: [
		        {
		            name: '访问次数',
		            type: 'bar',
		            data: data.map(function(item){
	        			return item['requestNum'];
		        	})
		        }
		    ]
		};
		vBarView.setOption(option);
		addLoadEvent(vBarView.resize);
	};
	if(e.indexOf("area")!=(-1)){
		var areaView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='请求类型面积图';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var data=data.timeSlot;
		var dataArr=[];
		for(var i=0;i<data.length;i++){
			dataArr.push({});
			dataArr[i].name=data[i].requestType;
			dataArr[i].value=data[i].requestNum;
		}
		areaView.setOption(option = {
	        color:["#203d6e","#6d8c8c","#346d9f","#202e3f","#6b83ba","#f9d992","#324e58","#628f9e","#335592","#d1bca8"],
		    silent:true,
	        calculable : false,
	        series : [
	            {
	                name:'请求数量统计',
	                type:'treemap',
	                itemStyle: {
	                    normal: {
	                        label: {
	                            show: true,
	                            formatter: "IP"
	                        },
	                        borderWidth: 1
	                    },
	                    emphasis: {
	                        label: {
	                            show: true
	                        }
	                    }
	                },
	                data:dataArr
	            }
	        ]
	   });
		addLoadEvent(areaView.resize);

	};
}

//tp首页图例
function tpChartViewCheck(e,data,num){
	if(e.indexOf("vBar")!=(-1)){
		var data=data[0].timeSlot;
		var rfAllUrl=data.length;
		var vBarView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='页面停留时间排行';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var option = {
		    silent:true,
		    color:['rgb(86,144,242)'],
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis: {
		        type: 'value',
		        boundaryGap: [0, 0.01],
				axisLine: {
		                lineStyle: {
		                    color: '#737373'
		                }
		            }
		    },
		    yAxis: {
		        type: 'category',
		        data: data.map(function(item){
		        		return "URL"+rfAllUrl--;
		        	}),
				axisLine: {
		                lineStyle: {
		                    color: '#737373'
		                }
		            }
			},
		    series: [
		        {
		            name: '访问次数',
		            type: 'bar',
		            barMinHeight:10,
		            data: data.map(function(item){
		        		return item['stayTimeType'];
		        	})
		        }
		    ]
		};
		vBarView.setOption(option);
		addLoadEvent(vBarView.resize);
	};
	if(e.indexOf("area")!=(-1)){
		var areaView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='页面停留时间面积图';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var data=data[0].timeSlot
		var dataArr=[];
		for(var i=0;i<data.length;i++){
			dataArr.push({});
			dataArr[i].name=data[i].url;
			dataArr[i].value=data[i].stayTimeType;
		}
		areaView.setOption(option = {
	        color:["#203d6e","#6d8c8c","#346d9f","#202e3f","#6b83ba","#f9d992","#324e58","#628f9e","#335592","#d1bca8"],
		    silent:true,
	        calculable : false,
	        series : [
	            {
	                name:'访问来源统计',
	                type:'treemap',
	                itemStyle: {
	                    normal: {
	                        label: {
	                            show: true,
	                            formatter: "IP"
	                        },
	                        borderWidth: 1
	                    },
	                    emphasis: {
	                        label: {
	                            show: true
	                        }
	                    }
	                },
	                data:dataArr
	            }
	        ]
	   });
		addLoadEvent(areaView.resize);
	}
}

//rf首页图例
function rfChartViewCheck(e,data,num){
	if(e.indexOf("vBar")!=(-1)){
		var rfAllUrl=10;
		var data=data["indirectType"];
		var vBarView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='访问来源排行';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var option = {
		    silent:true,
		    color:['rgb(86,144,242)'],
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis: {
		        type: 'value',
		        boundaryGap: [0, 0.01],
				axisLine: {
		                lineStyle: {
		                    color: '#737373'
		                }
		            }
		    },
		    yAxis: {
		        type: 'category',
		        data: data.map(function(item){
		        		return "URL"+rfAllUrl--;
		        	}),
				axisLine: {
		                lineStyle: {
		                    color: '#737373'
		                }
		            }
			},
		    series: [
		        {
		            name: '访问次数',
		            type: 'bar',
		            barMinHeight:10,
		            data: data.map(function(item){
		        		return item['urlNum'];
		        	})
		        }
		    ]
		}
		vBarView.setOption(option);
		addLoadEvent(vBarView.resize);
	};
	if(e.indexOf("area")!=(-1)){
		var areaView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='访问来源面积图';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var dataArr=[];
		for(var i=0;i<data.length;i++){
			dataArr.push({});
			dataArr[i].name=data[i].referrerType;
			dataArr[i].value=data[i].referrerNum;
		}
		areaView.setOption(option = {
	        color:["#203d6e","#6d8c8c","#346d9f","#202e3f","#6b83ba","#f9d992","#324e58","#628f9e","#335592","#d1bca8"],
		    silent:true,
	        calculable : false,
	        series : [
	            {
	                name:'访问来源统计',
	                type:'treemap',
	                itemStyle: {
	                    normal: {
	                        label: {
	                            show: true,
	                            formatter: "IP"
	                        },
	                        borderWidth: 1
	                    },
	                    emphasis: {
	                        label: {
	                            show: true
	                        }
	                    }
	                },
	                data:dataArr
	            }
	        ]
	   })
		addLoadEvent(areaView.resize);
	};
}

//rf首页图例
function rfChartViewCheck(e,data,num){
	console.log(data)
	if(e.indexOf("vBar")!=(-1)){
		//var rfAllUrl=10;

		var rfAllUrl=10;
		var data1=data[0];
		var data2=data[1];
		var data=[];
		for(var i=0;i<data2.length-1;i++){
			data.push({});
			data[i].url=data2[i].url;
			data[i].urlNum=data2[i].urlNum;
		}
		data.push({});
		if(data2.length>0){
			data[data2.length-1].url="直接访问";
			data[data2.length-1].urlNum=data1[0].referrerNum;
		}
		var vBarView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='访问来源统计条形图';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var option = {
		    silent:true,
		    color:['rgb(86,144,242)'],
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis: {
		        type: 'value',
		        boundaryGap: [0, 0.01],
				axisLine: {
		                lineStyle: {
		                    color: '#fff'
		                }
		            }
		    },
		    yAxis: {
		        type: 'category',
		        data: data.map(function(item){
		        		return "URL"+rfAllUrl--;
		        	}),
				axisLine: {
		                lineStyle: {
		                    color: '#fff'
		                }
		            }
			},
		    series: [
		        {
		            name: '访问次数',
		            type: 'bar',
		            barMinHeight:10,
		            data: data.map(function(item){
		        		return item['urlNum'];
		        	})
		        }
		    ]
		}
		vBarView.setOption(option);
		addLoadEvent(vBarView.resize);
	};
	if(e.indexOf("area")!=(-1)){
		var areaView=echarts.init(document.getElementsByClassName("viewNum"+num)[0]);
		var title='访问来源统计面积图';
		titleTime(num);
		$(".viewNum"+num).parent().prev().children(".caption").append("<span class='titleProIdSpan'>项目名称："+titleProID+"</span><br /><i class='fa fa-bar-chart-o font-green'></i><span class='chartViewTitleSpan'>"+title+"</span><br /><i class='fa fa-clock-o font-green'></i><span class='chartViewDateSpan'>"+titleStartTime+" - "+titleEndTime+"</span>")
		var data1=data[0];
		var data2=data[1];
		var dataArr=[];
		for(var i=0;i<data2.length-1;i++){
			dataArr.push({});
			dataArr[i].name=data2[i].url;
			dataArr[i].value=data2[i].urlNum;
		}
		dataArr.push({});
		if(data2.length>0){
			dataArr[data2.length-1].name="直接访问";
			dataArr[data2.length-1].vakue=data1[0].referrerNum;
		}
		areaView.setOption(option = {
	        color:["#203d6e","#6d8c8c","#346d9f","#202e3f","#6b83ba","#f9d992","#324e58","#628f9e","#335592","#d1bca8"],
		    silent:true,
	        calculable : false,
	        series : [
	            {
	                name:'访问来源统计',
	                type:'treemap',
	                itemStyle: {
	                    normal: {
	                        label: {
	                            show: true,
	                            formatter: "IP"
	                        },
	                        borderWidth: 1
	                    },
	                    emphasis: {
	                        label: {
	                            show: true
	                        }
	                    }
	                },
	                data:dataArr
	            }
	        ]
	   })
		addLoadEvent(areaView.resize);
	};
}