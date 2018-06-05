<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/include/head.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<link rel="shortcut icon" href="${ctx}/img/ico.png">
		<link rel="stylesheet" href="${ctx}/css/public.css" />
		<script type="text/javascript" src="${ctx}/js/jQuery-2.1.4.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/public.js" ></script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		
		<title>-网上书城</title>
	</head>

	<body>
		<%@ include file="/WEB-INF/page/include/nav.jsp" %>
		<div class="page">
			<h2 id="bookName" class="page-title"></h2>
			<table width="100%">
				<tr>
					<td>
						<img id="bookPic" width="240px" height="320px" />
					</td>
					<td>
						<div>
							<h3 id="bookISBN"></h3>
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
							<button id="deleteBook" class="purchase">删除图书</button>
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

		<script>
			window.onload = function() {
				$.post("${ctx}/showBook",{"bookISBN":"${param.bookISBN}"},function(json){
					console.log(json);
					window.bookData = json;
					
					if(! json.book){//没有查找到图书
						$(".page").remove();
						$("body").append($("<div class='page'><h2 class='page-title'>抱歉</h2>没有找到该书籍。</div>"));
						document.title = "没有找到该书籍-网上书城"
						return;
					}
					
					$("#bookPic")[0].src = json.bookPicSrc + json.book.bookISBN + json.book.bookPic;
					$("#bookName").html(json.book.bookName);
					$("#bookISBN").html("ISBN " + json.book.bookISBN);
					$("#bookAuthor").html("作者 " + json.book.bookAuthor);
					$("#bookPublish").html("出版社 " + json.book.bookPublish);
					$("#bookPrice").html("￥" + json.book.bookPrice.toFixed(2));
					document.title = json.book.bookName + document.title;
					
					var bookAbstract = $("#bookAbstract");
					if(json.book.bookAbstract){
						bookAbstract.html(json.book.bookAbstract)
					}else{
						bookAbstract.html("该书暂无说明。")
					}
					
				},"json");
				$.post("${ctx}/showBookComment",{"bookISBN":"${param.bookISBN}"},function(json){
					console.log(json);
					var bookCommentList = json.bookCommentList;
					var bookCommentListHTML = "<table width='100%'>";
					
					for(var i in bookCommentList){
						bookCommentListHTML += "<tr><td>" + bookCommentList[i].description + "</td>";
						bookCommentListHTML += "<td>" + bookCommentList[i].logname + "</td>";
						bookCommentListHTML += "<td width='150px'>" + getDateString(bookCommentList[i].commentDate) + "</td></tr>";
					}
					bookCommentListHTML += "</table>";
					if(bookCommentList.length == 0){
						bookCommentListHTML = "该书暂时没有评价。";
					}
					$("#bookCommentList").html(bookCommentListHTML);
				},"json");
				
			}
			$("#bookNumberReduce").on("click",function(){
				var bookNumber = parseInt($("#bookNumber").text());
				bookNumber--;
				if (bookNumber <= 0) {
					bookNumber = 1;
				}
				$("#bookNumber").text(bookNumber);
			});
			
			$("#bookNumberAdd").on("click",function(){
				var bookNumber = parseInt($("#bookNumber").text());
				bookNumber++;
				if (bookNumber >= 99) {
					bookNumber = 99;
				}
				$("#bookNumber").text(bookNumber);
			});
			$("#purchase").on("click",function(){
				var number = $("#bookNumber").text();
				$.post("${ctx}/addShoppingCart",{"bookISBN" : "${param.bookISBN}", "number" : number},function(json){
					console.log(json);
					
					messageBox("添加购物车", json.message, function(){
						jsonCodeTest(json.code);//根据返回码进行相应操作
					});

				},"json");
				
			});
			$("#deleteBook").on("click",function(){
				$.post("${ctx}/deleteBook",{"bookISBN" : "${param.bookISBN}"},function(json){
					console.log(json);
					messageBox("删除图书", json.message);
				},"json");
			});
		</script>
	</body>

</html>