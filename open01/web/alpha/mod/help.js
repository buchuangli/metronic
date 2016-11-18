define(["text!../../tpl/help.html"],function(html){
	function render(){
		$(".page-content").html(html);
	}
	return {
		render:render
	};
});
