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
		<c:if test="${viewOnly == false}">
		<div class="table-responsive">
			<table class="table table-striped jambo_table bulk_action">
				<thead>
					<tr class="headings">
						<th class="column-title">STT</th>
						<th class="column-title">Tên</th>
						<th class="column-title">Giá</th>
						 
						<th class="column-title">Nhà cung cấp </th>
						<th width="20%" class="column-title no-link last text-center"
							colspan="3"><span class="nobr"> + </span></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listProduct}" var="item" varStatus="i">
								<tr>
									<td>${pageInfo.offSet + i.index + 1}</td>
									<td class="title-product">${item.name}</td>
									<td>${item.price}</td>
									 
									<td>${item.supplier.name}</td>
									<td colspan="3" class="last text-center"><a
										href="javascript:void(0)" onclick="orderItem(${item.id})"
										class="btn btn-warning"><i class="glyphicon glyphicon-edit"></i></a>
									</td>
								</tr>
					</c:forEach>
				</tbody>
			</table>
			<jsp:include page="/WEB-INF/views/layout/paging.jsp" />
		</div>
		</c:if>
		<div class="clearfix"></div>
					<div class="row">
						<div class="col-md-12 col-sm-12 ">
							<div class="x_panel">
								<div class="x_content">
									<br />
									<form:form servletRelativeAction="/goods-receipt/pay"  modelAttribute="submitForm" method="POST" cssClass="form-horizontal form-label-left">
										<form:hidden path="id"/>
										<form:hidden path="activeFlag"/>
										<form:hidden path="createDate"/>
										<form:hidden path="updateDate"/>
										<form:hidden path="invoiceType"/>
										<form:hidden path="objectType"/>
									 <div class="table-responsive">
												<table class="table table-striped jambo_table bulk_action">
													<thead>
														<tr class="headings">
															<th class="column-title">#</th>
															<th class="column-title">Tên thuốc </th>
															<th class="column-title">Giá</th>
															<th class="column-title">Số lượng</th>
															<th class="column-title">Thành tiền</th>
															<th class="column-title">Nhà cung cấp </th>
															 <c:if test="${viewOnly == false}">
																<th width="20%" class="column-title no-link last text-center"><span class="nobr"> + </span></th>
															 </c:if>
														</tr>
													</thead>
													<tbody>
														<c:if test="${invoice != null}">
															<c:forEach items="${invoice.listInvoice}" var="item" varStatus="i">
																<tr>
																	<td>${pageInfo.offSet + i.index + 1}</td>	
																	<td class="title-product">${item.product.name}</td>
																	<td>${item.price}</td>
																	<td>${item.quantity}</td>
																	<td>${item.totalPrice}</td>	
																	 <td class="title-product">${item.product.supplier.name}</td>
																	<c:if test="${viewOnly == false}">
																		<td class="last text-center"><a href="javascript:void(0)"
																			onclick="deleteItem(${item.product.id})" class="btn btn-danger"><i
																				class="glyphicon glyphicon-trash"></i></a></td>
																	</c:if>		
																</tr>
															</c:forEach>
														</c:if>
													</tbody>
													<tfoot>
														<tr>
															<td colspan="4"></td>
															<td>${invoice.totalPrice}</td>
															<td></td>
														</tr>
													</tfoot>
												</table>  
											</div> 
										<div class="ln_solid"></div>
											<div class="item form-group">
												<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
												<c:if test="${viewOnly == false}">
													<button type="button" onclick="pay()" class="btn btn-success"><i class="glyphicon glyphicon-ok-circle"></i> Submit</button>		
													<a href="#" onclick="cancelInvoice()"><button class="btn btn-primary" type="button"><i class="glyphicon glyphicon-minus-sign"></i> Cancel</button></a>																					
												</c:if>
												<c:if test="${viewOnly == true}">
													<a href="<c:url value='/goods-receipt/list/1'/>"><button class="btn btn-primary" type="button"><i class="glyphicon glyphicon-minus-sign"></i>Back</button></a>									
												</c:if>
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
 
	var invoice = '${invoice}';
	 
	function pay(id){
		if(invoice == ''){
			alert("Bạn chưa tạo đơn nhập");
		}else{
			if(confirm("Bạn có chắc chắn muốn thanh toán nó không ?")){
				$("#submitForm").attr('action',"<c:url value='/goods-receipt/pay'/>");
				$("#submitForm").submit();
			}
		}
	}
	
	function orderItem(id){
		if(confirm("Bạn có chắc chắn muốn đặt nó không ?")){
			location.href="<c:url value='/goods-receipt/order?id="+id+"'/>";
		}
	}
	function deleteItem(id){
		if(confirm("Bạn có chắc chắn muốn xoá nó không ?")){
			location.href="<c:url value='/goods-receipt/remove/'/>"+id;
		}
	}
	function cancelInvoice(){
 
		if(invoice == ''){
			alert("Bạn chưa tạo đơn nhập");
		}else{
			if(confirm("Bạn có chắc chắn muốn huỷ nó không ?")){
				location.href="<c:url value='/goods-receipt/cancel'/>";
			}
		}  
		
		 
	}
	function payInvoice(){
		if(confirm("Bạn có chắc chắn muốn thanh toán không ?")){
			location.href="<c:url value='/goods-receipt/pay'/>";
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
			var viewOnly = '${viewOnly}';
			if(viewOnly == 'true'){
				$('#goodsreceiptlist').addClass('current-page').siblings().removeClass("current-page");
				$("#goodsreceiptlist").parents("li").addClass("active").siblings().removeClass("active");
				$("#goodsreceiptlist").parents().show();				
			}else{
				$('#goodsReceipt').addClass('current-page').siblings().removeClass("current-page");
				$("#goodsReceipt").parents("li").addClass("active").siblings().removeClass("active");
				$("#goodsReceipt").parents().show();		
			}
		 	
		});	
	</script>