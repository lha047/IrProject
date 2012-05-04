<!doctype html />
<!-- 
	Project: Info323/Feedjam
	Frontend: Torstein Thune
	Backend: Snorre Davøen, Lisa Halvorsen
	-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>FeedJam: A New Twitter Experience</title>
	<link rel="stylesheet" type="text/css" href="resources/style.css" />
</head>
<body class="awesome">

	<h1>Hello world!</h1>
	<div>
		<c:if test="${not empty results}">
			<div id="dailies">
				<c:forEach var="result" items="${results}">
					<div class="tweetRow">
						<p>${result.fromUser} ${result.text}</p>

					</div>
				</c:forEach>
			</div>
		</c:if>
	</div>
	<div id="main-content">

		<c:if test="${not empty publicTweets}">
			<div id="dailies">
				<c:forEach var="tweet" items="${publicTweets}">
					<div class="tweetRow">
						<img class="tweetPic" src="${tweet.profileImageUrl}"> <span
							class="tweetUser"><c:out value="${tweet.fromUser}" /></span> <span
							class="tweetContent"> <span class="tweetText"><c:out
									value="${tweet.text}" /></span><br> <span class="tweetDate"><c:out
									value="${tweet.createdAt}" /></span>
						</span>
					</div>
				</c:forEach>
			</div>
		</c:if>


	</div>

</body>
</html>
