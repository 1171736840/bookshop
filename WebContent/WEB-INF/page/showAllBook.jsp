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
		<!--<link rel="stylesheet" href="${ctx}/css/paging.css" />-->
		<script type="text/javascript" src="${ctx}/js/jQuery-2.1.4.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/public.js"></script>
		<!--<script type="text/javascript" src="${ctx}/js/paging.js"></script>-->

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

		<script>
			function first() {
				getBookList(1);
			}

			function previous() {
				getBookList(window.bookListData.thisPageNumber - 1);
			}

			function next() {
				var thisPageNumber = window.bookListData.thisPageNumber + 1;
				thisPageNumber = thisPageNumber > window.bookListData.maxPageNumber ? window.bookListData.maxPageNumber : thisPageNumber;
				getBookList(thisPageNumber);
			}

			function last() {
				getBookList(window.bookListData.maxPageNumber);
			}

			function selectBook() {
				//修改地址栏URL而不刷新页面
				history.pushState({}, null, "${ctx}/jsp/showAllBook?" + $("#from").serialize());
				first();
			}

			function getBookList(pageNumber) {

				loadingIn(g); //加载开始动画,参数一是动画完成后的回调函数

				function g() {
					var parameter = $("#from").serialize();
					parameter += "&thisPageNumber=" + pageNumber;
					$.post("${ctx}/showAllBook", parameter, function(json) {
						console.log(json);
						window.bookListData = json;
						var bookListHTML = "";
						var bookList = json.bookList;
						for(var i in bookList) {
							bookListHTML += "<div class='min-page'>";
							bookListHTML += "<div class='min-page-photo'><a href='${ctx}/jsp/showBook?bookISBN=" + bookList[i].bookISBN + "' target='_blank'>";
							bookListHTML += "<img class='book-photo' src='" + json.bookPicSrc + bookList[i].bookISBN + bookList[i].bookPic + "'/>";
							bookListHTML += "</a></div>";
							bookListHTML += "<span class='price'>￥" + bookList[i].bookPrice.toFixed(2) + "</span><br />";
							bookListHTML += "<a class='bookName' href='${ctx}/jsp/showBook?bookISBN=" + bookList[i].bookISBN + "' target='_blank' title='" + bookList[i].bookName + "'>" + bookList[i].bookName + "</a>";
							bookListHTML += "<div><a class='bookPublish' href='${ctx}/jsp/showAllBook?bookPublish=" + bookList[i].bookPublish + "' target='_blank' title='" + bookList[i].bookPublish + "'>" + bookList[i].bookPublish + "</a>";
							bookListHTML += "<a class='bookAuthor' href='${ctx}/jsp/showAllBook?bookAuthor=" + bookList[i].bookAuthor + "' target='_blank' title='" + bookList[i].bookAuthor + "'>" + bookList[i].bookAuthor + "</a>";
							bookListHTML += "</div></div>";
						}
						$("#bookList").html(bookListHTML);
						$("#resultNumber").html(json.resultNumber);
						$("#thisPageNumber").html(json.thisPageNumber);
						$("#maxPageNumber").html(json.maxPageNumber);

						loadingOut(); //加载结束动画

						//修改当前页面的历史记录数据
						var stateObject = {
							"id": 1,
							"bookName": $("input[name='bookName']").val(),
							"bookAuthor": $("input[name='bookAuthor']").val(),
							"bookPublish": $("input[name='bookPublish']").val(),
							"thisPageNumber": window.bookListData.thisPageNumber
						};
						console.log(stateObject);
						history.replaceState(stateObject, null, "");

//						$('#fy').paging({
//							initPageNo: json.thisPageNumber, // 初始页码
//							totalPages: json.maxPageNumber, //总页数
//							totalCount: '合计' + json.resultNumber + '条数据', // 条目总数
//							slideSpeed: 600, // 缓动速度。单位毫秒
//							jump: true, //是否支持跳转
//							callback: function(page) { // 回调函数
//								console.log(page);
//							}
//						});

					}, "json");
				}
			}

			function loadingIn(fun) { //加载开始动画
				var time = 500;
				$(".min-page").animate({
					"opacity": 0
				}, time);

				var img = $("#loading").find("img");
				img.css("opacity", 0);
				img.animate({
					"margin-top": "0px",
					"opacity": 1
				}, time);

				if(typeof fun == "function") {
					setTimeout(fun, time);
				}
			}

			function loadingOut() { //加载结束动画
				var time = 500;
				var minPage = $(".min-page");
				minPage.css("opacity", 0);
				minPage.animate({
					"opacity": 1
				}, time);
				$("#loading").find("img").animate({
					"margin-top": "-180px",
					"opacity": 0
				}, time);
			}

			window.onload = function() {
				first();
			}
			window.addEventListener('popstate', function(event) {

				var state = event.state || {
					"id": 1,
					"bookName": "",
					"bookAuthor": "",
					"bookPublish": "",
					"thisPageNumber": 1
				};

				console.log(state);

				$("input[name='bookName']").val(state.bookName);
				$("input[name='bookAuthor']").val(state.bookAuthor);
				$("input[name='bookPublish']").val(state.bookPublish);
				window.bookListData.thisPageNumber = state.thisPageNumber + 1;
				previous();
			});
		</script>
	</body>

</html>