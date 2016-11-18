function reqBtn(){
	moreDateMark=0;
	allAjaxCheck=false;
	btnUrl="requestType.open";
	pageName="REQUEST";
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
        				<th>页面请求类型</th>\
        				<th>访问请求量(次)</th>\
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
        	<ul class="smChartsImgBox">\
              </ul>\
        	</div>\
        </div>\
				<div class="txtData">\
				<div class="countent">\
				<table border="0" bordercolor="#737373" cellspacing="0" cellpadding="0" class="dataTable">\
				<col style="width: 10%" />\
				<col style="width: 65%" />\
				<col style="width: 20%" />\
				<tr>\
				<th>序号</th>\
				<th>页面请求类型</th>\
				<th>访问请求量(次)</th>\
				</tr>\
				</table>\
				</div>\
				</div>\
    </div>');
    var paginationArr=['TOP50 访问请求类型','TOP50 访问请求类型(占比图)']
		var swiper = new Swiper('.swiper-container', {
			observer : true,
			observeParents:true,
	        pagination: '.swiper-pagination',
			paginationClickable: true,
			paginationBulletRender: function (index, className) {
			    return '<span class="' + className+' pagination' + '">' + paginationArr[index] + '</span>';
			}
	    });
    console.log(skipSwiper);
	swiper.slideTo(skipSwiper);
	skipSwiper=0;
	skipPage="";
	$(".leftBtn li e").css("border-color","transparent");
	$(".leftBtnTit").css("border-color","transparent");
	$("#gpSerch e").css("border-color","#797f94");
	$("#gpSerch").parent().prev().css("border-color","#797f94");

	//swiper点击后更新内容
	
	//AJAX与echarts插件引用
	$.ajax({
		type:"get",
		url:'requestType.open',
		data : {
			project_id : projectID
		},
	//	url:'data/requestType.json',
		async:true,
		success:function(data){
			loadData(data);
			REQarea(data);
			REQvBar(data);
			//REQMap(data);
			allAjaxCheck=true;
		},
		error : function() {
			alert('请求失败');
			allAjaxCheck=true;
		}
	})
	//查看地理视图
	$(".moreCharts>span").click(function() {
		$(this).parent().parent().next().css({
			"transform": "translateY(0)"
		});
		$(this).parent().parent().css({
			"transform": "translateY(-100%)"
		});
	})
	//收起地理视图
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
							REQarea(data);
							REQvBar(data);
							// REQMap(data);
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
// REQ矩形树图（面积图）
function REQarea(data){
	areaChart=echarts.init(document.getElementsByClassName("echarts_area")[0]);
	var data=data.timeSlot;
	var dataArr=[];
	for(var i=0;i<data.length;i++){
		dataArr.push({});
		dataArr[i].name=data[i].requestType;
		dataArr[i].value=data[i].requestNum;
	}
	areaChart.setOption(option = {
        title : {
            text: '请求类型面积图',
            show:false,
            subtext: 'areaDemo',
            left: 'center',
	        textStyle:{
		        color:"#737373"
	        }
        },
				color:["#c7c8cd","#b7bed6","#94a0b2","#7188a6","#bfcad1","#859faa","#525e64","#536c7b","#767575"],
        tooltip : {
            trigger: 'item',
            formatter: function(params){
            	return "请求类型:"+dataArr[params.dataIndex-1].name+"</br>访问次数:"+dataArr[params.dataIndex-1].value+"</br>";
            }
        },
        toolbox: {
	        show:true,
	    	right:"15%",
	    	iconStyle:{
	    		normal:{
	    			borderColor:'#737373'
	    		},
	    		emphasis:{
	    			borderColor:'#737373'
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
                name:'请求类型统计',
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
		var tr=$("<tr><td>"+(data.length-i)+"</td><td style='overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'>"+data[i].requestType+"</td><td>"+data[i].requestNum+"</td></tr>");
		$(".dataTable").eq(0).append(tr);
		i-=1;
	}
}
//BR横向条形图
function REQvBar(data){
	var data=data.timeSlot;
	vBarChart=echarts.init(document.getElementsByClassName("echarts_Vbar")[0]);
	var option = {
    title: {
	        text: '访问系统条形统计图',
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
        name:'请求类型',
        data: data.map(function(item){
        		return item.requestType;
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
}
	vBarChart.setOption(option)
	addLoadEvent(vBarChart.resize);
	//根据数据量循环创建tr、td
	$("tr",$(".dataTable").eq(1)).not($("tr:eq(0)",$(".dataTable").eq(1))).remove()
	var i=data.length-1
	while(i>0||i==0){
		var tr=$("<tr><td>"+(data.length-i)+"</td><td style='overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'>"+data[i].requestType+"</td><td>"+data[i].requestNum+"</td></tr>");
		$(".dataTable").eq(1).append(tr);
		i-=1;
	}
}
//BR地图
function REQMap(data){
	mapChart=echarts.init(document.getElementsByClassName("echarts_map")[0]);
	function randomData() {
	    return Math.round(Math.random()*1000);
	}
	var option={
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
	    			borderColor:'#737373'
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
	            name: '请求次数',
	            type: 'map',
	            mapType: 'china',
	            roam: false,
	            label: {
	                normal: {
	                    show: false,
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
	mapChart.setOption(option)
	addLoadEvent(mapChart.resize);
};