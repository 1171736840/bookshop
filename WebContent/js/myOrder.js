$(function() {
	$.post(window.ctx + "/order/getAllOrder", {}, function(json) {
		console.log(json);
		if(!json.result) {
			messageBox("收货地址", json.message, function() {
				jsonCodeTest(json.code); //根据返回码进行相应操作
			});
			return;
		}
		
//		var notSettlementOrderList = [];//待结算订单
//		var notCommentOrderItemList = [];//待评价书籍
//		
//		for (var i in json.orderList) {
//			var order = json.orderList[i];
//			
//			if(order.orderStatus == "未付款"){
//				notSettlementOrderList.push(order);
//			}
//			
//			for (var i in order.orderItemList) {
//				var orderItem = order.orderItemList[i];
//				if(orderItem.commentStatus == false){
//					notCommentOrderItemList.push(orderItem);
//				}
//			}
//			
//			
//		}

		$("#allOrderList").html(createOrderList(json.orderList));
		

	});
});

function createOrderList(orderList){
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
		if (order.orderStatus) {//如果未结算
			orderListHtml += "<div class='th'><button onClick='settlement(" + order.orderNO + ")'>立即结算</button></div>";
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
			orderListHtml += "<div class='td'><a href='${ctx}/jsp/showBook?bookISBN=" + book.bookISBN + "' target='_blank'>" + book.bookName + "</a></div>";
			orderListHtml += "<div class='td'>" + book.bookISBN + "</div>";
			orderListHtml += "<div class='td'>" + book.bookISBN + "</div>";
			if (! orderItem.commentStatus) {//如果未结算
				orderListHtml += "<div class='td'><button onClick='settlement(" + orderItem.orderNO + ")'>立即评价</button></div>";
			}
			orderListHtml += "</div>";
			orderListHtml += "</div>";
			
		
		}
		
		
		
	}
	
	return orderListHtml;
	
	
}
function settlement(id){
	messageBox("结算订单","确定支付吗？",function(){
		messageBox("结算订单","结算成功!");
		
	},function(){
		
	});
}
