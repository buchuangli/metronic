function tpBtn(){
	moreDateMark=0;
	btnUrl="staytime.open";
	pageName="TP";
	allAjaxCheck=false;
	$('.v-swiper').eq(0).html('<div class="swiper-wrapper"><div class="swiper-slide"><div class="swiperImg">\
        	<div class="swiperB swiperB_full echarts_area treemap">\
        	</div>\
        	<ul class="smChartsImgBox">\
              </ul>\
        	</div>\
        	<div class="txtData">\
        		<div class="countent">\
        		<table border="0" bordercolor="#737373" cellspacing="0" cellpadding="0" class="dataTable">\
        			<col style="width: 10%" />\
					<col style="width: 65%" />\
					<col style="width: 20%" />\
        			<tr>\
        				<th>序号</th>\
        				<th>访问请求路径</th>\
        				<th>访问停留时间(秒)</th>\
        			</tr>\
        		</table>\
        		</div>\
        	</div>\
        </div>\
        <div class="swiper-slide">\
        	<div class="swiperImg">\
        	<div class="swiperB swiperB_full hbarplot">\
        		<div class="echarts_Vbar" draggable="false">\
               </div>\
               <div class="echarts_lBar">\
               </div>\
        	</div>\
        	</div>\
        	<ul class="smChartsImgBox">\
              </ul>\
        	</div>\
        	<div class="txtData">\
        		<div class="countent">\
        		<table border="0" bordercolor="#737373" cellspacing="0" cellpadding="0" class="dataTable">\
        			<col style="width: 10%" />\
					<col style="width: 65%" />\
					<col style="width: 20%" />\
        			<tr>\
        				<th>序号</th>\
        				<th>访问请求路径</th>\
        				<th>访问停留时间(秒)</th>\
        			</tr>\
        		</table>\
        		</div>\
        	</div>\
        </div>\
    </div>');
    var paginationArr=['TOP50 停留时长的访问请求路径','TOP50 停留时长的访问请求路径(占比图)']
	var swiper = new Swiper('.swiper-container', {
		observer : true,
		observeParents:true,
        pagination: '.swiper-pagination',
		paginationClickable: true,
		paginationBulletRender: function (index, className) {
		    return '<span class="' + className+' pagination' + '">' + paginationArr[index] + '</span>';
		}
    });
		swiper.slideTo(skipSwiper);
		skipSwiper=0;
		skipPage="";
		$(".leftBtn li e").css("border-color","transparent");
		$(".leftBtnTit").css("border-color","transparent");
		$("#tpSerch e").css("border-color","#797f94");
		$("#tpSerch").parent().prev().css("border-color","#797f94");
	//swiper点击后更新内容
	
	//AJAX与echarts插件引用
	$.ajax({
		type:"get",
		url:'staytime.open',
		data : {
			project_id : projectID
		},
		async:true,
		success:function(data){
			loadData(data);
			TParea(data);
			TPvBar(data);
			//TPMap(data);
			allAjaxCheck=true;
		},
		error : function() {
			alert('请求失败');
			allAjaxCheck=true;
		}
	})
	//数据详情
	$(".moreCharts>span").click(function() {
		$(this).parent().parent().next().css({
			"transform": "translateY(0)"
		});
		$(this).parent().parent().css({
			"transform": "translateY(-100%)"
		});
	})
	//收起表单
	$(".closeCharts>span").click(function() {
		$(this).parent().parent().css({
			"transform": "translateY(100%)"
		});
		$(this).parent().parent().prev().css({
			"transform": "translateY(0)"
		})
	})
	//调取缩略图生成方法
      smChartsBuild();
      //缩略图点击事件
	$(".smChartsImgBox").delegate(
			".smChartsImg img",
			"click",
			function() {
				if (allAjaxCheck && $(this).attr('src') != 'img/error.png') {
					allAjaxCheck = false;
					showLoading();
					$("li", $(this).parent().parent()).css("border",
							"1px solid rgba(0,0,0,0)");
					$(this).parent().css("border", "1px solid #2E98FF");
					var imgDateArr = $(this).attr("src")
							.replace("NailFIG/", "").replace(".png", "").split(
									"/");
					var projectID = imgDateArr[4];
					var figType = imgDateArr[6];
					var interval = imgDateArr[7];
					var imgStartTime = imgDateArr[8].replace(/-/g, "");
					console.log(projectID + "|" + figType + "|" + interval
							+ "|" + imgStartTime)
					$.ajax({
						type : "post",
						url : btnUrl + '?cmd=WEL:GETCHARINFO',
						data : {
							project_id : projectID,
							chartType : figType,
							dateType : interval,
							date : imgStartTime
						},
						async : true,
						success : function(data) {
							TParea(data);
							TPvBar(data);
							// TPMap(data);
							allAjaxCheck = true;
							hideLoading();
						},
						error : function() {
							alert('请求失败');
							allAjaxCheck = true;
						}
					})
				}
			})
}
// TP矩形树图（面积图）
function TParea(data){
	areaChart=echarts.init(document.getElementsByClassName("echarts_area")[0]);
	var allNum=data.allNum;
	var data=data.timeSlot;
	var dataArr=[];
	for(var i=0;i<data.length;i++){
		dataArr.push({});
		dataArr[i].name=data[i].url;
		dataArr[i].value=data[i].stayTimeType;
	}
	areaChart.setOption(option = {
        title : {
            text: '页面停留时间面积图',
            show:false,
            subtext: 'areaDemo',
            left: 'center',
	        textStyle:{
		        color:"#737373"
	        }
        },
        tooltip : {
            trigger: 'item',
            formatter: function(params){
            	return "URL:"+dataArr[params.dataIndex-1].name+"</br>页面停留时间:"+dataArr[params.dataIndex-1].value+"</br>占比:"+((ForDight(dataArr[params.dataIndex-1].value*100/allNum,2)))+"%";
            }
        },
				color:["#c7c8cd","#b7bed6","#94a0b2","#7188a6","#bfcad1","#859faa","#525e64","#536c7b","#767575"],
        toolbox: {
	        show:true,
	    	right:"15%",
	    	iconStyle:{
	    		normal:{
	    			borderColor:'#737373'
	    		},
	    		emphasis:{
	    			borderColor:'#3BFFFF'
	    		}
	    	},
	        feature: {
	            restore: {},
	            saveAsImage: {}
	        }
	    },
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
                        borderWidth: 1,
                        borderColor:"#e7e7e7"
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
	addLoadEvent(areaChart.resize);
	//根据数据量循环创建tr、td
	$("tr",$(".dataTable").eq(0)).not($("tr:eq(0)",$(".dataTable").eq(0))).remove()
	var i=data.length-1
	while(i>0||i==0){
		var tr=$("<tr><td>"+(data.length-i)+"</td><td style='overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'>"+data[i].url+"</td><td>"+data[i].stayTimeType+"</td></tr>");
		$(".dataTable").eq(0).append(tr);
		i-=1;
	}
}
//TP横向条形图
function TPvBar(data){
	var data=data.timeSlot;
	vBarChart=echarts.init(document.getElementsByClassName("echarts_Vbar")[0]);
	var option = {
    title: {
	        text: '页面停留时间统计图',
	        show:false,
	        subtext: 'BAR DEMO',
	        left: 'center',
	        textStyle:{
		        color:"#737373"
	        }
	    },
	color:['#87abdd'],
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    legend:{
    		data:[{
    			name:"访问次数",
    			icon:'roundRect'
    		}],
    		left:'10%',
    		top:'3%',
    		textStyle:{
    			color:'#737373'
    		}
    	},
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
        name:'页面停留时间排名',
        data: data.map(function(item){
        		return "URL";
        	}),
		axisLine: {
                lineStyle: {
                    color: '#737373'
                }
            }
},
    series: [
        {
            name: '停留时间',
            type: 'bar',
            data: data.map(function(item){
        		return item['stayTimeType'];
        	})
        }
    ]
}
	vBarChart.setOption(option)
	addLoadEvent(vBarChart.resize);
	//根据数据量循环创建tr、td
	$("tr",$(".dataTable").eq(1)).not($("tr:eq(0)",$(".dataTable").eq(1))).remove()
	var i=data.length-1
	while(i>0||i==0){
		var tr=$("<tr><td>"+(data.length-i)+"</td><td style='overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'>"+data[i].url+"</td><td>"+data[i].stayTimeType+"</td></tr>");
		$(".dataTable").eq(1).append(tr);
		i-=1;
	}
}
//TP地图
function TPMap(data){
	mapChart=echarts.init(document.getElementsByClassName("echarts_map")[0]);
	function randomData() {
	    return Math.round(Math.random()*1000);
	}
	var option={
		title: {
	        text: '页面停留时间地理视图',
	        show:false,
	        subtext: 'MAP DEMO',
	        left: 'center',
	        textStyle:{
		        color:"#737373"
	        }
	    },
			color:['#0080b5','#5780b8','#00a3e0'],
	    tooltip: {
	        trigger: 'item'
	    },
	    visualMap: {
	        min: 0,
	        max: 2500,
	        left: '5%',
	        top: 'bottom',
	        calculable: true,
	        inRange: {
	            color: ['#dff6ff', '#0080b5']
	        },
	        textStyle:{
		        color:"#737373"
	        }
	    },
	    toolbox: {
	        show:true,
	    	right:"5%",
	    	iconStyle:{
	    		normal:{
	    			borderColor:'#737373'
	    		},
	    		emphasis:{
	    			borderColor:'#b2e4f6'
	    		}
	    	},
	        orient: 'vertical',
	        top: 'center',
	        feature: {
	            restore: {},
	            saveAsImage: {}
	        }
	    },
	    series: [
	        {
	            name: '数据流量',
	            type: 'map',
	            mapType: 'china',
	            roam: false,
	            label: {
	                normal: {
	                    show: true,
	                    textStyle:{
	                    	color:'#999'
	                    }
	                },
	                emphasis: {
	                    show: true,
	                    textStyle:{
	                    	color:'#000'
	                    }
	                }
	            },
	            itemStyle: {
	            	normal: {
		                areaColor: '#fff',
		                borderColor: '#111'
		            },
	                emphasis: {
	                	areaColor: '#b2e4f6',
	                    borderColor: '#737373',
	                    borderWidth: 1
	                }
	            },
	            data:[
	                {name: '北京',value: randomData() },
	                {name: '天津',value: randomData() },
	                {name: '上海',value: randomData() },
	                {name: '重庆',value: randomData() },
	                {name: '河北',value: randomData() },
	                {name: '河南',value: randomData() },
	                {name: '云南',value: randomData() },
	                {name: '辽宁',value: randomData() },
	                {name: '黑龙江',value: randomData() },
	                {name: '湖南',value: randomData() },
	                {name: '安徽',value: randomData() },
	                {name: '山东',value: randomData() },
	                {name: '新疆',value: randomData() },
	                {name: '江苏',value: randomData() },
	                {name: '浙江',value: randomData() },
	                {name: '江西',value: randomData() },
	                {name: '湖北',value: randomData() },
	                {name: '广西',value: randomData() },
	                {name: '甘肃',value: randomData() },
	                {name: '山西',value: randomData() },
	                {name: '内蒙古',value: randomData() },
	                {name: '陕西',value: randomData() },
	                {name: '吉林',value: randomData() },
	                {name: '福建',value: randomData() },
	                {name: '贵州',value: randomData() },
	                {name: '广东',value: randomData() },
	                {name: '青海',value: randomData() },
	                {name: '西藏',value: randomData() },
	                {name: '四川',value: randomData() },
	                {name: '宁夏',value: randomData() },
	                {name: '海南',value: randomData() },
	                {name: '台湾',value: randomData() },
	                {name: '香港',value: randomData() },
	                {name: '澳门',value: randomData() }
	            ]
	        }
	    ]
	}
	// option.visualMap.max=(function(){
	// 	var maxNum=0;
	// 	if(params.name=="访问次数"){
	// 		option.visualMap.inRange.color=['#dff6ff', '#0080b5'];
	// 		for(var i=0;i<pvList.length;i++){
	// 			if(maxNum<pvList[i].value){
	// 				maxNum=pvList[i].value;
	// 			};
	// 		}
	// 	}
	// 	}
	mapChart.setOption(option)
	addLoadEvent(mapChart.resize);
};