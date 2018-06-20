window.onload = function() {
	var bookISBN = $("#bookISBN").text();
	$.post(window.ctx + "/showBook",{"bookISBN":bookISBN},function(json){
		console.log(json);
		window.bookData = json;
		
		if(! json.book){//没有查找到图书
			$("div.main").html("");
			$("div.main").append($("<div class='page'><h2 class='page-title'>抱歉</h2>没有找到该书籍。</div>"));
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
	
	$.post(window.ctx + "/showBookComment",{"bookISBN":bookISBN},function(json){
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
		var bookISBN = $("#bookISBN").attr("bookISBN");
		console.log(bookISBN)
		$.post(window.ctx + "/addShoppingCart",{"bookISBN" : bookISBN, "number" : number},function(json){
			console.log(json);
			
			messageBox("添加购物车", json.message, function(){
				jsonCodeTest(json.code);//根据返回码进行相应操作
			});
	
		},"json");
		
	});
	$("#deleteBook").on("click",function(){
		var bookISBN = $("#bookISBN").attr("bookISBN");
		$.post(window.ctx + "/deleteBook",{"bookISBN" : bookISBN},function(json){
			console.log(json);
			messageBox("删除图书", json.message);
		},"json");
	});
}
