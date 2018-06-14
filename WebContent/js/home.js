function showEditPasswordPage() {
	showMask($("#editPasswordMask"));
}

function modifyPassword() {
	if(!testForm()) return;
	var parameter = $("#editPasswordForm").serialize();
	$.post(window.ctx + "/modifyPassword", parameter, function(json) {
		console.log(json);
		messageBox("修改密码", json.message, function() {
			if(!json.result){
				jsonCodeTest(json.code);
				return;
			}else{
				window.location.reload();//刷新
			}
		});
	}, "json");
}
function testForm(){
	var oldPassword = $("#oldPassword").val().trim();
	var newPassword = $("#newPassword").val().trim();
	var newPassword2 = $("#newPassword2").val().trim();
	$("#oldPassword").val(oldPassword);
	$("#newPassword").val(newPassword);
	$("#newPassword2").val(newPassword2);
	
	if(oldPassword == "") {
		messageBox("修改密码", "老密码不能为空！");
		return false;
	}
	if(oldPassword.length > 16 || oldPassword.length < 8) {
		messageBox("修改密码", "老密码长度必须8-16个字符！");
		return false;
	}
	if(newPassword == "") {
		messageBox("修改密码", "新密码不能为空！");
		return false;
	}
	if(newPassword.length > 16 || newPassword.length < 8) {
		messageBox("修改密码", "新密码长度必须8-16个字符！");
		return false;
	}
	if(newPassword2 == "") {
		messageBox("修改密码", "再次输入新密码不能为空！");
		return false;
	}
	if(newPassword2.length > 16 || oldPassword.length < 8) {
		messageBox("修改密码", "再次输入新密码长度必须8-16个字符！");
		return false;
	}
	
	if (newPassword == oldPassword) {
		messageBox("修改密码", "新老密码不能相同！");
		return false;
	}
	
	if (newPassword != newPassword2) {
		messageBox("修改密码", "两次输入的新密码不相同！");
		return false;
	}
	return true;
}
