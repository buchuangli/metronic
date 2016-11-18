function rfBtn(){
	moreDateMark=0;
	allAjaxCheck=false;
	dataTableObjList=[];
	btnUrl="referrer.open";
	pageName="REFERRER";
	$('.v-swiper').eq(0).html('<div class="swiper-wrapper">\
					<div class="swiper-slide"><div class="swiperImg col-md-12">\
        	<div class="swiperB row swiperB_full echarts_area treemap">\
        	</div>\
        	<ul class="smChartsImgBox row"></ul>\
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
        				<th>序号</th>\
        				<th>访问来源</th>\
        				<th>访问次数</th>\
        			</tr>\
						</thead>\
			<tbody></tbody>\
        		</table>\
        		</div>\
        	</div>\
        </div>\
        <div class="swiper-slide">\
        	<div class="swiperImg col-md-12">\
        	<div class="swiperB row swiperB_full hbarplot">\
        		<div class="echarts_Vbar" draggable="false">\
	           </div>\
	           <div class="echarts_lBar">\
	           </div>\
        	</div>\
					<ul class="smChartsImgBox row"></ul>\
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
				<th>序号</th>\
				<th>URL</th>\
				<th>访问次数</th>\
				</tr>\
				</thead>\
			<tbody></tbody>\
				</table>\
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
    var paginationArr=['页面访问来源统计面积图','页面访问来源形统计图']
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
		url:"referrer.open",
	//	url:'data/rf.json',
		data : {
			project_id : projectID
		},
		async:true,
		success:function(data){
			loadData(data);
			RFarea(data);
			RFvBar(data);
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
						RFarea(data);
						RFvBar(data);
					},
					error : function() {
						alert('请求失败');
						allAjaxCheck = true;
					}
				})
	      })
}
//TP矩形树图（面积图）
function RFarea(data){
	areaChart=echarts.init(document.getElementsByClassName("echarts_area")[0]);
	var data1=data["directType"];
	var data=data["indirectType"];
	var dataArr=[];
	//console.log()
	for(var i=0;i<data.length-1;i++){
		dataArr.push({});
		dataArr[i].name=data[i].url;
		dataArr[i].value=data[i].urlNum;
	}
	dataArr.push({});
	if(data.length>0){
		dataArr[data.length-1].name="直接访问";
		dataArr[data.length-1].value=data1[0].referrerNum;
	}

	areaChart.setOption(option = {
        title : {
            text: '访问来源统计面积图',
            subtext: 'areaDemo',
            left: 'center',
	        textStyle:{
		        color:"#737373"
	        }
        },
        tooltip : {
            trigger: 'item',
            formatter: function(params){
            	return "URL:"+dataArr[params.dataIndex-1].name+"</br>访问次数:"+dataArr[params.dataIndex-1].value+"</br>占比:"+((ForDight((dataArr[params.dataIndex-1].value/(data1[0].referrerNum+data1[1].referrerNum))*100,2)))+"%";
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
                name:'访问来源统计',
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
	var data=dataArr;
	var i=data.length-1;
	while(i>0||i==0){
		var tr=$("<tr><td>"+(data.length-i)+"</td><td><div class='urlTd' style='width:"+($(".txtData>.countent").width())*0.65+"px"+";'>"+data[i].name+"</div></td><td>"+data[i].value+"</td></tr>");
		$("tbody",table).append(tr);
		i-=1;
	}
	dataTableObjList[0]=dataTableInit(table);
}
//RF横向条形图
function RFvBar(data){
	var rfAllUrl=10;
	var data1=data["directType"];
	var data2=data["indirectType"];
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

	console.log(data)
	vBarChart=echarts.init(document.getElementsByClassName("echarts_Vbar")[0]);
	var option = {
    title: {
	        text: '访问来源统计条形图',
	        subtext: 'TIME',
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
        name:'访问来源',
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
	vBarChart.setOption(option);
	addLoadEvent(vBarChart.resize);
	vBarChart.on('click', function (params) {
		var thisDom=this._dom;
		var fullUrl=data[Math.abs((params.name.replace("URL",""))-10)].url;
		var url=fullUrl.split("?")[0];
    	$('.echarts_Vbar').css("width","40%")
    	$('.echarts_lBar').css({"width":"59%","height":"100%"})
		vBarChart=echarts.init(this._dom);
		option.title.show=false;
		vBarChart.setOption(option)
		console.log(url+":"+"===="+startTime+'======'+endTime);
		getReferrerByUrl(url,startTime,endTime);
		vBarChart.on('click', function (params) {
			var fullUrl=data[Math.abs((params.name.replace("URL",""))-10)].url;
			var url=fullUrl.split("?")[0];
			console.log(url+":"+"===="+startTime+'======'+endTime);
			getReferrerByUrl(url,startTime,endTime);
		})
	});
	//根据数据量循环创建tr、td
	console.log(data)
	var table=$("table").eq(2);
	$("tbody tr",table).remove()
	var i=data.length-1;
	while(i>0||i==0){
		var tr=$("<tr><td>"+(data.length-i)+"</td><td><div class='urlTd' style='width:"+($(".txtData>.countent").width())*0.65+"px"+";'>"+data[i].url+"</div></td><td>"+data[i].urlNum+"</td></tr>");
		$("tbody",table).append(tr);
		i-=1;
	}
	dataTableObjList[1]=dataTableInit(table);
}

function getReferrerByUrl(url,startTime,endTime){
	if(startTime.toString().length == 6){
		$.ajax({
			type:"get",
			url:btnUrl+"?cmd=WEL:SELECTURLBYMONTH",
			data : {
				referrer :url,
				startTime:startTime,
				endTime:endTime
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				RFBar_l1(data,url);
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
			url:btnUrl+"?cmd=WEL:SELECTURLBYDAILY",
			data : {
				referrer :url,
				startTime:startTime,
				endTime:endTime
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				RFBar_l1(data,url);
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
			url:btnUrl+"?cmd=WEL:SELECTURLBYHOUR",
			data : {
				referrer :url,
				startTime:startTime,
				endTime:endTime
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				RFBar_l1(data,url);
				allAjaxCheck=true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck=true;
			}
		})
	}else if(startTime.toString().length == 12){
		console.log(startTime)
		console.log(endTime)
		$.ajax({
			type:"get",
			url:btnUrl+"?cmd=WEL:SELECTURLBYMINUTE",
			data : {
				referrer :url,
				startTime:startTime,
				endTime:endTime
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				RFBar_l1(data,url);
				allAjaxCheck=true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck=true;
			}
		})
	}
}
//RF辅助竖状条形图
function RFBar_l1(data,title){
	console.log(data);
	var data = data.timeSlot;
	lBarChart=echarts.init(document.getElementsByClassName('echarts_lBar')[0]);
	var option = {
		 title: {
	        text: '访问来源统计条形图',
	        subtext: title,
	        left: 'center',
	        textStyle:{
		        color:"#737373"
	        }
	    },
	    color: ['#87abdd'],
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
	            data : data.map(function(item){
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
	            data:data.map(function(item){
	            	return item.statusNum
	            }),
	        }
	    ]
	};
	lBarChart.setOption(option);
	addLoadEvent(lBarChart.resize);
}
