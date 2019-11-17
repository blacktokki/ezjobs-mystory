<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:forEach var="item" items="${sentences}" varStatus="status">
	<li class="list-group-item p-0">
		${item}
	</li>
</c:forEach>