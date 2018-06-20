<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/include/head.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
		<link rel="shortcut icon" href="${ctx}/img/ico.png">
		<link rel="stylesheet" href="${ctx}/css/public.css" />
		<script type="text/javascript" src="${ctx}/js/jQuery-2.1.4.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/public.js" ></script>
		<script type="text/javascript" src="${ctx}/js/showBook.js" ></script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>-网上书城</title>
	</head>

	<body>
		<%@ include file="/WEB-INF/page/include/nav.jsp" %>
		<%@ include file="/WEB-INF/page/include/rightTools.jsp" %>
		
		<div class="main">
			<div class="page">
				<h2 id="bookName" class="page-title"></h2>
				<table width="100%">
					<tr>
						<td>
							<img id="bookPic" width="240px" height="320px" />
						</td>
						<td>
							<div>
								<h3 id="bookISBN" bookISBN="${param.bookISBN}">${param.bookISBN}</h3>
								<h3 id="bookAuthor"></h3>
								<h3 id="bookPublish"></h3>
								<h3 id="bookPrice" class="price"></h3>
								<div class="button-number-box">
									<button  id="bookNumberReduce" class="button-number-box-button">-</button>
									<div id="bookNumber" class="button-number-box-number">1</div>
									<button id="bookNumberAdd" class="button-number-box-button">+</button>
								</div>
								<br />
								<button id="purchase" class="purchase">加入购物车</button>
								<button id="deleteBook" class="purchase" style="display: none;">删除图书</button>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="page">
				<h2 class="page-title">说明</h2>
				<div id="bookAbstract"></div>
			</div>
			<div class="page">
				<h2 class="page-title">评价</h2>
				<div id="bookCommentList"></div>
			</div>
		</div>
		
		<%@ include file="/WEB-INF/page/include/footer.jsp" %>
	</body>

</html>