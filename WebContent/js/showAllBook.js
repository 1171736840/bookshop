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
		history.pushState({}, null, window.ctx + "/jsp/showAllBook?" + $("#from").serialize());
		first();
	}

	function getBookList(pageNumber) {

		loadingIn(g); //加载开始动画,参数一是动画完成后的回调函数

		function g() {
			var parameter = $("#from").serialize();
			parameter += "&thisPageNumber=" + pageNumber;
			$.post(window.ctx + "/showAllBook", parameter, function(json) {
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

			}, "json");
		}
	}

	function loadingIn(fun) { //加载开始动画
		var time = 500;
		var minPage = $(".min-page");
		minPage.stop(true);//停止所有动画
		minPage.animate({
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
		minPage.stop(true);//停止所有动画
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