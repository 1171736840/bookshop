$("#loginButton,#registerButton").on("click", navLoginAndRegisterButtonClick);

function navLoginAndRegisterButtonClick() {
	//检测登录界面是否已加载，已加载则显示，未加载则从服务器加载
	var loginAndRegisterPageMask = $("#loginAndRegisterPageMask");
	var srcElement = window.event.srcElement;//保存一下事件的DOM对象，不然回调函数读取不到
	if(loginAndRegisterPageMask.length != 0) {
		showPage(srcElement);
		showMask(loginAndRegisterPageMask);	
	} else {
		$("<div></div>").load(window.ctx + "/jsp/loginAndRegister", {}, function(response) {
			console.log(response);
			$("body").append($("<div></div>").html(response));
			showPage(srcElement);
			setTimeout('showMask($("#loginAndRegisterPageMask"));',100);//这里延迟处理一下，不然可能事件未处理完导致位置不对
		}, "html");
		
	}
	
	function showPage(srcElement){	//显示对应的页面
		try{
			srcElement = srcElement || window.event.srcElement;
			console.log(srcElement.id)
			if (srcElement.id == "registerButton") {
				showRegisterPage();
			}else{
				showLoginPage();
			}
		}catch(e){
			//TODO handle the exception
		}
	}
}


$("#logoutButton").on("click", navLogoutButtonClick);
function navLogoutButtonClick() { //退出登录
	messageBox("退出登录","确定要退出登录吗？",function(){
		$.post(window.ctx + "/logout", {}, function(json) {
			console.log(json);
			messageBox("退出登录", json.message, function() {
				window.location.reload(); //刷新页面
			});
	
		}, "json");
	},function(){
		
	});
}