<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
 
<c:forEach var="item" items="${sentences}" varStatus="status">
	<li class="list-group-item list-group-item-action w-100 p-0 list-sentence">
		${item}
	</li>
</c:forEach>