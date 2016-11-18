define(["text!../../tpl/manage.html","datatable","bootswitch","maxlength","bootstrapselect","sweetAlert","icheck"], function(html) {
	function render() {
		$(".page-content").html(html);
		var tr_mid_x = "";
		var ms, xm, fid, pid, ssa = "";
		var xd=[];
		var sss = [];
		var kjss = [];
		var op=[];
		var ed=[];
		var fileid="";
		//创建表格
		$.ajax({
			async: true,
			cache: true,
			type: "post",
			dataType: "json", //返回json格式
			url: "/open01/project.open",
			data: {},
			success: function(data) { //请求成功后处理函数。
				$.each(data, function(index, values) { // 解析出data对应的Object数组
					op.push(values.name);
					ed.push(values.id);
					var fileLable="";
					var file_name="";
					var file_type="";
					var file_up="";
					var file_lose="";
					var file_size="";
					// var pro_items="";
					if(values.project_data === null) {
						fileLable="";
						file_name="";
						file_type="";
						file_up="";
						file_lose="";
						file_size="";
						// pro_items="";
						values.project_data = "";
					}else{
						var pro_data=values.project_data.split(",");
						var id_data=values.fileid.split(",");
						var up_date=values.fileuptime.split(",");
						var lose_date=values.filedeadtime.split(",");
						// pro_items="";
						fileLable="";
						file_name="";
						file_type="";
						file_up="";
						file_lose="";
						file_size="";
						for(var i=0;i<pro_data.length;i++){
							// pro_items+="<p class='" + id_data[i] + "'><span>" + pro_data[i] + "</span></p>";
							fileLable+='<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline fen"><input type="checkbox" class="sell" data-id="'+id_data[i]+'"><span></span></label>'
							file_name+="<div class='fen'>"+pro_data[i]+"</div>";
							file_type+="<div class='fen'>文件</div>";
							file_up+="<div class='fen'>"+up_date[i]+"</div>";
							file_lose+="<div class='fen'>"+lose_date[i]+"</div>";
							file_size+="<div class='fen'>0.2</div>";
						}
					}
					// var itemsPP=$("<div class='all_itemsp " + values.id + "'><span>" + values.name + "</span>"+pro_items+"</div>");
					// $(".all_items").append(itemsPP);
					var tr1 = $("<tr data-id='" + values.id + "' data-file=" + values.fileid + "  class='tr_mid'>" +
						"<td>可使用</td>"+
						"<td class='table-checkbox sorting_disabled'><label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'><input type='checkbox' class='sel'><span></span></label></td>"+
						"<td class='p_buttonx'><input type='text' class='ccc' title='" + values.name + "' value='" + values.name + "' disabled maxlength='100'><textarea spellcheck='false' maxlength=100'></textarea><input type='checkbox' class='btnOC'></td>" +
						"<td class='p_buttonx'><input type='text' class='ccc' title='" + values.description + "' value='" + values.description + "' disabled maxlength='100'><textarea spellcheck='false' maxlength=100'></textarea><input type='checkbox' class='btnOC'></td>" +
						"<td>" + values.time + "</td>" +
						"<td class='table-checkbox sorting_disabled clearfix' style='overflow:hidden;'>" + fileLable + "</td>" +
						"<td>"+file_name+"</td>"+
						"<td>"+file_type+"</td>"+
						"<td>"+file_up+"</td>"+
						"<td>"+file_lose+"</td>"+
						"<td>"+file_size+"</td>"+
						"</tr>");
					$("tbody").append(tr1);
				});
			},
			error: function() { //请求失败处理函数
				// alert('请求失败');
			}
		});
		$.ajax({
			async: true,
			cache: true,
			type: "post",
			dataType: "json", //返回json格式
			url: "/open01/dosage.open",
			data: {},
			success: function(data) { //请求成功后处理函数。
				console.log(data)
				$.each(data, function(index, values) { // 解析出data对应的Object数组
					var tr2 = $("<tr data-file=" + values.ds_id + "  class='tr_mid'>" +
						"<td>未启用</td>"+
						"<td></td>"+
						"<td><select class='selectpicker'></select></td>" +
						"<td></td>" +
						"<td></td>" +
						"<td class='table-checkbox sorting_disabled clearfix' style='overflow:hidden;'><label class='mt-checkbox mt-checkbox-single mt-checkbox-outline fen'><input type='checkbox' class='sell' data-id='"+values.ds_id+"'><span></span></label></td>" +
						"<td>"+values.name+"</td>"+
						"<td>文件</td>"+
						"<td>"+values.uptime+"</td>"+
						"<td>"+values.deadtime+"</td>"+
						"<td>"+values.filesize+"</td>"+
						"</tr>");
					$("tbody").append(tr2);
				})
			}
		});
		$.each(op,function (index) {
			$(".selectpicker").append("<option data-id='"+ed[index]+"'>"+this+"</option>");
		})
		$('.selectpicker').selectpicker({
			width:"100%",
			title:"请选择项目",
		  size: 4,
		});

		//表格初始化
		var table = $("#source").DataTable({
			"autoWidth":false,
			"info":true,
			"ordering":false,
			"searching":true,
			"lengthChange":true,
			"paging":true,
			"deferRender": true,
			// "scrollY":10,
			"language":{
			    "decimal":        "",
			    "emptyTable":     "没有数据",
			    "info":           "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
			    "infoEmpty":      "显示第 0 至 0 项结果，共 0 项",
			    "infoFiltered":   "(从 _MAX_ 条数据中检索)",
			    "infoPostFix":    "",
			    "thousands":      ",",
			    "lengthMenu":     "每页显示: _MENU_ 条记录",
			    "loadingRecords": "数据载入中...",
			    "processing":     "数据载入中...",
			    "search":         "查找",
			    "zeroRecords":    "没有数据",
			    "paginate": {
			        "first":      "首页",
			        "last":       "尾页",
			        "next":       "后一页",
			        "previous":   "前一页"
			    },
			    "aria": {
			        "sortAscending":  ": 以升序排列此列",
			        "sortDescending": ": 以降序排列此列"
			    }
			},
		});
		$('input').iCheck({
	    checkboxClass: 'icheckbox_square-blue',
	    increaseArea: '20%' // optional
	  });
		//  $("div.toolbar").html("<button class='btn blue btn-outline' id='ana'>分析报表</button> <button class='btn blue btn-outline' id='serc'>智能搜索</button>");
		$("input[type='search']").attr("class","form-control input-sm input-small input-inline");
		//项目名称可编辑开关
		$('.edit_proN').click(function(event) {
			swal({
				title:"编辑项目名称",
				text:"名称",
				type:"input",
  			showCancelButton: true,
  			closeOnConfirm: false,
			})
				// var id = $(this).parents("tr").attr("data-id");
				// var val1 = $('td:eq(2) input', $(this).parents("tr")).val();
				// var val2 = $('td:eq(3) input', $(this).parents("tr")).val();
				// var data = [id, val1, val2];
				// $.post("/open01/project.open?cmd=WEL:update", {
				// 	'updata[]': data
				// });

		});
		//项目描述可编辑开关
		$('.edit_proD').click(function(event) {
			swal({
				title:"编辑项目描述",
				text:"描述",
				type:"input",
				showCancelButton: true,
				closeOnConfirm: false,
			})
		})
		//编辑时的弹出框
		// $("tbody").delegate("input.ccc","click",function (event) {
		// 	event.stopPropagation();
		// });
		// $("tbody").delegate("input.ccc","focus",function () {
		// 	$(this).siblings("textarea").show();
		// 	$(this).siblings().val($(this).val());
		// });
		// $("tbody").delegate("textarea","blur",function () {
		// 	$(this).hide();
		// });
		// //textarea与input双向绑定
		// $("tbody").delegate("textarea","keyup",function (event) {
		// 	$(this).siblings("input").val($(this).val());
		// 	$(this).siblings("input").attr("title",$(this).val());
		// });
		// $("tbody").delegate("input.ccc","keyup",function (event) {
		// 	$(this).siblings("textarea").val($(this).val());
		// 	$(this).attr("title",$(this).val());
		// });

		//选中行
		$('tbody').on( 'ifChecked', '.sel', function () {
			var p_tr=$(this).parents("tr");
			xd=[];
			// $('tr').removeClass('selected');
			// $('.sel').prop("checked",false);
      p_tr.addClass('selected');
			$(this).prop("checked",true);
			p_tr.children("td").eq(6).children("div.fen").each(function() {
				xd.push($(this).text());
			});
			ms = p_tr.children("td").eq(2).children("input.ccc").val();
			xm = p_tr.children("td").eq(3).children("input.ccc").val();
			fid = p_tr.attr("data-file");
			pid = p_tr.attr("data-id");
    });
		$('tbody').on( 'ifUnchecked', 'input.sel', function () {
			var p_tr=$(this).parents("tr");
			p_tr.removeClass('selected');
				xd=[];
				ms = "";
				xm = "";
				fid = "";
				pid = "";
		})
		$('tbody').on( 'click', 'tr.tr_mid', function () {
        $(this).toggleClass('selected');
				$(this).find(".sel").iCheck('toggle');
    } );
		//跳转智能搜索
		$("#serc").click(function() {
			var mmx = 0;
			$(".tr_mid").each(function() {
				if($(this).hasClass("selected")) { //判断是否有选择项目
					mmx = 1;
					return false;
				}
			});
			if(mmx === 0) {
				swal("请先选择项目");
			}else {
				projectID=pid;
				projectID_m=pid;
				var fi=fid.split(",");
				dataID=fi;
				location.href="#serch";
			}
		});
		//跳转分析报表
		$("#ana").click(function() {
			var TR_midName = 0;
			$(".tr_mid").each(function() {
				if($(this).hasClass("selected")) {
					TR_midName = 1;
					return false;
				}
			});
			if(TR_midName == 1) {
				projectID=pid;
				location.href="#analysis";
				upDateProID();
			}else{
				swal("请先选择项目");
			}
		});
		//打印
		$("#c_print").click(function () {
			var a=$("body").clone();
			var b=$("#source").clone();
			$("body").html(b);
			window.print();
			window.location.reload() ;
		});
		//数据全选反选
		$(function () {
			var selectall1 = $(".sell");
			var check1=$(".allsell");
			var selectall2 = $("tbody input[type='checkbox']").not("input[name='nopro']");
			var check2=$(".allsel");
			check1.on("ifChecked", function() {
				selectall1.iCheck('check');
			})
			check2.on("ifChecked", function() {
				selectall2.iCheck('check');
			});
			check1.on("ifUnchecked", function() {
				selectall1.iCheck('uncheck');
			});
			check2.on("ifUnchecked", function() {
				selectall2.iCheck('uncheck');
			});
		});
		//删除项目
		$("#delete_select").click(function() {
			if($("table input[type='checkbox']:checked").length==0){
				swal("请先选择删除对象");
				return false;
			}
			if($("tr").hasClass("selected")){
				var sid = $("tr.selected").attr('data-id');
				swal({
				  title: "确定要删除项目吗",
					text: "删除项目会导致项目内所有数据一并删除；\n 删除的项目将在回收站中保存七天",
				  type: "warning",
				  showCancelButton: true,
				  confirmButtonClass: "btn-danger",
				  confirmButtonText: "确定删除",
				  cancelButtonText: "取消",
				  closeOnConfirm: false,
				  closeOnCancel: true
				},
				function() {
						$.ajax({
							async: true,
							type: "post",
							//dataType : "json", //返回json格式
							url: "/open01/project.open?cmd=WEL:DELETE",
							data: {
								id: sid
							},
							success: function(data) { //请求成功后处理函数。
								swal("删除成功","", "success");
								window.location.reload();
							},
							error: function() { //请求失败处理函数
								swal('删除失败',"","error");
							}
						});
				});
			}else{
				fileid="";
				$(".sell:checked").each(function () {
					fileid+=$(this).attr("data-id");
				})
				swal({
				  title: "确定要删除数据吗",
					text: "删除的数据将在回收站中保存七天",
				  type: "warning",
				  showCancelButton: true,
				  confirmButtonClass: "btn-danger",
				  confirmButtonText: "确定删除",
				  cancelButtonText: "取消",
				  closeOnConfirm: false,
				  closeOnCancel: true
				},
				function() {
						$.ajax({
							async: true,
							type: "post",
							//dataType : "json", //返回json格式
							url: "/open01/dosage.open?cmd=WEL:DELETE",
							data: {
								fid: fileid
							},
							success: function(data) { //请求成功后处理函数。
								swal("删除成功!","", "success");
								window.location.reload();
							},
							error: function() { //请求失败处理函数
								swal("删除失败","" ,"error");
							}
						});
				});
			}
		});
		$("#savebtn").click(function () {
			swal({
			  title: "保存与处理",
			  text: "处理需要花费一定时间， \n 取决于变更数据量的多少",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonClass: "btn-danger",
			  confirmButtonText: "确定处理",
			  cancelButtonText: "取消",
			  closeOnConfirm: false,
			  closeOnCancel: false
			},
			function(isConfirm) {
			  if (isConfirm) {
			    swal("操作成功!", "本次处理需要一段时间，在此期间您仍可以使用并查看原有数据", "success");
			  } else {
			    swal("操作失败", "", "error");
			  }
			});
		})


	}
	return {
		render: render
	};
});
