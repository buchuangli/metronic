//STATUS
function statusBtn(){
	moreDateMark=0;
	dataTableObjList=[];
	allAjaxCheck=false;
	statusArr=[];
	btnUrl="status.open";
	pageName="STATUS";
	$('.v-swiper').eq(0).html('<div class="swiper-wrapper">\
					<div class="swiper-slide"><div class="swiperImg col-md-12">\
        	<div class="swiperB echarts_bar1 swiperB_full vbarplot" style="display:block;width:100%;height:72%">\
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
        				<th class="sorting">状态码</th>\
        				<th class="sorting">出现次数</th>\
        			</tr>\
						</thead>\
			<tbody></tbody>\
        		</table>\
        		</div>\
        	</div>\
        </div>\
        <div class="swiper-slide">\
        	<div class="swiperImg col-md-12">\
        	<div class="swiperB echarts_bar2 swiperB_full mc_line" style="display:block;width:100%;height:72%">\
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
        				<th class="sorting">状态码</th>\
        				<th class="sorting">时间</th>\
        				<th class="sorting">出现次数</th>\
        			</tr>\
						</thead>\
			<tbody></tbody>\
        		</table>\
        		</div>\
        	</div>\
        </div>\
        <div class="swiper-slide">\
        	<div class="swiperImg col-md-12">\
            	<div class="swiperB swiperB_full mc_bar_status_url">\
             		<div class="echarts_Vbar1" draggable="false">\
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
            	<div class="swiperB swiperB_full mc_bar_status">\
             		<div class="echarts_Vbar2 harfCharts" draggable="false">\
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
    var paginationArr=['页面状态统计图表','页面状态时间分布表','页面状态URL分布统计表','页面状态IP分布统计表']
		var swiper = new Swiper('.swiper-container', {
			observer : true,
			observeParents:true,
	        pagination: '.swiper-pagination',
			paginationClickable: true,
			paginationBulletRender: function (index, className) {
			    return '<span class="col-md-3 col-xs-3 ' + className+' swiperContainer' + '">' + paginationArr[index] + '</span>';
			}
	    });

    console.log(skipSwiper);
	swiper.slideTo(skipSwiper);
	skipSwiper=0;
	skipPage="";
	//AJAX与echarts插件引用
	$.ajax({
		type:"get",
		url:'status.open',
//		url:'data/status.json',
		data : {
			project_id : projectID
		},
		async:true,
		success:function(data){
			loadData(data);
			STATUSBar1(data);
			for(var i=0;i<data.timeSlot.length;i++){
				statusArr.push(data.timeSlot[i]["status"])
			}
			STATUSBar2(data);
			STATUSVbar1(data);
			STATUSVbar2(data);
			allAjaxCheck=true;
		},
		error : function() {
			alert('请求失败');
			allAjaxCheck=true;
		}
	})
	//地理视图1
	$(".moreCharts>span", $(".swiper-slide:eq(2)")).click(function() {
		if(allAjaxCheck==true){
			allAjaxCheck=false;
			$.ajax({
				type: "get",
				url: 'data/hour_data.json',
				async: true,
				success: function(data) {
					STATUSMap1(data);
					allAjaxCheck=true;
				},
				error: function() {
					alert('请求失败');
					allAjaxCheck=true;
				}
			})
		}
	})
	//地理视图2
			$.ajax({
				type: "get",
				url: 'data/hour_data.json',
				async: true,
				success: function(data) {
					STATUSMap2(data);
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
      $(".smChartsImg img").click(function(){
      	$("li",$(this).parent().parent()).css("border","1px solid rgba(0,0,0,0)");
      	$(this).parent().css("border","1px solid #2E98FF");
      	var imgDateArr=$(this).attr("src").replace("NailFIG/","").replace(".png","").split("/");
      	var projectID=imgDateArr[1];
      	var figType=imgDateArr[3];
      	var interval=imgDateArr[4];
      	var imgStartTime=imgDateArr[5].replace(/-/g,"");
      	console.log(projectID+"|"+figType+"|"+interval+"|"+imgStartTime)
      	   		$.ajax({
					type : "post",
					url : btnUrl + '?cmd=WEL:GETCHARINFO',
					data : {
						project_id : projectID,
						chartType : figType,
						dateType : interval,
						date:imgStartTime
						//date : 20160301
					},
					async : true,
					success : function(data) {
						console.log(data);
					},
					error : function() {
						alert('请求失败');
						allAjaxCheck = true;
					}
				})
      })
}
//STATUS竖向柱状图1
function STATUSBar1(data){
	barChart=echarts.init(document.getElementsByClassName("echarts_bar1")[0]);
	var data=data.timeSlot;
	var option = {
		title:{
    		text:'状态码统计图表',
    		left:'center',
    		textStyle:{
    			color:'#737373'
    		}
    	},
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
			name:'页面状态分类',
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
			name:'页面状态出现次数',
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
	barChart.setOption(option);
	addLoadEvent(barChart.resize);
	//根据数据量循环创建tr、td
	var table=$("table").eq(0);
	$("tbody tr",table).remove();
	for(var i=0;i<data.length;i++){
		var tr=$("<tr><td>"+(i+1)+"</td><td>"+data[i].status+"</td><td><div class='urlTd' style='width:"+($(".txtData>.countent").width())*0.65+"px"+";''>"+data[i].statusNum+"</div></td></tr>");
		$("tbody",table).append(tr);
	}
	dataTableObjList[0]=dataTableInit(table);
}
//STATUS竖向柱状图2
function STATUSBar2(data){
	barChart=echarts.init(document.getElementsByClassName("echarts_bar2")[0]);
	console.log(statusArr)
	var dataArr=[];
	var data=data.allStatusType;
	for(var i in data){
		dataArr.push(data[i])
	}
	console.log(dataArr)
	var option = {
		color:['#87abdd'],
		title:{
    		text:'页面状态时间分布表',
    		left:'center',
    		textStyle:{
    			color:'#737373'
    		}
    	},
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
	    legend:{
    		data:statusArr.map(function(item){
    			return item.toString();
    		}),
    		left:'5%',
    		top:'6%',
    		icon:'roundRect',
    		textStyle:{
    			color:'#737373'
    		},
    		selectedMode:'single'
    	},
	    xAxis : [
	        {
	            type : 'category',
	            data : dataArr[0].map(function(item){
	            	return item['time']
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
			name:'该状态出现次数',
	            axisLine: {
                lineStyle: {
                    color: '#737373'
                }
            }
	        },
	    ],
	    series : function() {
			var serie = [];
			for (var i = 0; i < statusArr.length; i++) {
				var serieObj = {
					name : statusArr[i],
					type : 'line',
					data : dataArr[i].map(function(item) {
						return item['pvNum'];
					})
				}
				serie.push(serieObj);
			}
			return serie;
		}()
	};
	barChart.setOption(option);
	addLoadEvent(barChart.resize);
	//根据数据量循环创建tr、td
	var table=$("table").eq(2)
	$("tbody tr",table).remove()
	for(var i=0;i<data["200"].length;i++){
		var tr=$("<tr><td>"+"200"+"</td><td>"+data["200"][i].time+"</td><td>"+data["200"][i].pvNum+"</td></tr>");
		$("tbody",table).append(tr);
	}
	dataTableObjList[1]=dataTableInit(table);
	barChart.on("legendselectchanged",function(params){
		$("table").eq(1).empty();
		$("table").eq(1).append($("<tr><th>状态码</th><th>时间</th><th>次数</th></tr>"));
		for(var i=0;i<data[params.name].length;i++){
			var tr=$("<tr><td>"+params.name+"</td><td>"+data[params.name][i].time+"</td><td>"+data[params.name][i].pvNum+"</td></tr>");
			$("table").eq(1).append(tr);
		}
		dataTableObjList[1]=dataTableInit(table);
	})
}
//STATUS横向条形图1
function STATUSVbar1(data){
	var statusListNum=1;
	var dataArr=[];
	var data=data.urlStatusType;
	for(var i in data){
		dataArr.push(data[i])
	}
	console.log(dataArr);
	vBarChart=echarts.init(document.getElementsByClassName("echarts_Vbar1")[0]);
	var option = {
		color:['#87abdd'],
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    legend:{
    		data:statusArr.map(function(item){
    			return item.toString();
    		}),
    		left:'5%',
    		top:'0%',
    		width:'30%',
    		textStyle:{
    			color:'#737373'
    		},
    		selectedMode:'single'
    	},
	dataZoom: [
        {
            type: 'slider',
            show: true,
            yAxisIndex: [0],
            top:'9.7%',
            left: '93%',
            start: 50,
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
			name:'产生该状态的URL排名',
        data: statusArr.map(function(item){
        		return "URL"+statusListNum++;
        	}),
		axisLine: {
                lineStyle: {
                    color: '#737373'
                }
            }
	},
	series : function() {
		var serie = [];
		for (var i = 0; i < statusArr.length; i++) {
			var serieObj = {
				name : statusArr[i],
				type : 'bar',
				data : dataArr[i].map(function(item) {
					return item['statusNum'];
				})
			}
			serie.push(serieObj);
		}
		return serie;
	}()
}
	vBarChart.setOption(option);
	addLoadEvent(vBarChart.resize);
	//根据数据量循环创建tr、td
	var table=$("table").eq(4)
	$("tbody tr",table).remove()
	var i=data.length-1
	while(i>0||i==0){
		var tr=$("<tr><td>"+(data.length-i)+"</td><td><div class='urlTd' style='width:"+($(".txtData>.countent").width())*0.65+"px"+";''>"+data[i].osType+"</td><td>"+data[i].osNum+"</div></td></tr>");
		$("tbody",table).append(tr);
		i-=1;
	}
	dataTableObjList[2]=dataTableInit(table);
	vBarChart.on('click', function (params) {
		if(allAjaxCheck==true){
			allAjaxCheck=false;
			var thisDom=this._dom;
			$('.echarts_Vbar1').css("width","40%")
	    	$('.echarts_lBar').css({"width":"59%","height":"100%"})
			vBarChart=echarts.init(this._dom);
			//－－ＡＪＡＸ开始－－
			var url=data[params.seriesName][params.dataIndex].url;

			var status = params.seriesName;
			console.log(startTime+'---'+endTime)
			console.log(status+"|"+url)
			getStatusByUrl(status,url,startTime,endTime)

			//－－ＡＪＡＸ结束，回调开始－－
			vBarChart.setOption(option)
			addLoadEvent(vBarChart.resize);
			//STATUSBar_l1(data,url);

			vBarChart.on('click', function (params) {
				console.log(params)
				if(allAjaxCheck==true){
					allAjaxCheck=false;
					var url=data[params.seriesName][params.dataIndex].url;
					console.log(status+"|"+url)
					var status = params.seriesName;
					getStatusByUrl(status,url,startTime,endTime)
				}
			});
		}
	});
	vBarChart.on("legendselectchanged", function(params) {
		console.log(params.name)
		var statusListNum=data[params.name].length;
		option.yAxis.data=data[params.name].map(function(item){
			return statusListNum--
		})
		vBarChart.setOption(option);

	})
}

function getStatusByUrl(status,url,startTime,endTime){
	if(startTime.toString().length == 6){
		$.ajax({
			type:"get",
			// url:btnUrl+"?cmd=WEL:GETINFOBYSTATUSMONTH",
			url:"data/status.json",
			data : {
				status :status,
				url :url,
				startTime:startTime,
				endTime:endTime
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				STATUSBar_l1(data,url);
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
			// url:btnUrl+"?cmd=WEL:GETINFOBYSTATUSDAILY",
			url:"data/status.json",
			data : {
				status :status,
				url :url,
				startTime:startTime,
				endTime:endTime
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				STATUSBar_l1(data,url);
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
			// url:btnUrl+"?cmd=WEL:GETINFOBYSTATUSHOUR",
			url:"data/status.json",
			data : {
				status :status,
				url :url,
				startTime:startTime,
				endTime:endTime
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				STATUSBar_l1(data,url);
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
			// url:btnUrl+"?cmd=WEL:GETINFOBYSTATUSMINUTE",
			url:"data/status.json",
			data : {
				status :status,
				url :url,
				startTime:startTime,
				endTime:endTime
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				STATUSBar_l1(data,url);
				allAjaxCheck=true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck=true;
			}
		})
	}
}
function getStatusByIp(status,url,startTime,endTime){
	if(startTime.toString().length == 6){
		$.ajax({
			type:"get",
			// url:btnUrl+"?cmd=WEL:GETIPBYSTATUSMONTH",
			url:"data/status.json",
			data : {
				status :status,
				ip :url,
				startTime:startTime,
				endTime:endTime
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				STATUSBar_l2(data,url);
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
			// url:btnUrl+"?cmd=WEL:GETIPBYSTATUSDAILY",
			url:"data/status.json",
			data : {
				status :status,
				ip :url,
				startTime:startTime,
				endTime:endTime
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				console.log(data)
				STATUSBar_l2(data,url);
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
			// url:btnUrl+"?cmd=WEL:GETIPBYSTATUSHOUR",
			url:"data/status.json",
			data : {
				status :status,
				ip :url,
				startTime:startTime,
				endTime:endTime
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				STATUSBar_l2(data,url);
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
			// url:btnUrl+"?cmd=WEL:GETIPBYSTATUSMINUTE",
			url:"data/status.json",
			data : {
				status :status,
				ip :url,
				startTime:startTime,
				endTime:endTime
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				STATUSBar_l2(data,url);
				allAjaxCheck=true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck=true;
			}
		})
	}
}
//STATUS辅助竖状条形图1
function STATUSBar_l1(data,title){
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
	            data : data.everyUrlStatus.map(function(item){
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
	            data:data.everyUrlStatus.map(function(item){
	            	return item.statusNum
	            }),
	        }
	    ]
	};
	lBarChart.setOption(option);
	addLoadEvent(lBarChart.resize);
}
//STATUS横向条形图2
function STATUSVbar2(data){
	var statusListNum=1;
	var dataArr=[];
	var data=data.IpStatusType;
	for(var i in data){
		dataArr.push(data[i])
	}
	console.log(dataArr)
	vBarChart=echarts.init(document.getElementsByClassName("echarts_Vbar2")[0]);
	var option = {
		color:['#87abdd'],
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    legend:{
    		data:statusArr.map(function(item){
    			return item.toString();
    		}),
    		left:'5%',
    		top:'0%',
    		textStyle:{
    			color:'#737373'
    		},
    		selectedMode:'single'
    	},
	dataZoom: [
        {
            type: 'slider',
            show: true,
            yAxisIndex: [0],
            right: '0',
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
			name:'产生该状态的IP排名',
        data: statusArr.map(function(item){
        		return "URL"+statusListNum++;
        	}),
		axisLine: {
                lineStyle: {
                    color: '#737373'
                }
            }
	},
	series : function() {
		var serie = [];
		for (var i = 0; i < statusArr.length; i++) {
			var serieObj = {
				name : statusArr[i],
				type : 'bar',
				data : dataArr[i].map(function(item) {
					return item['statusNum'];
				})
			}
			serie.push(serieObj);
		}
		return serie;
	}()
}
	vBarChart.setOption(option);
	addLoadEvent(vBarChart.resize);
	//根据数据量循环创建tr、td
	var table=$("table").eq(6)
	$("tbody tr",table).remove()
	var i=data.length-1
	while(i>0||i==0){
		var tr=$("<tr><td>"+(data.length-i)+"</td><td><div class='urlTd' style='width:"+($(".txtData>.countent").width())*0.65+"px"+";''>"+data[i].osType+"</td><td>"+data[i].osNum+"</div></td></tr>");
		$("tbody",table).append(tr);
		i-=1;
	}
	dataTableObjList[3]=dataTableInit(table);
	vBarChart.on('click', function (params) {
		if(allAjaxCheck==true){
			allAjaxCheck==false;
			var thisDom=this._dom;
			console.log(params)
			$('.harfCharts').css("height","50%");
			$('.echarts_lBar').css({"height":"50%"});
			mapChart2=echarts.init(document.getElementsByClassName("echarts_map")[1]);
			mapChart2.setOption(harfMapChartsOption);
			vBarChart=echarts.init(this._dom);
			//－－ＡＪＡＸ开始－－
			var url=data[params.seriesName][params.dataIndex].ip;

			var status = params.seriesName;
			console.log(startTime+'---'+endTime)
			getStatusByIp(status,url,startTime,endTime)
			addLoadEvent(vBarChart.resize);
			//－－ＡＪＡＸ结束，回调开始－－

			vBarChart.setOption(option)

			//STATUSBar_l1(data,url);

			vBarChart.on('click', function (params) {
				if(allAjaxCheck==true){
					allAjaxCheck=false;
					var url=data[params.seriesName][params.dataIndex].ip;
					var status = params.seriesName;
					getStatusByIp(status,url,startTime,endTime)
				}
			});
		}
	});
	vBarChart.on("legendselectchanged", function(params) {
		option.yAxis.data=data[params.name].map(function(item){
			return item["ip"];
		})
		vBarChart.setOption(option);
	})
}
//STATUS辅助竖状条形图2
function STATUSBar_l2(data,title){
	lBarChart=echarts.init(document.getElementsByClassName('echarts_lBar')[1]);
	console.log(data)
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
	            data : data.everyIpStatus.map(function(item){
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
	            data:data.everyIpStatus.map(function(item){
	            	return item.statusNum
	            }),
	        }
	    ]
	};
	lBarChart.setOption(option);
	addLoadEvent(lBarChart.resize);
}
//STATUS地图2
function STATUSMap2(data){
	mapChart2=echarts.init(document.getElementsByClassName("echarts_map")[0]);
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
	harfMapChartsOption=option;
	mapChart2.setOption(option)
	addLoadEvent(mapChart2.resize);
};
