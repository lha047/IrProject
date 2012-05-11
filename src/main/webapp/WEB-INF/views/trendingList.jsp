<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<c:if test="${not empty  trends}">
	
	<c:forEach var="hourTrends" items="${trends.trends}">
		<li class="trending_hour ten_cols slide hidden">
			<div class="date">
				<fmt:formatDate value="${hourTrends.key}" pattern="dd.MMM HH:mm" />
			</div>
			<ul class="">
				
				<c:forEach var="trend" items="${hourTrends.value}">
		 			<li><a href="search?q=<c:out value="${trend.name}" />&resultsPerPage=20"><c:out value="${trend.name}" /></a></li>	
				</c:forEach>
			</ul>
		</li>	
	</c:forEach>
</c:if>