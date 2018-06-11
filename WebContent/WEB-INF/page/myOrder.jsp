<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/include/head.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<link rel="shortcut icon" href="${ctx}/img/ico.png">
		<link rel="stylesheet" href="${ctx}/css/public.css" />
		<link rel="stylesheet" href="${ctx}/css/myOrder.css" />
		<script type="text/javascript" src="${ctx}/js/jQuery-2.1.4.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/public.js"></script>
		<script type="text/javascript" src="${ctx}/js/myOrder.js" ></script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

		<title>我的订单-网上书城</title>
	</head>

	<body>
		<%@ include file="/WEB-INF/page/include/nav.jsp" %>
		<div class="page">
			<h2 class="page-title">待结算</h2>
			<div id="notSettlementOrderList">

			</div>
		</div>
		<div class="page">
			<h2 class="page-title">待评价</h2>
			<div id="notCommentOrderList">

			</div>
		</div>
		<div class="page">
			<h2 class="page-title">全部订单</h2>
			<div id="allOrderList">

			</div>
		</div>
		
		
		<div id="editCommentPageMask" class="mask" style="display: none;" onclick="noneMask()">
			<div class="editCommentPage" onclick="stopPropagation(event)">
				<h2>写评价</h2>
				<textarea placeholder="在这里写下你的评价,最多120字。" maxlength="120" onchange="this.value=this.value.substring(0, 120)" onkeydown="this.onchange(this)" onkeyup="this.onchange(this)"></textarea>
				<button onclick="submitComment()">提交评价</button>
			</div>
		</div>
		
	</body>

</html>