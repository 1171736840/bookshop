$("#loginAndRegisterPageMask").on("click", noneLoginAndRegisterPageMask); //隐藏登录窗口
function noneLoginAndRegisterPageMask() { //隐藏登录窗口
	$("#loginAndRegisterPageMask").css("display", "none");
}
$("#loginAndRegisterPage").on("click", function(event) {
	event.stopPropagation(); //阻止事件向上冒泡
});
$("#login").on("click", function() { //登录
	if(!testForm()) return;

	$.post(window.ctx + "/login", $("#loginAndRegisterForm").serialize(), function(json) {
		console.log(json);
		if(json.result) {
			messageBox("登录成功", json.message,function(){
				window.location.reload();//刷新页面
			});
		} else {
			messageBox("登录失败", json.message);
		}
	}, "json");
});
$("#register").on("click", function() { //注册
	if(!testForm()) return;

	$.post(window.ctx + "/regist", $("#loginAndRegisterForm").serialize(), function(json) {
		console.log(json);

		if(json.result) {
			messageBox("注册成功", json.message);
		} else {
			messageBox("注册失败", json.message);
		}
	}, "json");
});





function testForm() {
	var logname = $("#logname").val().trim();
	var password = $("#password").val().trim();
	$("#logname").val(logname);
	$("#password").val(password);
	if(logname == "") {
		messageBox("登录 注册", "用户名不能为空！");
		return false;
	}
	if(password == "") {
		messageBox("登录 注册", "密码不能为空！");
		return false;
	}
	return true;
}