<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="./_g/head.jsp" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>未找到页面_UDRS系统</title>
<style>
body{text-align:center}
.i404{width:375px;height:175px;margin:200px auto 0;background:url("${basePath}/resources/yc_udrs/imgs/i404.png") no-repeat;}
p{margin:20px auto 40px;font-size:24px;font-weight:bold;}
a{display:block;width:344px;height:48px;margin:0 auto;background:url("${basePath}/resources/yc_udrs/imgs/i_home.png") no-repeat;}
</style>
</head>
<body>

<!--html start-->
<div class="i404"></div>
<p>抱歉！系统正在升级...</p>
<a href="${basePath}/"></a>
<!--html end-->

</body>
</html>