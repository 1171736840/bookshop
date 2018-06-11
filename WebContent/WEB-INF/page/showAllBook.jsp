<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/include/head.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>所有图书-网上书城</title>
		<link rel="shortcut icon" href="${ctx}/img/ico.png">
		<link rel="stylesheet" href="${ctx}/css/public.css" />
		<link rel="stylesheet" href="${ctx}/css/showAllBook.css" />
		<script type="text/javascript" src="${ctx}/js/jQuery-2.1.4.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/public.js"></script>
		<script type="text/javascript" src="${ctx}/js/showAllBook.js"></script>

	</head>

	<body>
		<%@ include file="/WEB-INF/page/include/nav.jsp" %>
		<div class="page">
			<h2 class="page-title">搜索图书</h2>
			<form id="from" action="${ctx}/showAllBook" method="post">
				书名：<input type="text" name="bookName" value="${param.bookName}" /> 作者：
				<input type="text" name="bookAuthor" value="${param.bookAuthor}" /> 出版社：
				<input type="text" name="bookPublish" value="${param.bookPublish}" />
				<input type="button" onclick="selectBook()" value="查询图书" />
				<input type="reset" value="重置" />
			</form>

			共查找到 <span id="resultNumber" class="red"></span> 本图书，当前显示第 <span id="thisPageNumber" class="red"></span> 页，共 <span id="maxPageNumber" class="red"></span> 页
			<button onclick="first()">第一页</button>
			<button onclick="previous()">上一页</button>
			<button onclick="next()">下一页</button>
			<button onclick="last()">最后一页</button>
			<br />
			<!--<div id="fy" class="box"></div>-->
		</div>
		<div id="loading">
			<img height="180px" style="margin-top: -180px;" src="${ctx}/img/loading.gif" />
		</div>
		<div id="bookList">

		</div>
	</body>

</html>