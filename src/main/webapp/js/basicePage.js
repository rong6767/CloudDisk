$(function(){
	function init(){
		$(".body .list table .t_row span").hide();
		$(".body .list table .t_row").mouseover(function(){
			$(this).find("span").show();
		})
		$(".body .list table .t_row").mouseout(function(){
			$(".body .list table .t_row span").hide();
		})
	}
	init();
})
