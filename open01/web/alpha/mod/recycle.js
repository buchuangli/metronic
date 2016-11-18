define(["text!../../tpl/recycle.html","datatable","bootstrapselect","sweetAlert"],function(html){
	function render(){
		$(".page-content").html(html);
		$("input[type='search']").attr("class","form-control input-sm input-small input-inline");
		$('.selectpicker').selectpicker({
			width:"100%",
			title:"请选择项目",
		  size: 4,
		});
	}
	return {
		render:render
	}
})
