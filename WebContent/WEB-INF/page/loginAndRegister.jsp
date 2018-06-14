<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/include/head.jsp"%>
<!--登录和注册界面-->
<link rel="stylesheet" href="${ctx}/css/loginAndRegister.css" />

<div id="loginAndRegisterPageMask" class="mask" onclick="noneMask()">
	<div id="loginAndRegisterPage" class="login-register-page" type="login" onclick="stopPropagation(event)">
		<div class="title">登录</div>
		<form id="loginAndRegisterForm" action="#" method="post">
			<input id="logname" placeholder="请输入登录账号" type="text" name="logname" value="张三">
			<input id="password" placeholder="请输登录入密码" type="password" name="password" value="12345678">
			<input id="password2" class="pwd-none" placeholder="请再次输入密码" type="password" name="password2" value="" >
		</form>
		<button id="login" class="button">登&nbsp;&nbsp;录</button>
		<button id="register" class="button">注&nbsp;&nbsp;册</button>
	</div>
</div>
<script type="text/javascript" src="${ctx}/js/loginAndRegister.js" ></script>