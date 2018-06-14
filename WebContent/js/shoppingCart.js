$(getAllShoppingCart);

function getAllShoppingCart() {
	$.post(window.ctx + "/getAllShoppingCart", {}, function(json) { //获取购物车数据
		console.log(json);
		var shoppingCart = json.shoppingCart;
		var shoppingCartPage = $("#shoppingCartPage").css("display", "block");
		if(shoppingCart == null || shoppingCart.length == 0) { //如果购物车没有物品
			shoppingCartPage.html("购物车中还没有物品，你可以 <a href='" + window.ctx + "/jsp/showAllBook'>浏览图书</a> 。");
			return;
		}
		shoppingCartHtml = "";
		for(var i in shoppingCart) {
			shoppingCartHtml += "<tr id='" + shoppingCart[i].id + "' bookISBN='" + shoppingCart[i].bookISBN + "'>";
			shoppingCartHtml += "<td><input type='checkbox' checked='checked' /></td>";
			shoppingCartHtml += "<td></td>";
			shoppingCartHtml += "<td></td>";
			shoppingCartHtml += "<td width=150px><div class='button-number-box'><button class='button-number-box-button'>-</button>"
			shoppingCartHtml += "<div class='button-number-box-number'>" + shoppingCart[i].number + "</div>";
			shoppingCartHtml += "<button class='button-number-box-button'>+</button></div></td>"
			shoppingCartHtml += "<td></td>";
			shoppingCartHtml += "<td><button class='detlet'>删除</button></td>";
			shoppingCartHtml += "</tr>";

			$.post(window.ctx + "/showBook", {
				"bookISBN": shoppingCart[i].bookISBN
			}, function(json) {
				console.log(json);
				$("[bookISBN=" + json.book.bookISBN + "]").each(function() {
					var td = $(this).find("td");
					td[1].innerHTML = "<a  href='${ctx}/jsp/showBook?bookISBN=" + json.book.bookISBN + "' target='_blank'><img class='shopping-cart-book-img' src='" + json.bookPicSrc + json.book.bookISBN + json.book.bookPic + "'/></a>";
					td[1].innerHTML += "<div class='book-name'><a class='bookName' href='${ctx}/jsp/showBook?bookISBN=" + json.book.bookISBN + "' target='_blank'>" + json.book.bookName + "</a></div>";
					td[2].innerHTML = "<span class='price'>￥" + json.book.bookPrice.toFixed(2);
					td[4].innerHTML = "<span type='smallPlan' class='price'>￥" + (json.book.bookPrice * parseInt($(td[3]).find(".button-number-box-number").text())).toFixed(2);

				});

				countPrice(); //统计总价格

			}, "json");
		}
		$("#shoppingCart").find("tbody").html(shoppingCartHtml);
		$("input[type='checkbox']").on("click", countPrice);
		$(".detlet").on("click", detele);
		$(".button-number-box-button").on("click", numberButton);
		$("#shoppingCart").find("input[type='checkbox']").on("click", testSelectAll);
		$("#shoppingCart").find("input[type='checkbox']").parent().on("click", selectInput);
		testSelectAll();
	}, "json");
}

function countPrice() { //计算商品总价
	var bookCountPrice = 0;
	$("[type='smallPlan']").each(function() {
		if($(this.parentElement.parentElement).find("input[type='checkbox']")[0].checked) { //判断是否选择商品
			bookCountPrice += parseFloat($(this).html().substring(1)); //累加订单总价
		}
	});
	$("#totalPrice").html("￥" + bookCountPrice.toFixed(2));
}

function detele() {
	var id = this.parentElement.parentElement.id;
	messageBox("删除商品","确定要删除购物车中的这个商品吗？",function(){
		
		$.post(window.ctx + "/deleteShoppingCart", {
			"id": id
		}, function(json) { //删除购物车中的某件商品
			console.log(json);
	
			messageBox("删除商品", json.message, function() {
				getAllShoppingCart();
			});
	
		});
	},function(){});
}

