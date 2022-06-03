<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"  %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Login </title>

    <!-- Bootstrap -->
       <link href='<c:url value="/resources/vendors/bootstrap/dist/css/bootstrap.min.css"></c:url>' rel="stylesheet">
    <!-- Font Awesome -->
    <link href='<c:url value="/resources/vendors/font-awesome/css/font-awesome.min.css"></c:url>' rel="stylesheet">
    <!-- NProgress -->
    <link href='<c:url value="/resources/vendors/nprogress/nprogress.css"></c:url>' rel="stylesheet">
    <!-- Animate.css -->
    <link href='<c:url value="/resources/vendors/animate.css/animate.min.css"></c:url>' rel="stylesheet">
    <link href='<c:url value="/resources/vendors/bootstrap-daterangepicker/daterangepicker.css"></c:url>' rel="stylesheet">
    <link href="<c:url value="/resources/vendors/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.css"/>" rel="stylesheet">
    <!-- Custom Theme Style -->
   <link href='<c:url value="/resources/build/css/custom.min.css"></c:url>' rel="stylesheet">
  </head>

  <body class="login">
    <div>
      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
          <c:url var="processLogin"  value="/processLogin"/>
            <form:form servletRelativeAction="/processLogin" modelAttribute="loginForm" method="POST">
              <h1>Đăng Nhập</h1>
              <div>         
                <form:input path="username" cssClass="form-control" placeholder="Username"/>
                <div class="has-error">
         			<form:errors path="username" cssClass="helpl-block" style="color :red"/>         		
         		</div>	
              </div>
              <div>               
                <form:password path="password" cssClass="form-control" placeholder="Pasword"/>
         		<div class="has-error">
         			<form:errors path="password" cssClass="helpl-block" style="color :red"/>         		
         		</div>	
              </div>
              <div>
                <button class="btn btn-primary" type="submit">Đăng nhập</button>
                 
              </div>

              <div class="clearfix"></div>
            </form:form>
          </section>
        </div>

 
      </div>
    </div>
  </body>
</html>
