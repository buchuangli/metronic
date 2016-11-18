require.config({
	baseUrl:"./lib",
	paths:{
		jquery:"jquery",
		backbone:"backbone",
		underscore:"underscore",
		text:"text",
		lazyLoad:"loadimg",
		echarts:"echarts",
		swiper:"swiper",
		china:"china",
		css:'css',
		open01:"../open01Charts/open01",
		pvCharts:"../open01Charts/pvCharts",
		ipCharts:"../open01Charts/ipCharts",
		dataCharts:"../open01Charts/dataCharts",
		statusCharts:"../open01Charts/statusCharts",
		brCharts:"../open01Charts/brCharts",
		osCharts:"../open01Charts/osCharts",
		reqCharts:"../open01Charts/requestCharts",
		cvrCharts:"../open01Charts/cvrCharts",
		tpCharts:"../open01Charts/tpCharts",
		rfCharts:"../open01Charts/rfCharts",
		laydate:"laydate",
		homeCharts:"../mod/homeCharts",
		serchCharts:"../mod/serchCharts",
		bootstrap:"bootstrap.min",
		datatable:"jquery.dataTables",
		bootswitch:"../assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min",
		maxlength:"../assets/global/plugins/bootstrap-maxlength/bootstrap-maxlength.min",
		bootstrapselect:"../assets/global/plugins/bootstrap-select/js/bootstrap-select.min",
		jqueryForm:"jquery.form",
		jqueryUi:"../assets/global/plugins/jquery-ui/jquery-ui.min",
		moment:"../assets/global/plugins/moment.min",
		dateRangePicker:"../assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min",
		sweetAlert:"../assets/global/plugins/bootstrap-sweetalert/sweetalert.min",
		jqueryForm:"jquery.form",
		jqueryUi:"../assets/global/plugins/jquery-ui/jquery-ui.min",
		icheck:"../assets/global/plugins/icheck/icheck.min",
		counterup:"../assets/global/plugins/counterup/jquery.counterup.min",
		waypoints:"../assets/global/plugins/counterup/jquery.waypoints.min",
		dropzone:"../assets/global/plugins/dropzone/dropzone.min"
	}
})
require(["jquery","../router","backbone","echarts"],function($){
	Backbone.history.start();
	
	$.ajax({
		async : true,
		cache : false,
		type : "post",
		url : "/open01/options.open",
		data : {},
		success : function(data) { // 请求成功后处理函数。
			$("#moreBtn").css("background-image","url("+data.image+")")
			$(".moreNav_img").css("background-image","url("+data.image+")")
			$(".moreNav_name").html(data.name)
			userID=data.client_id;
		},
		error : function() {// 请求失败处理函数
			alert('请求失败');
		}
	});
	
	
	//查询数据总计流量和使用流量
	$.ajax({ 
		async: false,
		cache: true,
		type: "post", 
		url: "/open01/dosage.open?cmd=WEL:DATASIZE",  
		success: function(data) { 
			$(".moreNav_dosage span").html(data.usedSize+"MB")
		/*	$("#totol_data_size").text(data.totalSize+"MB");
			$("#used_data_size").text(data.totalSize-data.usedSize+"MB");
			$("#dosageBar_blue").css("width",((data.totalSize-data.usedSize)/data.totalSize)*100+"%")*/
		},
		error: function() { //请求失败处理函数  
			alert('查询数据流量失败');
		}
	});
	
	
	$("body").delegate("#pvSerch","click",function(){
		skipPage='PV'
	})
	$("body").delegate("#tpSerch","click",function(){
		skipPage='TP'
	})
	$("body").delegate("#rfSerch","click",function(){
		skipPage='REFERRER'
	})

	$("body").delegate("#ipSerch","click",function(){
		skipPage='IP'
	})
	$("body").delegate("#osSerch","click",function(){
		skipPage='OS'
	})
	$("body").delegate("#brSerch","click",function(){
		skipPage='BR'
	})

	$("body").delegate("#cvrSerch","click",function(){
		skipPage='CVR'
	})

	$("body").delegate("#statusSerch","click",function(){
		skipPage='STATUS'
	})
	$("body").delegate("#trsSerch","click",function(){
		skipPage='DATA'
	})
	$("body").delegate("#gpSerch","click",function(){
		skipPage='REQUEST'
	})
	//“更多”按钮
	var moreBtnMark=0;
		$("#moreBtn").click(function(){
			event.stopPropagation();
			if(moreBtnMark==1){
				$("#moreBtn").removeAttr("class");
				$(".moreNav").animate({"opacity":"0"},167,function(){
					moreBtnMark=0;
					$(".moreNav").css("display","none");
				})
			}else{
				$("#moreBtn").attr("class","navLiSelected");
				$(".moreNav").css("display","block");
				$(".moreNav").animate({"opacity":"1"},167,function(){
					moreBtnMark=1;
				})
			}
		})
		$("body").not("#moreBtn").click(function(){
			$("#moreBtn").removeAttr("class");
			$(".moreNav").animate({"opacity":"0"},167,function(){
				moreBtnMark=0;
				$(".moreNav").css("display","none");
			})
		})
	//切页
		function changePage(){
			$(".page-sidebar-menu>.nav-item").removeClass("active");
			var url=location.hash;
			switch (url){
				case "#/home":
				$(".page-sidebar-menu>.nav-item").eq(0).addClass("active");
				break;
				case "#/dosage":
				$(".page-sidebar-menu>.nav-item").eq(5).addClass("active");
				break;
				case "#/upload":
				$(".page-sidebar-menu>.nav-item").eq(5).addClass("active");
				break;
				case "#/manage":
				$(".page-sidebar-menu>.nav-item").eq(6).addClass("active");
				break;
				case "#/analysis":
				break;
				case "#/serch":
				$(".page-sidebar-menu>.nav-item").eq(7).addClass("active");
				break;
				case "#/help":
				$(".page-sidebar-menu>.nav-item").eq(9).addClass("active");
				break;
				case "#/options":
				break;
				case "#/recycle":
				$(".page-sidebar-menu>.nav-item").eq(8).addClass("active");
				break;
				default:
					$(".page-sidebar-menu>.nav-item").eq(0).addClass("active");
				break;
			}
			$("body").eq(0).css("min-height","screen.height"-125+"px");
		}
		window.onhashchange=changePage;
		$(document).ready(changePage);
	//更多按钮分页
		$("a:lt(3)",$(".moreNav .nav")).each(function(){
			$(this).click(function(){
				$(".moreNav .nav a").not($(this)).animate({"opacity":"0"},167,function(){
					$(this).css("display","none")
					$(".moreNav .nav a:nth-child(5)").css("display","block")
				})
				$(".moreNav .nav a:nth-child(5)").animate({"opacity":"1"},330)
				$(this).css({"position":"absolute"})
				$("e",$(this)).css({"background-position-y":"-690px"})
				$(this).animate({"left":"0"},167)
			})
		})
	//更多按钮 返回
		$(".moreNav .nav a:nth-child(5)").click(function(){
				$("a:lt(4)",$(".moreNav .nav")).animate({"opacity":"0"},0,function(){
					$(this).css({"position":"relative","left":"","display":"inline-block"})
					$("e",$(this)).css({"background-position-y":"-647px"})
					$(".moreNav .nav a:nth-child(5)").css("display","none")
					$(this).animate({"opacity":"1"},330)
				})
		})
			// windows = (navigator.userAgent.indexOf("Windows",0) != -1)?1:0;
			// mac = (navigator.userAgent.indexOf("mac",0) != -1)?1:0;
			// linux = (navigator.userAgent.indexOf("Linux",0) != -1)?1:0;
			// unix = (navigator.userAgent.indexOf("X11",0) != -1)?1:0;
			// if (windows) os_type = "MS Windows";
			// else if (mac) os_type = "Apple mac";
			// else if (linux) os_type = "Lunix";
			// else if (unix) os_type = "Unix";
			// return os_type;
})
