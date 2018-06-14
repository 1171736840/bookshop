<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/include/head.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
		<link rel="shortcut icon" href="${ctx}/img/ico.png">
		<link rel="stylesheet" href="${ctx}/css/public.css" />
		<link rel="stylesheet" href="${ctx}/css/shoppingCart.css" />
		<script type="text/javascript" src="${ctx}/js/jQuery-2.1.4.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/public.js"></script>
		<script type="text/javascript" src="${ctx}/js/shoppingCart.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

		<title>购物车-网上书城</title>
	</head>

	<body>
		<%@ include file="/WEB-INF/page/include/nav.jsp" %>
		<%@ include file="/WEB-INF/page/include/rightTools.jsp" %>
		
		<div class="main">
			<div class="page">
				<h2 class="page-title">购物车</h2>
				<div id="shoppingCartPage" style="display: none;">
					<table id="shoppingCart" class="shopping-cart">
						<thead>
							<tr>
	
								<th width="40px">选择</th>
								<th>商品</th>
								<th>单价（元）</th>
								<th>数量</th>
								<th>小计</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
	
						</tbody>
					</table>
					<div class="controller-page">
						<input id="allSelect" type="checkbox" onclick="selectAll()" />&nbsp;&nbsp;全选
						<button class="settlement" onclick="settlement()">立即结算</button>
						<div id="totalPrice" class="total-price"></div>
					</div>
				</div>
			</div>
			
			<div id="selectAddressMask" class="mask" onclick="noneMask()">
				<div class="select-address-page" onclick="stopPropagation(event)">
					<h2>选择收货地址</h2>
					<div id="addressList" class="address-list">
						<div class="address-list-head">
							<div class="address-list-col">收货人</div>
							<div class="address-list-col">联系电话</div>
							<div class="address-list-col">地址</div>
							<div class="address-list-col">操作</div>
						</div>
						<div id="addressListBody" class="address-list-body">
							
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<%@ include file="/WEB-INF/page/include/footer.jsp" %>
	</body>

</html>