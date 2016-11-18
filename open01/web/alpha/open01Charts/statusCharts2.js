//STATUS
function statusBtn(){
	moreDateMark=0;
	allAjaxCheck=false;
	statusArr=[];
	btnUrl="status.open";
	pageName="STATUS";
	$('.v-swiper').eq(0).html('<div class="swiper-wrapper"><div class="swiper-slide"><div class="swiperImg">\
        	<div class="swiperB echarts_bar1 swiperB_full vbarplot" style="display:block;width:100%;height:72%">\
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
        				<th>状态码</th>\
        				<th>出现次数</th>\
        			</tr>\
        		</table>\
        		</div>\
        	</div>\
        </div>\
        <div class="swiper-slide">\
        	<div class="swiperImg">\
        	<div class="swiperB echarts_bar2 swiperB_full mc_line" style="display:block;width:100%;height:72%">\
        	</div>\
        	</div>\
        	<div class="txtData">\
        		<div class="countent">\
        		<table border="0" bordercolor="#737373" cellspacing="0" cellpadding="0" class="dataTable">\
        			<col style="width: 10%" />\
					<col style="width: 65%" />\
					<col style="width: 20%" />\
        			<tr>\
        				<th>状态码</th>\
        				<th>时间</th>\
        				<th>出现次数</th>\
        			</tr>\
        		</table>\
        		</div>\
        	</div>\
        </div>\
        <div class="swiper-slide">\
        	<div class="swiperImg">\
            	<div class="swiperB swiperB_full mc_bar_status_url">\
             		<div class="echarts_Vbar1" draggable="false">\
              		</div>\
               		<div class="echarts_lBar">\
              		</div>\
             	</div>\
        	</div>\
					<div class="txtData">\
						<div class="countent">\
						<table border="0" bordercolor="#737373" cellspacing="0" cellpadding="0" class="dataTable">\
							<col style="width: 10%" />\
					<col style="width: 65%" />\
					<col style="width: 20%" />\
							<tr>\
								<th>状态码</th>\
								<th>访问请求路径</th>\
								<th>出现次数(次)</th>\
							</tr>\
						</table>\
						</div>\
					</div>\
        </div>\
       	<div class="swiper-slide">\
        	<div class="swiperImg">\
            	<div class="swiperB swiperB_full mc_bar_status">\
             		<div class="echarts_Vbar2" draggable="false">\
              		</div>\
               		<div class="echarts_lBar">\
              		</div>\
             	</div>\
        	</div>\
					<div class="txtData">\
						<div class="countent">\
						<table border="0" bordercolor="#737373" cellspacing="0" cellpadding="0" class="dataTable">\
							<col style="width: 10%" />\
					<col style="width: 65%" />\
					<col style="width: 20%" />\
							<tr>\
								<th>状态码</th>\
								<th>访问请求IP</th>\
								<th>出现次数(次)</th>\
							</tr>\
						</table>\
						</div>\
					</div>\
        </div>\
    </div>');
    var paginationArr=['访问请求响应状态','访问请求响应状态时间分布','TOP50 响应状态的访问请求路径','TOP50 相应状态的访问请求IP来源']
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
	skipSwiper = 0;
	skipPage = "";
	$(".leftBtn li e").css("border-color","transparent");
	$(".leftBtnTit").css("border-color","transparent");
	$("#statusSerch e").css("border-color","#797f94");
	$("#statusSerch").parent().prev().css("border-color","#797f94");

	// swiper点击后更新内容

	// AJAX与echarts插件引用

	$.ajax({
		type : "get",
		url : 'status.open',
		data : {
			project_id : projectID
		},
		// url:'data/status.json',
		async : true,
		success : function(data) {
			loadData(data);
			STATUSBar1(data);
			for (var i = 0; i < data.timeSlot.length; i++) {
				statusArr.push(data.timeSlot[i]["status"])
			}
			STATUSBar2(data);
			STATUSVbar1(data);
			STATUSVbar2(data);
			allAjaxCheck = true;
		},
		error : function() {
			alert('请求失败');
			allAjaxCheck = true;
		}
	})
	// 数据详情
	$(".moreCharts>span").click(function() {
		$(this).parent().parent().next().css({
			"transform" : "translateY(0)"
		});
		$(this).parent().parent().css({
			"transform" : "translateY(-100%)"
		});
	})
	// 收起表单
	$(".closeCharts>span").click(function() {
		$(this).parent().parent().css({
			"transform" : "translateY(100%)"
		});
		$(this).parent().parent().prev().css({
			"transform" : "translateY(0)"
		})
	})
	// 地理视图1
	$(".moreCharts>span", $(".swiper-slide:eq(2)")).click(function() {
		if (allAjaxCheck == true) {
			allAjaxCheck = false;
			$.ajax({
				type : "get",
				url : 'data/hour_data.json',
				async : true,
				success : function(data) {
					STATUSMap1(data);
					allAjaxCheck = true;
				},
				error : function() {
					alert('请求失败');
					allAjaxCheck = true;
				}
			})
		}
	})
	//地理视图2
	/*		$.ajax({
				type : "get",
				url : 'data/hour_data.json',
				async : true,
				success : function(data) {
					STATUSMap2(data);
					allAjaxCheck = true;
				},
				error : function() {
					alert('请求失败');
					allAjaxCheck = true;
				}
			})
		}
	})*/
	// 调取缩略图生成方法
	smChartsBuild();
	// 缩略图点击事件
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
							console.log(data)
							STATUSBar1(data);
							statusArr = [];
							for (var i = 0; i < data.timeSlot.length; i++) {
								statusArr.push(data.timeSlot[i]["status"])
							}
							STATUSBar2(data);
							STATUSVbar1(data);
							STATUSVbar2(data);
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
// STATUS竖向柱状图1
function STATUSBar1(data) {
	barChart = echarts
			.init(document.getElementsByClassName("echarts_bar1")[0]);
	var data = data.timeSlot;
	var option = {
		title : {
			text : '状态码统计图表',
			show:false,
			left : 'center',
			textStyle : {
				color : '#737373'
			}
		},
		color:['#87abdd'],
		tooltip : {
			trigger : 'axis',
			axisPointer : { // 坐标轴指示器，坐标轴触发有效
				type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		grid : {
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		xAxis : [ {
			type : 'category',
			name:'页面状态分类',
			data : data.map(function(item) {
				return item['status'].toString();
			}),
			axisTick : {
				alignWithLabel : true
			},
			axisLine : {
				lineStyle : {
					color : '#737373'
				}
			}
		} ],
		yAxis : [ {
			type : 'value',
			name:'页面状态出现次数',
			axisLine : {
				lineStyle : {
					color : '#737373'
				}
			}
		}, ],
		series : [ {
			name : '出现次数',
			type : 'bar',
			barWidth : '60%',
			data : data.map(function(item) {
				return item['statusNum'];
			}),
		} ]
	};
	barChart.setOption(option);
	addLoadEvent(barChart.resize);
	// 根据数据量循环创建tr、td
	$("tr", $(".dataTable").eq(0)).not($("tr:eq(0)", $(".dataTable").eq(0)))
			.remove()
	for (var i = 0; i < data.length; i++) {
		var tr = $("<tr><td>" + (i + 1) + "</td><td>" + data[i].status
				+ "</td><td>" + data[i].statusNum + "</td></tr>");
		$(".dataTable").eq(0).append(tr);
	}
}
// STATUS竖向柱状图2
function STATUSBar2(data) {
	barChart = echarts
			.init(document.getElementsByClassName("echarts_bar2")[0]);
	var dataArr = [];
	console.log(data)
	var data = data.allStatusType;
	console.log(data)
	for (var i in data) {
		dataArr.push(data[i])
	}
	console.log(dataArr)
	var option = {
		color:['#87abdd'],
		title : {
			text : '页面状态时间分布表',
			show:false,
			left : 'center',
			textStyle : {
				color : '#737373'
			}
		},
		tooltip : {
			trigger : 'axis',
			axisPointer : { // 坐标轴指示器，坐标轴触发有效
				type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		grid : {
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		legend : {
			data : statusArr.map(function(item) {
				return item.toString();
			}),
			left : '5%',
    		top:'6%',
			icon : 'roundRect',
			textStyle : {
				color : '#737373'
			},
			selectedMode : 'single'
		},
		xAxis : [ {
			type : 'category',
			data : dataArr[0].map(function(item) {
				return item['time']
			}),
			axisTick : {
				alignWithLabel : true
			},
			axisLine : {
				lineStyle : {
					color : '#737373'
				}
			}
		} ],
		yAxis : [ {
			type : 'value',
			name:'该状态出现次数',
			axisLine : {
				lineStyle : {
					color : '#737373'
				}
			}
		}, ],
		series : function() {
			var serie = [];
			console.log(statusArr)
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
	// 根据数据量循环创建tr、td
	$("tr", $(".dataTable").eq(1)).not($("tr:eq(0)", $(".dataTable").eq(1)))
			.remove()
	var tableArr = [];
	for ( var i in data) {
		tableArr.push(i)
	}
	for (var i = 0; i < tableArr.length; i++) {
		var tr = $("<tr><td>" + tableArr[0] + "</td><td>"
				+ data[tableArr[i]][i].time + "</td><td>"
				+ data[tableArr[i]][i].pvNum + "</td></tr>");
		$(".dataTable").eq(1).append(tr);
	}
	barChart.on("legendselectchanged", function(params) {
		$(".dataTable").eq(1).empty();
		$(".dataTable").eq(1).append(
				$("<tr><th>状态码</th><th>时间</th><th>次数</th></tr>"));
		for (var i = 0; i < data[params.name].length; i++) {
			var tr = $("<tr><td>" + params.name + "</td><td>"
					+ data[params.name][i].time + "</td><td>"
					+ data[params.name][i].pvNum + "</td></tr>");
			$(".dataTable").eq(1).append(tr);
		}
	})
}
// STATUS横向条形图1
function STATUSVbar1(data) {
	$('.echarts_Vbar1').css("width","100%");
	$('.echarts_lBar').css({"width":"0%","height":"100%"});
	$('.echarts_lBar').empty();
	var startTime=data.startTime;
	var endTime=data.endTime;
	var dataArr = [];
	var data = data.urlStatusType;
	for ( var i in data) {
		dataArr.push(data[i])
	}
	if(dataArr.length==0){
		var arr=[];
		var obj={
				"url":"",
				"statusNum":0
		}
		arr.push(obj)
		dataArr.push(arr)
	}
	var statusListNum = dataArr[0].length;
	var vBarChart = echarts.init(document
			.getElementsByClassName("echarts_Vbar1")[0]);
	var option = {
		title : {
			text : '访问状态URL排名统计表',
			show:false,
			subtext : 'BAR DEMO',
			left : 'center',
			textStyle : {
				color : "#737373"
			}
		},
		color:['#87abdd'],
		tooltip : {
			trigger : 'axis',
			axisPointer : {
				type : 'shadow'
			}
		},
		legend : {
			data : statusArr.map(function(item) {
				return item.toString();
			}),
			left : '5%',
			top : '0%',
			width : '30%',
			textStyle : {
				color : '#737373'
			},
			selectedMode : 'single'
		},
		dataZoom : [ {
			type : 'slider',
			show : true,
			yAxisIndex : [ 0 ],
			top : '9.7%',
			left : '93%',
			start : 50,
			end : 100,
			textStyle : {
				color : "#737373"
			}
		} ],
		grid : {
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		xAxis : {
			type : 'value',
			boundaryGap : [ 0, 0.01 ],
			axisLine : {
				lineStyle : {
					color : '#737373'
				}
			}
		},
		yAxis : {
			type : 'category',
			name:'产生该状态的URL排名',
			data : dataArr[0].map(function(item) {
				return statusListNum--;
			}),
			axisLine : {
				lineStyle : {
					color : '#737373'
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
	$("tr",$(".dataTable").eq(2)).not($("tr:eq(0)",$(".dataTable").eq(2))).remove()
	for(var i=0;i<dataArr[0].length;i++){
		var tr=$("<tr><td>"+statusArr[0]+"</td><td>"+dataArr[0][i].url+"</td><td>"+dataArr[0][i].statusNum+"</td></tr>");
		$(".dataTable").eq(2).append(tr);
	}
	vBarChart.on('click', function(params) {
		if (allAjaxCheck == true) {
			allAjaxCheck = false;
			var thisDom = this._dom;
			$('.echarts_Vbar1').css("width", "40%")
			$('.echarts_lBar').css({
				"width" : "59%",
				"height" : "100%"
			})
			vBarChart = echarts.init(this._dom);
			// －－ＡＪＡＸ开始－－
			var url = data[params.seriesName][params.dataIndex].url;

			var status = params.seriesName;
			getStatusByUrl(status, url, startTime, endTime)

			// －－ＡＪＡＸ结束，回调开始－－
			vBarChart.setOption(option)
			addLoadEvent(vBarChart.resize);
			vBarChart.on('click', function(params) {
				if (allAjaxCheck == true) {
					allAjaxCheck = false;
					var url = data[params.seriesName][params.dataIndex].url;
					var status = params.seriesName;
					getStatusByUrl(status, url, startTime, endTime)
				}
			});
		}
	});
	vBarChart.on("legendselectchanged", function(params) {
		$(".dataTable").eq(2).empty();
		$(".dataTable").eq(2).append($("<colgroup><col style='width: 10%' /><col style='width: 65%'/><col style='width: 20%' /></colgroup><tr><th>状态码</th><th>访问请求路径</th><th>出现次数(次)</th></tr>"));
		for(var i=0;i<data[params.name].length;i++){
			var tr=$("<tr><td>"+params.name+"</td><td>"+data[params.name][i].url+"</td><td>"+data[params.name][i].statusNum+"</td></tr>");
			$(".dataTable").eq(2).append(tr);
		}
		var statusListNum=data[params.name].length;
		option.yAxis.data=data[params.name].map(function(item){
			return statusListNum--
		})
		vBarChart.setOption(option);
	})
}

function getStatusByUrl(status, url, startTime, endTime) {
	if (startTime.toString().length == 6) {
		$.ajax({
			type : "get",
			url : btnUrl + "?cmd=WEL:GETINFOBYSTATUSMONTH",
			data : {
				status : status,
				url : url,
				startTime : startTime,
				endTime : endTime,
				project_id : projectID
			},
			async : true,
			cache : true,
			dataType : "json", // 返回json格式
			success : function(data) {
				STATUSBar_l1(data, url);
				allAjaxCheck = true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck = true;
			}
		})
	} else if (startTime.toString().length == 8) {
		$.ajax({
			type : "get",
			url : btnUrl + "?cmd=WEL:GETINFOBYSTATUSDAILY",
			data : {
				status : status,
				url : url,
				startTime : startTime,
				endTime : endTime,
				project_id : projectID
			},
			async : true,
			cache : true,
			dataType : "json", // 返回json格式
			success : function(data) {
				STATUSBar_l1(data, url);
				allAjaxCheck = true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck = true;
			}
		})
	} else if (startTime.toString().length == 10) {
		$.ajax({
			type : "get",
			url : btnUrl + "?cmd=WEL:GETINFOBYSTATUSHOUR",
			data : {
				status : status,
				url : url,
				startTime : startTime,
				endTime : endTime,
				project_id : projectID
			},
			async : true,
			cache : true,
			dataType : "json", // 返回json格式
			success : function(data) {
				STATUSBar_l1(data, url);
				allAjaxCheck = true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck = true;
			}
		})
	} else if (startTime.toString().length == 12) {
		$.ajax({
			type : "get",
			url : btnUrl + "?cmd=WEL:GETINFOBYSTATUSMINUTE",
			data : {
				status : status,
				url : url,
				startTime : startTime,
				endTime : endTime,
				project_id : projectID
			},
			async : true,
			cache : true,
			dataType : "json", // 返回json格式
			success : function(data) {
				STATUSBar_l1(data, url);
				allAjaxCheck = true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck = true;
			}
		})
	}
}
function getStatusByIp(status, url, startTime, endTime) {
	if (startTime.toString().length == 6) {
		$.ajax({
			type : "get",
			url : btnUrl + "?cmd=WEL:GETIPBYSTATUSMONTH",
			data : {
				status : status,
				ip : url,
				startTime : startTime,
				endTime : endTime,
				project_id : projectID
			},
			async : true,
			cache : true,
			dataType : "json", // 返回json格式
			success : function(data) {
				STATUSBar_l2(data, url);
				allAjaxCheck = true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck = true;
			}
		})
	} else if (startTime.toString().length == 8) {
		$.ajax({
			type : "get",
			url : btnUrl + "?cmd=WEL:GETIPBYSTATUSDAILY",
			data : {
				status : status,
				ip : url,
				startTime : startTime,
				endTime : endTime,
				project_id : projectID
			},
			async : true,
			cache : true,
			dataType : "json", // 返回json格式
			success : function(data) {
				STATUSBar_l2(data, url);
				allAjaxCheck = true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck = true;
			}
		})
	} else if (startTime.toString().length == 10) {
		$.ajax({
			type : "get",
			url : btnUrl + "?cmd=WEL:GETIPBYSTATUSHOUR",
			data : {
				status : status,
				ip : url,
				startTime : startTime,
				endTime : endTime,
				project_id : projectID
			},
			async : true,
			cache : true,
			dataType : "json", // 返回json格式
			success : function(data) {
				STATUSBar_l2(data, url);
				allAjaxCheck = true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck = true;
			}
		})
	} else if (startTime.toString().length == 12) {
		$.ajax({
			type : "get",
			url : btnUrl + "?cmd=WEL:GETIPBYSTATUSMINUTE",
			data : {
				status : status,
				ip : url,
				startTime : startTime,
				endTime : endTime,
				project_id : projectID
			},
			async : true,
			cache : true,
			dataType : "json", // 返回json格式
			success : function(data) {
				STATUSBar_l2(data, url);
				allAjaxCheck = true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck = true;
			}
		})
	}
}
// STATUS辅助竖状条形图1
function STATUSBar_l1(data, title) {
	lBarChart = echarts.init(document
			.getElementsByClassName('echarts_lBar')[0]);
	var option = {
		color : [ 'rgb(86,144,242)' ],
		tooltip : {
			trigger : 'axis',
			axisPointer : { // 坐标轴指示器，坐标轴触发有效
				type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		grid : {
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		xAxis : [ {
			type : 'category',
			data : data.everyUrlStatus.map(function(item) {
				return item.time
			}),
			axisTick : {
				alignWithLabel : true
			},
			axisLine : {
				lineStyle : {
					color : '#737373'
				}
			}
		} ],
		yAxis : [ {
			type : 'value',
			axisLine : {
				lineStyle : {
					color : '#737373'
				}
			}
		}, ],
		series : [ {
			name : '直接访问',
			type : 'bar',
			barWidth : '60%',
			data : data.everyUrlStatus.map(function(item) {
				return item.statusNum
			}),
		} ]
	};
	lBarChart.setOption(option);
	addLoadEvent(lBarChart.resize);
}
//STATUS横向条形图2
function STATUSVbar2(data){
	$('.echarts_Vbar2').css("width","100%");
	$('.echarts_lBar').css({"width":"0%","height":"100%"});
	$('.echarts_lBar').empty();
	var startTime=data.startTime;
	var endTime=data.endTime;
	var statusListNum=1;
	var dataArr=[];
	var data=data.IpStatusType;
	for(var i in data){
		dataArr.push(data[i])
	}
	if (dataArr.length == 0) {
		dataArr.push([]);
		dataArr[0].ip = "";
		dataArr[0].statusNum = 0;
	}
	vBarChart = echarts.init(document
			.getElementsByClassName("echarts_Vbar2")[0]);
	var option = {
		title : {
			show:false,
			text : '访问状态IP排名统计表',
			subtext : 'BAR DEMO',
			left : 'center',
			textStyle : {
				color : "#737373"
			}
		},
		color:['#87abdd'],
		tooltip : {
			trigger : 'axis',
			axisPointer : {
				type : 'shadow'
			}
		},
		legend : {
			data : statusArr.map(function(item) {
				return item.toString();
			}),
			left : '5%',
			top : '0%',
			textStyle : {
				color : '#737373'
			},
			selectedMode : 'single'
		},
		dataZoom : [ {
			type : 'slider',
			show : true,
			yAxisIndex : [ 0 ],
            right: '0',
            start: 80,
			end : 100,
			textStyle : {
				color : "#737373"
			}
		} ],
		grid : {
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		xAxis : {
			type : 'value',
			boundaryGap : [ 0, 0.01 ],
			axisLine : {
				lineStyle : {
					color : '#737373'
				}
			}
		},
		yAxis : {
			type : 'category',
			name:'产生该状态的IP排名',
			data : dataArr[0].map(function(item) {
				return item['ip'];
			}),
			axisLine : {
				lineStyle : {
					color : '#737373'
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
	$("tr",$(".dataTable").eq(3)).not($("tr:eq(0)",$(".dataTable").eq(3))).remove()
	for(var i=0;i<dataArr[0].length;i++){
		var tr=$("<tr><td>"+statusArr[0]+"</td><td>"+dataArr[0][i].ip+"</td><td>"+dataArr[0][i].statusNum+"</td></tr>");
		$(".dataTable").eq(3).append(tr);
	}
	vBarChart.on('click', function(params) {
		if (allAjaxCheck == true) {
			allAjaxCheck == false;
			var thisDom = this._dom;
			$('.echarts_Vbar2').css("width", "40%")
			$('.echarts_lBar').css({
				"width" : "59%",
				"height" : "100%"
			})
			vBarChart = echarts.init(this._dom);
			// －－ＡＪＡＸ开始－－
			var url = data[params.seriesName][params.dataIndex].ip;

			var status = params.seriesName;
			getStatusByIp(status, url, startTime, endTime)
			addLoadEvent(vBarChart.resize);
			// －－ＡＪＡＸ结束，回调开始－－

			vBarChart.setOption(option)

			// STATUSBar_l1(data,url);

			vBarChart.on('click', function(params) {
				if (allAjaxCheck == true) {
					allAjaxCheck = false;
					var url = data[params.seriesName][params.dataIndex].ip;
					var status = params.seriesName;
					getStatusByIp(status, url, startTime, endTime)
				}
			});
		}
	});
	vBarChart.on("legendselectchanged", function(params) {
		$(".dataTable").eq(3).empty();
		$(".dataTable").eq(3).append($("<colgroup><col style='width: 10%' /><col style='width: 65%'/><col style='width: 20%' /></colgroup><tr><th>状态码</th><th>访问请求路径</th><th>出现次数(次)</th></tr>"));
		for(var i=0;i<data[params.name].length;i++){
			var tr=$("<tr><td>"+params.name+"</td><td>"+data[params.name][i].ip+"</td><td>"+data[params.name][i].statusNum+"</td></tr>");
			$(".dataTable").eq(3).append(tr);
		}
		option.yAxis.data=data[params.name].map(function(item){
			return item["ip"];
		})
		vBarChart.setOption(option);
	})
}
// STATUS辅助竖状条形图2
function STATUSBar_l2(data, title) {
	lBarChart = echarts.init(document
			.getElementsByClassName('echarts_lBar')[1]);
	var option = {
		color:['#87abdd'],
		tooltip : {
			trigger : 'axis',
			axisPointer : { // 坐标轴指示器，坐标轴触发有效
				type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		grid : {
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		xAxis : [ {
			type : 'category',
			data : data.everyIpStatus.map(function(item) {
				return item.time
			}),
			axisTick : {
				alignWithLabel : true
			},
			axisLine : {
				lineStyle : {
					color : '#737373'
				}
			}
		} ],
		yAxis : [ {
			type : 'value',
			axisLine : {
				lineStyle : {
					color : '#737373'
				}
			}
		}, ],
		series : [ {
			name : '直接访问',
			type : 'bar',
			barWidth : '60%',
			data : data.everyIpStatus.map(function(item) {
				return item.statusNum
			}),
		} ]
	};
	lBarChart.setOption(option);
	addLoadEvent(lBarChart.resize);
}
// STATUS地图2
function STATUSMap2(data) {
	mapChart2 = echarts
			.init(document.getElementsByClassName("echarts_map")[1]);
	function randomData() {
		return Math.round(Math.random() * 1000);
	}
	var option = {
		title : {
			show:false,
			text : title,
			subtext : 'MAP DEMO',
			left : 'center',
			textStyle : {
				color : "#737373"
			}
		},
		color : [ '#5fb7e5', '#11ffe5', '#ffa800' ],
		tooltip : {
			trigger : 'item'
		},
		visualMap : {
			min : 0,
			max : 2500,
			left : '5%',
			top : 'bottom',
			calculable : true,
			inRange : {
	            color: ['#dff6ff', '#0080b5']
			},
			textStyle : {
				color : "#737373"
			}
		},
		toolbox : {
			show : true,
			right : "5%",
			iconStyle : {
				normal : {
					borderColor : '#737373'
				},
				emphasis : {
	    			borderColor:'#737373'
				}
			},
			orient : 'vertical',
			top : 'center',
			feature : {
				restore : {},
				saveAsImage : {}
			}
		},
		series : [ {
			name : '访问次数',
			type : 'map',
			mapType : 'china',
			roam : false,
			label : {
				normal : {
					show : true,
					textStyle : {
						color : '#999'
					}
				},
				emphasis : {
					show : true,
					textStyle : {
						color : '#000'
					}
				}
			},
			itemStyle : {
				normal : {
					areaColor : '#737373',
					borderColor : '#111'
				},
				emphasis : {
					areaColor : '#3BFFFF',
					borderColor : '#737373',
					borderWidth : 1
				}
			},
			data : [ {
				name : '北京',
				value : randomData()
			}, {
				name : '天津',
				value : randomData()
			}, {
				name : '上海',
				value : randomData()
			}, {
				name : '重庆',
				value : randomData()
			}, {
				name : '河北',
				value : randomData()
			}, {
				name : '河南',
				value : randomData()
			}, {
				name : '云南',
				value : randomData()
			}, {
				name : '辽宁',
				value : randomData()
			}, {
				name : '黑龙江',
				value : randomData()
			}, {
				name : '湖南',
				value : randomData()
			}, {
				name : '安徽',
				value : randomData()
			}, {
				name : '山东',
				value : randomData()
			}, {
				name : '新疆',
				value : randomData()
			}, {
				name : '江苏',
				value : randomData()
			}, {
				name : '浙江',
				value : randomData()
			}, {
				name : '江西',
				value : randomData()
			}, {
				name : '湖北',
				value : randomData()
			}, {
				name : '广西',
				value : randomData()
			}, {
				name : '甘肃',
				value : randomData()
			}, {
				name : '山西',
				value : randomData()
			}, {
				name : '内蒙古',
				value : randomData()
			}, {
				name : '陕西',
				value : randomData()
			}, {
				name : '吉林',
				value : randomData()
			}, {
				name : '福建',
				value : randomData()
			}, {
				name : '贵州',
				value : randomData()
			}, {
				name : '广东',
				value : randomData()
			}, {
				name : '青海',
				value : randomData()
			}, {
				name : '西藏',
				value : randomData()
			}, {
				name : '四川',
				value : randomData()
			}, {
				name : '宁夏',
				value : randomData()
			}, {
				name : '海南',
				value : randomData()
			}, {
				name : '台湾',
				value : randomData()
			}, {
				name : '香港',
				value : randomData()
			}, {
				name : '澳门',
				value : randomData()
			} ]
		}]
	}
	mapChart2.setOption(option)
	addLoadEvent(mapChart2.resize);
};