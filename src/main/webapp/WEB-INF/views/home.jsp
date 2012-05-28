<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<jsp:include page="htmlheader.jsp"></jsp:include>
	<title>FeedJam: A New Twitter Experience</title>

	
<jsp:include page="header.jsp"></jsp:include>

	<c:if test="${not empty error}">
		<div class="error">
			<c:out value="${error}" />
		</div>
	</c:if>
	<c:if test="${empty error}">
		<div id="trendingList">
			<h1 class="block ten_cols center trending_topics text_center">Wordwide trends last 24 hours</h1>
			<div class="slider ten_cols">
				<div class="prev"></div>
				<div class="next"></div>
				<ul id="trending_topics" class="trending_topics ten_cols slides">
					<jsp:include page="trendingList.jsp"></jsp:include>
				</ul>
			</div>
		</div>
	</c:if>
<jsp:include page="footer.jsp"></jsp:include>