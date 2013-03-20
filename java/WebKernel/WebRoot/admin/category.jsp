<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="inc/head.jsp" %>
<div class="content-box">
	<div class="content-box-header">
		<h3>创建栏目</h3>
	</div>
	<div class="content-box-content">
		<div class="tab-content default-tab" id="tab1">
			<html:form action="/category/save.do">
				<fieldset>
					<p>
						<label>名称</label>
						<html:textfield name="category.name" cssStyle="text-input" />
					</p>
					<p>
						<label>上级</label>
						<select name="category.parent" class="small-input">
							<option value="">顶级</option>
							<c:forEach items="${categorys}" var="e">
								<option value="${e.code}">${e.name }</option>
							</c:forEach>
						</select>
					</p>
					<p>
						<label>类型</label>
						<select name="category.type" class="small-input">
							<option value="0">顶级</option>
							<option value="1">单页面</option>
							<option value="2">列表页</option>
							<option value="3">图文列表</option>
						</select>
					</p>
					<p>
						<label>状态</label> 
						<select name="category.status" class="small-input">
							<option value="1">启用</option>
							<option value="2">禁用</option>
						</select>
					</p>
					<p>
						<label>描述</label>
						<html:textarea name="category.description" cssStyle="text-input" />
					</p>
					<p>
						<label>排序</label>
						<html:textfield name="category.sort" cssStyle="text-input"/>
					</p>
					<p>
						<input type="submit"  class="button"  value="Submit" />
					</p>
				</fieldset>
				<div class="clear"></div>
			</html:form>
		</div>
	</div>
</div>
<%@include file="inc/footer.jsp"%>