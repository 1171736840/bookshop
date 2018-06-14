function messageBox(title, message, confirmFun, cancelFun) { //弹出一个消息框
	var mask = $("<div class='mask'><div class='messagebox'><div class='message-title'></div><div class='message-context'></div><div class='button-page'><button name='confirm'>确定</button></div></div></div>");
	$(document.body).append(mask);

	mask.find(".message-title").html(title); //设置标题
	mask.find(".message-context").html(message); //设置标题

	var id = new Date().getTime();
	mask.attr("id", id);
	var confirmButton = mask.find("button[name='confirm']");
	confirmButton.focus(); //获取焦点
	confirmButton.on("click", function() { //确定按钮
		removeMask(mask); //删除消息框
		if(typeof confirmFun == "function") {
			confirmFun();
		}
	});

	//如果有对应的取消方法则创建取消按钮并添加对应事件
	if(typeof cancelFun == "function") {
		var cancelButton = $("<button name='cancel'>取消</button>")
		cancelButton.on("click", function() {
			removeMask(mask); //删除消息框
			cancelFun();
		});
		mask.find(".button-page").prepend(cancelButton); //插入元素到开头
	}

	//检测按下事件
	function keyup(event) {
		switch(event.keyCode) {
			case 32: //空格
			case 13: //回车
				$("body").off("keyup", keyup);
				//				$("button[name='confirm']").trigger("click");//模拟点击确定按钮
				//这里不需要模拟点击了，因为确定按钮是默认焦点按钮
				break;
			case 27: //ESC
				$("body").off("keyup", keyup);
				if(typeof cancelFun == "function") { //先判断取消按钮是否存在，存在点击取消按钮，不存在点击确定按钮
					$("button[name='cancel']").trigger("click");
				} else {
					$("button[name='confirm']").trigger("click");
				}

				break;
		}
	}

	//绑定键盘事件
	$("body").on("keyup", keyup);

	//调整messageBox位置
	showMask(mask);
}

function jsonCodeTest(code) { //检测执行json的code对应的操作
	switch(code) {
		case 100: //表示用户未登录，打开登录框
			navLoginAndRegisterButtonClick();
			break;
		default:

			break;
	}
}

function formMatch(type, text) { //表单验证
	var defaultVal = {
		NUMBER: "^[0-9]*$",
		TEL: "^0(10|2[0-5789]|\\d{3})-\\d{7,8}$",
		IP: "^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$",
		MOBILE: "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$",
		MAIL: "^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$",
		IDENTITY: "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|91)\\d{4})((((19|20)(([02468][048])|([13579][26]))0229))|((20[0-9][0-9])|(19[0-9][0-9]))((((0[1-9])|(1[0-2]))((0[1-9])|(1\\d)|(2[0-8])))|((((0[1,3-9])|(1[0-2]))(29|30))|(((0[13578])|(1[02]))31))))((\\d{3}(x|X))|(\\d{4}))",
		CHINESE: "^([\u4E00-\uFA29]|[\uE7C7-\uE7F3])*$",
		URL: "^http[s]?://[\\w\\.\\-]+$",
		POSTCODE: "^[1-9][0-9]{5}$"
	};
	var flag = false;
	if(type in defaultVal) { //验证规则匹配
		flag = new RegExp(defaultVal[type]).test(text);
	}
	return flag;
}

function stopPropagation(event) { //阻止事件向上冒泡
	event.stopPropagation();
}

function getDateString(time) { //获取时间字符串
	var date = new Date(time);
	return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
}

function showMask(mask) {
	var page = mask.children("div");
//	console.log(page[0].offsetHeight);
	mask.css("display", "block");
	page.css("margin-top", (0 - page[0].offsetHeight) + "px");
	mask.css("opacity", 0);

	mask.animate({
		"opacity": 1
	}, 200);
	page.animate({
		"margin-top": (0 - page[0].offsetHeight / 2) + "px"
	}, 200);
}

function removeMask(mask) {
	var page = mask.children("div");
	mask.animate({
		"opacity": 0
	}, 200, function() {
		mask.remove();
	});
	page.animate({
		"margin-top": (0 - page[0].offsetHeight) + "px"
	}, 200);
}

function noneMask(mask) { //隐藏这种
	mask = $(mask || $(window.event.srcElement));
	var page = mask.children("div");
	mask.animate({
		"opacity": 0
	}, 200, function() {
		mask.css("display", "none");
	});
	page.animate({
		"margin-top": (0 - page[0].offsetHeight) + "px"
	}, 200);
}

function toTop(fun) { //返回到页面顶端
	$("body").animate({
		"scrollTop": 0
	}, 300, function() {
		//兼容IE9动画无效的问题
		$(window).scrollTop(0);
		if(typeof fun == "function") {
			fun();
		}
	});
}

//判断当前浏览类型 
function BrowserType() {
	var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串 
	var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器 
	var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器 
	var isEdge = userAgent.indexOf("Windows NT 6.1; Trident/7.0;") > -1 && !isIE; //判断是否IE的Edge浏览器 
	var isFF = userAgent.indexOf("Firefox") > -1; //判断是否Firefox浏览器 
	var isSafari = userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") == -1; //判断是否Safari浏览器 
	var isChrome = userAgent.indexOf("Chrome") > -1 && userAgent.indexOf("Safari") > -1; //判断Chrome浏览器 

	if(isIE) {
		var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
		reIE.test(userAgent);
		var fIEVersion = parseFloat(RegExp["$1"]);
		if(fIEVersion == 7) {
			return "IE7";
		} else if(fIEVersion == 8) {
			return "IE8";
		} else if(fIEVersion == 9) {
			return "IE9";
		} else if(fIEVersion == 10) {
			return "IE10";
		} else if(fIEVersion == 11) {
			return "IE11";
		} else {
			return "0"
		} //IE版本过低 
	} //isIE end 

	if(isFF) {
		return "FF";
	}
	if(isOpera) {
		return "Opera";
	}
	if(isSafari) {
		return "Safari";
	}
	if(isChrome) {
		return "Chrome";
	}
	if(isEdge) {
		return "Edge";
	}
} //myBrowser() end 

//判断是否是IE浏览器 
function isIE() {
	var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串 
	var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器 
	return userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器 
}

//判断是否是IE浏览器，包括Edge浏览器 
function IEVersion() {
	var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串 
	var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器 
	var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器 
	var isEdge = userAgent.indexOf("Windows NT 6.1; Trident/7.0;") > -1 && !isIE; //判断是否IE的Edge浏览器 
	if(isIE) {
		var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
		reIE.test(userAgent);
		var fIEVersion = parseFloat(RegExp["$1"]);
		if(fIEVersion == 7) {
			return "IE7";
		} else if(fIEVersion == 8) {
			return "IE8";
		} else if(fIEVersion == 9) {
			return "IE9";
		} else if(fIEVersion == 10) {
			return "IE10";
		} else if(fIEVersion == 11) {
			return "IE11";
		} else {
			return "0"
		} //IE版本过低 
	} else if(isEdge) {
		return "Edge";
	} else {
		return "-1"; //非IE 
	}
}