<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="inc/head.jsp"%>
<div class="content-box">
	<div class="content-box-header">
		<h3>写资讯</h3>
	</div>
	<div class="content-box-content">
		<div class="tab-content default-tab" id="tab1">
			<html:form action="/information/save.do">
				<fieldset>
					<p>
						<label>标题</label>
						<html:textfield name="information.title" cssStyle="text-input small-input" />
						<span class="input-notification success png_bg">Successful message</span> 
						<br /> 
						<small>资讯标题100字以内</small>
					</p>
					<p>
						<label>作者</label>
						<html:textfield name="information.author" cssStyle="text-input" />
						<span class="input-notification error png_bg">Error message</span>
					</p>
					<p>
						<label>来源时间</label>
						<html:textfield name="information.sourceDate"
							cssStyle="text-input"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" />
						<br /> <small>资讯的来源时间:yyyy-MM-dd</small>
					</p>
					<p>
						<label>栏目分类</label> 
						<select name="information.category" class="small-input">
							<c:forEach items="${categorys}" var="e">
								<c:choose>
									<c:when test='${e.type eq 0 }'>
										<option value="${e.code}">${e.name }</option>
									</c:when>
								</c:choose>
							</c:forEach>
						</select>
					</p>
					<p>
						<label>引导图</label>
						<input type="file" class="text-input  small-input" name="file"/>
						<br/>
						<br/>
						<img src="${pageContext.request.contextPath}/resources/images/test.png" width="250px" height="250px"/>
					</p>
					<p>
						<label>附件</label>
						<input type="file" class="text-input  small-input" name="file"/>
					</p>
					<p>
						<label>资讯内容</label>
						<textarea class="text-input textarea wysiwyg" name="information.content" cols="79" rows="15"></textarea>
					</p>
					<p>
						<label>选项</label>
						<html:checkbox inputValue="true" name="information.home" />&nbsp;主页显示
						&nbsp;&nbsp;
						<html:checkbox inputValue="true" name="information.top" />&nbsp;置顶
						&nbsp;&nbsp;
						<html:checkbox inputValue="true" name="information.hot" />&nbsp;热点
						&nbsp;&nbsp;
						<html:checkbox inputValue="true" name="information.status" />&nbsp;发布
					</p>
					<p>
						<label>发布时间段</label> 开始时间&nbsp;
						<html:textfield name="information.releaseDate"
							cssStyle="text-input"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" />
						~ 结束时间&nbsp;
						<html:textfield name="information.endReleaseDate"
							cssStyle="text-input"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" />
					</p>
					<p>
						<label>关键字</label>
						<html:textfield name="information.keywords" cssStyle="text-input large-input" />
						<br /> 
						<small>多个关键字用逗号分隔，如：关键字1，关键字2</small>
					</p>
					<p>
						<label>描述</label>
						<html:textarea name="information.description" cssStyle="text-input" />
					</p>
					<p>
						<input class="button" type="submit" value="Submit" />
					</p>
				</fieldset>
				<div class="clear"></div>
			</html:form>
		</div>
	</div>
</div>
<%@include file="inc/footer.jsp"%>