function numberButton() {
	var num = parseInt($(this.parentElement).find("div").text());
	if($(this).text() == "+") {
		num++;
		if(num > 99) {
			num = 99
		}
	} else {
		num--;
		if(num < 1) {
			num = 1;
		}
	}
	var id = this.parentElement.parentElement.parentElement.id;
	$.post(window.ctx + "/setShoppingCartNumber", {
		"id": id,
		"number": num
	}, function(json) { //删除购物车中的某件商品
		console.log(json);
		if(json.result) {
			var item = $("#" + json.id);
			item.find(".button-number-box").find("div").html(json.number);
			var bookPrice = parseFloat(item.find(".price")[0].innerText.substring(1));
			item.find(".price")[1].innerHTML = "￥" + (bookPrice * json.number).toFixed(2)
			countPrice(); //统计总价格
		} else {
			messageBox("修改数量", json.message);
		}
	});

}

function selectAll() {
	if(window.event.srcElement.checked) {
		$("#shoppingCart").find("input[type='checkbox']").each(function() {
			this.checked = true;
		});
	} else {
		$("#shoppingCart").find("input[type='checkbox']").each(function() {
			this.checked = false;
		});
	}
	countPrice(); //统计总价格
}

function testSelectAll() {
	var selectAll = true;
	$("#shoppingCart").find("input[type='checkbox']").each(function() {
		if(!this.checked) {
			selectAll = false;
		}
	});
	$("#allSelect")[0].checked = selectAll;
}

function selectInput() {
	$(window.event.srcElement).find("input").trigger("click"); //触发里面是input的click事件
}

function settlement() { //结算
	window.orderIdList = [];
	$("#shoppingCart").find(":checked").each(function() {
		if($(this.parentElement.parentElement).find("input[type='checkbox']")[0].checked) { //判断是否选择商品
			window.orderIdList.push(this.parentElement.parentElement.id);
		}
	});
	
	console.log(window.orderIdList);
	
	$.post(window.ctx + "/address/getAllAddress", {}, function(json) {
		console.log(json);
		if(!json.result) {
			messageBox("收货地址", json.message, function() {
				jsonCodeTest(json.code); //根据返回码进行相应操作
			});
			return;
		}

		var addressList = json.addressList || [];

		if(addressList.length == 0) {
			$("#addressListBody").html("暂无收货地址，你可以 <a href='javascript:showAddAddressPage()'>添加收货地址</a>");
			return;
		}

		var addressListHTML = "<div class='address-list-body'>";
		for(var i in addressList) {
			addressListHTML += "<div class='address-list-item' id=" + addressList[i].addrNO + ">"
			addressListHTML += "<div class='address-list-col'>" + addressList[i].receiver + "</div>";
			addressListHTML += "<div class='address-list-col'>" + addressList[i].phone + "</div>";
			addressListHTML += "<div class='address-list-col'>" + addressList[i].receivingAddress + "</div>";
			addressListHTML += "<div class='address-list-col'><button onclick='selectAddress()'>选择地址</button></div>";
			addressListHTML += "</div>";
		}
		$("#addressListBody").html(addressListHTML);
		showMask($("#selectAddressMask"));
	}, "json");
}
function selectAddress(){
	window.addressId = window.event.srcElement.parentElement.parentElement.id;
	createOrder()
}
function createOrder(){//创建订单
	$.post(window.ctx + "/order/createOrder", {"orderIds":window.orderIdList.join(","),"addressId":window.addressId}, function(json) {
		console.log(json);
		messageBox("创建订单", json.message, function() {
			if(!json.result) {
				jsonCodeTest(json.code); //根据返回码进行相应操作
			}else{
				window.location.href = window.ctx + "/jsp/myOrder";
			}
		});
		
		
	});
	
}
