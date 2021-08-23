$(function(){
	init();
	function init(){
		$(".showMenu .base a").click(function(){
			$(".showMenu .base").removeClass("menu");
			$(".showMenu .base").find("i").removeClass("icon-white");
			$(this).parent().addClass("menu")
			$(this).parent().find("i").addClass("icon-white");
		})
	}
	
	
	
	
})
