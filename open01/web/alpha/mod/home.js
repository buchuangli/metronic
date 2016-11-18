define([
	"text!../../tpl/home.html",
	'css!../../css/home.css',
	'css!../../assets/global/plugins/bootstrap-sweetalert/sweetalert.css',
	"homeCharts",
	"open01",
	"jqueryUi",
	"dateRangePicker",
	"moment",
	"sweetAlert"
],function(html){
	var calendar;
	function render(){
		if(localStorage.getItem("indexCharts")!=null&&localStorage.getItem("indexCharts")!=[]){
			storageArr=localStorage.getItem("indexCharts").split(",");
		}else{
			storageArr=[];
		}
		//HTML节点注入section中
		$(".page-content").html(html);
		//<--循环创建图表-->
		if(localStorage.getItem("indexCharts")!=null&&localStorage.getItem("indexCharts")!=[]){
			$(".chartViewBox").empty();
			for(var i=0;i<storageArr.length;i++){
				var chartView=$("<li class='portlet portlet-sortable light col-lg-6 col-xs-12 col-sm-12'>"+
												"<div class='chartViewBlock portlet light bordered'>"+
												"<div class='chartViewTitle portlet-title'>"+
												"<div class='caption'><i class='fa fa-folder-o font-green'>"+
												"</i></div><div class='actions'>"+
												"<a class='btn btn-circle btn-icon-only btn-default shadeBox' href='javascript:;'>"+
												"<i class=' icon-action-redo'></i></a>"+
												"<a class='btn btn-circle btn-icon-only btn-default delChartView' href='javascript:;'>"+
												"<i class='icon-trash'></i></a></div></div><div class='chartView portlet-body'><div class='chartBox viewNum"+i+"'>"+
												"</div></div></div></div></li>");
				$(".chartViewBox").append(chartView);
				ajaxCheck(storageArr[i],i)
			}
			//注册点击删除事件
			$(".delChartView").click(function(){
				var that=$(this);
				swal({
				  title: "Are you sure?",
				  text: "Your will not be able to recover this imaginary file!",
				  type: "warning",
				  showCancelButton: true,
				  confirmButtonClass: "btn-danger",
				  confirmButtonText: "Yes, delete it!",
					cancelButtonText: "No, cancel plx!",
				  closeOnConfirm: false
				},
				function(){
				  swal("Deleted!", "Your imaginary file has been deleted.", "success");
					var delNum=that.parent().parent().parent().parent().index();
					that.parent().parent().parent().parent().remove();
					storageArr.splice(delNum,1);
					console.log(storageArr)
					localStorage.setItem("indexCharts",storageArr)
				});
			});
			//点击切换到分析报表相应页面
			$(".shadeBox").click(function(){
				var skipNum=parseInt($(this).parent().parent().parent().parent().index());
				var skipArr=storageArr[skipNum].split("|");
				skipPage=skipArr[0];
				var skipType=skipArr[1];
				if(skipPage=="PV"||skipPage=="IP"||skipPage=="DATA"){
					if(skipType=="line"){
						skipSwiper=0
					}else if(skipType=="map"){
						skipSwiper=1
					}else if(skipType=="vBar"){
						skipSwiper=2
					}else if(skipType=="area"){
						skipSwiper=3
					}
				}else if(skipPage=="STATUS"){
					if(skipType=="bar1"){
						skipSwiper=0
					}else if(skipType=="bar2"){
						skipSwiper=1
					}else if(skipType=="vBar1"){
						skipSwiper=2
					}else if(skipType=="vBar2"){
						skipSwiper=3
					}
				}else if(skipPage=="BR"||skipPage=="OS"||skipPage=="REQUEST"||skipPage=="REFERRER"||skipPage=="TP"){
					if(skipType=="area"){
						skipSwiper=0
					}else if(skipType=="vBar"){
						skipSwiper=1
					}
				}
				location.href="#/analysis"
			})
			//sortable初始化
			$( "#sortable" ).sortable({
	      revert: 330,
				distance: 50,
				forcePlaceholderSize: true,
				items: "li",
				handle:".chartViewTitle",
				containment: "body",
				update: function(){//首页图表拖拽后保存
					var indexArr=new Array;
					var newStorageArr=new Array;
					$(".chartBox").each(function(){
						indexArr.push(parseInt($(this).attr("class").replace("chartBox viewNum","")))
					})
					for(var i=0;i<indexArr.length;i++){
						newStorageArr.push(storageArr[indexArr[i]])
					}
					console.log(indexArr)
					localStorage.setItem("indexCharts",newStorageArr)
				}
	    });
			$( "#sortable" ).disableSelection();
		}else{
			$(".chartView:last").click(function(){
				location.href="#/analysis"
			})
		}
		function ajaxCheck(e,num){
			if(e.indexOf("PV")!=(-1)){
				$.ajax({
					type:"get",
					url:"data/hour_data.json",
					async:true,
					success:function(data){
						pvChartViewCheck(e,data,num)
					}
				});
			}
			if(e.indexOf("IP")!=(-1)){
				$.ajax({
					type:"get",
					url:"data/hour_data.json",
					async:true,
					success:function(data){
						ipChartViewCheck(e,data,num)
					}
				});
			}
			if(e.indexOf("DATA")!=(-1)){
				$.ajax({
					type:"get",
					url:"data/hour_data.json",
					async:true,
					success:function(data){
						dataChartViewCheck(e,data,num)
					}
				});
			}
			if(e.indexOf("STATUS")!=(-1)){
				$.ajax({
					type:"get",
					url:"data/status.json",
					async:true,
					success:function(data){
						statusChartViewCheck(e,data,num)
					}
				});
			}
			if(e.indexOf("BR")!=(-1)){
				$.ajax({
					type:"get",
					url:"data/browser.json",
					async:true,
					success:function(data){
						brChartViewCheck(e,data,num)
					}
				});
			}
			if(e.indexOf("OS")!=(-1)){
				$.ajax({
					type:"get",
					url:"data/os.json",
					async:true,
					success:function(data){
						osChartViewCheck(e,data,num)
					}
				});
			}
			if(e.indexOf("REQUEST")!=(-1)){
				$.ajax({
					type:"get",
					url:"data/requestType.json",
					async:true,
					success:function(data){
						requestChartViewCheck(e,data,num)
					}
				});
			}
			if(e.indexOf("TP")!=(-1)){
				tpChartViewCheck(e,data,num)
			}
			if(e.indexOf("REFERRER")!=(-1)){
				$.ajax({
					type:"get",
					url:"data/rf.json",
					async:true,
					success:function(data){
						rfChartViewCheck(e,data,num)
					}
				});
			}
		}
		//<----------->
		//<--点击日历获取日期-->
		$("#dateBtn").click(function () {
			event.stopPropagation();
		})
		var dateBtnMark=0;
		$("#dateBtn").click(function(){
			if(dateBtnMark==0){
				$("#datePickerBox").css({"display":"block","opacity":"1"})
				dateBtnMark=1;
			}else{
				$("#datePickerBox").css({"display":"none","opacity":"0"})
				dateBtnMark=0;
			}
		})
		$("body").not("#dateBtn").click(function(){
				if(dateBtnMark==1){
					$("#datePickerBox").css({"display":"none","opacity":"0"})
					dateBtnMark=0;
				}
		})
		//projectSelect
		var projectSelectMark=0;
		$("#projectSelectE").click(function(){
			if(projectSelectMark==0){
				$(".projectSelect").css({"display":"block","opacity":"1"})
				projectSelectMark=1;
			}else{
				$(".projectSelect").css({"display":"none","opacity":"0"})
				projectSelectMark=0;
			}
		})
		$("#projectSelectE").click(function () {
			event.stopPropagation();
		})
		$(".projectSelect").click(function () {
			event.stopPropagation();
		})
		$("body").not("#projectSelectE",".projectSelectBtn").click(function(){
				if(projectSelectMark==1){
					$(".projectSelect").css({"display":"none","opacity":"0"})
					projectSelectMark=0;
				}
		})
		//projectSelectBtn
		$(".projectSelect").delegate("li","click",function(){
			console.log(1)
			$(".projectSelectBtn").css({"border-right-color":"transparent","color":"#737373"});
			$(this).css({"border-right-color":"rgb(121, 127, 148)","color":"rgb(121, 127, 148)"})
		})
		function calendarClick(){
			$("div[gldp-el~='mydate']>div:gt(12)").not($("div[gldp-el~='mydate'] div[class~='selected']")).click(function(){
				calendarRush();
			})
			$("div[gldp-el~='mydate'] div[class~='selected']").click(function(){
				calendarClick()
			})
			$("div[gldp-el~='mydate']>div:lt(6)").click(function(){
				calendarClick()
			})
		}
		function calendarRush(){
			if(allAjaxCheck==true){
				allAjaxCheck=false;
			var month;
			var day;
			switch ($(".titleMonth span").text()){
				case "January":
					month="01";
				break;
				case "February":
					month="02";
				break;
				case "March":
					month="03";
				break;
				case "April":
					month="04";
				break;
				case "May":
					month="05";
				break;
				case "June":
					month="06";
				break;
				case "July":
					month="07";
				break;
				case "August":
					month="08";
				break;
				case "September":
					month="09";
				break;
				case "October":
					month="10";
				break;
				case "November":
					month="11";
				break;
				case "December":
					month="12";
				break;
			}
			if(parseInt($("div[gldp-el~='mydate'] div[class~='selected']").text())<10){
				day="0"+$("div[gldp-el~='mydate'] div[class~='selected']").text()
			}else{
				day=$("div[gldp-el~='mydate'] div[class~='selected']").text()
			}
			calendar=parseInt($(".titleYear span").text()+month+day)
			$.ajax({
			type:"get",
			url:"toppage.open?cmd=WEL:GETTOPLIST",
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			data:{
				date:calendar
			},
			success:function(data){
				if(data.reportType!=""){
					storageArr=data.cList;
					$(".chartViewBox").empty();
			for(var i=0;i<storageArr.length;i++){
				var dataP=data.sList[i];
				var chartView=$("<div class='chartViewBlock'><div class='chartViewTitle'></div><div class='chartView'><div class='chartBox viewNum"+i+"'></div><div class='shadeBox'></div><span class='delChartView'></span></div></div>");
				$(".chartViewBox").append(chartView);
				ajaxCheck(storageArr[i],i,dataP);
			}
			var chartView=$("<div class='chartViewBlock'><div class='chartView'><div class='addChartView'></div></div></div>");
			$(".chartViewBox").append(chartView);
			$(".chartView:last").click(function(){
				location.href="#/analysis"
			})
			//注册点击删除事件
					$(".delChartView").click(function(){
						var delNum=parseInt($(this).prev().prev().attr("class").replace("chartBox viewNum",""));
						$(this).parent().remove();
						storageArr.splice(delNum-localArrNum,1);
						localArrNum++;
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
								alert('删除成功');
							},
							error : function() {// 请求失败处理函数
								alert('请求失败');
							}
						})
						//localStorage.setItem("indexCharts",storageArr)
					})
			$(".shadeBox").click(function(){
				var skipNum=parseInt($(this).prev().attr("class").replace("chartBox viewNum",""));
				var skipArr=storageArr[skipNum].split("|");
				skipPage=skipArr[0];
				var skipType=skipArr[1];
				if(skipPage=="PV"||skipPage=="IP"||skipPage=="DATA"){
					console.log(skipPage+"|"+skipType)
					if(skipType=="line"){
						skipSwiper=0
					}else if(skipType=="map"){
						skipSwiper=1
					}else if(skipType=="vBar"){
						skipSwiper=2
					}else if(skipType=="area"){
						skipSwiper=3
					}
				}else if(skipPage=="STATUS"){
					if(skipType=="bar1"){
						skipSwiper=0
					}else if(skipType=="bar2"){
						skipSwiper=1
					}else if(skipType=="vBar1"){
						skipSwiper=2
					}else if(skipType=="vBar2"){
						skipSwiper=3
					}
				}else if(skipPage=="BR"||skipPage=="OS"||skipPage=="REQUEST"||skipPage=="REFERRER"){
					if(skipType=="area"){
						skipSwiper=0
					}else if(skipType=="vBar"){
						skipSwiper=1
					}
				}
				location.href="#/analysis"
			})

		}else{
			$(".chartView:last").click(function(){
				location.href="#/analysis"
			})
		}
				allAjaxCheck=true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck=true;
			}
		})

			calendarClick();
			}
		}
		calendarClick();
		//<--------------->
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
		 	"alwaysShowCalendars": true,
	    "startDate": new Date(),
	    "endDate": new Date()
		}, function(start, end, label) {
		console.log(new Date().getDate())
		$("#reportrange>span").html(start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD'))
	  console.log('New date range selected: ' + start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')');
		});
	}
	return {
		render:render
	}
})
