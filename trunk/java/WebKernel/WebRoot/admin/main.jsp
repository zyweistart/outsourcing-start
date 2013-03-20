<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Simpla Admin by www.865171.cn</title>
<!--                       CSS                       -->
<!-- Reset Stylesheet -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/resources/css/reset.css" type="text/css" media="screen" />
<!-- Main Stylesheet -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/resources/css/style.css" type="text/css" media="screen" />
<!-- Invalid Stylesheet. This makes stuff look pretty. Remove it if you want the CSS completely valid -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/resources/css/invalid.css" type="text/css" media="screen" />
<!--                       Javascripts              -->
<!-- jQuery -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery-1.3.2.min.js"></script>
<!-- Facebox jQuery Plugin -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/facebox.js"></script>

<!-- jQuery Configuration -->
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/resources/scripts/simpla.jquery.configuration.js"></script>

</head>
<body  style= 'overflow:scroll;overflow:hidden '>
	<div id="body-wrapper">
	  <div id="sidebar">
	    <div id="sidebar-wrapper">
	      <h1 id="sidebar-title"><a href="#">Simpla Admin</a></h1>
	      <a href="#"><img id="logo" src="${pageContext.request.contextPath}/admin/resources/images/logo.png" alt="Simpla Admin logo" /></a>
	      <div id="profile-links"> 
	      	Hello, <a href="#" title="Edit your profile">魏振耀</a>, 
	      	你有 <a href="#messages" rel="modal" title="3 Messages">3 条消息</a><br /><br />
	        <a href="#" title="View the Site">设置</a> | <a href="#" title="Sign Out">退出</a> </div>
	      <ul id="main-nav">
	        <li> <a href="#" class="nav-top-item no-submenu">仪表盘 </a> </li>
	        <li> <a href="#" class="nav-top-item current">资讯 </a>
	          <ul>
	            <li><a href="${pageContext.request.contextPath}/information/view.do" target="iframemaincontent">写资讯</a></li>
	            <li><a class="current" href="${pageContext.request.contextPath}/information.do" target="iframemaincontent">资讯管理</a></li>
	            <li><a href="${pageContext.request.contextPath}/category.do" target="iframemaincontent">栏目分类</a></li>
	            <li><a href="#">资讯评论</a></li>
	          </ul>
	        </li>
	        <li> <a href="#" class="nav-top-item"> 页面</a>
	          <ul>
	            <li><a href="#">创建</a></li>
	            <li><a href="#">管理</a></li>
	          </ul>
	        </li>
	        <li> <a href="#" class="nav-top-item">图像 </a>
	          <ul>
	            <li><a href="#">上传</a></li>
	            <li><a href="#">类别</a></li>
	            <li><a href="#">专辑</a></li>
	            <li><a href="#">设置</a></li>
	          </ul>
	        </li>
	        <li> <a href="#" class="nav-top-item"> 事件日期 </a>
	          <ul>
	            <li><a href="#">日期概述</a></li>
	            <li><a href="#">添加事件</a></li>
	            <li><a href="#">日期设置</a></li>
	          </ul>
	        </li>
	        <li> <a href="#" class="nav-top-item"> 系统设置</a>
	          <ul>
	            <li><a href="#">General</a></li>
	            <li><a href="#">设计</a></li>
	            <li><a href="#">用户权限</a></li>
	            <li><a href="#">个性化设置</a></li>
	          </ul>
	        </li>
	      </ul>
	      <!-- End #main-nav -->
	      <div id="messages" style="display: none">
	        <!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
	        <h3>3 Messages</h3>
	        <p> <strong>17th May 2009</strong> by Admin<br />
	          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus magna. Cras in mi at felis aliquet congue. <small><a href="#" class="remove-link" title="Remove message">Remove</a></small> </p>
	        <p> <strong>2nd May 2009</strong> by Jane Doe<br />
	          Ut a est eget ligula molestie gravida. Curabitur massa. Donec eleifend, libero at sagittis mollis, tellus est malesuada tellus, at luctus turpis elit sit amet quam. Vivamus pretium ornare est. <small><a href="#" class="remove-link" title="Remove message">Remove</a></small> </p>
	        <p> <strong>25th April 2009</strong> by Admin<br />
	          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus magna. Cras in mi at felis aliquet congue. <small><a href="#" class="remove-link" title="Remove message">Remove</a></small> </p>
	        <form action="#" method="post">
	          <h4>New Message</h4>
	          <fieldset>
	          <textarea class="textarea" name="textfield" cols="79" rows="5"></textarea>
	          </fieldset>
	          <fieldset>
	          <select name="dropdown" class="small-input">
	            <option value="option1">Send to...</option>
	            <option value="option2">Everyone</option>
	            <option value="option3">Admin</option>
	            <option value="option4">Jane Doe</option>
	          </select>
	          <input class="button" type="submit" value="Send" />
	          </fieldset>
	        </form>
	      </div>
	      <!-- End #messages -->
	    </div>
	  </div>
	  <!-- End #sidebar -->
	  <iframe src="${pageContext.request.contextPath}/information.do" name="iframemaincontent"  width="100%" height="100%" frameBorder="0"></iframe>
	</div>
</body>
</html>