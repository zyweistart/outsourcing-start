<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/tld/fn.tld" prefix="fn" %>
<%@taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@taglib uri="/WEB-INF/tld/html-tag.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
<!-- jQuery WYSIWYG Plugin -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.wysiwyg.js"></script>
<!-- jQuery Datepicker Plugin -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.datePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.date.js"></script>
<!-- My97DatePicker -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/calendar/WdatePicker.js"></script>


<!-- jQuery Configuration -->
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/resources/scripts/simpla.jquery.configuration.js"></script>

</head>
<body>
	<div id="body-wrapper">
	
	  <div id="main-content">
	  <noscript>
		<div class="notification error png_bg">
		  <div> Javascript is disabled or is not supported by your browser. Please <a href="http://browsehappy.com/" title="Upgrade to a better browser">upgrade</a> your browser or <a href="http://www.google.com/support/bin/answer.py?answer=23852" title="Enable Javascript in your browser">enable</a> Javascript to navigate the interface properly.
		    Download From <a href="http://www.exet.tk">exet.tk</a></div>
		</div>
		</noscript>
		<h2>亲爱的，辰羽 </h2>
		<p id="page-intro">你想做什么呢?</p>
		<ul class="shortcut-buttons-set">
		  <li><a class="shortcut-button" href="${pageContext.request.contextPath}/information/view.do"><span> <img src="${pageContext.request.contextPath}/admin/resources/images/icons/pencil_48.png" alt="icon" /><br />
		    写资讯 </span></a></li>
		  <li><a class="shortcut-button" href="#"><span> <img src="${pageContext.request.contextPath}/admin/resources/images/icons/paper_content_pencil_48.png" alt="icon" /><br />
		    创建页面 </span></a></li>
		  <li><a class="shortcut-button" href="#"><span> <img src="${pageContext.request.contextPath}/admin/resources/images/icons/image_add_48.png" alt="icon" /><br />
		    上传图像 </span></a></li>
		  <li><a class="shortcut-button" href="#"><span> <img src="${pageContext.request.contextPath}/admin/resources/images/icons/clock_48.png" alt="icon" /><br />
		    添加事件 </span></a></li>
		  <li><a class="shortcut-button" href="#messages" rel="modal"><span> <img src="${pageContext.request.contextPath}/admin/resources/images/icons/comment_48.png" alt="icon" /><br />
		    Open Modal </span></a></li>
		</ul>
		<div class="clear"></div>