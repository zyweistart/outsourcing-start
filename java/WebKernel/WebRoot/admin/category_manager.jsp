<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="inc/head.jsp"%>
<div class="content-box">
	<div class="content-box-header">
		<h3>栏目分类</h3>
	</div>
	<div class="content-box-content">
		<html:form action="/category.do">
			<table>
				<tr>
					<td>名称&nbsp;
						<html:textfield name="category.name" cssStyle="text-input"/>
					</td>
					<td>上级&nbsp;
						<select name="category.parent" >
							<option value="">顶级</option>
							<c:forEach items="${categorys}" var="e">
								<option value="${e.code}">${e.name }</option>
							</c:forEach>
						</select>
					</td>
					<td>类型&nbsp; 
						<select name="category.type">
								<option value="0">顶级</option>
								<option value="1">单页面</option>
								<option value="2">列表页</option>
								<option value="3">图文列表</option>
						</select>
					</td>
					<td><input type=submit class="button" value="开始搜索" /></td>
				</tr>
			</table>
		</html:form>
		<table>
			<thead>
				<tr>
					<th><input class="check-all" type="checkbox" /></th>
					<th>名称</th>
					<th>父级</th>
					<th>类型</th>
					<th>描述</th>
					<th>管理</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<td colspan="6">
						<div class="bulk-actions align-left">
							<select name="dropdown">
								<option value="option1">事件</option>
								<option value="option3">删除</option>
								<option value="option3">发布</option>
								<option value="option3">撤销</option>
							</select> 
							<a class="button" href="#">应用</a> 
							<a class="button" href="${pageContext.request.contextPath}/category/view.do">创建栏目</a>
						</div>
						<%@include file="inc/pagination.jsp"%>
					</td>
				</tr>
			</tfoot>
			<tbody>
			<c:forEach items="${categorys}" var="e">
				<tr>
					<td><input type="checkbox" /></td>
					<td>${e.name}</td>
					<td><a href="#" title="title">${e.parent}</a></td>
					<td>${e.type}</td>
					<td>${e.description}</td>
					<td>
						<a href="#" title="Edit"> 
							<img src="${pageContext.request.contextPath}/admin/resources/images/icons/pencil.png" alt="Edit" />
						</a> 
						<a href="#" title="Delete"> 
							<img src="${pageContext.request.contextPath}/admin/resources/images/icons/cross.png" alt="Delete" />
						</a> 
						<a href="#" title="Edit Meta"> 
							<img src="${pageContext.request.contextPath}/admin/resources/images/icons/hammer_screwdriver.png" alt="Edit Meta" />
						</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<%@include file="inc/footer.jsp"%>