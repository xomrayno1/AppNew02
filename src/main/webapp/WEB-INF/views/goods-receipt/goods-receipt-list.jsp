<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="right_col" role="main">
	<div class="">
			<div class="page-title">
				<div class="title_left">
					<h4>Nhập hàng</h4>
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
									<c:url value="/goods-receipt/list/1" var="searchUrl"></c:url>
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
                            <th class="column-title">Mã hoá đơn</th>
                            <th class="column-title">Nhà cung cấp</th> 
                            <th class="column-title">Thời gian</th>                      
                            <th class="column-title">Thành tiền</th> 
                            <th class="column-title">Người tạo đơn </th>
                            <th class="column-title no-link last text-center" colspan="3" ><span class="nobr">+ </span>
                            </th>
                          </tr>
                        </thead>

                        <tbody>
                          <c:forEach items="${list}" var="item" varStatus="i"> 
                          	<tr>
                            <td>${pageInfo.offSet + i.index + 1} </td>
                            <td>${item.id}</td>  
                            <td>
                        		${item.objectName}
                        	</td>
                            <td>
                           	 	<fmt:formatDate value="${item.createDate}" pattern="dd/MM/yyyy"/>
                            </td>                      
                        	 
                        	<td><fmt:formatNumber value="${item.totalPrice}"  pattern="###,#00"/></td>
                        	<td>${item.user.name}</td>	
                            <td colspan="3" class="last text-center">
                            	 
	                            <a href='<c:url value="/goods-receipt/view/${item.id}"></c:url>' class="btn btn-primary"><i class="glyphicon glyphicon-eye-open"></i></a> 
	                            
                            </td>                  
                          	</tr>
                          </c:forEach>
							
                        </tbody>
                      </table>
     <jsp:include page="/WEB-INF/views/layout/paging.jsp"/>                      


      </div>

		 
	</div>
</div>

<script type="text/javascript">
	function gotoPage(page){
		$("#searchForm").attr('action',"<c:url value='/goods-receipt/list/'/>"+page);
		$("#searchForm").submit();
	}
	function deleteItem(id){
		if(confirm("Bạn có chắc chắn xóa nó không ?")){
			location.href="<c:url value='/goods-receipt/delete/'/>"+id;
		}
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
		$('#goodsreceiptlist').parents("li").addClass('active').siblings().removeClass("active");
		$('#goodsreceiptlist').addClass('current-page').siblings().removeClass("current-page");
		$('#goodsreceiptlist').parents().show();
	});
	

	
</script>



