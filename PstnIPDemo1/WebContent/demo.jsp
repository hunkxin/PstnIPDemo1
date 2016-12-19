<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
</head>
<body>
	<h1>PSTN与IP电话混合通话DEMO1</h1>
	<br>
	<button id="createcall">创建会议室</button>
	<div id = newcaller>
	
	</div>
	<br>
	<button id="showdb">显示已创建的会议室</button>
	<br>
	<div id = "showconfid">
	</div>
	<div id = "showsingleconf">
	</div>
	<div id = "dailall">
	</div>
</body>
</html>