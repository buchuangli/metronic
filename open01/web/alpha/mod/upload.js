define(["text!../../tpl/upload.html","dropzone","jqueryForm"],function(html){
	function render(){
		var projectIDArr=[];
		//HTML节点注入section中
		$(".page-content").html(html);
		$(".dropz").dropzone({
        url: "handle-upload.php",
        maxFiles: 10,
        maxFilesize: 512,
        acceptedFiles: ".js,.obj,.dae"
    });
		$('.addFilesSelect li').eq(0).css("border-left-color","#265577")
		$('.addFilesSelect li').each(function(){
			$(this).click(function(){
				$(".selectBox").css("height","0");
				$(".upLoadIndex").css("display","none")
				if($(this).next().attr("class")){
					$(this).next().css("height","70px")
				}
				$('.addFilesSelect li').css("border-left-color","rgba(0,0,0,0)")
				$(this).css({"border-left-color":"#265577"})
				if($(this).attr("data-chose")==1){
					$('.addFilesSlide2').animate({"opacity":"0"},167,function(){
						$(this).css({"display":"none"});
						$('.addFilesSlide1').css({"display":"block"});
						$(".flow").css({"display":"block"});
						$('.addFilesSlide1').animate({"opacity":"1"},167);
						$(".flow").animate({"opacity":"1"},167);
						$(".upLoadRightBox").scrollTop(0);
					});
					$('.addFilesSlide3').animate({"opacity":"0"},167,function(){
						$(this).css({"display":"none"})
						$('.addFilesSlide1').css({"display":"block"})
						$(".flow").css({"display":"block"});
						$('.addFilesSlide1').animate({"opacity":"1"},167)
						$(".flow").animate({"opacity":"1"},167);
						$(".upLoadRightBox").scrollTop(0);
					})
					$('.addFilesSlide4').animate({"opacity":"0"},167,function(){
						$(this).css({"display":"none"})
						$('.addFilesSlide1').css({"display":"block"})
						$(".flow").css({"display":"block"});
						$('.addFilesSlide1').animate({"opacity":"1"},167)
						$(".flow").animate({"opacity":"1"},167);
						$(".upLoadRightBox").scrollTop(0);
					})
				}else if($(this).attr("data-chose")==2){
					$('.addFilesSlide1').animate({"opacity":"0"},167,function(){
						$(this).css({"display":"none"})
						$('.addFilesSlide2').css({"display":"block"})
						$('.addFilesSlide2').animate({"opacity":"1"},167)
						$(".upLoadRightBox").scrollTop(0);
					});
					$('.addFilesSlide3').animate({"opacity":"0"},167,function(){
						$(this).css({"display":"none"})
						$('.addFilesSlide2').css({"display":"block"})
						$('.addFilesSlide2').animate({"opacity":"1"},167)
						$(".upLoadRightBox").scrollTop(0);
					})
					$('.addFilesSlide4').animate({"opacity":"0"},167,function(){
						$(this).css({"display":"none"})
						$('.addFilesSlide2').css({"display":"block"})
						$('.addFilesSlide2').animate({"opacity":"1"},167)
						$(".upLoadRightBox").scrollTop(0);
					})
				}else if($(this).attr("data-chose")==3){
					$(".flow").animate({"opacity":"0"},167);
					$('.addFilesSlide1').animate({"opacity":"0"},167,function(){
						$(".flow").css("display","none");
						$(this).css({"display":"none"})
						$('.addFilesSlide3').css({"display":"block"})
						$('.addFilesSlide3').animate({"opacity":"1"},167)
						$(".upLoadRightBox").scrollTop(0);
					});
					$('.addFilesSlide2').animate({"opacity":"0"},167,function(){
						$(this).css({"display":"none"})
						$('.addFilesSlide3').css({"display":"block"})
						$('.addFilesSlide3').animate({"opacity":"1"},167)
						$(".upLoadRightBox").scrollTop(0);
					})
					$('.addFilesSlide4').animate({"opacity":"0"},167,function(){
						$(this).css({"display":"none"})
						$('.addFilesSlide3').css({"display":"block"})
						$('.addFilesSlide3').animate({"opacity":"1"},167)
						$(".upLoadRightBox").scrollTop(0);
					})
				}else if($(this).attr("data-chose")==4){
					$('.addFilesSlide1').animate({"opacity":"0"},167,function(){
						$(this).css({"display":"none"})
						$('.addFilesSlide4').css({"display":"block"})
						$('.addFilesSlide4').animate({"opacity":"1"},167)
						$(".upLoadRightBox").scrollTop(0);
					});
					$('.addFilesSlide2').animate({"opacity":"0"},167,function(){
						$(this).css({"display":"none"})
						$('.addFilesSlide4').css({"display":"block"})
						$('.addFilesSlide4').animate({"opacity":"1"},167)
						$(".upLoadRightBox").scrollTop(0);
					})
					$('.addFilesSlide3').animate({"opacity":"0"},167,function(){
						$(this).css({"display":"none"})
						$('.addFilesSlide4').css({"display":"block"})
						$('.addFilesSlide4').animate({"opacity":"1"},167)
						$(".upLoadRightBox").scrollTop(0);
					})
				}
			})
		})

		$(".tr_mid").each(function() {
			$(this).on("click", function() {
				if($(this).attr("data-chose") == 0) {
					$(this).css("background","#858585")
					$(this).css("background-color", "#779ead")
					$(this).attr("data-chose","1")
				} else {
					$(this).removeAttr("style")
					$(this).attr("data-chose","0")
				};
			})
		})
		$("#allCheck").click(function(){
			$(".tr_mid").each(function() {
				$(this).css("background","#858585")
				$(this).css("background-color", "#779ead")
				$(this).attr("data-chose","1")
			})
		})
		$("#delChose").click(function(){
			$(".tr_mid[data-chose='1']").each(function(){
				$(this).remove()
			})
		})
		$(".selectBox div:eq(0)").css("border-left-color","#265577");
		$(".selectBox div:eq(2)").css("border-left-color","#265577");
		$(".selectBox div:eq(0)").click(function(){
			$(".upLoadRightBox").scrollTop(0);
			$(".selectBox:eq(0) div").css("border-left-color","rgba(0,0,0,0)")
			$(this).css("border-left-color","#265577")
			$(".addFilesSlide2 .countent:nth-child(3)").css("display","none")
			$(".addFilesSlide2 .countent:nth-child(2)").css("display","block")
			$(".addFilesSlide2 .selectBox div").css("background-color","rgba(255,255,255,0.2)")
		})
		$(".selectBox div:eq(2)").click(function(){
			$(".upLoadRightBox").scrollTop(0);
			$(".selectBox:eq(1) div").css("border-left-color","rgba(0,0,0,0)")
			$(this).css("border-left-color","#265577")
			$(".addFilesSlide3 .countent:nth-child(3)").css("display","none")
			$(".addFilesSlide3 .countent:nth-child(2)").css("display","block")
			$(".selectBox div").css("background-color","rgba(255,255,255,0.2)")
		})
		$(".selectBox div:eq(1)").click(function(){
			$(".upLoadRightBox").scrollTop(0);
			$(".selectBox:eq(0) div").css("border-left-color","rgba(0,0,0,0)")
			$(this).css("border-left-color","#265577")
			$(".addFilesSlide2 .countent:nth-child(2)").css("display","none")
			$(".addFilesSlide2 .countent:nth-child(3)").css("display","block")
			$(".addFilesSlide2 .selectBox div").css("background-color","rgba(255,255,255,0.2)")
		})
		$(".selectBox div:eq(3)").click(function(){
			$(".upLoadRightBox").scrollTop(0);
			$(".selectBox:eq(1) div").css("border-left-color","rgba(0,0,0,0)")
			$(this).css("border-left-color","#265577")
			$(".addFilesSlide3 .countent:nth-child(2)").css("display","none")
			$(".addFilesSlide3 .countent:nth-child(3)").css("display","block")
			$(".addFilesSlide3 .selectBox div").css("background-color","rgba(255,255,255,0.2)")
		})
		var totalSize = 0;
		//checkBox click
		$(".projectSelectBox").delegate("li input","click",function(){
			if($(this).attr("data-chose")==0){
				projectIDArr.push($(this).attr("value"));
				$(this).attr("data-chose","1");
				$(this).prop("checked",true)
			}else{
				projectIDArr.splice(projectIDArr.indexOf($(this).attr("value")),1);
				$(this).attr("data-chose","0");
				$(this).prop("checked",false)
			}
		})
		//allCheck
		$("#upLoadAllCheck").click(function(){
			$(".projectSelectBox li input").each(function(){
				$(this).prop("checked",true).attr("data-chose","1");
			})
		})
		//绑定所有type=file的元素的onchange事件的处理函数
		$(':file').change(function() {
			var file = this.files[0]; //假设file标签没打开multiple属性，那么只取第一个文件就行了
			name = file.name;
			size = file.size;
			url = window.URL.createObjectURL(file); //获取本地文件的url，如果是图片文件，可用于预览图片
			type=name.split(".").pop();
			console.log(type)
			var fileType=["txt","zip","tar","7z","gzip","xz","rar","json","csv","log"];
			var fileTypeCheck=0;
			for(var i=0;i<fileType.length;i++){
				if(fileType[i].indexOf(type)!=-1){
					fileTypeCheck=1;
				}
			}
			if(fileTypeCheck==1){
				$("#upload_data").removeAttr("onsubmit")
				$(this).parent().append("<div class='fileList'></div>")
				$(".fileList").append("<ul><li><e></e><span class='eTitle'>"+name+"</span>文件名：" + name + "</br> 文件类型：." + type + "</br> 文件大小：" + size +" Bytes</li></ul>")
				//+ "</br> 预览: <img src="+url+" style='width:100px;vertical-align:top;'>"
				totalSize += size;
				$("#info").html(totalSize + " bytes");
				totalSize=0;
				$("#progressBox").css("display","block")
			}else{
				alert("请上传正确格式的文件，目前支持的格式有.txt,.zip,.tar,.7z,.gzip,.xz,.rar,.json,.csv,.log")
			}
		});

		$("#upLoadSubmit").click(function(){
			$(".addFilesSlide1").fadeOut();
			$(".upLoadIndex").fadeIn();
			$(".flow span").eq(0).attr("class","flowSpan");
			$(".flow span").eq(1).attr("class","flowActiveSpan");
		})
		$("#confirmUpload").click(function(){
			$(".upLoadIndex").fadeOut();
			$(".complateUpload").fadeIn();
			$(".flow span").eq(1).attr("class","flowSpan");
			$(".flow span").eq(2).attr("class","flowActiveSpan");
			var timeout=4;
			setInterval(function(){
				$(".complateUpload span i").html(timeout)
				if(timeout>0){
					timeout--;
				}
			},1000)
			setTimeout(function(){
				location.href="#/dosage"
			},5000)
		})

		//uploadForm
		var options={
			success:function(data){
				console.log(data);
			}
		}
		$("#uploadForm").ajaxForm(options)
		//buildScript 1to2
		$(".buildBtn").click(function(){
			var path=$("#logPath").val();
			var logType=$(".scriptUploadBox1_select").eq(0).val();
			var tag=$("#customTag").val();

			$(".scriptUploadBox1").fadeOut();
			$(".scriptUploadBox2").fadeIn();
			$(".scriptUploadFlow li:eq(1)").addClass("scriptUploadFlowActive");
		})
		//nextPage
		$(".nextPage").each(function(index){
			$(this).click(function(){
				$(".scriptUploadBox"+(index+2)).fadeOut();
				$(".scriptUploadBox"+(index+3)).fadeIn();
				$(".scriptUploadFlow li:eq("+(index+2)+")").addClass("scriptUploadFlowActive");
			})
		})
		//prevPage
		$(".prevPage").each(function(index){
			$(this).click(function(){
				$(".scriptUploadBox"+(index+2)).fadeOut();
				$(".scriptUploadBox"+(index+1)).fadeIn();
				$(".scriptUploadFlow li:eq("+(index+1)+")").removeClass("scriptUploadFlowActive");
			})
		})
	}
	return {
		render:render
	}
})
