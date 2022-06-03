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
						<th class="column-title">Số lượng </th>
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
									<td>${item.quantity}</td>
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
									<form:form servletRelativeAction="/goods-issue/pay"  modelAttribute="submitForm" method="POST" cssClass="form-horizontal form-label-left">
										<form:hidden path="id"/>
										<form:hidden path="activeFlag"/>
										<form:hidden path="createDate"/>
										<form:hidden path="updateDate"/>
										<form:hidden path="invoiceType"/>
										<form:hidden path="objectType"/>
										<div class="item form-group"> 
											<label class="col-form-label col-md-3 col-sm-3 label-align" for="productId">Khách hàng <span class="required">*</span>
											</label>
											<div class="col-md-6 col-sm-6 ">												
													<form:select path="objectId" cssClass="form-control">
										 
															<c:forEach items="${listCustomer}" var="item">
																<form:option value="${item.id}">${item.name}</form:option>
															</c:forEach>
													</form:select>
												<div class="has-error">
													<form:errors path="objectId" cssClass="help-block"/>
												</div>
											</div>
										</div>
 
									 <div class="table-responsive">
												<table class="table table-striped jambo_table bulk_action">
													<thead>
														<tr class="headings">
															<th class="column-title">#</th>
															<th class="column-title">Tên thuốc </th>
															<th class="column-title">Giá</th>
															<th class="column-title">Số lượng</th>
															<th class="column-title">Thành tiền</th>
															 <c:if test="${viewOnly == false}">
																<th width="20%" class="column-title no-link last text-center"><span class="nobr"> + </span></th>
															</c:if>	
														</tr>
													</thead>
													<tbody>
														<c:if test="${invoiceIssued != null}">
															<c:forEach items="${invoiceIssued.listInvoice}" var="item" varStatus="i">
																<tr>
																	<td>${pageInfo.offSet + i.index + 1}</td>	
																	<td class="title-product">${item.product.name}</td>
																	<td>${item.price}</td>
																	<td>${item.quantity}</td>
																	<td>${item.totalPrice}</td>	
																	 
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
															<td>${invoiceIssued.totalPrice}</td>
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
													<a href="<c:url value='/goods-issue/list/1'/>"><button class="btn btn-primary" type="button"><i class="glyphicon glyphicon-minus-sign"></i>Back</button></a>									
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
	var objectId = '${invoiceIssued.objectId}';
	console.log(objectId)
	if(objectId != null && objectId.length > 0 ){
		$('#objectId').val(objectId);
	}
 
	
	function pay(id){
		if(objectId == null  || objectId == 'null' || objectId.length == 0 ){
			alert("Bạn chưa tạo đơn bán ");
		}else{
			if(confirm("Bạn có chắc chắn muốn thanh toán nó không ?")){
				$("#submitForm").attr('action',"<c:url value='/goods-issue/pay'/>");
				$("#submitForm").submit();
			}
		}
 
	}
	
	function orderItem(id){
		if(confirm("Bạn có chắc chắn muốn đặt nó không ?")){
			var customerId = $("#objectId").val();
			location.href="<c:url value='/goods-issue/order?id="+id+"&customerId="+customerId+"'/>";
		}
	}
	
	function deleteItem(id){
		if(confirm("Bạn có chắc chắn muốn xoá nó không ?")){
			location.href="<c:url value='/goods-issue/remove/'/>"+id;
		}
	}
	function cancelInvoice(){
		if(objectId == null  || objectId == 'null' || objectId.length == 0 ){
			alert("Bạn chưa tạo đơn bán ");
		}else{
			if(confirm("Bạn có chắc chắn muốn huỷ nó không ?")){
				location.href="<c:url value='/goods-issue/cancel'/>";
			}
		}
		 
	}
	function payInvoice(){
		if(confirm("Bạn có chắc chắn muốn thanh toán không ?")){
			location.href="<c:url value='/goods-issue/pay'/>";
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
				$('#goodsIssuedList').addClass('current-page').siblings().removeClass("current-page");
				$("#goodsIssuedList").parents("li").addClass("active").siblings().removeClass("active");
				$("#goodsIssuedList").parents().show();		
			}else{
				$('#goodsIssued').addClass('current-page').siblings().removeClass("current-page");
				$("#goodsIssued").parents("li").addClass("active").siblings().removeClass("active");
				$("#goodsIssued").parents().show();		
			}
			 	
		});	
	</script>