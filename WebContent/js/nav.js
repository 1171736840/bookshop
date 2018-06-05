$("#loginButton,#registerButton").on("click", navLoginAndRegisterButtonClick);

$("#logoutButton").on("click", navLogoutButtonClick);

function navLoginAndRegisterButtonClick() {
	//检测登录界面是否已加载，已加载则显示，未加载则从服务器加载
	var loginAndRegisterPageMask = $("#loginAndRegisterPageMask");
	if(loginAndRegisterPageMask.length != 0) {
		loginAndRegisterPageMask.css("display", "block");
	} else {
		var div = $("<div></div>").load(window.ctx + "/jsp/loginAndRegister", {}, function(response) {
			console.log(response);
		}, "html");
		$("body").append(div);
	}

}

function navLogoutButtonClick() { //退出登录
	$.post(window.ctx + "/logout", {}, function(json) {
		console.log(json);
		messageBox("退出登录", json.message, function() {
			window.location.reload(); //刷新页面
		});

	}, "json");
}