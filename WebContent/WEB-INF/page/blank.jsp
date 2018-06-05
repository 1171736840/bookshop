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
		
		<title>${title}-网上书城</title>
	</head>

	<body>
		<%@ include file="/WEB-INF/page/include/nav.jsp" %>
		<div class="page">
			<h2 class="page-title">${pageTiele}</h2>
			<div id="pageMessage">${pageMessage}</div>
		</div>
		<script>
			window.onload = function(){
				var code = parseInt("${code}");
				jsonCodeTest(code);
			}
		</script>
	</body>

</html>