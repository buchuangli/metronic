var title="页面访问总量统计";
	var btnUrl;
	var pageName;
	var sessionObj={};
	var allData;
	var interval="day";
	var storageArr;
	var skipSwiper=0;
	var skipPage;
	var analysisOnload=true;
	var allAjaxCheck=true;
	var userID;
	var addChartCheck=0;
	var titleStartTime;
	var titleEndTime;
	var titleProID;
  	var os_type;
    var endTime;
    var startTime;
    var cvrUrlData;
    var userID;//客户Id 拿小图用
  	//模拟项目ＩＤ
  	var projectID;
	//文件ID
	var dataID=[];
	var projectID_m="";
	var projectNameList;//首页拿项目名称
	var statusArr=[];
		$.ajax({
			type:"get",
			url:"toppage.open?cmd=WEL:GETPROJECTID",
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				projectID=data.projectID;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
			}
		})
		function upDateProID(){
			$.ajax({
				type:"POST",
				url:"toppage.open?cmd=WEL:UPDATEPROJECTID",
				data:{
					projectID:projectID
				},
				async:true,
				cache : true,
				dataType : "json", //返回json格式
				success:function(data){
					
				}
			})
		}
	/**
	*js中更改日期
	* y年， m月， d日， h小时， n分钟，s秒
	*/
	Date.prototype.add = function (part, value) {
	    value *= 1;
	    if (isNaN(value)) {
	        value = 0;
	    }
	    switch (part) {
	        case "y":
	            this.setFullYear(this.getFullYear() + value);
	            break;
	        case "m":
	            this.setMonth(this.getMonth() + value);
	            break;
	        case "d":
	            this.setDate(this.getDate() + value);
	            break;
	        case "h":
	            this.setHours(this.getHours() + value);
	            break;
	        case "n":
	            this.setMinutes(this.getMinutes() + value);
	            break;
	        case "s":
	            this.setSeconds(this.getSeconds() + value);
	            break;
	        default:
	    }
	}

	//时间设置
	Date.prototype.Format = function(fmt)
	{ //author: meizz
	  var o = {
	    "M+" : this.getMonth()+1,                 //月份
	    "d+" : this.getDate(),                    //日
	    "h+" : this.getHours(),                   //小时
	    "m+" : this.getMinutes(),                 //分
	    "s+" : this.getSeconds(),                 //秒
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度
	    "S"  : this.getMilliseconds()             //毫秒
	  };
	  if(/(y+)/.test(fmt))
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	  for(var k in o)
	    if(new RegExp("("+ k +")").test(fmt))
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
	  return fmt;
	}
	function searchData(url,li){
		time=li.attr('data-time')
	//	if(url !='conversion.open'){
			if(time == 'day'){
				selectHour(time,"","",url);
			}
			if(time == 'week' || time == 'oneMonth'){
				selectDaily(time,"","",url);
			}
			if(time == 'threeMonth' || time == 'sixMonth' || time =='year'){
				selectMonth(time,"","",url);
			}
			if(time == 'twoYear'){
				selectYear(time,"","",url);
			}
	//	}
		
	};
	function selectYear(time,startTime,endTime,url){
		allAjaxCheck=false;
		$.ajax({
			type:"get",
			url:url+"?cmd=WEL:SELECTYEAR",
			data : {
				date :time,
				startTime:startTime,
				endTime:endTime,
				project_id : projectID
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				loadData(data);
				pageNameCheck(data);
				allAjaxCheck=true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck=true;
			}
		})
	}
	function selectMonth(time,startTime,endTime,url){
		allAjaxCheck=false;
		$.ajax({
			type:"get",
			url:url+"?cmd=WEL:SELECTMONTH",
			data : {
				date :time,
				startTime:startTime,
				endTime:endTime,
				project_id : projectID
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				loadData(data);
				pageNameCheck(data);
				allAjaxCheck=true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck=true;
			}
		})
	}
	function selectDaily(time,startTime,endTime,url){
		allAjaxCheck=false;
		$.ajax({
			type:"get",
			url:url+"?cmd=WEL:SELECTDAILY",
			data : {
				date :time,
				startTime:startTime,
				endTime:endTime,
				project_id : projectID
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				loadData(data);
				pageNameCheck(data);
				allAjaxCheck=true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck=true;
			}
		})
	}
	function selectHour(time,startTime,endTime,url){
		allAjaxCheck=false;
		$.ajax({
			type:"get",
			url:url+"?cmd=WEL:SELECTHOUR",
			data : {
				date :time,
				startTime:startTime,
				endTime:endTime,
				project_id : projectID
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				loadData(data);
				pageNameCheck(data);
				allAjaxCheck=true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck=true;
			}
		})
	}
	function selectMinute(time,startTime,endTime,url){
		allAjaxCheck=false;
		$.ajax({
			type:"get",
			url:url+"?cmd=WEL:SELECTMINUTE",
			data : {
				date :time,
				startTime:startTime,
				endTime:endTime,
				project_id : projectID
			},
			async:true,
			cache : true,
			dataType : "json", //返回json格式
			success:function(data){
				loadData(data);
				pageNameCheck(data);
				allAjaxCheck=true;
			},
			error : function() {// 请求失败处理函数
				alert('请求失败');
				allAjaxCheck=true;
			}
		})
	}

  /*Javascript设置要保留的小数位数，四舍五入。
     *ForDight(Dight,How):数值格式化函数，Dight要格式化的 数字，How要保留的小数位数。
     *这里的方法是先乘以10的倍数，然后去掉小数，最后再除以10的倍数。
     */
    function ForDight(Dight,How){
        Dight = Math.round(Dight*Math.pow(10,How))/Math.pow(10,How);
        return parseFloat(Dight);
    }
	function loadData(data){
		allData=data;
		var urlCount=0;
		var ipCount=0;
		var bytesCount=0;
		hideLoading();
	}
