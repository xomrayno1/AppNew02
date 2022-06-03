<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
	<div class="menu_section">
		<h3>General</h3>
			<ul class="nav side-menu" id="home">
				<li><a href='<c:url value="/index"></c:url>'><i class="fa fa-home"></i>Trang chủ  </a>					
				</li>
				
			</ul>
			  <c:if test="${userInfo.role == 1  || userInfo.role == 2}">  
				<ul class="nav side-menu" id="mainAct">
					<li><a><i class="fa fa-tasks"></i>Tác vụ chính<span class="fa fa-chevron-down"></span></a>
						<ul class="nav child_menu">
							<li id="goodsIssued"><a href='<c:url value="/goods-issue/add/1"></c:url>'> Bán hàng </a></li> 	
							<c:if test="${userInfo.role == 1 }">  
								<li id="goodsReceipt"><a href='<c:url value="/goods-receipt/add/1"></c:url>'>Nhập hàng </a></li> 
							</c:if> 					
						</ul>
					</li>
				</ul>	
				</c:if> 
				<c:if test="${userInfo.role == 1 }">  
				<ul class="nav side-menu" id="manage">
					<li><a><i class="fa fa-users"></i>Quản lý<span class="fa fa-chevron-down"></span></a>
						<ul class="nav child_menu">
							<li id="productList"><a href='<c:url value="/manage/product/list"></c:url>'>Sản phẩm</a></li>
							<li id="supplierlist"><a href='<c:url value="/manage/supplier/list"></c:url>'>Nhà cung cấp </a></li>
							<li id="userlist"><a href='<c:url value="/manage/user/list"></c:url>'>Tài khoản</a></li>	
							<li id="customerslist"><a href='<c:url value="/customers/list"></c:url>'>Khách hàng</a></li>
							<li id="goodsIssuedList"><a href='<c:url value="/goods-issue/list/1"></c:url>'>Bán hàng </a></li> 	
							<li id="goodsReceiptList"><a href='<c:url value="/goods-receipt/list/1"></c:url>'>Nhập hàng </a></li> 	
							 
						</ul>
					</li>
				</ul>
				
				<ul class="nav side-menu" id="statistics">
					<li><a><i class="fa fa-bar-chart"></i>Báo cáo<span class="fa fa-chevron-down"></span></a>
						<ul class="nav child_menu">
						 	<li id="reportIssued"><a href='<c:url value="/goods-issue/report/list"></c:url>'>Báo cáo bán</a></li>		
						 	<li id="reportReceipt"><a href='<c:url value="/goods-receipt/report/list"></c:url>'>Báo cáo nhập</a></li>	
						</ul>
					</li>
				</ul>
 			</c:if> 
			 
	</div>
</div>