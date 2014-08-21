<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    
    <title>My JSP 'picshow.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="./WEB-INF/jquery/jquery-1.9.1.min.js">
	
	var pp=0;
	$(function(){
	  $.get("http://3.weixinowner.sinaapp.com/BaiDuPicServLet", { picid:0 },//getUrlParam('picid')
        function(data){
           alert(data);
           $("#meinv").attr('src',data.url);
           pp=data.picid;
        },"json");
	});
	
	function pre()
	{
	   $.get("http://3.weixinowner.sinaapp.com/BaiDuPicServLet", { picid: parseInt(pp)+1},
        function(data){
           alert(data);
           $("#meinv").attr('src',data.url);
           pp=data.picid;
        },"json");
	}
	function next()
	{
	   $.get("http://3.weixinowner.sinaapp.com/BaiDuPicServLet", { picid: parseInt(pp)+1},
        function(data){
           alert(data);
           $("#meinv").attr('src',data.url);
           pp=data.picid;
        },"json");
	}
	
	function getUrlParam(name)
	{
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg);  //匹配目标参数
		if (r!=null) return unescape(r[2]); return null; //返回参数值
	}
	
	</script>

  </head>
  
  <body style="background-color: #009ddc">
  

	  <a onclick="pre()" id="pre" style=" float: left;z-index: 9999;height: 100%;width: 50px;background-position: 19px center; background: url(./WEB-INF/images/previous-arrow.png) no-repeat 24px center;"></a>

	  <div align="center"  style=" float:left; foverflow: hidden;">
	     <img alt="meimei" align="middle" style="display: block;" id="meinv" src="./WEB-INF/images/4e2cde18e568834c5c0000ce.jpg" />
	  </div>

	  <a onclick="next()" id="next" style=" float:right;z-index: 9999;height: 100%;width: 50px;background-position: 19px center; background: url(./WEB-INF/images/next-arrow.png) no-repeat 24px center;"></a>
	  
    
  </body>
</html>
