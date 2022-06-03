<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
	<div class="right_col" role="main">
				<div class="">
					<div class="page-title">
						<div class="title_left">
							<h3>${title}</h3>
						</div>
					</div>
		<div class="clearfix"></div>
					<div class="row">
						<div class="col-md-12 col-sm-12 ">
							<div class="x_panel">
								<div class="x_content">
									<br />
									<form:form servletRelativeAction="/manage/user/save"  modelAttribute="submitForm" method="POST" cssClass="form-horizontal form-label-left">
										<form:hidden path="id"/>
										<form:hidden path="activeFlag"/>
										<form:hidden path="createDate"/>
										<form:hidden path="updateDate"/>						
										<c:if test="${submitForm.id == null || viewOnly}">
											<div class="item form-group">
												<label class="col-form-label col-md-3 col-sm-3 label-align" for="username">Tên tài khoản<span class="required">*</span>
												</label>
												<div class="col-md-6 col-sm-6 ">												
													<form:input path="username" cssClass="form-control" readonly="${viewOnly}"/>
													<div class="has-error">
														<form:errors path="username" cssClass="help-block"/>
													</div>
												</div>
											</div>
										</c:if>
										<c:if test="${submitForm.id != null && !viewOnly}">
											<form:hidden path="username" cssClass="form-control"/>
										</c:if>

										<div class="item form-group">
											<label class="col-form-label col-md-3 col-sm-3 label-align" for="password">Mật khẩu<span class="required">*</span>
											</label>
											<div class="col-md-6 col-sm-6 ">												
												<form:password showPassword="true" path="password" cssClass="form-control" readonly="${viewOnly}"/>
												<div class="has-error">
													<form:errors path="password" cssClass="help-block"/>
												</div>
											</div>
										</div>
										<div class="item form-group">
											<label class="col-form-label col-md-3 col-sm-3 label-align" for="name">Tên<span class="required">*</span>
											</label>
											<div class="col-md-6 col-sm-6 ">												
												<form:input path="name" cssClass="form-control" readonly="${viewOnly}"/>
												<div class="has-error">
													<form:errors path="name" cssClass="help-block"/>
												</div>
											</div>
										</div>
										<div class="item form-group">
											<label class="col-form-label col-md-3 col-sm-3 label-align" for="name">Email<span class="required">*</span>
											</label>
											<div class="col-md-6 col-sm-6 ">												
												<form:input path="email" cssClass="form-control" readonly="${viewOnly}"/>
												<div class="has-error">
													<form:errors path="email" cssClass="help-block"/>
												</div>
											</div>
										</div>
										<div class="item form-group">
											<label class="col-form-label col-md-3 col-sm-3 label-align" for="name">Quyền<span class="required">*</span>
											</label>
											<div class="col-md-6 col-sm-6 ">
											<c:if test="${viewOnly}">
												<form:select path="role" cssClass="form-control" readonly="${viewOnly}">
													<form:option value="1">Người quản trị</form:option>
													<form:option value="2"> Nhân viên</form:option>
												 
												</form:select>
											</c:if>
											<c:if test="${!viewOnly}">
												<form:select path="role" cssClass="form-control">
													<form:option value="1">Người quản trị</form:option>
													<form:option value="2">Nhân viên </form:option>
													 
												</form:select>
											</c:if>
												 										
											</div>
										</div>
				 
										<div class="ln_solid"></div>
											<div class="item form-group">
												<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
												<c:if test="${!viewOnly}">
													<button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-ok-circle"></i> Submit</button>
													<button class="btn btn-primary" type="reset"><i class="glyphicon glyphicon-refresh"></i> Reset</button>	
												</c:if>			
												<a href='<c:url value="/manage/user/list/1"></c:url>'><button class="btn btn-primary" type="button"><i class="glyphicon glyphicon-minus-sign"></i> Cancel</button></a>																					
												</div>
											</div>
									</form:form>
								</div>
							</div>
						</div>
					</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#userlist').addClass('current-page').siblings().removeClass("current-page");
			$("#userlist").parents("li").addClass("active").siblings().removeClass("active");
			$("#userlist").parents().show();			
		});	
	</script>