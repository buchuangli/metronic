define([
	"text!../../tpl/serch.html",
	"china",
	"open01",
	"serchCharts",
	"datatable"
], function(html) {
	function render() {
		$(".page-content").html(html);
		//AJAX与echarts插件引用
		$('.dateBtn').each(function() {
			var that = $(this);
			$(this).click(function() {
				showLoading();
				searchData(btnUrl, that);
			});
		});

		var table_cont=[];
		$(function() {
				//获取项目及文件ID
			$.ajax({
				async: true,
				cache: true,
				type: "post",
				dataType: "json", //返回json格式
				url: "/open01/project.open",
				data: {},
				success: function(data) { //请求成功后处理函数。

					$.each(data, function(index, values) { // 解析出data对应的Object数组
						var ffi=values.fileid;
						var op = $("<option value='" + values.name + "' data-fileid='" + ffi + "' data-id='"+values.id+"'>" + values.name + "</div>");
						$(".pro_choose").append(op);
					})
					if(projectID_m!=""){
				      $(".pro_choose option[data-id='"+projectID_m+"']").prop("selected",true);
				     }else{
				      $(".pro_choose option:first-of-type").prop("selected",true);
				     }
				},
				error: function() { //请求失败处理函数
					// alert('请求失败');
				}
			});
			//获取热搜词z
			$.ajax({
				async: true,
				cache: false,
				type: "post",
				//dataType: "json", //返回json格式
				url: "/open01/serch.open?cmd=WEL:SELECTTERMS",
				data: {},
				success: function(data) { //请求成功后处理函数。
					console.log(data);
					$.each(data, function() {
						$(".mid_znssH1Span3").append('<span class="keyword" title="'+this+'">' + this + '</span>');
					})
				}
			});
			//显示日期
			function getNowFormatDate() {
				var date = new Date();
				var seperator1 = "-";
				var seperator2 = ":";
				var strMonth = date.getMonth() + 1;
				var strDate = date.getDate();
				var strHour = date.getHours();
				var strMinute = date.getMinutes();
				var strSecond = date.getSeconds();
				if(strMonth >= 1 && strMonth <= 9) {
					strMonth = "0" + strMonth;
				}
				if(strDate >= 0 && strDate <= 9) {
					strDate = "0" + strDate;
				}
				if(strHour >= 0 && strHour <= 9) {
					strHour = "0" + strHour;
				}
				if(strMinute >= 0 && strMinute <= 9) {
					strMinute = "0" + strMinute;
				}
				if(strSecond >= 0 && strSecond <= 9) {
					strSecond = "0" + strSecond;
				}
				var currentdate = date.getFullYear() + seperator1 + strMonth + seperator1 + strDate +
					" " + strHour + seperator2 + strMinute + seperator2 + strSecond;
				return currentdate;
			}

			var ccc = new Date();
			ccc.add("d", -1);
			var ddd = ccc.Format("yyyy-MM-dd hh:mm:ss");

			$("#start").html(ddd);
			$("#end").html(getNowFormatDate());
			//删除input框内的内容,
			$(".searchinfo").on("focus", function() {
				$(".mid_znssHP1 span").css("display", "block");
				$(".mid_znssHP1 span").on("click", function() {
					$(".mid_znssHP1 input").val("");
					$("#append li").remove();
					$("#append").hide();
				});
			});
			//搜索框失去焦点
			$(".searchinfo").on("blur", function() {
				if($(".mid_znssHP1 input").val() === "") {
					$(".mid_znssHP1 span").hide();
				}
			});
			//时间段选择
			$(".mid_znssH1Span span").click(function() {
				$(".mid_znssH1Span span").removeClass("gl_span");
				$(this).addClass("gl_span");
				var start_time = new Date($("#end").html());
				start_time.add($(this).attr("datatime"), $(this).attr("datacont"));
				var start_timeC = start_time.Format("yyyy-MM-dd hh:mm:ss");
				$("#start").html(start_timeC);
			});

			$(".znss2-rightM").delegate(".shou_fang", "click", function() {
				var shouP = $(this).next(".topIp");
				if(shouP.attr("class") == "topIp topIp_hide") {
					shouP.attr("class", "topIp topIp_show");
					$(this).next().children(".topIpdiv").attr("class", "topIpdiv topIpdiv_show");
					$(this).css("transform", "rotate(180deg)");
				} else {
					shouP.attr("class", "topIp topIp_hide");
					$(this).next().children(".topIpdiv").attr("class", "topIpdiv topIpdiv_hide");
					$(this).css("transform", "rotate(0deg)");
				}
			});
			//选择项目
			$(".pro_choose").change(function() {
				var faa = $(".pro_choose").find("option:selected").attr("data-fileid");
				dataID=faa.split(",");
				projectID_m=$(".pro_choose").find("option:selected").attr("data-id");
			})
			//热搜词
			$(".mid_znssH1Span3").delegate(".keyword", "click", function() {
				$("#append").hide().html("");
				$("#txt").val($(this).html());
				if(dataID.length==0) {
					var fff=$(".pro_choose option:first-of-type").attr("data-fileid");
					var fi=fff.split(",");
					dataID = fi;
					projectID_m=$(".pro_choose option:first-of-type").attr("data-id");
				}
				var searchVal = $(this).html();
				var _startT = $("#start").text();
				var _endT = $("#end").text();
				var laydateTime = _startT + "~" + _endT;
				if($(".znss2-rightH div").length < 6) {
					addTab(searchVal, _startT, _endT, laydateTime, "");
				} else {
					$(".znss2-rightH>div:first-of-type").remove();
					$(".znss2-rightM>div:nth-of-type(1),.znss2-rightM>div:nth-of-type(2)").remove();
					addTab(searchVal, _startT, _endT, laydateTime, "");
				}
			});
			//搜索
			$(".serchC").click(function() {
				$("#append").hide().html("");
				if(dataID.length==0) {
					var fff=$(".pro_choose option:first-of-type").attr("data-fileid");
					var fi=fff.split(",");
					dataID = fi;
					projectID_m=$(".pro_choose option:first-of-type").attr("data-id");
				}
				var searchVal = $(".searchinfo").val();
				var _startT = $("#start").text();
				var _endT = $("#end").text();
				var laydateTime = _startT + "~" + _endT;
				if(searchVal === "") {
					return false;
				}
				if($(".znss2-rightH div").length < 6) {
					addTab(searchVal, _startT, _endT, laydateTime, "");
				} else {
					$(".znss2-rightH>div:first-of-type").remove();
					$(".znss2-rightM>div:nth-of-type(1),.znss2-rightM>div:nth-of-type(2)").remove();
					addTab(searchVal, _startT, _endT, laydateTime, "");
				}
			});
			//在结果中搜索
			$(".serchE").click(function() {
				$("#append").hide().html("");
				if(dataID.length==0) {
					var fff=$(".pro_choose option:first-of-type").attr("data-fileid");
					var fi=fff.split(",");
					dataID = fi;
					projectID_m=$(".pro_choose option:first-of-type").attr("data-id");
				}
				var searchVal = $(".searchinfo").val();
				var last_searchVal = $(".qhtrue").attr("name");
				var _startT = $(".qhtrue").attr("data-start");
				var _endT = $(".qhtrue").attr("data-end");
				var laydateTime = _startT + "~" + _endT;
				if(searchVal == last_searchVal || searchVal === "") {
					return false;
				}
				if($(".znss2-rightH div").length < 6) {
					addTab(searchVal, _startT, _endT, laydateTime, last_searchVal);
				} else {
					$(".znss2-rightH>div:first-of-type").remove();
					$(".znss2-rightM>div:nth-of-type(1),.znss2-rightM>div:nth-of-type(2)").remove();
					addTab(searchVal, _startT, _endT, laydateTime, last_searchVal);
				}
			});
			//tab添加删除与切换
			//tab切换
			$(".znss2-rightH").delegate("div", "click", function() {
				// Get the tab name
				var tabName = $(this).find("span").html();
				// hide  all other tabs
				$(".znss2-rightH div").attr("class", "qhfalse");
				$(".end_znss1").hide();
				$(".tablex").hide();
				// show current tab
				$(this).attr("class", "qhtrue");
				$(".end_znss1:eq("+$.inArray(tabName, table_cont)+")").show();
				$(".tablex:eq("+$.inArray(tabName, table_cont)+")").show();
			});
			//tab删除
			$(".znss2-rightH").delegate("i", "click", function(event) {
				event.stopPropagation();
				// Get the tab name
				var tabid = $(this).parent().children("span").html();

					// remove tab and related content
					$(".end_znss1[name='" + tabid + "']").remove();
					$(".tablex:eq("+$.inArray(tabid, table_cont)+")").remove();
					$(this).parent().remove();
					table_cont.splice($.inArray(tabid, table_cont),1)
					// if there is no current tab and if there are still tabs left, show the first one
					if($(".znss2-rightH div.qhtrue").length === 0 && $(".znss2-rightH div").length > 0) {
						// find the first tab
						$(".znss2-rightH div:last-of-type").attr("class", "qhtrue");
						// get its link name and show related content
						var lasttabid = $(".znss2-rightH div:last-of-type").find("span").html();
						$(".end_znss1:eq("+$.inArray(lasttabid, table_cont)+")").show();
						$(".tablex:eq("+$.inArray(lasttabid, table_cont)+")").show();
					}
				});

		});
		$(".znss2-rightM").delegate(".tableSave", "click", function() {
			var p_data = {
				fid: dataID, //文件ID
				text: $(this).attr("name"),
				start_time: $(this).attr("data-start"),
				end_time: $(this).attr("data-end")
			}
			$.ajax({
				async: true,
				cache: false,
				type: "post",
				//dataType: "json", //返回json格式
				url: "/open01/serch.open?cmd=WEL:EXPORTEXCEL",
				data: p_data,
				success: function(data) { //请求成功后处理函数。
					 $("#export_excel").submit();
				}
			});
		})

		//tab添加table\echarts添加
		function addTab(searchVal, _startT, _endT, laydateTime, last_searchVal) {
			var twoVal = "";
			if(last_searchVal == "") {
				twoVal = searchVal;
			} else {
				twoVal = searchVal + "," + last_searchVal;
			}
			// If tab already exist in the list, return
			var tcnum=projectID_m + twoVal + laydateTime;
			for(var i=0;i<table_cont.length;i++){
				if(tcnum==table_cont[i]) {
					return;
				}
			}
			var newTab = $(".tabinfo div").clone();
			var newtable = $(".tableinfo .tablex").clone();
			var newCharts = $(".tableinfo .end_znss1").clone();
			newTab.find("span").html(projectID_m + twoVal + laydateTime);
			newTab.find("em").html(twoVal);
			newTab.attr("title", "项目：" + projectID_m + " 搜索词：" + twoVal + " 时间范围：" + laydateTime);
			newTab.attr("name", twoVal);
			newTab.attr("data-start", _startT);
			newTab.attr("data-end", _endT);
			newCharts.attr("name",projectID_m + twoVal + laydateTime);
			// newtable.attr("data-cont",projectID_m + twoVal + laydateTime);
			newtable.find(".tableSave").attr("name", twoVal);
			newtable.find(".tableSave").attr("data-start", _startT);
			newtable.find(".tableSave").attr("data-end", _endT);
			table_cont.push(tcnum);
			// hide other tabs
			$(".znss2-rightH div").attr("class", "qhfalse");

			// add new tab and related content
			$(".znss2-rightH").append(newTab);
			$(".znss2-rightM").append(newCharts);
			$(".znss2-rightM").append(newtable);
			$(".end_znss1").hide();
			$(".tablex").hide();

			//表格动态渲染
			var table1 = newtable.find(".tableSort").dataTable({
				"language": {
					"lengthMenu": "每页显示_MENU_条记录",
					"info": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
					"infoEmpty": "没有数据",
					"infoFiltered": "(从 _MAX_ 条数据中检索)",
					"zeroRecords": "没有检索到数据",
					"search": "名称:",
					"paginate": {
						"first": "首页",
						"previous": "前一页",
						"next": "后一页",
						"last": "尾页"
					}

				},
				"autoWidth": false,
				"paging": true,
				"lengthChange": true,
				"searching": false,
				"ordering": false,
				"processing": true, //加载数据时显示正在加载信息
				"serverSide": true, //指定从服务器端获取数据
				"pagingType": "full_numbers", //翻页界面类型
				"deferRender": true,
				"ajax": function(data, callback, settings) {
					//封装请求参数
					var param = {
						start_time: _startT,
						end_time: _endT,
						fid: dataID, //文件ID
						limit: data.length, //页面显示记录条数，在页面显示每页显示多少项的时候
						//start: data.start, //开始的记录序号
						page: (data.start / data.length) + 1, //当前页码
						txt: twoVal,
					};
					console.log(param);
					//ajax请求数据
					$.ajax({
						type: "GET",
						url: "data/searchTable.json",
						cache: false,
						data: param, //传入组装的参数
						dataType: "json",
						success: function(result) {
								//封装返回数据
							var returnData = {};
							returnData.draw = data.draw; //这里直接自行返回了draw计数器,应该由后台返回
							returnData.recordsTotal = result.total; //返回数据全部记录
							returnData.recordsFiltered = result.total; //后台不实现过滤功能，每次查询均视作全部结果
							returnData.data = result.data; //返回的数据列表
							//console.log(returnData);
							//调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
							//此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
							callback(returnData);
							// console.log(param);
							// console.log(returnData);
						}
					});
				},
				"columns": [{
						"data": "order"
					}, {
						"data": "datatime"
					}, {
						"data": "message",
						"render": function(data) {
							var lastTd = '<i class="shou_fang"></i><div class="topIp topIp_hide"><div class="topIpdiv topIpdiv_hide">' + data + '</div></div>';
							return lastTd;
						}
					}

				],
				"dom": '<"top"i>rt<"bottom"flp><"clear">'
			}).api();
			//图表
			var pree = {
				fid: dataID, //文件ID
				txt: twoVal,
				start_time: _startT,
				end_time: _endT
			};
			$.ajax({
				type: "GET",
				url: "data/searchTable.json",
				scriptCharset: 'utf-8',
				cache: false,
				data: pree, //传入组装的参数
				dataType: "json",
				success: function(res) {
					// console.log(pree)
					var _city = res.city;
					var _aa = [];
					for(var i = 0; i < _city.length; i++) {
						_aa.push(_city[i].value);
					}
					var pre_data = Math.max.apply(null, _aa);
					pre_data==0?pre_data=1000:pre_data=Math.max.apply(null, _aa);

							var select1 = document.getElementsByName(projectID_m + twoVal + laydateTime)[0].childNodes[1].childNodes[1];
							var select2 = document.getElementsByName(projectID_m + twoVal + laydateTime)[0].childNodes[1].childNodes[3];
							var data = {
								percent: res.percent,
								order: res.order,
								city: _city,
								pre_max:pre_data
							};
							echartsInit1(data, select1);
							echartsInit2(data, select2);

						}
					});

					// set the newly added tab as current
					newCharts.fadeIn(350);
					newtable.fadeIn(350);
				}




			//ajax获取     搜索框

        var tedata = "";
        var data = [];
        $("#txt").on("keyup", function() {
            $.ajax({
                url: "data/autocomplete1.json",
                type: "GET",
                dataType: "json",
                async: false,
                success: function(data1) {
                    data = data1.all;
                }
            });
        });
        $(document).keydown(function(e) {
            e = e || window.event;
            var keycode = e.which ? e.which : e.keyCode;
            if(keycode == 38 && $("#append").html() !== "") {
                movePrev();
            } else if(keycode == 40 && $("#append").html() !== ""){
                $("#txt").blur();
                if($(".item").hasClass("addbg")) {
                    moveNext();
                } else {
                    $(".item").removeClass('addbg').eq(0).addClass('addbg');
                }
            } else if(keycode == 13) {
                if($(".addbg").length!==0){
                    dojob();
                }else {
									if(dataID.length==0){
										var fff=$(".pro_choose option:first-of-type").attr("data-fileid");
										var fi=fff.split(",");
										dataID = fi;
										projectID_m=$(".pro_choose option:first-of-type").attr("data-id");
									}
                    var searchVal = $(".searchinfo").val();
                    var _startT=$("#start").text();
                    var _endT=$("#end").text();
                    var laydateTime = _startT + "~" + _endT;
                    if(searchVal === ""){
                        return false;
                    }
                    if($(".znss2-rightH div").length < 6) {
                        addTab(searchVal,_startT,_endT,laydateTime,"");
                    }else{
                        $(".znss2-rightH>div:first-of-type").remove();
                        $(".znss2-rightM>div:nth-of-type(1),.znss2-rightM>div:nth-of-type(2)").remove();
                        addTab(searchVal,_startT,_endT,laydateTime,"");
                    }
                }
            }
        });
        var movePrev = function() {
            $("#txt").blur();
            var index = $(".addbg").prevAll().length;
            if(index === 0) {
                $(".item").removeClass('addbg').eq($(".item").length - 1).addClass('addbg');
            } else {
                $(".item").removeClass('addbg').eq(index - 1).addClass('addbg');
            }
        };
        var moveNext = function() {
            var index = $(".addbg").prevAll().length;
            if(index == $(".item").length - 1) {
                $(".item").removeClass('addbg').eq(0).addClass('addbg');
            } else {
                $(".item").removeClass('addbg').eq(index + 1).addClass('addbg');
            }
        };
        var dojob = function() {
            if($(".addbg").length!== 0){
                $("#txt").blur();
                var value = $(".addbg").text();
                $("#txt").val(value);
                $("#append").hide().html("");
            }
        };
        $("#txt").on("keyup", function() {
            getContent($(this));
        });
        $(".item").on('mouseenter', function() {
            getFocus(this);
        });
        $(".item").on("click", function() {
            getCon(this);
        });

		function getContent(obj) {
			var txt = jQuery.trim($(obj).val());
			if(txt === "") {
				$("#append").hide().html("");
				return false;
			}
			var html = "";
			for(var i = 0; i < data.length; i++) {
				if(data[i].indexOf(txt) >= 0) {
					html = html + "<li class='item'>" + data[i] + "</li>";
				}
			}
			if(html !== "") {
				$("#append").show().html(html);
				$("#append li").on("click", function() {
					$(".mid_znssHP1 input").val($(this).text());
					$("#append").hide().html("");
				});
			} else {
				$("#append").hide().html("");
			}
		}

		function getFocus(obj) {
			$(".item").removeClass("addbg");
			$(obj).addClass("addbg");
		}

		function getCon(obj) {
			var value = $(obj).text();
			$("#txt").val(value);
			$("#append").hide().html("");
		}
	}
	return {
		render: render
	};
});
