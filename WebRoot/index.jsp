<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-type" content="text/html;charset=UTF-8">
<title>上传图片--watermark</title>
</head>

<body>
	<h4>上传图片</h4>
	<hr />
	<form name="form1" action="${pageContext.request.contextPath}/watermark.action"
		method="post" enctype="multipart/form-data">
		<input type="file" name="image" /><br /> 
		<input type="file" name="image" /><br /> 
		<input type="file" name="image" /><br /> 
		<input type="file" name="image" /><br /> 
		<input type="file" name="image" /><br /><br /> 
		<input type="submit" value="上传图片" />
	</form>
	<hr />
</body>
</html>
