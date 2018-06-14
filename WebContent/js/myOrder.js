$(getAllOrder);

function getAllOrder() {
	$.post(window.ctx + "/order/getAllOrder", {}, function(json) {
		console.log(json);
		if(!json.result) {
			messageBox("收货地址", json.message, function() {
				jsonCodeTest(json.code); //根据返回码进行相应操作
			});
			return;
		}
		
		var notSettlementOrderList = [];//待结算订单
		var notCommentOrderList = [];//待评价订单
		
		for (var i in json.orderList) {
			var flag = false;	//表示当前订单下是否有未评价书籍
			var order = json.orderList[i];
			
			if(order.orderStatus == "未付款"){//未付款的订单
				notSettlementOrderList.push(order);
			}else{//已付款的订单
				for (var i in order.orderItemList) {//遍历订单下所有项目，发现有未评价的则添加
					var orderItem = order.orderItemList[i];
					if(orderItem.commentStatus == false){
						
						if (!flag) {
							var orderTemp = JSON.parse(JSON.stringify(order));//变相深拷贝这个订单对象
							orderTemp.orderItemList = [];//把订单下所有项目设置为空
							notCommentOrderList.push(orderTemp);	
							flag = true;
						}
						
						notCommentOrderList[notCommentOrderList.length - 1].orderItemList.push(orderItem);
					}
				}
			}
			
			
			
			
		}
		
		console.log(notCommentOrderList);
		$("#notSettlementOrderList").html(createOrderList(notSettlementOrderList));
		$("#notCommentOrderList").html(createOrderList(notCommentOrderList));
		$("#allOrderList").html(createOrderList(json.orderList));
		

	},"json");
}

function createOrderList(orderList){
	if ((!orderList) || orderList.length == 0) {
		return "暂时没有订单，你可以 <a href='" + window.ctx + "/jsp/showAllBook'>浏览图书</a> ，或者 <a href='" + window.ctx + "/jsp/shoppingCart'>前往购物车</a> 。";
	}
	var orderListHtml = "";
	
	for (var i in orderList) {
		var order = orderList[i];
		//订单标题
		orderListHtml += "<div class='thead' id='" + order.orderNO + "'>";
		orderListHtml += "<div class='tr'>";
		orderListHtml += "<div class='th'>" + getDateString(order.orderDate) + "</div>";
		orderListHtml += "<div class='th'>" + order.logname + "</div>";
		orderListHtml += "<div class='th'>" + order.phone + "</div>";
		orderListHtml += "<div class='th'>" + order.address + "</div>";
		orderListHtml += "<div class='th'><span class='price'>￥" + order.sum.toFixed(2) + "</span></div>";
		if (order.orderStatus == "未付款") {//如果未付款
			orderListHtml += "<div class='th'><button onClick='settlementOrder(" + order.orderNO + ")'>立即结算</button></div>";
		}else{
			orderListHtml += "<div class='th'></div>";
		}
		orderListHtml += "</div>";
		orderListHtml += "</div>";
		//订单内容
		
		for (var i in order.orderItemList) {
			var orderItem = order.orderItemList[i];
			var book = orderItem.book;
			orderListHtml += "<div class='tbody' id='" + orderItem.itemNO + "'>";
			orderListHtml += "<div class='tr'>";
			orderListHtml += "<div class='td'><img class='book-pic' src='" +window.ctx + "/img/bookPic/" + book.bookISBN + book.bookPic + "'/></div>";
			orderListHtml += "<div class='td'><a href='" + window.ctx + "/jsp/showBook?bookISBN=" + book.bookISBN + "' target='_blank'>" + book.bookName + "</a></div>";
			orderListHtml += "<div class='td'>单价：<span class='price'>￥" + book.bookPrice.toFixed(2) + "</span></div>";
			orderListHtml += "<div class='td'>数量：" + orderItem.count + "</div>";
			orderListHtml += "<div class='td'>小计：<span class='price'>￥" + (orderItem.count * book.bookPrice).toFixed(2) + "</span></div>";
			if (order.orderStatus == "未付款") {//如果未付款
				orderListHtml += "<div class='td'><button disabled='disabled'>立即评价</button></div>";
			}else{
				if(orderItem.commentStatus){//如果已评价
					orderListHtml += "<div class='td'><button onClick='showComment(" + orderItem.itemNO + ")'>查看评价</button></div>";
				}else{
					orderListHtml += "<div class='td'><button onClick='setComment(" + orderItem.itemNO + ")'>立即评价</button></div>";
				}
				
			}
			orderListHtml += "</div>";
			orderListHtml += "</div>";
			
		
		}
		
		
		
	}
	
	return orderListHtml;
	
	
}
function settlementOrder(orderNO){
	messageBox("结算订单","确定结算这个订单吗？",function(){
		$.post(window.ctx + "/order/settlementOrder", {"orderNO":orderNO}, function(json) {
		console.log(json);
		
		messageBox("结算订单", json.message, function() {
			if(json.result) {
				window.location.reload();
			}else{
				jsonCodeTest(json.code); //根据返回码进行相应操作
			}
		});

	},"json");
		
		
		
		
		
		
	},function(){
		
	});
}
function setComment(itemNO){//立即评论
	window.itemNO = itemNO;
	showMask($("#editCommentPageMask"));
}
function showComment(itemNO){//显示评论
	$.post(window.ctx + "/order/getBookComment", {"orderItemNO":itemNO}, function(json) {
		console.log(json);
		if(!json.result) {
			messageBox("我的评论", json.message, function() {
				jsonCodeTest(json.code); //根据返回码进行相应操作
			});
			return;
		}
		
		messageBox("我的评论", json.bookComment);
		
		
	},"json");
}

function submitComment(){//提交评价
	var comment = $("#editCommentPageMask textarea").val();
	if(comment.length < 10){
		messageBox("评价字数提示","评价字数不能少于10字。");
		return;
	}
	if(comment.length > 120){
		messageBox("评价字数提示","评价字数不能多于120字。");
		return;
	}
	console.log(comment,window.itemNO);
	
	$.post(window.ctx + "/order/submitBookComment", {"orderItemNO":window.itemNO,"comment":comment}, function(json) {
		console.log(json);
		messageBox("提交评论", json.message, function() {
			if(!json.result) {
				jsonCodeTest(json.code); //根据返回码进行相应操作
				return;
			}
			
			noneMask($("#editCommentPageMask"));
			getAllOrder();//重新获取页面所有数据
		});
		
		
		
	},"json");
}
