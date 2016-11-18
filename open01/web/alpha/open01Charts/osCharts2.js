
function osBtn(){
	moreDateMark=0;
	allAjaxCheck=false;
	btnUrl="os.open";
	pageName="OS";
	$('.v-swiper').eq(0).html('<div class="swiper-wrapper"><div class="swiper-slide"><div class="swiperImg">\
			<div style="text-align:center;">计入统计的日志数<span>XX</span>条，占全部日志比例<span>xx</span>%<br />说明：未计入统计的日志系因该条日志中未记录操作系统/浏览器信息。您可以通过自行设置服务器日志格式进行调整。</div>\
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
        				<th>操作系统类型</th>\
        				<th>访问请求量(次)</th>\
        			</tr>\
        		</table>\
        		</div>\
        	</div>\
        </div>\
        <div class="swiper-slide">\
        	<div class="swiperImg">\
			<div style="text-align:center;">计入统计的日志数<span>XX</span>条，占全部日志比例<span>xx</span>%<br />说明：未计入统计的日志系因该条日志中未记录操作系统/浏览器信息。您可以通过自行设置服务器日志格式进行调整。</div>\
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
        				<th>操作系统类型</th>\
        				<th>访问请求量(次)</th>\
        			</tr>\
        		</table>\
        		</div>\
        	</div>\
        </div>\
    </div>');
    var paginationArr=['TOP50 访问端操作系统','TOP50 访问端操作系统(占比图)']
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
	$("#osSerch e").css("border-color","#797f94");
	$("#osSerch").parent().prev().css("border-color","#797f94");

	//swiper点击后更新内容
	
	//AJAX与echarts插件引用
	$.ajax({
		type:"get",
		url:btnUrl,
		data : {
			project_id : projectID
		},
//		url:'data/os.json',
		async:true,
		success:function(data){
			loadData(data);
			OSarea(data);
			OSvBar(data);
//			OSMap(data);
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
	 $(".smChartsImgBox").delegate(".smChartsImg img","click",function(){
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
							OSarea(data);
							OSvBar(data);
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
// OS矩形树图（面积图）
function OSarea(data){
	areaChart=echarts.init(document.getElementsByClassName("echarts_area")[0]);
	var data=data.timeSlot;
	var dataArr=[];
	for(var i=0;i<data.length;i++){
		dataArr.push({});
		if(data[i].osType=='others'){
			dataArr[i].name='其他';
		}else{
			dataArr[i].name=data[i].osType;
		}
		dataArr[i].value=data[i].osNum;
	}
	areaChart.setOption(option = {
        title : {
            text: '访问系统面积图',
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
		if(data[i].osType=='others'){
			data[i].osType='其他';
		}
		var tr=$("<tr><td>"+(data.length-i)+"</td><td style='overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'>"+data[i].osType+"</td><td>"+data[i].osNum+"</td></tr>");
		$(".dataTable").eq(0).append(tr);
		i-=1;
	}
}
//OS横向条形图
function OSvBar(data){
	var data=data.timeSlot;
	for(var i=0;i<data.length;i++){
		if(data[i].osType=='others'){
			data[i].osType='其他';
		}
	}
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
}
