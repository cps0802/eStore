<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Spring Form Tag Library 사용하기 위한 코드 -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="container-wrapper">
	<div class="container">

		<h2>Edit Product</h2>
		<p class="lead">Fill the below information to add a product:</p>

		<sf:form
			action="${pageContext.request.contextPath}/admin/productInventory/editProduct?${_csrf.parameterName}=${_csrf.token}"
			method="post" modelAttribute="product" enctype="multipart/form-data">
			<!-- Controller에서 model의 product라는 이름으로 넘겨줬기 때문에.일치해야지 Mapping 발생 -->
			
			
			<!-- editProduct()로 부터 받음 product객체에 의해서 Path의 속성 값이 다 들어가게 됨. -->
			<!-- 그 후 이 Page에서 수정된 데이터를 받아서 Contoroller editProductPost()를 통해 전송 -->
			<sf:hidden path="id"/>

			<div class="form-group">
				<label for="name">Name</label>
				<sf:input path="name" id="name" class="form-control" />
				<sf:errors path="name" cssStype="color:#ff0000"/>
			</div>

			<div class="form-group">
				<label for="category">Category</label>
				<sf:radiobutton path="category" id="category" value="컴퓨터" /> 컴퓨터
				<sf:radiobutton path="category" id="category" value="가전" /> 가전
				<sf:radiobutton path="category" id="category" value="신발" /> 신발
			</div>

			<div class="form-group">
				<label for="description">Description</label>
				<sf:textarea path="description" id="description"
					class="form-control" />
			</div>

			<div class="form-group">
				<label for="price">Price</label>
				<sf:input path="price" id="price" class="form-control" />
				<sf:errors path="price" cssStype="color:#ff0000"/>
			</div>

			<div class="form-group">
				<label for="unitInStock">Unit In Stock</label>
				<sf:input path="unitInStock" id="unitInStock" class="form-control" />
				<sf:errors path="unitInStock" cssStype="color:#ff0000"/>
			</div>

			<div class="form-group">
				<label for="manufacturer">Manufacturer</label>
				<sf:input path="manufacturer" id="manufacturer" class="form-control" />
				<sf:errors path="manufacturer" cssStype="color:#ff0000"/>
			</div>
			
			<div class="form-group">
				<label for="productImage">Upload Picture</label> <!-- file upload를 위한 설정 type="file" -->
				<sf:input path="productImage" id="productImage" type="file" class="form-control" />
			</div>

			<br>
			<input type="submit" value="submit" class="btn btn-default">
			<a href="<c:url value="/admin/productInventory" />"
				class="btn btn-default">Cancel</a>
		</sf:form>
		<br />
	</div>
</div>