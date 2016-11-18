//DATA
function dataBtn(dom) {
	moreDateMark=0;
	dataTableObjList=[];
	allAjaxCheck=false;
	btnUrl="datacount.open";
	pageName="DATA";
	$('.v-swiper').eq(0).html('<div class="swiper-wrapper">\
								<div class="swiper-slide"><div class="swiperImg col-md-12">\
	              <div class="swiperB echarts_line areaplot">\
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
	                 <th class="sorting">时间</th>\
	                 <th class="sorting">流量(bytes)</th>\
	                </tr>\
								</thead>\
			<tbody></tbody>\
	               </table>\
	               </div>\
	              </div>\
	             </div>\
	             <div class="swiper-slide">\
	              <div class="swiperImg col-md-12">\
								<div class="swiperB swiperB_full echarts_map mapplot">\
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
	                 <th class="sorting">流量(bytes)</th>\
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
									<div class="swiperB swiperB_full_ip echarts_map harfCharts">\
									</div>\
									<div class="echarts_lBar">\
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
	                 <th class="sorting">时间</th>\
	                 <th class="sorting">流量(bytes)</th>\
	                </tr>\
									</thead>\
			<tbody></tbody>\
	               </table>\
	               </div>\
	              </div>\
	             </div>\
	             <div class="swiper-slide">\
	              <div class="swiperImg col-md-12">\
	              <div class="swiperB echarts_area swiperB_full treemap">\
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
	                 <th class="sorting">IP</th>\
	                 <th class="sorting">流量(byte)</th>\
	                </tr>\
									</thead>\
			<tbody></tbody>\
	               </table>\
	               </div>\
	              </div>\
	             </div>\
	         </div>')
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
	var paginationArr=['数据传输总量统计图表','地理视图','数据传输总量统计表','数据传输量TOP50 IP']
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
			type: "get",
			url: "datacount.open",
//			url:'data/data.json',
			data : {
				project_id : projectID
			},
			async: true,
			success: function(data) {
				console.log(data)
				loadData(data);
				DATALine(data);
				DATAMap(data);
				DATAvBar(data);
				DATAarea(data);
				allAjaxCheck=true;
			},
			error: function() {
				alert('请求失败');
				allAjaxCheck=true;
			}
		})
	//调取缩略图生成方法
      smChartsBuild();
      //缩略图点击事件
	 $(".smChartsImgBox").delegate(".smChartsImg img","click",function(){
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
						if(figType=="areaplot"){
							dataTableObjList[0].destroy();
							DATALine(data);
						}else if(figType=="mapplot"){
							dataTableObjList[1].destroy();
							DATAMap(data);
						}else if(figType=="hbarplot"){
							dataTableObjList[2].destroy();
							DATAvBar(data);
						}else if(figType=="treemap"){
							dataTableObjList[3].destroy();
							DATAarea(data);
						}
					},
					error : function() {
						alert('请求失败');
						allAjaxCheck = true;
					}
				})
      })
}
//DATA折线图
function DATALine(data,time){
	lineChart=echarts.init(document.getElementsByClassName("echarts_line")[0])
	var data=data.timeSlot;
	var i=0;
	var option={
    	title:{
    		text:'访问流量统计图表',
    		subtext:time,
    		left:'center',
    		textStyle:{
    			color:'#737373'
    		}
    	},
			color:['rgba(135,171,221,0.3)','rgba(239,162,134,0.15)'],
    	legend:{
    		left:'13%',
    		top:'3%',
    		data:[{
    			name:"访问流量",
    			icon:'roundRect'
    		}],
    		textStyle:{
    			color:'#737373'
    		}
    	},
    	tooltip:{
    		trigger:'axis',
    		axisPointer:{
    			type:'line',
    			lineStyle:{
    				color:'#737373'
    			}
    		}
    	},
    	grid:{
    		left:'3%',
    		right:'4%',
    		bottom:'3%',
    		containLabel:true
    	},
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
    		name:'访问流量(Byte)',
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
        toolbox:{
        	show:true,
        	right:"15%",
					top:"15px",
        	iconStyle:{
        		normal:{
        			borderColor:'#737373'
        		},
        		emphasis:{
        			borderColor:'#737373'
        		}
        	},
        	feature:{
        		myTool1: {
	                show: true,
	                title: '切换为折线面积图',
									icon: 'image://img/t1.png',
	                onclick: function(){
	                	for(var i=0;i<option.series.length;i++){
		                	option.series[i].type="line"
		                	option.series[i].areaStyle={normal: {}}
		                	option.series[i].showSymbol=false;
	                	}
	                	lineChart.setOption(option)
	                }
	            },
	            myTool2: {
	                show: true,
	                title: '切换为折线图',
									icon: 'image://img/t2.png',
	                onclick: function(){
	                	for(var i=0;i<option.series.length;i++){
		                	option.series[i].type="line"
		                	option.series[i].areaStyle=false;
		                	option.series[i].showSymbol=true;
	                	}
	                	lineChart.setOption(option)
	                }
	            },
	            myTool3: {
	            	show: true,
	                title: '切换为柱形图',
									icon: 'image://img/t3.png',
	                onclick: function(){
	                	for(var i=0;i<option.series.length;i++){
		                	option.series[i].type="bar"
	                	}
	                	option.xAxis={
	                		boundaryGap: ['20%', '20%'],
	                		axisTick:{
	                			alignWithLabel:true
	                		}
	                	}
	                	lineChart.setOption(option)
	                	option.xAxis={
	                		boundaryGap: 0,
	                		axisTick:{
	                			alignWithLabel:true
	                		}
	                	}
	                }
	            },
	            myTool4: {
	            	show: true,
	                title: '切换为散点图',
									icon: 'image://img/t4.png',
	                onclick: function(){
	                	for(var i=0;i<option.series.length;i++){
		                	option.series[i].type="scatter"
	                	}
	                	option.xAxis={
	                		boundaryGap: ['10%', '10%'],
	                		axisTick:{
	                			alignWithLabel:true
	                		}
	                	}
	                	lineChart.setOption(option)
	                	option.xAxis={
	                		boundaryGap: 0,
	                		axisTick:{
	                			alignWithLabel:true
	                		}
	                	}
	                }
	            },
        		saveAsImage:{

        		},
        		myTool5: {
	            	show: true,
	                title: '返回上一层',
									icon: 'image://img/kj_sprite_11.gif',
	                onclick: function (){
	                	lineChart.showLoading();
	                	var rushDate1=data[0].time.toString().replace(/-/g,"");
                        var rushDate2=rushDate1.replace(' ',"");
                        var rushDate3=rushDate2.replace(':',"");
                        var startTime=parseInt(Number(rushDate3)/10000)*100;
                        var endTime=((parseInt(Number(rushDate3)/10000))+1)*100;
                        console.log(startTime+"|"+endTime)
	                	if(startTime.toString().length>=6){
		                	var time = 'free';
		                	if(startTime.toString().length == 6){
				    			selectMonth(time,startTime,endTime,'datacount.open');
				    		}else if(startTime.toString().length == 8){
				    			selectDaily(time,startTime,endTime,'datacount.open');
				    		}else if(startTime.toString().length == 10){
				    			selectHour(time,startTime,endTime,'datacount.open');
				    		}else if(startTime.toString().length == 12){
				    			selectMinute(time,startTime,endTime,'datacount.open');
				    		}
	                	}
	                }
	            }
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
        series:[{
        	name:"访问流量",
        	type:'line',
        	data:data.map(function(item){
        		return item["bytes"];
        	}),
        	lineStyle:{
        		normal:{
							color:'rgba(135,171,221,1)'
        		}
        	},
        	markLine : {
                data : [
                    {type : 'average', name : '平均值'}
                ],
                lable:{
                	normal:{
                		show:false
                	}
                },
                silent:true
           	},
        	areaStyle: {normal: {}},
        	symbolSize:10,
        	showSymbol:false
        }
        ]
    }
	lineChart.setOption(option)
	addLoadEvent(lineChart.resize);
	//点击选择时间段
	lineChart.on('click', function (params) {
    	var rushDate1=params.name.replace(/-/g,"")
    	var rushDate2=rushDate1.replace(' ',"")
    	var rushDate3=rushDate2.replace(':',"")
    	var startTime=rushDate3+'00'
    	var endTime=(Number(rushDate3)+1)*100
    	if(startTime.length<=12){
    		lineChart.showLoading();
    		var time = 'free';
    		if(startTime.length == 6){
    			selectMonth(time,startTime,endTime,'datacount.open');
    		}else if(startTime.length == 8){
    			selectDaily(time,startTime,endTime,'datacount.open');
    		}else if(startTime.length == 10){
    			selectHour(time,startTime,endTime,'datacount.open');
    		}else if(startTime.length == 12){
    			selectMinute(time,startTime,endTime,'datacount.open');

    		}

		};
	});
	//根据数据量循环创建tr、td
	var table=$("table").eq(0)
	$("tbody tr",table).remove()
	for(var i=0;i<data.length;i++){
		var tr=$("<tr><td>"+(i+1)+"</td><td><div class='urlTd' style='width:"+($(".txtData>.countent").width())*0.65+"px"+";''>"+data[i].time+"</div></td><td>"+data[i].bytes+"</td></tr>");
		$("tbody",table).append(tr);
	}
	dataTableObjList[0]=dataTableInit(table);
}
//DATA地图
function DATAMap(data){
	mapChart=echarts.init(document.getElementsByClassName("echarts_map")[0]);
	var pvList=[];
	var ipList=[];
	var bytesList=[];
	for(var i=0;i<data.geographyPVCount.length;i++){
		pvList.push({"name":data.geographyPVCount[i].province,"value":data.geographyPVCount[i].pvNum})
	}
	for(var i=0;i<data.geographyIpCount.length;i++){
		ipList.push({"name":data.geographyIpCount[i].province,"value":data.geographyIpCount[i].pvNum})
	}
	for(var i=0;i<data.geographyByteList.length;i++){
		bytesList.push({"name":data.geographyByteList[i].province,"value":data.geographyByteList[i].pvNum})
	}
	var option={
		title: {
	        text: '流量/PV/IP 地理视图',
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
	    legend: {
	        orient: 'vertical',
	        left: '5%',
	        data:['数据流量','访问次数','IP数量'],
	        textStyle:{
		        color:"#737373"
	        }
	    },
	    visualMap: {
	        min: 0,
	        max: 200,
	        left: '5%',
	        top: 'bottom',
	        inRange: {
	            color: ['#dcf6ff', '#00a3e0']
	        },
	        calculable:true,
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
		                borderColor: '#111',
	                    textStyle:'#737373'
		            },
	                emphasis: {
	                	areaColor: '#b2e4f6',
	                    borderColor: '#737373',
	                    borderWidth: 1
	                }
	            },
	            data:bytesList
	        },
	        {
	            name: '访问次数',
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
		                borderColor: '#111',
	                    textStyle:'#737373'
		            },
	                emphasis: {
	                	areaColor: '#b2e4f6',
	                    borderColor: '#737373',
	                    borderWidth: 1
	                }
	            },
	            data:pvList
	        },
	        {
	            name: 'IP数量',
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
		                borderColor: '#111',
	                    textStyle:'#737373'
		            },
	                emphasis: {
	                	areaColor: '#b2e4f6',
	                    borderColor: '#737373',
	                    borderWidth: 1
	                }
	            },
	            data:ipList
	        }
	    ]
	}
	option.legend.selectedMode = 'single';
	option.visualMap.max=(function(){
		var maxNum=0;
		for(var i=0;i<pvList.length;i++){
			if(maxNum<pvList[i].value){
				maxNum=pvList[i].value;
			};
		}
		return maxNum;
	})()
	mapChart.setOption(option)
	addLoadEvent(mapChart.resize);
	//根据数据量循环创建tr、td
  var table=$("table").eq(2)
	$("tbody tr",table).remove()
	for(var i=0;i<data.length;i++){
		var tr=$("<tr><td>"+(i+1)+"</td><td><div class='urlTd' style='width:"+($(".txtData>.countent").width())*0.65+"px"+";''>"+data[i].time+"</div></td><td>"+data[i].ipNum+"</td></tr>");
		$("tbody",table).append(tr);
	}
  dataTableObjList[1]=dataTableInit(table);
	mapChart.on("legendselectchanged",function(params){
		option.visualMap.max=(function(){
			var maxNum=0;
			if(params.name=="访问次数"){
				option.visualMap.inRange.color=['#dff6ff', '#0080b5'];
				for(var i=0;i<pvList.length;i++){
					if(maxNum<pvList[i].value){
						maxNum=pvList[i].value;
					};
				}
			}else if(params.name=="IP数量"){
				option.visualMap.inRange.color=['#eaf3ff', '#5780b8'];
				for(var i=0;i<ipList.length;i++){
					if(maxNum<ipList[i].value){
						maxNum=ipList[i].value;
					};
				}
			}else if(params.name=="数据流量"){
				option.visualMap.inRange.color=['#dcf6ff', '#00a3e0'];
				for(var i=0;i<bytesList.length;i++){
					if(maxNum<bytesList[i].value){
						maxNum=bytesList[i].value;
					};
				}
			}
			return maxNum;
		})();
		mapChart.setOption(option)
		addLoadEvent(mapChart.resize);
	})
	//根据数据量循环创建tr、td
	var table=$("table").eq(4)
	$("tbody tr",table).remove()
	for(var i=0;i<data.geographyByteList.length;i++){
		var tr=$("<tr><td>"+(i+1)+"</td><td><div class='urlTd' style='width:"+($(".txtData>.countent").width())*0.65+"px"+";''>"+data.geographyByteList[i].province+"</div></td><td>"+data.geographyByteList[i].pvNum+"</td></tr>");
		$("tbody",table).append(tr);
	}
	dataTableObjList[2]=dataTableInit(table);
};
//DATA横向条形图
function DATAvBar(data){
	var dataUrlNum=50;
	var urlI=10;
	var data=data;
	vBarChart=echarts.init(document.getElementsByClassName("echarts_Vbar")[0]);
	var option = {
    title: {
	        text: '访问流量TOP50 URL统计表',
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
    			name:"访问流量",
    			icon:'roundRect'
    		}],
    		left:'10%',
    		top:'3%',
    		textStyle:{
    			color:'#737373'
    		}
    	},
	dataZoom: [
        {
            type: 'slider',
            show: true,
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
        data: data.urlCount.map(function(item){
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
            name: '访问流量',
            type: 'bar',
            data: data.urlCount.map(function(item){
        		return item['bytes'];
        	})
        }
    ]
}
	vBarChart.setOption(option)
	addLoadEvent(vBarChart.resize);
	vBarChart.on('click', function (params) {
		if(allAjaxCheck==true){
			var thisDom=this._dom;
			var index=parseInt(params.dataIndex);
			var url=data.urlCount[index].url;
			$('.harfCharts').css("height","50%")
			$('.echarts_lBar').css({"height":"50%"})
			mapChart2=echarts.init(document.getElementsByClassName("echarts_map")[1]);
			mapChart2.setOption(harfMapChartsOption);
			vBarChart=echarts.init(this._dom);
			vBarChart.setOption(option)
			getBytesByUrl(url,startTime,endTime);
			addLoadEvent(vBarChart.resize);

			vBarChart.on('click', function (params) {
				if(allAjaxCheck==true){
					allAjaxCheck=false;
					var index=parseInt(params.dataIndex);
					var url=data.urlCount[index].url;
					getBytesByUrl(url,startTime,endTime);
				}
			})
		}
	});
}

