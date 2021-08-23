$(function(){
	init();
	function init(){
		$(".title .menu a").click(function(){
			$(".title .menu span").removeClass("bottonSpan")
			$(this).parent("span").addClass("bottonSpan")
		});
		$(".showMenu .base a").click(function(){
			$(".showMenu .base").removeClass("menu");
			$(".showMenu .base").find("i").removeClass("icon-white");
			$(this).parent().addClass("menu")
			$(this).parent().find("i").addClass("icon-white");
		})
	}
	
	
	
	
})