function showLoading(){
	console.log("loading")
	if(typeof lineChart!="undefined"){
		lineChart.showLoading();
	}
	if(typeof mapChart!="undefined"){
		mapChart.showLoading();
	}
	if(typeof mapChart2!="undefined"){
		mapChart2.showLoading();
	}
	if(typeof vBarChart!="undefined"){
		vBarChart.showLoading();
	}
	if(typeof lBarChart!="undefined"){
		console.log(typeof lBarChart)
		lBarChart.showLoading();
	}
	if(typeof areaChart!="undefined"){
		areaChart.showLoading();
	}
	if(typeof barChart!="undefined"){
		barChart.showLoading();
	}
	if(typeof funnelChart!="undefined"){
		funnelChart.showLoading();
	}
}
function hideLoading(){
	console.log("hideLoading")
	if((typeof lineChart)!="undefined"){
		lineChart.hideLoading();
	}
	if(typeof mapChart!="undefined"){
		mapChart.hideLoading();
	}
	if(typeof mapChart2!="undefined"){
		mapChart2.hideLoading();
	}
	if(typeof vBarChart!="undefined"){
		vBarChart.hideLoading();
	}
	if(typeof lBarChart!="undefined"){
		lBarChart.hideLoading();
	}
	if(typeof areaChart!="undefined"){
		areaChart.hideLoading();
	}
	if(typeof barChart!="undefined"){
		barChart.hideLoading();
	}
	if(typeof funnelChart!="undefined"){
		funnelChart.hideLoading();
	}
}
function pageNameCheck(data){
	if(pageName=="PV"){
		PVLine(data);
    	PVMap(data);
    	PVvBar(data);
    	PVarea(data);
    	pvRefresh(allData)
	}else if(pageName=="IP"){
		IPLine(data);
		IPMap(data);
		IPvBar(data);
		IParea(data);
		ipRefresh(allData)
	}else if(pageName=="DATA"){
		DATALine(data);
		DATAMap(data);
		DATAvBar(data);
		DATAarea(data);
		dataRefresh(allData)
	}else if(pageName=="STATUS"){
		STATUSBar1(data);
		statusArr=[];
		for(var i=0;i<data.timeSlot.length;i++){
			statusArr.push(data.timeSlot[i]["status"])
		}
		STATUSBar2(data);
		STATUSVbar1(data);
		STATUSVbar2(data);

	}else if(pageName=="BR"){
		BRarea(data);
		BRvBar(data);
	}else if(pageName=="OS"){
		OSarea(data);
		OSvBar(data);
	}else if(pageName=="REQUEST"){
		REQarea(data);
		REQvBar(data);
	}else if(pageName=="REFERRER"){
		RFarea(data);
		RFvBar(data);
	}else if(pageName=="TP"){
		TParea(data);
		TPvBar(data);
	}
	else if(pageName=="CVR"){
		console.log(data)
		cvrUrlData=data.funnelInfo;
		data=data.funnelInfo;
		var cvrValue=[];
    	var cvrUrl=[];
    	var maxCvrValue=1;
    	$(".echarts_funnel").css({"bottom":"0"});
		$(".echarts_more").css({"top":"100%"});
		funnelChart.resize();
		$(".echarts_more").empty();
    	for(var i in data){
    		cvrUrl.push(i);
    		cvrValue.push(parseInt(data[i]));
    	}
		//漏斗图判断与初始化
		if(document.getElementsByClassName("echarts_funnel")[0]){
			var funnelName=[];
			for(var i=1;i<cvrUrl.length;i++){
				var obj={};
				console.log(cvrValue[i]+"|"+cvrValue[i-1])
				obj.value=ForDight((cvrValue[i]/cvrValue[i-1])*100,0);
				console.log(obj.value)
				obj.name="第"+i+"步："+obj.value+"%";
				funnelName.push(obj);
			}
			op01Funnel(funnelName);
		}
	}
}
	//底部日期选择缩略图生成
