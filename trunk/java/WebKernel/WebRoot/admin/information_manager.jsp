<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="inc/head.jsp"%>
<div class="content-box">
	<div class="content-box-header">
		<h3>资讯管理</h3>
	</div>
	<div class="content-box-content">
		<html:form action="/information.do">
			<table>
				<tr>
					<td>标题&nbsp;<html:textfield name="information.title" cssStyle="text-input" /></td>
					<td>作者&nbsp;<html:textfield name="information.author" cssStyle="text-input" /></td>
					<td>来源时间&nbsp;<html:textfield name="information.sourceDate" cssStyle="text-input" /></td>
					<td><input type="submit" class="button" value="开始搜索" /></td>
				</tr>
			</table>
		</html:form>
		<table>
			<thead>
				<tr>
					<th><input class="check-all" type="checkbox" /></th>
					<th>标题</th>
					<th>作者</th>
					<th>来源时间</th>
					<th>栏目分类</th>
					<th>管理</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<td colspan="6">
						<div class="bulk-actions align-left">
							<select name="dropdown">
								<optgroup label="行为方式">
									<option value="delete">删除</option>
									<option value="release">发布</option>
									<option value="cancel">撤销</option>
								</optgroup>
							</select> <a class="button" href="#">执行</a>
						</div> 
						<%@include file="inc/pagination.jsp"%>
					</td>
				</tr>
			</tfoot>
			<tbody>
				<c:forEach items="${informations}" var="e">
					<tr>
						<td><input type="checkbox" /></td>
						<td>${e.title}</td>
						<td><a href="#" title="title">${e.author}</a></td>
						<td>${e.sourceDate}</td>
						<td>${e.category}</td>
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