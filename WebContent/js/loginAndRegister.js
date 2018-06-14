//$("#loginAndRegisterPageMask").on("click", noneLoginAndRegisterPageMask); //隐藏登录窗口
//function noneLoginAndRegisterPageMask() { //隐藏登录窗口
//	$("#loginAndRegisterPageMask").css("display", "none");
//}
//$("#loginAndRegisterPage").on("click", function(event) {
//	event.stopPropagation(); //阻止事件向上冒泡
//});
$("#login").on("click", function() { //登录
	//判断是不是登录页面，不是注册页面则切换到登录页面
	if(showLoginPage()){
		return;
	}
	//是登录页面则开始登录流程
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
	//判断是不是注册页面，不是注册页面则切换到注册页面
	if(showRegisterPage()){
		return;
	}
	
	//是注册页面则开始注册流程
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
	var password2 = $("#password2").val().trim();
	$("#logname").val(logname);
	$("#password").val(password);
	$("#password2").val(password2);
	if(logname == "") {
		messageBox("登录 注册", "用户名不能为空！");
		return false;
	}
	
	if(logname.length > 10 || logname.length < 2) {
		messageBox("登录 注册", "用户名长度必须2-10个字符！");
		return false;
	}
	
	if(password == "") {
		messageBox("登录 注册", "密码不能为空！");
		return false;
	}
	if(password.length > 16 || password.length < 8) {
		messageBox("登录 注册", "密码长度必须8-16个字符！");
		return false;
	}
	
	if ($("#loginAndRegisterPage").attr("type") == "register") {//如果是注册流程
		if(password2 == "") {
			messageBox("登录 注册", "再次输入密码不能为空！");
			return false;
		}
		if(password2 != password) {
			messageBox("登录 注册", "两次输入的密码不一致！");
			return false;
		}
	}
	return true;
}
function showRegisterPage(){
	var loginAndRegisterPage = $("#loginAndRegisterPage");
	if(loginAndRegisterPage.attr("type") == "login"){
		loginAndRegisterPage.attr("type","register");
		var logname = $("#logname");
		var password = $("#password");
		var password2 = $("#password2");
		var title = $("#loginAndRegisterPage div.title");
		logname.attr("placeholder","请输入注册账号");
		logname.val("");
		password.attr("placeholder","请输入注册密码");
		password.val("");
		password2.val("");
		password2.attr("class","");
		title.html("注册");
		return true;
	}
}
function showLoginPage(){
	var loginAndRegisterPage = $("#loginAndRegisterPage");
	if(loginAndRegisterPage.attr("type") == "register"){
		loginAndRegisterPage.attr("type","login");
		var logname = $("#logname");
		var password = $("#password");
		var password2 = $("#password2");
		var title = $("#loginAndRegisterPage div.title");
		logname.attr("placeholder","请输入登录账号");
		logname.val("");
		password.attr("placeholder","请输入登录密码");
		password.val("");
		password2.val("");
		password2.attr("class","pwd-none");
		title.html("登录");
		return true;
	}
}
