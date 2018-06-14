<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/include/head.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
		<link rel="shortcut icon" href="${ctx}/img/ico.png">
		<link rel="stylesheet" href="${ctx}/css/public.css" />
		<link rel="stylesheet" href="${ctx}/css/myAddress.css" />
		<script type="text/javascript" src="${ctx}/js/jQuery-2.1.4.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/public.js"></script>
		<script type="text/javascript" src="${ctx}/js/PCASClass.js"></script>
		<script type="text/javascript" src="${ctx}/js/myAddress.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

		<title>收货地址-网上书城</title>
	</head>

	<body>
		<%@ include file="/WEB-INF/page/include/nav.jsp" %>
		<%@ include file="/WEB-INF/page/include/rightTools.jsp" %>
		
		<div class="main">
			<div class="page">
				<h2 class="page-title">我的收货地址</h2>
				<button class="page-title-button" onclick="showAddAddressPage()">添加收货地址</button>
				<div id="addressList">
	
				</div>
			</div>
			
			<div id="addAddressMask" class="mask"  onclick="noneMask()">
				<div class="mask-Address-page" onclick="stopPropagation(event)">
					<h2 class="page-title">添加收货地址</h2>
					<form id="newAddress">
						<div class="mask-Address-page-item">
							<span class="page-title-min">收货人</span>
							<input type="text" name="receiver" />
						</div>
						<div class="mask-Address-page-item">
							<span class="page-title-min">联系电话</span>
							<input type="text" name="phone" />
						</div>
						<div class="mask-Address-page-item">
							<span class="page-title-min">邮编</span>
							<input type="text" name="postcode" />
						</div>
						<div class="mask-Address-page-item">
							<span class="page-title-min">收货地址</span>
							<select id="province" name="province"></select>
						</div>
						<div class="mask-Address-page-item">
							<span class="page-title-min"></span>
							<select id="city" name="city"></select>
						</div>
						<div class="mask-Address-page-item">
							<span class="page-title-min"></span>
							<select id="area" name="area"></select>
						</div>
						<div class="mask-Address-page-item">
							<span class="page-title-min">详细街道</span>
							<input type="text" name="street" />
						</div>
						<div class="mask-Address-page-item">
							<input type="button" onclick="addAddress()" value="添加收货地址" />
						</div>
					</form>
				</div>
			</div>
	
			<div id="editAddressMask" class="mask"  onclick="noneMask()">
				<div class="mask-Address-page" onclick="stopPropagation(event)">
					<h2 class="page-title">修改收货地址</h2>
					<form id="editAddress">
						<div class="mask-Address-page-item">
							<span class="page-title-min">收货人</span>
							<input type="text" name="receiver" />
						</div>
						<div class="mask-Address-page-item">
							<span class="page-title-min">联系电话</span>
							<input type="text" name="phone" />
						</div>
						<div class="mask-Address-page-item">
							<span class="page-title-min">邮编</span>
							<input type="text" name="postcode" />
						</div>
						<div class="mask-Address-page-item">
							<span class="page-title-min">收货地址</span>
							<select id="province2" name="province"></select>
						</div>
						<div class="mask-Address-page-item">
							<span class="page-title-min"></span>
							<select id="city2" name="city"></select>
						</div>
						<div class="mask-Address-page-item">
							<span class="page-title-min"></span>
							<select id="area2" name="area"></select>
						</div>
						<div class="mask-Address-page-item">
							<span class="page-title-min">详细街道</span>
							<input type="text" name="street" />
						</div>
						<div class="mask-Address-page-item">
							<input type="button" onclick="editAddress()" value="修改收货地址" />
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<%@ include file="/WEB-INF/page/include/footer.jsp" %>
	</body>

</html>