<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<jsp:include page="htmlheader.jsp"></jsp:include>
	<title>FeedJam: A New Twitter Experience</title>

	
<jsp:include page="header.jsp"></jsp:include>

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
	<script type="text/javascript" src="resources/js/feedjam.revolver.js"></script>
	<script type="text/javascript" src="resources/js/feedjam.twitter-interaction.js"></script>
<jsp:include page="footer.jsp"></jsp:include>
