$(function(){
	init();
	function init(){
		index = 0;
		max = 2;
		imgs=["img/2.jfif","img/3.jfif","img/1.jfif"];
		setInterval(function(){
			if(index > max){
				index = 0;
			}
			path = imgs[index];
			index ++;
			$(".bgimg img").attr("src",path)
		},3500)
	}
})
