<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:forEach var="item" items="${results}" varStatus="status">
	<c:choose>
 		<c:when test="${scores[status.index]>4}"><font color="red">${item}&nbsp;</font></c:when>
 		<c:when test="${scores[status.index]>0}"><font color="orange">${item}&nbsp;</font></c:when>
 		<c:otherwise>${item}&nbsp;</c:otherwise>
	</c:choose>
</c:forEach>
(${rates}%)