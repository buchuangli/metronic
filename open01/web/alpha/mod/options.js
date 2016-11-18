define(["text!../../tpl/options.html"],function(html){
	function render(){
		$(".page-content").html(html);
		 $.ajax({
				async : true,
				cache : false,
				type : "post",
				//dataType : "json", //返回json格式
				url : "/open01/options.open",
				data : {},
				success : function(data) { //请求成功后处理函数。
					$('#user_id').val(data.user_id);
					$('.userinfo_title>span').eq(0).html(data.client_id);

					$('#username').val(data.name);
					$('#email').val(data.email);
					$('#phone').val(data.phone);
					$('#sectionName').val(data.sectionName);
					$('.headImg').eq(0).css('background-image','url('+data.image+')');
					$('.headBg').eq(0).css('background-image','url('+data.image+')');
				},
				error : function() {//请求失败处理函数
					alert('请求失败');
				}
			});


		 $('#btnsave').click(function(){
			 $.ajax({
					async : true,
					cache : false,
					type : "post",
					dataType : "json", //返回json格式
					url : "/open01/options.open?cmd=WEL:UPDATE",
					data : {
						user_id : $('#user_id').val(),
						username : $('#username').val(),
						email : $('#email').val(),
						phone : $('#phone').val(),
						sectionName : $('#sectionName').val(),
						headImgDataUrl:headImgDataUrl
					},
					success : function(data) { //请求成功后处理函数
						if(data.statu == 'success'){
							alert('保存成功');
						}
					},
					error : function() {//请求失败处理函数
						alert('请求失败');
					}
				});
		 })
		 $('#btnreset').click(function(){
			 $.ajax({
				 async : true,
				 cache : false,
				 type : "post",
				 dataType : "json", //返回json格式
				 url : "/open01/options.open?cmd=WEL:RESET",
				 data : {
					 username : $('#username').val()
				 },
				 success : function(data) { //请求成功后处理函数
					 if(data.statu == 'success'){
						$('#user_id').val(data.user_id);
						$('#username').val(data.name);
						$('#email').val(data.email);
						$('#phone').val(data.phone);
						$('#sectionName').val(data.sectionName);
					 }
				 },
				 error : function() {//请求失败处理函数
					 alert('请求失败');
				 }
			 });
		 })
		 $("#changeAvatarBtn").click(function(){
		 	$(".changeAvatarBox").css("display","block");
		 })
		 //上传图片转化dataurl
		var headImgDataUrl;
		$(".a-upLoad").delegate("#headImg","change",function(){
		  	$(".changeAvatarInput").css("display","none");
		  	$(".changeAvatarPreview").css("display","block");
		    var file = this.files[0];
	        if(window.FileReader) {
	            var fr = new FileReader();
	            fr.onloadend = function(e) {
	                headImgDataUrl = e.target.result;
	                document.getElementsByClassName("changeAvatarExplainImg")[0].style.backgroundImage = 'url('+headImgDataUrl+')';
	            };
	            fr.readAsDataURL(file);
	        }
		})
		$(".changeAvatarCancel").click(function(){
			$(".changeAvatarBox").css("display","none");
			$(".changeAvatarInput").css("display","block");
		 	$(".changeAvatarPreview").css("display","none");
		 	var fileDom=$("#headImg");
			fileDom.after(fileDom.clone().val(""));
			fileDom.remove();
		})
		$(".changeAvatarExplainCheck").click(function(){
			$(".changeAvatarBox").css("display","none");
			$(".changeAvatarInput").css("display","block");
		 	$(".changeAvatarPreview").css("display","none");
		 	document.getElementsByClassName("headImg")[0].style.backgroundImage = 'url('+headImgDataUrl+')';
		 	var fileDom=$("#headImg");
			fileDom.after(fileDom.clone().val(""));
			fileDom.remove();
		})
		//个人设置、修改密码切换
		$(".optionSlide1").click(function(){
			$(".optionSlideBox li").removeClass("optionSlideBoxSeclect");
			$(this).addClass("optionSlideBoxSeclect");
			$(".resetPassword").animate({"opacity":"0"},167,function(){
				$(this).css("display","none");
			});
			$(".userinfo").css("display","block");
			$(".userinfo").animate({"opacity":"1"},167);
		})
		$(".optionSlide2").click(function(){
			$(".optionSlideBox li").removeClass("optionSlideBoxSeclect");
			$(this).addClass("optionSlideBoxSeclect");
			$(".userinfo").animate({"opacity":"0"},167,function(){
				$(this).css("display","none");
			});
			$(".resetPassword").css("display","block");
			$(".resetPassword").animate({"opacity":"1"},167);
		})
	}
	return {
		render:render
	}
})
