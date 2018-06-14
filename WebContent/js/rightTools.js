//当页面滚动时判断要不要显示到顶端按钮
$(window).on("scroll",function(e){
	var h = document.documentElement.scrollTop || document.body.scrollTop;
//	console.log(h,window.innerHeight);
	if (h > 100) {
		$("#toTop").css("height",50);
		$("#toTop").css("border-width",1);
	}else{
		$("#toTop").css("height",0);
		$("#toTop").css("border-width",0);
	}
});

