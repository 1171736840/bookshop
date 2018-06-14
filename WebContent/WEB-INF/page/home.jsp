<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/include/head.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="shortcut icon" href="${ctx}/img/ico.png">
		<link rel="stylesheet" href="${ctx}/css/public.css" />
		<link rel="stylesheet" href="${ctx}/css/home.css" />
		<script type="text/javascript" src="${ctx}/js/jQuery-2.1.4.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/public.js" ></script>
		<script type="text/javascript" src="${ctx}/js/home.js" ></script>
		<title>个人中心-网上书城</title>
	</head>

	<body>
		<%@ include file="/WEB-INF/page/include/nav.jsp" %>
		<%@ include file="/WEB-INF/page/include/rightTools.jsp" %>
		
		<div class="main">
			<div class="page">
				<h2 class="page-title">个人信息</h2>
				<div>
					用户名：${user.logname}
					<br />
					<br />
					<button onclick="showEditPasswordPage()">修改密码</button>
				</div>
			</div>
			<div class="mask" id="editPasswordMask" onclick="noneMask()">
				<div class="edit-password-page" onclick="stopPropagation(event)">
					<div class="mask-title">修改密码</div>
					<form id="editPasswordForm" action="#" >
						<input id="oldPassword" placeholder="请输入老密码" type="password" name="oldPassword">
						<input id="newPassword" placeholder="请输入新密码" type="password" name="newPassword" >
						<input id="newPassword2" placeholder="请再次输入新密码" type="password" name="newPassword2" >
					</form>
					<button class="button" onclick="modifyPassword()">修改密码</button>
				</div>
			</div>
		</div>
		

		<%@ include file="/WEB-INF/page/include/footer.jsp" %>
	</body>
	
</html>