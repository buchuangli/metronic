define([
	"text!../../tpl/analysis.html",
	'css!../../css/analysis.css',
	'css!../../css/swiper.css',
	'css!../../css/datatables.bootstrap.css',
	'css!../../css/datatables.min.css',
	'css!../../assets/global/plugins/bootstrap-sweetalert/sweetalert.css',
	"china",
	"open01",
	"pvCharts",
	"ipCharts",
	"dataCharts",
	"statusCharts",
	"brCharts",
	"osCharts",
	"reqCharts",
	"cvrCharts",
	"tpCharts",
	"rfCharts",
	"jqueryUi",
	"dateRangePicker",
	"moment",
	"datatable",
	"sweetAlert",
	"counterup",
	"waypoints",
	"bootstrapselect"
	],function(html){
	function render(){
		//HTML节点注入section中
		$(".page-content").html(html);
		//日期选择
		$("#reportrange>span").html((new Date).Format("yyyy-MM-dd")+" - "+(new Date).Format("yyyy-MM-dd"))
		$('#reportrange').daterangepicker({
				"ranges": {
	        "今日": [
	            new Date(),
	            new Date()
	        ],
	        "昨日": [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
	        "本周": [
	            moment().weekday(1),
	            new Date()
	        ],
	        "本月": [
	            moment().startOf('month'),
	            new Date()
	        ],
	        "本年": [
						moment().startOf('year'),
						new Date()
	        ]
	    },
	    "startDate": new Date().add("d",-1),
	    "endDate": new Date()
		}, function(start, end, label) {
		console.log(new Date().getDate())
		$("#reportrange>span").html(start.format('YYYY-MM-DD-HH-mm') + ' - ' + end.format('YYYY-MM-DD-HH-mm'))
		console.log('New date range selected: ' + start.format('YYYY-MM-DD-HH-mm') + ' to ' + end.format('YYYY-MM-DD-HH-mm') + ' (predefined range: ' + label + ')');
		
		});

		$.ajax({
			type:"get",
			url:"toppage.open?cmd=WEL:SELECTTOPINFO", 
			async:true,
			cache : true,
			dataType : "json", //返回json格式	  
			success:function(data){
				if(data.reportType!=""){
					storageArr=data.reportType.split(",");
					console.log(storageArr)
				}else{
					storageArr=[]
				}
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
			}
		})
		//数据详情
	    $("#dataListBtn").click(function(){
	    	if(moreDateMark===0){
					$(".txtData").css({"-webkit-transform":"translateY(-100%)","-moz-transform":"translateY(-100%)","-ms-transform":"translateY(-100%)","-o-transform":"translateY(-100%)"});
					$(".swiperImg").css({"-webkit-transform":"translateY(-100%)","-moz-transform":"translateY(-100%)","-ms-transform":"translateY(-100%)","-o-transform":"translateY(-100%)"});
			    moreDateMark=1;
	    	}else if(moreDateMark==1){
					$(".swiperImg").css({"-webkit-transform":"translateY(0)"});
					$(".swiperImg").css({"-moz-transform":"translateY(0)"});
					$(".swiperImg").css({"-ms-transform":"translateY(0)"});
	    		$(".swiperImg").css({"-o-transform":"translateY(0)"});
					$(".txtData").css({"-webkit-transform":"translateY(0)"});
					$(".txtData").css({"-moz-transform":"translateY(0)"});
					$(".txtData").css({"-ms-transform":"translateY(0)"});
			    $(".txtData").css({"-o-transform":"translateY(0)"});
			    moreDateMark=0;
	    	}

	    });
	    $("#nav i").click(function(){
			$("#laydate_clear").hide();
		});
	    //选择项目初始化
	    $('＃selectPro').selectpicker({
			width:"100px",
			title:"选择项目",
			size: 10
		});
	    //选择项目
		$.ajax({
			type:"get",
			url:"project.open?cmd=WEL:getProjectByClient", 
			async:true,
			cache : true,
			dataType : "json", //返回json格式	  
			success:function(data){
				console.log(data);
				for(var i =0;i<data.length;i++){
					var	option = $("<option value="+data[i].pId+" data-id='1'>"+data[i].pName+"</option>");
					if(projectID==data[i].pId){
						option.attr("selected",true)
					}
					$("select").append(option);
				}
				
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
			}
		})
	    //HTML节点注入section中
		$("section").html(html);
		$("#selectPro").change(function(){
			var optionIndex=$(this).context.selectedIndex;
			projectID=$("#selectPro option").eq(optionIndex).val();
			console.log(projectID)
			upDateProID();
			window.location.reload();
		})
	    //将所选图表添加到首页
		$("#addChart").click(function(){
			var that=$(this);
			if(allAjaxCheck===true&&addChartCheck==0){
				swal({
				  title: "确认将此图表添加到首页?",
				  text: "添加到首页后您将在首页中看到此图表的实时预览图，点击预览图快速跳转到该图表的分析页面。",
				  type: "info",
				  showCancelButton: true,
				  confirmButtonClass: "btn-success",
				  confirmButtonText: "确认",
					cancelButtonText: "取消",
				  closeOnConfirm: false
				},
				function(){
					var sessionVal;
					if(that.parents(".page-head").next().children(".portlet").children(".swiper-wrapper").children(".swiper-slide-active").children(".swiperImg").children(".swiperB").attr("class").indexOf("echarts_line")!=-1){
						sessionVal=pageName+"|line|"+interval+"|"+projectID;
					}else if(that.parents(".page-head").next().children(".portlet").children(".swiper-wrapper").children(".swiper-slide-active").children(".swiperImg").children(".swiperB").attr("class").indexOf("echarts_map")!=-1){
						sessionVal=pageName+"|map|"+interval+"|"+projectID;
					}else if(that.parents(".page-head").next().children(".portlet").children(".swiper-wrapper").children(".swiper-slide-active").children(".swiperImg").children(".swiperB").children().eq(0).attr("class")){
						if(that.parents(".page-head").next().children(".portlet").children(".swiper-wrapper").children(".swiper-slide-active").children(".swiperImg").children(".swiperB").children().eq(0).attr("class").indexOf("echarts_Vbar")!=-1&&pageName=="STATUS"){
							if(that.parents(".page-head").next().children(".portlet").children(".swiper-wrapper").children(".swiper-slide-active").children(".swiperImg").children(".swiperB").children().eq(0).attr("class").indexOf("echarts_Vbar1")!=-1){
								sessionVal=pageName+"|vBar1|"+interval+"|"+projectID;
							}else if(that.parents(".page-head").next().children(".portlet").children(".swiper-wrapper").children(".swiper-slide-active").children(".swiperImg").children(".swiperB").children().eq(0).attr("class").indexOf("echarts_Vbar2")!=-1){
								sessionVal=pageName+"|vBar2|"+interval+"|"+projectID;
							}
						}else if(that.parents(".page-head").next().children(".portlet").children(".swiper-wrapper").children(".swiper-slide-active").children(".swiperImg").children(".swiperB").children().eq(0).attr("class").indexOf("echarts_Vbar")!=-1){
							sessionVal=pageName+"|vBar|"+interval+"|"+projectID;
						}
					}else if(that.parents(".page-head").next().children(".portlet").children(".swiper-wrapper").children(".swiper-slide-active").children(".swiperImg").children(".swiperB").attr("class").indexOf("echarts_area")!=-1){
						sessionVal=pageName+"|area|"+interval+"|"+projectID;
					}else if(pageName=="STATUS"){
						if(that.parents(".page-head").next().children(".portlet").children(".swiper-wrapper").children(".swiper-slide-active").children(".swiperImg").children(".swiperB").eq(0).attr("class").indexOf("echarts_bar1")!=-1){
							sessionVal=pageName+"|bar1|"+interval+"|"+projectID;
						}else if(that.parents(".page-head").next().children(".portlet").children(".swiper-wrapper").children(".swiper-slide-active").children(".swiperImg").children(".swiperB").eq(0).attr("class").indexOf("echarts_bar2")!=-1){
							sessionVal=pageName+"|bar2|"+interval+"|"+projectID;
						}
					}

					//当值不为空的时候推入数组
					if(sessionVal!==""){
						storageArr.push(sessionVal);
						console.log(storageArr)
						localStorage.setItem("indexCharts",storageArr);
						swal("添加成功!", "图表已被添加到首页。", "success");

							$.ajax({
								type:"post",
								url:"toppage.open",
								data : {
									storageArr :storageArr
								},
								async:true,
								cache : true,
								//dataType : "json", //返回json格式
								success:function(data){
									alert("添加成功")
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
		});
		//<--自定义搜索按钮-->
		
		//<---->
  		analysisOnload=true;
		if(skipPage==""||skipPage==undefined){
			setTimeout(pvBtn,300);
		}

	  //左侧10个按钮
	     $("#pvSerch").click(function(){
	    	if(allAjaxCheck==true){
	    		pvBtn();
	    	}
	     });
	     $("#ipSerch").click(function(){
	    	if(allAjaxCheck==true){
	    		ipBtn();
	    	}
	     });
	     $("#trsSerch").click(function(){
	    	if(allAjaxCheck==true){
	    		dataBtn();
	    	}
	     });
	     $("#statusSerch").click(function(){
	    	if(allAjaxCheck==true){
	    		statusBtn();
	    	}
	     });
	     $("#brSerch").click(function(){
	    	if(allAjaxCheck==true){
	    		brBtn();
	    	}
	     });
	     $("#osSerch").click(function(){
	    	if(allAjaxCheck==true){
	    		osBtn();
	    	}
	     });
	     $("#gpSerch").click(function(){
	    	if(allAjaxCheck==true){
	    		reqBtn();
	    	}
	     });
	     $("#cvrSerch").click(function(){
	    	if(allAjaxCheck==true){
	    		cvrBtn();
	    	}
	     });
	     $("#tpSerch").click(function(){
	    	if(allAjaxCheck==true){
	    		tpBtn();
	    	}
	     });
	     $("#rfSerch").click(function(){
	    	if(allAjaxCheck==true){
	    		rfBtn();
	    	}
	     });

	    (function(){
	    	if(skipPage=="PV"){
	    		pvBtn();
	    	}else if(skipPage=="IP"){
	    		ipBtn();
	    	}else if(skipPage=="DATA"){
	    		dataBtn();
	    	}else if(skipPage=="STATUS"){
	    		statusBtn();
	    	}else if(skipPage=="BR"){
	    		brBtn();
	    	}else if(skipPage=="OS"){
	    		osBtn();
	    	}else if(skipPage=="REQUEST"){
	    		reqBtn();
	    	}else if(skipPage=="REFERRER"){
	    		rfBtn();
	    	}else if(skipPage=="TP"){
	    		tpBtn();
	    	}
	    })();
		window.onresize=function(){
			$("section").css("min-height",document.documentElement.clientHeight-240+"px");
		};
	}
	return {
		render:render
	};
});
