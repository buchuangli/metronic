function osBtn(){
	moreDateMark=0;
	dataTableObjList=[];
	allAjaxCheck=false;
	btnUrl="os.open";
	pageName="OS";
	$('.v-swiper').eq(0).html('<div class="swiper-wrapper">\
					<div class="swiper-slide"><div class="swiperImg col-md-12">\
        	<div class="swiperB swiperB_full echarts_area treemap">\
        	</div>\
        	<ul class="smChartsImgBox row">\
              </ul>\
        	</div>\
        	<div class="txtData col-md-12">\
        		<div class="countent">\
						<div class="portlet-title"><div class="page-toolbar dataTextBtn">\
						<button class="dt-button buttons-print btn dark btn-outline">Print</button>\
						<button class="dt-button buttons-pdf buttons-html5 btn green btn-outline">PDF</button>\
						<button class="dt-button buttons-csv buttons-html5 btn purple btn-outline">CSV</button>\
						</div></div>\
        		<table border="0" bordercolor="#737373" cellspacing="0" cellpadding="0" class="dataTable">\
						<thead>\
        			<tr>\
        				<th class="sorting">序号</th>\
        				<th class="sorting">系统类型</th>\
        				<th class="sorting">访问次数</th>\
        			</tr>\
						</thead>\
			<tbody></tbody>\
        		</table>\
        		</div>\
        	</div>\
        </div>\
        <div class="swiper-slide">\
        	<div class="swiperImg col-md-12">\
        	<div class="swiperB swiperB_full hbarplot">\
					<div class="echarts_Vbar harfCharts" draggable="false">\
					</div>\
        	</div>\
        	<ul class="smChartsImgBox row">\
              </ul>\
        	</div>\
        	<div class="txtData col-md-12">\
        		<div class="countent">\
						<div class="portlet-title"><div class="page-toolbar dataTextBtn">\
						<button class="dt-button buttons-print btn dark btn-outline">Print</button>\
						<button class="dt-button buttons-pdf buttons-html5 btn green btn-outline">PDF</button>\
						<button class="dt-button buttons-csv buttons-html5 btn purple btn-outline">CSV</button>\
						</div></div>\
						<table border="0" bordercolor="#737373" cellspacing="0" cellpadding="0" class="dataTable">\
						<thead>\
						 <tr>\
							<th class="sorting">序号</th>\
							<th class="sorting">地理位置</th>\
							<th class="sorting">页面访问量（次）</th>\
						 </tr>\
						 </thead>\
			<tbody></tbody>\
						</table>\
        		</div>\
        	</div>\
        </div>\
    </div>');
$(".general").html('<div class="swiperT row">\
										 <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">\
												 <div class="dashboard-stat2 bordered">\
														 <div class="display"><div class="number">\
																		 <h3 class="font-green-sharp">\
																				 <span class="counterSpan" data-counter="counterup" data-value="9999"></span>\
																				 <small class="font-green-sharp">$</small>\
																		 </h3>\
																		 <small>TOTAL PROFIT</small>\
																 </div>\
																 <div class="icon">\
																		 <i class="icon-pie-chart"></i>\
																 </div>\
														 </div>\
												 </div>\
										 </div>\
										 <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">\
												 <div class="dashboard-stat2 bordered">\
														 <div class="display">\
																 <div class="number">\
																		 <h3 class="font-red-haze">\
																				 <span class="counterSpan" data-counter="counterup" data-value="9999"></span>\
																		 </h3>\
																		 <small>NEW FEEDBACKS</small>\
																 </div>\
																 <div class="icon">\
																		 <i class="icon-like"></i>\
																 </div>\
														 </div>\
												 </div>\
										 </div>\
										 <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">\
												 <div class="dashboard-stat2 bordered">\
														 <div class="display">\
																 <div class="number">\
																		 <h3 class="font-blue-sharp">\
																				 <span class="counterSpan" data-counter="counterup" data-value="9999"></span>\
																		 </h3>\
																		 <small>NEW ORDERS</small>\
																 </div>\
																 <div class="icon">\
																		 <i class="icon-basket"></i>\
																 </div>\
														 </div>\
												 </div>\
										 </div>\
										 <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">\
												 <div class="dashboard-stat2 bordered">\
														 <div class="display">\
																 <div class="number">\
																		 <h3 class="font-purple-soft">\
																				 <span class="counterSpan" data-counter="counterup" data-value="9999"></span>\
																		 </h3>\
																		 <small>NEW USERS</small>\
																 </div>\
																 <div class="icon">\
																		 <i class="icon-user"></i>\
																 </div>\
														 </div>\
												 </div>\
									 </div>\
</div>')
//counterup
$(".counterSpan").attr("data-value","99999")
$(".counterSpan").counterUp({
	delay: 10, // the delay time in ms
	time: 1000 // the speed time in ms
});
    var paginationArr=['操作系统访问量统计面积图','操作系统访问量条形统计图']
	var swiper = new Swiper('.swiper-container', {
		observer : true,
		observeParents:true,
        pagination: '.swiper-pagination',
		paginationClickable: true,
		paginationBulletRender: function (index, className) {
		    return '<span class="col-md-3 col-xs-3 ' + className+' swiperContainer' + '">' + paginationArr[index] + '</span>';
		}
    });
	swiper.slideTo(skipSwiper);
	skipSwiper=0;
	skipPage="";
	//AJAX与echarts插件引用
	$.ajax({
		type:"get",
		url:btnUrl,
//		url:'data/os.json',
		data : {
			project_id : projectID
		},
		async:true,
		success:function(data){
			loadData(data);
			OSarea(data);
			OSvBar(data);
			// OSMap(data);
			allAjaxCheck=true;
		},
		error : function() {
			alert('请求失败');
			allAjaxCheck=true;
		}
	})
	//调取缩略图生成方法
      smChartsBuild();
      //缩略图点击事件
	 $(".smChartsImgBox").delegate(".smChartsImg img","click",function(){
		 if (allAjaxCheck && $(this).attr('src') != 'img/error.png') {
				allAjaxCheck = false;
				showLoading();
				$("li",$(this).parent().parent()).css("border","1px solid rgba(0,0,0,0)");
				$(this).parent().css("border","1px solid #2E98FF");
				var imgDateArr=$(this).attr("src").replace("NailFIG/","").replace(".png","").split("/");
				var projectID=imgDateArr[4];
				var figType=imgDateArr[6];
				var interval=imgDateArr[7];
				var imgStartTime=imgDateArr[8].replace(/-/g,"");
				console.log(projectID+"|"+figType+"|"+interval+"|"+imgStartTime)
				
				$.ajax({
					type : "post",
					url : btnUrl + '?cmd=WEL:GETCHARINFO',
					data : {
						project_id : projectID,
						chartType : figType,
						dateType : interval,
						date:imgStartTime
					},
					async : true,
					success : function(data) {
						OSarea(data);
						OSvBar(data);
						OSMap(data);
						hideLoading();
						allAjaxCheck = true;
					},
					error : function() {
						alert('请求失败');
						allAjaxCheck = true;
					}
				})
		 }
      })
}
//OS矩形树图（面积图）
function OSarea(data){
	areaChart=echarts.init(document.getElementsByClassName("echarts_area")[0]);
	var data=data.timeSlot;
	var dataArr=[];
	for(var i=0;i<data.length;i++){
		dataArr.push({});
		dataArr[i].name=data[i].osType;
		dataArr[i].value=data[i].osNum;
	}
	areaChart.setOption(option = {
        title : {
            text: '访问系统面积图',
            subtext: 'areaDemo',
            left: 'center',
	        textStyle:{
		        color:"#737373"
	        }
        },
        tooltip : {
            trigger: 'item',
            formatter: function(params){
            	return "操作系统类型:"+dataArr[params.dataIndex-1].name+"</br>访问次数:"+dataArr[params.dataIndex-1].value+"</br>";
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
                name:'访问流量统计',
                type:'treemap',
								breadcrumb:{
									show:false
								},
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
	addLoadEvent(areaChart.resize);
	//根据数据量循环创建tr、td
	var table=$("table").eq(0)
	$("tbody tr",table).remove()
	var i=data.length-1
	while(i>0||i==0){
		var tr=$("<tr><td>"+(data.length-i)+"</td><td><div class='urlTd' style='width:"+($(".txtData>.countent").width())*0.65+"px"+";''>"+data[i].osType+"</div></td><td>"+data[i].osNum+"</div></td></tr>");
		$("tbody",table).append(tr);
		i-=1;
	}
	dataTableObjList[0]=dataTableInit(table);
}
//OS横向条形图
function OSvBar(data){
	var data=data.timeSlot;
	vBarChart=echarts.init(document.getElementsByClassName("echarts_Vbar")[0]);
	var option = {
    title: {
	        text: '访问系统条形统计图',
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
        name:'系统类型',
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
}
	vBarChart.setOption(option)
	addLoadEvent(vBarChart.resize);
	//根据数据量循环创建tr、td
	var table=$("table").eq(2)
	$("tbody tr",table).remove()
	var i=data.length-1
	while(i>0||i==0){
		var tr=$("<tr><td>"+(data.length-i)+"</td><td><div class='urlTd' style='width:"+($(".txtData>.countent").width())*0.65+"px"+";''>"+data[i].osType+"</td><td>"+data[i].osNum+"</div></td></tr>");
		$("tbody",table).append(tr);
		i-=1;
	}
	dataTableObjList[1]=dataTableInit(table);
}
// //BR地图
// function OSMap(data){
// 	mapChart=echarts.init(document.getElementsByClassName("echarts_map")[0]);
// 	function randomData() {
// 	    return Math.round(Math.random()*1000);
// 	}
// 	var option={
// 		title: {
// 	        text: '流量/PV/IP 地理视图',
// 	        subtext: 'MAP DEMO',
// 	        left: 'center',
// 	        textStyle:{
// 		        color:"#737373"
// 	        }
// 	    },
// 	    tooltip: {
// 	        trigger: 'item'
// 	    },
// 	    visualMap: {
// 	        min: 0,
// 	        max: 2500,
// 	        left: '5%',
// 	        top: 'bottom',
// 	        calculable: true,
// 	        inRange: {
// 	            color: ['#dff6ff', '#0080b5']
// 	        },
// 	        textStyle:{
// 		        color:"#737373"
// 	        }
// 	    },
// 	    toolbox: {
// 	        show:true,
// 	    	right:"5%",
// 	    	iconStyle:{
// 	    		normal:{
// 	    			borderColor:'#737373'
// 	    		},
// 	    		emphasis:{
// 	    			borderColor:'#737373'
// 	    		}
// 	    	},
// 	        orient: 'vertical',
// 	        top: 'center',
// 	        feature: {
// 	            restore: {},
// 	            saveAsImage: {}
// 	        }
// 	    },
// 	    series: [
// 	        {
// 	            name: '数据流量',
// 	            type: 'map',
// 	            mapType: 'china',
// 	            roam: false,
// 	            label: {
// 	                normal: {
// 	                    show: false,
// 	                    textStyle:{
// 	                    	color:'#999'
// 	                    }
// 	                },
// 	                emphasis: {
// 	                    show: true,
// 	                    textStyle:{
// 	                    	color:'#000'
// 	                    }
// 	                }
// 	            },
// 	            itemStyle: {
// 	            	normal: {
// 		                areaColor: '#fff',
// 		                borderColor: '#111'
// 		            },
// 	                emphasis: {
// 	                	areaColor: '#b2e4f6',
// 	                    borderColor: '#737373',
// 	                    borderWidth: 1
// 	                }
// 	            },
// 	            data:[
// 	                {name: '北京',value: randomData() },
// 	                {name: '天津',value: randomData() },
// 	                {name: '上海',value: randomData() },
// 	                {name: '重庆',value: randomData() },
// 	                {name: '河北',value: randomData() },
// 	                {name: '河南',value: randomData() },
// 	                {name: '云南',value: randomData() },
// 	                {name: '辽宁',value: randomData() },
// 	                {name: '黑龙江',value: randomData() },
// 	                {name: '湖南',value: randomData() },
// 	                {name: '安徽',value: randomData() },
// 	                {name: '山东',value: randomData() },
// 	                {name: '新疆',value: randomData() },
// 	                {name: '江苏',value: randomData() },
// 	                {name: '浙江',value: randomData() },
// 	                {name: '江西',value: randomData() },
// 	                {name: '湖北',value: randomData() },
// 	                {name: '广西',value: randomData() },
// 	                {name: '甘肃',value: randomData() },
// 	                {name: '山西',value: randomData() },
// 	                {name: '内蒙古',value: randomData() },
// 	                {name: '陕西',value: randomData() },
// 	                {name: '吉林',value: randomData() },
// 	                {name: '福建',value: randomData() },
// 	                {name: '贵州',value: randomData() },
// 	                {name: '广东',value: randomData() },
// 	                {name: '青海',value: randomData() },
// 	                {name: '西藏',value: randomData() },
// 	                {name: '四川',value: randomData() },
// 	                {name: '宁夏',value: randomData() },
// 	                {name: '海南',value: randomData() },
// 	                {name: '台湾',value: randomData() },
// 	                {name: '香港',value: randomData() },
// 	                {name: '澳门',value: randomData() }
// 	            ]
// 	        }
// 	    ]
// 	}
// 	mapChart.setOption(option)
// 	addLoadEvent(mapChart.resize);
// };