function smChartsBuild(){
    $(".smChartsImgBox").each(function(){
    	$(this).empty();
      	var figType=$(this).prev().attr('class').split(" ").pop();
      	var imgTime;
		var imgDate=new Date();
  		if(interval=="day"){
			imgDate.add("d",-7);
			for(var i=0;i<7;i++){
				imgDate.add("d",+1);
				imgTime=imgDate.Format("yyyy-MM-dd");
		  		$(this).append($("<li class='smChartsImg'><img src='"+'http://101.200.218.23:8080/NailFIG/'+userID+'/'+projectID+'/'+pageName.toLowerCase()+'/'+figType+'/'+interval+'/'+imgTime+'.png'+"' alt='缩略图加载失败'></img><span>"+imgTime+"</span></li>"))
		  	}
		}else if(interval=="week"){
			if(os_type == "MS Windows"){
				imgDate.add("d",-imgDate.getDay());
			}else{
				imgDate.add("d",-imgDate.getDay()+1);
			}
			imgDate.add("d",-49);
			for(var i=0;i<7;i++){
				imgDate.add("d",+7);
				imgTime=imgDate.Format("yyyy-MM-dd");
		  		$(this).append($("<li class='smChartsImg'><img src='"+'http://101.200.218.23:8080/NailFIG/'+userID+'/'+projectID+'/'+pageName.toLowerCase()+'/'+figType+'/'+interval+'/'+imgTime+'.png'+"' alt='缩略图加载失败'></img><span>"+imgTime+"</span></li>"))
		  	}
		}else if(interval=="oneMonth"){
			imgDate.add("m",-7);
			for(var i=0;i<7;i++){
				imgDate.add("m",+1);
				imgTime=(parseInt(Number(imgDate.Format("yyyy-MM-dd").replace(/-/g,""))/100)+'01').replace(/(.{4})/,"$1-").replace(/(.{7})/,"$1-");
		  		$(this).append($("<li class='smChartsImg'><img src='"+'http://101.200.218.23:8080/NailFIG/'+userID+'/'+projectID+'/'+pageName.toLowerCase()+'/'+figType+'/'+interval+'/'+imgTime+'.png'+"' alt='缩略图加载失败'></img><span>"+imgTime+"</span></li>"))
		  	}
		}else if(interval=="threeMonth"){
			console.log(imgDate.getMonth())
			if(imgDate.getMonth()+1>9){
				imgDate.setMonth(9)
			}else if(imgDate.getMonth()+1>6&&imgDate.getMonth()+1<=9){
				imgDate.setMonth(6)
			}else if(imgDate.getMonth()+1>3&&imgDate.getMonth()+1<=6){
				imgDate.setMonth(3)
			}else if(imgDate.getMonth()+1<3){
				imgDate.setMonth(0)
			}
			console.log(imgDate)
			imgDate.add("m",-21);
			for(var i=0;i<7;i++){
				imgDate.add("m",+3);
				imgTime=(parseInt(Number(imgDate.Format("yyyy-MM-dd").replace(/-/g,""))/100)+'01').replace(/(.{4})/,"$1-").replace(/(.{7})/,"$1-");
		  		$(this).append($("<li class='smChartsImg'><img src='"+'http://101.200.218.23:8080/NailFIG/'+userID+'/'+projectID+'/'+pageName.toLowerCase()+'/'+figType+'/'+interval+'/'+imgTime+'.png'+"' alt='缩略图加载失败'></img><span>"+imgTime+"</span></li>"))
		  	}
		}else if(interval=="sixMonth"){
			if(imgDate.getMonth()+1>6){
				imgDate.setMonth(6)
			}else if(imgDate.getMonth()+1<6){
				imgDate.setMonth(0)
			}
			imgDate.add("m",-42);
			for(var i=0;i<7;i++){
				imgDate.add("m",+6);
				imgTime=(parseInt(Number(imgDate.Format("yyyy-MM-dd").replace(/-/g,""))/100)+'01').replace(/(.{4})/,"$1-").replace(/(.{7})/,"$1-");
				$(this).append($("<li class='smChartsImg'><img src='"+'http://101.200.218.23:8080/NailFIG/'+userID+'/'+projectID+'/'+pageName.toLowerCase()+'/'+figType+'/'+interval+'/'+imgTime+'.png'+"' alt='缩略图加载失败'></img><span>"+imgTime+"</span></li>"))
		  	}
		}else if(interval=="year"){
			imgDate.add("m",-84);
			for(var i=0;i<7;i++){
				imgDate.add("m",+12);
				imgTime=(parseInt(Number(imgDate.Format("yyyy-MM-dd").replace(/-/g,""))/10000)+'0101').replace(/(.{4})/,"$1-").replace(/(.{7})/,"$1-");
		  		$(this).append($("<li class='smChartsImg'><img src='"+'http://101.200.218.23:8080/NailFIG/'+userID+'/'+projectID+'/'+pageName.toLowerCase()+'/'+figType+'/'+interval+'/'+imgTime+'.png'+"' alt='缩略图加载失败'></img><span>"+imgTime+"</span></li>"))
		  	}
		}
		else if(interval=="twoYear"){
			imgDate.add("m",-168);
			for(var i=0;i<7;i++){
				imgDate.add("m",+24);
				imgTime=(parseInt(Number(imgDate.Format("yyyy-MM-dd").replace(/-/g,""))/10000)+'0101').replace(/(.{4})/,"$1-").replace(/(.{7})/,"$1-");
		  		$(this).append($("<li class='smChartsImg'><img src='"+'http://101.200.218.23:8080/NailFIG/'+userID+'/'+projectID+'/'+pageName.toLowerCase()+'/'+figType+'/'+interval+'/'+imgTime+'.png'+"' alt='缩略图加载失败'></img><span>"+imgTime+"</span></li>"))
		  	}
		}
    })
    $(".smChartsImg img").error(function(){
    	$(this).attr("src","img/error.png");
    	$(this).css("width","100%")
    })
}

function addLoadEvent(fn){
    var old = window.onresize;
    if(typeof window.onresize != 'function'){
        window.onresize = fn;
    }else{
        window.onresize = function(){
            old();
            fn();
        }
    }
};
