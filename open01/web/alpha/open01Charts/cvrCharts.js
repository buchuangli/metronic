//cvr Button
function cvrBtn(){
	moreDateMark=0;
	var cvrUrlData;
	$(".v-swiper").eq(0).html('<div class="cvrBox swiper-wrapper">\
    	<div class="cvrBox_line2 col-md-12">\
    		<div class="cvrBox_empty">\
    			<p>您还没有设置转化流程</p>\
    			<p>说明：首次使用转化率分析报表 请预先设置转化流程。<a id="optionCvr" style="text-decoration: underline;">设置转化流程</a></p>\
    		</div>\
    	</div>\
    	<!--设置转化流程-->\
    	<div class="cvrBox_line3 col-md-12" style="display: none;">\
    		<div class="countent">\
			<div class="cvrBtnBox">\
			<button class="cvrAddBtn J_btn">\
				添加转化流程\
			</button>\
			<button class="cvrRemoveBtn J_btn">\
				移除转化流程\
			</button>\
			<button class="cvrSaveBtn J_btn">\
				保存转化流程\
			</button>\
			</div>\
        		<table border="0" bordercolor="#737373" cellspacing="0" cellpadding="0" class="cvrDataTable">\
        			<tr>\
        				<th>序号</th>\
        				<th>描述(*必填)</th>\
        				<th>URL(*必填)</th>\
        			</tr>\
        			<tr>\
        				<td>1</td>\
        				<td><input type="text" name="cvrText1" id="cvrText1" value="" placeholder="请输入URL的描述"/></td>\
        				<td><input type="url" name="cvrText1" id="cvrText1" value="" placeholder="请输入想要分析的URL"/></td>\
        			</tr>\
        			<tr>\
        				<td>2</td>\
        				<td><input type="text" name="cvrText1" id="cvrText1" value="" placeholder="请输入URL的描述"/></td>\
        				<td><input type="url" name="cvrText1" id="cvrText1" value="" placeholder="请输入想要分析的URL"/></td>\
        			</tr>\
        		</table>\
    		</div>\
    	</div>\
    	<div class="cvrBox_line4 col-md-12" style="display: none;">\
    		<div class="swiper-slide" style="position:absolute;">\
        		<div class="countent">\
        			<span>总转化率：<span>24%</span></span>\
        			<button class="cvrTime J_btn">时间轨迹</button>\
        			<button class="cvrAddres J_btn">地理分析</button>\
        			<button class="cvrOrigin J_btn">访问来源</button>\
        			<button class="cvrBrowser J_btn">浏览器</button>\
					<button class="cvrOs J_btn">操作系统</button>\
					<button class="cvrBack J_btn"style="float: right;">编辑转化流程</button>\
        		</div>\
            	<div class="swiperB echarts_funnel swiperB_full">\
            	</div>\
            </div>\
    	</div>\
    </div>')
 $(".general").empty();
	//swiper插件配置与初始化
  	var paginationArr=['自定义转化率分析']
	var swiper = new Swiper('.swiper-container', {
			observer : true,
			observeParents:true,
	    pagination: '.swiper-pagination',
			paginationClickable: true,
			paginationBulletRender: function (index, className) {
			    return '<span class="col-md-3 col-xs-3 ' + className+' swiperContainer' + '">' + paginationArr[index] + '</span>';
			}
    });
	// $.ajax({
	//     type:"get",
	//     url:"conversion.open?cmd=WEL:GETCVRINFO",
	//     data : {
	// 		project_id : projectID
	// 	},
	//     async:true,
	//     success:function(data){
	//     	console.log(data);
	//     	if(data.length>0){
	//     		cvrUrlData=data;
	// 	    	var description=[];
	// 	    	var cvrUrl=[];
	// 	    	$(".cvrBox_line2").css("display","none");
	// 	    	$(".cvrBox_line4").css("display","block");
	// 	    	for(var i=0;i<data.length;i++){
	// 	    		cvrUrl.push(data[i].url);
	// 	    		description.push(data[i].desc);
	// 	    	}
	// 	    	//漏斗图判断与初始化
	// 			if(document.getElementsByClassName("echarts_funnel")[0]){
	// 				funnelChart=echarts.init(document.getElementsByClassName("echarts_funnel")[0]);
	// 				var funnelName=[];
	// 				for(var i=0;i<description.length;i++){
	// 					var obj={};
	// 					obj.name=description[i];
	// 					obj.value=parseInt(Math.random()*100);
	// 					funnelName.push(obj);
	// 				}
	// 				op01Funnel(funnelChart,title,funnelName);
	// 			}
	//     	}
	//     	allAjaxCheck=true;
	//     },
	//     error : function() {
	//      alert('获取用户CVR设置失败');
	//      allAjaxCheck=true;
	//     }
	//    })
    //设置转化流程
    $("#optionCvr").click(function(){
    	$(".cvrBox_line2").css("display","none");
    	$(".cvrBox_line3").css("display","block");
    })
    //添加转化流程
    $(".cvrAddBtn").click(function(){
    	if($(".cvrBox_line3 table tr").length<14){
	    	var listNum=$(".cvrBox_line3 table tr:last-child td:nth-child(1)").text();
	    	$(".cvrBox_line3 table tbody").append('<tr>\
            				<td>'+(parseInt(listNum)+1)+'</td>\
            				<td><input type="text" name="cvrText1" id="cvrText1" value="" placeholder="请输入URL的描述"/></td>\
            				<td><input type="url" name="cvrText1" id="cvrText1" value="" placeholder="请输入想要分析的URL"/></td>\
            			</tr>')
    	}
    })
    //移除转化流程
    $(".cvrRemoveBtn").click(function(){
    	if($(".cvrBox_line3 table tr").length>3){
	    	$(".cvrBox_line3 table tbody tr:last-child").remove()
    	}
    })
    //重新编辑转化流程
    $(".cvrBack").click(function(){
    	$(".cvrDataTable tr").not("tr:eq(0)").remove();
    	console.log(cvrUrlData)
    	for(var i=0;i<cvrUrlData.length;i++){
    		$(".cvrDataTable").append('<tr>\
    				<td>'+(parseInt(i)+1)+'</td>\
    				<td><input type="text" name="cvrText1" id="cvrText1" value="'+cvrUrlData[i].desc+'" placeholder="请输入URL的描述"/></td>\
    				<td><input type="url" name="cvrText1" id="cvrText1" value="'+cvrUrlData[i].url+'" placeholder="请输入想要分析的URL"/></td>\
    			</tr>')
    	}
    	$(".cvrBox_line4").css("display","none");
    	$(".cvrBox_line3").css("display","block");
    })
    //保存转化流程
    $(".cvrSaveBtn").click(function(){
    	var description=[];
    	var cvrUrl=[];
    	for(var i=0;i<$(".cvrBox_line3 table tr").length-1;i++){
    		description.push($(".cvrBox_line3 table tr:eq("+(i+1)+") td:nth-child(2) input").val());
    		cvrUrl.push($(".cvrBox_line3 table tr:eq("+(i+1)+") td:nth-child(3) input").val());
    	}
    	function inputCheck(){
    		for(var i=0;i<$(".cvrBox_line3 input").length;i++){
    			if($(".cvrBox_line3 input").eq(i).val()==""){
    				return false;
    			}
    		}
    	}
    	if(inputCheck()!=false){
    		// $.ajax({
    		// 	type:"get",
    		// 	url:"conversion.open?cmd=WEL:INSERTCVRINFO",
    		// 	async:true,
    		// 	cache : true,
    		// 	data:{
    		// 		project_id:projectID,
    		// 		desc:description,
    		// 		url:cvrUrl
    		// 	},
    		// 	dataType : "json", //返回json格式
    		// 	success:function(data){
    		// 		cvrUrlData=data;
    		// 		console.log(data)
    				alert('保存成功')
    		    	$(".cvrBox_line3").css("display","none");
    		    	$(".cvrBox_line4").css("display","block");
    	    		//漏斗图判断与初始化
    				if(document.getElementsByClassName("echarts_funnel")[0]){
    					funnelChart=echarts.init(document.getElementsByClassName("echarts_funnel")[0]);
    					var funnelName=[];
    					for(var i=0;i<description.length;i++){
    						var obj={};
    						obj.name=description[i];
    						obj.value=parseInt(Math.random()*100);
    						funnelName.push(obj);
    					}
    					op01Funnel(funnelChart,title,funnelName);
    				}
    		// 	},
    		// 	error : function() {// 请求失败处理函数
    		// 		alert('请求失败');
    		// 	}
    		// })
    	}else{
    		alert("请完整填写数据,或移除不需要的流程.")
    	}
    })
		//cvrTime click
		$(".cvrTime").click(function(){
			$.ajax({
				type:"get",
				url:"data/cvrTime.json",
				async:true,
    		cache : true,
				// data:{
				// 	project_id:projectID,
				// },
				success:function(data){
					console.log(data);
					cvrLine(data);
				}
			})
		})
}
//漏斗图
function op01Funnel(dom,title,data){
	option = {
	    title : {
            text: title,
            subtext: 'areaDemo',
            left: 'center',
	        textStyle:{
		        color:"#737373"
	        }
        },
		color:['#525e64','#536c7b','#859faa','#bfcad1','#e0ebee'],
	    tooltip: {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c}%"
	    },
	    toolbox: {
	        show:true,
	    	right:"10%",
	    	top:'20%',
	    	iconStyle:{
	    		normal:{
	    			borderColor:'#737373'
	    		},
	    		emphasis:{
	    			borderColor:'#3BFFFF'
	    		}
	    	},
	        orient: 'vertical',
	        feature: {
	            restore: {},
	            saveAsImage: {}
	        }
	    },
	    legend: {
	    	orient: 'vertical',
	    	left:'10%',
	    	top:'20%',
	        data: data.map(function(item){
	        	return item.name;
	        }),
	        textStyle:{
		        color:"#737373"
	        }
	    },
	    series: [
        {
            name:title,
            type:'funnel',
            left: '10%',
            top: 60,
            //x2: 80,
            bottom: 60,
            width: '80%',
            // height: {totalHeight} - y - y2,
            min: 0,
            max: 100,
            minSize: '0%',
            maxSize: '100%',
            sort: 'descending',
            gap: 2,
            label: {
                normal: {
                    show: true,
                    position: 'inside'
                },
                emphasis: {
                    textStyle: {
                        fontSize: 20
                    }
                }
            },
            labelLine: {
                normal: {
                    length: 10,
                    lineStyle: {
                        width: 1,
                        type: 'solid'
                    }
                }
            },
            itemStyle: {
                normal: {
                    borderColor: '#737373',
                    borderWidth: 1
                }
            },
            data: data
        }
    ]
	};
	dom.setOption(option);
	addLoadEvent(dom.resize);
}
//cvrTimeCharts
function cvrLine(data,time){
	lineChart=echarts.init(document.getElementsByClassName("echarts_funnel")[0]);
	var i=0;
	var option={
    	title:{
    		text:'页面访问总量统计图表',
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
    			name:"访问次数",
    			icon:'roundRect'
    		},
    		{
    			name:"独立IP数",
    			icon:'roundRect'
    		}],
    		textStyle:{
    			color:'#737373'
    		}
    	},
    	selected:{
    		'独立IP数':false
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
    		data:data[0].map(function(item){
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
        		saveAsImage:{

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
        	name:"访问次数",
        	type:'line',
        	data:data.map(function(item){
        		return item["pvNum"];
        	}),
        	lineStyle:{
        		normal:{
        			color:'rgba(135,171,221,1)'
        		}
        	},
        	areaStyle: {normal: {}},
        	markLine : {
                data : [
                    {type : 'average', name : '平均值'}
                ],
                silent:true
           	},
        	symbolSize:10,
        	showSymbol:false
        },
        {
        	name:"独立IP数",
        	type:'line',
        	yAxisIndex:1,
        	data:data.map(function(item,index){
        		return item["ipNum"];
        	}),
        	lineStyle: {
                normal: {
                    color: 'rgba(239,162,134,1)'
                }
            },
            areaStyle: {normal: {}},
            markLine : {
                data : [
                    {type : 'average', name : '平均值'}
                ],
                silent:true
           	},
        	symbolSize:10,
        	showSymbol:false
        }
        ]
    }
	lineChart.setOption(option)
	addLoadEvent(lineChart.resize);
}