function getBytesByUrl(url,startTime,endTime){
	if(startTime.toString().length == 6){
		$.ajax({
			type:"get",
			// url:btnUrl+"?cmd=WEL:GETBYTESBYURLMONTH",
			url:"data/data.json",
			data : {
				url :url,
				startTime:startTime,
				endTime:endTime
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				DATABar_l(data,url);
				allAjaxCheck=true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck=true;
			}
		})
	}else if(startTime.toString().length == 8){
		$.ajax({
			type:"get",
			// url:btnUrl+"?cmd=WEL:GETBYTESBYURLDAILY",
			url:"data/data.json",
			data : {
				url :url,
				startTime:startTime,
				endTime:endTime
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				DATABar_l(data,url);
				allAjaxCheck=true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck=true;
			}
		})
	}else if(startTime.toString().length == 10){
		$.ajax({
			type:"get",
			// url:btnUrl+"?cmd=WEL:GETBYTESBYURLHOUR",
			url:"data/data.json",
			data : {
				url :url,
				startTime:startTime,
				endTime:endTime
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				DATABar_l(data,url);
				allAjaxCheck=true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck=true;
			}
		})
	}else if(startTime.toString().length == 12){
		$.ajax({
			type:"get",
			// url:btnUrl+"?cmd=WEL:GETBYTESBYURLMINUTE",
			url:"data/data.json",
			data : {
				url :url,
				startTime:startTime,
				endTime:endTime
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				PVBar_l(data,url);
				allAjaxCheck=true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck=true;
			}
		})
	}
}
//DATA辅助竖状条形图
function DATABar_l(data,title){
	lBarChart=echarts.init(document.getElementsByClassName('echarts_lBar')[0]);
	var option = {
		color:['#87abdd'],
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            data : data.timeSlot.map(function(item){
	            	return item.time
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
	            axisLine: {
                lineStyle: {
                    color: '#737373'
                }
            }
	        },
	    ],
	    series : [
	        {
	            name:'直接访问',
	            type:'bar',
	            barWidth: '60%',
	            data:data.timeSlot.map(function(item){
	            	return item.bytes
	            }),
	        }
	    ]
	};
	lBarChart.setOption(option);
	addLoadEvent(lBarChart.resize);
}
//DATA矩形树图（面积图）
function DATAarea(data){
	areaChart=echarts.init(document.getElementsByClassName("echarts_area")[0]);
	var data=data.ipCount;
	var dataArr=[];
	for(var i=0;i<data.length;i++){
		dataArr.push({});
		dataArr[i].name=data[i].ip;
		dataArr[i].value=data[i].bytes;
	}
	areaChart.setOption(option = {
        title : {
            text: '访问流量TOP50 IP面积图',
            subtext: 'areaDemo',
            left: 'center',
	        textStyle:{
		        color:"#737373"
	        }
        },
        tooltip : {
            trigger: 'item',
            formatter: "IP</br> 访问流量：{c}"
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
	var table=$("table").eq(6)
	$("tbody tr",table).remove()
	var i=data.length-1
	while(i>0||i==0){
		var tr=$("<tr><td>"+(data.length-i)+"</td><td><div class='urlTd' style='width:"+($(".txtData>.countent").width())*0.65+"px"+";''>"+data[i].ip+"</div></td><td>"+data[i].bytes+"</td></tr>");
		$("tbody",table).append(tr);
		i-=1;
	}
	dataTableObjList[3]=dataTableInit(table);

}
// //DATA地图2
// function DATAMap2(data){
// 	mapChart2=echarts.init(document.getElementsByClassName("echarts_map")[1]);
// 	function randomData() {
// 	    return Math.round(Math.random()*1000);
// 	}
// 	var option={
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
// 	            name: '访问次数',
// 	            type: 'map',
// 	            mapType: 'china',
// 	            roam: false,
// 	            label: {
// 	                normal: {
// 	                    show: false
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
// 	harfMapChartsOption=option;
// 	mapChart2.setOption(option);
// 	addLoadEvent(mapChart2.resize);
// };
