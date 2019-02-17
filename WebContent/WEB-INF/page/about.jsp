<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/include/head.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="shortcut icon" href="${ctx}/img/ico.png">
		<link rel="stylesheet" href="${ctx}/css/public.css" />
		<script type="text/javascript" src="${ctx}/js/jQuery-2.1.4.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/public.js" ></script>
		<title>关于-网上书城</title>
	</head>

	<body>
		<%@ include file="/WEB-INF/page/include/nav.jsp" %>
		<%@ include file="/WEB-INF/page/include/rightTools.jsp" %>
		
		<div class="main">
			<div class="page">
				<h2 class="page-title">关于本站</h2>
				<div>
					这个网上书城小项目是我在课余时间写出来锻炼技术的，前端使用了jQuery框架，后端使用了Java语言，Spring、SpringMVC和Hibernate框架，MSSQLServer数据库。
					目前在Chrome、Edge、IE9+等浏览器上经过测试正常运行。
					<br />
					<br />
					本站所有书籍信息全部来自豆瓣，通过豆瓣提供的API获取的，不对数据的真实性做任何保证。
					<br />
					<br />
					本站所有操作均为模拟，并不是真正的商城，购买并不需要支付任何东西，当然你也不会得到任何东西。
					<br />
					<br />
					GitHub仓库地址： <a href="https://github.com/1171736840/bookshop" target="_blank">bookshop</a> 。
				</div>
			</div>
			<div class="page">
				<h2 class="page-title">关于我</h2>
				<div>
					我的博客：<a href="https://www.xuyanwu.cn" target="_blank">WGG</a>
					<br />
					<br />
					我的GitHub地址：<a href="https://github.com/1171736840" target="_blank">1171736840</a>
					<br />
					<br />
					联系邮箱 1171736840@qq.com
				</div>
			</div>
		</div>

		<%@ include file="/WEB-INF/page/include/footer.jsp" %>
	</body>
	
</html>