<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-wrapper">
	<!-- 직접 만든 css설정 main.css-->
	
	<div class="container">
		<h2>Administrator Page</h2>
		<p class="lead">Products를 관리할 수 있는 페이지입니다.</p>
	</div>

	<div class="container">
	<!-- c:url은 context root가 앞에 붙게 된다. eStore -->
		<a href="<c:url value="/admin/productInventory"/>"><h2>Products Inventory</h2></a>
		<p>Here you can view, check and modify the product inventory!!</p>
	</div>
</div>
