<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="right_col" role="main">
	<div class="">
			<div class="page-title">
				<div class="title_left">
					<h4>Báo cáo bán hàng</h4>
				</div>
				<div class="clearfix"></div>
			</div>
				<div class="clearfix"></div>
			<div class="clearfix"></div>
					<div class="row">
						<div class="col-md-12 col-sm-12 ">
							<div class="x_panel">
								<div class="x_content">
								 <br />
									<c:url value="/goods-issue/report/list" var="searchUrl"></c:url>
									<form:form servletRelativeAction="${searchUrl}" method="POST" modelAttribute="searchForm" cssClass="form-horizontal form-label-left">
 
										<div class="item form-group">
											<label class="col-form-label col-md-3 col-sm-3 label-align" for="name"> Từ   <span class="required"> </span>
											</label>
											<div class="col-md-6 col-sm-6 ">
												 
												<form:input path="fromDate"  type="date" cssClass="form-control"/>
											</div>
 
										</div>
										<div class="item form-group">
											<label class="col-form-label col-md-3 col-sm-3 label-align" for="name"> Đến  <span class="required"> </span>
											</label>
											<div class="col-md-6 col-sm-6 ">
											 	 
												<form:input path="toDate"  type="date" cssClass="form-control"/>
											</div>
 
											  
										</div>
										 <div class="item form-group">
											<label class="col-form-label col-md-3 col-sm-3 label-align" for="name"> Nhân viên   <span class="required"> </span>
											</label>
											<div class="col-md-6 col-sm-6 ">
											 	 
													<form:select path="userId" cssClass="form-control">
															<form:option value="" >Tất cả</form:option>
															<c:forEach items="${lstUsers}" var="item">
																<form:option value="${item.id}">${item.username}</form:option>
															</c:forEach>
													</form:select>
											</div>
 
											  
										</div>
										  
										<div class="ln_solid"></div>
										<div class="item form-group">
											<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
												<button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-search"></i> Search</button>												
												<button class="btn btn-primary" type="reset"><i class="glyphicon glyphicon-refresh"></i> Reset</button>												
											</div>
										</div>

									</form:form>
 								</div>
							</div>
						</div>
					</div>


	<div class="table-responsive">
                      <table class="table table-striped jambo_table bulk_action">
                        <thead>
                          <tr class="headings">
                            <th class="column-title">STT</th>
                            <th class="column-title">Mã nhân viên </th>
                            <th class="column-title">Nhân viên</th> 
                            <th class="column-title">Thành tiền</th>
                        
                          </tr>
                        </thead>

                        <tbody>
                          <c:forEach items="${list}" var="item" varStatus="i"> 
                          	<tr>
                            <td>${pageInfo.offSet + i.index + 1} </td>
                            <td>${item.userId}</td>   
                            <td>${item.name}</td>   
                        	<td><fmt:formatNumber value="${item.price}"  pattern="###,#00"/></td>
                        	  
                          	</tr>
                          </c:forEach>
							<tr>
								<td colspan="3"></td> 
								<td  ><fmt:formatNumber value="${totalprice}"  pattern="###,#00"/> </td>
							</tr>
							
                        </tbody>
                         
                      </table>
     <jsp:include page="/WEB-INF/views/layout/paging.jsp"/>                      


      </div>
 
	</div>
</div>

<script type="text/javascript">
	function gotoPage(page){
		$("#searchForm").attr('action',"<c:url value='/goods-issue/report/list//'/>"+page);
		$("#searchForm").submit();
	}
 
	$(document).ready(function(){
		var msgError = '${msgError}';
		var msgSuccess ='${msgSuccess}';
		if(msgError){
			new PNotify({
		        title: 'Thông Báo',
		        text: msgError,
		        type: 'error',
		        styling: 'bootstrap3'
		        
		    });	
		}
		if(msgSuccess){
			new PNotify({
		        title: 'Thông Báo',
		        text: msgSuccess,
		        type: 'success',
		        styling: 'bootstrap3'
		    });	
		}
	});
	
	$(document).ready(function(){
		$('#reportIssued').parents("li").addClass('active').siblings().removeClass("active");
		$('#reportIssued').addClass('current-page').siblings().removeClass("current-page");
		$('#reportIssued').parents().show();
	});
	

	
</script>



