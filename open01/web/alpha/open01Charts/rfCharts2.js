function rfBtn(){
	moreDateMark=0;
	allAjaxCheck=false;
	btnUrl="referrer.open";
	pageName="REFERRER";
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
        				<th>访问请求来源</th>\
        				<th>访问次数</th>\
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
				<div class="txtData">\
				<div class="countent">\
				<table border="0" bordercolor="#737373" cellspacing="0" cellpadding="0" class="dataTable">\
				<col style="width: 10%" />\
				<col style="width: 65%" />\
				<col style="width: 20%" />\
				<tr>\
				<th>序号</th>\
				<th>访问请求来源</th>\
				<th>访问请求量(次)</th>\
				</tr>\
				</table>\
				</div>\
				</div>\
    </div>');
    var paginationArr=['TOP50 访问请求来源','TOP50 访问请求来源(占比图)']
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
	$("#rfSerch e").css("border-color","#797f94");
	$("#rfSerch").parent().prev().css("border-color","#797f94");

	//swiper点击后更新内容
	
	//AJAX与echarts插件引用
	$.ajax({
		type:"get",
		url:btnUrl,
		data : {
			project_id : projectID
		},
//		url:'data/rf.json',
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
							RFarea(data);
							RFvBar(data);
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
function RFarea(data){
	areaChart=echarts.init(document.getElementsByClassName("echarts_area")[0]);
	var data1=data["directType"];
	var data=data["indirectType"];
	var dataArr=[];
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
	var data=dataArr;
	var i=data.length-1;
	while(i>0||i==0){
		var tr=$("<tr><td>"+(data.length-i)+"</td><td style='overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'>"+data[i].name+"</td><td>"+data[i].value+"</td></tr>");
		$(".dataTable").append(tr);
		i-=1;
	}
}
//RF横向条形图
function RFvBar(data){
	/*var startTime=data.startTime;
	var endTime=data.endTime;*/
	var rfAllUrl=10;
	var data1=data["directType"];
	var data2=data["indirectType"];
	var data=[];
	$('.echarts_Vbar').css("width","100%");
	$('.echarts_lBar').css({"width":"0%","height":"100%"});
	$('.echarts_lBar').empty();
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
	        show:false,
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
	//根据数据量循环创建tr、td
	$("tr",$(".dataTable").eq(1)).not($("tr:eq(0)",$(".dataTable").eq(1))).remove()
	var i=data.length-1;
	while(i>0||i==0){
		var tr=$("<tr><td>"+(data.length-i)+"</td><td style='overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'>"+data[i].url+"</td><td>"+data[i].urlNum+"</td></tr>");
		$(".dataTable").eq(1).append(tr);
		i-=1;
	}
	vBarChart.on('click', function (params) {
		var thisDom=this._dom;
		var fullUrl=data[Math.abs((params.name.replace("URL",""))-10)].url;
		var url=fullUrl.split("?")[0];
    	$('.echarts_Vbar').css("width","40%")
    	$('.echarts_lBar').css({"width":"59%","height":"100%"})
		vBarChart=echarts.init(this._dom);
		vBarChart.setOption(option)
		getReferrerByUrl(url,startTime,endTime);
		vBarChart.on('click', function (params) {
			var fullUrl=data[Math.abs((params.name.replace("URL",""))-10)].url;
			var url=fullUrl.split("?")[0];
			getReferrerByUrl(url,startTime,endTime);
		})
	});
}

function getReferrerByUrl(url,startTime,endTime){
	var rfUrl;
	var rfStartTime;
	var rfEndTime;
	allAjaxCheck=false;
	/*
	 * 判断选择那种类型表年月日时分
	 */
	$.ajax({
		type:"post",
		url:"analysis.open?cmd=WEL:GETDATERYPE",
		data : {
			startTime :startTime,
			endTime:endTime
		},
		async:true,
		cache : true,
		dataType : "json", //返回json格式
		success:function(data){
			if(data.dateType=='year'){
				rfUrl="YEAR";
				rfStartTime=parseInt(Number(startTime)/100000000)
				rfEndTime=parseInt(Number(endTime)/100000000)
			}else if(data.dateType=='month'){
				rfUrl="MONTH";
				rfStartTime=parseInt(Number(startTime)/1000000)
				rfEndTime=parseInt(Number(endTime)/1000000)
			}else if(data.dateType=='daily'){
				rfUrl="DAILY";
				rfStartTime=parseInt(Number(startTime)/10000)
				rfEndTime=parseInt(Number(endTime)/10000)
			}else if(data.dateType=='hour'){
				rfUrl="HOUR";
				rfStartTime=parseInt(Number(startTime)/100)
				rfEndTime=parseInt(Number(endTime)/100)
			}else if(data.dateType=='minute'){
				rfUrl="MINUTE";
				rfStartTime=parseInt(Number(startTime))
				rfEndTime=parseInt(Number(endTime))
			}
			
			$.ajax({
				type:"get",
				url:btnUrl+"?cmd=WEL:SELECTURLBY"+rfUrl, 
				data : {
					referrer :url,
					startTime:rfStartTime,
					endTime:rfEndTime,
					project_id : projectID
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
	});
}
//RF辅助竖状条形图
function RFBar_l1(data,title){
	console.log(data);
	var data = data.timeSlot;
	lBarChart=echarts.init(document.getElementsByClassName('echarts_lBar')[0]);
	var option = {
		 title: {
	        text: '访问来源统计条形图',
	        show:false,
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
	            	return item.pvNum
	            }),
	        }
	    ]
	};
	lBarChart.setOption(option);
	addLoadEvent(lBarChart.resize);
}