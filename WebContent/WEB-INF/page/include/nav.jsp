<%@ page contentType="text/html; charset=utf-8"%>
<meta charset="utf-8" />
<div class="nav">
	<div class="nav-main">
		<div class="nav-main-page">
			<img height="50px" width="50px" src="${ctx}/img/ico.png">&nbsp;网上书城
		</div>
		<div class="nav-main-page">
			<!--占位使用-->
		</div>
		<div class="nav-main-page">
			<!--<a href="${ctx}/" target="_blank">首页</a>-->
			<a href="${ctx}/jsp/showAllBook" target="_blank">浏览图书</a>
			<a href="${ctx}/jsp/shoppingCart" target="_blank">购物车</a>
			<a href="${ctx}/jsp/myAddress" target="_blank">我的地址</a>
			<a href="${ctx}/jsp/myOrder" target="_blank">我的订单</a>
			<a href="${ctx}/jsp/modifyPassword" target="_blank">修改密码</a>
			
		</div>
		<div class="nav-main-page" style="float: right;">
		<!-- 检测用户是否已经登录 -->
			<c:choose>
				<c:when test="${user != null}">
					<div class="button" id="logoutButton">退出</div>
					<div class="button">
						<img class="photo" src="${ctx}/img/photo.jpg">
						&nbsp;&nbsp;
						<span id="navLogname">${user.logname}</span>
					</div>
					
				</c:when>
				<c:otherwise>
					<div class="button" id="registerButton">注册</div>
					<div class="button" id="loginButton">登录</div>
				</c:otherwise>
			</c:choose>  
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/js/nav.js" ></script>