$(getAllAddress);
function getAllAddress() {

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
			$("#addressList").html("暂无收货地址，你可以 <a href='javascript:showAddAddressPage()'>添加收货地址</a>");
			return;
		}

		var addressListHTML = "<div class='address-list-head'>";
		addressListHTML += "<div class='address-list-col'>收货人</div>";
		addressListHTML += "<div class='address-list-col'>联系电话</div>";
		addressListHTML += "<div class='address-list-col'>邮编</div>";
		addressListHTML += "<div class='address-list-col'>地址</div>";
		addressListHTML += "<div class='address-list-col'>操作</div>";
		addressListHTML += "</div><div class='address-list-body'>";
		for(var i in addressList) {
			addressListHTML += "<div class='address-list-item' id=" + addressList[i].addrNO + ">"
			addressListHTML += "<div class='address-list-col'>" + addressList[i].receiver + "</div>";
			addressListHTML += "<div class='address-list-col'>" + addressList[i].phone + "</div>";
			addressListHTML += "<div class='address-list-col'>" + addressList[i].postcode + "</div>";
			addressListHTML += "<div class='address-list-col'>" + addressList[i].receivingAddress + "</div>";
			addressListHTML += "<div class='address-list-col'><button onclick='showEditAddress()'>编辑</button><button onclick='deleteAddress()'>删除</button></div>";
			addressListHTML += "</div></div>";
		}
		$("#addressList").html(addressListHTML);
	}, "json");
}

function addAddress() {
	var parameter = formatAddressForm($("#newAddress"));
	if(!parameter) {
		return
	}

	$.post(window.ctx + "/address/addAddress", parameter, function(json) {
		console.log(json);
		if(!json.result) {
			messageBox("收货地址", json.message, function() {
				jsonCodeTest(json.code); //根据返回码进行相应操作
			});
			return;
		}
		getAllAddress(); //重新加载收货地址信息
		cancelAddAddress();//隐藏添加界面
	}, "json");
}

function editAddress() { //修改信息
	var parameter = formatAddressForm($("#editAddress"));
	if(!parameter) {
		return
	}

	$.post(window.ctx + "/address/updateAddress", parameter, function(json) {
		console.log(json);

		if(!json.result) {
			messageBox("编辑收货地址", json.message, function() {
				jsonCodeTest(json.code);
			});
			return;
		}

		getAllAddress();
		cancelEditAddress();

	}, "json");

}

function deleteAddress() {
	id = window.event.srcElement.parentElement.parentElement.id;
	messageBox("删除收货地址", "确定要删除这个收货地址吗？", function() { //确定按钮
		$.post(window.ctx + "/address/deleteAddress", {
			"addrNO": id
		}, function(json) {
			console.log(json);
			if(!json.result) {
				messageBox("收货地址", json.message, function() {
					jsonCodeTest(json.code); //根据返回码进行相应操作
				});
				return;
			}
			getAllAddress(); //重新加载收货地址信息

		}, "json");

	}, function() { //取消按钮

	});
}

function showEditAddress() { //显示编辑收货地址页面
	var item = $(window.event.srcElement.parentElement.parentElement).find("div[class=address-list-col]");
	var receiver = item[0].innerText; //联系人
	var phone = item[1].innerText; //联系电话
	var postcode = item[2].innerText; //邮编
	var receivingAddress = item[3].innerText; //收货地址
	var id = window.event.srcElement.parentElement.parentElement.id;
	var pcqp = ""; //省
	var pcac = ""; //市
	var pcaa = ""; //县
	var street = ""; //街道

	for(var i in window.PCAP) { //遍历查找出省
		if(receivingAddress.startsWith(window.PCAP[i])) {
			pcqp = window.PCAP[i];

			receivingAddress = receivingAddress.substring(pcqp.length); //删除省的信息
			for(var j in window.PCAC[i]) { //遍历查找出市
				if(receivingAddress.startsWith(window.PCAC[i][j])) {
					pcac = window.PCAC[i][j];

					receivingAddress = receivingAddress.substring(pcac.length); //删除市的信息
					for(var k in window.PCAA[i][j - 1]) { //遍历查找出县
						if(receivingAddress.startsWith(window.PCAA[i][j - 1][k])) {
							pcaa = window.PCAA[i][j - 1][k];
							street = receivingAddress.substring(pcaa.length); //删除县的信息，最后剩下的就是街道信息
							break;
						}
					}
					break;
				}
			}
			break;
		}
	}

	console.log(pcqp, pcac, pcaa, street, receiver, phone, postcode);
	$("#editAddressMask").css("display", "block");

	var form = $("#editAddress");
	form.attr("addrNO", id);
	form.find("input[name=receiver]").val(receiver);
	form.find("input[name=phone]").val(phone);
	form.find("input[name=postcode]").val(postcode);
	form.find("input[name=receiver]").val(receiver);
	new PCAS("province2", "city2", "area2", pcqp, pcac, pcaa); //创建地址选择三级联动
	form.find("input[name=street]").val(street);
}



function cancelEditAddress() { //取消编辑,关闭页面
	$("#editAddressMask").css("display", "none");
}

function showAddAddressPage() { //显示添加收货地址页面
	$("#addAddressMask").css("display", "block");
	new PCAS("province", "city", "area"); //创建地址选择三级联动，已经有了的相对于重置一些，解决form.reset()无法重置的问题
}

function cancelAddAddress() { //取消添加,隐藏添加界面
	$("#addAddressMask").css("display", "none");
	$("#newAddress")[0].reset();//重置表单，这里会重置不干净，因为一些问题市区县区的下拉选择框无法重置
}

function formatAddressForm(form) {
	form = $(form);
	var addrNO = form.attr("addrNO");
	var receiver = form.find("input[name=receiver]").val(); //联系人
	var phone = form.find("input[name=phone]").val(); //联系电话
	var postcode = form.find("input[name=postcode]").val(); //邮编
	var province = form.find("select[name=province]").val(); //收货地址,省
	var city = form.find("select[name=city]").val();
	var area = form.find("select[name=area]").val();
	var street = form.find("input[name=street]").val();

	if(!formMatch("CHINESE", receiver) || receiver.length < 2 || receiver.length > 4) {
		messageBox("修改失败", "联系人格式不正确，长度为2-4个汉字。");
		return null;
	}
	if(!formMatch("MOBILE", phone)) {
		messageBox("修改失败", "联系电话格式不正确！");
		return null;
	}
	if(!formMatch("POSTCODE", postcode)) {
		messageBox("修改失败", "邮编格式不正确！");
		return null;
	}

	if(!province || !city || !area) {
		messageBox("修改失败", "收货地址格式不正确！");
		return null;
	}
	if(!street || street.length < 4 || street.length > 12) {
		messageBox("修改失败", "详细街道格式不正确！长度为4-12个汉字。");
		return null;
	}

	return {
		"addrNO": addrNO,
		"receiver": receiver,
		"phone": phone,
		"postcode": postcode,
		"receivingAddress": province + city + area + street
	};
}