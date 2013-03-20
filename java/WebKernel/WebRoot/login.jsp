<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Simpla Admin | Sign In by www.865171.cn</title>
<!-- Reset Stylesheet -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/resources/css/reset.css" type="text/css" media="screen" />
<!-- Main Stylesheet -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/resources/css/style.css" type="text/css" media="screen" />
<!-- Invalid Stylesheet. This makes stuff look pretty. Remove it if you want the CSS completely valid -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/resources/css/invalid.css" type="text/css" media="screen" />
</head>
<body id="login">
	<div id="login-wrapper" class="png_bg">
	  <div id="login-top">
	    <h1>Simpla Admin</h1>
	    <a href="#">
	    	<img id="logo" src="${pageContext.request.contextPath}/admin/resources/images/logo.png" alt="Simpla Admin logo" />
	    </a> 
	  </div>
	  <div id="login-content">
	    <form action="${pageContext.request.contextPath}/admin/index.jsp">
	      <div class="notification information png_bg">
	        <div> Just click "Sign In". No password needed. </div>
	      </div>
	      <p>
	        <label>账户</label>
	        <input class="text-input" type="text" />
	      </p>
	      <div class="clear"></div>
	      <p>
	        <label>密码</label>
	        <input class="text-input" type="password" />
	      </p>
	      <div class="clear"></div>
	      <p id="remember-password">
	        <input type="checkbox" />记住密码 
	      </p>
	      <div class="clear"></div>
	      <p>
	        <input class="button" type="submit" value="登陆" />
	      </p>
	    </form>
	  </div>
	</div>
</body>
</html>